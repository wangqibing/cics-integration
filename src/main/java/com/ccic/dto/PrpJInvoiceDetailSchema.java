package com.ccic.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by 8000600758 on 2018/10/19.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PrpJInvoiceDetailSchema {
    @XmlElement(name = "InvoiceDetail")
    private List<InvoiceDetail> invoiceDetail;
    @XmlElement(name = "SerialNo")
    private String serialNo;
    @XmlElement(name = "InvoiceCode")
    private String invoiceCode;
    @XmlElement(name = "InvoiceNo")
    private String invoiceNo;
    @XmlElement(name = "ReceiptNo")
    private String receiptNo;
    @XmlElement(name = "InvoiceStatus")
    private String invoiceStatus;
    @XmlElement(name = "PrintDate")
    private String printDate;
    @XmlElement(name = "Printer")
    private String printer;
    @XmlElement(name = "Remark")
    private String remark;
    @XmlElement(name = "OperateName")
    private String operateName;
    @XmlElement(name = "OperateDate")
    private String operateDate;
    @XmlElement(name = "SelfBuildFlag")
    private String selfBuildFlag;
}
