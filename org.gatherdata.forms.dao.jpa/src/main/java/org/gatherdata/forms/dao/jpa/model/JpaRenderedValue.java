package org.gatherdata.forms.dao.jpa.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.ObjectUtils;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.RenderedValue;

/**
 * JPA specialized subclass of RenderedValue.
 */
@Entity
@Table(name="RENDERED_VALUE")
public class JpaRenderedValue implements RenderedValue {

    /**
     * 
     */
    private static final long serialVersionUID = 9152408469704792328L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int jpaId;

    @ManyToOne
    private JpaFlatForm form;

    @Temporal (TemporalType.TIMESTAMP)
    private Calendar dateCreated;

    @Basic
    @Column(name="namespace")
    private String namespaceAsString;
    
    @Transient
    private URI lazyNamespace;

    @Basic
    private String path;

    @Basic
    private String tag;

    @Basic
    private boolean valueAsBoolean;

    @Temporal(TemporalType.DATE)
    private Calendar valueAsDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar valueAsDateTime;
    
    @Basic
    private float valueAsFloat;

    @Basic
    private int valueAsInt;

    @Basic
    private String valueAsString;

    @Basic
    private boolean voided;
	
    public JpaRenderedValue() {
        ;
    }
    
    public JpaRenderedValue(RenderedValue originalValue) {
        setDateCreated(originalValue.getDateCreated());
        setNamespace(originalValue.getNamespace());
        setPath(originalValue.getPath());
        setTag(originalValue.getTag());
        setValueAsBoolean(originalValue.getValueAsBoolean());
        setValueAsDate(originalValue.getValueAsDate());
        setValueAsDateTime(originalValue.getValueAsDateTime());
        setValueAsFloat(originalValue.getValueAsFloat());
        setValueAsInt(originalValue.getValueAsInt());
        setValueAsString(originalValue.getValueAsString());
        setVoided(originalValue.isVoided());
    }

    public int getJpaId() {
        return this.jpaId;
    }
    
    public void setJpaId(int id) {
        this.jpaId = id;
    }
	
    public Calendar getDateCreated() {
        return this.dateCreated;
    }
    public FlatForm getForm() {
        return this.form;
    }
    public URI getNamespace() {
        if (this.lazyNamespace == null) {
            try {
                this.lazyNamespace = new URI(namespaceAsString);
            } catch (URISyntaxException e) {
                this.lazyNamespace = null;
            }
        }
        return this.lazyNamespace;
    }
    public String getPath() {
        return this.path;
    }
    public String getTag() {
        return this.tag;
    }
    public boolean getValueAsBoolean() {
        return this.valueAsBoolean;
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
    public boolean isVoided() {
        return this.voided;
    }

    public void setForm(JpaFlatForm form) {
        this.form = form;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setNamespace(URI namespace) {
        this.lazyNamespace = namespace;
        this.namespaceAsString = namespace.toASCIIString();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setValueAsBoolean(boolean valueAsBoolean) {
        this.valueAsBoolean = valueAsBoolean;
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

}
