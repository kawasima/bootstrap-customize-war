package net.unit8.bootstrap.customize.action;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;

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

    @Action(value="apply")
    public String apply() {
    	return SUCCESS;
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
