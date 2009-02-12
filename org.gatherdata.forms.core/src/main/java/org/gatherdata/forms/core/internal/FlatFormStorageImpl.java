/**
 * 
 */
package org.gatherdata.forms.core.internal;

import java.io.Serializable;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.List;

import org.gatherdata.archiver.core.spi.ArchiverDao;
import org.gatherdata.archiver.core.spi.EnvelopeArchiver;
import org.gatherdata.core.io.MimeTypes;
import org.gatherdata.core.io.QualifiedType;
import org.gatherdata.core.model.Envelope;
import org.gatherdata.core.model.GenericEnvelope;
import org.gatherdata.core.model.Seal;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.spi.FlatFormDao;
import org.gatherdata.forms.core.spi.FlatFormStorage;

import com.google.inject.Inject;

/**
 * Standard implementation of the FlatFormStorage, uses any available
 * FlatFormDao implementations injected by Guice.
 *
 */
public class FlatFormStorageImpl implements FlatFormStorage {

	FlatFormDao flatFormDao;
	
	ArchiverDao archiverDao;

    private static QualifiedType[] qualifiedTypes;
    
    static {
        qualifiedTypes = new QualifiedType[] {
                new QualifiedType(MimeTypes.JAVA_OBJECT, FlatForm.class.getName())
        };
    }

	@Inject
	public FlatFormStorageImpl(ArchiverDao archiverDao, FlatFormDao flatFormDao) {
		this.archiverDao = archiverDao;
		this.flatFormDao = flatFormDao;
	}

	/* (non-Javadoc)
	 * @see org.gatherdata.core.spi.EnvelopeStorage#save(org.gatherdata.core.model.Envelope)
	 */
	public Envelope save(Envelope envelopeToSave) {
		Envelope savedEnvelope = archiverDao.saveProxy(envelopeToSave, flatFormDao);
		return savedEnvelope;
	}

    public List<? extends FlatForm> getAllContents() {
        return flatFormDao.getAllContents();
    }

    public FlatForm getContent(URI uidOfContainingEnvelope) {
        return flatFormDao.getContent(uidOfContainingEnvelope);
    }

    public long getTotalNumberOfStoredEnvelopes() {
        return archiverDao.getNumberOfQualifiedEnvelopes(FlatForm.class.getName());
    }

    public List<? extends Envelope> retrieveAllEnvelopes() {
        return archiverDao.getAllProxiedEnvelopes(flatFormDao);
    }

    public Envelope retrieveEnvelope(URI uidOfEnvelope) {
        return archiverDao.getProxiedEnvelope(uidOfEnvelope, flatFormDao);
    }

    public QualifiedType[] getAcceptableTypes() {
        return qualifiedTypes;
    }

}
