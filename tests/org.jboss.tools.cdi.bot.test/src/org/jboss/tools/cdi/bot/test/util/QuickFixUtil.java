/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.cdi.bot.test.util;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.ui.ide.QuickFixDialog;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.condition.SpecificErrorExists;
import org.jboss.tools.cdi.bot.test.condition.SpecificWarningExists;
import org.jboss.tools.ui.bot.ext.types.IDELabel;

/**
 * 
 * @author jjankovi
 *
 */
public class QuickFixUtil {
	
	public static void performQuickFix(ProblemsType problemsType, 
			String problemRegex) {
		performQuickFix(problemsType, problemRegex, null);
	}
	
	public static void performQuickFix(ProblemsType problemsType, 
			String problemRegex, String fixRegex) {
		waitForProblem(problemsType, problemRegex);
		selectProblem(problemsType, problemRegex);
		openProblemQuickFixDialog(problemsType, problemRegex);
		useQuickFix(fixRegex);
		waitForProblemDisappearance(problemsType, problemRegex);
	}
	
	public static void performOpenQuickFix(ProblemsType problemsType, 
			String problemRegex) {
		waitForProblem(problemsType, problemRegex);
		selectProblem(problemsType, problemRegex);
		openProblemQuickFixDialog(problemsType, problemRegex);
	}
	
	public static void waitForProblem(ProblemsType problemsType, String problemRegex) {
		if (problemsType == ProblemsType.WARNINGS) {
			new WaitUntil(new SpecificWarningExists(problemRegex), TimePeriod.NORMAL);
		} else {
			new WaitUntil(new SpecificErrorExists(problemRegex), TimePeriod.NORMAL);
		}
	}
	
	public static void selectProblem(ProblemsType problemsType,
			String problemRegex) {
		org.jboss.reddeer.eclipse.ui.problems.ProblemsView pView = 
				new org.jboss.reddeer.eclipse.ui.problems.ProblemsView();
		List<TreeItem> problems = new ArrayList<TreeItem>();
		problems = (problemsType == ProblemsType.WARNINGS)?
			pView.getAllWarnings():
			pView.getAllErrors();
		for (TreeItem problem : problems) {
			if (problem.getText().contains(problemRegex)) {
				problem.select();
				break;
			}			
		}
	}
	
	public static void openProblemQuickFixDialog(ProblemsType problemsType,
			String problemRegex) {
		new ContextMenu(IDELabel.Menu.QUICK_FIX).select();
	}
	
	public static void useQuickFix(String fixRegex) {
		QuickFixDialog quickFixDialog = new QuickFixDialog();
		if (fixRegex == null) {
			quickFixDialog.selectFix(0);
		} else {
			quickFixDialog.selectFix(fixRegex);
		}
		quickFixDialog.selectAll();
		quickFixDialog.finish();
	}

	public static void waitForProblemDisappearance(ProblemsType problemsType,
			String problemRegex) {
		if (problemsType == ProblemsType.WARNINGS) {
			new WaitWhile(new SpecificWarningExists(problemRegex), 
				TimePeriod.NORMAL);
		} else {
			new WaitWhile(new SpecificErrorExists(problemRegex), 
				TimePeriod.NORMAL);
		}
	}
	
}
