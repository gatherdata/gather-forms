package org.gatherdata.forms.core.internal;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.model.FormTemplateStyle;
import org.gatherdata.forms.core.model.impl.MutableFormTemplate;
import org.gatherdata.forms.core.spi.FormCatalogService;
import org.gatherdata.forms.core.spi.FormTemplateDao;
import org.osgi.framework.BundleContext;

import com.google.inject.Inject;

/**
 * Internal implementation of FormService
 */
public final class FormCatalogServiceImpl implements FormCatalogService {

    @Inject
    FormTemplateDao dao;
    
    @Inject
    BundleContext bc;
    
    public boolean exists(URI uid) {
        return dao.exists(uid);
    }

    public FormTemplate get(URI uid) {
        return dao.get(uid);
    }

    public Iterable<? extends FormTemplate> getAll() {
        return dao.getAll();
    }

    public Iterable<? extends FormTemplate> getAllStyledAs(FormTemplateStyle style) {
        return dao.getAllStyledAs(style);
    }

    public void remove(URI uid) {
        dao.remove(uid);
    }

    public FormTemplate save(FormTemplate entity) {
        FormTemplate savedEntity = null;
        if (exists(entity.getUid())) {
            update(entity);
            savedEntity = get(entity.getUid());
        } else {
            dao.beginTransaction();
            savedEntity = dao.save(entity);
            dao.endTransaction();
        }
        return savedEntity;
    }

    public void update(FormTemplate entity) {
        if (entity != null) {
            MutableFormTemplate updatedEntity = new MutableFormTemplate();
            updatedEntity.copy(get(entity.getUid()));
            updatedEntity.update(entity);
            save(updatedEntity);
        }
    }
    public String orbeonToRosa(String xhtml) {
        String rosaXhtml = null;
        try {
            rosaXhtml = transform("simple-orbeon.xhtml", "orb2rosa.xsl");
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rosaXhtml;
    }

    public String transform(String xhtml, String xslResourceName) throws TransformerException, IOException {
        Reader originalInput = new StringReader(xhtml);

        URL xslUrl = bc.getBundle().getResource(xslResourceName);
        Reader xsl = new InputStreamReader(xslUrl.openStream());
        
        StringWriter transformedString = new StringWriter();
        TransformerFactory tFactory = TransformerFactory.newInstance();

        Transformer transformer = tFactory.newTransformer(
                new StreamSource( xsl));

        transformer.transform(new StreamSource(originalInput), new StreamResult(transformedString));
        
        return transformedString.toString();
    }

    public URL getResource(String resourceName) {
        return this.getClass().getClassLoader().getResource(resourceName);
    }


}
