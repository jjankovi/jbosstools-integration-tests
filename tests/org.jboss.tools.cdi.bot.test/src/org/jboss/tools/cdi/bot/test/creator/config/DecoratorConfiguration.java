package org.jboss.tools.cdi.bot.test.creator.config;

import java.util.List;

/**
 * 
 * @author jjankovi
 *
 */
public class DecoratorConfiguration extends CDIComponentConfiguration {

	private boolean isPublic;
	private boolean isDefault;
	private boolean isAbstract;
	private boolean isFinal;
	private List<String> decoratedInterfaces;
	private String delegateField;
	private boolean registerInBeansXML;
	
	@Override
	public DecoratorConfiguration setSourceFolder(String sourceFolder) {
		super.setSourceFolder(sourceFolder);
		return this;
	}
	
	@Override
	public DecoratorConfiguration setPackageName(String packageName) {
		super.setPackageName(packageName);
		return this;
	}
	
	@Override
	public DecoratorConfiguration setName(String name) {
		super.setName(name);
		return this;
	}
	
	public boolean isPublic() {
		return isPublic;
	}

	public DecoratorConfiguration setPublic(boolean isPublic) {
		this.isPublic = isPublic;
		return this;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public DecoratorConfiguration setDefault(boolean isDefault) {
		this.isDefault = isDefault;
		return this;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public DecoratorConfiguration setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public DecoratorConfiguration setFinal(boolean isFinal) {
		this.isFinal = isFinal;
		return this;
	}

	public List<String> getDecoratedInterfaces() {
		return decoratedInterfaces;
	}

	public DecoratorConfiguration setDecoratedInterfaces(
			List<String> decoratedInterfaces) {
		this.decoratedInterfaces = decoratedInterfaces;
		return this;
	}

	public String getDelegateField() {
		return delegateField;
	}

	public DecoratorConfiguration setDelegateField(
			String delegateField) {
		this.delegateField = delegateField;
		return this;
	}

	public boolean isRegisterInBeansXML() {
		return registerInBeansXML;
	}

	public DecoratorConfiguration setRegisterInBeansXML(
			boolean registerInBeansXML) {
		this.registerInBeansXML = registerInBeansXML;
		return this;
	}
	
	@Override
	public DecoratorConfiguration generateComments(boolean generate) {
		super.generateComments(generate);
		return this;
	}
	
}
