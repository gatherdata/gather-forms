package org.gatherdata.forms.core.model;

import org.gatherdata.commons.model.DescribedEntity;
import org.joda.time.DateTime;

/**
 * A FormTemplate is the structured representation of a form.
 *
 */
public interface FormTemplate extends DescribedEntity {

	public DateTime getDateModified();
	
	/**
	 * The mime-type representation of the form.
	 * 
	 * Ex.: text/xml
	 * 
	 * @return
	 */
	public String getFormType();
	
	/**
	 * The form style is further qualifies the type of 
	 * the form. For instance, for xforms the styles
	 * could be "orbeon" or "rosa". 
	 * 
	 * @return
	 */
	public String getFormStyle();
	
	/**
	 * 
	 * @return
	 */
	public String getFormTemplate();
	
}
