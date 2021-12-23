package com.westvalley.g.finance.pay;

import weaver.conn.RecordSet;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;
import weaver.workflow.request.RequestManager;
/**
 * （1）	在会计节点前附加操作 删除已生成的明细
 * @author tangqf 
 *
 */
public class DeleteAccountMXService extends BaseAction{
	private String info = "会计节点退回:";	
	public String execute(RequestInfo requestInfo) {
		//获取本地数据源
		RecordSet rs = new RecordSet();
		String flag = "0";
		RequestManager requestManager = requestInfo.getRequestManager();
		String requestid = requestInfo.getRequestid();//请求ID      
		int formid = requestManager.getFormid();
		formid = Math.abs(formid); 
		
		
		String  deletesql2="delete from formtable_main_"+formid+"_dt2 where mainid in (select  id from formtable_main_"+formid+" where requestid='"+requestid+"' )";
		boolean isS2=rs.execute(deletesql2);
		if(isS2){
			flag="1";
		}else{
			flag = "0";
		}
		rs.writeLog(info+"deletesql2>"+deletesql2);
		String  deletesql3="delete from formtable_main_"+formid+"_dt3 where mainid in (select  id from formtable_main_"+formid+" where requestid='"+requestid+"' )";
		boolean isS3=rs.execute(deletesql3);
		if(isS3){
			flag="1";
		}else{
			flag = "0";
		}
		rs.writeLog(info+"deletesql3>"+deletesql3);
		
		
		return flag;
	}
	
	
}
