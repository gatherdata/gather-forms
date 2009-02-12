package org.gatherdata.forms.core.internal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.gatherdata.archiver.core.spi.ArchiverDao;
import org.gatherdata.archiver.core.spi.EnvelopeArchiver;
import org.gatherdata.core.io.MimeTypes;
import org.gatherdata.core.io.QualifiedType;
import org.gatherdata.core.model.Envelope;
import org.gatherdata.core.model.GenericEnvelope;
import org.gatherdata.core.model.TextEnvelope;
import org.gatherdata.core.net.CbidFactory;
import org.gatherdata.core.net.GatherUrnFactory;
import org.gatherdata.core.spi.dao.ContentStorageDao;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.GenericFlatForm;
import org.gatherdata.forms.core.model.GenericRenderedValue;
import org.gatherdata.forms.core.model.RenderedValue;
import org.gatherdata.forms.core.spi.FlatFormDao;
import org.gatherdata.forms.core.spi.FlatFormStorage;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit testing for the behavior of FlatFormStorageImpl.
 *
 */
public class FlatFormStorageImplTest {

	FlatFormStorage storage;
	ArchiverDao mockArchiverDao;
	FlatFormDao mockFlatFormDao;
	Random rnd = new Random();
	GatherUrnFactory urnFactory = new GatherUrnFactory();
	
	@Before
	public void setupStorageInstance() {
	    mockArchiverDao = (ArchiverDao)createMock(ArchiverDao.class);
	    mockFlatFormDao = (FlatFormDao)createMock(FlatFormDao.class);
        storage = new FlatFormStorageImpl(mockArchiverDao, mockFlatFormDao);
	}

    private Envelope createMockFlatFormEnvelope() {
        GenericFlatForm mockContent = new GenericFlatForm();
        int numberOfValuesToAdd = rnd.nextInt(10);
        for (int i=0; i<numberOfValuesToAdd; i++) {
            mockContent.addToValues(createMockRenderedValue());
        }
        GenericEnvelope mockEnvelope = new GenericEnvelope(urnFactory.getLocalUrn(), mockContent, MimeTypes.JAVA_OBJECT);
        return mockEnvelope;
    }
	
	private GenericRenderedValue createMockRenderedValue() {
	    GenericRenderedValue mockValue = new GenericRenderedValue();
        return mockValue;
    }

	/**
	 * All EnvelopeStorage implementations are expected to 
	 * provide an array of QualifiedTypes which are accepted
	 * for storage.
	 */
	@Test
	public void shouldRevealWhatQualifiedTypesAreAcceptable() {
	    assertNotNull(storage.getAcceptableTypes());
	}

    /**
     * All EnvelopeStorage implementations are expected to 
     * provide an array of QualifiedTypes which are accepted
     * for storage.
     */
    @Test
    public void shouldClaimToAcceptJavaObjectFlatForm() {
        
        QualifiedType[] claimedAcceptableTypes = storage.getAcceptableTypes();
        
        boolean claimsToAcceptJavaObjectFlatForm = false;
        for (int i=0; i<claimedAcceptableTypes.length; i++) {
            if (claimedAcceptableTypes[i].getType().equals(MimeTypes.JAVA_OBJECT) &&
                    FlatForm.class.getName().equals(claimedAcceptableTypes[i].getQualifier())) {
                claimsToAcceptJavaObjectFlatForm = true;
            }
        }
        assertTrue (claimsToAcceptJavaObjectFlatForm);
    }
    
    @Test
    public void shouldSaveAnEnvelopeContainingAFlatForm() {
        Envelope envelopeToSave = createMockFlatFormEnvelope();
        
        expect(mockArchiverDao.saveProxy(envelopeToSave, mockFlatFormDao)).andReturn(envelopeToSave);
        
        replay(mockArchiverDao);
        Envelope savedEnvelope = storage.save(envelopeToSave);
        assertNotNull(savedEnvelope);
    }

    @Test
    public void shouldNotSaveAnEnvelopeContainingPlainText() {
        Envelope incompatibleEnvelope = new TextEnvelope(urnFactory.getLocalUrn(), "plain text contents", MimeTypes.TEXT_PLAIN);
        assertNull(storage.save(incompatibleEnvelope));
    }
    
