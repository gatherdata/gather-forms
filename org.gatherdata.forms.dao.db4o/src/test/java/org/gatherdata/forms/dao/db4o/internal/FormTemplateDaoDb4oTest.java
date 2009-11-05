/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.dao.db4o.internal;

import org.gatherdata.forms.core.spi.BaseFormTemplateDaoTest;
import org.gatherdata.forms.core.spi.FormTemplateDao;
import org.junit.After;

import com.db4o.ObjectContainer;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class FormTemplateDaoDb4oTest extends BaseFormTemplateDaoTest {

    @Inject
    ObjectContainer db4o;

    @Override
    protected FormTemplateDao createStorageDaoImpl() {
    	FormTemplateDao dao = new FormTemplateDaoDb4o();
        
        // guice up the instance
        Injector injector = Guice.createInjector(new Db4oTestingModule());
        injector.injectMembers(this);
        injector.injectMembers(dao);
        
        return dao;
    }
    
    @After
    public void shutdownDatabase() {
        db4o.commit();
        db4o.close();
    }
    
    @Override
    protected void beginTransaction() {
        ;
    }

    @Override
    protected void endTransaction() {
        ;
    }
}