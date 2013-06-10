package org.jboss.tools.cdi.reddeer.cdi.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * 
 * @author jjankovi
 *
 */
public class RenameNamedBeanWizardSecondPage extends WizardPage {

	public List<String> getAffectedFiles() {
		List<String> affectedFiles = new ArrayList<String>();
		
		for (TreeItem file : new DefaultTreeItem("Rename @Named Bean").getItems()) {
			affectedFiles.add(file.getText().split(" - ")[0]);
		}
		return affectedFiles;
	}
	
}
