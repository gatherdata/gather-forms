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
