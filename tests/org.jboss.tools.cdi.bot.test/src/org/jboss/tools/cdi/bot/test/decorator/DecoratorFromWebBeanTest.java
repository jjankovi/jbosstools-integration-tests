/*******************************************************************************
 * Copyright (c) 2010-2012 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.cdi.bot.test.decorator;

import java.util.Arrays;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.jboss.tools.cdi.bot.test.CDIConstants;
import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.editor.BeansEditor;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewDecoratorCreationWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewDecoratorCreationWizardPage;
import org.junit.After;
import org.junit.Test;

/**
 * Test operates on creating new decorator from web bean 
 * implementing some existing interface
 * 
 * @author Jaroslav Jankovic
 * 
 */

public class DecoratorFromWebBeanTest extends CDITestBase {
	
	private static final String ACCOUNT = "Account";
	
	private static final String ACCOUNT_JAVA = ACCOUNT + ".java";
	
	private static final String ACCOUNT_DECORATOR = "AccountDecorator";
	
	private static final String ACCOUNT_DECORATOR_JAVA = ACCOUNT_DECORATOR + ".java";
	
	@Override
	public String getProjectName() {
		return "DecoratorFromWebBean";
	}

	@After
	public void cleanUp() {
		projectExplorer.deleteAllProjects();
	}
	
	@Test
	public void testCreatingDecoratorWithMenu() {
		
		String[] path = (getProjectName() + "/" + CDIConstants.SRC
				+ "/" + getPackageName()).split("/");
		
		packageExplorer.show();
		packageExplorer.selectTreeItem(ACCOUNT_JAVA, path);
		
		NewDecoratorCreationWizardDialog decoratorDialog = 
				new NewDecoratorCreationWizardDialog();
		decoratorDialog.open();
		NewDecoratorCreationWizardPage decoratorPage = 
				decoratorDialog.getFirstPage();
		
		
		assertTrue(decoratorPage.getName().equals(ACCOUNT_DECORATOR));
		
		assertTrue(decoratorPage.getDecoratedInterfaces().size() == 1);
		
		assertTrue(decoratorPage.getDecoratedInterfaces().
				get(0).equals(getPackageName() + "." + ACCOUNT));
		
		decoratorDialog.finish();
		
		packageExplorer.openFile(getProjectName(), 
				CDIConstants.WEB_INF_BEANS_XML_PATH.split("/"));
		
		SWTBotEditor editor = new SWTWorkbenchBot().activeEditor();
		BeansEditor be = new BeansEditor(editor.getReference(), new SWTWorkbenchBot());
		be.activatePage("Source");
		SWTBotEclipseEditor activeEditor = bot.activeEditor().toTextEditor(); 
		
		assertTrue(activeEditor.getText().contains("\n <decorators>\n  " +
				"<class>cdi.AccountDecorator</class>\n </decorators>"));
		
		activeEditor = packageExplorer.openFile(getProjectName(), CDIConstants.SRC, 
				getPackageName(), ACCOUNT_DECORATOR_JAVA).toTextEditor();
		
		assertTrue(activeEditor.getText().contains("@Decorator"));
		assertTrue(activeEditor.getText().contains("@Inject\n\t@Delegate\n\t@Any" +
				"\n\tprivate Account account;"));
		assertTrue(activeEditor.getText().contains("BigDecimal getBalance()"));
		assertTrue(activeEditor.getText().contains("User getOwner()"));
		assertTrue(activeEditor.getText().contains("void withdraw(BigDecimal amount)"));
		assertTrue(activeEditor.getText().contains("void deposit(BigDecimal amount)"));
		
		
	}
	
	@Test
	public void testCreatingDecoratorWithWizard() {
		
		NewDecoratorCreationWizardDialog decoratorDialog = 
				new NewDecoratorCreationWizardDialog();
		decoratorDialog.open();
		NewDecoratorCreationWizardPage decoratorPage = 
				decoratorDialog.getFirstPage();
		
		decoratorPage.setName(ACCOUNT_DECORATOR);
		decoratorPage.setPackage(getPackageName());
		decoratorPage.setDecoratedType(Arrays.asList(
				getPackageName() + "." + ACCOUNT));
		
		decoratorDialog.finish();
		
		packageExplorer.openFile(getProjectName(), 
				CDIConstants.WEB_INF_BEANS_XML_PATH.split("/"));
		
		SWTBotEditor editor = new SWTWorkbenchBot().activeEditor();
		BeansEditor be = new BeansEditor(editor.getReference(), new SWTWorkbenchBot());
		be.activatePage("Source");
		SWTBotEclipseEditor activeEditor = bot.activeEditor().toTextEditor();

		assertTrue(activeEditor.getText().contains("\n <decorators>\n  " +
				"<class>cdi.AccountDecorator</class>\n </decorators>"));
		
		activeEditor = packageExplorer.openFile(getProjectName(), CDIConstants.SRC, 
				getPackageName(), ACCOUNT_DECORATOR_JAVA).toTextEditor();
		
		assertTrue(activeEditor.getText().contains("@Decorator"));
		assertTrue(activeEditor.getText().contains("@Inject\n\t@Delegate\n\t@Any" +
				"\n\tprivate Account account;"));
		assertTrue(activeEditor.getText().contains("BigDecimal getBalance()"));
		assertTrue(activeEditor.getText().contains("User getOwner()"));
		assertTrue(activeEditor.getText().contains("void withdraw(BigDecimal amount)"));
		assertTrue(activeEditor.getText().contains("void deposit(BigDecimal amount)"));
		
	}

}
