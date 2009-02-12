package org.gatherdata.forms.transform.xml.internal;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.gatherdata.core.util.GatherDateTime;
import org.gatherdata.forms.core.model.FlatForm;
import org.gatherdata.forms.core.model.RenderedValue;
import org.junit.Test;

public class XmlToFlatFormTransformTest {


    private String readTestXmlFrom(String resourceName) {
        StringBuilder contents = new StringBuilder();

        try {
            InputStream is = getClass().getResourceAsStream(resourceName);
            BufferedReader input = new BufferedReader(new InputStreamReader(is));
            try {
                String line = null; 
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }
    
	@Test
	public void shouldTransformSingleLevelDocWithTextValue() {
	    final String expectedStringValue = "ordinary text";
        final String testXml = "<foo>" + expectedStringValue + "</foo>";
        XmlToFlatFormTransform transformer = new XmlToFlatFormTransform();
        FlatForm transformedForm = transformer.transform(testXml);
        List<? extends RenderedValue> values = transformedForm.getValues();
        assertEquals(1, values.size());
        assertEquals(expectedStringValue, values.get(0).getValueAsString());
	}

    @Test
    public void shouldTransformSingleLevelDocWithFloatValue() {
        final float expectedValueAsFloat = 3.14159F;
        final String expectedValueAsString = Float.toString(expectedValueAsFloat);
        final String testXml = "<foo>" + expectedValueAsString + "</foo>";
        XmlToFlatFormTransform transformer = new XmlToFlatFormTransform();
        FlatForm transformedForm = transformer.transform(testXml);
        List<? extends RenderedValue> values = transformedForm.getValues();
        assertEquals(1, values.size());
        assertEquals(expectedValueAsString, values.get(0).getValueAsString());
        assertEquals(expectedValueAsFloat, values.get(0).getValueAsFloat(), 0.000001);
    }
    
    @Test
    public void shouldTransformSingleLevelDocWithDateValue() throws ParseException {
        final String originalCalendarString = "2009-01-22-05:14";
        final Calendar parsedCalendar = GatherDateTime.parse(originalCalendarString);
        
        final String testXml = "<foo>" + originalCalendarString + "</foo>";
        XmlToFlatFormTransform transformer = new XmlToFlatFormTransform();
        FlatForm transformedForm = transformer.transform(testXml);
        List<? extends RenderedValue> values = transformedForm.getValues();
        assertEquals(1, values.size());
        assertEquals(originalCalendarString, values.get(0).getValueAsString());
        assertEquals(parsedCalendar, values.get(0).getValueAsDate());
    }
    
    @Test
    public void shouldTransformSingleLevelDocWithBooleanValue() throws ParseException {
        final String originalBooleanString = "true";
        
        final String testXml = "<foo>" + originalBooleanString + "</foo>";
        XmlToFlatFormTransform transformer = new XmlToFlatFormTransform();
        FlatForm transformedForm = transformer.transform(testXml);
        List<? extends RenderedValue> values = transformedForm.getValues();
        assertEquals(1, values.size());
        assertEquals(originalBooleanString, values.get(0).getValueAsString());
        assertTrue(values.get(0).getValueAsBoolean());
    }
    
    @Test
    public void shouldTransformMultiValuedDoc() throws ParseException {
        final String originalBooleanString = "true";
        final String expectedStringValue = "ordinary text";
        final float expectedValueAsFloat = 1200.50F;
        final int expectedValueAsInt = 42;
        final String originalCalendarString = "2009-01-22-05:14";
        
        final String testXml = 
            "<bar>" 
            + "<hasBeer>" + originalBooleanString + "</hasBeer>"
            + "<todaysSpecials>" + expectedStringValue + "</todaysSpecials>"
            + "<cashInRegister>" + Float.toString(expectedValueAsFloat) + "</cashInRegister>"
            + "<patronCount>" + expectedValueAsInt + "</patronCount>"
            + "<timeofDay>" + originalCalendarString + "</timeofDay>" 
            + "</bar>";
        XmlToFlatFormTransform transformer = new XmlToFlatFormTransform();
        FlatForm transformedForm = transformer.transform(testXml);
        List<? extends RenderedValue> values = transformedForm.getValues();
        assertEquals(5, values.size());
        for (RenderedValue value : values) {
            assertTrue(value.getPath().equals("bar"));
        }
    }
    
    @Test
    public void shouldAllowRepeatedTagsAtSamePath() throws ParseException {        
        final String testXml = 
            "<developers>" 
            + "<name>Andreas Kollegger</name>" 
            + "<name>Jonathan Jackson</name>" 
            + "<name>Tom Hubschman</name>" 
            + "</developers>";
        XmlToFlatFormTransform transformer = new XmlToFlatFormTransform();
        FlatForm transformedForm = transformer.transform(testXml);
        List<? extends RenderedValue> values = transformedForm.getValues();
        assertEquals(3, values.size());
        for (RenderedValue value : values) {
            assertEquals("developers", value.getPath());
            assertEquals("name", value.getTag());
        }
    }
    
    @Test
    public void shouldAllowRepeatedTagsAtDifferentPaths() throws ParseException {        
        final String testXml = 
            "<developers>" 
            + "<tembo><name>Andreas Kollegger</name></tembo>" 
            + "<dimagi><name>Jonathan Jackson</name></dimagi>" 
            + "<other><name>Tom Hubschman</name></other>" 
            + "</developers>";
        XmlToFlatFormTransform transformer = new XmlToFlatFormTransform();
        FlatForm transformedForm = transformer.transform(testXml);
        List<? extends RenderedValue> values = transformedForm.getValues();
        assertEquals(3, values.size());
        for (RenderedValue value : values) {
            assertTrue(value.getPath().startsWith("developers"));
            assertEquals("name", value.getTag());
        }
    }

    @Test
    public void testTransformHmis033AInstanceData() {
        String testXml = readTestXmlFrom("/HMIS033A.xml");
        XmlToFlatFormTransform transformer = new XmlToFlatFormTransform();
        FlatForm transformedForm = transformer.transform(testXml);
        System.out.println(transformedForm);
    }
}
