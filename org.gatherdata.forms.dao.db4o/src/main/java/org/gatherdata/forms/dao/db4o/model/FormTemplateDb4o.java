package org.gatherdata.forms.dao.db4o.model;

import org.gatherdata.commons.db.db4o.model.DescribedEntityDb4o;
import org.gatherdata.commons.model.DescribedEntity;
import org.gatherdata.forms.core.model.FormTemplate;
import org.joda.time.DateTime;

public class FormTemplateDb4o extends DescribedEntityDb4o implements FormTemplate {

	private DateTime lazyDateModified;
	private long dateModifiedMillis;
	private String formStyle;
	private String formTemplate;
	private String formType;
	
	public DateTime getDateModified() {
		if (lazyDateModified == null) {
			lazyDateModified = new DateTime(dateModifiedMillis);
		}
		return lazyDateModified;
	}
	
	public void setDateModified(long millis) {
		dateModifiedMillis = millis;
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

	public FormTemplateDb4o copy(FormTemplate template) {
        if ((template != null) && (template != this)) {
            super.copy(template);
            DateTime templateDateModified = template.getDateModified();
            if (templateDateModified != null) {
                setDateModified(templateDateModified.getMillis());
            }
            setFormStyle(template.getFormStyle());
            setFormTemplate(template.getFormTemplate());
            setFormType(template.getFormType());
            
        }
        return this;
	}

    @Override
    public String toString() {
        return "FormTemplate [uid=" + getUid() + ", dateCreated=" + getDateCreated()
                + ", dateModified=" + getDateModified() 
                + ", formType=" + formType+ ", formStyle=" + formStyle 
                + "]";
    }

	
	
}
