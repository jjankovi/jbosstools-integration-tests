package org.jboss.tools.cdi.reddeer.cdi.ui;


public class NewScopeCreationWizardDialog extends CDIComponentWizardDialog {

	public NewScopeCreationWizardDialog() {
		super("Scope Annotation");
	}
	
	@Override
	public NewScopeCreationWizardPage getFirstPage() {
		return new NewScopeCreationWizardPage();
	}

}
