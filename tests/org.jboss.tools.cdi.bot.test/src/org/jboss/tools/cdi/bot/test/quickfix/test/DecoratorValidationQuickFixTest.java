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


import java.util.ArrayList;
import java.util.List;

import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.annotations.ProblemsType;
import org.jboss.tools.cdi.bot.test.creator.DecoratorCreator;
import org.jboss.tools.cdi.bot.test.creator.config.DecoratorConfiguration;
import org.jboss.tools.cdi.bot.test.creator.util.CDICreatorUtil;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of CDI Decorator component
 * 
 * @author Jaroslav Jankovic
 */

public class DecoratorValidationQuickFixTest extends CDITestBase {
	
	private static final String VALIDATION_PROBLEM_1 = "Bean class of " + 
		"a session bean cannot be annotated @Decorator";
	private static final String VALIDATION_PROBLEM_2 = "Decorator " + 
		"should not have a name";
	private static final String VALIDATION_PROBLEM_3 = "Producer " + 
		"cannot be declared in a decorator";
	private static final String VALIDATION_PROBLEM_4 = "Decorator " + 
		"has a method annotated @Disposes";
	private static final String VALIDATION_PROBLEM_5 = "Decorator " + 
		"cannot have a method with a parameter annotated @Observes";
	private static final String VALIDATION_PROBLEM_6 = "Decorator " + 
		"should not be annotated @Specializes";
	
	private static final String QUICK_FIX_1 = "Delete annotation"; 
	
	// https://issues.jboss.org/browse/JBIDE-7680
	@Test
	public void testSessionAnnotation() {
			
		String className = "Decorator1";
		
		newDecoratorWithContent(getPackageName(), className, 
			"/resources/quickfix/decorator/DecoratorWithStateless.java.cdi");
		
		editResourceUtil.replaceInEditor("DecoratorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_1);
			
	}
	
	// https://issues.jboss.org/browse/JBIDE-7636
	@Test
	public void testNamedAnnotation() {
		
		String className = "Decorator2";
		
		newDecoratorWithContent(getPackageName(), className, 
			"/resources/quickfix/decorator/DecoratorWithNamed.java.cdi");
		
		editResourceUtil.replaceInEditor("DecoratorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_1);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7683
	@Test
	public void testProducer() {
		
		String className = "Decorator3";
		
		newDecoratorWithContent(getPackageName(), className, 
			"/resources/quickfix/decorator/DecoratorWithProducer.java.cdi");
		
		editResourceUtil.replaceInEditor("DecoratorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_3, QUICK_FIX_1);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7684
	@Test
	public void testDisposesAnnotation() {
		
		String className = "Decorator4";
		
		newDecoratorWithContent(getPackageName(), className, 
			"/resources/quickfix/decorator/DecoratorWithDisposes.java.cdi");
		
		editResourceUtil.replaceInEditor("DecoratorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_4, QUICK_FIX_1);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7685
	@Test
	public void testObservesAnnotation() {
		
		String className = "Decorator5";
		
		newDecoratorWithContent(getPackageName(), className, 
			"/resources/quickfix/decorator/DecoratorWithDisposes.java.cdi");
		
		editResourceUtil.replaceInEditor("import javax.enterprise.inject.Disposes;", 
				"import javax.enterprise.event.Observes;");
		editResourceUtil.replaceInEditor("@Disposes", "@Observes");
		editResourceUtil.replaceInEditor("DecoratorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_5, QUICK_FIX_1);
			
	}
	
	// https://issues.jboss.org/browse/JBIDE-7686
	@Test
	public void testSpecializesAnnotation() {
		
		String className = "Decorator6";
		
		newDecoratorWithContent(getPackageName(), className, 
			"/resources/quickfix/decorator/DecoratorWithSpecializes.java.cdi");
			
		editResourceUtil.replaceInEditor("DecoratorComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_6, QUICK_FIX_1);
			
	}
	
	private void newDecoratorWithContent(String packageName, String name, String resource) {
		List<String> interfaces = new ArrayList<String>();
		interfaces.add("java.util.Set");
		new DecoratorCreator(new DecoratorConfiguration()
			.setPackageName(packageName)
			.setName(name)
			.setDecoratedInterfaces(interfaces)).newDecorator();
		CDICreatorUtil.fillContentOfEditor(name + ".java", resource);
	}
	
}