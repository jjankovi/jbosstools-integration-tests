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

package org.jboss.tools.cdi.bot.test.util;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.tools.cdi.bot.test.condition.OpenedEditorHasTitleCondition;
import org.jboss.tools.ui.bot.ext.SWTBotExt;
import org.jboss.tools.ui.bot.ext.SWTBotFactory;
import org.jboss.tools.ui.bot.ext.SWTOpenExt;
import org.jboss.tools.ui.bot.ext.SWTUtilExt;
import org.jboss.tools.ui.bot.ext.gen.ActionItem.NewObject.JavaAnnotation;

/**
 * 
 * @author jjankovi
 *
 */
public class AnnotationCreator {
	
	private static SWTBotExt bot = SWTBotFactory.getBot();
	private static SWTUtilExt util = SWTBotFactory.getUtil();
	private static SWTOpenExt open = SWTBotFactory.getOpen();
	
	/**
	 * Method creates Java Annotation with selected name and package	 
	 * @param name
	 * @param packageName
	 */
	public static void newAnnotation(String name, String packageName) {
		SWTBot openWizard = open.newObject(JavaAnnotation.LABEL);
		openWizard.textWithLabel("Name:").setText(name);
		openWizard.textWithLabel("Package:").setText(packageName);
		openWizard.button("Finish").click();
		util.waitForNonIgnoredJobs();
		new WaitUntil(new OpenedEditorHasTitleCondition(name + ".java"));
		bot.editorByTitle(name + ".java").show();
	}
	
}
