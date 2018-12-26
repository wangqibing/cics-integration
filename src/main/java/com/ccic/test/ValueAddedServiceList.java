package com.ccic.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by 8000600758 on 2018/10/22.
 */
@Data
public class ValueAddedServiceList {
    @JsonProperty(value = "valueAddedServiceList")
    List<ValueAddedServiceDTO> valueAddedServiceList;
}
