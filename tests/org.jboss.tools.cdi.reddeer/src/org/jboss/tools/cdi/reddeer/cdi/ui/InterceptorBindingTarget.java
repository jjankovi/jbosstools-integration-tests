package org.jboss.tools.cdi.reddeer.cdi.ui;

/**
 * 
 * @author jjankovi
 *
 */
public enum InterceptorBindingTarget {
	
	TYPE("TYPE"),TYPE_METHOD("TYPE,METHOD");
	
	private String target;
	
	private InterceptorBindingTarget(String target) {
		this.target = target;
	}
	
	public String getTargetValue() {
		return target;
	}
}
