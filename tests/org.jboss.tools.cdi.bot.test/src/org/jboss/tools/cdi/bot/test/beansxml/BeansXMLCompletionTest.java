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

package org.jboss.tools.cdi.bot.test.beansxml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.jboss.tools.cdi.bot.test.CDIConstants;
import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.creator.AnnotationLiteralCreator;
import org.jboss.tools.cdi.bot.test.creator.BeanCreator;
import org.jboss.tools.cdi.bot.test.creator.DecoratorCreator;
import org.jboss.tools.cdi.bot.test.creator.InterceptorBindingCreator;
import org.jboss.tools.cdi.bot.test.creator.InterceptorCreator;
import org.jboss.tools.cdi.bot.test.creator.QualifierCreator;
import org.jboss.tools.cdi.bot.test.creator.ScopeCreator;
import org.jboss.tools.cdi.bot.test.creator.StereotypeCreator;
import org.jboss.tools.cdi.bot.test.creator.config.AnnotationLiteralConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.DecoratorConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.InterceptorBindingConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.InterceptorConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.QualifierConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.ScopeConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.StereotypeConfiguration;
import org.jboss.tools.cdi.bot.test.util.BeansXMLUtil;
import org.jboss.tools.ui.bot.ext.helper.ContentAssistHelper;
import org.jboss.tools.ui.bot.ext.types.IDELabel;
import org.junit.Test;

/**
 * Test operates on code completion in beans.xml
 * 
 * @author Jaroslav Jankovic
 * 
 */

public class BeansXMLCompletionTest extends CDITestBase {
	
	private static final List<String> BEANS_XML_TAGS = Arrays.asList(
			"alternatives", "decorators", "interceptors");

	@Test
	public void testPossibleCompletionInBeansXML() {
		
		BeansXMLUtil.createBeansXMLWithEmptyTag(getProjectName());
		LOGGER.info("Clear beans.xml was created");
				
		checkAutoCompletion(3, 0, "<>", IDELabel.WebProjectsTree.BEANS_XML, BEANS_XML_TAGS);				
	}
	
	@Test
	public void testInterceptorsCompletion() {

		List<String> INTERCEPTOR_NAMES = Arrays.asList("I1", "I2", "I3");
		
		InterceptorConfiguration interceptorConfig = new InterceptorConfiguration();
		interceptorConfig.setPackageName(getPackageName());
		
		interceptorConfig.setName(INTERCEPTOR_NAMES.get(0));
		new InterceptorCreator(interceptorConfig).newInterceptor();
		interceptorConfig.setName(INTERCEPTOR_NAMES.get(1));
		new InterceptorCreator(interceptorConfig).newInterceptor();
		interceptorConfig.setName(INTERCEPTOR_NAMES.get(2));
		new InterceptorCreator(interceptorConfig).newInterceptor();
		
		BeansXMLUtil.createBeansXMLWithInterceptor(getProjectName(), getPackageName(), null);
		LOGGER.info("Beans.xml with interceptors tag was created");
				
		List<String> proposalList = editResourceUtil.getProposalList(
				IDELabel.WebProjectsTree.BEANS_XML, CDIConstants.CLASS_END_TAG, 0, 0);
		for (String interceptor : INTERCEPTOR_NAMES) {
			assertTrue(proposalList.contains(interceptor + " - " + getPackageName()));
		}
	}
	
	@Test
	public void testDecoratorsCompletion() {
		
		List<String> DECORATORS_NAMES = Arrays.asList("D1", "D2", "D3");
		List<String> interfaces = new ArrayList<String>();
		interfaces.add("java.util.Set");
		
		DecoratorConfiguration decoratorConfiguration = new DecoratorConfiguration();
		decoratorConfiguration
			.setPackageName(getPackageName())
			.setDecoratedInterfaces(interfaces);
		
		decoratorConfiguration.setName(DECORATORS_NAMES.get(0));
		new DecoratorCreator(decoratorConfiguration).newDecorator();
		decoratorConfiguration.setName(DECORATORS_NAMES.get(1));
		new DecoratorCreator(decoratorConfiguration).newDecorator();
		decoratorConfiguration.setName(DECORATORS_NAMES.get(2));
		new DecoratorCreator(decoratorConfiguration).newDecorator();
		
		BeansXMLUtil.createBeansXMLWithDecorator(getProjectName(), getPackageName(), null);
		LOGGER.info("Beans.xml with decorators tag was created");
			
		List<String> proposalList = editResourceUtil.getProposalList(IDELabel.WebProjectsTree.BEANS_XML, 
				CDIConstants.CLASS_END_TAG, 0, 0);
		for (String decorator : DECORATORS_NAMES) {
			assertTrue(proposalList.contains(decorator + " - " + getPackageName()));
		}

	}
	
