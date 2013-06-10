package org.jboss.tools.cdi.bot.test.creator.config;

import java.util.List;

/**
 * 
 * @author jjankovi
 *
 */
public class BeanConfiguration extends CDIComponentConfiguration {

	private boolean isPublic;
	private boolean isDefault;
	private boolean isAbstract;
	private boolean isFinal;
	private String superClass;
	private List<String> interfaces;
	private boolean isNamed;
	private String beanName;
	private boolean isAlternative;
	private boolean registerInBeansXML;
	private String scope;
	private List<String> qualifiers;
	
	@Override
	public BeanConfiguration setSourceFolder(String sourceFolder) {
		super.setSourceFolder(sourceFolder);
		return this;
	}
	
	@Override
	public BeanConfiguration setPackageName(String packageName) {
		super.setPackageName(packageName);
		return this;
	}
	
	@Override
	public BeanConfiguration setName(String name) {
		super.setName(name);
		return this;
	}
	
	public boolean isPublic() {
		return isPublic;
	}
	public BeanConfiguration setPublic(boolean isPublic) {
		this.isPublic = isPublic;
		return this;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public BeanConfiguration setDefault(boolean isDefault) {
		this.isDefault = isDefault;
		return this;
	}
	public boolean isAbstract() {
		return isAbstract;
	}
	public BeanConfiguration setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
		return this;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public BeanConfiguration setFinal(boolean isFinal) {
		this.isFinal = isFinal;
		return this;
	}
	public String getSuperClass() {
		return superClass;
	}
	public BeanConfiguration setSuperClass(String superClass) {
		this.superClass = superClass;
		return this;
	}
	public List<String> getInterfaces() {
		return interfaces;
	}
	public BeanConfiguration setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
		return this;
	}
	public boolean isNamed() {
		return isNamed;
	}
	public BeanConfiguration setNamed(boolean isNamed) {
		this.isNamed = isNamed;
		return this;
	}
	public String getBeanName() {
		return beanName;
	}
	public BeanConfiguration setBeanName(String beanName) {
		this.beanName = beanName;
		return this;
	}
	public boolean isAlternative() {
		return isAlternative;
	}
	public BeanConfiguration setAlternative(boolean isAlternative) {
		this.isAlternative = isAlternative;
		return this;
	}
	public boolean isRegisterInBeansXML() {
		return registerInBeansXML;
	}
	public BeanConfiguration setRegisterInBeansXML(boolean registerInBeansXML) {
		this.registerInBeansXML = registerInBeansXML;
		return this;
	}
	
	public String getScope() {
		return scope;
	}

	public BeanConfiguration setScope(String scope) {
		this.scope = scope;
		return this;
	}
	
	public List<String> getQualifiers() {
		return qualifiers;
	}

	public BeanConfiguration setQualifiers(List<String> qualifiers) {
		this.qualifiers = qualifiers;
		return this;
	}
	
	@Override
	public BeanConfiguration generateComments(boolean generate) {
		super.generateComments(generate);
		return this;
	}
	
}
