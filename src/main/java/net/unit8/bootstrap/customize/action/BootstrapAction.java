package net.unit8.bootstrap.customize.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.List;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.TypeReference;
import net.arnx.jsonic.util.Base64;
import net.unit8.bootstrap.customize.dto.ReposContentDto;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.lesscss.LessCompiler;
import org.lesscss.LessException;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Namespace("/css")
public class BootstrapAction extends ActionSupport {
	private InputStream inputStream;

	private static final File BOOTSTRAP_DIR = new File("target/less/bootstrap");

	@Override
	@Action(value="bootstrap.css", results={
			@Result(name="success", type="stream", params={"contentType", "text/css"}),
			@Result(name="error", location="bootstrap-error.ftl"),
	})
	public String execute() throws Exception {
		if (!BOOTSTRAP_DIR.exists())
			FileUtils.forceMkdir(BOOTSTRAP_DIR);
		File readed = new File(BOOTSTRAP_DIR, ".readed");
		if (!readed.exists())
			fetchBootstrapLess();

		try {
			String css = compileBootstrapLess();
			System.out.println(css);
			setInputStream(new ByteArrayInputStream(css.getBytes()));
			return SUCCESS;
		} catch(LessException ex) {
			ex.printStackTrace();
			return ERROR;
		}
	}

	protected String compileBootstrapLess() throws IOException, LessException {
		File customizeBase = new File(BOOTSTRAP_DIR, "../bootstrap-custom-base.less");
		if (!customizeBase.exists()) {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("/bootstrap-custom-base.less");
			try {
				FileUtils.copyInputStreamToFile(in, customizeBase);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}
		LessCompiler compiler = new LessCompiler();
		String css = compiler.compile(customizeBase);
		return css;
	}

	protected void fetchBootstrapLess() throws Exception {
		List<ReposContentDto> reposContents = lessList();
		for (ReposContentDto reposContent : reposContents) {
			if (reposContent.getName().endsWith(".less")) {
				String less = less(reposContent.getName());
				FileUtils.writeStringToFile(new File(BOOTSTRAP_DIR, reposContent.getName()), less, "UTF-8");
			}
		}
		File readed = new File(BOOTSTRAP_DIR, ".readed");
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
