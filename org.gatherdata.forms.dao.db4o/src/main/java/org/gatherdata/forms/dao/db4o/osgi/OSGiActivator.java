/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.dao.db4o.osgi;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import java.util.Dictionary;
import java.util.Properties;

import org.gatherdata.forms.core.spi.FormTemplateDao;
import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.db4o.ObjectContainer;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Extension of the default OSGi bundle activator
 */
public final class OSGiActivator
    implements BundleActivator
{
	@Inject
	Export<FormTemplateDao> dao;
	
    @Inject
    ObjectContainer db4o;

    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        Injector serviceGuicer = createInjector(osgiModule(bc), new GuiceBindingModule());
        FormTemplateDaoModule daoModule = new FormTemplateDaoModule();
        serviceGuicer.injectMembers(daoModule);
        createInjector(osgiModule(bc), daoModule).injectMembers(this);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
        db4o.close();
    }
}

