package com.westvalley.g.finance.pay;

import weaver.conn.RecordSet;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;
import weaver.workflow.request.RequestManager;
/**
 * （1）	在出纳节点前附加操作 删除已生成的明细
 * @author tangqf 
 *
 */
public class DeleteTellerMXService extends BaseAction{
	private String info = "出纳节点退回:";	
	public String execute(RequestInfo requestInfo) {
		//获取本地数据源
		RecordSet rs = new RecordSet();
		String flag = "0";
		RequestManager requestManager = requestInfo.getRequestManager();
		String requestid = requestInfo.getRequestid();//请求ID      
		int formid = requestManager.getFormid();
		formid = Math.abs(formid); 
		String  deletesql4="delete from formtable_main_"+formid+"_dt4 where mainid in (select  id from formtable_main_"+formid+" where requestid='"+requestid+"' )";
		boolean isS4=rs.execute(deletesql4);
		if(isS4){
			flag="1";
		}else{
			flag = "0";
		}
		rs.writeLog(info+"deletesql4>"+deletesql4);
		String  deletesql5="delete from formtable_main_"+formid+"_dt5 where mainid in (select  id from formtable_main_"+formid+" where requestid='"+requestid+"' )";
		boolean isS5=rs.execute(deletesql5);
		if(isS5){
			flag="1";
		}else{
			flag = "0";
		}
		rs.writeLog(info+"deletesql5>"+deletesql5);
		return flag;
	}
	
	
}
