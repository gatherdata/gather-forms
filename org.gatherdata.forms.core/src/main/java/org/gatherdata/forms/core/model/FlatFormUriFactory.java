package org.gatherdata.forms.core.model;

import java.net.URI;

import org.gatherdata.core.net.CbidFactory;

public class FlatFormUriFactory {

	public static URI createUriFor(FlatForm formNeedingUri) {
		return CbidFactory.createCbid(formNeedingUri.getValues());
	}

	
}
