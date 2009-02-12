package org.gatherdata.forms.dao.jpa.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import org.gatherdata.core.net.GatherUrnFactory;
import org.junit.Before;
import org.junit.Test;

public class JpaFlatFormTest {
    
    EntityManagerFactory emf;
    
    EntityManager em = null;
    
    GatherUrnFactory urnFactory;
    
    @Before
    public void setupEntityManager() {
            emf = Persistence.createEntityManagerFactory("hibernateInMemory");

            em = emf.createEntityManager();
            
            urnFactory = new GatherUrnFactory();
    }
    
    @Test
    public void shouldSaveToGenericJpaEntityManager() throws URISyntaxException {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        JpaFlatForm sampleFlatForm = new JpaFlatForm();
        sampleFlatForm.setCbid(urnFactory.getLocalUrn());
        sampleFlatForm.setDateCreated(GregorianCalendar.getInstance());
        sampleFlatForm.setNamespace(new URI("http://www.gatherdata.org/testing"));
        
        JpaRenderedValue sampleStringValue = new JpaRenderedValue();
        sampleStringValue.setValueAsString("plain text");
        sampleFlatForm.addToValues(sampleStringValue);
        
        JpaRenderedValue sampleDateValue = new JpaRenderedValue();
        sampleDateValue.setValueAsDate(GregorianCalendar.getInstance());
        sampleFlatForm.addToValues(sampleDateValue);
        
        JpaRenderedValue sampleFloatValue = new JpaRenderedValue();
        sampleFloatValue.setValueAsFloat(3.159f);
        sampleFlatForm.addToValues(sampleFloatValue);
        
        JpaRenderedValue sampleIntValue = new JpaRenderedValue();
        sampleIntValue.setValueAsInt(42);
        sampleFlatForm.addToValues(sampleIntValue);
        
        em.persist(sampleFlatForm);
        for (JpaRenderedValue jpaValue : sampleFlatForm.getValues()) {
            em.persist(jpaValue);
        }
        tx.commit();
    }

}
