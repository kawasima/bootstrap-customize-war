package net.unit8.bootstrap.customize.config;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ApplicationConfig implements Serializable {
	private static ApplicationConfig instance;

	protected ApplicationConfig() {
		setLessDirectory(new File("target/less"));
		setBaseLessFile(new File(lessDirectory, "bootstrap-custom-base.less"));
		setCssOutputFile(new File("target/css/bootstrap-custom.css"));
        setBootstrapVersion("2.2.2");
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
    private String bootstrapVersion;

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

    public String getBootstrapVersion() {
        return bootstrapVersion;
    }

    public void setBootstrapVersion(String bootstrapVersion) {
        this.bootstrapVersion = bootstrapVersion;
    }
}
