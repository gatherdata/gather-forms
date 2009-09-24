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
