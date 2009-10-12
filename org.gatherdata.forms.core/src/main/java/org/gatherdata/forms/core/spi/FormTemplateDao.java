package org.gatherdata.forms.core.spi;

import org.gatherdata.commons.spi.StorageDao;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.FormTemplateStyle;

public interface FormTemplateDao extends StorageDao<FormTemplate> {

    public Iterable<? extends FormTemplate> getAllStyledAs(FormTemplateStyle style);

}
