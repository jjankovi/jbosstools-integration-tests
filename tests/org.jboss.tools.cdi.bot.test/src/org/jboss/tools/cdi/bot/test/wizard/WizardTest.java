/*******************************************************************************
 * Copyright (c) 2010-2011 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.cdi.bot.test.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jface.exception.JFaceLayerException;
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
import org.jboss.tools.cdi.bot.test.util.ProjectUtil;
import org.jboss.tools.cdi.reddeer.cdi.ui.InterceptorBindingTarget;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewBeansXMLCreationWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.StereotypeTarget;
import org.junit.Test;

/**
 * Test checks all CDI components wizardExts
 * 
 * @author Lukas Jungmann
 * @author jjankovi
 */
public class WizardTest extends CDITestBase {

	@Override
	public void waitForJobs() {
		projectExplorer.selectProject(getProjectName());
	}
		
	@Test
	public void testComponentsWizards() {
		testQualifier();
		testScope();
		testIBinding();
		testStereotype();
		testDecorator();
		testInterceptor();
		testBeansXml();
		testBean();
		testAnnLiteral();
	}
	
	private void testQualifier() {
		new QualifierCreator(
			new QualifierConfiguration()
				.setPackageName(getPackageName())
				.setName("Q1"))
			.newQualifier();
		SWTBotEditor ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("Q1.java").equals(ed.getTitle()));
		String code = ed.toTextEditor().getText();
		assertTrue(code.contains("@Qualifier"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE, METHOD, PARAMETER, FIELD })"));
		assertFalse(code.contains("@Inherited"));
		assertFalse(code.startsWith("/**"));

