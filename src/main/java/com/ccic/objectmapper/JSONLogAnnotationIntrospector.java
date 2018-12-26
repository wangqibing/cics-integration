/**
 * copy
 */
package com.ccic.objectmapper;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties.Value;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * @author young.yu
 * @date Dec 3, 2018
 *
 */
public class JSONLogAnnotationIntrospector extends JacksonAnnotationIntrospector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2593350422776599247L;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector#
	 * findPropertyIgnorals(com.fasterxml.jackson.databind.introspect.Annotated)
	 */
	@Override
	public Value findPropertyIgnorals(Annotated a) {
		System.out.println(a.getRawType());
		if (a.getRawType() == LinkedHashMap.class) {
			return Value.forIgnoredProperties("AAAAAA");
		}
		return super.findPropertyIgnorals(a);
	}
}
