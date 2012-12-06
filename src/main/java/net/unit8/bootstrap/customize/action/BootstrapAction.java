package net.unit8.bootstrap.customize.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.ServletContext;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.TypeReference;
import net.arnx.jsonic.util.Base64;
import net.unit8.bootstrap.customize.config.ApplicationConfig;
import net.unit8.bootstrap.customize.dto.ReposContentDto;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.lesscss.LessCompiler;
import org.lesscss.LessException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Namespace("/css")
public class BootstrapAction extends ActionSupport {
	private static LessCompiler compiler = new LessCompiler();
	private InputStream inputStream;

	@Override
	@Action(value="bootstrap.css", results={
			@Result(name="success", type="stream", params={"contentType", "text/css"}),
			@Result(name="error", location="bootstrap-error.ftl"),
	})
	public String execute() throws Exception {
		File bootstrapDir = ApplicationConfig.instance().getBootstrapDirectory();
		if (!bootstrapDir.exists())
			FileUtils.forceMkdir(bootstrapDir);
		File readed = new File(bootstrapDir, ".readed");
		if (!readed.exists())
			fetchBootstrapLess();

		try {
			compileBootstrapLess();
			setInputStream(new BufferedInputStream(
					new FileInputStream(ApplicationConfig.instance().getCssOutputFile())));
			return SUCCESS;
		} catch(LessException ex) {
			LOG.error(ex.getMessage(), ex);
			return ERROR;
		}
	}

	protected void compileBootstrapLess() throws IOException, LessException {
		File customizeBase = ApplicationConfig.instance().getBaseLessFile();
		if (!customizeBase.exists() || customizeBase.length() == 0) {
			ServletContext context = (ServletContext) ActionContext
					.getContext().get(StrutsStatics.SERVLET_CONTEXT);
			InputStream in = context.getResourceAsStream("/WEB-INF/bootstrap-custom-base.less");
			try {
				FileUtils.copyInputStreamToFile(in, customizeBase);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}
		File cssOutputFile = ApplicationConfig.instance().getCssOutputFile();
		if (!cssOutputFile.getParentFile().exists())
			FileUtils.forceMkdir(cssOutputFile.getParentFile());
		compiler.compile(customizeBase, cssOutputFile);
	}

	protected void fetchBootstrapLess() throws Exception {
		File bootstrapDir = ApplicationConfig.instance().getBootstrapDirectory();
		List<ReposContentDto> reposContents = lessList();
		for (ReposContentDto reposContent : reposContents) {
			if (reposContent.getName().endsWith(".less")) {
				String less = less(reposContent.getName());
				FileUtils.writeStringToFile(new File(bootstrapDir, reposContent.getName()), less, "UTF-8");
			}
		}
		File readed = new File(bootstrapDir, ".readed");
		FileUtils.touch(readed);
	}
	protected List<ReposContentDto> lessList() throws MalformedURLException, IOException, URISyntaxException {
		URI uri = new URI("https://api.github.com/repos/twitter/bootstrap/contents/less?ref=v2.2.1");
		URLConnection connection = uri.toURL().openConnection();
		List<ReposContentDto> reposContents = JSON
				.decode(connection.getInputStream(), new TypeReference<List<ReposContentDto>>() {});
		return reposContents;
	}

	protected String less(String name) throws MalformedURLException, IOException, URISyntaxException {
		URI uri = new URI("https://api.github.com/repos/twitter/bootstrap/contents/less/" + name + "?ref=v2.2.1");
		URLConnection connection = uri.toURL().openConnection();
		ReposContentDto reposContent = JSON
				.decode(connection.getInputStream(), ReposContentDto.class);
		return new String(Base64.decode(reposContent.getContent()), "UTF-8");

	}
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
