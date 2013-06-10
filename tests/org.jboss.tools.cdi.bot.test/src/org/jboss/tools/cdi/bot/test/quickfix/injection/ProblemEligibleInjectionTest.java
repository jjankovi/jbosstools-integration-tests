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

package org.jboss.tools.cdi.bot.test.quickfix.injection;

import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.creator.BeanCreator;
import org.jboss.tools.cdi.bot.test.creator.QualifierCreator;
import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.QualifierConfiguration;
import org.jboss.tools.cdi.bot.test.creator.util.CDICreatorUtil;
import org.jboss.tools.cdi.bot.test.quickfix.base.EligibleInjectionQuickFixTestBase;
import org.jboss.tools.cdi.bot.test.util.ProjectUtil;
import org.junit.After;
import org.junit.Test;

/**
 * Test checks if Quick Fix provides useful operations when 
 * ambiguous injection points
 * @author jjankovi
 *
 */

public class ProblemEligibleInjectionTest extends EligibleInjectionQuickFixTestBase {
	
	private static final String ANIMAL = "Animal";
	private static final String DOG = "Dog";
	private static final String BROKEN_FARM = "BrokenFarm";
	private static final String QUALIFIER = "Q1";
	
	private static final String VALIDATION_PROBLEM_1 = "Multiple beans are "
			+ "eligible for injection to the injection point";
	private static final String VALIDATION_PROBLEM_2 = "No bean is eligible "
			+ "for injection to the injection point";
	
	@After
	public void waitForJobs() {
		ProjectUtil.deletePackage(getProjectName(), getPackageName());		
		util.waitForNonIgnoredJobs();
	}
	
	@Test
	public void testMultipleBeansAddingExistingQualifier() {
		
		newQualifier();
		prepareCDIComponents1();
		resolveMultipleBeans(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, DOG, QUALIFIER, 
				QualifierOperation.ADD);

		bot.editorByTitle(BROKEN_FARM + ".java").show();		
		String code = bot.activeEditor().toTextEditor().getText();
		assertTrue(code.contains("@Inject @" + QUALIFIER));
		code = bot.editorByTitle(DOG + ".java").toTextEditor().getText();
		assertTrue(code.contains("@" + QUALIFIER));
	}
	
	@Test
	public void testMultipleBeansAddingNonExistingQualifier() {

		prepareCDIComponents1();
		resolveMultipleBeans(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, DOG, QUALIFIER, 
				QualifierOperation.ADD);

		bot.editorByTitle(BROKEN_FARM + ".java").show();
		String code = bot.activeEditor().toTextEditor().getText();
		assertTrue(code.contains("@Inject @" + QUALIFIER));
		code = bot.editorByTitle(DOG + ".java").toTextEditor().getText();
		assertTrue(code.contains("@" + QUALIFIER));
	}
	
	@Test
	public void testMultipleBeansRemovingExistingQualifier() {

		newQualifier();
		prepareCDIComponents2();
		resolveMultipleBeans(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, DOG, QUALIFIER, 
				QualifierOperation.REMOVE);
		
		bot.editorByTitle(BROKEN_FARM + ".java").show();
		String code = bot.activeEditor().toTextEditor().getText();
		assertTrue(code.contains("@Inject private") || code.contains("@Inject  private"));
		code = bot.editorByTitle(DOG + ".java").toTextEditor().getText();
		assertTrue(!code.contains("@" + QUALIFIER));
	}
	
	@Test
	public void testNoBeanEligibleAddingExistingQualifier() {

		newQualifier();
		prepareCDIComponents3();
		resolveMultipleBeans(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, DOG, QUALIFIER, 
				QualifierOperation.ADD);

		bot.editorByTitle(BROKEN_FARM + ".java").show();
		String code = bot.activeEditor().toTextEditor().getText();
		assertTrue(code.contains("@Inject @" + QUALIFIER));
		code = bot.editorByTitle(DOG + ".java").toTextEditor().getText();
		assertTrue(code.contains("@" + QUALIFIER));
		
	}
	
	@Test
	public void testNoBeanEligibleRemovingExistingQualifier() {
		
		newQualifier();
		prepareCDIComponents4();
		resolveMultipleBeans(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, DOG, QUALIFIER, 
				QualifierOperation.REMOVE);

		bot.editorByTitle(BROKEN_FARM + ".java").show();
		String code = bot.activeEditor().toTextEditor().getText();
		assertTrue(code.contains("@Inject private") || code.contains("@Inject  private"));
		code = bot.editorByTitle(DOG + ".java").toTextEditor().getText();
		assertFalse(code.contains("@" + QUALIFIER));
		
	}
	
	private void newQualifier() {
		new QualifierCreator(new QualifierConfiguration()
		.setPackageName(getPackageName())
		.setName(QUALIFIER)).newQualifier();
	}
	
	private void prepareCDIComponents1() {
		
		prepareCDIComponents(
				"/resources/quickfix/injection/Dog.java.cdi",
				null,
				"/resources/quickfix/injection/BrokenFarm.java.cdi");
		
	}
	
	private void prepareCDIComponents2() {
		
		prepareCDIComponents(
				"/resources/quickfix/injection/DogWithQualifier.java.cdi",
				"/resources/quickfix/injection/AnimalWithQualifier.java.cdi",
				"/resources/quickfix/injection/BrokenFarmWithQualifier.java.cdi");
		
	}
	
	private void prepareCDIComponents3() {
		
		prepareCDIComponents(
				"/resources/quickfix/injection/Dog.java.cdi",
				null,
				"/resources/quickfix/injection/BrokenFarmWithQualifier.java.cdi");
		
	}
	
	private void prepareCDIComponents4() {
		
		prepareCDIComponents(
				"/resources/quickfix/injection/DogWithQualifier.java.cdi",
				"/resources/quickfix/injection/AnimalWithQualifier.java.cdi",
				"/resources/quickfix/injection/BrokenFarm.java.cdi");
		
	}
	
	private void prepareCDIComponents(String dogResource, 
			String animalResource, String brokenFarmResource) {
		BeanConfiguration beanConfig = new BeanConfiguration();
		beanConfig.setPackageName(getPackageName());
		
		beanConfig.setName(DOG);
		new BeanCreator(beanConfig).newBean();
		CDICreatorUtil.fillContentOfEditor(DOG + ".java", dogResource);
		
		beanConfig.setName(ANIMAL);
		new BeanCreator(beanConfig).newBean();
		CDICreatorUtil.fillContentOfEditor(ANIMAL + ".java", animalResource);
				
		beanConfig.setName(BROKEN_FARM);
		new BeanCreator(beanConfig).newBean();
		CDICreatorUtil.fillContentOfEditor(BROKEN_FARM + ".java", brokenFarmResource);
		
	}

}
