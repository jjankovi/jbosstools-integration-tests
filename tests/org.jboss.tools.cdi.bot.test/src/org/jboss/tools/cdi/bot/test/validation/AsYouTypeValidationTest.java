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

package org.jboss.tools.cdi.bot.test.validation;

import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.condition.AsYouTypeMarkerExistsCondition;
import org.jboss.tools.cdi.bot.test.creator.BeanCreator;
import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.util.BeansXMLUtil;
import org.junit.After;
import org.junit.Test;

/**
 * Tests as-you-type validation in java editor
 * 
 * @author jjankovi
 * 
 */
public class AsYouTypeValidationTest extends CDITestBase {

	private static final String ELIGIBLE_VALIDATION_PROBLEM = "Multiple beans are eligible " +
			"for injection to the injection point.*";
	
	private static final String BEAN_IS_NOT_ALTERNATIVE = ".*is not an alternative bean class.*";
	
	@After
	public void cleanUp() {
		bot.activeEditor().save();
	}
	
	@Test
	public void testJavaAYTValidation() {
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName("Test")).newBean();
		
		//=======================================================================
		// 	Invoke as-you-type validation marker appearance without saving file
		//=======================================================================
		
		editResourceUtil.replaceInEditor("// TODO Auto-generated constructor stub", "");
		
		new WaitWhile(new AsYouTypeMarkerExistsCondition("TODO Auto-generated constructor stub"));
		
		editResourceUtil.replaceClassContentByResource(AsYouTypeValidationTest.class.
				getResourceAsStream("/resources/validation/Test1.java.cdi"), 
				false, false, getPackageName(), "Test");
		
		new WaitUntil(new AsYouTypeMarkerExistsCondition(ELIGIBLE_VALIDATION_PROBLEM));
		
		//==========================================================================
		// 	Invoke as-you-type validation marker disappearance without saving file
		//==========================================================================
		
		editResourceUtil.replaceInEditor("@Inject ", "@Inject @Named ", false);
		new WaitWhile(new AsYouTypeMarkerExistsCondition(ELIGIBLE_VALIDATION_PROBLEM));
	}
	
	@Test
	public void testBeansXmlAYTValidation() {
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName("A1")).newBean();
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName("A2")
			.setAlternative(true)).newBean();
		
		//=======================================================================
		// 	Invoke as-you-type validation marker appearance without saving file
		//=======================================================================
		
		BeansXMLUtil.createBeansXMLWithAlternative(getProjectName(), 
				getPackageName(), "A1", false);
		
		new WaitUntil(new AsYouTypeMarkerExistsCondition(BEAN_IS_NOT_ALTERNATIVE));
		
		//==========================================================================
		// 	Invoke as-you-type validation marker disappearance without saving file
		//==========================================================================
		
		editResourceUtil.replaceInEditor("A1", "A2", false);
		
		new WaitWhile(new AsYouTypeMarkerExistsCondition(BEAN_IS_NOT_ALTERNATIVE));
	}
	
}