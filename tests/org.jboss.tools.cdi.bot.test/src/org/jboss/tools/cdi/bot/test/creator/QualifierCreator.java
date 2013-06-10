package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.QualifierConfiguration;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewQualifierWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewQualifierWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class QualifierCreator extends CDIComponentCreator {

	private NewQualifierWizardPage qualifierPage;
	private QualifierConfiguration qualifierConfig;
	
	public QualifierCreator(QualifierConfiguration qualifierConfig) {
		this.qualifierConfig = qualifierConfig;
	}
	
	public void newQualifier() {
		
		NewQualifierWizardDialog qualifierDialog = 
				new NewQualifierWizardDialog();  
		qualifierDialog.open();
		qualifierPage = qualifierDialog.getFirstPage();
		
		fillDefaults(qualifierConfig);
		
		qualifierPage.addInherated(qualifierConfig.isInherated());
		
		qualifierDialog.finish();
	}
	
	@Override
	public NewQualifierWizardPage getPage() {
		return qualifierPage;
	}

}
