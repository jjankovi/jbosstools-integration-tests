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


import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.creator.InterceptorBindingCreator;
import org.jboss.tools.cdi.bot.test.creator.config.InterceptorBindingConfiguration;
import org.jboss.tools.cdi.bot.test.creator.util.CDICreatorUtil;
import org.jboss.tools.cdi.bot.test.util.AnnotationCreator;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of CDI Interceptor Binding component
 * 
 * @author Jaroslav Jankovic
 */

public class IBindingValidationQuickFixTest extends CDITestBase {
	
	private final static String VALIDATION_PROBLEM_1 = "Annotation-valued " + 
		"member of an interceptor binding type should be annotated @Nonbinding";
	private final static String VALIDATION_PROBLEM_2 = "Array-valued member of " + 
		"an interceptor binding type must be annotated @Nonbinding";
	private final static String QUICK_FIX = "Add annotation @Nonbinding to method";
			
	
	// https://issues.jboss.org/browse/JBIDE-7641
	@Test
	public void testNonbindingAnnotation() {
		
		String className = "IBinding1";
			
		AnnotationCreator.newAnnotation("AAnnotation", getPackageName());
		new InterceptorBindingCreator(
			new InterceptorBindingConfiguration()
				.setPackageName(getPackageName())
				.setName(className)).newInterceptorBinding();
		CDICreatorUtil.fillContentOfEditor(className + ".java", 
			"/resources/quickfix/interceptorBinding/IBindingWithAnnotation.java.cdi");
		
		editResourceUtil.replaceInEditor("IBindingComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, QUICK_FIX);
		
		editResourceUtil.replaceClassContentByResource(IBindingValidationQuickFixTest.class
			.getResourceAsStream("/resources/quickfix/interceptorBinding/IBindingWithStringArray.java.cdi"), 
			false);
		editResourceUtil.replaceInEditor("IBindingComponent", className);
			
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX);
	}
	
}
