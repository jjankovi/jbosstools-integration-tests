package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.BeanConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.CDIConfigurator;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewBeanWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewBeanWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class BeanCreator extends CDIComponentCreator {

	private NewBeanWizardPage beanPage;
	private BeanConfiguration beanConfig;
	
	public BeanCreator(BeanConfiguration beanConfig) {
		this.beanConfig = beanConfig;
	}
	
	public void newBean() {
		NewBeanWizardDialog beanDialog = new NewBeanWizardDialog();
		beanDialog.open();
		beanPage = beanDialog.getFirstPage();
		
		fillDefaults(beanConfig);
		
		if (beanConfig.isPublic())
			beanPage.setPublic();
		if (beanConfig.isDefault())
			beanPage.setDefault();
		beanPage.setAbstract(beanConfig.isAbstract());
		beanPage.setFinal(beanConfig.isFinal());
		if (CDIConfigurator.isConfigurated(beanConfig.getSuperClass())) 
			beanPage.setSuperclassWithBrowse(beanConfig.getSuperClass());
		if (CDIConfigurator.isConfigurated(beanConfig.getInterfaces()))
			beanPage.setInterfaces(beanConfig.getInterfaces());
		beanPage.addNamed(beanConfig.isNamed());
		if (CDIConfigurator.isConfigurated(beanConfig.getBeanName())) {
			beanPage.addNamed(true);
			beanPage.setBeanName(beanConfig.getBeanName());
		}
		beanPage.addAlternative(beanConfig.isAlternative());
		if (beanConfig.isRegisterInBeansXML()) {
			beanPage.addAlternative(true);
			beanPage.registerInBeansXML(true);
		}
		if (CDIConfigurator.isConfigurated(beanConfig.getScope())) 
			beanPage.setScope(beanConfig.getScope());
		if (CDIConfigurator.isConfigurated(beanConfig.getQualifiers())) 
			beanPage.setQualifiers(beanConfig.getQualifiers());
		
		beanDialog.finish();
	}

	@Override
	public NewBeanWizardPage getPage() {
		return beanPage;
	}
	
}
