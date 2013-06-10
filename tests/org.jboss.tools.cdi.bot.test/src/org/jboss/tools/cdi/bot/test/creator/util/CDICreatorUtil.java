package org.jboss.tools.cdi.bot.test.creator.util;

import org.jboss.tools.cdi.bot.test.uiutils.EditorResourceHelper;
import org.jboss.tools.ui.bot.ext.SWTBotExt;
import org.jboss.tools.ui.bot.ext.SWTBotFactory;

/**
 * 
 * @author jjankovi
 *
 */
public class CDICreatorUtil {

	private static SWTBotExt bot;
	private static EditorResourceHelper editorHelper = 
		new EditorResourceHelper();
	
	public static void fillContentOfEditor(String editorName, String resource) {
		if (resource == null) {
			return;
		}
		bot = SWTBotFactory.getBot();
		
		bot.editorByTitle(editorName).show();
		
		editorHelper.replaceClassContentByResource(
				CDICreatorUtil.class.getResourceAsStream(resource), false);
	}
	
}
