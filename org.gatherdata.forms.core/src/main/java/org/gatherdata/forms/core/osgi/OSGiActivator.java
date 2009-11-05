/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.core.osgi;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import java.net.URL;

import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.gatherdata.forms.core.spi.FormCatalogService;
import org.gatherdata.forms.core.spi.FormTemplateDao;

import com.google.inject.Inject;

/**
 * Extension of the default OSGi bundle activator
 */
public final class OSGiActivator
    implements BundleActivator
{
	
	@Inject
	FormTemplateDao dao;
	
	@Inject
	Export<FormCatalogService> service;
	
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
		createInjector(osgiModule(bc), new GuiceBindingModule()).injectMembers(this);

        URL orb2RosaUrl = bc.getBundle().getResource("orb2rosa.xsl");
        
		service.get().setOrbeonToRosaXml(orb2RosaUrl);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
    }
}

