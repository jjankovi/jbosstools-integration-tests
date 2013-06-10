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

package org.jboss.tools.cdi.bot.test.openon;

import java.util.ArrayList;
import java.util.List;

import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.creator.BeanCreator;
import org.jboss.tools.cdi.bot.test.creator.DecoratorCreator;
import org.jboss.tools.cdi.bot.test.creator.InterceptorCreator;
import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.DecoratorConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.InterceptorConfiguration;
import org.jboss.tools.ui.bot.ext.helper.OpenOnHelper;
import org.jboss.tools.ui.bot.ext.types.IDELabel;

/**
 * test base for OpenOn-like CDI tests
 * 
 * @author jjankovi
 *
 */

public class OpenOnBase extends CDITestBase {
	
	protected static final String[] events = { "myBean1Q1Event", "myBean1AnyEvent",
			"myBean2Q1Event", "myBean2AnyEvent", "myBean1Q2Event",
			"myBean2Q2Event", "myBean1Q1Event.fire(new MyBean1());",
			"myBean1AnyEvent.fire(new MyBean1())",
			"myBean2Q1Event.fire(new MyBean2())",
			"myBean2AnyEvent.fire(new MyBean2())",
			"myBean1Q2Event.fire(new MyBean1())",
			"myBean2Q2Event.fire(new MyBean2())",
			"myBean1AnyEvent.fire(new MyBean2())" };
	
	protected static final String[] observers = { "observeNoQualifierMyBean1",
			"observeAnyMyBean1", "observeQ1MyBean1",
			"observeNoQualifierMyBean2", "observeAnyMyBean2",
			"observeQ1MyBean2", "observeQ2MyBean1", "observeQ2MyBean2" };
	
	/**
	 * Method creates Decorator component with entered name and package name.
	 * Then it opens beans.xml and simulates direct openOn through method openOnDirect.
	 * Finally it checks if the class which was opened-on to is correct.
	 * @param packageName
	 * @param className
	 * @return
	 */
	protected void checkBeanXMLDecoratorOpenOn(String packageName, String className) {
		List<String> interfaces = new ArrayList<String>();
		interfaces.add("java.util.Set");
		new DecoratorCreator(new DecoratorConfiguration()
			.setPackageName(packageName)
			.setName(className)
			.setDecoratedInterfaces(interfaces)
			.setRegisterInBeansXML(true)).newDecorator();
		checkOpenOnBeanXml(packageName, className);
	}
	
	
	/**
	 * Method creates Interceptor component with entered name and package name.
	 * Then it opens beans.xml and simulates direct openOn through method openOnDirect.
	 * Finally it checks if the class which was opened-on to is correct.
	 * @param packageName
	 * @param className
	 * @return
	 */
	protected void checkBeanXMLInterceptorOpenOn(String packageName, String className) {
		new InterceptorCreator(new InterceptorConfiguration()
			.setPackageName(packageName)
			.setName(className)
			.setRegisterInBeansXML(true)).newInterceptor();
		checkOpenOnBeanXml(packageName, className);
	}
	
	/**
	 * Method creates Alternative Bean component with entered name and package name.
	 * Then it opens beans.xml and simulates direct openOn through method openOnDirect.
	 * Finally it checks if the class which was opened-on to is correct.
	 * @param packageName
	 * @param className
	 * @return
	 */
	protected void checkBeanXMLAlternativeOpenOn(String packageName, String className) {
		new BeanCreator(new BeanConfiguration()
			.setPackageName(packageName)
			.setName(className)
			.setAlternative(true)
			.setRegisterInBeansXML(true)).newBean();
		checkOpenOnBeanXml(packageName, className);
	}
	
	private void checkOpenOnBeanXml(String packageName, String className) {
		bot.editorByTitle(IDELabel.WebProjectsTree.BEANS_XML).show();
		bot.cTabItem("Source").activate();
		OpenOnHelper.checkOpenOnFileIsOpened(bot, IDELabel.WebProjectsTree.BEANS_XML, 
				packageName + "." + className, className + ".java");
	}

}
