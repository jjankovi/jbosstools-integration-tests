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

import org.jboss.tools.cdi.bot.test.CDIConstants;
import org.jboss.tools.cdi.bot.test.creator.BeansXMLCreator;
import org.jboss.tools.cdi.bot.test.creator.config.BeansXMLConfiguration;
import org.jboss.tools.cdi.bot.test.openon.OpenOnTest;
import org.jboss.tools.cdi.bot.test.uiutils.EditorResourceHelper;
import org.jboss.tools.ui.bot.ext.SWTBotExt;
import org.jboss.tools.ui.bot.ext.SWTBotFactory;
import org.jboss.tools.ui.bot.ext.Timing;
import org.jboss.tools.ui.bot.ext.types.IDELabel;
import org.jboss.tools.ui.bot.ext.view.PackageExplorer;

/**
 * Helper for beans.xml validation
 * 
 * @author Jaroslav Jankovic
 * 
 */

public class BeansXMLUtil {

	private static final String CLEAR_BEANS_XML = "/resources/beansXML/"
			+ "beans.xml.cdi";
	private static final String CLEAR_BEANS_XML_WITH_TAG = "/resources/beansXML/"
			+ "beansXmlWithEmptyTag.xml.cdi";
	private static final String BEANS_XML_WITH_INTERCEPTOR = "/resources/beansXML/"
			+ "beansXmlWithInterceptor.xml.cdi";
	private static final String BEANS_XML_WITH_DECORATOR = "/resources/beansXML/"
			+ "beansXmlWithDecorator.xml.cdi";
	private static final String BEANS_XML_WITH_STEREOTYPE = "/resources/beansXML/"
			+ "beansXmlWithStereotype.xml.cdi";
	private static final String BEANS_XML_WITH_ALTERNATIVE = "/resources/beansXML/"
			+ "beansXmlWithAlternative.xml.cdi";

	private static SWTBotExt bot = SWTBotFactory.getBot();
	private static PackageExplorer packageExplorer = SWTBotFactory.getPackageexplorer();
	private static EditorResourceHelper editResourceUtil = new EditorResourceHelper();
	
	/**
	 * Methods creates beans.xml with no tags for entered project.
	 * 
	 * @param projectName
	 */
	public static void createEmptyBeansXML(String projectName) {

		createBeansXML(projectName);
		replaceBeansXMLContent(projectName, CLEAR_BEANS_XML);

	}

	/**
	 * Methods creates beans.xml with empty tag <> for entered project.
	 * 
	 * @param projectName
	 */
	public static void createBeansXMLWithEmptyTag(String projectName) {

		createBeansXML(projectName);
		replaceBeansXMLContent(projectName, CLEAR_BEANS_XML_WITH_TAG);
	}

	/**
	 * Methods creates beans.xml with interceptor tags in it for entered
	 * project. Package and interceptor component name which should be showed in
	 * tag is determined by parameters. If className is null, then Component
	 * text will be removed
	 * 
	 * @param projectName
	 * @param packageName
	 * @param className
	 */
	public static void createBeansXMLWithInterceptor(String projectName,
			String packageName, String className) {

		createBeansXML(projectName);
		replaceBeansXMLContent(projectName, BEANS_XML_WITH_INTERCEPTOR);
		if (className == null || className.length() == 0) {
			editResourceUtil.replaceInEditor("<class>Component</class>",
					"<class></class>");
		} else {
			editResourceUtil.replaceInEditor("Component", packageName + "."
					+ className);
		}

	}

	/**
	 * Methods creates beans.xml with decorator tags in it for entered project.
	 * Package and decorator component name which should be showed in tag is
	 * determined by parameters. If className is null, then Component text will
	 * be removed
	 * 
	 * @param projectName
	 * @param packageName
	 * @param className
	 */
	public static void createBeansXMLWithDecorator(String projectName,
			String packageName, String className) {

		createBeansXML(projectName);
		replaceBeansXMLContent(projectName, BEANS_XML_WITH_DECORATOR);
		if (className == null || className.length() == 0) {
			editResourceUtil.replaceInEditor("<class>Component</class>",
					"<class></class>");
		} else {
			editResourceUtil.replaceInEditor("Component", packageName + "."
					+ className);
		}

	}

	/**
	 * Methods creates beans.xml with stereotype tags in it for entered project.
	 * Package and stereotype component name which should be showed in tag is
	 * determined by parameters. If className is null, then Component text will
	 * be removed
	 * 
	 * @param projectName
	 * @param packageName
	 * @param className
	 */
	public static void createBeansXMLWithStereotype(String projectName,
			String packageName, String className) {

		createBeansXML(projectName);
		replaceBeansXMLContent(projectName, BEANS_XML_WITH_STEREOTYPE);
		if (className == null || className.length() == 0) {
			editResourceUtil.replaceInEditor(
					"<stereotype>Component</stereotype>",
					"<stereotype></stereotype>");
		} else {
			editResourceUtil.replaceInEditor("Component", packageName + "."
					+ className);
		}

	}

	public static void createBeansXMLWithAlternative(String projectName,
			String packageName, String className) {
		createBeansXMLWithAlternative(projectName, packageName, className, true);
	}
	
	/**
	 * Methods creates beans.xml with alternative tags in it for entered
	 * project. Package and alternative bean component name which should be
	 * showed in tag is determined by parameters. If className is null, then
	 * Component text will be removed
	 * 
	 * @param projectName
	 * @param packageName
	 * @param className
	 */
	public static void createBeansXMLWithAlternative(String projectName,
			String packageName, String className, boolean save) {

		createBeansXML(projectName);
		bot.sleep(Timing.time1S()); // for as-you-type validation
		replaceBeansXMLContent(projectName, BEANS_XML_WITH_ALTERNATIVE);
		if (className == null || className.length() == 0) {
			editResourceUtil.replaceInEditor("<class>Component</class>",
					"<class></class>");
		} else {
			editResourceUtil.replaceInEditor("Component", packageName + "."
					+ className, save);
		}
	}

	/**
	 * If there is no beans.xml file in the project, then this method creates a
	 * new one
	 * 
	 * @param projectName
	 */
	private static void createBeansXML(String projectName) {

		if (!packageExplorer.isFilePresent(projectName,
				CDIConstants.META_INF_BEANS_XML_PATH.split("/"))
				&& !packageExplorer.isFilePresent(projectName,
						CDIConstants.WEB_INF_BEANS_XML_PATH.split("/"))) {
			
			new BeansXMLCreator(new BeansXMLConfiguration()
				.setParentFolder(projectName + "/" 
					+ IDELabel.WebProjectsTree.WEB_CONTENT + "/"
					+ IDELabel.WebProjectsTree.WEB_INF)	
				.setFileName("beans.xml")).newBeanXML();
		}
	}

	/**
	 * Methods create beans.xml for entered project with content of file
	 * determined by parameter path. If there is beans.xml in project, its
	 * content is simply replaced
	 * 
	 * @param projectName
	 * @param path
	 */
	private static void replaceBeansXMLContent(String projectName, String path) {

		if (packageExplorer.isFilePresent(projectName,
				CDIConstants.WEB_INF_BEANS_XML_PATH.split("/"))) {
			packageExplorer.openFile(projectName,
					CDIConstants.WEB_INF_BEANS_XML_PATH.split("/"));
		} else {
			packageExplorer.openFile(projectName,
					CDIConstants.META_INF_BEANS_XML_PATH.split("/"));
		}
		bot.cTabItem("Source").activate();
		editResourceUtil.replaceClassContentByResource(
				OpenOnTest.class.getResourceAsStream(path), false);
	}

}