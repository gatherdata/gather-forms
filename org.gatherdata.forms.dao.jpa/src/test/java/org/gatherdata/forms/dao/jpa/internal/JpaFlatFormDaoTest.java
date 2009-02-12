package org.gatherdata.forms.dao.jpa.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.gatherdata.core.net.CbidFactory;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.GenericFlatForm;
import org.gatherdata.forms.core.model.GenericRenderedValue;
import org.junit.Before;
import org.junit.Test;

public class JpaFlatFormDaoTest {

    EntityManagerFactory emf;
    EntityManager em = null;
    JpaFlatFormDao dao;
    int mockPlainTextCount = 0;
    
    @Before
    public void setupEntityManager() {
            emf = Persistence.createEntityManagerFactory("hibernateInMemory");

            em = emf.createEntityManager();

            dao = new JpaFlatFormDao(em);
    }

    private FlatForm createMockGenericFlatForm() {
        GenericRenderedValue mockValue = new GenericRenderedValue();
        mockValue.setDateCreated(GregorianCalendar.getInstance());
        mockValue.setNamespace("http://gatherdata.org/unittest/jpaflatformdaotest");
        mockValue.setTag("foo");
        mockValue.setValueAsString("this is mock value #" + Integer.toString(mockPlainTextCount++));
        
        GenericFlatForm mockForm = new GenericFlatForm();
        mockForm.addToValues(mockValue);
        mockForm.setDateStored(GregorianCalendar.getInstance());
        mockForm.setCbid(CbidFactory.createCbid(mockValue));
        
        return mockForm;
    }

    @Test
    public void shouldHaveEntityManagerFactory() {
        assertNotNull(emf);
    }
    
    @Test
    public void shouldHaveEntityManager() {
        assertNotNull(em);
    }
    
    @Test 
    public void shouldReturnEquivalentFlatFormWhenSavingAGenericFlatForm() {
        FlatForm formToSave = createMockGenericFlatForm();
        assertNotNull(formToSave);
        FlatForm savedForm = dao.save(formToSave);
        assertEquals(formToSave, savedForm);
        assertEquals(formToSave.getValues().size(), savedForm.getValues().size());
    }

    @Test
    public void shouldGetASavedFlatForm() {
        FlatForm formToSave = createMockGenericFlatForm();
        dao.save(formToSave);
        FlatForm retrievedForm = dao.getContent(formToSave.getCbid());
        assertEquals(formToSave, retrievedForm);
        assertEquals(formToSave.getValues().size(), retrievedForm.getValues().size());
    }
    
    @Test
    public void shouldAffirmThatASavedFormExists() {
        FlatForm formToSave = createMockGenericFlatForm();
        
        dao.save(formToSave);
        
        assertTrue(dao.contentExists(formToSave.getCbid()));
    }

    @Test
    public void shouldGetAllSavedContentUids() {
        final int EXPECTED_NUMBER_OF_FORMS = new Random().nextInt(100);
        List<FlatForm> formsToSave = new ArrayList<FlatForm>();
        
        for (int i=0; i< EXPECTED_NUMBER_OF_FORMS; i++) {
            FlatForm formToSave = createMockGenericFlatForm();
            formsToSave.add(formToSave);
            dao.save(formToSave);
        }
        
        List<URI> savedUidList = dao.getAllContentUids();
        assertEquals(formsToSave.size(), savedUidList.size());
    }
    
    @Test
    public void shouldGetAllSavedForms() {
        final int EXPECTED_NUMBER_OF_FORMS = new Random().nextInt(100);
        List<FlatForm> formsToSave = new ArrayList<FlatForm>();
        
        for (int i=0; i< EXPECTED_NUMBER_OF_FORMS; i++) {
            FlatForm formToSave = createMockGenericFlatForm();
            formsToSave.add(formToSave);
            dao.save(formToSave);
        }
        
        List<? extends FlatForm> savedFormList = dao.getAllContents();
        assertEquals(EXPECTED_NUMBER_OF_FORMS, savedFormList.size());
    }

    
    
}
