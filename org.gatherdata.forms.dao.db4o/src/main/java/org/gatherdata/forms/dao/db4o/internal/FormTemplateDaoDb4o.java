package org.gatherdata.forms.dao.db4o.internal;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.spi.FormTemplateDao;
import org.gatherdata.forms.dao.db4o.model.FormTemplateDb4o;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.google.inject.Inject;

/**
 * POJO implementation of FormServiceDao, for in-memory storage.
 */
public final class FormTemplateDaoDb4o
    implements FormTemplateDao
{
    protected static final Log log = LogFactory.getLog(FormTemplateDaoDb4o.class);

    @Inject
    ObjectContainer db4o;

    public void beginTransaction() {
        // TODO Auto-generated method stub

    }

    public void endTransaction() {
        db4o.commit();
    }

    public boolean exists(URI uid) {
        return (get(uid) != null);
    }

    public FormTemplate get(URI uid) {
        FormTemplate foundEntity = null;
        
        final URI queryUid = uid;
        ObjectSet<FormTemplateDb4o> result = db4o.query(new Predicate<FormTemplateDb4o>() {
            @Override
            public boolean match(FormTemplateDb4o possibleMatch) {
                return possibleMatch.getUid().equals(queryUid);
            }
        });
        if (result.size() > 1) {
            log.warn("more than one FormTemplateDb4o found for uid: " + queryUid);
        }
        if (result.hasNext()) {
            foundEntity = result.next();
        }
        
        return foundEntity;
    }

    public Iterable<? extends FormTemplate> getAll() {
        return db4o.query(FormTemplateDb4o.class);
    }

    public int getCount() {
        return db4o.query(FormTemplateDb4o.class).size();
    }

    public void remove(URI uid) {
        db4o.delete(get(uid));
    }

    public FormTemplate save(FormTemplate entityToSave) {
    	FormTemplateDb4o entityDto = (FormTemplateDb4o) get(entityToSave.getUid());
        if (entityDto == null) {
            entityDto = new FormTemplateDb4o();
        }
        entityDto.copy(entityToSave);
        db4o.store(entityDto);
        
        return get(entityToSave.getUid());
    }
    
}

