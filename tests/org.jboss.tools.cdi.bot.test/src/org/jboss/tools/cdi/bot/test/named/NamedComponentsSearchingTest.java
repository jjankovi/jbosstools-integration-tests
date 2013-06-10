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

package org.jboss.tools.cdi.bot.test.named;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.tools.cdi.bot.test.CDITestBase;
import org.jboss.tools.cdi.bot.test.creator.BeanCreator;
import org.jboss.tools.cdi.bot.test.creator.StereotypeCreator;
import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.StereotypeConfiguration;
import org.jboss.tools.cdi.bot.test.creator.util.CDICreatorUtil;
import org.jboss.tools.cdi.bot.test.util.ProjectUtil;
import org.jboss.tools.cdi.reddeer.ui.OpenCDINamedBeanDialog;
import org.jboss.tools.ui.bot.ext.Timing;
import org.jboss.tools.ui.bot.ext.types.IDELabel;
import org.junit.After;
import org.junit.Test;

/**
 * Test operates on @Named searching  
 * 
 * @author Jaroslav Jankovic
 * 
 */

public class NamedComponentsSearchingTest extends CDITestBase {

	private static final String beanName = "Bean1";
	private static final String stereotypeName = "Stereotype1";
	private OpenCDINamedBeanDialog namedDialog = null;
	private static final String BEAN_STEREOTYPE_PATH = "/resources/named/BeanWithStereotype.java.cdi";
	private static final String BEAN_STEREOTYPE_NAMED_PATH = "/resources/named/BeanWithStereotypeAndNamed.java.cdi";

	@After
	public void waitForJobs() {
		ProjectUtil.deletePackage(getProjectName(), getPackageName());
	}
	
