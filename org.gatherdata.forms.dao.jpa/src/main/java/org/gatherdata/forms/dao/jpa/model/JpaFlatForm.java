/**
 * 
 */
package org.gatherdata.forms.dao.jpa.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.RenderedValue;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * JPA annotated sub-class of a FlatForm, ready for storage.
 * 
 */
@Entity 
@Table(name="FLAT_FORM")
public class JpaFlatForm implements FlatForm {

    /**
     * 
     */
    private static final long serialVersionUID = -8313084518428651473L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int dbid;

    @Basic 
    @Column(name="cbid")
    private String cbidAsString;

    @Transient
    private URI lazyCbid;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateCreated;
    
    @Basic
    @Column(name="namespace")
    private String namespaceAsString;
    
    @Transient
    private URI lazyNamespace;
    
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<JpaRenderedValue> jpaValues = new ArrayList<JpaRenderedValue>();

    /**
     * 
     */
    public JpaFlatForm() {
        ;
    }
    
    public JpaFlatForm(FlatForm  originalForm) {
        setCbid(originalForm.getCbid());
        setDateCreated(originalForm.getDateCreated());
        setNamespace(originalForm.getNamespace());
        for (RenderedValue originalValue : originalForm.getValues()) {
            addToValues(new JpaRenderedValue(originalValue));
        }
    }

    public int getDbid() {
        return this.dbid;
    }
    
    public void setDbid(int id) {
        this.dbid = id;
    }
    
    public URI getCbid() {
        if (this.lazyCbid == null) {
            try {
                this.lazyCbid = new URI(cbidAsString);
            } catch (URISyntaxException e) {
                this.lazyCbid = null;
            }
        }
        return this.lazyCbid;
    }
    
    public void setCbid(URI cbid) {
        this.lazyCbid = cbid;
        this.cbidAsString = cbid.toASCIIString();
    }

    @Basic
    public Calendar getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    public URI getNamespace() {
        if ((this.lazyNamespace == null) && (this.namespaceAsString != null)) {
            try {
                this.lazyNamespace = new URI(this.namespaceAsString);
            } catch (URISyntaxException e) {
                this.lazyNamespace = null;
            }
        }
        return this.lazyNamespace;
    }
    
    public void setNamespace(URI namespace) {
        this.lazyNamespace = namespace;
        this.namespaceAsString = (namespace != null) ? namespace.toASCIIString() : null;
    }

    @OneToMany
    public List<JpaRenderedValue> getValues() {
        return jpaValues;
    }

    public void addToValues(JpaRenderedValue renderedValue) {
        jpaValues.add(renderedValue);
        renderedValue.setForm(this);
    }

    public void removeFromValues(RenderedValue renderedValue) {
        jpaValues.remove(renderedValue);
    }

    protected void setValues(List<JpaRenderedValue> values) {
        this.jpaValues = values;
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
            .append(this.getNamespace(), rhs.getNamespace())
            .append(this.getCbid(),rhs.getCbid())
            .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(969177683, -607910761)
        .append(this.getNamespace())
        .append(this.getCbid())
        .toHashCode();
    }
    
    

}
