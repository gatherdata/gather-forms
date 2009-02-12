package org.gatherdata.forms.core.model;

import java.io.Serializable;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

/**
 * A FlatForm stores all form values without any hierarchy.
 */

public interface FlatForm extends Serializable {


	public URI getCbid();

	public Calendar getDateCreated();

	public URI getNamespace();

	public List<? extends RenderedValue> getValues();

	
	

}
