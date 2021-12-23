package com.westvalley.g.sap;

import weaver.conn.RecordSet;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;

public class SAPCall {
	
	JCoDestination jCoDestination = null;
	RecordSet rs=new RecordSet();
	
	public SAPCall() throws JCoException {
		this.jCoDestination = SAPClientFactory.getJcoConnection();
		rs.writeLog("11");
	}
	
	/**
	 * 获取SAP方法
	 * @param functionName
	 * @return
	 * @throws JCoException
	 */
	public JCoFunction getFunction(String functionName) throws JCoException {
		JCoRepository repository = jCoDestination.getRepository();
		repository.removeFunctionTemplateFromCache(functionName);
		JCoFunctionTemplate functionTemplate = repository.getFunctionTemplate(functionName);
		JCoFunction function = functionTemplate.getFunction();
		rs.writeLog("22");
		return function;
	}
	
	/**
	 * 执行
	 * @param function
	 * @throws JCoException
	 */
	public void excute(JCoFunction function) throws JCoException {
		function.execute(jCoDestination);
		rs.writeLog("33");
	}
}
