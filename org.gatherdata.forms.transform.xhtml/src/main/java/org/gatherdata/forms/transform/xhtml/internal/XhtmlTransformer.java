package org.gatherdata.forms.transform.xhtml.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.osgi.framework.BundleContext;

public class XhtmlTransformer {
    
    BundleContext bc;

    public String orbeonToRosa(String xhtml) {
        String rosaXhtml = null;
        try {
            rosaXhtml = transform("simple-orbeon.xhtml", "orb2rosa.xsl");
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rosaXhtml;
    }

    public String transform(String xhtml, String xslResourceName) throws TransformerException, IOException {
        Reader originalInput = new StringReader(xhtml);

        URL xslUrl = bc.getBundle().getResource(xslResourceName);
        Reader xsl = new InputStreamReader(xslUrl.openStream());
        
        StringWriter transformedString = new StringWriter();
        TransformerFactory tFactory = TransformerFactory.newInstance();

        Transformer transformer = tFactory.newTransformer(
                new StreamSource( xsl));

        transformer.transform(new StreamSource(originalInput), new StreamResult(transformedString));
        
        return transformedString.toString();
    }

    public URL getResource(String resourceName) {
        return this.getClass().getClassLoader().getResource(resourceName);
    }
}
