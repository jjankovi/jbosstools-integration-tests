package org.jboss.tools.cdi.bot.test.creator;

import org.jboss.tools.cdi.bot.test.creator.config.AnnotationLiteralConfiguration;
import org.jboss.tools.cdi.bot.test.creator.config.CDIConfigurator;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewAnnotationLiteralWizardDialog;
import org.jboss.tools.cdi.reddeer.cdi.ui.NewAnnotationLiteralWizardPage;

/**
 * 
 * @author jjankovi
 *
 */
public class AnnotationLiteralCreator extends CDIComponentCreator {

	private AnnotationLiteralConfiguration aLiteralConfig;
	private NewAnnotationLiteralWizardPage aLiteralPage;
	
	public AnnotationLiteralCreator(
			AnnotationLiteralConfiguration aLiteralConfig) {
		this.aLiteralConfig = aLiteralConfig;
	}
	
	public void newAnnotationLiteral() {
		NewAnnotationLiteralWizardDialog aLiteralDialog = 
				new NewAnnotationLiteralWizardDialog();
		aLiteralDialog.open();
		aLiteralPage = aLiteralDialog.getFirstPage();
		
		fillDefaults(aLiteralConfig);
		
		if (aLiteralConfig.isPublic())
			aLiteralPage.setPublic();
		if (aLiteralConfig.isDefault())
			aLiteralPage.setDefault();
		aLiteralPage.setAbstract(aLiteralConfig.isAbstract());
		aLiteralPage.setFinal(aLiteralConfig.isFinal());
		if (CDIConfigurator.isConfigurated(aLiteralConfig.getQualifier()))
			aLiteralPage.setQualifier(aLiteralConfig.getQualifier());
		
		aLiteralDialog.finish();
	}

	@Override
	public NewAnnotationLiteralWizardPage getPage() {
		return aLiteralPage;
	}
	
}
