package org.gatherdata.forms.core.osgi;

import static com.google.inject.Guice.createInjector;
import static org.ops4j.peaberry.Peaberry.osgiModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gatherdata.forms.core.spi.FlatFormStorage;
import org.ops4j.peaberry.Export;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

/**
 * The OSGi activator for the Gather Archiver Storage bundle.
 * 
 **/
public class OSGiActivator implements BundleActivator {

	Log log = LogFactory.getLog(OSGiActivator.class);

	@Inject
	Export<FlatFormStorage> storage;

	/**
	 * Implements BundleActivator.start().
	 * 
	 * Adds itself as a ServiceListener to the OSGi context and registers a
	 * Service.
	 * 
	 * @param bc the OSGi framework context for the bundle.
	 **/
	public void start(BundleContext bc) {

		createInjector(osgiModule(bc), new GuiceBindingModule()).injectMembers(
				this);

		log.info("FlatForms Core started");
	}

	/**
	 * Implements BundleActivator.stop(). Prints a message and removes itself
	 * from the bundle context as a service listener.
	 * 
	 * @param context
	 *            the framework context for the bundle.
	 **/
	public void stop(BundleContext context) {
		log.info("FlatForms Core stopped");
	}

}
