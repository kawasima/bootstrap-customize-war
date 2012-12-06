package net.unit8.bootstrap.customize.config;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ApplicationConfig implements Serializable {
	private static ApplicationConfig instance;

	protected ApplicationConfig() {
		lessDirectory = new File("target/less");
		baseLessFile  = new File(lessDirectory, "bootstrap-custom-base.less");
		cssOutputFile = new File("target/css/bootstrap-custom.css");
		setTemplatePath(null);
	}

	public static ApplicationConfig instance() {
		if (instance == null) {
			instance = new ApplicationConfig();
		}
		return instance;
	}

	private File baseLessFile;
	private File lessDirectory;
	private File cssOutputFile;
	private File templatePath;

	public File getBaseLessFile() {
		return baseLessFile;
	}

	public void setBaseLessFile(File baseLessFile) {
		this.baseLessFile = baseLessFile;
	}

	public File getLessDirectory() {
		return lessDirectory;
	}

	public void setLessDirectory(File lessDirectory) {
		this.lessDirectory = lessDirectory;
	}

	public File getCssOutputFile() {
		return cssOutputFile;
	}

	public void setCssOutputFile(File cssOutputFile) {
		this.cssOutputFile = cssOutputFile;
	}

	public File getBootstrapDirectory() {
		return new File(lessDirectory, "bootstrap");
	}

	public File getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(File templatePath) {
		this.templatePath = templatePath;
	}
}
