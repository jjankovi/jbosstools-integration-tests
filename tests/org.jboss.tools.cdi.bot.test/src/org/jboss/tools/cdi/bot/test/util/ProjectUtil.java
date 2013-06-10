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

package org.jboss.tools.cdi.bot.test.util;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.keyboard.KeyboardFactory;
import org.eclipse.swtbot.swt.finder.keyboard.Keystrokes;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.tools.cdi.bot.test.CDIConstants;
import org.jboss.tools.cdi.bot.test.annotations.JSFEnvironment;
import org.jboss.tools.cdi.bot.test.annotations.JSFTemplate;
import org.jboss.tools.cdi.bot.test.uiutils.actions.NewFileWizardAction;
import org.jboss.tools.cdi.bot.test.uiutils.actions.NewJSFProjectWizard;
import org.jboss.tools.cdi.bot.test.uiutils.wizards.DynamicWebProjectWizard;
import org.jboss.tools.ui.bot.ext.SWTBotExt;
import org.jboss.tools.ui.bot.ext.SWTBotFactory;
import org.jboss.tools.ui.bot.ext.SWTUtilExt;
import org.jboss.tools.ui.bot.ext.condition.ProgressInformationShellIsActiveCondition;
import org.jboss.tools.ui.bot.ext.condition.ShellIsActiveCondition;
import org.jboss.tools.ui.bot.ext.condition.TaskDuration;
import org.jboss.tools.ui.bot.ext.helper.ContextMenuHelper;
import org.jboss.tools.ui.bot.ext.types.IDELabel;
import org.jboss.tools.ui.bot.ext.view.ExplorerBase;
import org.jboss.tools.ui.bot.ext.view.ProjectExplorer;

public class ProjectUtil {
	
	private static SWTBotExt bot = SWTBotFactory.getBot();
	private static SWTUtilExt util = SWTBotFactory.getUtil();
	private static ProjectExplorer projectExplorer = SWTBotFactory.getProjectexplorer();
	
	/**
	 * Method creates new CDI Project with CDI Web Project wizard
	 * @param projectName
	 */
	public static void newCDIProjectWithCDIWizard(String projectName) {
		
		new NewFileWizardAction().run()
			.selectTemplate(CDIConstants.CDI_GROUP, CDIConstants.CDI_WEB_PROJECT).next();
		new DynamicWebProjectWizard().setProjectName(projectName).finishWithWait();		
	}
	
	/**
	 * Method creates new CDI Project with Dynamic Web Project, after that it 
	 * adds CDI Support
	 * @param projectName
	 */
	public static void newCDIProjectWithDynamicWizard(String projectName) {
		newDynamicWebProject(projectName);
		addCDISupport(projectName);
	}
	
	/**
	 * Method creates new Dynamic Web Project with CDI Preset checked
	 * @param projectName
	 */
	public static void newDynamicWebProjectWithCDIPreset(String projectName) {
		new NewFileWizardAction().run()
				.selectTemplate(IDELabel.PreferencesDialog.JBOSS_TOOLS_WEB, 
						IDELabel.JBossCentralEditor.DYNAMIC_WEB_PROJECT).next();
		new DynamicWebProjectWizard().setProjectName(projectName).setCDIPreset().finishWithWait();
	}
	
	/**
	 * Method creates new Dynamic Web Project with CDI Facets checked
	 * @param projectName
	 */
	public static void newDynamicWebProjectWithCDIFacets(String projectName) {
		new NewFileWizardAction().run()
				.selectTemplate(IDELabel.PreferencesDialog.JBOSS_TOOLS_WEB, 
						IDELabel.JBossCentralEditor.DYNAMIC_WEB_PROJECT).next();
		new DynamicWebProjectWizard().setProjectName(projectName).setCDIFacet().finishWithWait();
	}
	
	/**
	 * Methods creates new JSF Project with selected name, environment and template. Finnaly
	 * it adds CDI support to this project.
	 * @param projectName
	 * @param env
	 * @param template
	 */
	public static void newJSFProjectWithCDISupport(String projectName, 
			JSFEnvironment env, JSFTemplate template) {
		newJSFProject(projectName, env, template);
		addCDISupport(projectName);
	}

