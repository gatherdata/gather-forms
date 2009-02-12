package org.gatherdata.forms.core.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import org.apache.commons.lang.ObjectUtils;

/**
 * A RenderedValue presents multiple possible renderings of a value.
 */

public interface RenderedValue extends Serializable {

	public Calendar getDateCreated();

	public FlatForm getForm();

	public URI getNamespace();

	public String getPath();

	public String getTag();

	public Calendar getValueAsDate();
	
	public Calendar getValueAsDateTime();

	public float getValueAsFloat();

	public int getValueAsInt();

	public String getValueAsString();
    
    public boolean getValueAsBoolean();

	public boolean isVoided();

}
