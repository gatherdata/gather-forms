package org.gatherdata.forms.transform.xhtml.internal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.TransformerException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;

public class BirdTransformTest {

	@Test
	public void shouldTransformBirds() throws IOException, TransformerException {
		
		BirdTransform transform = new BirdTransform();
		Reader originalInput = new InputStreamReader(this.getClass().getClassLoader().getResource("birds.xml").openStream());
		StringWriter transformedOutput = new StringWriter();
		String expectedResult = readResource("birds.out");
		
		transform.transform(originalInput, transformedOutput);
		
		String transformedString = transformedOutput.toString();
		
		assertThat(transformedString, notNullValue());
		assertThat(transformedString, is(equalToIgnoringWhiteSpace(expectedResult)));
		
	}

	private String readResource(String resourceName) throws IOException {
		BufferedReader resourceReader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResource(resourceName).openStream()));
		StringBuffer buffer = new StringBuffer();
		String line = resourceReader.readLine();
		while (line != null) {
			buffer.append(line);
			buffer.append("\n");
			line = resourceReader.readLine();
		}
		return buffer.toString();
	}
}
