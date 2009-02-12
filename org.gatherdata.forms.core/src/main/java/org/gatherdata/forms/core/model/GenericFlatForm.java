package org.gatherdata.forms.core.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A FlatForm stores all form values without any hierarchy.
 */

public class GenericFlatForm implements FlatForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8313084518428651473L;

	private URI cbid;
	private Calendar dateCreated;
	private URI namespace;
	private List<GenericRenderedValue> values;

	public GenericFlatForm() {
		this.values = new ArrayList<GenericRenderedValue>();
	}

	public void addToValues(GenericRenderedValue aRenderedValue) {
		this.values.add(aRenderedValue);
		aRenderedValue.setForm(this);
	}

	public URI getCbid() {
		return this.cbid;
	}

	public Calendar getDateCreated() {
		return this.dateCreated;
	}

	public URI getNamespace() {
		return this.namespace;
	}

	public List<? extends RenderedValue> getValues() {
		return this.values;
	}

	public void removeFromValues(GenericRenderedValue aRenderedValue) {
		this.values.remove(aRenderedValue);
		aRenderedValue.setForm(null);
	}

	public void setCbid(URI cbid) {
		this.cbid = cbid;
	}

	public void setDateStored(Calendar dateStored) {
		this.dateCreated = dateStored;
	}

	public void setNamespace(URI namespace) {
		this.namespace = namespace;
	}

	protected void setValues(List<GenericRenderedValue> values) {
		this.values = values;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlatForm)) {
			return false;
		}
		FlatForm rhs = (FlatForm) object;
		return new EqualsBuilder()
			.append(this.namespace, rhs.getNamespace())
			.append(this.cbid,rhs.getCbid())
			.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1457628007, 632920219)
			.append(this.namespace)
			.append(this.cbid)
			.toHashCode();
	}

}
