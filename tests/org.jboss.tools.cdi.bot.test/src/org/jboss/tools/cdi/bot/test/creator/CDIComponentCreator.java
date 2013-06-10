package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.CDIComponentConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.CDIConfigurator;
import org.jboss.tools.cdi.reddeer.cdi.ui.CDIComponentWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public abstract class CDIComponentCreator {

	protected void fillDefaults(CDIComponentConfiguration config) {
		if (CDIConfigurator.isConfigurated(config.getSourceFolder()))
			getPage().setSourceFolder(config.getSourceFolder());
		if (CDIConfigurator.isConfigurated(config.getPackageName()))
			getPage().setPackage(config.getPackageName());
		if (CDIConfigurator.isConfigurated(config.getName()))
			getPage().setName(config.getName());
		getPage().generateComments(config.areCommentsGenerated());
	}
	
	public abstract CDIComponentWizardPage getPage();
	
}