package org.gatherdata.forms.core.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import org.apache.commons.lang.ObjectUtils;

/**
 * Generic concrete implementation of the RenderedValue interface.
 */

public class GenericRenderedValue implements RenderedValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3989477987923437193L;

	private Calendar dateCreated;
	private FlatForm form;
	private URI namespace;
	private String path;
	private String tag;
	private Calendar valueAsDate;
	private Calendar valueAsDateTime;
	private float valueAsFloat;
	private int valueAsInt;
	private String valueAsString;
	private boolean voided;

    private boolean valueAsBoolean;

	public GenericRenderedValue() {
	}

	public Calendar getDateCreated() {
		return this.dateCreated;
	}

	public FlatForm getForm() {
		return this.form;
	}

	public URI getNamespace() {
		return this.namespace;
	}

	protected String getNamespaceAsString() {
		return ObjectUtils.toString(this.namespace);
	}

	public String getPath() {
		return this.path;
	}

	public String getTag() {
		return this.tag;
	}

	public Calendar getValueAsDate() {
		return this.valueAsDate;
	}

    public Calendar getValueAsDateTime() {
        return this.valueAsDateTime;
    }

	public float getValueAsFloat() {
		return this.valueAsFloat;
	}

	public int getValueAsInt() {
		return this.valueAsInt;
	}

	public String getValueAsString() {
		return this.valueAsString;
	}
    
    public boolean getValueAsBoolean() {
        return this.valueAsBoolean;
    }

	public boolean isVoided() {
		return this.voided;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	public void setForm(FlatForm form) {
		this.form = form;
	}

	public void setNamespace(String uriString) {
		try {
			setNamespace(new URI(uriString));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public void setNamespace(URI namespace) {
		this.namespace = namespace;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setValueAsDate(Calendar valueAsDate) {
		this.valueAsDate = valueAsDate;
	}

    public void setValueAsDateTime(Calendar valueAsDateTime) {
        this.valueAsDateTime = valueAsDateTime;
    }

	public void setValueAsFloat(float valueAsFloat) {
		this.valueAsFloat = valueAsFloat;
	}

	public void setValueAsInt(int valueAsInt) {
		this.valueAsInt = valueAsInt;
	}

	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}

	public void setVoided(boolean voided) {
		this.voided = voided;
	}

	public void setValueAsBoolean(boolean valueAsBoolean) {
	    this.valueAsBoolean = valueAsBoolean;
	}

}
