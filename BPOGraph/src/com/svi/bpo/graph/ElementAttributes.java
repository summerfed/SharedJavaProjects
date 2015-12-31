package com.svi.bpo.graph;

import com.svi.bpo.graph.ElementFunctions;

/**
 * This enum contains list of Editable Element Field Names Accessible Publicly
 * @author fmanangan
 *
 */
public enum ElementAttributes {
	PRIORITY(ElementFunctions.ELEMENT_ATTR_PRIORITY),
	FILE_POINTER(ElementFunctions.ELEMENT_ATTR_FILE_POINTER),
	EXTRA1(ElementFunctions.ELEMENT_ATTR_EXTRA1),
	EXTRA2(ElementFunctions.ELEMENT_ATTR_EXTRA2),
	EXTRA3(ElementFunctions.ELEMENT_ATTR_EXTRA3);
	
	
	private String attribute;
	private ElementAttributes(String attribute) {
		this.attribute = attribute;
	}
	
	public String toString() {
		return attribute;
	}
}

