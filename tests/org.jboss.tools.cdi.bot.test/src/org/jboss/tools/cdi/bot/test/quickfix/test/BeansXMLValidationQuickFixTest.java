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

package org.jboss.tools.cdi.bot.test.quickfix.test;

import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.creator.BeanCreator;
import org.jboss.tools.cdi.bot.test.creator.StereotypeCreator;
import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.StereotypeConfiguration;
import org.jboss.tools.cdi.bot.test.quickfix.base.BeansXMLQuickFixTestBase;
import org.jboss.tools.cdi.bot.test.util.BeansXMLUtil;
import org.jboss.tools.cdi.bot.test.util.ProjectUtil;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewBeansXMLCreationWizardDialog;
import org.junit.After;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of beans.xml
 * 
 * @author Jaroslav Jankovic
 */

public class BeansXMLValidationQuickFixTest extends BeansXMLQuickFixTestBase {

	@Override
	public String getProjectName() {
		return "noBeansXML";
	}
	
	@After
	public void waitForJobs() {
		
		ProjectUtil.deleteFolder("beans.xml", getProjectName(), 
				"WebContent", "WEB-INF");
		util.waitForNonIgnoredJobs();
	}
	
	@Test
	public void testNoBeanComponent() {

		String bean = "A1";
		BeansXMLUtil.createBeansXMLWithAlternative(getProjectName(), getPackageName(), bean);
		
		resolveAddNewAlternative(bean, getPackageName());
		
		assertTrue(isBeanXMLValidationErrorEmpty());		
	}
	
	@Test
	public void testNoStereotypeAnnotation() {

		String stereotype = "S1";
		BeansXMLUtil.createBeansXMLWithStereotype(getProjectName(), getPackageName(), stereotype);
		
		resolveAddNewStereotype(stereotype, getPackageName());
		
		assertTrue(isBeanXMLValidationErrorEmpty());		
		
	}
	
	@Test
	public void testNoAlternativeBeanComponent() {

		String bean = "B1";
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(bean)).newBean();
		
		BeansXMLUtil.createBeansXMLWithAlternative(getProjectName(), getPackageName(), bean);
		
		resolveAddAlternativeToBean(bean);
		
		assertTrue(isBeanXMLValidationErrorEmpty());	
		
	}
	
	@Test
	public void testNoAlternativeStereotypeAnnotation() {

		String stereotype = "S2";
		
		new StereotypeCreator(new StereotypeConfiguration()
			.setPackageName(getPackageName())
			.setName(stereotype)).newStereotype();
		
		BeansXMLUtil.createBeansXMLWithStereotype(getProjectName(), getPackageName(), stereotype);
		
		resolveAddAlternativeToStereotype(stereotype);
		
		assertTrue(isBeanXMLValidationErrorEmpty());	
		
	}
	
	@Test
	public void testNoInterceptor() {

		String interceptor = "I1";
		BeansXMLUtil.createBeansXMLWithInterceptor(getProjectName(), 
				getPackageName(), interceptor);
		
		resolveAddNewInterceptor(interceptor, getPackageName());
		
		assertTrue(isBeanXMLValidationErrorEmpty());		
		
	}
	
	@Test
	public void testNoDecorator() {

		String decorator = "D1";
		BeansXMLUtil.createBeansXMLWithDecorator(getProjectName(), getPackageName(), decorator);
		
		resolveAddNewDecorator(decorator, getPackageName());
		
		assertTrue(isBeanXMLValidationErrorEmpty());		
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-14384
	@Test
	public void testNoBeansXmlPresent() {
		
		QuickFixUtil.performOpenQuickFix(ProblemsType.WARNINGS, 
			"Missing beans.xml file in the project");
		QuickFixUtil.useQuickFix("Create File beans.xml");
		new NewBeansXMLCreationWizardDialog().finish();
		
		assertTrue(isBeanXMLValidationErrorEmpty());
	}
	
}