    @Test
    public void shouldDeriveGrandTotalOfEnvelopesByAskingTheDao() {
        long EXPECTED_GRAND_TOTAL = 12l;
        
        expect(mockArchiverDao.getNumberOfQualifiedEnvelopes(FlatForm.class.getName())).andReturn(EXPECTED_GRAND_TOTAL);
        
        replay(mockArchiverDao);
        
        assertEquals(EXPECTED_GRAND_TOTAL, storage.getTotalNumberOfStoredEnvelopes());
        
        verify(mockArchiverDao);
    }
    
    @Test
    public void shouldSaveUsingProxyFromArchiverToFlatFormDaos() {
        Envelope envelopeToSave = createMockFlatFormEnvelope();
        
        expect(mockArchiverDao.saveProxy(envelopeToSave, mockFlatFormDao)).andReturn(envelopeToSave);
        
        expect(mockArchiverDao.getProxiedEnvelope(envelopeToSave.getUid(), mockFlatFormDao)).andReturn(envelopeToSave);
        
        replay(mockArchiverDao);
        
        storage.save(envelopeToSave);
        
        Envelope retrievedEnvelope = storage.retrieveEnvelope(envelopeToSave.getUid());
        
        assertEquals(envelopeToSave, retrievedEnvelope);

        verify(mockArchiverDao);
    }
    
    @Test
    public void shouldGetTheContentsOfASavedEnvelopeUsingTheFlatFormDao() {
        Envelope envelopeToSave = createMockFlatFormEnvelope();
        
        assertNotNull(envelopeToSave.getContents());
        
        expect(mockFlatFormDao.getContent(envelopeToSave.getUid())).andReturn((FlatForm) envelopeToSave.getContents());

        replay(mockFlatFormDao);
        
        storage.save(envelopeToSave);
        
        FlatForm savedContents = storage.getContent(envelopeToSave.getUid());

        verify(mockFlatFormDao);
        
        assertNotNull(savedContents);
        
        assertEquals(envelopeToSave.getContents(), savedContents);
        
    }
    
    @Test
    public void shouldGetAllSavedEnvelopesUsingArchiverDaoProxiedWithFlatFormDao() {
        int EXPECTED_NUMBER_OF_ENVELOPES = rnd.nextInt(100);
        
        // mock expectations
        expect(mockArchiverDao.saveProxy(isA(Envelope.class), eq(mockFlatFormDao))).andReturn(null).times(EXPECTED_NUMBER_OF_ENVELOPES);

        List<Envelope> envelopesToSave = new ArrayList<Envelope>();
        
        for (int i=0; i<EXPECTED_NUMBER_OF_ENVELOPES; i++) {
            Envelope newEnvelopeToSave = createMockFlatFormEnvelope();
            envelopesToSave.add(newEnvelopeToSave);
        }
        
        expect(mockArchiverDao.getAllProxiedEnvelopes(mockFlatFormDao)).andReturn(envelopesToSave);
        replay(mockArchiverDao);
        
        for (Envelope envelopeToSave : envelopesToSave) {
            storage.save(envelopeToSave);
        }
        
        List<? extends Envelope> retrievedEnvelopes = storage.retrieveAllEnvelopes();
        
        assertNotNull(retrievedEnvelopes);
    }
    
    @Test
    public void shouldUseFlatFormDaoToRetrieveAllContents() {
        int EXPECTED_NUMBER_OF_ENVELOPES = rnd.nextInt(100);
        
        // mock expectations
        expect(mockArchiverDao.saveProxy(isA(Envelope.class), eq(mockFlatFormDao))).andReturn(null).times(EXPECTED_NUMBER_OF_ENVELOPES);

        List<FlatForm> savedContents = new ArrayList<FlatForm>();

        for (int i=0; i<EXPECTED_NUMBER_OF_ENVELOPES; i++) {
            Envelope newEnvelopeToSave = createMockFlatFormEnvelope();
            savedContents.add((FlatForm)newEnvelopeToSave.getContents());
        }
        
        expect(mockFlatFormDao.getAllContents()).andStubReturn(savedContents);
        
        replay(mockFlatFormDao);
        
        List<? extends FlatForm> allContents = storage.getAllContents();
        
        verify(mockFlatFormDao);
        
        assertNotNull(allContents);
        
    }
}
