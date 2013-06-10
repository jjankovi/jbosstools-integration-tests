package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.BeansXMLConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.CDIConfigurator;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewBeansXMLCreationWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewBeansXMLCreationWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class BeansXMLCreator {

	private NewBeansXMLCreationWizardPage beansXMLPage;
	private BeansXMLConfiguration beansXMLConfig;
	
	public BeansXMLCreator(BeansXMLConfiguration beansXMLConfig) {
		this.beansXMLConfig = beansXMLConfig;
	}
	
	public void newBeanXML() {
		
		NewBeansXMLCreationWizardDialog beansXMLDialog = 
				new NewBeansXMLCreationWizardDialog();
		beansXMLDialog.open();
		
		beansXMLPage = beansXMLDialog.getFirstPage();
		
		if (CDIConfigurator.isConfigurated(beansXMLConfig.getParentFolder()))
			beansXMLPage.setParentFolder(beansXMLConfig.getParentFolder());
		if (CDIConfigurator.isConfigurated(beansXMLConfig.getFileName()))
			beansXMLPage.setFileName(beansXMLConfig.getFileName());
		
		beansXMLDialog.finish();
		
	}
	
}
