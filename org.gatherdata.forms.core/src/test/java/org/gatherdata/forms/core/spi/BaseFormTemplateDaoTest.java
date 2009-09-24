package org.gatherdata.forms.core.spi;

import org.gatherdata.commons.spi.BaseStorageDaoTest;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.MockFormTemplateFactory;

public abstract class BaseFormTemplateDaoTest extends BaseStorageDaoTest<FormTemplate, FormTemplateDao> {
    
    @Override
    protected FormTemplate createMockEntity() {
    	return MockFormTemplateFactory.createMock();
    }

}