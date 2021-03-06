/*******************************************************************************
 * Copyright (c) 2010-2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.archives.ui.bot.test;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.ShellTreeItem;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.tools.archives.reddeer.archives.ui.MainPreferencePage;
import org.jboss.tools.ui.bot.ext.types.IDELabel;
import org.junit.Test;

/**
 * 
 * @author jjankovi
 *
 */
public class ArchivePreferencesTest extends ArchivesTestBase {

	private static String projectName = "prj";
	
	@Test
	public void testArchivePreferences() {
		testGlobalArchivePreferences();
		testLocalArchivePreferences();
	}

	private void testGlobalArchivePreferences() {
		MainPreferencePage archivesPreferencePage = new MainPreferencePage();
		archivesPreferencePage.open();
		checkAllSettingsInArchivePreferencePage(archivesPreferencePage);
		archivesPreferencePage.ok();
	
	}

	private void testLocalArchivePreferences() {
		createJavaProject(projectName);
		addArchivesSupport(projectName);
		openLocalArchivePreferences(projectName);
		openGlobalPreferencesFromLocal(projectName);
	}
	
	private void openLocalArchivePreferences(String projectName) {
		openPropertiesForProject(projectName);
		
		String projectProperties = "Properties for " + projectName;
		new WaitUntil(new ShellWithTextIsActive(projectProperties));
		new DefaultShell(projectProperties);
		new ShellTreeItem("Project Archives").select();
	}
	
	private void openPropertiesForProject(String projectName) {
		PackageExplorer packageExplorer = new PackageExplorer();
		packageExplorer.open();
		
		packageExplorer.selectProject(projectName);
		new ContextMenu(IDELabel.Menu.PROPERTIES).select();
	}
	
	private void openGlobalPreferencesFromLocal(String projectName) {
		bot.link().click();
		new WaitUntil(new ShellWithTextIsActive(IDELabel.Shell.PREFERENCES_FILTERED));
		new DefaultShell(IDELabel.Shell.PREFERENCES_FILTERED);
		try {
			bot.clabel("Project Archives");
			new PushButton(IDELabel.Button.CANCEL).click();
			new WaitWhile(new ShellWithTextIsActive(IDELabel.Shell.PREFERENCES_FILTERED));
			new DefaultShell("Properties for " + projectName);
			new PushButton(IDELabel.Button.CANCEL).click();
			new WaitWhile(new ShellWithTextIsActive("Properties for " + projectName));
		}catch (WidgetNotFoundException wnfe) {
			fail("Archive global preferences page was not invoked");
		}
	}

	private void checkAllSettingsInArchivePreferencePage(
			MainPreferencePage archivesPreferencePage) {
		archivesPreferencePage.enableDefaultExcludes(
				archivesPreferencePage.isIncrementalBuilderEnabled());
		
		archivesPreferencePage.showBuildErrorDialog(
				archivesPreferencePage.isBuildErrorDialogShown());
		
		archivesPreferencePage.showOutputPathNextToPackages(
				archivesPreferencePage.isOutputPathNextToPackagesShown());
		
		archivesPreferencePage.showRootDirectoryOfFilesets(
				archivesPreferencePage.isRootDirectoryOfFilesetsShown());
		
		archivesPreferencePage.showProjectAtTheRoot(
				archivesPreferencePage.isProjectAtTheRootShown());
		
		archivesPreferencePage.showAllProjectsThatContainPackages(
				archivesPreferencePage.areAllProjectsThatContainPackagesShown());
		
		archivesPreferencePage.showNodeInAllProjects(
				archivesPreferencePage.isNodeInAllProjectShown());
		
		archivesPreferencePage.enableDefaultExcludes(
				archivesPreferencePage.isDefaultExcludesEnabled());
	}
	
}
