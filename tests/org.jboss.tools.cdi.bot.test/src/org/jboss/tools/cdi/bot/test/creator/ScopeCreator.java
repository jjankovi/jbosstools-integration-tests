package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.ScopeConfiguration;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewScopeCreationWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewScopeCreationWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class ScopeCreator extends CDIComponentCreator {
	
	private NewScopeCreationWizardPage scopePage;
	private ScopeConfiguration scopeConfig;
	
	public ScopeCreator(ScopeConfiguration scopeConfig) {
		this.scopeConfig = scopeConfig;
	}

	public void newScope() {
		
		NewScopeCreationWizardDialog scopeDialog = 
				new NewScopeCreationWizardDialog();  
		scopeDialog.open();
		scopePage = scopeDialog.getFirstPage();
		
		fillDefaults(scopeConfig);
		
		scopePage.addInherited(scopeConfig.isInherited());
		scopePage.setNormalScope(scopeConfig.isNormalScope());
		scopePage.setPassivating(scopeConfig.isPassivating());
		
		scopeDialog.finish();
	}

	@Override
	public NewScopeCreationWizardPage getPage() {
		return scopePage;
	}

}
