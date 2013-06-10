package org.jboss.tools.cdi.reddeer.cdi.ui;

import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;

/**
 * 
 * @author jjankovi
 *
 */
public class RenameNamedBeanWizardDialog extends WizardDialog {

	public RenameNamedBeanWizardDialog() {
		super();
		new DefaultShell("Refactoring");
	}
	
	@Override
	public RenameNamedBeanWizardFirstPage getFirstPage() {
		return new RenameNamedBeanWizardFirstPage();
	}
	
	public RenameNamedBeanWizardSecondPage getSecondPage() {
		return new RenameNamedBeanWizardSecondPage();
	}
	
}
