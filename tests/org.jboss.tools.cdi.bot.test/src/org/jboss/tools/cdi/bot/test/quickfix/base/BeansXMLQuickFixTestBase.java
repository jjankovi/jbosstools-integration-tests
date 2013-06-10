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

import java.util.Arrays;

import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewBeanWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewBeanWizardPage;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewDecoratorCreationWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewDecoratorCreationWizardPage;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewInterceptorWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewInterceptorWizardPage;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewStereotypeWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewStereotypeWizardPage;
import org.jboss.tools.ui.bot.ext.view.ProblemsView;

public class BeansXMLQuickFixTestBase extends CDITestBase {

	private static final String VALIDATION_PROBLEM_1 = 
			"There is no class";
	private static final String VALIDATION_PROBLEM_2 = 
			"There is no annotation";
	private static final String VALIDATION_PROBLEM_3 = 
			"is not an alternative bean class";
	private static final String VALIDATION_PROBLEM_4 = 
			"is not @Alternative stereotype annotation";
	
	/**
	 * Method checks if there is no beans.xml validation error
	 * @return
	 */
	public boolean isBeanXMLValidationErrorEmpty() {
		return ProblemsView.getFilteredErrorsTreeItems(bot, null, "/" + getProjectName(), 
				"beans.xml", "CDI Problem").length == 0;
	}
	
	/**
	 * Method resolves validation error where there is no such Alternative as 
	 * configured in beans.xml. It opens quick fix and through finishWithWait button
	 * the Bean Wizard dialog is opened where both parameters are used to create
	 * the new alternative bean
	 * @param name
	 * @param pkg
	 */
	public void resolveAddNewAlternative(String name, String pkg) {
		
		QuickFixUtil.performOpenQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_1);
		QuickFixUtil.useQuickFix("Create");
		
		NewBeanWizardDialog dialog = new NewBeanWizardDialog();
		
		NewBeanWizardPage page = dialog.getFirstPage();
		page.setName(name);
		page.setPackage(pkg);
		
		dialog.finish();
		
	}
	
	/**
	 * Method resolves validation error where there is no such Stereotype as 
	 * configured in beans.xml. It opens quick fix and through finishWithWait button
	 * the Stereotype Wizard dialog is opened where both parameters are used to create
	 * the new stereotype annotation
	 * @param name
	 * @param pkg
	 */
	public void resolveAddNewStereotype(String name, String pkg) {
		
		QuickFixUtil.performOpenQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_2);
		QuickFixUtil.useQuickFix("Create");
		
		NewStereotypeWizardDialog dialog = new NewStereotypeWizardDialog();
		
		NewStereotypeWizardPage page = dialog.getFirstPage();
		page.setName(name);
		page.setPackage(pkg);
		
		dialog.finish();
		
	}
	
	/**
	 * Method resolves validation error where there is no such decorator as 
	 * configured in beans.xml. It opens quick fix and through finishWithWait button
	 * the Decorator Wizard dialog is opened where both parameters are used to create
	 * the new decorator. Interface "java.util.List" is automatically used. 
	 * @param name
	 * @param pkg
	 */
	public void resolveAddNewDecorator(String name, String pkg) {
		
		QuickFixUtil.performOpenQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_1);
		QuickFixUtil.useQuickFix("Create");
		
		NewDecoratorCreationWizardDialog dialog = new NewDecoratorCreationWizardDialog();
		
		NewDecoratorCreationWizardPage page = dialog.getFirstPage();
		page.setName(name);
		page.setPackage(pkg);
		page.setDecoratedType(Arrays.asList("java.util.List"));
		
		dialog.finish();
		
	}
	
	/**
	 * Method resolves validation error where there is no such Interceptor as 
	 * configured in beans.xml. It opens quick fix and through finishWithWait button
	 * the Interceptor Wizard dialog is opened where both parameters are used to create
	 * the new Interceptor
	 * @param name
	 * @param pkg
	 */
	public void resolveAddNewInterceptor(String name, String pkg) {
		
		QuickFixUtil.performOpenQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_1);
		QuickFixUtil.useQuickFix("Create");
		
		NewInterceptorWizardDialog dialog = new NewInterceptorWizardDialog();
		
		NewInterceptorWizardPage page = dialog.getFirstPage();
		page.setName(name);
		page.setPackage(pkg);
		
		dialog.finish();
		
	}
	
	/**
	 * Method corrects Bean which has no @Alternative annotation in it 
	 * by adding these parameter.
	 * @param name
	 * @param pkg
	 */
	public void resolveAddAlternativeToBean(String name) {
		
		QuickFixUtil.performOpenQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_3);
		QuickFixUtil.useQuickFix("Add");
		
		String content = bot.editorByTitle(name + ".java").toTextEditor().getText();
		assertTrue(content.contains("@Alternative"));
		
	}
	
	/**
	 * Method corrects Stereotype which has no @Alternative annotation in it 
	 * by adding these parameter.
	 * @param name
	 * @param pkg
	 */
	public void resolveAddAlternativeToStereotype(String name) {
		
		QuickFixUtil.performOpenQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_4);
		QuickFixUtil.useQuickFix("Add");
		
		String content = bot.editorByTitle(name + ".java").toTextEditor().getText();
		assertTrue(content.contains("@Alternative"));
		
	}
	
}
