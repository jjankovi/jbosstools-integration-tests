package org.jboss.tools.cdi.reddeer.cdi.ui;


/**
 * 
 * @author jjankovi
 *
 */
public class NewAnnotationLiteralWizardDialog extends CDIComponentWizardDialog {

	public NewAnnotationLiteralWizardDialog() {
		super("Annotation Literal");
	}
	
	@Override
	public NewAnnotationLiteralWizardPage getFirstPage() {
		return new NewAnnotationLiteralWizardPage();
	}

}
