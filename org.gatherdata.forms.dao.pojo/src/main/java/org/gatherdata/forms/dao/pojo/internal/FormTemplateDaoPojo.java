package org.gatherdata.forms.dao.pojo.internal;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.spi.FormTemplateDao;

/**
 * POJO implementation of FormServiceDao, for in-memory storage.
 */
public final class FormTemplateDaoPojo
    implements FormTemplateDao
{
    private Map<URI, FormTemplate> uidToFormTemplateMap = new HashMap<URI, FormTemplate>();

    public void beginTransaction() {
        ;
    }

    public void endTransaction() {
        ;
    }

    public boolean exists(URI uid) {
        return uidToFormTemplateMap.containsKey(uid);
    }

    public FormTemplate get(URI uid) {
        return uidToFormTemplateMap.get(uid);
    }

    public Iterable<? extends FormTemplate> getAll() {
        return uidToFormTemplateMap.values();
    }

    public int getCount() {
        return uidToFormTemplateMap.size();
    }

    public void remove(URI uid) {
        uidToFormTemplateMap.remove(uid);
    }

    public FormTemplate save(FormTemplate entity) {
        uidToFormTemplateMap.put(entity.getUid(), entity);
        return entity;
    }
    
}

