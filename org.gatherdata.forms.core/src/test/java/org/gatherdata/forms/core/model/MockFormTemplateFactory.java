/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.core.model;

import org.gatherdata.commons.net.CbidFactory;
import org.gatherdata.forms.core.model.impl.MutableFormTemplate;
import org.joda.time.DateTime;

public class MockFormTemplateFactory {

	private static int mockCount = 0;

	public static FormTemplate createMock() {
        MutableFormTemplate mockTemplate = new MutableFormTemplate();
        mockTemplate.setUid(CbidFactory.createCbid("mockFormTemplate #:" + mockCount ));
        DateTime dateModified = new DateTime();
        DateTime dateCreated = dateModified.minusMinutes(1);
        mockTemplate.setDateCreated(dateCreated);
        mockTemplate.setDateModified(dateModified);
        mockTemplate.setDescription("a mock form template #" + mockCount);
        mockTemplate.setFormStyle("mock");
        mockTemplate.setFormTemplate("name: %x");
        mockTemplate.setFormType("text/plain");
        mockTemplate.setName("mock #" + mockCount);
        mockCount++;
        return mockTemplate;
	}
	
}