	/**
	 * Methods creates new JSF Project with selected name, environment and template.
	 * @param projectName
	 * @param env
	 * @param template
	 */
	public static void newJSFProject(String projectName, JSFEnvironment env, 
			JSFTemplate template) {				
		new NewJSFProjectWizard().run().
			setName(projectName).
			setEnvironment(env).
			setJSFTemplate(template).		
			finish();		
		/*
		 * workaround for non Web Perspective, click No button
		 * to not change perspective to Web Perspectives
		 * 
		 */
		try {
			bot.button("No").click();
		} catch (WidgetNotFoundException exc) {
			// do nothing here
		}
		util.waitForNonIgnoredJobs();						
	}
	
	/**
	 * Methods checks if project with entered name exists in actual workspace
	 * @param projectName
	 * @return 
	 */
	public static boolean projectExists(String projectName) {
		PackageExplorer packageExplorer = new PackageExplorer();
		packageExplorer.open();
		return packageExplorer.containsProject(projectName);
	}
	
	/**
	 * Method deletes whole package with given name for entered project
	 * @param projectName
	 * @param packageName
	 */
	public static void deletePackage(String projectName, String packageName) {
		if (projectExplorer.isFilePresent(projectName, "Java Resources", "JavaSource")) {	
			String[] path = {projectName, "Java Resources", "JavaSource"};
			deleteFolder(packageName, path);
		}else {
			String[] path = {projectName, "Java Resources", "src"};
			deleteFolder(packageName, path);
		}		
	}
	
	/**
	 * Method deletes folder with given name and path
	 * @param folderName
	 * @param path
	 */
	public static void deleteFolder(String folderName, String... path) {
				
		projectExplorer.selectTreeItem(folderName, path); 				
		
		new ShellMenu(IDELabel.Menu.EDIT, IDELabel.Menu.DELETE).select();
		new DefaultShell(IDELabel.Shell.CONFIRM_DELETE);
		new PushButton(IDELabel.Button.OK).click();
		new WaitWhile(new ShellWithTextIsActive(
				IDELabel.Shell.CONFIRM_DELETE));
	}
	
	/**
	 * in explorer base View, the file which is located in "sourceFolder" 
	 * is moved to location "destFolder" 
	 * @param file
	 * @param sourceFolder
	 * @param destFolder
	 */
	public static void moveFileInExplorerBase(ExplorerBase explorerBase, 
			String file, String sourceFolder, String destFolder) {
		
		explorerBase.selectTreeItem(file, sourceFolder.split("/"));		
		
		new ShellMenu(IDELabel.Menu.FILE, IDELabel.Menu.MOVE).select();
		new DefaultShell(IDELabel.Shell.MOVE);
		TreeItem ti = new DefaultTreeItem(destFolder.split("/")[0]);
		ti.collapse();
		ti.select();
		new PushButton(IDELabel.Button.OK).click();
		new WaitWhile(new ShellWithTextIsActive(IDELabel.Shell.MOVE));
	}
	
	/**
	 * in explorer base View, the file which is located in "path" 
	 * is renamed to newFileName value 
	 * @param explorerBase
	 * @param file
	 * @param path
	 * @param newFileName
	 */
	public static void renameFileInExplorerBase(ExplorerBase explorerBase, 
			String file, String path, String newFileName) {
		explorerBase.selectTreeItem(file, path.split("/"));		
		
		new ShellMenu(IDELabel.Menu.FILE, 
				IDELabel.Menu.RENAME_WITH_DOTS).select();
		new DefaultShell(IDELabel.Shell.RENAME_RESOURCE);
		new DefaultText(0).setText(newFileName);
		new PushButton(IDELabel.Button.OK).click();
		new WaitWhile(new ShellWithTextIsActive(
				IDELabel.Shell.RENAME_RESOURCE));	
	}
	
	/**
	 * Method deletes whole web folder with given name for entered project
	 * @param projectName
	 * @param packageName
	 */
	public static void deleteWebFolder(String projectName, String folder) {
		
		String[] path = {projectName, "WebContent"};
		ProjectUtil.deleteFolder(folder, path);
		
	}
	
