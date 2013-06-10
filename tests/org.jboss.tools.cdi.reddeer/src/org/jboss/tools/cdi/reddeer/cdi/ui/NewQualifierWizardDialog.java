package org.jboss.tools.cdi.reddeer.cdi.ui;

/**
 * 
 * @author jjankovi
 *
 */
public class NewQualifierWizardDialog extends CDIComponentWizardDialog {

	public NewQualifierWizardDialog() {
		super("Qualifier Annotation");
	}

	@Override
	public NewQualifierWizardPage getFirstPage() {
		return new NewQualifierWizardPage();
	}
	
}
