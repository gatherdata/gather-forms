package org.gatherdata.forms.dao.db4o.osgi;

import java.io.File;

import org.gatherdata.forms.core.spi.FormTemplateDao;
import org.gatherdata.forms.dao.db4o.internal.FormTemplateDaoDb4o;

import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;
import com.db4o.osgi.Db4oService;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;

import static org.ops4j.peaberry.util.TypeLiterals.export;
import static org.ops4j.peaberry.util.Filters.ldap;
import static org.ops4j.peaberry.Peaberry.service;


public class FormTemplateDaoModule extends AbstractModule {

	// ABKTODO: make this configurable
    public static final String DEFAULT_DATA_DIR = "forms" + File.separatorChar + "db4o";

    @Inject
    Db4oService db4oService;
    
	@Override 
	protected void configure() {
        new File(DEFAULT_DATA_DIR).mkdirs();
        
        Configuration config = db4oService.newConfiguration();
                
        ObjectContainer archiverObjectContainer = db4oService.openFile(config, DEFAULT_DATA_DIR + File.separatorChar + "db4o.db");

		// local binds
        bind(ObjectContainer.class).toInstance(archiverObjectContainer);

		// exports
		bind(export(FormTemplateDao.class)).toProvider(service(FormTemplateDaoDb4o.class).export());
		
		
	}
	
}