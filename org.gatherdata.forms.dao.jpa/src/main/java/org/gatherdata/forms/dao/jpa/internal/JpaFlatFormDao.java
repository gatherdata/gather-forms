/**
 * 
 */
package org.gatherdata.forms.dao.jpa.internal;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gatherdata.archiver.core.spi.ArchiverDao;
import org.gatherdata.core.model.Envelope;
import org.gatherdata.core.spi.dao.ContentStorageDao;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.RenderedValue;
import org.gatherdata.forms.core.spi.FlatFormDao;
import org.gatherdata.forms.dao.jpa.model.JpaFlatForm;

/**
 * JPA based implementation of FlatFormDao.
 *
 */
public class JpaFlatFormDao implements FlatFormDao {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    public static final String PERSISTENCE_UNIT_NAME = "ApplicationEntityManager";
    
    private EntityManager entityManager;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     * @param persistentClass the class type you'd like to persist
     * @param entityManager the configured EntityManager for JPA implementation.
     */
    public JpaFlatFormDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityTransaction beginTransaction() {
        EntityTransaction newTransaction = this.entityManager.getTransaction();
        newTransaction.begin();
        return newTransaction;
    }
    
    public void endTransaction(EntityTransaction transaction) {
        transaction.commit();
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /* (non-Javadoc)
     * @see org.gatherdata.core.spi.dao.ContentStorageDao#adapt(java.io.Serializable)
     */
    public FlatForm adapt(Serializable contentToAdapt) {
        FlatForm adaptedContent = null;
        if (contentToAdapt instanceof FlatForm) {
            adaptedContent = (FlatForm)contentToAdapt;
        }
        return adaptedContent; // no adaption needed
    }

    /* (non-Javadoc)
     * @see org.gatherdata.core.spi.dao.ContentStorageDao#getAllContents()
     */
    @SuppressWarnings("unchecked")
    public List<? extends FlatForm> getAllContents() {
        Query q = this.entityManager.createQuery("SELECT form FROM JpaFlatForm form");

        List<FlatForm> results = (List<FlatForm>)q.getResultList();
        
        return results;
    }

    /* (non-Javadoc)
     * @see org.gatherdata.core.spi.dao.ContentStorageDao#getContent(java.net.URI)
     */
    public FlatForm getContent(URI uidOfEnvelope) {
        Query q = this.entityManager.createQuery("SELECT form FROM JpaFlatForm form WHERE form.cbidAsString = :uidOfEnvelope");
        q.setParameter("uidOfEnvelope", uidOfEnvelope.toASCIIString());

        FlatForm result = (FlatForm)q.getSingleResult();
        if (result == null) {
            String msg = "Uh oh, contents with of envelope uid '" + uidOfEnvelope + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }
        return result;
    }

    /* (non-Javadoc)
     * @see org.gatherdata.core.spi.dao.ContentStorageDao#removeContent(java.net.URI)
     */
    public void removeContent(URI uidOfEnvelope) {
        // ABKTODO: use a jpql update to null the contents of the envelope
        throw new NotImplementedException();
    }

    /* (non-Javadoc)
     * @see org.gatherdata.core.spi.dao.ContentStorageDao#save(java.io.Serializable)
     */
    public FlatForm save(FlatForm contentToSave) {
        FlatForm savedForm = null;
        if (contentToSave != null) {
            JpaFlatForm saveableForm = new JpaFlatForm(contentToSave);
            
            EntityTransaction tx = beginTransaction();
            savedForm = entityManager.merge(saveableForm);
            endTransaction(tx);
        }
        return savedForm;
    }

    /* (non-Javadoc)
     * @see org.gatherdata.core.spi.dao.ContentStorageDao#savedAdapted(java.io.Serializable)
     */
    public Serializable saveAdapted(Serializable contentToSave) {
        FlatForm adaptedContent = adapt(contentToSave);
        FlatForm savedContent = null;
        if (adaptedContent != null) {
            savedContent = save(adaptedContent);
        }
        return savedContent;
    }

    @SuppressWarnings("unchecked")
    public List<URI> getAllContentUids() {
        // this is the same as all Envelopes with non-null content
        Query q = this.entityManager.createQuery("SELECT form.cbidAsString FROM JpaFlatForm form");

        List<String> resultList = (List<String>)q.getResultList();
        
        // ABKTODO: ugh. look into auto-converting from string to URI when retrieving objects with jpa
        // or, if not possible, then return a proxied list backed by the strings which converts on demand
        // instead of doing it all up front
        List<URI> resultAsUri = new ArrayList<URI>(resultList.size());
        for (String uidAsString : resultList) {
            try {
                resultAsUri.add(new URI(uidAsString));
            } catch (URISyntaxException e) {
                log.error("Stored content cbid {" + uidAsString + "} is not a valid URI");
                e.printStackTrace();
            }
        }

        return resultAsUri;
    }

    public boolean contentExists(URI uidOfEnvelope) {
        Query q = this.entityManager.createQuery("SELECT COUNT(form) FROM JpaFlatForm form WHERE form.cbidAsString = :uidOfEnvelope");
        q.setParameter("uidOfEnvelope", uidOfEnvelope.toASCIIString());

        Long result = (Long)q.getSingleResult();
        return (result != 0);
    }

}
