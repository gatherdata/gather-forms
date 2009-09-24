package org.gatherdata.forms.core.internal;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gatherdata.forms.core.model.FormTemplate;
import org.gatherdata.forms.core.spi.FormService;
import org.gatherdata.forms.core.spi.FormTemplateDao;

import com.google.inject.Inject;

/**
 * Internal implementation of FormService
 */
public final class FormServiceImpl
    implements FormService
{
	
	@Inject
	FormTemplateDao dao;

	public boolean exists(URI uid) {
		return dao.exists(uid);
	}

	public FormTemplate get(URI uid) {
		return dao.get(uid);
	}

	public Iterable<? extends FormTemplate> getAll() {
		return dao.getAll();
	}

	public void remove(URI uid) {
		dao.remove(uid);
	}

	public FormTemplate save(FormTemplate entity) {
		return dao.save(entity);
	}
	
}

