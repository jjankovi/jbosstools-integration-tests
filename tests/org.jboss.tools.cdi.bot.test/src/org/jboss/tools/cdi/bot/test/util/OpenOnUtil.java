package org.jboss.tools.cdi.bot.test.util;

import org.jboss.tools.cdi.bot.test.uiutils.wizards.OpenOnOptionsDialog;
import org.jboss.tools.ui.bot.ext.SWTBotExt;
import org.jboss.tools.ui.bot.ext.SWTBotFactory;
import org.jboss.tools.ui.bot.ext.SWTJBTExt;
import org.jboss.tools.ui.bot.ext.Timing;
import org.jboss.tools.ui.bot.ext.types.IDELabel;

/**
 * 
 * @author jjankovi
 *
 */
public class OpenOnUtil {

	private static SWTBotExt bot = SWTBotFactory.getBot();
	
	/**
	 * Method select openOnString and then open proposal dialog which
	 * is returned as object
	 * @param openOnString
	 * @param titleName
	 * @return
	 */
	public static OpenOnOptionsDialog openOnDialog(String openOnString, String titleName) {
		SWTJBTExt.selectTextInSourcePane(bot, titleName,
				openOnString, 0, openOnString.length());
		bot.menu(IDELabel.Menu.EDIT).menu(IDELabel.Menu.QUICK_FIX).click();	
		bot.sleep(Timing.time1S());
		
		return new OpenOnOptionsDialog(bot);
	}
}
