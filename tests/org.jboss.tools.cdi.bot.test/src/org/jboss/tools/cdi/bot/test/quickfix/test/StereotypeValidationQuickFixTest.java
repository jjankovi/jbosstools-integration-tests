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
import org.jboss.tools.cdi.bot.test.creator.StereotypeCreator;
import org.jboss.tools.cdi.bot.test.creator.config.StereotypeConfiguration;
import org.jboss.tools.cdi.bot.test.creator.util.CDICreatorUtil;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of CDI Stereotype component
 * 
 * @author Jaroslav Jankovic
 */

public class StereotypeValidationQuickFixTest extends CDITestBase {
	
	private static final String VALIDATION_PROBLEM_1 = "Stereotype annotation " + 
		"type must be annotated with one of";
	private static final String VALIDATION_PROBLEM_2 = "Stereotype annotation " + 
		"type must be annotated with @Retention(RUNTIME)";
	private static final String VALIDATION_PROBLEM_3 = "Stereotype declares a " + 
		"non-empty @Named annotation";
	private static final String VALIDATION_PROBLEM_4 ="A stereotype should not " + 
		"be annotated @Typed";
	
	private static final String QUICK_FIX_1 = "TYPE, METHOD, FIELD";
	private static final String QUICK_FIX_2 = "Change annotation";
	private static final String QUICK_FIX_3 = "Add annotation";
	private static final String QUICK_FIX_4 = "Delete annotation @Typed";
	
	// https://issues.jboss.org/browse/JBIDE-7630
	@Test
	public void testTargetAnnotation() {
		
		String className = "Stereotype1";
		
		newStereotype(getPackageName(), className);
		
		editResourceUtil.replaceInEditor("@Target({ TYPE, METHOD, FIELD })", 
				"@Target({ TYPE, FIELD })");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_1);
		
		editResourceUtil.replaceInEditor("@Target({TYPE, METHOD, FIELD})", "");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_1);
	}
	
	// https://issues.jboss.org/browse/JBIDE-7631
	@Test
	public void testRetentionAnnotation() {
		
		String className = "Stereotype2";

		newStereotype(getPackageName(), className);
		
		editResourceUtil.replaceInEditor("@Retention(RUNTIME)", "@Retention(CLASS)");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_2);
		editResourceUtil.replaceInEditor("@Retention(RUNTIME)", "");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_3);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7634
	@Test
	public void testNamedAnnotation() {
		
		String className = "Stereotype3";
		
		newStereotype(getPackageName(), className,
			"/resources/quickfix/stereotype/StereotypeWithNamed.java.cdi");
		
		editResourceUtil.replaceInEditor("StereotypeComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.ERRORS, 
				VALIDATION_PROBLEM_3, QUICK_FIX_2);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7640
	@Test	
	public void testTypedAnnotation() {
		
		String className = "Stereotype4";
		
		newStereotype(getPackageName(), className,
			"/resources/quickfix/stereotype/StereotypeWithTyped.java.cdi");
		
		editResourceUtil.replaceInEditor("StereotypeComponent", className);
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_4, QUICK_FIX_4);
		
	}	
	
	private void newStereotype(String packageName, String name) {
		newStereotype(packageName, name, null);
	}
	
	private void newStereotype(String packageName, String name, String resource) {
		new StereotypeCreator(new StereotypeConfiguration()
			.setPackageName(packageName)
			.setName(name)).newStereotype();
		if (resource != null) 
			CDICreatorUtil.fillContentOfEditor(name + ".java", resource);
	}

}
