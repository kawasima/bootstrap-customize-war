package net.unit8.bootstrap.customize.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import net.unit8.bootstrap.customize.config.ApplicationConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {
	private String baseColor;
	private String mainColor;
	private String accentColor;

	private static final Pattern VARIABLE_DEFINITION_PTN = Pattern.compile("@\\w+:\\s*(#[A-Fa-f0-9]+)\\s*;");

	public String execute() {
		File customizeBase = ApplicationConfig.instance().getBaseLessFile();
		try {
			if (!customizeBase.exists() || customizeBase.length() == 0) {
				setBaseColor("#d9d5d9");
				setMainColor("#5b554d");
				setAccentColor("#b11a2c");
			} else {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								new FileInputStream(customizeBase)));
				try {
					while(true) {
						String line = reader.readLine();
						if (line == null || hasSetAllColors())
							break;
						if (line.startsWith("@baseColor:")) {
							setBaseColor(findLessVariable(line));
						} else if (line.startsWith("@mainColor:")) {
							setMainColor(findLessVariable(line));
						} else if (line.startsWith("@accentColor:")) {
							setAccentColor(findLessVariable(line));
						}
					}
				} finally {
					IOUtils.closeQuietly(reader);
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
    }

    @Action(value="apply", results={
    	@Result(name="success", type="redirect", location="/")
    })
    public String apply() {
		File customizeBase = ApplicationConfig.instance().getBaseLessFile();
		try {
			StringBuilder newLess = new StringBuilder();
			BufferedReader reader = new BufferedReader(
					new StringReader(FileUtils.readFileToString(customizeBase, "UTF-8")));
			while(true) {
				String line = reader.readLine();
				if (line == null)
					break;
				if (line.startsWith("@baseColor:")) {
					newLess.append("@baseColor: ").append(baseColor).append(";\n");
				} else if (line.startsWith("@mainColor:")) {
					newLess.append("@mainColor: ").append(mainColor).append(";\n");
				} else if (line.startsWith("@accentColor:")) {
					newLess.append("@accentColor: ").append(accentColor).append(";\n");
				} else {
					newLess.append(line).append("\n");
				}
			}
			FileUtils.writeStringToFile(customizeBase, newLess.toString(), "UTF-8");
	    	return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
    }

    public String getBaseColor() {
		return baseColor;
	}

	public void setBaseColor(String baseColor) {
		this.baseColor = baseColor;
	}

	public String getMainColor() {
		return mainColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	public String getAccentColor() {
		return accentColor;
	}

	public void setAccentColor(String accentColor) {
		this.accentColor = accentColor;
	}

	public String getExamplePath() {
		ServletContext context = (ServletContext) ActionContext
				.getContext().get(StrutsStatics.SERVLET_CONTEXT);
		String templatePath = context.getInitParameter("templatePath");
		if (templatePath == null) {
			return "example.html";
		} else {
			return "/example.html";
		}
	}

	private boolean hasSetAllColors() {
		return baseColor != null && mainColor != null && accentColor != null;
	}

	private String findLessVariable(String line) {
		Matcher m = VARIABLE_DEFINITION_PTN.matcher(line);
		if (m.find()) {
            return m.group(1);
		}
		throw new IllegalArgumentException("Match failure: " + line);
	}
}
