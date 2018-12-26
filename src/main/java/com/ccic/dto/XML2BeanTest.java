package com.ccic.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * Created by 8000600758 on 2018/10/19.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "root")
public class XML2BeanTest {
    @XmlElement(name = "SourceCode")
    private String sourceCode;
    @XmlElement(name = "PrpJInvoiceDetailSchema")
    private PrpJInvoiceDetailSchema prpJInvoiceDetailSchema;

    public String toString(){
        return JSON.toJSONString(this);
    }
}
