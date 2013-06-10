package org.jboss.tools.cdi.reddeer.cdi.ui;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class NewBeansXMLCreationWizardPage extends WizardPage {

	public NewBeansXMLCreationWizardPage() {
		super();
	}
	
	public void setParentFolder(String parentFolder) {
		CDIWizardPageUtils.setParentFolder(parentFolder);
	}
	
	public void setFileName(String fileName) {
		CDIWizardPageUtils.setFileName(fileName);
	}
	
}