		new QualifierCreator(
			new QualifierConfiguration()
				.setPackageName(getPackageName())
				.setName("Q2")
				.setInherated(true)
				.generateComments(true))
			.newQualifier();
		
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("Q2.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@Qualifier"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE, METHOD, PARAMETER, FIELD })"));
		assertTrue(code.contains("@Inherited"));
		assertTrue(code.startsWith("/**"));
	}
	
	private void testScope() {
		
		new ScopeCreator(
			new ScopeConfiguration()
				.setPackageName(getPackageName())
				.setName("Scope1")
				.setInherited(true)
				.setNormalScope(true))
			.newScope();
		SWTBotEditor ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("Scope1.java").equals(ed.getTitle()));
		String code = ed.toTextEditor().getText();
		assertTrue(code.contains("@NormalScope"));
		assertFalse(code.contains("@Scope"));
		assertFalse(code.contains("passivating"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE, METHOD, FIELD })"));
		assertTrue(code.contains("@Inherited"));
		assertFalse(code.startsWith("/**"));

		new ScopeCreator(
			new ScopeConfiguration()
				.setPackageName(getPackageName())
				.setName("Scope2")
				.generateComments(true)
				.setNormalScope(true)
				.setPassivating(true))
			.newScope();
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("Scope2.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@NormalScope(passivating = true)"));
		assertFalse(code.contains("@Scope"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE, METHOD, FIELD })"));
		assertFalse(code.contains("@Inherited"));
		assertTrue(code.startsWith("/**"));

		new ScopeCreator(
			new ScopeConfiguration()
				.setPackageName(getPackageName())
				.setName("Scope3")
				.generateComments(true))
			.newScope();
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("Scope3.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@Scope"));
		assertFalse(code.contains("@NormalScope"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE, METHOD, FIELD })"));
		assertFalse(code.contains("@Inherited"));
		assertTrue(code.startsWith("/**"));
	}
	
	private void testIBinding() {
		
		new InterceptorBindingCreator(
			new InterceptorBindingConfiguration()
				.setPackageName(getPackageName())
				.setName("B1")
				.setInherited(true))
			.newInterceptorBinding();
		SWTBotEditor ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("B1.java").equals(ed.getTitle()));
		String code = ed.toTextEditor().getText();
		assertTrue(code.contains("@InterceptorBinding"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE, METHOD })"));
		assertTrue(code.contains("@Inherited"));
		assertFalse(code.startsWith("/**"));

		new InterceptorBindingCreator(
			new InterceptorBindingConfiguration()
				.setPackageName(getPackageName())
				.setName("B2")
				.setTarget(InterceptorBindingTarget.TYPE)
				.generateComments(true))
			.newInterceptorBinding();
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("B2.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@InterceptorBinding"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE })"));
		assertFalse(code.contains("@Inherited"));
		assertTrue(code.startsWith("/**"));

		new InterceptorBindingCreator(
			new InterceptorBindingConfiguration()
				.setPackageName(getPackageName())
				.setName("B3")
				.setTarget(InterceptorBindingTarget.TYPE)
				.generateComments(true))
			.newInterceptorBinding();
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("B3.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@InterceptorBinding"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE })"));
		assertFalse(code.contains("@Inherited"));
		assertTrue(code.startsWith("/**"));

		List<String> iBindings = new ArrayList<String>();
		iBindings.add(getPackageName() + ".B2");
		new InterceptorBindingCreator(
			new InterceptorBindingConfiguration()
				.setPackageName(getPackageName())
				.setName("B4")
				.setTarget(InterceptorBindingTarget.TYPE)
				.setInherited(true)
				.setInterceptors(iBindings))
			.newInterceptorBinding();
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("B4.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@InterceptorBinding"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE })"));
		assertTrue(code.contains("@Inherited"));
		assertFalse(code.startsWith("/**"));
		assertTrue(code.contains("@B2"));
	}
	
	private void testStereotype() {
		
		new StereotypeCreator(
			new StereotypeConfiguration()
				.setPackageName(getPackageName())
				.setName("S1"))
			.newStereotype();
		SWTBotEditor ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("S1.java").equals(ed.getTitle()));
		String code = ed.toTextEditor().getText();
		assertTrue(code.contains("@Stereotype"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE, METHOD, FIELD })"));
		assertFalse(code.contains("@Named"));
		assertFalse(code.contains("@Alternative"));
		assertFalse(code.contains("@Inherited"));
		assertFalse(code.startsWith("/**"));

		new StereotypeCreator(
			new StereotypeConfiguration()
				.setPackageName(getPackageName())
				.setName("S2")
				.setScope("@Scope3")
				.setTarget(StereotypeTarget.FIELD)
				.setInherited(true)
				.setNamed(true)
				.setAlternative(true)
				.generateComments(true))
			.newStereotype();
		
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("S2.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@Stereotype"));
		assertTrue(code.contains("@Scope3"));
		assertTrue(code.contains("@Named"));
		assertTrue(code.contains("@Alternative"));
		assertTrue(code.contains("@Inherited"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ FIELD })"));
		assertTrue(code.startsWith("/**"));

		List<String> iBindings = new ArrayList<String>();
		iBindings.add(getPackageName() + ".B1");
		List<String> stereotypes = new ArrayList<String>();
		stereotypes.add(getPackageName() + ".S1");
		new StereotypeCreator(
			new StereotypeConfiguration()
				.setPackageName(getPackageName())
				.setName("S3")
				.setAlternative(true)
				.setInterceptors(iBindings)
				.setStereotypes(stereotypes))
			.newStereotype();
		
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("S3.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@Stereotype"));
		assertFalse(code.contains("@Scope3"));
		assertFalse(code.contains("@Named"));
		assertTrue(code.contains("@Alternative"));
		assertTrue(code.contains("@B1"));
		assertTrue(code.contains("@S1"));
		assertTrue(code.contains("@Retention(RUNTIME)"));
		assertTrue(code.contains("@Target({ TYPE })"));
		assertFalse(code.contains("@Inherited"));
		assertFalse(code.startsWith("/**"));
	}
	
	private void testDecorator() {
		
		List<String> interfaces = new ArrayList<String>();
		interfaces.add("java.lang.Comparable");
		new DecoratorCreator(
			new DecoratorConfiguration()
				.setPackageName(getPackageName())
				.setName("")
				.setDecoratedInterfaces(interfaces)
				.setPublic(true)
				.setAbstract(true))
			.newDecorator();
		SWTBotEditor ed = new SWTWorkbenchBot().editorByTitle("ComparableDecorator.java");
		assertTrue(("ComparableDecorator.java").equals(ed.getTitle()));
		String code = ed.toTextEditor().getText();
		assertTrue(code.contains("@Decorator"));
		assertTrue(code.contains("abstract class"));
		assertTrue(code.contains("@Delegate"));
		assertTrue(code.contains("@Inject"));
		assertTrue(code.contains("@Any"));
		assertTrue(code.contains("private Comparable<T> comparable;"));
		assertFalse(code.contains("final"));
		assertFalse(code.startsWith("/**"));

		interfaces = new ArrayList<String>();
		interfaces.add("java.util.Map");
		new DecoratorCreator(
			new DecoratorConfiguration()
				.setPackageName(getPackageName())
				.setName("")
				.setDelegateField("field")
				.setDecoratedInterfaces(interfaces)
				.setFinal(true)
				.generateComments(true))
			.newDecorator();
		ed = new SWTWorkbenchBot().editorByTitle("MapDecorator.java");
		assertTrue(("MapDecorator.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@Decorator"));
		assertFalse(code.contains("abstract"));
		assertTrue(code.contains("@Delegate"));
		assertTrue(code.contains("@Inject"));
		assertTrue(code.contains("@Any"));
		assertTrue(code.contains("private Map<K, V> field;"));
		assertTrue(code.contains("final class"));
		assertTrue(code.startsWith("/**"));
	}
	
	private void testInterceptor() {
		
		List<String> interceptors = new ArrayList<String>();
		interceptors.add("B2");
		new InterceptorCreator(
			new InterceptorConfiguration()
				.setPackageName(getPackageName())
				.setName("I1")
				.setInterceptors(interceptors))
			.newInterceptor();
		SWTBotEditor ed = new SWTWorkbenchBot().editorByTitle("I1.java");
		assertTrue(("I1.java").equals(ed.getTitle()));
		String code = ed.toTextEditor().getText();
		assertTrue(code.contains("@B2"));
		assertTrue(code.contains("@Interceptor"));
		assertTrue(code.contains("@AroundInvoke"));
		assertTrue(code.contains("public Object aroundInvoke(InvocationContext ic) throws Exception {"));
		assertFalse(code.contains("final"));
		assertFalse(code.startsWith("/**"));
		
		interceptors = new ArrayList<String>();
		interceptors.add("B4");
		new InterceptorCreator(
			new InterceptorConfiguration()
				.setPackageName(getPackageName())
				.setName("I2")
				.setInterceptors(interceptors)
				.setSuperClass("java.util.Date")
				.setAroundInvokeName("sample")
				.generateComments(true))
			.newInterceptor();
		
		ed = new SWTWorkbenchBot().editorByTitle("I2.java");
		assertTrue(("I2.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("@B4"));
		assertTrue(code.contains("@Interceptor"));
		assertTrue(code.contains("@AroundInvoke"));
		assertTrue(code.contains("public Object sample(InvocationContext ic) throws Exception {"));
		assertFalse(code.contains("final"));
		assertTrue(code.startsWith("/**"));
		assertTrue(code.contains("extends Date"));
	}
	
	private void testBeansXml() {
		ProjectUtil.deleteFolder(
				"beans.xml", getProjectName(), 
				"WebContent", "WEB-INF");
		new ProjectExplorer().getProject(getProjectName()).select();
		NewBeansXMLCreationWizardDialog beansXMLDialog = 
				new NewBeansXMLCreationWizardDialog();
		beansXMLDialog.open();
		beansXMLDialog.finish();
		
		beansXMLDialog.open();
		try {
			beansXMLDialog.finish();
			fail("File 'beans.xml' should not be able to be created again!");
		} catch (JFaceLayerException jfle) {
			beansXMLDialog.cancel();
		}
		
	}
	
	private void testBean() {
		
		new BeanCreator(
			new BeanConfiguration()
				.setPackageName(getPackageName())
				.setName("Bean1")
				.setPublic(true)
				.setAbstract(true))
			.newBean();
		SWTBotEditor ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("Bean1.java").equals(ed.getTitle()));
		String code = ed.toTextEditor().getText();
		assertTrue(code.contains("package cdi;"));
		assertTrue(code.contains("public abstract class Bean1 {"));
		assertFalse(code.contains("@Named"));
		assertFalse(code.contains("final"));
		assertFalse(code.startsWith("/**"));
		
		new BeanCreator(
			new BeanConfiguration()
				.setPackageName(getPackageName())
				.setName("Bean2")
				.setFinal(true)
				.setNamed(true)
				.setScope("@Dependent")
				.generateComments(true))
			.newBean();
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("Bean2.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("package cdi;"));
		assertTrue(code.contains("@Named"));
		assertFalse(code.contains("@Named("));
		assertTrue(code.contains("@Dependent"));
		assertTrue(code.contains("final class Bean2 {"));
		assertTrue(code.startsWith("/**"));

		List<String> qualifiers = new ArrayList<String>();
		qualifiers.add("Q1");
		new BeanCreator(
			new BeanConfiguration()
				.setPackageName(getPackageName())
				.setName("Bean3")
				.setPublic(true)
				.generateComments(true)
				.setBeanName("TestedBean")
				.setScope("@Scope2")
				.setQualifiers(qualifiers))
			.newBean();
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("Bean3.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("package cdi;"));
		assertTrue(code.contains("@Named(\"TestedBean\")"));
		assertTrue(code.contains("@Scope2"));
		assertTrue(code.contains("@Q1"));
		assertTrue(code.contains("public class Bean3 {"));
		assertFalse(code.contains("final"));
		assertTrue(code.startsWith("/**"));
	}
	
	private void testAnnLiteral() {
		
		new AnnotationLiteralCreator(
			new AnnotationLiteralConfiguration()
				.setPackageName(getPackageName())
				.setName("AnnL1")
				.setPublic(true)
				.setFinal(true)
				.setQualifier(getPackageName() + ".Q1"))
			.newAnnotationLiteral();
		SWTBotEditor ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("AnnL1.java").equals(ed.getTitle()));
		String code = ed.toTextEditor().getText();
		assertTrue(code.contains("package cdi;"));
		assertTrue(code.contains("public final class AnnL1 extends AnnotationLiteral<Q1> implements Q1"));
		assertTrue(code.contains("public static final Q1 INSTANCE = new AnnL1();"));
		assertFalse(code.contains("abstract"));
		assertFalse(code.startsWith("/**"));
		
		new AnnotationLiteralCreator(
			new AnnotationLiteralConfiguration()
				.setPackageName(getPackageName())
				.setName("AnnL2")
				.setAbstract(true)
				.generateComments(true)
				.setQualifier(getPackageName() + ".Q2"))
			.newAnnotationLiteral();
		ed = new SWTWorkbenchBot().activeEditor();
		assertTrue(("AnnL2.java").equals(ed.getTitle()));
		code = ed.toTextEditor().getText();
		assertTrue(code.contains("package cdi;"));
		assertTrue(code.contains("abstract class AnnL2 extends AnnotationLiteral<Q2> implements Q2 {"));
		assertTrue(code.contains("public static final Q2 INSTANCE = new AnnL2();"));
		assertFalse(code.substring(code.indexOf("final") + 5).contains("final"));
		assertTrue(code.contains("abstract"));
		assertTrue(code.startsWith("/**"));
	}
}
