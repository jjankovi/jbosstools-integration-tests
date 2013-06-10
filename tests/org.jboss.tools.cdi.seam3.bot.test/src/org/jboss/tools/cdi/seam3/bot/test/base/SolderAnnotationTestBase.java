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

package org.jboss.tools.cdi.seam3.bot.test.base;

import org.jboss.tools.cdi.bot.test.CDIConstants;
import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.jboss.tools.ui.bot.ext.helper.OpenOnHelper;

/**
 * 
 * @author jjankovi
 *
 */
public class SolderAnnotationTestBase extends Seam3TestBase {
	
	private static final String VALIDATION_PROBLEM_1 = "No bean is eligible "
			+ "for injection to the injection point";
	private static final String VALIDATION_PROBLEM_2 = "Multiple beans are "
			+ "eligible for injection to the injection point";
	
	protected String APPLICATION_CLASS = "Application.java";
	
	/**
	 * 
	 * @param projectName
	 */
	protected void testNoBeanValidationProblemExists() {
		testBeanValidationProblemExists(
				ProblemsType.WARNINGS, VALIDATION_PROBLEM_1);
	}
	
	/**
	 * 
	 * @param projectName
	 */
	protected void testMultipleBeansValidationProblemExists() {
		testBeanValidationProblemExists(
				ProblemsType.WARNINGS, VALIDATION_PROBLEM_2);
	}
	
	/**
	 * 
	 * @param projectName
	 * @param noBeanEligible
	 */
	private void testBeanValidationProblemExists(ProblemsType problemsType, String problem) {
		QuickFixUtil.waitForProblem(problemsType, problem);
	}
	
	/**
	 * 
	 * @param projectName
	 * @param openOnString
	 * @param openedClass
	 * @param producer
	 * @param producerMethod
	 */
	protected void testProperInjectBean(String openOnString, String openedClass) {
		testProperInject(openOnString, openedClass, false, null);
	}
	
	/**
	 * 
	 * @param projectName
	 * @param openOnString
	 * @param openedClass
	 */
	protected void testProperInjectProducer(String openOnString, String openedClass, 
			String producerMethod) {
		testProperInject(openOnString, openedClass, true, producerMethod);
	}
	
	/**
	 * 
	 * @param projectName
	 * @param openOnString
	 * @param openedClass
	 * @param producer
	 * @param producerMethod
	 */
	private void testProperInject(String openOnString, String openedClass, 
			boolean producer, String producerMethod) {
		
		QuickFixUtil.waitForProblemDisappearance(ProblemsType.WARNINGS, VALIDATION_PROBLEM_1);
		QuickFixUtil.waitForProblemDisappearance(ProblemsType.WARNINGS, VALIDATION_PROBLEM_1);
		OpenOnHelper.checkOpenOnFileIsOpened(bot, APPLICATION_CLASS, 
				openOnString, CDIConstants.OPEN_INJECT_BEAN, openedClass + ".java");
		if (producer) {
			assertTrue(bot.activeEditor().toTextEditor().
					getSelection().equals(producerMethod));
		}
		
	}

}
