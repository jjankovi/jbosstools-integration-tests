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
package org.jboss.tools.cdi.seam3.bot.test.tests;

import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.jboss.tools.cdi.seam3.bot.test.base.Seam3TestBase;
import org.jboss.tools.cdi.seam3.bot.test.util.SeamLibrary;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author jjankovi
 *
 */
public class MessageLoggerAnnotationTest extends Seam3TestBase {

	private static String projectName = "messageLogger";
	private static final String VALIDATION_PROBLEM_1 = "No bean is eligible "
			+ "for injection to the injection point";
	
	@BeforeClass
	public static void setup() {
		importSeam3ProjectWithLibrary(projectName, SeamLibrary.SOLDER_3_1);
	}
	
	@Test
	public void testMessageLoggerSupport() {
		
		/* test there is not any validation error */
		assertMessageLoggerIsInjectable();
		
	}
	
	private void assertMessageLoggerIsInjectable() {
		QuickFixUtil.waitForProblem(ProblemsType.WARNINGS, VALIDATION_PROBLEM_1);
	}
	
}
