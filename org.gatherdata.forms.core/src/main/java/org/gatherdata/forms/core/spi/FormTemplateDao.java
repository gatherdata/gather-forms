/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.core.spi;

import org.gatherdata.commons.spi.StorageDao;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.FormTemplateStyle;

public interface FormTemplateDao extends StorageDao<FormTemplate> {

    public Iterable<? extends FormTemplate> getAllStyledAs(FormTemplateStyle style);

}
