/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.cdi.bot.test.quickfix.base;

import java.util.List;

import org.jboss.reddeer.eclipse.ui.ide.QuickFixDialog;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.condition.TableHasItem;
import org.jboss.tools.cdi.bot.test.quickfix.injection.QualifierOperation;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.jboss.tools.cdi.reddeer.cdi.ui.AddQualifiersToBeanWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.AddQualifiersToBeanWizardDialogFirstPage;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewQualifierWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewQualifierWizardPage;

public class EligibleInjectionQuickFixTestBase extends CDITestBase {
	
	/**
	 * Method resolves multiple bean injection problem. By setting class which
	 * should be more qualified and qualifier name it resolves this problem.
	 * If qualifier doesn't exist, by using qualifier wizard it creates the new
	 * one and uses it to resolve problem
	 * @param classToQualify
	 * @param qualifier
	 */
	public void resolveMultipleBeans(ProblemsType problemsType, String problem, 
			String classToQualify, String qualifier, QualifierOperation operation) {
		String qualifierFullName = qualifier + " - " + getPackageName();
		QuickFixUtil.performOpenQuickFix(problemsType, problem);
		QuickFixDialog quickFixDialog = new QuickFixDialog();
		for (String availableFix : quickFixDialog.getAvailableFixes()) {
			if (availableFix.contains(classToQualify)) {
				quickFixDialog.selectFix(availableFix);
				quickFixDialog.setResource(quickFixDialog.getResources().get(0));
				quickFixDialog.finish();
				break;
			}
		}
	
		AddQualifiersToBeanWizardDialog dialog = new AddQualifiersToBeanWizardDialog();
		AddQualifiersToBeanWizardDialogFirstPage page = dialog.getFirstPage();
		if (operation == QualifierOperation.ADD) {
			if (!qualifierIsPresent(qualifierFullName, 
					page.getAvailableQualifiers())) {
				page.createNewQualifier();
				NewQualifierWizardDialog qualifierDialog = new NewQualifierWizardDialog();
				NewQualifierWizardPage qualifierPage = qualifierDialog.getFirstPage();
				qualifierPage.setName(qualifier);
					qualifierPage.setPackage(getPackageName());
				quickFixDialog.finish();
				new AddQualifiersToBeanWizardDialog();
				new WaitUntil(new TableHasItem(new DefaultTable(0), 
						qualifierFullName));
			}
			page.selectAvailableQualifier(qualifierFullName);
			page.add();
			
		} else {
			if (qualifierIsPresent(qualifier + " - " + getPackageName(), 
					page.getInBeanQualifiers())) {
				page.selectInBeanQualifier(qualifier + " - " + getPackageName());
				page.remove();
			}
		}
		dialog.finish();
	}
	
	private boolean qualifierIsPresent(String qualifier, List<String> qualifiers) {
		for (String availableQualifier : qualifiers) {
			if (availableQualifier.equals(qualifier)) {
				return true;
			}
		}
		return false;
	}

}
