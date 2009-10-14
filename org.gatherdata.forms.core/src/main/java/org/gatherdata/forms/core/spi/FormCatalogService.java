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

