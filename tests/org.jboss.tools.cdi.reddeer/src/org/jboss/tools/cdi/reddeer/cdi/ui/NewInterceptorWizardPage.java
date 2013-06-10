package org.jboss.tools.cdi.reddeer.cdi.ui;

import java.util.List;

/**
 * 
 * @author jjankovi
 *
 */
public class NewInterceptorWizardPage extends CDIComponentWizardPage {

	public NewInterceptorWizardPage() {
		super();
	}
	
	public void setSuperClass(String superClass) {
		CDIWizardPageUtils.setSuperclass(superClass);
	}
	
	public void setSuperClassWithBrowse(String superClass) {
		CDIWizardPageUtils.setSuperclassWithBrowse(superClass);
	}
	
	public void setInterceptorBindings(List<String> interceptors) {
		CDIWizardPageUtils.setInterceptorBindings(interceptors);
	}
	
	public void setAroundInvokeMethodName(String methodName) {
		CDIWizardPageUtils.setAroundInvokeMethodName(methodName);
	}
	
	public void registerInBeansXML(boolean toggle) {
		CDIWizardPageUtils.registerInBeansXML(toggle);
	}
	
}
