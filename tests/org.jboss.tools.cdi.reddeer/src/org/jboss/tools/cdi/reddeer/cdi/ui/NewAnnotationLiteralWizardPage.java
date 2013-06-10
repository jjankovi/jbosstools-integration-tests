package org.jboss.tools.cdi.reddeer.cdi.ui;


/**
 * 
 * @author jjankovi
 *
 */
public class NewAnnotationLiteralWizardPage extends CDIComponentWizardPage {

	public NewAnnotationLiteralWizardPage() {
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
	
	public void setQualifier(String qualifier) {
		CDIWizardPageUtils.setQualifier(qualifier);
	}
	
	public void setQualifierWithBrowse(String qualifier) {
		CDIWizardPageUtils.setQualifierWithBrowse(qualifier);
	}
	
}
