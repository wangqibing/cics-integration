/**
 * 
 */
package com.ccic.objectmapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * @author young.yu
 *
 */
public class JSONLogSerializer {

	public static final String LOG_IGNORE_FILTER_NAME = "LOG_IGNORE_PROPERTIES_FILTER";
	public static final String INT_LOG_EXCEPT = "int.log.except.";
	public static final String INT_LOG_EXCEPT_PROPERTIES = ".properties";

	private ObjectMapper writerMapper = null;

	private JSONLogBeanPropertyFilter propertyFilter;
	private JSONSimpleMixInResolver resolver;

	private Map<Class<?>, Set<String>> exceptProperties;

	public JSONLogSerializer() {
		initialize();
	}

	private void initialize() {
		writerMapper = new ObjectMapper();
		// final SimpleModule baseEntityAsKeyModule = new SimpleModule();
		// writerMapper.registerModule(baseEntityAsKeyModule);
		writerMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// writerMapper.setDateFormat(new ISO8601WithoutTimeZoneDateFormat());
		// writerMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,
		// true);
		// writerMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER,
		// true);
		// writerMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,
		// true);
		// writerMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
		// true);
		// writerMapper.configure(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS,
		// true);
		// writerMapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS,
		// false);
		// writerMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,
		// true);
		// writerMapper.configure(Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
		// writerMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		//
		// writerMapper.disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
		// writerMapper.setPropertyNamingStrategy(new PascalCaseStrategy());
		// writerMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		// writerMapper.registerModules(new JavaTimeModule());
		propertyFilter = new JSONLogBeanPropertyFilter(this);
		resolver = new JSONSimpleMixInResolver(this);
		final FilterProvider provider = new SimpleFilterProvider().addFilter(LOG_IGNORE_FILTER_NAME, propertyFilter);
		writerMapper.setFilterProvider(provider);
		writerMapper.setMixInResolver(resolver);
		writerMapper.setAnnotationIntrospector(new JSONLogAnnotationIntrospector());
	}

	public String toJson(final Object obj) {
		try {
			return writerMapper.writeValueAsString(obj);
		} catch (final Exception e) {
		}
		return "";
	}

	/**
	 * @param exceptProperties
	 *            the exceptProperties to set
	 */
	public void setExceptProperties(final Map<Class<?>, Set<String>> exceptProperties) {
		if (null == this.exceptProperties) {
			this.exceptProperties = new HashMap<Class<?>, Set<String>>();
		}
		this.exceptProperties.putAll(exceptProperties);
	}

	public void addExceptProperties(final Class<?> clazz, final Set<String> properties) {
		if (null == this.exceptProperties) {
			this.exceptProperties = new HashMap<Class<?>, Set<String>>();
		}
		this.exceptProperties.put(clazz, properties);
	}

	/**
	 * @param clazz
	 * @return
	 */
	public Set<String> findExceptProperties(final Class<?> clazz) {
		return findMixInBeanProperties(clazz);
	}

	/**
	 * @return the propertyFilter
	 */
	public JSONLogBeanPropertyFilter getPropertyFilter() {
		return propertyFilter;
	}

	/**
	 * @return the resolver
	 */
	public JSONSimpleMixInResolver getResolver() {
		return resolver;
	}

	/**
	 * @param clazz
	 * @return
	 */
	public Class<?> findMixInClassFor(final Class<?> clazz) {
		if (isMixInBeanClass(clazz)) {
			return JSONLogMixIn.class;
		}
		return null;
	}

	private Set<String> findMixInBeanProperties(final Class<?> clazz) {
		if (null != exceptProperties && exceptProperties.containsKey(clazz)) {
			return exceptProperties.get(clazz);
		}
		final String key = INT_LOG_EXCEPT + clazz.getName() + INT_LOG_EXCEPT_PROPERTIES;
		final String exceptProperties = "";// Env.getParameter(key);
		if (!StringUtils.isEmpty(exceptProperties)) {
			final String[] properties = exceptProperties.split(",");
			final Set<String> props = new HashSet<String>(properties.length);
			Collections.addAll(props, properties);
			addExceptProperties(clazz, props);
			return props;
		}
		return null;
	}

	private boolean isMixInBeanClass(final Class<?> clazz) {
		if (null != exceptProperties && exceptProperties.containsKey(clazz)) {
			return true;
		}
		final String key = INT_LOG_EXCEPT + clazz.getName();
		final String exceptClass = "";// Env.getParameter(key);
		if (!StringUtils.isEmpty(exceptClass)) {
			try {
				final Class<?> cls = Class.forName(exceptClass);
				return cls == clazz;
			} catch (final ClassNotFoundException e) {
				return false;
			}
		}
		return false;
	}
}
