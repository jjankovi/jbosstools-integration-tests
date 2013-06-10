package org.jboss.tools.cdi.reddeer.cdi.ui;

/**
 * 
 * @author jjankovi
 *
 */
public class NewQualifierWizardPage extends CDIComponentWizardPage {

	public NewQualifierWizardPage() {
		super();
	}
	
	public void addInherated(boolean toggle) {
		CDIWizardPageUtils.addInherated(toggle);
	}
	
}
