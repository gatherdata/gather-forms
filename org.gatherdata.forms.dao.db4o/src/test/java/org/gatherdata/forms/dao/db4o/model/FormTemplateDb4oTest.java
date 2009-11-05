/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
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