	@Test
	public void testStereotypesCompletion() {
		
		List<String> STEREOTYPES_NAMES = Arrays.asList("S1", "S2", "S3");
		
		StereotypeConfiguration stereotypeConfigutation = new StereotypeConfiguration();
		stereotypeConfigutation
			.setPackageName(getPackageName())
			.setAlternative(true);
		
		stereotypeConfigutation.setName(STEREOTYPES_NAMES.get(0));
		new StereotypeCreator(stereotypeConfigutation).newStereotype();
		stereotypeConfigutation.setName(STEREOTYPES_NAMES.get(1));
		new StereotypeCreator(stereotypeConfigutation).newStereotype();
		stereotypeConfigutation.setName(STEREOTYPES_NAMES.get(2));
		new StereotypeCreator(stereotypeConfigutation).newStereotype();
				
		BeansXMLUtil.createBeansXMLWithStereotype(getProjectName(), getPackageName(), null);
		LOGGER.info("Beans.xml with stereotype tag was created");
			
		List<String> proposalList = editResourceUtil.getProposalList(IDELabel.WebProjectsTree.BEANS_XML, 
				CDIConstants.STEREOTYPE_END_TAG, 0, 0);
		for (String stereotype : STEREOTYPES_NAMES) {
			assertTrue(proposalList.contains(stereotype + " - " + getPackageName()));
		}

	}
	
	@Test
	public void testAlternativesCompletion() {
		
		List<String> ALTERNATIVES_NAMES = Arrays.asList("A1", "A2", "A3");
		
		BeanConfiguration beanConfiguration = new BeanConfiguration();
		beanConfiguration
			.setPackageName(getPackageName())
			.setAlternative(true);
		
		beanConfiguration.setName(ALTERNATIVES_NAMES.get(0));
		new BeanCreator(beanConfiguration).newBean();
		beanConfiguration.setName(ALTERNATIVES_NAMES.get(1));
		new BeanCreator(beanConfiguration).newBean();
		beanConfiguration.setName(ALTERNATIVES_NAMES.get(2));
		new BeanCreator(beanConfiguration).newBean();
		
		BeansXMLUtil.createBeansXMLWithAlternative(getProjectName(), getPackageName(), null);
		LOGGER.info("Beans.xml with alternative tag was created");
		
		List<String> proposalList = editResourceUtil.getProposalList(IDELabel.WebProjectsTree.BEANS_XML, 
				CDIConstants.CLASS_END_TAG, 0, 0);
		for (String alternative : ALTERNATIVES_NAMES) {
			assertTrue(proposalList.contains(alternative + " - " + getPackageName()));
		}

	}
	
	@Test
	public void testNonSupportedComponentCompletion() {
		
		String[] components = {"AL1", "Q1", "B1", "IB1", "Sc1"};
		
		new AnnotationLiteralCreator(new AnnotationLiteralConfiguration()
			.setPackageName(getPackageName())
			.setName(components[0])
			.setQualifier("javax.enterprise.inject.Any")).newAnnotationLiteral();
		new QualifierCreator(new QualifierConfiguration()
			.setPackageName(getPackageName())
			.setName(components[1])).newQualifier();
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(components[2])).newBean();
		new InterceptorBindingCreator(
			new InterceptorBindingConfiguration()
				.setPackageName(getPackageName())
				.setName(components[3])).newInterceptorBinding();
		new ScopeCreator(new ScopeConfiguration()
			.setPackageName(getPackageName())
			.setName(components[4])).newScope();
		
		BeansXMLUtil.createBeansXMLWithEmptyTag(getProjectName());
		LOGGER.info("Clear beans.xml with empty tag was created");
		
		List<String> proposalList = editResourceUtil.getProposalList(
				IDELabel.WebProjectsTree.BEANS_XML, "<>", 1, 0);
		List<String> nonSupportedComponents = Arrays.asList(components);
		
		for (String nonSupportedComponent : nonSupportedComponents) {
			for (String proposalOption : proposalList) {				
				assertFalse((nonSupportedComponent + " - " + getPackageName()).
						equals(proposalOption));
			}
		}
		
	}
	
	/**
	 * Method checks auto completion proposals. First, it 
	 * types provided text on location provided by parameters.
	 * Then checks all items in proposal list by 
	 * ContentAssistHelper.checkContentAssistContent() method.
	 * At the end, it removes inserted text(due to possible formating error) 
	 * @param row
	 * @param column
	 * @param text
	 * @param expectedProposalList
	 */
	private void checkAutoCompletion(int row, int column, String text, String editorTitle,
			List<String> expectedProposalList) {
		SWTBotEclipseEditor activeEditor = bot.activeEditor().toTextEditor();
		activeEditor.navigateTo(row, column);
		activeEditor.typeText(text);
		ContentAssistHelper.checkContentAssistContent(bot, 
				editorTitle, text, 1, 0, expectedProposalList, false);		
		editResourceUtil.replaceInEditor(text, "");
	}
	
}
