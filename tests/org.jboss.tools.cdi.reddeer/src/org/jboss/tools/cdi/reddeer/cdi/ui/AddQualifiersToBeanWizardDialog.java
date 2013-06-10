package org.jboss.tools.cdi.reddeer.cdi.ui;

import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

public class AddQualifiersToBeanWizardDialog extends WizardDialog {

	public AddQualifiersToBeanWizardDialog() {
		super();
		new DefaultShell("Specify CDI Bean for the Injection Point");
	}
	
	@Override
	public AddQualifiersToBeanWizardDialogFirstPage getFirstPage() {
		return new AddQualifiersToBeanWizardDialogFirstPage();
	}
	
	public AddQualifiersToBeanWizardDialogSecondPage getSecondPage() {
		return new AddQualifiersToBeanWizardDialogSecondPage();
	}
	
}