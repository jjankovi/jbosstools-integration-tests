package org.jboss.tools.cdi.reddeer.cdi.ui;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class CDIComponentWizardPage extends WizardPage {

	public CDIComponentWizardPage() {
		super();
	}
	
	public void setSourceFolder(String sourceFolder) {
		CDIWizardPageUtils.setSourceFolder(sourceFolder);
	}
	
	public String getSourceFolder() {
		return CDIWizardPageUtils.getSourceFolder();
	}
	
	public void setSourceFolderWithBrowse(String... sourceFolderPath) {
		CDIWizardPageUtils.setSourceFolderWithBrowse(sourceFolderPath);
	}
	
	public void setPackage(String packageName) {
		CDIWizardPageUtils.setPackage(packageName);
	}
	
	public String getPackage() {
		return CDIWizardPageUtils.getPackage();
	}
	
	public void setPackageWithBrowse(String packageName) {
		CDIWizardPageUtils.setPackageWithBrowse(packageName);
	}
	
	public void setName(String name) {
		CDIWizardPageUtils.setName(name);
	}
	
	public String getName() {
		return CDIWizardPageUtils.getName();
	}
	
	public void generateComments(boolean toggle) {
		CDIWizardPageUtils.generateComments(toggle);
	}
	
}
