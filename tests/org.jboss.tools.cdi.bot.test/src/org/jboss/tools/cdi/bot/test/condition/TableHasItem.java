package org.jboss.tools.cdi.bot.test.condition;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.table.DefaultTable;

/**
 * 
 * @author jjankovi
 *
 */
public class TableHasItem implements WaitCondition {

	private DefaultTable table;
	private String item;
	
	public TableHasItem(DefaultTable table, String item) {
		super();
		this.table = table;
		this.item = item;
	}

	public boolean test() {
		int tableRowsCount = table.rowCount();
		for (int i = 0; i < tableRowsCount; i++) {
			if (item.equals(table.cell(i, 0))) {
				return true;
			}
		}
		return false;
	}

	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

}
