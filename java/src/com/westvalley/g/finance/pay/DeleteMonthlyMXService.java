package com.westvalley.g.finance.pay;

import weaver.conn.RecordSet;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;
import weaver.workflow.request.RequestManager;
/**
 * （1）	在月结出纳节点前附加操作 删除已生成的明细
 * @author tangqf 
 *
 */
public class DeleteMonthlyMXService extends BaseAction{
	private String info = "出纳节点退回:";	
	public String execute(RequestInfo requestInfo) {
		//获取本地数据源
		RecordSet rs = new RecordSet();
		String flag = "0";
		RequestManager requestManager = requestInfo.getRequestManager();
		String requestid = requestInfo.getRequestid();//请求ID      
		int formid = requestManager.getFormid();
		formid = Math.abs(formid); 
		
		
		String  deletesql1="delete from formtable_main_"+formid+"_dt1 where mainid in (select  id from formtable_main_"+formid+" where requestid='"+requestid+"' )";
		boolean isS1=rs.execute(deletesql1);
		if(isS1){
			flag="1";
		}else{
			flag = "0";
		}
		rs.writeLog(info+"deletesql1>"+deletesql1);
		String  deletesql2="delete from formtable_main_"+formid+"_dt2 where mainid in (select  id from formtable_main_"+formid+" where requestid='"+requestid+"' )";
		boolean isS2=rs.execute(deletesql2);
		if(isS2){
			flag="1";
		}else{
			flag = "0";
		}
		rs.writeLog(info+"deletesql2>"+deletesql2);
		return flag;
	}
	
	
}
