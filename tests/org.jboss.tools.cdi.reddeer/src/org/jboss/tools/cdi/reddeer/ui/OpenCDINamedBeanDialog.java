package org.jboss.tools.cdi.reddeer.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * 
 * @author jjankovi
 *
 */
public class OpenCDINamedBeanDialog extends DefaultShell {

	private static final String DIALOG_TITLE = "Open CDI Named Bean";
	
	public OpenCDINamedBeanDialog() {
		super(DIALOG_TITLE);
	}
	
	public void setELPrefix(String prefix) {
		new DefaultText(0).setText("");
		new DefaultText(0).setText(prefix);
	}
	
	public void selectItems(String... items) {
		new DefaultTable().select(items);
	}
	
	public List<String> getMatchingItems() {
		List<String> matchingItems = new ArrayList<String>();
		Table matchingItemsTable = new DefaultTable();
		try {
			new WaitUntil(new TableHasRows(matchingItemsTable));
		} catch (Exception te) {
			return matchingItems;
		}
		int tableItemsCount = matchingItemsTable.rowCount();
		for (int i = 0; i < tableItemsCount; i++) {
			String itemInTable = matchingItemsTable.cell(i, 0);
			if (itemInTable.contains("Workspace matches")) continue;
			matchingItems.add(itemInTable);
		}
		return matchingItems;
	}
	
	public void ok() {
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(DIALOG_TITLE));
	}
	
	public void cancel() {
		new PushButton("Cancel").click();
		new WaitWhile(new ShellWithTextIsActive(DIALOG_TITLE));
	}
	
}
