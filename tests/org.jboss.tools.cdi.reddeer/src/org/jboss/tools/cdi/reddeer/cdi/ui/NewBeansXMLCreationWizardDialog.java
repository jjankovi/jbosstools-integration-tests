package org.jboss.tools.cdi.reddeer.cdi.ui;

/**
 * 
 * @author jjankovi
 *
 */
public class NewBeansXMLCreationWizardDialog extends CDIComponentWizardDialog {

	public NewBeansXMLCreationWizardDialog() {
		super("File beans.xml");
	}
	
	@Override
	public NewBeansXMLCreationWizardPage getFirstPage() {
		return new NewBeansXMLCreationWizardPage();
	}
	
}
