package org.gatherdata.forms.transform.xhtml.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class OrbeonToRosaTransform {
	
	URL xslStyleSheet;
	
	public OrbeonToRosaTransform() {
		xslStyleSheet = this.getClass().getClassLoader().getResource("birds.xsl");
	}
	

	public void transform(InputStream source, OutputStream result) throws IOException, TransformerException {
		TransformerFactory tFactory = TransformerFactory.newInstance();

		// Use the TransformerFactory to instantiate a Transformer that will
		// work with
		// the stylesheet you specify. This method call also processes the
		// stylesheet
		// into a compiled Templates object.
		Transformer transformer = tFactory.newTransformer(
				new StreamSource(xslStyleSheet.openStream()));

		// Use the Transformer to apply the associated Templates object to an
		// XML document
		// (foo.xml) and write the output to a file (foo.out).

		transformer.transform(new StreamSource(source), new StreamResult(result));
	}
}
