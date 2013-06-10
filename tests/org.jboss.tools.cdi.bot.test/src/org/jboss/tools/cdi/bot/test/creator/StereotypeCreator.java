package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.CDIConfigurator;
import org.jboss.tools.cdi.bot.test.creator.config.StereotypeConfiguration;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewStereotypeWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewStereotypeWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class StereotypeCreator extends CDIComponentCreator {

	private NewStereotypeWizardPage stereotypePage;
	private StereotypeConfiguration stereotypeConfig;
	
	public StereotypeCreator(StereotypeConfiguration stereotypeConfig) {
		this.stereotypeConfig = stereotypeConfig;
	}

	public void newStereotype() {
		
		NewStereotypeWizardDialog stereotypeDialog = 
				new NewStereotypeWizardDialog();  
		stereotypeDialog.open();
		stereotypePage = stereotypeDialog.getFirstPage();
		
		fillDefaults(stereotypeConfig);
		
		stereotypePage.addInherited(stereotypeConfig.isInherited());
		stereotypePage.addAlternative(stereotypeConfig.isAlternative());
		stereotypePage.registerInBeansXML(
				stereotypeConfig.isRegisterInBeansXML());
		stereotypePage.addNamed(stereotypeConfig.isNamed());
		if (CDIConfigurator.isConfigurated(stereotypeConfig.getScope()))
			stereotypePage.setScope(stereotypeConfig.getScope());
		if (stereotypeConfig.getTarget() != null)
			stereotypePage.setTarget(stereotypeConfig.getTarget());
		if (CDIConfigurator.isConfigurated(stereotypeConfig.getInterceptors()))
			stereotypePage.setInterceptorBindings(
					stereotypeConfig.getInterceptors());
		if (CDIConfigurator.isConfigurated(stereotypeConfig.getStereotypes()))
			stereotypePage.setStereotypes(stereotypeConfig.getStereotypes());

		stereotypeDialog.finish();
	}

	@Override
	public NewStereotypeWizardPage getPage() {
		return stereotypePage;
	}

}
