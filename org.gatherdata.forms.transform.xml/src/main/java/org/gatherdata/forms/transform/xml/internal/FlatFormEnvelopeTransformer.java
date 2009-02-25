package org.gatherdata.forms.transform.xml.internal;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;
import java.io.*;
import java.net.URI;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.gatherdata.core.io.MimeTypes;
import org.gatherdata.core.io.QualifiedType;
import org.gatherdata.core.model.Envelope;
import org.gatherdata.core.model.GenericEnvelope;
import org.gatherdata.core.model.Seal;
import org.gatherdata.core.spi.EnvelopeTransformer;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.FlatFormUriFactory;
import org.gatherdata.forms.core.model.RenderedValue;

/**
 * Transforms an Envelope containing text/xml into an Envelope containing a FlatForm
 * 
 */
public class FlatFormEnvelopeTransformer implements EnvelopeTransformer {

	private XmlToFlatFormTransform xmlTransformer = new XmlToFlatFormTransform();


	public Collection<Envelope> transform(Envelope source) {
		Collection<Envelope> resultSet = null;

		if (MimeTypes.equals(MimeTypes.TEXT_XML, source.getType())) {
			resultSet = new Vector<Envelope>();
			
			String rawXml = source.getContents().toString();

			FlatForm asFlatForm = xmlTransformer.transform(rawXml);

			URI formUid = FlatFormUriFactory.createUriFor(asFlatForm);
			Seal formSeal = Seal.createSealFor(asFlatForm);
			
			resultSet.add(new GenericEnvelope(formUid, source.getUid(), asFlatForm, 
					MimeTypes.JAVA_OBJECT, FlatForm.class.getName(), formSeal, GregorianCalendar.getInstance()));
						
		}
		return resultSet;
	}


    public QualifiedType[] getAcceptableTypes() {
        // TODO Auto-generated method stub
        return null;
    }
}