	/**
	 * Set system default jdk in the project
	 * @param projectName
	 */
	public static void addDefaultJDKIntoProject(String projectName) {
		
		projectExplorer.selectProject(projectName);
		bot.menu(IDELabel.Menu.FILE).menu(
				IDELabel.Menu.PROPERTIES).click();
		bot.waitForShell(IDELabel.Shell.PROPERTIES_FOR + " " + projectName);
		SWTBotShell propertiesShell = bot.shell(
				IDELabel.Shell.PROPERTIES_FOR + " " + projectName);
		propertiesShell.activate();
		SWTBotTreeItem item = bot.tree().getTreeItem(
				IDELabel.JavaBuildPathPropertiesEditor.
				JAVA_BUILD_PATH_TREE_ITEM_LABEL);
		item.select();
		bot.tabItem(IDELabel.JavaBuildPathPropertiesEditor.
				LIBRARIES_TAB_LABEL).activate();
		SWTBotTree librariesTree = bot.treeWithLabel(
				"JARs and class folders on the build path:");
		/** remove jdk currently configured on project */
		for (int i = 0; i < librariesTree.rowCount(); i++) {
			SWTBotTreeItem libraryItem = librariesTree.
					getAllItems()[i];
			if (libraryItem.getText().contains("JRE") || 
				libraryItem.getText().contains("jdk")) {
				libraryItem.select();
				break;
			}
		}
		bot.button(IDELabel.Button.REMOVE).click();
		
		/** add default jdk of system */
		bot.button(IDELabel.Button.ADD_LIBRARY).click();
		bot.waitForShell(IDELabel.Shell.ADD_LIBRARY);
		SWTBotShell libraryShell = bot.shell(
				IDELabel.Shell.ADD_LIBRARY);
		libraryShell.activate();
		bot.list().select("JRE System Library");
		bot.button(IDELabel.Button.NEXT).click();
		bot.radio(2).click();
		bot.button(IDELabel.Button.FINISH).click();
		bot.waitWhile(new ShellIsActiveCondition(libraryShell), 
				TaskDuration.LONG.getTimeout());
		bot.button(IDELabel.Button.OK).click();
		bot.waitWhile(new ShellIsActiveCondition(propertiesShell), 
				TaskDuration.LONG.getTimeout());
		util.waitForNonIgnoredJobs();
		
	}
	
	/**
	 * Method creates new Dynamic Web Project
	 * @param projectName
	 */
	public static void newDynamicWebProject(String projectName) {
		new NewFileWizardAction().run()
				.selectTemplate(IDELabel.PreferencesDialog.JBOSS_TOOLS_WEB, 
						IDELabel.JBossCentralEditor.DYNAMIC_WEB_PROJECT).next();
		new DynamicWebProjectWizard().setProjectName(projectName).finishWithWait();
	}
	
	/**
	 * Method adds CDI support to project with entered name
	 * @param projectName
	 */
	public static void addCDISupport(String projectName) {
		projectExplorer.selectProject(projectName);
		new ContextMenu(IDELabel.Menu.PACKAGE_EXPLORER_CONFIGURE, 
				CDIConstants.ADD_CDI_SUPPORT).select();
		new DefaultShell("Properties for " + projectName + " (Filtered)");
		new PushButton(IDELabel.Button.OK).click();
		bot.waitWhile(new 
				ProgressInformationShellIsActiveCondition(), 
				TaskDuration.LONG.getTimeout());
	}
	
	/**
	 * Method adds CDI support to project with entered name with Enter key
	 * @param projectName
	 */
	public static void addCDISupportWithEnterKey(String projectName) {
		projectExplorer.selectProject(projectName);
		new ContextMenu(IDELabel.Menu.PACKAGE_EXPLORER_CONFIGURE, 
				CDIConstants.ADD_CDI_SUPPORT).select();
		new DefaultShell("Properties for " + projectName + " (Filtered)");
		KeyboardFactory.getSWTKeyboard().pressShortcut(Keystrokes.CR, Keystrokes.LF);
		bot.waitWhile(new 
				ProgressInformationShellIsActiveCondition(), 
				TaskDuration.LONG.getTimeout());
	}
	
	/**
	 * Method checks if entered project has CDI support set	
	 * @param projectName
	 * @return
	 */
	public static boolean checkCDISupport(String projectName) {
		projectExplorer.selectProject(projectName);
		
		SWTBotTree tree = projectExplorer.bot().tree();
		ContextMenuHelper.prepareTreeItemForContextMenu(tree);
	    new SWTBotMenu(ContextMenuHelper.getContextMenu(
	    		tree,IDELabel.Menu.PROPERTIES,false)).click();
	    
	    bot.tree().expandNode(CDIConstants.CDI_PROPERTIES_SETTINGS).select();	    	    
		boolean isCDISupported = bot.checkBox().isChecked();
		bot.button(IDELabel.Button.CANCEL).click();
		return isCDISupported;
	}
	
}
