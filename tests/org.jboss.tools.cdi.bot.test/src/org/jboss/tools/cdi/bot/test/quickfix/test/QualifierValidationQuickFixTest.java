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
import org.jboss.tools.cdi.bot.test.creator.QualifierCreator;
import org.jboss.tools.cdi.bot.test.creator.config.QualifierConfiguration;
import org.jboss.tools.cdi.bot.test.creator.util.CDICreatorUtil;
import org.jboss.tools.cdi.bot.test.util.AnnotationCreator;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of CDI Qualifier component
 * 
 * @author Jaroslav Jankovic
 */

public class QualifierValidationQuickFixTest extends CDITestBase {
	
	private static final String VALIDATION_PROBLEM_1 = "Qualifier annotation " + 
		"type must be annotated with @Target";
	private static final String VALIDATION_PROBLEM_2 = "Qualifier annotation " + 
		"type must be annotated with @Retention(RUNTIME)";
	private static final String VALIDATION_PROBLEM_3 = "Annotation-valued member " + 
		"of a qualifier type should be annotated @Nonbinding";
	private static final String VALIDATION_PROBLEM_4 = "Array-valued member " + 
		"of a qualifier type should be annotated @Nonbinding";
	private static final String QUICK_FIX_1 = 
			"TYPE, METHOD, FIELD, PARAMETER";
	private static final String QUICK_FIX_2 = 
			"FIELD, PARAMETER";
	private static final String QUICK_FIX_3 = 
			"Change annotation";
	private static final String QUICK_FIX_4 = 
			"Add annotation @Retention(RUNTIME)";
	private static final String QUICK_FIX_5 = 
			"Add annotation @Nonbinding";
	
	// https://issues.jboss.org/browse/JBIDE-7630
	@Test
	public void testTargetAnnotation() {
		
		String className = "Qualifier1";
		
		newQualifier(getPackageName(), className);
		
		editResourceUtil.replaceInEditor("@Target({ TYPE, METHOD, PARAMETER, FIELD })", 
				"@Target({ TYPE, FIELD })");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_1);
		
		editResourceUtil.replaceInEditor("@Target({TYPE, METHOD, FIELD, PARAMETER})", "");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_2);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7631
	@Test
	public void testRetentionAnnotation() {
		
		String className = "Qualifier2";

		newQualifier(getPackageName(), className);
				
		editResourceUtil.replaceInEditor("@Retention(RUNTIME)", "@Retention(CLASS)");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_3);
		
		editResourceUtil.replaceInEditor("@Retention(RUNTIME)", "");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_4);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7641
	@Test
	public void testNonbindingAnnotation() {
	
		String className = "Qualifier3";
		
		AnnotationCreator.newAnnotation("AAnnotation", getPackageName());
		newQualifier(getPackageName(), className, 
			"/resources/quickfix/qualifier/QualifierWithAnnotation.java.cdi");
		
		editResourceUtil.replaceInEditor("QualifierComponent", className);
	
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_3, QUICK_FIX_5);
		
		editResourceUtil.replaceClassContentByResource(QualifierValidationQuickFixTest.class
				.getResourceAsStream("/resources/quickfix/qualifier/QualifierWithStringArray.java.cdi"), false);
		editResourceUtil.replaceInEditor("QualifierComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_4, QUICK_FIX_5);
		
	}
	
	private void newQualifier(String packageName, String name) {
		newQualifier(packageName, name, null);
	}
	
	private void newQualifier(String packageName, String name, String resource) {
		new QualifierCreator(new QualifierConfiguration()
			.setPackageName(packageName)
			.setName(name)).newQualifier();
		if (resource != null) 
			CDICreatorUtil.fillContentOfEditor(name + ".java", resource);
	}
}
