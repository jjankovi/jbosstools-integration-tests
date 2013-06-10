package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.CDIConfigurator;
import org.jboss.tools.cdi.bot.test.creator.config.DecoratorConfiguration;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewDecoratorCreationWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewDecoratorCreationWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class DecoratorCreator extends CDIComponentCreator {

	private DecoratorConfiguration decoratorConfig;
	private NewDecoratorCreationWizardPage decoratorPage;
	
	public DecoratorCreator(DecoratorConfiguration decoratorConfig) {
		this.decoratorConfig = decoratorConfig;
	}
	
	public void newDecorator() {
		NewDecoratorCreationWizardDialog decoratorDialog = 
				new NewDecoratorCreationWizardDialog();
		decoratorDialog.open();
		decoratorPage = decoratorDialog.getFirstPage();
		
		fillDefaults(decoratorConfig);
		
		if (decoratorConfig.isPublic())
			decoratorPage.setPublic();
		if (decoratorConfig.isDefault())
			decoratorPage.setDefault();
		decoratorPage.setAbstract(decoratorConfig.isAbstract());
		decoratorPage.setFinal(decoratorConfig.isFinal());
		if (CDIConfigurator.isConfigurated(decoratorConfig.getDecoratedInterfaces()))
			decoratorPage.setDecoratedType(decoratorConfig.getDecoratedInterfaces());
		if (CDIConfigurator.isConfigurated(decoratorConfig.getDelegateField()))
			decoratorPage.setDelegateFieldName(decoratorConfig.getDelegateField());
		decoratorPage.registerInBeansXML(decoratorConfig.isRegisterInBeansXML());
		
		decoratorDialog.finish();
	}

	@Override
	public NewDecoratorCreationWizardPage getPage() {
		return decoratorPage;
	}
	
}
