package org.gatherdata.forms.transform.xml.internal;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.gatherdata.core.io.MimeTypes;
import org.gatherdata.core.model.Envelope;
import org.gatherdata.core.model.GenericEnvelope;
import org.gatherdata.core.net.GatherUrnFactory;
import org.gatherdata.forms.core.model.FlatForm;
import org.junit.Test;


/**
 * Testing the behvaior of the FlatFormEnvelopeTransformer.
 *
 */
public class FlatFormEnvelopeTransformerTest {

    GatherUrnFactory urnFactory = new GatherUrnFactory();
    
    /**
     * The FlatFormEnvelopeTransformer should accept an Envelope
     * and return a single resulting Envelope. It should not 
     * decompose the contents into multiple results.
     */
    @Test
    public void shouldTransformTextXmlIntoASingleResult() {
        final String textXmlContent = "<foo><bar>1.0</bar></foo>"; 
        Envelope textXmlEnvelope = new GenericEnvelope(urnFactory.getLocalUrn(), textXmlContent, MimeTypes.TEXT_XML);
        
        FlatFormEnvelopeTransformer transformer = new FlatFormEnvelopeTransformer();
        Collection<Envelope> transformedEnvelopes = transformer.transform(textXmlEnvelope);
        assertEquals(1, transformedEnvelopes.size());
    }

    /**
     * The FlatFormEnvelopeTransformer should accept an
     * Envelope with {@link MimeTypes#TEXT_XML} data, producing an envelope
     * with {@link MimeTypes#JAVA_OBJECT} qualified as a FlatForm.
     */
    @Test
    public void shouldTransformTextXmlIntoApplicationJavaObject() {
        final String textXmlContent = "<foo><bar>value</bar></foo>"; 
        Envelope textXmlEnvelope = new GenericEnvelope(urnFactory.getLocalUrn(), textXmlContent, MimeTypes.TEXT_XML);
        
        FlatFormEnvelopeTransformer transformer = new FlatFormEnvelopeTransformer();
        Collection<Envelope> transformedEnvelopes = transformer.transform(textXmlEnvelope);
        Envelope firstEnvelope = (Envelope) CollectionUtils.get(transformedEnvelopes, 0);
        assertEquals(MimeTypes.JAVA_OBJECT, firstEnvelope.getType());
        assertEquals(FlatForm.class.getName(), firstEnvelope.getQualifier());
    }
}
