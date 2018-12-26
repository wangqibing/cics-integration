/**
 * copy
 */
package com.ccic.objectmapper;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author young.yu
 * @date Nov 29, 2018
 *
 */

public class TestJavaBean {

	private String certiType;
	private String certiNo;
	private String proposalNo;
	private String policyNo;
	// @JsonFilter("test.filter")

	private String underwriterCode;

	private JavaBeanSub subJavaBean;

	private Map<String, Object> dynamics;

	@JsonProperty(value = "certiType")
	public String getCertiType() {
		return this.certiType;
	}

	@JsonProperty(value = "certiType")
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	@JsonProperty(value = "certiNo")
	public String getCertiNo() {
		return this.certiNo;
	}

	@JsonProperty(value = "certiNo")
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	@JsonProperty(value = "proposalNo")
	public String getProposalNo() {
		return this.proposalNo;
	}

	@JsonProperty(value = "proposalNo")
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	@JsonProperty(value = "policyNo")
	public String getPolicyNo() {
		return this.policyNo;
	}

	@JsonProperty(value = "policyNo")
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@JsonProperty(value = "underwriterCode")
	public String getUnderwriterCode() {
		return this.underwriterCode;
	}

	@JsonProperty(value = "underwriterCode")
	public void setUnderwriterCode(String underwriterCode) {
		this.underwriterCode = underwriterCode;
	}

	/**
	 * @return the subJavaBean
	 */
	public JavaBeanSub getSubJavaBean() {
		return subJavaBean;
	}

	/**
	 * @param subJavaBean
	 *            the subJavaBean to set
	 */
	public void setSubJavaBean(JavaBeanSub subJavaBean) {
		this.subJavaBean = subJavaBean;
	}

	/**
	 * @param dynamics
	 *            the dynamics to set
	 */
	public void setDynamics(Map<String, Object> dynamics) {
		this.dynamics = dynamics;
	}

	/**
	 * @return the dynamics
	 */
	public Map<String, Object> getDynamics() {
		return dynamics;
	}
}
