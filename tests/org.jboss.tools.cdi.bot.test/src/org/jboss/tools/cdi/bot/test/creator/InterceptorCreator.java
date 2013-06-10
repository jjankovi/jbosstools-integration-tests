package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.CDIConfigurator;
import org.jboss.tools.cdi.bot.test.creator.config.InterceptorConfiguration;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewInterceptorWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewInterceptorWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class InterceptorCreator extends CDIComponentCreator {

	private NewInterceptorWizardPage interceptorPage;
	private InterceptorConfiguration interceptorConfig;
	
	public InterceptorCreator(InterceptorConfiguration interceptorConfig) {
		this.interceptorConfig = interceptorConfig;
	}
	
	public void newInterceptor() {
		
		NewInterceptorWizardDialog interceptorDialog = 
				new NewInterceptorWizardDialog();
		interceptorDialog.open();
		interceptorPage = interceptorDialog.getFirstPage();
		
		fillDefaults(interceptorConfig);
		
		if (CDIConfigurator.isConfigurated(
				interceptorConfig.getSuperClass()))
			interceptorPage.setSuperClass(interceptorConfig.getSuperClass());
		if (CDIConfigurator.isConfigurated(interceptorConfig.getInterceptors()))
			interceptorPage.setInterceptorBindings(
					interceptorConfig.getInterceptors());
		if (CDIConfigurator.isConfigurated(interceptorConfig.getAroundInvokeName()))
			interceptorPage.setAroundInvokeMethodName(
					interceptorConfig.getAroundInvokeName());
		interceptorPage.registerInBeansXML(interceptorConfig.isRegisterInBeansXML());

		interceptorDialog.finish();
	}
	
	@Override
	public NewInterceptorWizardPage getPage() {
		return interceptorPage;
	}

}
