package org.jboss.tools.cdi.reddeer.cdi.ui;

/**
 * 
 * @author jjankovi
 *
 */
public class NewScopeCreationWizardPage extends CDIComponentWizardPage {

	public NewScopeCreationWizardPage() {
		super();
	}
	
	public void addInherited(boolean toggle) {
		CDIWizardPageUtils.addInherated(toggle);
	}
	
	public void setNormalScope(boolean toggle) {
		CDIWizardPageUtils.setNormalScope(toggle);
	}
	
	public void setPassivating(boolean toggle) {
		CDIWizardPageUtils.setPassivating(toggle);
	}
	
}
