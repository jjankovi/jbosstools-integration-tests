package org.jboss.tools.cdi.reddeer.cdi.ui;

import java.util.List;


/**
 * 
 * @author jjankovi
 *
 */
public class NewBeanWizardPage extends CDIComponentWizardPage {

	public NewBeanWizardPage() {
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

	public void setSuperclass(String superClass) {
		CDIWizardPageUtils.setSuperclass(superClass);
	}
	
	public void setSuperclassWithBrowse(String superClass) {
		CDIWizardPageUtils.setSuperclassWithBrowse(superClass);
	}
	
	public void setInterfaces(List<String> interfaces) {
		CDIWizardPageUtils.setInterfaces(interfaces, 3);
	}
	
	public void addNamed(boolean toggle) {
		CDIWizardPageUtils.addNamed(toggle);
	}
	
	public void setBeanName(String beanName) {
		CDIWizardPageUtils.setBeanName(beanName);
	}
	
	public void addAlternative(boolean toggle) {
		CDIWizardPageUtils.addAlternative(toggle);
	}
	
	public void registerInBeansXML(boolean toggle) {
		CDIWizardPageUtils.registerInBeansXML(toggle);
	}
	
	public void setScope(String scope) {
		CDIWizardPageUtils.setScope(scope);
	}
	
	public void setQualifiers(List<String> qualifiers) {
		CDIWizardPageUtils.setQualifiers(qualifiers);
	}
	
}
