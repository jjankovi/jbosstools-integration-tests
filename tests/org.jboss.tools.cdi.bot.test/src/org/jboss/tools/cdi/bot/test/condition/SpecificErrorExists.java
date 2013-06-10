package org.jboss.tools.cdi.bot.test.condition;

import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * 
 * @author jjankovi
 *
 */
public class SpecificErrorExists implements WaitCondition {

	private ProblemsView problemsView;
	
	private String pattern;
	
	public SpecificErrorExists(String pattern) {
		this.pattern = pattern;
	}

	public boolean test() {
		problemsView = new ProblemsView();
		problemsView.open();
		for (TreeItem error : problemsView.getAllErrors()) {
			if (error.getText().contains(pattern)) {
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
