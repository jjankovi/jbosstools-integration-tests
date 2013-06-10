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

package org.jboss.tools.cdi.bot.test;

import java.util.logging.Logger;

import org.jboss.tools.cdi.bot.test.uiutils.EditorResourceHelper;
import org.jboss.tools.cdi.bot.test.util.ProjectUtil;
import org.jboss.tools.ui.bot.ext.RequirementAwareSuite;
import org.jboss.tools.ui.bot.ext.SWTTestExt;
import org.jboss.tools.ui.bot.ext.config.Annotations.Require;
import org.jboss.tools.ui.bot.ext.config.Annotations.Server;
import org.jboss.tools.ui.bot.ext.config.Annotations.ServerState;
import org.jboss.tools.ui.bot.ext.helper.ImportHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@Require(clearProjects = true, perspective = "Java EE", 
		 server = @Server(state = ServerState.NotRunning, 
		 version = "6.0", operator = ">="))
		 @RunWith(RequirementAwareSuite.class)
		 @SuiteClasses({ CDIAllBotTests.class })
public class CDITestBase extends SWTTestExt {
	
	private String projectName = "CDIProject";
	private String packageName = "cdi";
	
	protected static final Logger LOGGER = Logger.getLogger(CDITestBase.class.getName());
	protected static final EditorResourceHelper editResourceUtil = new EditorResourceHelper();
	
	@Before
	public void prepareWorkspace() {
		if (!ProjectUtil.projectExists(getProjectName())) {
			importCDITestProject(getProjectName());
		}
	}
	
	@After
	public void waitForJobs() {
		util.waitForNonIgnoredJobs();
	}
		
	protected String getProjectName() {
		return projectName;
	}
	
	protected String getPackageName() {
		return packageName;
	}
	
	protected static void importCDITestProject(String projectName) {
		String location = "/resources/projects/" + projectName;
		importCDITestProject(projectName, location, projectName);
	}
	
	protected static void importCDITestProject(String projectName, 
			String projectLocation, String dir) {
		
		ImportHelper.importProject(projectLocation, dir, PluginActivator.PLUGIN_ID);
				
		eclipse.addConfiguredRuntimeIntoProject(projectName, 
				configuredState.getServer().name);
		eclipse.cleanAllProjects();
	}
	
}