package org.gatherdata.forms.dao.db4o.model;

import static org.junit.Assert.assertTrue;

import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.MockFormTemplateFactory;
import org.gatherdata.forms.core.model.impl.FormTemplateSupport;
import org.junit.Test;

public class FormTemplateDb4oTest {

	@Test
	public void shouldDeeplyEqualCopyOfOriginal() {
		FormTemplate original = MockFormTemplateFactory.createMock();
		FormTemplateDb4o db4oVersion = new FormTemplateDb4o();
		db4oVersion.copy(original);
		
		assertTrue(FormTemplateSupport.deepEquals(original, db4oVersion));
	}
}
