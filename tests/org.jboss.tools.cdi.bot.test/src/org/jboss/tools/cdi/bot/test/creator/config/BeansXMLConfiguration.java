package org.jboss.tools.cdi.bot.test.creator.config;

/**
 * 
 * @author jjankovi
 *
 */
public class BeansXMLConfiguration {

	private String parentFolder;
	private String fileName;
	
	public String getParentFolder() {
		return parentFolder;
	}
	public BeansXMLConfiguration setParentFolder(String parentFolder) {
		this.parentFolder = parentFolder;
		return this;
	}
	public String getFileName() {
		return fileName;
	}
	public BeansXMLConfiguration setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
	
}