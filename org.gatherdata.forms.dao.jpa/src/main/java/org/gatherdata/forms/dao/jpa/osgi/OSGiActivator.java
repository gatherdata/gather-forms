package org.gatherdata.forms.dao.jpa.osgi;

import java.util.Dictionary;
import java.util.Properties;

import org.gatherdata.forms.core.spi.FlatFormDao;
import org.gatherdata.forms.dao.jpa.internal.JpaFlatFormDao;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Extension of the default OSGi bundle activator
 */
public final class OSGiActivator
    implements BundleActivator
{
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start( BundleContext bc )
        throws Exception
    {
        System.out.println( "STARTING org.gatherdata.forms.dao.jpa" );

        // ABKTODO: propagate the EntityManagerFactory to use (EclipeLink, OpenJPA, Hibernate)
        bc.registerService(FlatFormDao.class.getName(), new JpaFlatFormDao(null), null);

    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop( BundleContext bc )
        throws Exception
    {
        System.out.println( "STOPPING org.gatherdata.forms.dao.jpa" );
    }
}

