package org.jboss.tools.cdi.reddeer.cdi.ui;


public class NewInterceptorWizardDialog extends CDIComponentWizardDialog {

	public NewInterceptorWizardDialog() {
		super("Interceptor");
	}
	
	@Override
	public NewInterceptorWizardPage getFirstPage() {
		return new NewInterceptorWizardPage();
	}

}
