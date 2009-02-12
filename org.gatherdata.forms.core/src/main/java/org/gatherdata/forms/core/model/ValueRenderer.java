package org.gatherdata.forms.core.model;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.gatherdata.core.util.GatherDateTime;

public class ValueRenderer {

	public static void render(String value, GenericRenderedValue intoRendering) {
		intoRendering.setValueAsString(value);
		
		Calendar valueAsDate = renderAsCalendar(value);
		if (valueAsDate != null) {
			intoRendering.setValueAsDate(valueAsDate);
		}
		
		try {
			float valueAsFloat = Float.parseFloat(value);
			intoRendering.setValueAsFloat(valueAsFloat);
		} catch (NumberFormatException nfe) {;}

        int valueAsInt = 0;
        try {
            valueAsInt = Integer.parseInt(value);
			intoRendering.setValueAsInt(valueAsInt);
		} catch (NumberFormatException nfe) {;}
		
		intoRendering.setValueAsBoolean(Boolean.parseBoolean(value));
		
	}

	public static Calendar renderAsCalendar(String value) {
		Calendar renderedCalendar = null;
		
		try {
			renderedCalendar = GatherDateTime.parse(value);
		} catch (ParseException e) { ; }
		
		return renderedCalendar;
	}
}
