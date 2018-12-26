package com.ccic.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by 8000600758 on 2018/10/22.
 */
@Data
public class ValueAddedServiceDTO {
    @JsonProperty(value = "serviceTypeCode")
    private String serviceTypeCode;
    @JsonProperty(value = "serviceTypeName")
    private String serviceTypeName;
    @JsonProperty(value = "serviceItemCode")
    private String serviceItemCode;
    @JsonProperty(value = "serviceItemDesc")
    private String serviceItemDesc;
    @JsonProperty(value = "regionCode")
    private String regionCode;
    @JsonProperty(value = "productCode")
    private String productCode;
    @JsonProperty(value = "policyFormCode")
    private String policyFormCode;
    @JsonProperty(value = "updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;

}
