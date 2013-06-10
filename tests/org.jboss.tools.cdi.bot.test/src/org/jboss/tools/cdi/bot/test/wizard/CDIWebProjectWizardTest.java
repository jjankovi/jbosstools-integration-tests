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

package org.jboss.tools.cdi.bot.test.wizard;

import org.jboss.tools.cdi.bot.test.CDIConstants;
import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.util.ProjectUtil;
import org.junit.Test;

public class CDIWebProjectWizardTest extends CDITestBase {

	@Override	
	public void prepareWorkspace() {
		if (!ProjectUtil.projectExists(getProjectName())) {
			ProjectUtil.newCDIProjectWithCDIWizard(getProjectName());
		}
	}
	
	@Override
	public String getProjectName() {
		return "CDIWebProject";
	}
	
	@Test
	public void testCDIWizard() {
		assertTrue(ProjectUtil.checkCDISupport(getProjectName()));
		assertTrue("Error: beans.xml should be created when using CDI Web Project wizard", 
			packageExplorer.isFilePresent(getProjectName(),
			CDIConstants.WEB_INF_BEANS_XML_PATH.split("/")));
	}
	
}