/**
 * 
 */
package com.ccic.objectmapper;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * @author young.yu
 *
 */
@JsonFilter(JSONLogSerializer.LOG_IGNORE_FILTER_NAME)
public interface JSONLogMixIn {

}
