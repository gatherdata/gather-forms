package org.gatherdata.forms.core.osgi;

import static org.ops4j.peaberry.Peaberry.service;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import org.gatherdata.archiver.core.spi.EnvelopeArchiver;
import org.gatherdata.forms.core.internal.FlatFormStorageImpl;
import org.gatherdata.forms.core.spi.FlatFormStorage;
import org.ops4j.peaberry.Export;

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
		bind(EnvelopeArchiver.class).toProvider(service(EnvelopeArchiver.class).single());
		
		// exports
		bind(export(FlatFormStorage.class)).toProvider(service(FlatFormStorageImpl.class).export());
		
	}
}
