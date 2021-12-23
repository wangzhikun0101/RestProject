package com.westvalley.g.materiel.deliveryorder;


/**
 * 
 * SAP传入的信息
 *
 */
public class AfferentBean {
	private String id;//明细ID
	private String wulbh;//物品编号
	private String wulms;//物品描述
	private String danw;//单位
	private String xuqsl;//需求数量
	private String canggy;//仓管员
	private String shiy;//事由
	private String shijlysl;//实际领用数量
	private String lingyrq;//领用日期
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWulbh() {
		return wulbh;
	}
	public void setWulbh(String wulbh) {
		this.wulbh = wulbh;
	}
	public String getWulms() {
		return wulms;
	}
	public void setWulms(String wulms) {
		this.wulms = wulms;
	}
	public String getDanw() {
		return danw;
	}
	public void setDanw(String danw) {
		this.danw = danw;
	}
	public String getXuqsl() {
		return xuqsl;
	}
	public void setXuqsl(String xuqsl) {
		this.xuqsl = xuqsl;
	}
	public String getCanggy() {
		return canggy;
	}
	public void setCanggy(String canggy) {
		this.canggy = canggy;
	}
	public String getShiy() {
		return shiy;
	}
	public void setShiy(String shiy) {
		this.shiy = shiy;
	}
	public String getShijlysl() {
		return shijlysl;
	}
	public void setShijlysl(String shijlysl) {
		this.shijlysl = shijlysl;
	}
	public String getLingyrq() {
		return lingyrq;
	}
	public void setLingyrq(String lingyrq) {
		this.lingyrq = lingyrq;
	}
}
