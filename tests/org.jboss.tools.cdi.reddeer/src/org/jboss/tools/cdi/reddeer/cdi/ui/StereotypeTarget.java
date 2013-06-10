package org.jboss.tools.cdi.reddeer.cdi.ui;

/**
 * 
 * @author jjankovi
 *
 */
public enum StereotypeTarget {
	TYPE_METHOD_FIELD("TYPE,METHOD,FIELD"),METHOD_FIELD("METHOD,FIELD"),
	TYPE("TYPE"),METHOD("METHOD"),FIELD("FIELD");
	
	private String target;
	
	private StereotypeTarget(String target) {
		this.target = target;
	}
	
	public String getTargetValue() {
		return target;
	}
}
