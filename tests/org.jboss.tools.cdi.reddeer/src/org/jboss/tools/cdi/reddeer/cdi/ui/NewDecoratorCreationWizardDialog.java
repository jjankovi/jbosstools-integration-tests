package org.jboss.tools.cdi.reddeer.cdi.ui;

/**
 * 
 * @author jjankovi
 *
 */
public class NewDecoratorCreationWizardDialog extends CDIComponentWizardDialog {

	public NewDecoratorCreationWizardDialog() {
		super("Decorator");
	}
	
	@Override
	public NewDecoratorCreationWizardPage getFirstPage() {
		return new NewDecoratorCreationWizardPage();
	}
	
}
