/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.forms.core.model.impl;


import org.gatherdata.commons.model.impl.MutableDescribedEntity;
import org.gatherdata.forms.core.model.FormTemplate;
import org.joda.time.DateTime;

public class MutableFormTemplate extends MutableDescribedEntity implements FormTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9202927066714102494L;
	
	private DateTime dateModified;
	private String formStyle;
	private String formTemplate;
	private String formType;

	public DateTime getDateModified() {
		return this.dateModified;
	}
	
	public void setDateModified(DateTime dateModified) {
		this.dateModified = dateModified;
	}

	public String getFormStyle() {
		return this.formStyle;
	}
	
	public void setFormStyle(String formStyle) {
		this.formStyle = formStyle;
	}

	public String getFormTemplate() {
		return this.formTemplate;
	}
	
	public void setFormTemplate(String formTemplate) {
		this.formTemplate = formTemplate;
	}

	public String getFormType() {
		return this.formType;
	}
	
	public void setFormType(String formType) {
		this.formType = formType;
	}

}
