/**
 * copy
 */
package com.ccic.objectmapper;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author young.yu
 * @date Nov 29, 2018
 *
 */
public final class TestJsonMapper {

	public static void main(String[] args) {
		final TestJavaBean obj = new TestJavaBean();
		obj.setCertiNo("111111111");
		obj.setPolicyNo("PDE0000000000000");
		obj.setProposalNo("PPOD9999999999999");
		obj.setUnderwriterCode("88");
		final JavaBeanSub subJavaBean = new JavaBeanSub();
		subJavaBean.setUndwrtDate("9324758425");
		subJavaBean.setUndwrtName("AAAAAAAAA");
		subJavaBean.setUndwrtText("HHHHHHHHHHHHH");
		obj.setSubJavaBean(subJavaBean);
		final Map<String, Object> dynamics = new LinkedHashMap<String, Object>();
		dynamics.put("AAAAAA", "99999999");
		dynamics.put("BBBBBB", "8888888888");
		obj.setDynamics(dynamics);
		final String str = ObjectToJson(obj);
		System.out.println(str);
	}

	public static String ObjectToJson(final Object obj) {
		final JSONLogSerializer serializer = new JSONLogSerializer();
		final Set<String> subproperties = new HashSet<String>();
		subproperties.add("undwrtText");
		final Set<String> properties = new HashSet<String>();
		properties.add("underwriterCode");
		serializer.addExceptProperties(JavaBeanSub.class, subproperties);
		serializer.addExceptProperties(TestJavaBean.class, properties);
		return serializer.toJson(obj);
	}
}
