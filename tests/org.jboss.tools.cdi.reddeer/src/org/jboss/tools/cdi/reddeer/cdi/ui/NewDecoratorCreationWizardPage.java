package org.jboss.tools.cdi.reddeer.cdi.ui;

import java.util.List;

/**
 * 
 * @author jjankovi
 *
 */
public class NewDecoratorCreationWizardPage extends CDIComponentWizardPage {

	public NewDecoratorCreationWizardPage() {
		super();
	}
	
	public void setPublic() {
		CDIWizardPageUtils.setPublic();
	}
	
	public void setDefault() {
		CDIWizardPageUtils.setDefault();
	}
	
	public void setAbstract(boolean toggle) {
		CDIWizardPageUtils.setAbstract(toggle);
	}
	
	public void setFinal(boolean toggle) {
		CDIWizardPageUtils.setFinal(toggle);
	}
	
	public void setDecoratedType(List<String> decoratedInterfaces) {
		CDIWizardPageUtils.setInterfaces(decoratedInterfaces, 2);
	}
	
	public List<String> getDecoratedInterfaces() {
		return CDIWizardPageUtils.getDecoratedInterfaces();
	}
	
	public void setDelegateFieldName(String delegate) {
		CDIWizardPageUtils.setDelegateFieldName(delegate);
	}
	
	public void registerInBeansXML(boolean toggle) {
		CDIWizardPageUtils.registerInBeansXML(toggle);
	}
	
}
