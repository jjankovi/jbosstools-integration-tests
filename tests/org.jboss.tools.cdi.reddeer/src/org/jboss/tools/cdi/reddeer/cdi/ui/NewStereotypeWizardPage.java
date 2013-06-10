package org.jboss.tools.cdi.reddeer.cdi.ui;

import java.util.List;

/**
 * 
 * @author jjankovi
 *
 */
public class NewStereotypeWizardPage extends CDIComponentWizardPage {

	public NewStereotypeWizardPage() {
		super();
	}
	
	public void addInherited(boolean toggle) {
		CDIWizardPageUtils.addInherated(toggle);
	}
	
	public void addAlternative(boolean toggle) {
		CDIWizardPageUtils.addAlternative(toggle);
	}
	
	public void registerInBeansXML(boolean toggle) {
		CDIWizardPageUtils.registerInBeansXML(toggle);
	}
	
	public void addNamed(boolean toggle) {
		CDIWizardPageUtils.addNamed(toggle);
	}
	
	public void setScope(String scope) {
		CDIWizardPageUtils.setScope(scope);
	}
	
	public void setTarget(StereotypeTarget target) {
		CDIWizardPageUtils.setTarget(target.getTargetValue());
	}
	
	public void setInterceptorBindings(List<String> interceptors) {
		CDIWizardPageUtils.setInterceptorBindings(interceptors);
	}
	
	public void setStereotypes(List<String> stereotypes) {
		CDIWizardPageUtils.setStereotypes(stereotypes);
	}
	
}