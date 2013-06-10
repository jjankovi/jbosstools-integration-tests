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
import org.jboss.tools.cdi.bot.test.creator.ScopeCreator;
import org.jboss.tools.cdi.bot.test.creator.config.ScopeConfiguration;
import org.jboss.tools.cdi.bot.test.util.QuickFixUtil;
import org.junit.Test;

/**
 * Test operates on quick fixes used for validation errors of CDI Scope component
 * 
 * @author Jaroslav Jankovic
 */

public class ScopeValidationQuickFixTest extends CDITestBase {
	
	private static final String VALIDATION_PROBLEM_1 = "Scope annotation " + 
		"type must be annotated with @Target";
	private static final String VALIDATION_PROBLEM_2 = "Scope annotation " + 
		"type must be annotated with @Retention(RUNTIME)";
	
	private static final String QUICK_FIX_1 = "Change annotation";
	private static final String QUICK_FIX_2 = "Add annotation";
	
	// https://issues.jboss.org/browse/JBIDE-7633
	@Test
	public void testTargetAnnotation() {
		
		String className = "Scope1";
		
		newScope(getPackageName(), className);
		
		editResourceUtil.replaceInEditor("@Target({ TYPE, METHOD, FIELD })", 
				"@Target({ TYPE, FIELD })");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_1);
		
		editResourceUtil.replaceInEditor("@Target({TYPE, METHOD, FIELD})", "");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_1, QUICK_FIX_2);
		
	}
	
	// https://issues.jboss.org/browse/JBIDE-7631
	@Test
	public void testRetentionAnnotation() {
		
		String className = "Scope2";

		newScope(getPackageName(), className);
				
		editResourceUtil.replaceInEditor("@Retention(RUNTIME)", "@Retention(CLASS)");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_1);
		
		editResourceUtil.replaceInEditor("@Retention(RUNTIME)", "");
		
		QuickFixUtil.performQuickFix(ProblemsType.WARNINGS, 
				VALIDATION_PROBLEM_2, QUICK_FIX_2);
		
	}
	
	private void newScope(String packageName, String name) {
		new ScopeCreator(new ScopeConfiguration()
			.setPackageName(packageName)
			.setName(name)).newScope();
	} 
	
}
