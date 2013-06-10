package org.jboss.tools.cdi.reddeer.cdi.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.condition.TableHasRows;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.RadioButton;
import org.jboss.reddeer.swt.impl.combo.DefaultCombo;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.table.DefaultTable;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * 
 * @author jjankovi
 *
 */
public class CDIWizardPageUtils {

	public static void setSourceFolder(String sourceFolder) {
		new LabeledText("Source folder:").setText(sourceFolder);
	}
	
	public static void setSourceFolderWithBrowse(String... sourceFolderPath) {
		final String sourceFolderSelection = "Source Folder Selection"; 
		new PushButton(0).click();
		new DefaultShell(sourceFolderSelection);
		new DefaultTreeItem(sourceFolderPath).select();
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(sourceFolderSelection));
	}
	
	public static String getSourceFolder() {
		return new LabeledText("Source folder:").getText();
	}
	
	public static void setPackage(String packageName) {
		new LabeledText("Package:").setText(packageName);
	}
	
	public static void setPackageWithBrowse(String packageName) {
		final String packageSelection = "Package Selection"; 
		new PushButton(1).click();
		new DefaultShell(packageSelection);
		new DefaultTable().select(packageName);
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(packageSelection));
	}
	
	public static String getPackage() {
		return new LabeledText("Package:").getText();
	}
	
	public static void setName(String name) {
		new LabeledText("Name:").setText(name);
	}
	
	public static String getName() {
		return new LabeledText("Name:").getText();
	}
	
	public static void setPublic() {
		new RadioButton("public").click();
	}
	
	public static void setDefault() {
		new RadioButton("default").click();
	}
	
	public static void setAbstract(boolean toggle) {
		new CheckBox("abstract").toggle(toggle);
	}
	
	public static void setFinal(boolean toggle) {
		new CheckBox("final").toggle(toggle);
	}
	
	public static void setSuperclass(String superClass) {
		new LabeledText("Superclass:").setText(superClass);
	}
	
	public static void setSuperclassWithBrowse(String superClass) {
		final String superclassSelection = "Superclass Selection"; 
		new PushButton(2).click();
		new DefaultShell(superclassSelection);
		new LabeledText("Choose a type:").setText(superClass);
		new WaitUntil(new TableHasRows(new DefaultTable()));
		new DefaultTable().select(0);
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(superclassSelection));
	}
	
	public static void setQualifier(String qualifier) {
		new LabeledText("Qualifier:").setText(qualifier);
	}
	
	public static void setQualifierWithBrowse(String qualifier) {
		final String qualifierAnnotationType= "Select Qualifier Annotation Type"; 
		new PushButton(2).click();
		new DefaultShell(qualifierAnnotationType);
		new DefaultText(0).setText(qualifier);
		new WaitUntil(new TableHasRows(new DefaultTable()));
		new DefaultTable().select(0);
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(qualifierAnnotationType));
	}
	
	public static void setQualifiers(List<String> qualifiers) {
		for (String qualifier : qualifiers) {
			final String qualifierAnnotationType = 
					"Select Qualifier Annotation Type"; 
			new PushButton(5).click();
			new DefaultShell(qualifierAnnotationType);
			new DefaultText(0).setText(qualifier);
			new WaitUntil(new TableHasRows(new DefaultTable()));
			new DefaultTable().select(0);
			new PushButton("OK").click();
			new WaitWhile(new ShellWithTextIsActive(
					qualifierAnnotationType));
		}
	}
	
	public static void setInterfaces(List<String> interfaces, int buttonIndex) {
		final String interfaceSelection = "Implemented Interfaces Selection"; 
		new PushButton(buttonIndex).click();
		new DefaultShell(interfaceSelection);
		for (String interfaceName : interfaces) {
			new DefaultText(0).setText(interfaceName);
			new WaitUntil(new TableHasRows(new DefaultTable()));
			new DefaultTable().select(0);
			new PushButton("Add").click();
		}
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(interfaceSelection));
	}
	
	public static List<String> getDecoratedInterfaces() {
		List<String> decoratedInterfaces = new ArrayList<String>();
		Table interfaces = new DefaultTable();
		int count = interfaces.rowCount();
		for (int i = 0; i < count; i++) {
			decoratedInterfaces.add(interfaces.cell(i, 0));
		}
		return decoratedInterfaces;
	}
	
	public static void setTarget(String target) {
		new DefaultCombo("Target:").setSelection(target);
	}
	
	public static void setInterceptorBindings(List<String> interceptors) {
		for (String interceptor: interceptors) {
			final String iBindingType = 
					"Select Interceptor Binding Annotation Type"; 
			new PushButton("Add").click();
			new DefaultShell(iBindingType);
			new DefaultText(0).setText(interceptor);
			new WaitUntil(new TableHasRows(new DefaultTable()));
			new DefaultTable().select(0);
			new PushButton("OK").click();
			new WaitWhile(new ShellWithTextIsActive(
					iBindingType));
		}
	}
	
	public static void setStereotypes(List<String> stereotypes) {
		for (String stereotype: stereotypes) {
			final String stereotypeType = 
					"Select Stereotype Annotation Type"; 
			new PushButton(4).click();
			new DefaultShell(stereotypeType);
			new DefaultText(0).setText(stereotype);
			new WaitUntil(new TableHasRows(new DefaultTable()));
			new DefaultTable().select(0);
			new PushButton("OK").click();
			new WaitWhile(new ShellWithTextIsActive(
					stereotypeType));
		}
	}
	
	public static void setParentFolder(String parentFolder) {
		new DefaultText(0).setText(parentFolder);
	}
	
	public static void setFileName(String fileName) {
		new LabeledText("File name:").setText(fileName);
	}
	
	public static void setAroundInvokeMethodName(String methodName) {
		new LabeledText("Around Invoke Method Name:").setText(methodName);
	}
	
	public static void setDelegateFieldName(String delegate) {
		new LabeledText("Delegate Field Name:").setText(delegate);
	}
	
	public static void addInherated(boolean toggle) {
		new CheckBox("Add @Inherited").toggle(toggle);
	}
	
	public static void setNormalScope(boolean toggle) {
		new CheckBox("is normal scope").toggle(toggle);
	}
	
	public static void setPassivating(boolean toggle) {
		new CheckBox("is passivating").toggle(toggle);
	}
	
	public static void addNamed(boolean toggle) {
		new CheckBox("Add @Named").toggle(toggle);
	}
	
	public static void setBeanName(String beanName) {
		new LabeledText("Bean Name:").setText(beanName);
	}
	
	public static void addAlternative(boolean toggle) {
		new CheckBox("Add @Alternative").toggle(toggle);
	}
	
	public static void registerInBeansXML(boolean toggle) {
		new CheckBox("Register in beans.xml").toggle(toggle);
	}
	
	public static void setScope(String scope) {
		new DefaultCombo("Scope:").setSelection(scope);
	}
	
	public static void generateComments(boolean toggle) {
		new CheckBox("Generate comments").toggle(toggle);
	}
	
}
