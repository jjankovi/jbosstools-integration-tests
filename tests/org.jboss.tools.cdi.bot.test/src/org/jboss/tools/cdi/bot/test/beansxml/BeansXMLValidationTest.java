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

package org.jboss.tools.cdi.bot.test.beansxml;

import java.util.ArrayList;
import java.util.List;

import org.jboss.tools.cdi.bot.test.CDIConstants;
import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.creator.BeanCreator;
import org.jboss.tools.cdi.bot.test.creator.DecoratorCreator;
import org.jboss.tools.cdi.bot.test.creator.InterceptorCreator;
import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.DecoratorConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.InterceptorConfiguration;
import org.jboss.tools.cdi.bot.test.quickfix.base.BeansXMLQuickFixTestBase;
import org.jboss.tools.cdi.bot.test.util.BeansXMLUtil;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test operates on beans validation in beans.xml 
 * 
 * @author Jaroslav Jankovic
 * 
 */
public class BeansXMLValidationTest extends BeansXMLQuickFixTestBase {

	private static final String someBean = "Bean1";
	private static final String nonExistingPackage = "somePackage";
	private static final String VALIDATION_PROBLEM_1 = "There is no class";
	private static final String VALIDATION_PROBLEM_2 = "is not an interceptor class";
	private static final String VALIDATION_PROBLEM_3 = "is not a decorator bean class";
	private static final String VALIDATION_PROBLEM_4 = "is not an alternative bean class";
	
	@BeforeClass
	public static void setup() {
		problems.show();		
	}
	
	@Test
	public void testEmptyBeansXMLValidation() {
		
		BeansXMLUtil.createEmptyBeansXML(getProjectName());		
		assertTrue(isBeanXMLValidationErrorEmpty());
		
	}
	
	@Test
	public void testInterceptorsValidation() {
		
		String className = "I1";
		
		if (!projectExplorer.isFilePresent(getProjectName(),  
				(CDIConstants.JAVA_RESOURCES_SRC_FOLDER + getPackageName() + 
				"/" + someBean + ".java").split("/"))) {
			new BeanCreator(new BeanConfiguration()
				.setPackageName(getPackageName())
				.setName(someBean)).newBean();
		}
		
		new InterceptorCreator(new InterceptorConfiguration()
			.setPackageName(getPackageName())
			.setName(className)).newInterceptor();
		
		BeansXMLUtil.createBeansXMLWithInterceptor(
				getProjectName(), getPackageName(), className);
		assertTrue(isBeanXMLValidationErrorEmpty());
		
		BeansXMLUtil.createBeansXMLWithInterceptor(
				getProjectName(), nonExistingPackage, className);
		QuickFixUtil.waitForProblem(ProblemsType.ERRORS, VALIDATION_PROBLEM_1);
		
		BeansXMLUtil.createBeansXMLWithInterceptor(
				getProjectName(), getPackageName(), someBean);
		QuickFixUtil.waitForProblem(ProblemsType.ERRORS, VALIDATION_PROBLEM_2);
		
	}
	
	@Test
	public void testDecoratorsValidation() {
		
		String className = "D1";
		
		if (!projectExplorer.isFilePresent(getProjectName(),  
				(CDIConstants.JAVA_RESOURCES_SRC_FOLDER + getPackageName() + 
				"/" + someBean + ".java").split("/"))) {
			new BeanCreator(new BeanConfiguration()
				.setPackageName(getPackageName())
				.setName(someBean)).newBean();
		}
		
		List<String> interfaces = new ArrayList<String>();
		interfaces.add("java.util.Set");
		new DecoratorCreator(new DecoratorConfiguration()
			.setPackageName(getPackageName())
			.setName(className)
			.setDecoratedInterfaces(interfaces)).newDecorator();
		
		BeansXMLUtil.createBeansXMLWithDecorator(getProjectName(), getPackageName(), className);
		assertTrue(isBeanXMLValidationErrorEmpty());
		
		BeansXMLUtil.createBeansXMLWithDecorator(getProjectName(), nonExistingPackage, className);
		QuickFixUtil.waitForProblem(ProblemsType.ERRORS, VALIDATION_PROBLEM_1);
		
		BeansXMLUtil.createBeansXMLWithDecorator(getProjectName(), getPackageName(), someBean);
		QuickFixUtil.waitForProblem(ProblemsType.ERRORS, VALIDATION_PROBLEM_3);
		
	}
	
	@Test
	public void testAlternativesValidation() {
		
		String className = "A1";
		
		if (!projectExplorer.isFilePresent(getProjectName(),  
				(CDIConstants.JAVA_RESOURCES_SRC_FOLDER + getPackageName() + 
				"/" + someBean + ".java").split("/"))) {
			new BeanCreator(new BeanConfiguration()
				.setPackageName(getPackageName())
				.setName(someBean)).newBean();
		}
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(className)
			.setAlternative(true)).newBean();
		
		BeansXMLUtil.createBeansXMLWithAlternative(getProjectName(), getPackageName(), className);
		assertTrue(isBeanXMLValidationErrorEmpty());
		
		BeansXMLUtil.createBeansXMLWithAlternative(getProjectName(), nonExistingPackage, className);
		QuickFixUtil.waitForProblem(ProblemsType.ERRORS, VALIDATION_PROBLEM_1);
		
		BeansXMLUtil.createBeansXMLWithAlternative(getProjectName(), getPackageName(), someBean);
		QuickFixUtil.waitForProblem(ProblemsType.ERRORS, VALIDATION_PROBLEM_4);
		
	}
	
}