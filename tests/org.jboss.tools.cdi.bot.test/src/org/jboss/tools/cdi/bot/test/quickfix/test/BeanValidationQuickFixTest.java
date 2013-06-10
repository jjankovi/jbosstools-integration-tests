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
import org.jboss.tools.cdi.bot.test.creator.BeanCreator;
import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.creator.util.CDICreatorUtil;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of CDI bean component
 * 
 * @author Jaroslav Jankovic
 */

public class BeanValidationQuickFixTest extends CDITestBase {
	
	private static final String VALIDATION_PROBLEM_1 = "which " + 
		"declares a passivating scope SessionScoped";
	private static final String VALIDATION_PROBLEM_2 = "Bean " + 
		"constructor cannot have a parameter annotated @Disposes";
	private static final String VALIDATION_PROBLEM_3 = "Bean " + 
		"constructor cannot have a parameter annotated @Observes";
	private static final String VALIDATION_PROBLEM_4 = "Producer " + 
		"method has a parameter annotated @Disposes";
	private static final String VALIDATION_PROBLEM_5 = "Producer " + 
		"method has a parameter annotated @Observes";
	private static final String VALIDATION_PROBLEM_6 = "Disposer " + 
		"method cannot be annotated @Inject";
	private static final String VALIDATION_PROBLEM_7 = "Observer " + 
		"method cannot be annotated @Inject";
	private static final String VALIDATION_PROBLEM_8 = "Producer " + 
		"method or field cannot be annotated @Inject";
	private static final String VALIDATION_PROBLEM_9 = "Observer " + 
		"method has a parameter annotated @Disposes";
	
	private static final String QUICK_FIX_1 = "Add " + 
		"java.io.Serializable interface to class";
	private static final String QUICK_FIX_2 = "Delete annotation";
	
	// https://issues.jboss.org/browse/JBIDE-8550
	@Test
	public void testSerializableManagedBean() {
		
		String className = "ManagedBean";
		
		newBeanWithContent(getPackageName(), className, 
				"/resources/quickfix/bean/SerializableBean.java.cdi");
		editResourceUtil.replaceInEditor("BeanComponent", className);		
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_1);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7664
	@Test
	public void testConstructor() {
		
		String className = "Bean1";
		
		newBeanWithContent(getPackageName(), className, 
				"/resources/quickfix/bean/ConstructorWithParam.java.cdi");
		editResourceUtil.replaceInEditor("BeanComponent", className);		
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_2);
		
		editResourceUtil.replaceClassContentByResource(BeanValidationQuickFixTest.class
				.getResourceAsStream("/resources/quickfix/bean/ConstructorWithParam.java.cdi"), false);
		
		editResourceUtil.replaceInEditor("@Disposes", "@Observes");
		editResourceUtil.replaceInEditor("import javax.enterprise.inject.Disposes;", 
				"import javax.enterprise.event.Observes;");
		editResourceUtil.replaceInEditor("BeanComponent", className);		
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_3, QUICK_FIX_2);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7665
	@Test
	public void testProducer() {
		
		String className = "Bean2";
		
		newBeanWithContent(getPackageName(), className, 
				"/resources/quickfix/bean/ProducerWithParam.java.cdi");
		
		editResourceUtil.replaceInEditor("BeanComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_4, QUICK_FIX_2);
		
		editResourceUtil.replaceClassContentByResource(BeanValidationQuickFixTest.class
				.getResourceAsStream("/resources/quickfix/bean/ProducerWithParam.java.cdi"), false);
		editResourceUtil.replaceInEditor("BeanComponent", className);
		
		editResourceUtil.replaceInEditor("@Disposes", "@Observes");
		editResourceUtil.replaceInEditor("import javax.enterprise.inject.Disposes;", 
				"import javax.enterprise.event.Observes;");
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_5, QUICK_FIX_2);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7667
	@Test
	public void testInjectDisposer() {
			
		String className = "Bean3";
		
		newBeanWithContent(getPackageName(), className, 
				"/resources/quickfix/bean/BeanInjectDisposes.java.cdi");
		editResourceUtil.replaceInEditor("BeanComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_6, QUICK_FIX_2);
				
	}
	
	// https://issues.jboss.org/browse/JBIDE-7667
	@Test
	public void testInjectObserver() {
		
		String className = "Bean4";
		
		newBeanWithContent(getPackageName(), className, 
				"/resources/quickfix/bean/BeanInjectDisposes.java.cdi");
		
		editResourceUtil.replaceInEditor("import javax.enterprise.inject.Disposes;", 
				"import javax.enterprise.event.Observes;");
		editResourceUtil.replaceInEditor("@Disposes", "@Observes");
		editResourceUtil.replaceInEditor("BeanComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_7, QUICK_FIX_2);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7667
	@Test
	public void testInjectProducer() {
		
		String className = "Bean5";
		
		newBeanWithContent(getPackageName(), className, 
				"/resources/quickfix/bean/BeanInjectProducer.java.cdi");
		
		editResourceUtil.replaceInEditor("BeanComponent", className);
			
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_8, QUICK_FIX_2);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7668
	@Test
	public void testObserverWithDisposer() {
			
		String className = "Bean6";
		
		newBeanWithContent(getPackageName(), className, 
				"/resources/quickfix/bean/ObserverWithDisposer.java.cdi");
		
		editResourceUtil.replaceInEditor("BeanComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_9, QUICK_FIX_2);
			
	}
	
	private void newBeanWithContent(String packageName, String name, String resource) {
		new BeanCreator(new BeanConfiguration()
			.setPackageName(packageName)
			.setName(name)).newBean();
		CDICreatorUtil.fillContentOfEditor(name + ".java", resource);
	}
	
}
