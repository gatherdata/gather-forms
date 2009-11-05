/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.core.spi;

import java.net.URL;

import org.gatherdata.commons.spi.StorageService;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.FormTemplateStyle;

/**
 * A Service for storing Form definitions. 
 */
public interface FormCatalogService extends StorageService<FormTemplate>
{
    public Iterable<? extends FormTemplate> getAllStyledAs(FormTemplateStyle style);
    
    /**
     * Transforms Orbeon-styled xhtml into JavaRosa-styled xhtml.
     * 
     * @param xhtml
     * @return
     */
    public String orbeonToRosa(String xhtml);

    public void setOrbeonToRosaXml(URL orb2RosaUrl);

}

