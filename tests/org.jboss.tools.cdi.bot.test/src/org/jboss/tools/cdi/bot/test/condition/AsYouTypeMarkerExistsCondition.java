/*******************************************************************************
 * Copyright (c) 2010-2013 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package org.jboss.tools.cdi.bot.test.condition;

import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.tools.cdi.bot.test.util.AsYouTypeValidationUtil;

/**
 * 
 * @author jjankovi
 *
 */
public class AsYouTypeMarkerExistsCondition implements WaitCondition {

	private String message;
	
	public AsYouTypeMarkerExistsCondition(String message) {
		this.message = message;
	}
	
	public boolean test() {
		return AsYouTypeValidationUtil.markerExists(
			AsYouTypeValidationUtil.getAnnotationModel(), null, message);
	}

	public String description() {
		return "No as-you-type marker exists in '" + 
				AsYouTypeValidationUtil.getActiveTextEditor().getTitle() + "'";
	}

}
