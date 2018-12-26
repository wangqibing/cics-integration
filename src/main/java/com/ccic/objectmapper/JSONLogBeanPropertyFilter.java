/**
 * 
 */
package com.ccic.objectmapper;

import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

/**
 * @author young.yu
 *
 */
public class JSONLogBeanPropertyFilter extends SimpleBeanPropertyFilter {

	private JSONLogSerializer serializer;

	/**
	* 
	*/
	public JSONLogBeanPropertyFilter(final JSONLogSerializer serializer) {
		this.serializer = serializer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter#
	 * serializeAsField(java.lang.Object,
	 * com.fasterxml.jackson.core.JsonGenerator,
	 * com.fasterxml.jackson.databind.SerializerProvider,
	 * com.fasterxml.jackson.databind.ser.PropertyWriter)
	 */
	@Override
	public void serializeAsField(final Object pojo, final JsonGenerator jgen, final SerializerProvider provider,
			final PropertyWriter writer) throws Exception {
		if (include(pojo.getClass(), writer)) {
			writer.serializeAsField(pojo, jgen, provider);
		} else if (!jgen.canOmitFields()) { // since 2.3
			writer.serializeAsOmittedField(pojo, jgen, provider);
		}
	}

	protected boolean include(final Class<?> clazz, final PropertyWriter writer) {
		final Set<String> properties = this.serializer.findExceptProperties(clazz);
		if (null != properties && properties.contains(writer.getName())) {
			return false;
		}
		return super.include(writer);
	}
}
