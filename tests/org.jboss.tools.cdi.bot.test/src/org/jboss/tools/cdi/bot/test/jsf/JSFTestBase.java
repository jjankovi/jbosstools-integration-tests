/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.cdi.bot.test.jsf;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.tools.cdi.bot.test.CDIConstants;
import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.annotations.JSFEnvironment;
import org.jboss.tools.cdi.bot.test.annotations.JSFTemplate;
import org.jboss.tools.cdi.bot.test.uiutils.actions.NewXHTMLFileWizard;
import org.jboss.tools.cdi.bot.test.uiutils.wizards.XHTMLDialogWizard;
import org.jboss.tools.cdi.bot.test.util.ProjectUtil;
import org.jboss.tools.cdi.reddeer.cdi.ui.RenameNamedBeanWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.RenameNamedBeanWizardFirstPage;
import org.jboss.tools.cdi.reddeer.cdi.ui.RenameNamedBeanWizardSecondPage;
import org.jboss.tools.ui.bot.ext.SWTJBTExt;
import org.jboss.tools.ui.bot.ext.Timing;
import org.jboss.tools.ui.bot.ext.helper.ContextMenuHelper;
import org.jboss.tools.ui.bot.ext.types.IDELabel;
import org.junit.Before;

public class JSFTestBase extends CDITestBase {
	
	private static final Logger LOGGER = Logger.getLogger(JSFTestBase.class.getName());
	private JSFEnvironment env = JSFEnvironment.JSF_20;
	private JSFTemplate template = JSFTemplate.BLANK_LIBS;
	protected static final String WEB_FOLDER = "pages";
	
	public JSFEnvironment getEnv() {
		return env; 
	}
	
	public JSFTemplate getTemplate() {
		return template;
	}
	
	@Before
	public void checkAndCreateProject() {
		
		if (!ProjectUtil.projectExists(getProjectName())) {
			ProjectUtil.newJSFProjectWithCDISupport(
					getProjectName(), getEnv(), getTemplate());
		}
		
	}
	
	/**
	 * Method created new XHTML page with selected name
	 * @param pageName
	 */
	protected void createXHTMLPage(String pageName) {
		XHTMLDialogWizard xhtmlWizard = new NewXHTMLFileWizard().run();
		xhtmlWizard.setDestination(getProjectName() + "/" 
				+ IDELabel.WebProjectsTree.WEB_CONTENT 
				+ "/" + WEB_FOLDER).setName(pageName).finishWithWait();
	}
	
	/**
	 * Method created new XHTML page with content of resource
	 * @param pageName
	 */
	protected void createXHTMLPageWithContent(String pageName, String resource) {
		createXHTMLPage(pageName);
		editResourceUtil.replaceClassContentByResource(JSFTestBase.class.
				getResourceAsStream(resource), false);
	}
	
	
	/**
	 * Method opens context menu for CDI Refactor for selected class
	 * @param className
	 * @throws AnnotationException if no menu for CDI Refactor was found
	 */
	protected void openContextMenuForCDIRefactor(String className) throws AnnotationException {
		String text = getNamedAnnotationForClass(className);
		if (text == null) {
			throw new AnnotationException("There is no Named " +
					"annotation in class:" + className);
		}
		String renameContextMenuText = "Rename '" + 
					parseNamedAnnotation(className, text) + 
					"' Named Bean ";
		openContextMenuForTextInEditor(text, bot.editorByTitle(className + ".java"), 
				IDELabel.Menu.CDI_REFACTOR, renameContextMenuText);
	}
	
	/**
	 * Method returns @Named annotation or null if there is no such annotation
	 * @param className
	 * @return
	 */
	private String getNamedAnnotationForClass(String className) {
		try {
			bot.editorByTitle(className + ".java").show();
		} catch (WidgetNotFoundException exc) {
			projectExplorer.openFile(getProjectName(), CDIConstants.JAVA_RESOURCES, CDIConstants.JAVA_SOURCE, 
									 getPackageName(), className);
		}
		
		SWTBotEclipseEditor activeEditor = bot.activeEditor().toTextEditor();
		for (String line : activeEditor.getLines()) {
			if (line.contains("@Named") &&
					!line.contains("//") && !line.contains("*")) {
				return line;
			}
		}
		return null;
	}

	/**
	 * Method parses @Named annotation and returns correct EL name for class
	 * @param className
	 * @param text
	 * @return
	 */
	private String parseNamedAnnotation(String className, String text) {
		if (!text.contains("\"")) {
			return className.substring(0,1).toLowerCase() + className.substring(1);
		} else {
			return text.split("\"")[1];
		}
		
	}

	/**
	 * Method opens context menu for text in eclipse editor
	 * @param text
	 * @param menu
	 */
	protected void openContextMenuForTextInEditor(final String text, 
			final SWTBotEditor editorTitle, final String... menu) {
		assert menu.length > 0;		
		editorTitle.show();
		SWTJBTExt.selectTextInSourcePane(bot, editorTitle.getTitle(), 
				text, 0, text.length());	
					
		ContextMenuHelper.clickContextMenu(editorTitle, menu);
		
	}
	
	/**
	 * Method changes @Named annotation to "newNamed" for selected class
	 * @param className
	 * @param newNamed
	 * @return all affected files by CDI refactoring
	 */
	protected List<String> changeNamedAnnotation(String className, String newNamed) {
		List<String> affectedFiles = new ArrayList<String>();
		try {
			openContextMenuForCDIRefactor(className);
			
			RenameNamedBeanWizardDialog dialog = new RenameNamedBeanWizardDialog();
			
			RenameNamedBeanWizardFirstPage firstPage = dialog.getFirstPage();
			firstPage.setNamedName(newNamed);
			
			dialog.next();
			bot.sleep(Timing.time2S());
			
			RenameNamedBeanWizardSecondPage secondPage = dialog.getSecondPage();
			affectedFiles = secondPage.getAffectedFiles();
			
			dialog.finish();
		} catch (AnnotationException exc) {
			LOGGER.info("There is no named annotation in tested class");
			fail(exc.getMessage());
		} catch (WidgetNotFoundException exc) {
			bot.activeShell().bot().button("Close").click();
		}
		return affectedFiles;
	}
				
}