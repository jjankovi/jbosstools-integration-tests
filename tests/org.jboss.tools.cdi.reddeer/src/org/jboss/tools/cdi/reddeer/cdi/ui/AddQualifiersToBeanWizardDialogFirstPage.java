package org.jboss.tools.cdi.reddeer.cdi.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.DefaultText;

/**
 * 
 * @author jjankovi
 *
 */
public class AddQualifiersToBeanWizardDialogFirstPage extends WizardPage {

	public AddQualifiersToBeanWizardDialogFirstPage() {
		super();
	}
	
	public void enterCDIQualifierPrefix(String prefix) {
		new DefaultText(0).setText(prefix);
	}
	
	public void add() {
		new PushButton("Add >").click();
	}
	
	public void remove() {
		new PushButton("< Remove").click();
	}

	public void addAll() {
		new PushButton("Add All >>").click();
	}

	public void addRemove() {
		new PushButton("<< Remove All").click();
	}
	
	public void edit() {
		new PushButton("Edit Value...").click();
	}
	
	public void createNewQualifier() {
		new PushButton("Create New Qualifier...").click();
	}
	
	public List<String> getAvailableQualifiers() {
		List<String> availableQualifiers = new ArrayList<String>();
		Table availableQualifiersTable = new DefaultTable(0); 
		int availableQualifiersCount = availableQualifiersTable.rowCount();
		for (int i = 0; i < availableQualifiersCount; i++) {
			availableQualifiers.add(availableQualifiersTable.cell(i, 0));
		}
		return availableQualifiers;
	}
	
	public List<String> getInBeanQualifiers() {
		List<String> inBeanQualifiers = new ArrayList<String>();
		Table inBeanTable = new DefaultTable(1); 
		int inBeanQualifiersCount = inBeanTable.rowCount();
		for (int i = 0; i < inBeanQualifiersCount; i++) {
			inBeanQualifiers.add(inBeanTable.cell(i, 0));
		}
		return inBeanQualifiers;
	}
	
	public void selectAvailableQualifier(String qualifierToSelect) {
		for (String availableQualifier : getAvailableQualifiers()) {
			if (availableQualifier.equals(qualifierToSelect)) {
				new DefaultTable(0).select(qualifierToSelect);
				break;
			}
		}
	}
	
	public void selectInBeanQualifier(String qualifierToSelect) {
		for (String inBeanQualifier : getInBeanQualifiers()) {
			if (inBeanQualifier.equals(qualifierToSelect)) {
				new DefaultTable(1).select(qualifierToSelect);
				break;
			}
		}
	}
}
