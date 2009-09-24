package org.gatherdata.forms.dao.pojo.internal;

import static org.junit.Assert.assertTrue;

import org.gatherdata.commons.net.CbidFactory;
import org.gatherdata.commons.spi.BaseStorageDaoTest;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.MutableFormTemplate;
import org.joda.time.DateTime;
import org.junit.Test;

public class FormTemplateDaoPojoTest extends BaseStorageDaoTest<FormTemplate, FormTemplateDaoPojo> {

    private int mockCount = 0;

    @Override
    protected FormTemplate createMockEntity() {
        MutableFormTemplate mockTemplate = new MutableFormTemplate();
        mockTemplate.setUid(CbidFactory.createCbid("mockFormTemplate #:" + mockCount));
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

    @Override
    protected FormTemplateDaoPojo createStorageDaoImpl() {
        return new FormTemplateDaoPojo();
    }
    
    @Override
    protected void beginTransaction() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void endTransaction() {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Eclipse will run inherited tests, but won't 
     * notice them without at least one test in the class.
     */
    @Test
    public void shouldRunInheritedTestsInEclipse() {
        assertTrue(true);
    }

}
