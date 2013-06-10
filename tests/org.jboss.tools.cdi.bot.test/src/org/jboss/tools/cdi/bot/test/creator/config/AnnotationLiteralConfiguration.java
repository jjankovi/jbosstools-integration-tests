package org.jboss.tools.cdi.bot.test.creator.config;

/**
 * 
 * @author jjankovi
 *
 */
public class AnnotationLiteralConfiguration extends CDIComponentConfiguration {

	private boolean isPublic;
	private boolean isDefault;
	private boolean isAbstract;
	private boolean isFinal;
	private String qualifier;
	
	@Override
	public AnnotationLiteralConfiguration setSourceFolder(String sourceFolder) {
		super.setSourceFolder(sourceFolder);
		return this;
	}
	
	@Override
	public AnnotationLiteralConfiguration setPackageName(String packageName) {
		super.setPackageName(packageName);
		return this;
	}
	
	@Override
	public AnnotationLiteralConfiguration setName(String name) {
		super.setName(name);
		return this;
	}
	
	public boolean isPublic() {
		return isPublic;
	}

	public AnnotationLiteralConfiguration setPublic(boolean isPublic) {
		this.isPublic = isPublic;
		return this;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public AnnotationLiteralConfiguration setDefault(boolean isDefault) {
		this.isDefault = isDefault;
		return this;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public AnnotationLiteralConfiguration setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public AnnotationLiteralConfiguration setFinal(boolean isFinal) {
		this.isFinal = isFinal;
		return this;
	}

	public String getQualifier() {
		return qualifier;
	}

	public AnnotationLiteralConfiguration setQualifier(String qualifier) {
		this.qualifier = qualifier;
		return this;
	}
	
	@Override
	public AnnotationLiteralConfiguration generateComments(boolean generate) {
		super.generateComments(generate);
		return this;
	}
	
}
