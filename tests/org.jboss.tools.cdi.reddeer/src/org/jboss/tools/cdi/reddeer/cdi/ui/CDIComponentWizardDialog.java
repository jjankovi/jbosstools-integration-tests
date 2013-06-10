package org.jboss.tools.cdi.reddeer.cdi.ui;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;

public abstract class CDIComponentWizardDialog extends NewWizardDialog {

	protected CDIComponentWizardDialog(String component) {
		super("CDI (Context and Dependency Injection)", component);
	}
	
}
