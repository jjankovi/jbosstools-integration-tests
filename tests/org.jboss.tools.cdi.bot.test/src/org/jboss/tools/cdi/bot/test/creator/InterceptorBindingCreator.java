package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.CDIConfigurator;
import org.jboss.tools.cdi.bot.test.creator.config.InterceptorBindingConfiguration;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewInterceptorBindingWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewInterceptorBindingWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class InterceptorBindingCreator extends CDIComponentCreator {

	private NewInterceptorBindingWizardPage iBindingPage;
	private InterceptorBindingConfiguration iBindingConfig;
	
	public InterceptorBindingCreator(
			InterceptorBindingConfiguration iBindingConfig) {
		this.iBindingConfig = iBindingConfig;
	}

	public void newInterceptorBinding() {
		
		NewInterceptorBindingWizardDialog iBindingDialog = 
				new NewInterceptorBindingWizardDialog();
		iBindingDialog.open();
		iBindingPage = iBindingDialog.getFirstPage();
		
		fillDefaults(iBindingConfig);
		
		iBindingPage.addInherited(iBindingConfig.isInherited());
		if (iBindingConfig.getTarget() != null)
			iBindingPage.setTarget(iBindingConfig.getTarget());
		if (CDIConfigurator.isConfigurated(iBindingConfig.getInterceptors()))
			iBindingPage.setInterceptorBindings(iBindingConfig.getInterceptors());
		
		iBindingDialog.finish();
	}

	@Override
	public NewInterceptorBindingWizardPage getPage() {
		return iBindingPage;
	}

}
