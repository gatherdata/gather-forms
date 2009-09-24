package org.gatherdata.forms.dao.pojo.internal;

import static org.junit.Assert.assertTrue;

import org.gatherdata.commons.net.CbidFactory;
import org.gatherdata.commons.spi.BaseStorageDaoTest;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.impl.MutableFormTemplate;
import org.gatherdata.forms.core.spi.BaseFormTemplateDaoTest;
import org.joda.time.DateTime;
import org.junit.Test;

public class FormTemplateDaoPojoTest extends BaseFormTemplateDaoTest {

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
