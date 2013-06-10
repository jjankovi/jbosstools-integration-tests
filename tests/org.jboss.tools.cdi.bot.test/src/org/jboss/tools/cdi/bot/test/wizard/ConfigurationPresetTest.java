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


import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.util.ProjectUtil;
import org.junit.Test;

/**
* Test checks if CDI configuration preset sets CDI support correctly
* 
* @author Jaroslav Jankovic
*/
public class ConfigurationPresetTest extends CDITestBase {

	@Override	
	public void prepareWorkspace() {
		if (!ProjectUtil.projectExists(getProjectName())) {
			ProjectUtil.newDynamicWebProjectWithCDIPreset(getProjectName());
		}
	}
	
	@Override
	public String getProjectName() {
		return "CDIPresetProject";
	}
			
	@Test
	public void testCDIPreset() {
		assertTrue(ProjectUtil.checkCDISupport(getProjectName()));
	}
	
}
