package org.jboss.tools.cdi.reddeer.cdi.ui;


public class NewBeanWizardDialog extends CDIComponentWizardDialog {

	public NewBeanWizardDialog() {
		super("Bean");
	}
	
	@Override
	public NewBeanWizardPage getFirstPage() {
		return new NewBeanWizardPage();
	}
	
}
