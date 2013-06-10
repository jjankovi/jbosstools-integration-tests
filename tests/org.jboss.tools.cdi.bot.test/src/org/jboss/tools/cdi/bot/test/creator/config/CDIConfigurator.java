package org.jboss.tools.cdi.bot.test.creator.config;

import java.util.Collection;

/**
 * 
 * @author jjankovi
 *
 */
public class CDIConfigurator {

	public static boolean isConfigurated(String... configuration) {
		if (configuration.length == 0) {
			return false;
		}
		for (String configurationInstance : configuration) {
			if ((configurationInstance == null || 
					configurationInstance.isEmpty() || 
					configurationInstance.equals("")))
				return false;
		}
		return true;
	}
	
	public static boolean isConfigurated(Collection<String> configuration) {
		if (configuration == null || configuration.size() == 0) {
			return false;
		}
		for (String configurationInstance : configuration) {
			if ((configurationInstance == null || 
					configurationInstance.isEmpty() || 
					configurationInstance.equals("")))
				return false;
		}
		return true;
	}
	
}
