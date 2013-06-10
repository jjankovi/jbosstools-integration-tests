package org.jboss.tools.cdi.bot.test.creator.config;

import java.util.List;

import org.jboss.tools.cdi.reddeer.cdi.ui.InterceptorBindingTarget;

/**
 * 
 * @author jjankovi
 *
 */
public class InterceptorBindingConfiguration extends CDIComponentConfiguration {

	private boolean inherited;
	private InterceptorBindingTarget target;
	private List<String> interceptors;
	
	@Override
	public InterceptorBindingConfiguration setSourceFolder(String sourceFolder) {
		super.setSourceFolder(sourceFolder);
		return this;
	}
	
	@Override
	public InterceptorBindingConfiguration setPackageName(String packageName) {
		super.setPackageName(packageName);
		return this;
	}
	
	@Override
	public InterceptorBindingConfiguration setName(String name) {
		super.setName(name);
		return this;
	}
	
	public boolean isInherited() {
		return inherited;
	}

	public InterceptorBindingConfiguration setInherited(boolean inherited) {
		this.inherited = inherited;
		return this;
	}

	public InterceptorBindingTarget getTarget() {
		return target;
	}

	public InterceptorBindingConfiguration setTarget(
			InterceptorBindingTarget target) {
		this.target = target;
		return this;
	}

	public List<String> getInterceptors() {
		return interceptors;
	}

	public InterceptorBindingConfiguration setInterceptors(
			List<String> interceptors) {
		this.interceptors = interceptors;
		return this;
	}

	@Override
	public InterceptorBindingConfiguration generateComments(boolean generate) {
		super.generateComments(generate);
		return this;
	}
	
}
