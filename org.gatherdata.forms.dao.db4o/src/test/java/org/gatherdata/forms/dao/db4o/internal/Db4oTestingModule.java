/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.dao.db4o.internal;

import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.TypeLiterals.export;

import java.io.File;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.io.MemoryStorage;
import com.db4o.osgi.Db4oService;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class Db4oTestingModule extends AbstractModule {
    
    @Override 
    protected void configure() {
        
        // local binds
        bind(ObjectContainer.class).to(EmbeddedObjectContainer.class).in(Singleton.class);
        
    }
    
    /**
     * Constructs an in-memory database.
     * 
     * @return
     */
    @Provides
    public EmbeddedObjectContainer createObjectContainer() {
        MemoryStorage ms = new MemoryStorage();
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.file().storage(ms);

        return Db4oEmbedded.openFile(config, "in-memory");
    }
        
}
