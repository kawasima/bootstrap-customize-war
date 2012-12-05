package net.unit8.bootstrap.customize.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class IndexAction extends ActionSupport {
	private String baseColor;
	private String mainColor;
	private String accentColor;

	private static final File BOOTSTRAP_DIR = new File("target/less/bootstrap");

	public String execute() throws Exception {
        return SUCCESS;
    }

    @Action(value="apply", results={
    	@Result(name="success", type="redirect", location="/")
    })
    public String apply() {
		File customizeBase = new File(BOOTSTRAP_DIR, "../bootstrap-custom-base.less");
		try {
			StringBuilder newLess = new StringBuilder();
			BufferedReader reader = new BufferedReader(
					new StringReader(FileUtils.readFileToString(customizeBase, "UTF-8")));
			while(true) {
				String line = reader.readLine();
				if (line == null)
					break;
				if (line.startsWith("@baseColor")) {
					newLess.append("@baseColor: ").append(baseColor).append(";\n");
				} else if (line.startsWith("@mainColor")) {
					newLess.append("@mainColor: ").append(mainColor).append(";\n");
				} else if (line.startsWith("@accentColor")) {
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


}
