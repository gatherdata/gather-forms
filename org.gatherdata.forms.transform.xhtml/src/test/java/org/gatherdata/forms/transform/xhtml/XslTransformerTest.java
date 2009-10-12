package org.gatherdata.forms.transform.xhtml;

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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;

public class XslTransformerTest {

    @Test
    public void shouldTransformBirds() throws IOException, TransformerException {
        String actualResult = transform("birds.xml", "birds.xsl");
        String expectedResult = readResource("birds-out.xml");

        assertThat(actualResult, notNullValue());
        assertThat(actualResult, is(equalToIgnoringWhiteSpace(expectedResult)));
    }
    
    @Test
    public void shouldTransformSimpleOrbeonFormToRosa() throws IOException, TransformerException {
        String actualResult = transform("simple-orbeon.xhtml", "orb2rosa.xsl");
        String expectedResult = readResource("simple-rosa.xhtml");
        //writeTextFile(actualResult, "simple-result.xhtml");

        assertThat(actualResult, notNullValue());
        assertThat(actualResult, is(equalToIgnoringWhiteSpace(expectedResult)));
    }

    @Test
    public void shouldTransformFluSurveyToRosa() throws IOException, TransformerException {
        String actualResult = transform("flu-orbeon.xhtml", "orb2rosa.xsl");
        String expectedResult = readResource("flu-rosa.xhtml");
        //writeTextFile(actualResult, "flu-result.xhtml");

        assertThat(actualResult, notNullValue());
        assertThat(actualResult, is(equalToIgnoringWhiteSpace(expectedResult)));
    }


    public String transform(String inputResourceName, String xslResourceName) throws TransformerException, IOException {
        Reader originalInput = new InputStreamReader(getResource(inputResourceName)
                .openStream());

        Reader xsl = new InputStreamReader(getResource(xslResourceName).openStream());
        
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

    public String readResource(String resourceName) throws IOException {
        BufferedReader resourceReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader()
                .getResource(resourceName).openStream()));
        StringBuffer buffer = new StringBuffer();
        String line = resourceReader.readLine();
        while (line != null) {
            buffer.append(line);
            buffer.append("\n");
            line = resourceReader.readLine();
        }
        return buffer.toString();
    }

    public void writeTextFile(String text, String filename) throws FileNotFoundException {
        PrintWriter printer = new PrintWriter(new FileOutputStream(new File(filename)));
        printer.println(text);
        printer.flush();
        printer.close();
    }
}
