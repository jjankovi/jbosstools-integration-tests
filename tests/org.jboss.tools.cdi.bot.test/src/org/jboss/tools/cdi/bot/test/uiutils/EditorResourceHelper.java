/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.cdi.bot.test.uiutils;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.jboss.tools.cdi.bot.test.editor.BeansEditorTest;
import org.jboss.tools.ui.bot.ext.SWTBotExt;
import org.jboss.tools.ui.bot.ext.SWTBotFactory;
import org.jboss.tools.ui.bot.ext.SWTJBTExt;
import org.jboss.tools.ui.bot.ext.SWTTestExt;
import org.jboss.tools.ui.bot.ext.SWTUtilExt;
import org.jboss.tools.ui.bot.ext.Timing;
import org.jboss.tools.ui.bot.ext.parts.ContentAssistBot;
import org.jboss.tools.ui.bot.ext.parts.SWTBotEditorExt;
import org.jboss.tools.ui.bot.ext.view.ProjectExplorer;

public class EditorResourceHelper {
	
	SWTBotExt bot = SWTBotFactory.getBot();
	SWTUtilExt util = SWTBotFactory.getUtil();
	ProjectExplorer projectExplorer = SWTBotFactory.getProjectexplorer();
	
	public void replaceClassContentByResource(InputStream resource, boolean closeEdit) {
		replaceClassContentByResource(resource, true, closeEdit);
	}
	
	/**
	 * method replaces whole content of class "classEdit" by inputstream resource
	 * If closeEdit param is true, editor is not only saved but closed as well 
	 * Prerequisite: editor has been set 
	 * @param classEdit
	 * @param resource
	 * @param closeEdit
	 */
	public void replaceClassContentByResource(InputStream resource, boolean save, boolean closeEdit) {
		SWTBotEclipseEditor eclipseEditor = bot.activeEditor().toTextEditor();
		eclipseEditor.selectRange(0, 0, eclipseEditor.getText().length());
		String code = readStream(resource);
		eclipseEditor.setText(code);
		if (save) eclipseEditor.save();
		if (closeEdit) eclipseEditor.close();
	}
	
	public void replaceClassContentByResource(InputStream resource, boolean closeEdit, String... param) {
		replaceClassContentByResource(resource, true, closeEdit, param);
	}
	
	/**
	 * Method copies resource to class opened in SWTBotEditor with entered parameters
	 * @param classEdit
	 * @param resource
	 * @param closeEdit
	 * @param param
	 */
	public void replaceClassContentByResource(InputStream resource, boolean save, 
			boolean closeEdit, String... param) {
		SWTBotEclipseEditor eclipseEditor = bot.activeEditor().toTextEditor();
		String s = readStream(resource);
		String code = MessageFormat.format(s, param);
		eclipseEditor.selectRange(0, 0, eclipseEditor.getText().length());
		eclipseEditor.setText(code);
		if (save) eclipseEditor.save();
		if (closeEdit) eclipseEditor.close();
	}
	
	/**
	 * method copies resource from folder "src" param to folder "target" param
	 * @param src
	 * @param target
	 */
	public void copyResource(String src, String target) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProjects()[0];
		IFile f = project.getFile(target);
		if (f.exists()) {			
			try {
				f.delete(true, new NullProgressMonitor());
			} catch (CoreException ce) {				
			}
		}
		InputStream is = null;
		try {
			is = BeansEditorTest.class.getResourceAsStream(src);
			f.create(is, true, new NullProgressMonitor());
		} catch (CoreException ce) {			
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ioe) {
					//ignore
				}
			}
		}
	}

	public void replaceInEditor(String target, String replacement) {
		replaceInEditor(target, replacement, true);
	}
	
	/**
	 * Method replaces string "target" by string "replacement.
	 * Prerequisite: editor has been set
	 * @param target
	 * @param replacement
	 */
	public void replaceInEditor(String target, String replacement, boolean save) {
		SWTBotEclipseEditor eclipseEditor = bot.activeEditor().toTextEditor();
		eclipseEditor.selectRange(0, 0, eclipseEditor.getText().length());
		eclipseEditor.setText(eclipseEditor.getText().replace(
				target + (replacement.equals("") ? System
								.getProperty("line.separator") : ""),
				replacement));		
		if (save) eclipseEditor.save();
	}
	
	/**
	 * Method inserts the string "insertText" on location ("line", "column")
	 * Prerequisite: editor has been set
	 * @param line
	 * @param column
	 * @param insertText
	 */
	public void insertInEditor(int line, int column, String insertText) {
		SWTBotEclipseEditor eclipseEditor = bot.activeEditor().toTextEditor();
		eclipseEditor.toTextEditor().insertText(line, column, insertText);
		bot.sleep(Timing.time1S());
		eclipseEditor.save();
	}
	
	/**
	 * Method returns proposal list for given text on given position
	 * @param editorTitle
	 * @param textToSelect
	 * @param selectionOffset
	 * @param selectionLength
	 * @return
	 */
	public List<String> getProposalList(String editorTitle, String textToSelect, int selectionOffset,
			int selectionLength) {
		SWTJBTExt.selectTextInSourcePane(bot,
		        editorTitle, textToSelect, selectionOffset, selectionLength,
		        0);

		bot.sleep(Timing.time1S());
		    
		SWTBotEditorExt editor = SWTTestExt.bot.swtBotEditorExtByTitle(editorTitle);
		ContentAssistBot contentAssist = editor.contentAssist();
		List<String> currentProposalList = contentAssist.getProposalList();
		return currentProposalList;
	}

	/**
	 * Methods converts input stream to string component
	 * @param inputStream
	 * @return String - input stream converted to string
	 */
	@SuppressWarnings("resource")
	public String readStream(InputStream inputStream) {
		// we don't care about performance in tests too much, so this should be
		// OK
		return new Scanner(inputStream).useDelimiter("\\A").next();
	}

}