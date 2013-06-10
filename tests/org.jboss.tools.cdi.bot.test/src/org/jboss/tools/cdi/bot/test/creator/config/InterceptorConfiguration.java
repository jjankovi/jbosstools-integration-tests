package org.jboss.tools.cdi.bot.test.creator.config;

import java.util.List;

/**
 * 
 * @author jjankovi
 *
 */
public class InterceptorConfiguration extends CDIComponentConfiguration {

	private String superClass;
	private List<String> interceptors;
	private String aroundInvokeName;
	private boolean registerInBeansXML;
	
	@Override
	public InterceptorConfiguration setSourceFolder(String sourceFolder) {
		super.setSourceFolder(sourceFolder);
		return this;
	}
	
	@Override
	public InterceptorConfiguration setPackageName(String packageName) {
		super.setPackageName(packageName);
		return this;
	}
	
	@Override
	public InterceptorConfiguration setName(String name) {
		super.setName(name);
		return this;
	}
	
	public String getSuperClass() {
		return superClass;
	}

	public InterceptorConfiguration setSuperClass(String superClass) {
		this.superClass = superClass;
		return this;
	}

	public List<String> getInterceptors() {
		return interceptors;
	}

	public InterceptorConfiguration setInterceptors(
			List<String> interceptors) {
		this.interceptors = interceptors;
		return this;
	}

	public String getAroundInvokeName() {
		return aroundInvokeName;
	}

	public InterceptorConfiguration setAroundInvokeName(
			String aroundInvokeName) {
		this.aroundInvokeName = aroundInvokeName;
		return this;
	}

	public boolean isRegisterInBeansXML() {
		return registerInBeansXML;
	}

	public InterceptorConfiguration setRegisterInBeansXML(
			boolean registerInBeansXML) {
		this.registerInBeansXML = registerInBeansXML;
		return this;
	}
	
	@Override
	public InterceptorConfiguration generateComments(boolean generate) {
		super.generateComments(generate);
		return this;
	}

}
