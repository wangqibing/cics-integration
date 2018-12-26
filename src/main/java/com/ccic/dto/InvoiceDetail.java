package com.ccic.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 8000600758 on 2018/10/19.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceDetail {
       @XmlElement(name = "InvoiceType")
       private String invoiceType;
       @XmlElement(name = "VatRateType")
       private String vatRateType;
       @XmlElement(name = "TaxRate")
       private String taxRate;
       @XmlElement(name = "VatPlanFee")
       private String vatPlanFee;
       @XmlElement(name = "TniPlanFee")
       private String tniPlanFee;
}
