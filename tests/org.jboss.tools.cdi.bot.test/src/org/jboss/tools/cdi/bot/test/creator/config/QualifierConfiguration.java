package org.jboss.tools.cdi.bot.test.creator.config;


/**
 * 
 * @author jjankovi
 *
 */
public class QualifierConfiguration extends CDIComponentConfiguration {

	private boolean inherated;
	
	@Override
	public QualifierConfiguration setSourceFolder(String sourceFolder) {
		super.setSourceFolder(sourceFolder);
		return this;
	}
	
	@Override
	public QualifierConfiguration setPackageName(String packageName) {
		super.setPackageName(packageName);
		return this;
	}
	
	@Override
	public QualifierConfiguration setName(String name) {
		super.setName(name);
		return this;
	}
	
	public boolean isInherated() {
		return inherated;
	}

	public QualifierConfiguration setInherated(boolean inherated) {
		this.inherated = inherated;
		return this;
	}

	@Override
	public QualifierConfiguration generateComments(boolean generate) {
		super.generateComments(generate);
		return this;
	}
	
}
