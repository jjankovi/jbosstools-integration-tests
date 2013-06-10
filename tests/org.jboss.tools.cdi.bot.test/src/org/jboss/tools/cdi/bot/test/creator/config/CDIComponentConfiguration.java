package org.jboss.tools.cdi.bot.test.creator.config;

/**
 * 
 * @author jjankovi
 *
 */
public abstract class CDIComponentConfiguration {

	private String sourceFolder;
	private String packageName;
	private String name;
	private boolean generateComments;
	
	public String getSourceFolder() {
		return sourceFolder;
	}
	public CDIComponentConfiguration setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
		return this;
	}
	public String getPackageName() {
		return packageName;
	}
	public CDIComponentConfiguration setPackageName(String packageName) {
		this.packageName = packageName;
		return this;
	}
	public String getName() {
		return name;
	}
	public CDIComponentConfiguration setName(String name) {
		this.name = name;
		return this;
	}
	
	public boolean areCommentsGenerated() {
		return generateComments;
	}
	
	public CDIComponentConfiguration generateComments(boolean generate) {
		this.generateComments = generate;
		return this;
	}
	
}
