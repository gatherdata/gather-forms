/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.dao.pojo.osgi;

import static org.ops4j.peaberry.Peaberry.service;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import org.gatherdata.forms.core.spi.FormTemplateDao;
import org.gatherdata.forms.dao.pojo.internal.FormTemplateDaoPojo;
import org.ops4j.peaberry.Export;

import static org.ops4j.peaberry.util.TypeLiterals.iterable;
import static org.ops4j.peaberry.util.TypeLiterals.export;

/**
 * The GuiceBindingModule specifies bindings to provided and
 * consumed services for this bundle using Google Guice with
 * Peaberry extensions for OSGi.
 *
 */
public class GuiceBindingModule extends AbstractModule {

    
    @Override 
    protected void configure() {
        // imports
        //bind(ArchiverDao.class).toProvider(service(ArchiverDao.class).single());
        
        // exports
        bind(export(FormTemplateDao.class)).toProvider(service(FormTemplateDaoPojo.class).export());
        
    }
}
