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
 * 功能：采购申请
 */

public class ApplyProcurement extends BaseAction {

	/**
	 * 操作：
	 * 		回写成败相关信息
	 */
	@Override
	public String execute(RequestInfo requestInfo) {
		String flag="1";
		// 获取流程主表信息
		Property[] properties = requestInfo.getMainTableInfo().getProperty();
		
		String wllx="";//1.物料类型-主表wllx
		
		String type="";//返回类型
		String message="";//返回信息
		
		StringBuilder msg=new StringBuilder();
		
		for (Property p : properties) {
			if ("cglx".equals(p.getName())) {//1.物料类型
				wllx=p.getValue();
			}
		}
		if("0".equals(wllx)){
			wllx="Z005";
		}else if("1".equals(wllx)){
			wllx="Z006";
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
		//wllx=new GUtil().huoQWuLiaoLeiXing(wllx);//id转类型代码
		
		String saphcprdh="";
		SAPCall call = null;
		JCoFunction function = null;
		try {
			call = new SAPCall();	
			function = call.getFunction("ZRFC_PR_CREATE");//创建函数		
		}catch(Exception e){
			e.printStackTrace();
			rs.writeLog("调用采购申请异常，"+e);
		}
		//赋值表头信息
		JCoParameterList importParameterList = function.getImportParameterList();
		importParameterList.getStructure("GW_HEAD").setValue("PR_TYPE", wllx);
		
		JCoParameterList tableParameterList = function.getTableParameterList();
		
		JCoTable table = tableParameterList.getTable("GT_ITEM");//获取表名
		for (Row r : rows) {
			// 列对象
			Cell[] cells = r.getCell();
			String wlbh="";//2.物料编号-明细wlbh
			String sqsl="";//3.申请数量-明细sqsl
			String dw="";//4.单位-明细dw
			String xqrq="";//5.需求日期-明细xqrq
			String gc="";//6.工厂-明细gc
			String cgz="";//7.采购组-明细cgz
			String xqz="";//8.需求者-明细xqz
			
			String kem="";//科目
			String chengbzx="";//成本中心
			
			for (Cell c : cells) {//读取列
				if ("wlbh".equals(c.getName())) {
					wlbh=c.getValue();
				} else if ("sqsl".equals(c.getName())) {
					sqsl=c.getValue();
				} else if ("dw".equals(c.getName())) {
					dw=c.getValue();
				} else if ("xqrq".equals(c.getName())) {
					xqrq=c.getValue();
				} else if ("gc".equals(c.getName())) {
					gc=c.getValue();
				} else if ("cgz".equals(c.getName())) {
					cgz=c.getValue();
				} else if ("xqz".equals(c.getName())) {
					xqz=c.getValue();
				} else if ("km".equals(c.getName())) {//科目
					kem=c.getValue();
				} else if ("cbzx".equals(c.getName())) {//成本中心
					chengbzx=c.getValue();
				}
			}
			
			try {
				table.appendRows(1);
				table.setRow(i++);
				//1.订单类型（采购）
				table.setValue("BSART", wllx);
				
				//5.物料编号
				table.setValue("MATNR", wlbh.replaceAll("^(0+)", ""));//去零
				//6.数量
				table.setValue("MENGE", sqsl);
				//7.基本计量单位
				table.setValue("MEINS", dw);
				//8.交货日期
				table.setValue("EEIND", xqrq.replace("-", ""));
				//9.工厂
				table.setValue("WERKS", gc);
				//10.采购组
				table.setValue("EKGRP", cgz);
				//11.需求者/要求者名称
				table.setValue("AFNAM", new GUtil().getWorkcode(xqz));//根据ID查询工号
				if("Z005".equals(wllx)){//z005
					//科目
					table.setValue("SAKTO", kem);
					//成本中心
					table.setValue("KOSTL", chengbzx);
				}else{
					//科目
					table.setValue("SAKTO", "");
					//成本中心
					table.setValue("KOSTL", "");
				}
				
			} catch (Exception e) {
				rs.writeLog("调用采购申请接口异常..."+e);
				msg.append("第"+i+"行明细错误，返回信息为：调用采购创建接口异常，"+e+"<br/>");
			}
		}//for
		
		try {
			call.excute(function);
		} catch (JCoException e) {
			e.printStackTrace();
			rs.writeLog("执行采购申请接口失败.."+e);
		}
		//打印传输值
		rs.writeLog(function.toXML());
		
		//获取返回值
		JCoTable tables = tableParameterList.getTable("GT_RETURN");	
		rs.writeLog("TYPE的值："+tables.getValue("TYPE").toString()+"</br>");
		rs.writeLog("ID的值："+tables.getValue("ID").toString()+"</br>");
		rs.writeLog("NUMBER的值："+tables.getValue("NUMBER").toString()+"</br>");
		rs.writeLog("MESSAGE的值："+tables.getValue("MESSAGE").toString()+"</br>");
		if(!"".equals(function.getExportParameterList().getValue("MESSAGE").toString())){
			flag="0";//阻止提交
			msg.append(""+function.getExportParameterList().getValue("MESSAGE").toString()+"。<br/>");
		}
		saphcprdh+=function.getExportParameterList().getValue("BANFN").toString()+",";
		rs.writeLog("BANFN的值："+function.getExportParameterList().getValue("BANFN").toString()+"</br>"); 
		
		if(!"".equals(saphcprdh)){
			saphcprdh=saphcprdh.substring(0,saphcprdh.lastIndexOf(","));
		}
		
		//修改返回信息
		if(!"".equals(msg.toString())){	
			this.updateForm(requestInfo, msg.toString(), saphcprdh);
		}else{
			this.updateForm(requestInfo, "采购申请成功。", saphcprdh);
		}
		
		RequestManager requestManager = requestInfo.getRequestManager();
		if(msg.length()>0){
			flag="0";
			requestManager.setMessageid("111000");
			msg.insert(0, "很抱歉，提交失败。原因如下：<br/>");
			requestManager.setMessagecontent(msg.toString());
		}
		
		
		return flag;
	}
	
	/**
	 * 更新表单信息
	 * @param rq
	 * @param fanhxx
	 * @return
	 */
	private boolean updateForm(RequestInfo rq,String fanhxx,String saphcprdh) {
		int formid = rq.getRequestManager().getFormid();
		formid = Math.abs(formid);  
		String requestid = rq.getRequestid();
		RecordSet rs = new RecordSet();
		String sql = "update formtable_main_"+formid+" set sapxxfh='"+fanhxx+"',saphcprdh='"+saphcprdh+"' where requestid='"+requestid+"'";
		return rs.execute(sql);
	}
}
