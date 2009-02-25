package org.gatherdata.forms.transform.xml.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.gatherdata.core.io.MimeTypes;
import org.gatherdata.core.model.Envelope;
import org.gatherdata.core.spi.EnvelopeTransformer;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.FlatFormUriFactory;
import org.gatherdata.forms.core.model.GenericFlatForm;
import org.gatherdata.forms.core.model.GenericRenderedValue;
import org.gatherdata.forms.core.model.RenderedValue;
import org.gatherdata.forms.core.model.ValueRenderer;

/**
 * Transforms XML content into a FlatForm.
 * 
 */
public class XmlToFlatFormTransform extends DefaultHandler {
	
	private GenericFlatForm currentForm = null;
	private StringBuffer textBuffer;
	private Stack<String> xpath = new Stack<String>();

	public FlatForm transform(String xml) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new ByteArrayInputStream(xml
					.getBytes("UTF-8")), this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		currentForm.setCbid(FlatFormUriFactory.createUriFor(currentForm));
		return currentForm;
	}

	public void startDocument() throws SAXException {
		xpath.clear();
		currentForm = new GenericFlatForm();
	}

	public void endDocument() throws SAXException {
		
	}

	public void startElement(String namespaceURI, String sName, // simple name
			String qName, // qualified name
			Attributes attrs) throws SAXException {
		if (currentForm.getNamespace() == null) {
			try {
                currentForm.setNamespace(new URI(namespaceURI));
            } catch (URISyntaxException e) {
                e.printStackTrace();
                currentForm.setNamespace(null);
            }
		}
		
		xpath.push(sName);
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				String aName = attrs.getLocalName(i); // Attr name
				if ("".equals(aName))
					aName = attrs.getQName(i);
				renderValue(namespaceURI, aName, attrs.getValue(i));
			}
		}                                                   
	}

	private void renderValue(String namespace, String tag, String valueAsString) {
		GenericRenderedValue aRenderedValue = new GenericRenderedValue();
		aRenderedValue.setNamespace(namespace);
		aRenderedValue.setPath(getPathAsString());
		aRenderedValue.setTag(tag);
		ValueRenderer.render(valueAsString, aRenderedValue);
		
		currentForm.addToValues(aRenderedValue);
	}

	public void endElement(String namespaceURI, String sName, // simple name
			String qName // qualified name
	) throws SAXException {

		xpath.pop();
		
		String valueAsString = getText();
		if (!"".equals(valueAsString)) {
			renderValue(namespaceURI, sName, valueAsString);
		}
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);
		if (textBuffer == null) {
			textBuffer = new StringBuffer(s);
		} else {
			textBuffer.append(s);
		}
	}

	private String getText() throws SAXException {
		if (textBuffer == null)
			return "";
		String s = "" + textBuffer;
		textBuffer = null;
		return s.trim();
	}
	
	private String getPathAsString() {
		StringBuffer pathAsString = new StringBuffer();
		Iterator<String> it = xpath.iterator();
		while (it.hasNext()) {
			String s = it.next();
			pathAsString.append(s);
			if (it.hasNext()) {
				pathAsString.append('/');
			}
		}
		return pathAsString.toString();
	}
	
}
