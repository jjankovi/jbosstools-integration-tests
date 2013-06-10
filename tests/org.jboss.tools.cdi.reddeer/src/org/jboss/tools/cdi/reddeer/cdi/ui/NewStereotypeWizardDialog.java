package org.jboss.tools.cdi.reddeer.cdi.ui;


public class NewStereotypeWizardDialog extends CDIComponentWizardDialog {

	public NewStereotypeWizardDialog() {
		super("Stereotype Annotation");
	}
	
	@Override
	public NewStereotypeWizardPage getFirstPage() {
		return new NewStereotypeWizardPage();
	}

}
