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
import org.jboss.tools.cdi.bot.test.creator.InterceptorCreator;
import org.jboss.tools.cdi.bot.test.creator.config.InterceptorConfiguration;
import org.jboss.tools.cdi.bot.test.creator.util.CDICreatorUtil;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of CDI Interceptor component
 * 
 * @author Jaroslav Jankovic
 */

public class InterceptorValidationQuickFixTest extends CDITestBase {
	
	private static final String VALIDATION_PROBLEM_1 = "Bean class " + 
		"of a session bean cannot be annotated @Interceptor";
	private static final String VALIDATION_PROBLEM_2 = "Interceptor " + 
		"should not have a name";
	private static final String VALIDATION_PROBLEM_3 = "Producer " + 
		"cannot be declared in an interceptor";
	private static final String VALIDATION_PROBLEM_4 = "Interceptor " + 
		"has a method annotated @Disposes";
	private static final String VALIDATION_PROBLEM_5 = "Interceptor " + 
		"cannot have a method with a parameter annotated @Observes";
	private static final String VALIDATION_PROBLEM_6 = "Interceptor " + 
		"should not be annotated @Specializes";
	
	private static final String QUICK_FIX_1 = "Delete annotation"; 
	
	// https://issues.jboss.org/browse/JBIDE-7680
	@Test
	public void testSessionAnnotation() {
			
		String className = "Interceptor1";
		
		newInterceptorWithContent(getPackageName(), className, 
			"/resources/quickfix/interceptor/InterceptorWithStateless.java.cdi");
		
		editResourceUtil.replaceInEditor("InterceptorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_1);
			
	}
	
	// https://issues.jboss.org/browse/JBIDE-7636
	@Test
	public void testNamedAnnotation() {
		
		String className = "Interceptor2";
		
		newInterceptorWithContent(getPackageName(), className, 
			"/resources/quickfix/interceptor/InterceptorWithNamed.java.cdi");
		
		editResourceUtil.replaceInEditor("InterceptorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_1);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7683
	@Test
	public void testProducer() {
		
		String className = "Interceptor3";
		
		newInterceptorWithContent(getPackageName(), className, 
			"/resources/quickfix/interceptor/InterceptorWithProducer.java.cdi");
		
		editResourceUtil.replaceInEditor("InterceptorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_3, QUICK_FIX_1);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7684
	@Test
	public void testDisposesAnnotation() {
		
		String className = "Interceptor4";
		
		newInterceptorWithContent(getPackageName(), className, 
			"/resources/quickfix/interceptor/InterceptorWithDisposes.java.cdi");
		
		editResourceUtil.replaceInEditor("InterceptorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_4, QUICK_FIX_1);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7685
	@Test
	public void testObservesAnnotation() {
		
		String className = "Interceptor5";
		
		newInterceptorWithContent(getPackageName(), className, 
			"/resources/quickfix/interceptor/InterceptorWithDisposes.java.cdi");
		
		editResourceUtil.replaceInEditor("import javax.enterprise.inject.Disposes;", 
				"import javax.enterprise.event.Observes;");
		editResourceUtil.replaceInEditor("@Disposes", "@Observes");
		editResourceUtil.replaceInEditor("InterceptorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_5, QUICK_FIX_1);
			
	}
	
	// https://issues.jboss.org/browse/JBIDE-7686
	@Test
	public void testSpecializesAnnotation() {
		
		String className = "Interceptor6";
		
		newInterceptorWithContent(getPackageName(), className, 
			"/resources/quickfix/interceptor/InterceptorWithSpecializes.java.cdi");
		
		editResourceUtil.replaceInEditor("InterceptorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_6, QUICK_FIX_1);
			
	}
	
	private void newInterceptorWithContent(String packageName, String name, String resource) {
		new InterceptorCreator(new InterceptorConfiguration()
			.setPackageName(packageName)
			.setName(name)).newInterceptor();
		CDICreatorUtil.fillContentOfEditor(name + ".java", resource);
	} 
	
}
