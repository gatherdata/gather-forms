/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.core.model.impl;

import javax.management.modelmbean.DescriptorSupport;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.gatherdata.commons.model.impl.DescribedEntitySupport;
import org.gatherdata.forms.core.model.FormTemplate;

public class FormTemplateSupport extends DescribedEntitySupport {

    public static boolean deepEquals(FormTemplate lhs, FormTemplate rhs) {
		boolean areEqual = true;

		if (lhs != rhs) { // don't bother comparing object to itself
			areEqual = DescribedEntitySupport.deepEquals(lhs, rhs);

			if (areEqual) { // check FormTemplate extended properties
				areEqual = new EqualsBuilder()
                .append(lhs.getDateModified(), rhs.getDateModified())
                .append(lhs.getFormStyle(), rhs.getFormStyle())
                .append(lhs.getFormTemplate(), rhs.getFormTemplate())
                .append(lhs.getFormType(), rhs.getFormType())
                .isEquals();
			}
		}
        return areEqual;
    }
}
