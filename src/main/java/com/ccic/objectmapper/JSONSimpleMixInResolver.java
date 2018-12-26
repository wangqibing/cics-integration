/**
 * 
 */
package com.ccic.objectmapper;

import com.fasterxml.jackson.databind.introspect.SimpleMixInResolver;

/**
 * @author young.yu
 *
 */
public class JSONSimpleMixInResolver extends SimpleMixInResolver {

	private JSONLogSerializer serializer;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7911729524197416939L;

	/**
	 * @param overrides
	 */
	public JSONSimpleMixInResolver(final JSONLogSerializer serializer) {
		super(null);
		this.serializer = serializer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.introspect.SimpleMixInResolver#
	 * findMixInClassFor(java.lang.Class)
	 */
	@Override
	public Class<?> findMixInClassFor(final Class<?> cls) {
		final Class<?> clazz = this.serializer.findMixInClassFor(cls);
		return null == clazz ? super.findMixInClassFor(cls) : clazz;
	}

}
