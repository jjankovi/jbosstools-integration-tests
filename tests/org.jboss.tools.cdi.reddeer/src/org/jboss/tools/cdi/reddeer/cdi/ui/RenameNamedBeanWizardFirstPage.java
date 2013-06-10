package org.jboss.tools.cdi.reddeer.cdi.ui;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.text.DefaultText;

public class RenameNamedBeanWizardFirstPage extends WizardPage {

	public void setNamedName(String name) {
		new DefaultText(0).setText(name);
	}
	
}