	@Test
	public void testSearchDefaultNamedBean() {
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(beanName)
			.setPublic(true)
			.setNamed(true)).newBean();
		namedDialog = openSearchNamedDialog();		
		namedDialog.setELPrefix(beanName);
		assertTrue(namedDialog.getMatchingItems().size() == 1);
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
		
	}
	
	@Test
	public void testSearchNamedParameterBean() {
		
		String namedParam = "someBean";
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(beanName)
			.setPublic(true)
			.setNamed(true)
			.setBeanName(namedParam)).newBean();
		namedDialog = openSearchNamedDialog();
		namedDialog.setELPrefix(namedParam);		
		assertTrue(namedDialog.getMatchingItems().size() == 1);
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
	
	}
	
	@Test
	public void testSearchNamedParameterChangeBean() {
				
		String namedParam = "someBean";
		String changedNamedParam = "someOtherBean";
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(beanName)
			.setPublic(true)
			.setNamed(true)
			.setBeanName(namedParam)).newBean();
		namedDialog = openSearchNamedDialog();
		namedDialog.setELPrefix(namedParam);		
		assertTrue(namedDialog.getMatchingItems().size() == 1);
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
		
		editResourceUtil.replaceInEditor(namedParam, changedNamedParam);
		
		namedDialog = openSearchNamedDialog();
		namedDialog.setELPrefix(namedParam);		
		assertTrue(namedDialog.getMatchingItems().size() == 0);
		namedDialog.setELPrefix(changedNamedParam);
		assertTrue(namedDialog.getMatchingItems().size() == 1);
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
		
	}
	
	@Test
	public void testSearchTwoSameNamedBean() {
				
		String beanName2 = "Bean2";
		String namedParam = "someBean";
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(beanName)
			.setPublic(true)
			.setNamed(true)
			.setBeanName(namedParam)).newBean();
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(beanName2)
			.setPublic(true)
			.setNamed(true)
			.setBeanName(namedParam)).newBean();

		namedDialog = openSearchNamedDialog();
		namedDialog.setELPrefix(namedParam);
		List<String> matchingItems = namedDialog.getMatchingItems();
		assertTrue(matchingItems.size() == 2);
		for (String matchingItem : matchingItems) {
			if (matchingItem.contains(beanName)) {
				namedDialog.selectItems(matchingItem);
				break;
			}
		}		
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
		
	}
	
	@Test
	public void testSearchBeansWithSamePrefixNamedParam() {
	
		String[] beansNames = {"SomeBean", "SomeBean1", "SomeBean2", "SomeBean22", "SomeOtherBean"};
		
		Map<String, Integer> prefixesWithCount = new LinkedHashMap<String, Integer>();
		prefixesWithCount.put(beansNames[0], 4);
		prefixesWithCount.put(beansNames[1], 1);
		prefixesWithCount.put(beansNames[2], 2);
		prefixesWithCount.put(beansNames[3], 1);
		prefixesWithCount.put("Some", 5);
		
		for (String beanName : beansNames) {
			new BeanCreator(new BeanConfiguration()
				.setPackageName(getPackageName())
				.setName(beanName)
				.setPublic(true)
				.setNamed(true)).newBean();
		}
		
		for (String prefix : prefixesWithCount.keySet()) {
			namedDialog = openSearchNamedDialog();
			namedDialog.setELPrefix(prefix);		
			assertTrue(namedDialog.getMatchingItems().size() == prefixesWithCount.get(prefix));
			namedDialog.cancel();			
		}
		
	}
	
	@Test
	public void testSearchBeanWithStereotype() {
		
		new StereotypeCreator(new StereotypeConfiguration()
			.setPackageName(getPackageName())
			.setName(stereotypeName)
			.setNamed(true)).newStereotype();
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(beanName)).newBean();
		CDICreatorUtil.fillContentOfEditor(beanName + ".java", BEAN_STEREOTYPE_PATH);
		
		namedDialog = openSearchNamedDialog();
		namedDialog.setELPrefix(beanName);		
		assertTrue(namedDialog.getMatchingItems().size() == 1);
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
		
	}
	
	@Test
	public void testSearchBeanWithStereotypeAndNamedParam() {
		
		String namedParam = "someBean";
		new StereotypeCreator(new StereotypeConfiguration()
			.setPackageName(getPackageName())
			.setName(stereotypeName)
			.setNamed(true)).newStereotype();;
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(beanName)).newBean();
		CDICreatorUtil.fillContentOfEditor(beanName + ".java", BEAN_STEREOTYPE_NAMED_PATH);
		
		namedDialog = openSearchNamedDialog();
		namedDialog.setELPrefix(beanName);		
		assertTrue(namedDialog.getMatchingItems().size() == 0);
		namedDialog.setELPrefix(namedParam);
		assertTrue(namedDialog.getMatchingItems().size() == 1);
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
	}
	
	@Test
	public void testSearchBeanWithStereotypeWithNamedParamChange() {
		
		String namedParam = "someBean";
		String changedNamedParam = "someOtherBean";
		
		new StereotypeCreator(new StereotypeConfiguration()
			.setPackageName(getPackageName())
			.setName(stereotypeName)
			.setNamed(true)).newStereotype();;
		
		new BeanCreator(new BeanConfiguration()
			.setPackageName(getPackageName())
			.setName(beanName)).newBean();
		CDICreatorUtil.fillContentOfEditor(beanName + ".java", BEAN_STEREOTYPE_NAMED_PATH);
			
		namedDialog = openSearchNamedDialog();
		namedDialog.setELPrefix(beanName);		
		assertTrue(namedDialog.getMatchingItems().size() == 0);
		namedDialog.setELPrefix(namedParam);
		assertTrue(namedDialog.getMatchingItems().size() == 1);
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
		
		editResourceUtil.replaceInEditor(namedParam, changedNamedParam);
		
		namedDialog = openSearchNamedDialog();
		namedDialog.setELPrefix(namedParam);		
		assertTrue(namedDialog.getMatchingItems().size() == 0);
		namedDialog.setELPrefix(changedNamedParam);
		assertTrue(namedDialog.getMatchingItems().size() == 1);
		namedDialog.ok();
		assertTrue(bot.activeEditor().getTitle().equals(beanName + ".java"));
		assertTrue(bot.activeEditor().toTextEditor().getSelection().equals(beanName));
	}
	
	
	private OpenCDINamedBeanDialog openSearchNamedDialog() {
		bot.sleep(Timing.time2S());
		new ShellMenu(
				IDELabel.Menu.NAVIGATE, 
				IDELabel.Menu.OPEN_CDI_NAMED_BEAN).select();
		return new OpenCDINamedBeanDialog();
	}

}
