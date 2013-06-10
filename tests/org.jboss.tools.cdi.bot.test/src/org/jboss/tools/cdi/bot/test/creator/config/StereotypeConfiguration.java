package org.jboss.tools.cdi.bot.test.creator.config;

import java.util.List;

import org.jboss.tools.cdi.reddeer.cdi.ui.StereotypeTarget;

/**
 * 
 * @author jjankovi
 *
 */
public class StereotypeConfiguration extends CDIComponentConfiguration {

	private boolean inherited;
	private boolean alternative;
	private boolean registerInBeansXML;
	private boolean named;
	private String scope;
	private StereotypeTarget target;
	private List<String> interceptors;
	private List<String> stereotypes;
	
	@Override
	public StereotypeConfiguration setSourceFolder(String sourceFolder) {
		super.setSourceFolder(sourceFolder);
		return this;
	}
	
	@Override
	public StereotypeConfiguration setPackageName(String packageName) {
		super.setPackageName(packageName);
		return this;
	}
	
	@Override
	public StereotypeConfiguration setName(String name) {
		super.setName(name);
		return this;
	}
	
	public boolean isInherited() {
		return inherited;
	}

	public StereotypeConfiguration setInherited(boolean inherited) {
		this.inherited = inherited;
		return this;
	}

	public boolean isAlternative() {
		return alternative;
	}

	public StereotypeConfiguration setAlternative(boolean alternative) {
		this.alternative = alternative;
		return this;
	}

	public boolean isRegisterInBeansXML() {
		return registerInBeansXML;
	}

	public StereotypeConfiguration setRegisterInBeansXML(
			boolean registerInBeansXML) {
		this.registerInBeansXML = registerInBeansXML;
		return this;
	}

	public boolean isNamed() {
		return named;
	}

	public StereotypeConfiguration setNamed(boolean named) {
		this.named = named;
		return this;
	}

	public String getScope() {
		return scope;
	}

	public StereotypeConfiguration setScope(String scope) {
		this.scope = scope;
		return this;
	}

	public StereotypeTarget getTarget() {
		return target;
	}

	public StereotypeConfiguration setTarget(StereotypeTarget target) {
		this.target = target;
		return this;
	}

	public List<String> getInterceptors() {
		return interceptors;
	}

	public StereotypeConfiguration setInterceptors(List<String> interceptors) {
		this.interceptors = interceptors;
		return this;
	}

	public List<String> getStereotypes() {
		return stereotypes;
	}

	public StereotypeConfiguration setStereotypes(List<String> stereotypes) {
		this.stereotypes = stereotypes;
		return this;
	}

	@Override
	public StereotypeConfiguration generateComments(boolean generate) {
		super.generateComments(generate);
		return this;
	}
	
}
