package com.westvalley.g.materiel.deliveryorder;

/**
 * 
 * 返回到SAP的信息
 *
 */
public class ReturnBean {
	private String zhuangtm;//状态码
	private String xinx;//返回信息
	
	public String getZhuangtm() {
		return zhuangtm;
	}
	public void setZhuangtm(String zhuangtm) {
		this.zhuangtm = zhuangtm;
	}
	public String getXinx() {
		return xinx;
	}
	public void setXinx(String xinx) {
		this.xinx = xinx;
	}
}
