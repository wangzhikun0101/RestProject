package com.westvalley.g.materiel.purchase;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.westvalley.g.materiel.GUtil;
import com.westvalley.g.sap.SAPCall;
import weaver.conn.RecordSet;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.DetailTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;
import weaver.workflow.action.BaseAction;
import weaver.workflow.request.RequestManager;

/**
 * 功能：采购信息记录
 */

public class InfoRecord extends BaseAction {

	/**
	 * 操作：
	 * 		回写成败相关信息
	 */
	@Override
	public String execute(RequestInfo requestInfo) {
		String flag="1";
		// 获取流程主表信息
		Property[] properties = requestInfo.getMainTableInfo().getProperty();
		
		String gysbm="";//1.供应商-主表gysbm
		String cgzz="";//2.采购组织-主表cgzz
		String gc="";//3.工厂-主表gc
		String xxlb="";//4.采购信息记录分类-主表xxlb
		
		String cgz="";//6.采购组-主表cgz
		
		
		//9.生产版本
		//10.过量交货限度
		
		
		//13.价格单位
		
		//15.订单价格单位转换为订单单位的分母
		//16.订单价格单位转换为订单单位的分子
		
		
		
		String flags="";//19.单字符标记
		String fanhxx="";//20.字符100
		
		String gysbms="";//21.供应商或债权人的帐号-主表gysbm2
		
		String cgzzs="";//23.采购组织-主表cgzz2
		String gcs="";//24.工厂-主表gc2
		String xxlbs="";//25.采购信息记录分类-主表xxlb2
		
		
		//28.条件类型
		//29.描述
		//30.条件单位（货币或百分比）
		//31.条件定价单位
		//32.条件单位
		
		
		
		String wullx="";//物料类型
		String sfzzhk="";//是否中转港币
		String bib1="";//币别1
		String bib2="";//币别2
		
		String jiaglx="";//价格类型
		
		String zggys="";//转港供应商
		String zggc="";//转港工厂
		
		StringBuilder msg=new StringBuilder();
		
		for (Property p : properties) {
			if ("gysbm".equals(p.getName())) {//1.供应商
				gysbm=p.getValue();
				gysbms=p.getValue();
			}else if ("cgzz".equals(p.getName())) {//2.采购组织
				cgzz=p.getValue();
				cgzzs=p.getValue();
			}else if("gc".equals(p.getName())){//3.工厂
				gc=p.getValue();
				gcs=p.getValue();
			}else if("xxlb".equals(p.getName())){//4.采购信息记录分类
				xxlb=p.getValue();
				xxlbs=p.getValue();
			}else if("cgz".equals(p.getName())){//5.采购组
				cgz=p.getValue();
			}else if("wllx".equals(p.getName())){//6.物料类型
				wullx=p.getValue();
			}else if("sfzzhk".equals(p.getName())){//7.是否中转HK
				sfzzhk=p.getValue();
			}else if("bb1".equals(p.getName())){//8.币别1
				bib1=p.getValue();
			}else if("bb2".equals(p.getName())){//9.币别2
				bib2=p.getValue();
			}else if("jglx".equals(p.getName())){//10.价格类型
				jiaglx=p.getValue();
			}else if("zggys".equals(p.getName())){//11.转港供应商
				zggys=p.getValue();
			}else if("zggc".equals(p.getName())){//12.转港工厂
				zggc=p.getValue();
			}
		}
		
		if("0".equals(xxlb)){
			xxlb="0";
		}else if("1".equals(xxlb)){
			xxlb="3";
		}
		
		if("0".equals(xxlbs)){
			xxlb="0";
		}else if("1".equals(xxlbs)){
			xxlb="3";
		}
		
		if("1".equals(bib1)){
			bib1="CNY";
		}else if("2".equals(bib1)){
			bib1="USD";
		}else if("3".equals(bib1)){
			bib1="HKD";
		}else if("4".equals(bib1)){
			bib1="JPY";
		}
		
		if("1".equals(bib2)){
			bib2="CNY";
		}else if("2".equals(bib2)){
			bib2="USD";
		}else if("3".equals(bib2)){
			bib2="HKD";
		}else if("4".equals(bib2)){
			bib2="JPY";
		}
		
		RecordSet rs=new RecordSet();
		
		//明细
		DetailTableInfo detailTableInfo = requestInfo.getDetailTableInfo();
		//明细表可能有多个
		DetailTable[] detailTables = detailTableInfo.getDetailTable();
		//行对象
		Row[] rows = detailTables[0].getRow();
		int i=0;
		int j=0;
		//判断物料类型
		wullx=new GUtil().huoQWuLiaoLeiXing(wullx);//id转类型代码
		
		String xinx="";
		String xinx1="";
		
		if("Z012".equals(wullx) || "Z013".equals(wullx) || "Z040".equals(wullx) || "Z001".equals(wullx)){//Z012、Z013、Z040、Z001
			SAPCall call=null;
			JCoFunction function=null;
			try {
				call = new SAPCall();
				function = call.getFunction("ZRFC_PURCHASING_INFORECORD");//创建函数	
			
				//赋值表头信息
				JCoParameterList importParameterList = function.getImportParameterList();
				JCoParameterList tableParameterList = function.getTableParameterList();
				JCoTable table = tableParameterList.getTable("IT_MAT");//获取表名
				JCoTable tables = tableParameterList.getTable("IT_PRI");//获取表名
				
				rs.writeLog("00000000");
				
				String wlbm="";//22.物料编号-明细wlbm
				String wulbhOfSOS="";//物料编号辅助
				for (Row r : rows) {
					// 列对象
					Cell[] cells = r.getCell();
					String jhjhsj="";//5.计划交货时间（天）-明细jhjhsj
					String jyshdfpxy="";//7.标识：基于收货的发票验证-明细jyshdfpxy
					String sdm="";//8.销售/购买税代码-明细sdm
					String jj="";//11.净价-明细jj
					String hb="";//12.货币码-明细hb
					String dddwmrcgdw="";//14.订单价格单位（采购）-明细dddwmrcgdw
					String yxc="";//17.开始生效日期-明细yxc
					String yxz="";//18.有效截止日期-明细yxz
					String yxcs="";//26.开始生效日期-明细yxc2
					String yxzs="";//27.有效期截止日期-明细yxz2
					String tjdjsl="";//33.条件等级数量-明细tjdjsl
					String tjjehbfb="";//34.条件金额或百分比-明细tjjehbfb
					String xinjj="";//新净价
					wulbhOfSOS=wlbm;
					String tjdjsl1="";
					String tjdjsl2="";
					String tjdjsl3="";
					String tjdjsl4="";
					String tjdjsl5="";
					String tjdjsl6="";
					String tjdjsl7="";
					String tjjehbfb1="";
					String tjjehbfb2="";
					String tjjehbfb3="";
					String tjjehbfb4="";
					String tjjehbfb5="";
					String tjjehbfb6="";
					String tjjehbfb7="";
					String jiagsl="";
					for (Cell c : cells) {//读取列
						if ("jhjhsj".equals(c.getName())) {
							jhjhsj=c.getValue();
						} else if ("jyshdfpxy".equals(c.getName())) {
							jyshdfpxy=c.getValue();
						} else if ("sdm".equals(c.getName())) {
							sdm=c.getValue();
						} else if ("jj".equals(c.getName())) {
							jj=c.getValue();
						} else if ("hb".equals(c.getName())) {
							hb=c.getValue();
						} else if ("dddwmrcgdw".equals(c.getName())) {
							dddwmrcgdw=c.getValue();
						} else if ("yxc".equals(c.getName())) {
							yxc=c.getValue();
							yxcs=c.getValue();
						} else if ("yxz".equals(c.getName())) {
							yxz=c.getValue();
							yxzs=c.getValue();
						} else if ("wlbm".equals(c.getName())) {
							wlbm=c.getValue();
						} else if ("tjdjsl".equals(c.getName())) {//条件等级数量
							tjdjsl=c.getValue();
						} else if ("tjjehbfb".equals(c.getName())) {//条件金额或百分比
							tjjehbfb=c.getValue();
						} else if ("zghjj".equals(c.getName())) {
							xinjj=c.getValue();
						} else if ("tjdjsl1".equals(c.getName())) {//条件等级数量1
							tjdjsl1=c.getValue();
						} else if ("tjdjsl2".equals(c.getName())) {//条件等级数量2
							tjdjsl2=c.getValue();
						} else if ("tjdjsl3".equals(c.getName())) {//条件等级数量3
							tjdjsl3=c.getValue();
						} else if ("tjdjsl4".equals(c.getName())) {//条件等级数量4
							tjdjsl4=c.getValue();
						} else if ("tjdjsl5".equals(c.getName())) {//条件等级数量5
							tjdjsl5=c.getValue();
						} else if ("tjdjsl6".equals(c.getName())) {//条件等级数量6
							tjdjsl6=c.getValue();
						} else if ("tjdjsl7".equals(c.getName())) {//条件等级数量7
							tjdjsl7=c.getValue();
						} else if ("tjjehbfb1".equals(c.getName())) {//条件金额或百分比1
							tjjehbfb1=c.getValue();
						} else if ("tjjehbfb2".equals(c.getName())) {//条件金额或百分比2
							tjjehbfb2=c.getValue();
						} else if ("tjjehbfb3".equals(c.getName())) {//条件金额或百分比3
							tjjehbfb3=c.getValue();
						} else if ("tjjehbfb4".equals(c.getName())) {//条件金额或百分比4
							tjjehbfb4=c.getValue();
						} else if ("tjjehbfb5".equals(c.getName())) {//条件金额或百分比5
							tjjehbfb5=c.getValue();
						} else if ("tjjehbfb6".equals(c.getName())) {//条件金额或百分比6
							tjjehbfb6=c.getValue();
						} else if ("tjjehbfb7".equals(c.getName())) {//条件金额或百分比7
							tjjehbfb7=c.getValue();
						} else if ("jgslmrw1".equals(c.getName())) {//价格数量
							jiagsl=c.getValue();
						}
					}
					if("0".equals(sdm)){
						sdm="J0";
					}else if("1".equals(sdm)){
						sdm="J1";
					}else if("2".equals(sdm)){
						sdm="J2";
					}else if("3".equals(sdm)){
						sdm="J3";
					}else if("4".equals(sdm)){
						sdm="J4";
					}else if("5".equals(sdm)){
						sdm="J5";
					}else if("6".equals(sdm)){
						sdm="J6";
					} else if ("7".equals(sdm)) {
                        					sdm = "J9";
                    				}
					//判断是否中转HK港币
					if("0".equals(sfzzhk)){//是HK
						importParameterList.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));//非表
						//第一种币种~~~~~~~~~~
						//rs.writeLog("aa3港币非阶梯式");
						table.appendRows(1);
						table.setRow(i++);
						//1.物料编号
						table.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));//去零
						//2.采购组织
						table.setValue("EKORG", cgzz);
						//3.工厂
						table.setValue("WERKS", gc);
						//4.采购信息记录分类
						
						table.setValue("ESOKZ", xxlb);//下拉转换
						//5.计划交货时间（天）
						table.setValue("APLFZ", jhjhsj);
						//6.采购组
						table.setValue("EKGRP", cgz);
						//7.标识：基于收货的发票验证
						table.setValue("WEBRE", jyshdfpxy);
						//8.销售/购买税代码
						table.setValue("MWSKZ", sdm);//下拉转换
						//9.生产版本
						//10.过量交货限度
						//11.净价
						table.setValue("NETPR", jj);
						//12.货币码
						table.setValue("WAERS", bib1);
						//13.价格单位-价格数量
						table.setValue("PEINH", jiagsl);
						//14.订单价格单位（采购）
						table.setValue("BPRME", dddwmrcgdw);
						//15.订单价格单位转换为订单单位的分母
						//16.订单价格单位转换为订单单位的分子
						//17.开始生效日期
						table.setValue("DATAB", yxc.replace("-", ""));
						//18.有效截止日期 
						table.setValue("DATBI", yxz.replace("-", ""));
						//19.单字符标记
						//20.字符100
						
						if("0".equals(jiaglx)){
							rs.writeLog("港币阶梯式1");
							if(!"".equals(tjjehbfb) && !"".equals(jj)){//0
								rs.writeLog("港币阶梯式A");
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));
								rs.writeLog("港币阶梯式A9");
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								rs.writeLog("港币阶梯式A8");
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								rs.writeLog("港币阶梯式A7");
								//4.工厂
								tables.setValue("WERKS", gcs);
								rs.writeLog("港币阶梯式A6");
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								rs.writeLog("港币阶梯式A5");
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								rs.writeLog("港币阶梯式A4");
								//7.有效截止日期 
								tables.setValue("DATBI", yxzs.replace("-", ""));
								rs.writeLog("港币阶梯式A3");
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjjehbfb);//条件
								rs.writeLog("港币阶梯式A2");
								//14.条件金额或百分比
								tables.setValue("KBETR", jj);
								rs.writeLog("港币阶梯式A1");
							}
							if(!"".equals(tjdjsl1) && !"".equals(tjjehbfb1)){//2
								rs.writeLog("港币阶梯式B");
								//重新调用接口
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期 
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl1);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb1);
							}
							if(!"".equals(tjdjsl2) && !"".equals(tjjehbfb2)){//2
								rs.writeLog("港币阶梯式C");
								//重新调用接口
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期 
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl2);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb2);
							}
							if(!"".equals(tjdjsl3) && !"".equals(tjjehbfb3)){//3
								rs.writeLog("港币阶梯式D");
								//重新调用接口
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期 
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl3);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb3);
							}
							if(!"".equals(tjdjsl4) && !"".equals(tjjehbfb4)){//4
								rs.writeLog("港币阶梯式E");
								//重新调用接口
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期 
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl4);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb4);
							}
							if(!"".equals(tjdjsl5) && !"".equals(tjjehbfb5)){//5
								rs.writeLog("港币阶梯式F");
								//重新调用接口
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期 
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl5);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb5);
							}
							if(!"".equals(tjdjsl6) && !"".equals(tjjehbfb6)){//6
								rs.writeLog("港币阶梯式G");
								//重新调用接口
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期 
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl6);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb6);
							}
							if(!"".equals(tjdjsl7) && !"".equals(tjjehbfb7)){//7
								rs.writeLog("港币阶梯式H");
								//重新调用接口
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", zggys.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期 
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl7);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb7);
							}
						}
						
						//第二种币别
						SAPCall call2=null;
						JCoFunction function2=null;
						try {
							call2 = new SAPCall();
							function2 = call2.getFunction("ZRFC_PURCHASING_INFORECORD");//创建函数	
						
							rs.writeLog("aa1进来了...");
							//赋值表头信息
							JCoParameterList importParameterList2 = function2.getImportParameterList();
							importParameterList2.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));//非表
							JCoParameterList tableParameterList2 = function2.getTableParameterList();
							JCoTable table1s = tableParameterList2.getTable("IT_MAT");//获取表名
							JCoTable table2s = tableParameterList2.getTable("IT_PRI");//获取表名
							//第2种币种~~~~~~~~~~
							table1s.appendRows(1);
							table1s.setRow(i++);
							//1.物料编号
							table1s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));//去零
							//2.采购组织
							table1s.setValue("EKORG", cgzz);
							//3.工厂
							table1s.setValue("WERKS", zggc);
							//4.采购信息记录分类
							table1s.setValue("ESOKZ", xxlb);//下拉转换
							//5.计划交货时间（天）
							table1s.setValue("APLFZ", jhjhsj);
							//6.采购组
							table1s.setValue("EKGRP", cgz);
							//7.标识：基于收货的发票验证
							table1s.setValue("WEBRE", jyshdfpxy);
							//8.销售/购买税代码
							table1s.setValue("MWSKZ", sdm);//下拉转换
							//9.生产版本
							//10.过量交货限度
							//11.净价
							table1s.setValue("NETPR", xinjj);//汇率2
							//12.货币码
							table1s.setValue("WAERS", bib1);
							//13.价格单位-价格数量
							table1s.setValue("PEINH", jiagsl);
							//14.订单价格单位（采购）
							table1s.setValue("BPRME", dddwmrcgdw);
							//15.订单价格单位转换为订单单位的分母
							//16.订单价格单位转换为订单单位的分子
							//17.开始生效日期
							table1s.setValue("DATAB", yxc.replace("-", ""));
							//18.有效截止日期 
							table1s.setValue("DATBI", yxz.replace("-", ""));
							//19.单字符标记
							//20.字符100
							
							if("0".equals(jiaglx)){
								rs.writeLog("港币阶梯式2");
								if(!"".equals(tjjehbfb) && !"".equals(xinjj)){//0
									table2s.appendRows(1);
									table2s.setRow(j++);
									//1.供应商或债权人的帐号
									table2s.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
									//2.物料编号
									table2s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
									//3.采购组织
									table2s.setValue("EKORG", cgzzs);
									//4.工厂
									table2s.setValue("WERKS", zggc);
									//5.采购信息记录分类
									table2s.setValue("ESOKZ", xxlb);//下拉转换
									//6.开始生效日期
									table2s.setValue("DATAB", yxcs.replace("-", ""));
									//7.有效截止日期 
									table2s.setValue("DATBI", yxzs.replace("-", ""));
									//8.条件类型
									//9.描述
									//10.条件单位（货币或百分比）
									//11.条件定价单位
									table2s.setValue("KPEIN", jiagsl);
									//12.条件单位
									//13.条件等级数量
									table2s.setValue("KSTBM", tjjehbfb);
									//14.条件金额或百分比
									table2s.setValue("KBETR", xinjj);
								}
								if(!"".equals(tjdjsl1) && !"".equals(tjjehbfb1)){//1
									table2s.appendRows(1);
									table2s.setRow(j++);
									//1.供应商或债权人的帐号
									table2s.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
									//2.物料编号
									table2s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
									//3.采购组织
									table2s.setValue("EKORG", cgzzs);
									//4.工厂
									table2s.setValue("WERKS", zggc);
									//5.采购信息记录分类
									table2s.setValue("ESOKZ", xxlb);//下拉转换
									//6.开始生效日期
									table2s.setValue("DATAB", yxcs.replace("-", ""));
									//7.有效截止日期 
									table2s.setValue("DATBI", yxzs.replace("-", ""));
									//8.条件类型
									//9.描述
									//10.条件单位（货币或百分比）
									//11.条件定价单位
									table2s.setValue("KPEIN", jiagsl);
									//12.条件单位
									//13.条件等级数量
									table2s.setValue("KSTBM", tjdjsl1);
									//14.条件金额或百分比
									table2s.setValue("KBETR", tjjehbfb1);
								}
								if(!"".equals(tjdjsl2) && !"".equals(tjjehbfb2)){//2
									table2s.appendRows(1);
									table2s.setRow(j++);
									//1.供应商或债权人的帐号
									table2s.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
									//2.物料编号
									table2s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
									//3.采购组织
									table2s.setValue("EKORG", cgzzs);
									//4.工厂
									table2s.setValue("WERKS", zggc);
									//5.采购信息记录分类
									table2s.setValue("ESOKZ", xxlb);//下拉转换
									//6.开始生效日期
									table2s.setValue("DATAB", yxcs.replace("-", ""));
									//7.有效截止日期 
									table2s.setValue("DATBI", yxzs.replace("-", ""));
									//8.条件类型
									//9.描述
									//10.条件单位（货币或百分比）
									//11.条件定价单位
									table2s.setValue("KPEIN", jiagsl);
									//12.条件单位
									//13.条件等级数量
									table2s.setValue("KSTBM", tjdjsl2);
									//14.条件金额或百分比
									table2s.setValue("KBETR", tjjehbfb2);
								}
								if(!"".equals(tjdjsl3) && !"".equals(tjjehbfb3)){//3
									table2s.appendRows(1);
									table2s.setRow(j++);
									//1.供应商或债权人的帐号
									table2s.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
									//2.物料编号
									table2s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
									//3.采购组织
									table2s.setValue("EKORG", cgzzs);
									//4.工厂
									table2s.setValue("WERKS", zggc);
									//5.采购信息记录分类
									table2s.setValue("ESOKZ", xxlb);//下拉转换
									//6.开始生效日期
									table2s.setValue("DATAB", yxcs.replace("-", ""));
									//7.有效截止日期 
									table2s.setValue("DATBI", yxzs.replace("-", ""));
									//8.条件类型
									//9.描述
									//10.条件单位（货币或百分比）
									//11.条件定价单位
									table2s.setValue("KPEIN", jiagsl);
									//12.条件单位
									//13.条件等级数量
									table2s.setValue("KSTBM", tjdjsl3);
									//14.条件金额或百分比
									table2s.setValue("KBETR", tjjehbfb3);
								}
								if(!"".equals(tjdjsl4) && !"".equals(tjjehbfb4)){//4
									table2s.appendRows(1);
									table2s.setRow(j++);
									//1.供应商或债权人的帐号
									table2s.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
									//2.物料编号
									table2s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
									//3.采购组织
									table2s.setValue("EKORG", cgzzs);
									//4.工厂
									table2s.setValue("WERKS", zggc);
									//5.采购信息记录分类
									table2s.setValue("ESOKZ", xxlb);//下拉转换
									//6.开始生效日期
									table2s.setValue("DATAB", yxcs.replace("-", ""));
									//7.有效截止日期 
									table2s.setValue("DATBI", yxzs.replace("-", ""));
									//8.条件类型
									//9.描述
									//10.条件单位（货币或百分比）
									//11.条件定价单位
									table2s.setValue("KPEIN", jiagsl);
									//12.条件单位
									//13.条件等级数量
									table2s.setValue("KSTBM", tjdjsl4);
									//14.条件金额或百分比
									table2s.setValue("KBETR", tjjehbfb4);
								}
								if(!"".equals(tjdjsl5) && !"".equals(tjjehbfb5)){//5
									table2s.appendRows(1);
									table2s.setRow(j++);
									//1.供应商或债权人的帐号
									table2s.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
									//2.物料编号
									table2s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
									//3.采购组织
									table2s.setValue("EKORG", cgzzs);
									//4.工厂
									table2s.setValue("WERKS", zggc);
									//5.采购信息记录分类
									table2s.setValue("ESOKZ", xxlb);//下拉转换
									//6.开始生效日期
									table2s.setValue("DATAB", yxcs.replace("-", ""));
									//7.有效截止日期 
									table2s.setValue("DATBI", yxzs.replace("-", ""));
									//8.条件类型
									//9.描述
									//10.条件单位（货币或百分比）
									//11.条件定价单位
									table2s.setValue("KPEIN", jiagsl);
									//12.条件单位
									//13.条件等级数量
									table2s.setValue("KSTBM", tjdjsl5);
									//14.条件金额或百分比
									table2s.setValue("KBETR", tjjehbfb5);
								}
								if(!"".equals(tjdjsl6) && !"".equals(tjjehbfb6)){//6
									table2s.appendRows(1);
									table2s.setRow(j++);
									//1.供应商或债权人的帐号
									table2s.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
									//2.物料编号
									table2s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
									//3.采购组织
									table2s.setValue("EKORG", cgzzs);
									//4.工厂
									table2s.setValue("WERKS", zggc);
									//5.采购信息记录分类
									table2s.setValue("ESOKZ", xxlb);//下拉转换
									//6.开始生效日期
									table2s.setValue("DATAB", yxcs.replace("-", ""));
									//7.有效截止日期 
									table2s.setValue("DATBI", yxzs.replace("-", ""));
									//8.条件类型
									//9.描述
									//10.条件单位（货币或百分比）
									//11.条件定价单位
									table2s.setValue("KPEIN", jiagsl);
									//12.条件单位
									//13.条件等级数量
									table2s.setValue("KSTBM", tjdjsl6);
									//14.条件金额或百分比
									table2s.setValue("KBETR", tjjehbfb6);
								}
								if(!"".equals(tjdjsl7) && !"".equals(tjjehbfb7)){//7
									table2s.appendRows(1);
									table2s.setRow(j++);
									//1.供应商或债权人的帐号
									table2s.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
									//2.物料编号
									table2s.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
									//3.采购组织
									table2s.setValue("EKORG", cgzzs);
									//4.工厂
									table2s.setValue("WERKS", zggc);
									//5.采购信息记录分类
									table2s.setValue("ESOKZ", xxlb);//下拉转换
									//6.开始生效日期
									table2s.setValue("DATAB", yxcs.replace("-", ""));
									//7.有效截止日期 
									table2s.setValue("DATBI", yxzs.replace("-", ""));
									//8.条件类型
									//9.描述
									//10.条件单位（货币或百分比）
									//11.条件定价单位
									table2s.setValue("KPEIN", jiagsl);
									//12.条件单位
									//13.条件等级数量
									table2s.setValue("KSTBM", tjdjsl7);
									//14.条件金额或百分比
									table2s.setValue("KBETR", tjjehbfb7);
								}
							}//阶梯
							try {
								call2.excute(function2);
							} catch (JCoException e) {
								e.printStackTrace();
							}
							//打印传输值
							rs.writeLog(function2.toXML());
							
							//获取返回值
							//JCoParameterList exportParameterList = function.getTableParameterList();
							rs.writeLog("SIGN的值："+function2.getExportParameterList().getValue("SIGN").toString()+"</br>");
							//单字符标记
							rs.writeLog("SIGNX的值："+table1s.getValue("SIGNX").toString()+"</br>");
							//物料编号
							rs.writeLog("RETURN的值："+table1s.getValue("RETURN").toString()+"</br>");
							if("E".equals(function2.getExportParameterList().getValue("SIGN").toString())){
								flag="0";//阻止提交
								msg.append(wlbm+"中转返回信息："+table1s.getValue("RETURN").toString()+"。<br/>");
								xinx1+=wlbm+"中转返回信息："+table1s.getValue("RETURN").toString()+"；";//返回信息
							}else{
								xinx1+=wlbm+"维护成功；";
							}
						} catch (JCoException e1) {
							rs.writeLog("2调用..."+e1);
							e1.printStackTrace();
						}	
					}
					
					
					
					//B.非港币，仅一次
					else{
						importParameterList.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));//非表
						table.appendRows(1);
						table.setRow(i++);
						//1.物料编号
						String wulbm11=wlbm.replaceAll("^(0+)", "");
						table.setValue("MATNR", wulbm11);//去零
						//2.采购组织
						table.setValue("EKORG", cgzz);
						//3.工厂
						table.setValue("WERKS", gc);
						//4.采购信息记录分类
						table.setValue("ESOKZ", xxlb);//下拉转换
						//5.计划交货时间（天）
						table.setValue("APLFZ", jhjhsj);
						//6.采购组
						table.setValue("EKGRP", cgz);
						//7.标识：基于收货的发票验证
						table.setValue("WEBRE", jyshdfpxy);
						//8.销售/购买税代码
						table.setValue("MWSKZ", sdm);//下拉转换
						//9.生产版本
						//10.过量交货限度
						//11.净价
						table.setValue("NETPR", jj);//人民币的汇率*净价
						//12.货币码
						table.setValue("WAERS", bib1);
						//13.价格单位-价格数量
						table.setValue("PEINH", jiagsl);
						//14.订单价格单位（采购）
						table.setValue("BPRME", dddwmrcgdw);
						//15.订单价格单位转换为订单单位的分母
						//16.订单价格单位转换为订单单位的分子
						//17.开始生效日期
						String kaisrq11=yxc.replace("-", "");
						table.setValue("DATAB", kaisrq11);
						//18.有效截止日期 
						String jiesrq11=yxz.replace("-", "");
						table.setValue("DATBI", jiesrq11);
						//19.单字符标记
						//20.字符100
						if("0".equals(jiaglx)){//阶梯式
							rs.writeLog("aa2非港币阶梯式1");
							if(!"".equals(tjjehbfb) && !"".equals(jj)){//1
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								String gongysbms11=gysbm.replaceAll("^(0+)", "");
								tables.setValue("LIFNR", gongysbms11);
								//2.物料编号
								String wulbms11=wlbm.replaceAll("^(0+)", "");
								tables.setValue("MATNR", wulbms11);
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								String kaisrqs11=yxcs.replace("-", "");
								tables.setValue("DATAB", kaisrqs11);
								//7.有效截止日期
								String jiesrqs11=yxzs.replace("-", "");
								tables.setValue("DATBI", jiesrqs11);
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjjehbfb);
								//14.条件金额或百分比
								tables.setValue("KBETR", jj);
							}
							if(!"".equals(tjdjsl1) && !"".equals(tjjehbfb1)){//2
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl1);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb1);
							}
							if(!"".equals(tjdjsl2) && !"".equals(tjjehbfb2)){//3
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl2);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb2);
							}
							if(!"".equals(tjdjsl3) && !"".equals(tjjehbfb3)){//4
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl3);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb3);
							}
							if(!"".equals(tjdjsl4) && !"".equals(tjjehbfb4)){//5
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl4);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb4);
							}
							if(!"".equals(tjdjsl5) && !"".equals(tjjehbfb5)){//6
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl5);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb5);
							}
							if(!"".equals(tjdjsl6) && !"".equals(tjjehbfb6)){//7
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl6);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb6);
							}
							if(!"".equals(tjdjsl7) && !"".equals(tjjehbfb7)){//8
								tables.appendRows(1);
								tables.setRow(j++);
								//1.供应商或债权人的帐号
								tables.setValue("LIFNR", gysbm.replaceAll("^(0+)", ""));
								//2.物料编号
								tables.setValue("MATNR", wlbm.replaceAll("^(0+)", ""));
								//3.采购组织
								tables.setValue("EKORG", cgzzs);
								//4.工厂
								tables.setValue("WERKS", gcs);
								//5.采购信息记录分类
								tables.setValue("ESOKZ", xxlb);//下拉转换
								//6.开始生效日期
								tables.setValue("DATAB", yxcs.replace("-", ""));
								//7.有效截止日期
								tables.setValue("DATBI", yxzs.replace("-", ""));
								//8.条件类型
								//9.描述
								//10.条件单位（货币或百分比）
								//11.条件定价单位
								tables.setValue("KPEIN", jiagsl);
								//12.条件单位
								//13.条件等级数量
								tables.setValue("KSTBM", tjdjsl7);
								//14.条件金额或百分比
								tables.setValue("KBETR", tjjehbfb7);
							}
						}
					}//非港币
					
					try {
						call.excute(function);
					} catch (JCoException e) {
						e.printStackTrace();
					}
					//打印传输值
					rs.writeLog(function.toXML());
					
					//获取返回值
					//JCoParameterList exportParameterList = function.getTableParameterList();
					rs.writeLog("SIGN的值："+function.getExportParameterList().getValue("SIGN").toString()+"</br>");
					//单字符标记
					rs.writeLog("SIGNX的值："+table.getValue("SIGNX").toString()+"</br>");
					//物料编号
					rs.writeLog("RETURN的值："+table.getValue("RETURN").toString()+"</br>");
					if("E".equals(function.getExportParameterList().getValue("SIGN").toString())){
						flag="0";//阻止提交
						msg.append(wlbm+"："+table.getValue("RETURN").toString()+"。<br/>");
						xinx+=wlbm+"："+table.getValue("RETURN").toString()+"；";//返回信息
					}else{
						xinx+=wlbm+"维护成功；";
					}
					
				}//for行
			} catch (JCoException e1) {
				rs.writeLog("0调用..."+e1);
				e1.printStackTrace();
			}	
		}//if类型
		
		RequestManager requestManager = requestInfo.getRequestManager();
		if(msg.length()>0){
			flag="0";
			requestManager.setMessageid("111000");
			msg.insert(0, "很抱歉，提交失败。原因如下：<br/>");
			requestManager.setMessagecontent(msg.toString());
		}
		
		if(!"".equals(xinx1)){
			xinx=xinx1+xinx;
		}
		
		//修改返回信息
		this.updateForm(requestInfo, xinx);
		
		return flag;
	}
	
	/**
	 * 更新表单信息
	 * @param rq
	 * @param fanhxx
	 * @return
	 */
	private boolean updateForm(RequestInfo rq,String fanhxx) {
		int formid = rq.getRequestManager().getFormid();
		formid = Math.abs(formid);  
		String requestid = rq.getRequestid();
		RecordSet rs = new RecordSet();
		String sql = "update formtable_main_"+formid+" set sapxxfh='"+fanhxx+"' where requestid='"+requestid+"'";
		return rs.execute(sql);
	}
	
}
