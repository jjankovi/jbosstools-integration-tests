package org.jboss.tools.cdi.reddeer.cdi.ui;


public class NewInterceptorBindingWizardDialog extends CDIComponentWizardDialog {

	public NewInterceptorBindingWizardDialog() {
		super("Interceptor Binding Annotation");
	}
	
	@Override
	public NewInterceptorBindingWizardPage getFirstPage() {
		return new NewInterceptorBindingWizardPage();
	}

}
