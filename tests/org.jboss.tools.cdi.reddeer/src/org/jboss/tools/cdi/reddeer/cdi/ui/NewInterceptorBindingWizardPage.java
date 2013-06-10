package org.jboss.tools.cdi.reddeer.cdi.ui;

import java.util.List;

/**
 * 
 * @author jjankovi
 *
 */
public class NewInterceptorBindingWizardPage extends CDIComponentWizardPage {

	public NewInterceptorBindingWizardPage() {
		super();
	}
	
	public void addInherited(boolean toggle) {
		CDIWizardPageUtils.addInherated(toggle);
	}
	
	public void setTarget(InterceptorBindingTarget target) {
		CDIWizardPageUtils.setTarget(target.getTargetValue());
	}
	
	public void setInterceptorBindings(List<String> interceptors) {
		CDIWizardPageUtils.setInterceptorBindings(interceptors);
	}
	
}