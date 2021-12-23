package com.westvalley.g.finance.pay;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.westvalley.g.sap.SAPCall;
import com.westvalley.g.sysbasic.SysBasicService;

import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.DetailTableInfo;
import weaver.soa.workflow.request.MainTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;
import weaver.workflow.action.BaseAction;
import weaver.workflow.request.RequestManager;

/**
 * （1）	在会计节点生成凭证
 * @author tangqf 
 *
 */
public class TellerSAPService extends BaseAction{
	private String info = "费用报销出纳节点生成凭证:";	
	public String execute(RequestInfo requestInfo) {
		//获取本地数据源
		RecordSet rs = new RecordSet();
		if(requestInfo == null || requestInfo.getMainTableInfo() == null || requestInfo.getMainTableInfo().getPropertyCount() == 0 ){ 
			requestInfo.getRequestManager().setMessageid("TellerSAPService");
			requestInfo.getRequestManager().setMessagecontent(info+"：未获取到表单信息！");
			return "0";
		}
		SysBasicService sysb=new SysBasicService();
		String flag = "0";
		RequestManager requestManager = requestInfo.getRequestManager();
		StringBuilder msg=new StringBuilder();
		String V_SUBRC="";//返回值类型
        String  V_MESSAGE="";//返回值信息
        String  E_BELNR="";//返回凭证
        
        String V_SUBRC_Q="";//清账成功标志
        String V_MESSAGE_Q="";//清账返回信息
        
        
        int sumrow=0;
        boolean ispush=false;
        String requestid = requestInfo.getRequestid();//请求ID   
		//主表
		MainTableInfo main = requestInfo.getMainTableInfo();//主表
		Property[] property = main.getProperty();//主表字段
		//明细表
		DetailTableInfo detail = requestInfo.getDetailTableInfo();//所有明细表
		try{
			SAPCall call = new SAPCall();	
			JCoFunction function = call.getFunction("ZRFC_ACC_DOCUMENT_POST1");//创建函数	
			//赋值表头信息
			JCoParameterList tableParameterList = function.getTableParameterList();
		    JCoParameterList importParameterList = function.getImportParameterList();
		    JCoParameterList exportParameterList = function.getExportParameterList();
			JCoStructure structure =importParameterList.getStructure("DOC_HEADER");			//主表
			
			String HEADER_TXT = Util.null2String(this.getPropertyByName(property, "sy"));//事由
			String COMP_CODE = Util.null2String(this.getPropertyByName(property, "gsdm"));//公司代码
			String DOC_DATE = Util.null2String(this.getPropertyByName(property, "cnpzgzrq"));//出纳凭证记账日期
			String DOC_TYPE = Util.null2String(this.getPropertyByName(property, "cnpzlx"));//出纳凭证类型
			
			String bxrybh=Util.null2String(this.getPropertyByName(property, "bxrybh"));//人员编号
			String hjpzh=Util.null2String(this.getPropertyByName(property, "hjpzh"));//会计凭证号
			structure.setValue("HEADER_TXT", HEADER_TXT);
			structure.setValue("COMP_CODE", COMP_CODE);			
			structure.setValue("DOC_DATE", sysb.changeToDateYYYYMMDD(DOC_DATE));			
			structure.setValue("PSTNG_DATE", sysb.changeToDateYYYYMMDD(DOC_DATE));
			structure.setValue("FISC_YEAR", sysb.changeToDateYYYY(DOC_DATE));
			structure.setValue("FIS_PERIOD", sysb.changeToDateMM(DOC_DATE));
			structure.setValue("DOC_TYPE", DOC_TYPE);	
			
			
			if(detail != null && detail.getDetailTableCount() > 0){
				//----------------出纳借贷明细表写入借款-------------  
				JCoTable tablemx = tableParameterList.getTable("DOC_ITEM");//获取表名
				Row[] rows4=null;
				Cell[] cells4=null;
				DetailTable d_table4 = detail.getDetailTable(3);//第4个明细表
				rows4 = d_table4.getRow();
				d_table4 = null;
				Row[] rows5=null;
				Cell[] cells5=null;
				DetailTable d_table5 = detail.getDetailTable(4);//第5个明细表
				rows5 = d_table5.getRow();
				d_table5 = null;
				
				 sumrow=rows4.length+rows5.length;
				tablemx.appendRows(sumrow);
                int i=0;
				//借方明细
				  for (Row row : rows4) {
				    tablemx.setRow(i); 
				  	cells4 = row.getCell();//明细每一列的值
				  	String jzm = this.getCellByName(cells4, "jzm");//
				  	String km = this.getCellByName(cells4, "km");//
				  	String rybh = this.getCellByName(cells4, "rybh");
				  	String jzje=this.getCellByName(cells4, "jzje");//
					String wb=this.getCellByName(cells4, "wb");//
					tablemx.setValue("BSCHL", jzm);//记账码
					tablemx.setValue("GL_ACCOUNT", km);//科目
					tablemx.setValue("AMT_DOCCUR", jzje);//金额
					tablemx.setValue("VENDOR_NO", rybh);//供应商
					tablemx.setValue("CURRENCY", "CNY");//汇率
					tablemx.setValue("ITEM_TEXT", wb);//文本
					tablemx.setValue("COSTCENTER", "");//成本中心
					tablemx.setValue("ORDERID", "");//订单编号
					tablemx.setValue("TAX_CODE", "");//税代码
					tablemx.setValue("CUSTOMER", "");//客户编码 默认无
					tablemx.setValue("PROFIT_CTR", "");//利润中心 默认无
					tablemx.setValue("UMSKZ", "");
					
					i++;
				  }
				  rows4=null; cells4=null; 
				//贷方明细
				  for (Row row : rows5) {
					tablemx.setRow(i); 
				  	cells5 = row.getCell();//明细每一列的值
				  	String jzm = this.getCellByName(cells5, "jzm");
				  	String km = this.getCellByName(cells5, "km");
				  	String rybh = this.getCellByName(cells5, "rybh");
				  	String tbzzbs = this.getCellByName(cells5, "tbzzbs");
				  	String jzje=this.getCellByName(cells5, "jzje");
					String wb=this.getCellByName(cells5, "wb");
					
					tablemx.setValue("AMT_DOCCUR", jzje);
					tablemx.setValue("CURRENCY", "CNY");
					tablemx.setValue("COSTCENTER", "");//成本中心
					tablemx.setValue("GL_ACCOUNT", km);
					tablemx.setValue("ITEM_TEXT", wb);
					tablemx.setValue("ORDERID", "");//订单号
					tablemx.setValue("PROFIT_CTR", "");
					tablemx.setValue("TAX_CODE", "");
					tablemx.setValue("UMSKZ", tbzzbs);
					tablemx.setValue("BSCHL", jzm);
					tablemx.setValue("VENDOR_NO", rybh);//供应商
					tablemx.setValue("CUSTOMER", "");
					
					i++;
				  }
				  rows5=null; cells5=null; 
				  
				  //--------------获取报销总金额----------------------
				  Row[] rows2=null;
				  Cell[] cells2=null;
				  DetailTable d_table2 = detail.getDetailTable(1);//第2个明细表
				  rows2 = d_table2.getRow();
				  double sumbxje=0.0;
				  for (Row row : rows2) {
					  cells2 = row.getCell();//明细每一列的值
					  double  a = Double.parseDouble(Util.null2String(this.getCellByName(cells2, "jzje")));//记账金额
					  sumbxje=sumbxje+a;
					  
				  }
				  rows2=null; cells2=null; 
				  //-------------获取借款信息---------------------
				  Row[] rows6=null;
				  Cell[] cells6=null;
				  DetailTable d_table6 = detail.getDetailTable(5);//第6个明细表
				  rows6 = d_table6.getRow();
				  
				  JCoTable table = tableParameterList.getTable("DOC_CX");//获取表名 会计凭证接口冲销凭证
				  double sumjkje=0.0;//之前的借款总额
				  double sumcxje=0.0;//本次冲销的总额
				  String loanjson="";
				  int sumcxrow=0;
				  if(rows6.length>0){
					  for (Row row : rows6) {
						  cells6 = row.getCell();//明细每一列的值
						  double  a = Double.parseDouble(Util.null2String(this.getCellByName(cells6, "jkje")));//借款金额
						  double  b = Double.parseDouble(Util.null2String(this.getCellByName(cells6, "cxje")));//冲销借款金额
						  sumjkje=sumjkje+a;
						  sumcxje=sumcxje+b;
						  if(b>0){
							  sumcxrow++;
						  }
					  }
				  }	  

				  // IT_TAB_H   IT_TAB_S
				  //无借款则读取 借款余额接口,取凭证号等于当前会计凭证号的的记录   
				  if(sumjkje==0||sumcxje==0){
					   loanjson=new getReamiainingLoan().getLoanJson(bxrybh,hjpzh,"IT_TAB_H");
					   JSONArray jomx = JSON.parseArray(loanjson);
					    table.appendRows(jomx.size());
					   for(int j=0;j<jomx.size();j++){
			                JSONObject temp=  jomx.getJSONObject(j);
			                String BELNR = temp.getString("BELNR");
						  	String GJAHR = temp.getString("GJAHR");
						  	String BUZEI = temp.getString("BUZEI");
					  	    table.setRow(j); 
							table.setValue("REBZG", BELNR);//凭证号
							table.setValue("REBZJ", GJAHR);//年度
							table.setValue("REBZZ", BUZEI);//行号
					  }
				  }/*else if(sumbxje<sumjkje){//有借款，报销金额小于借款金额
					  table.appendRows(sumcxrow);
					  int j=0;
					  for (Row row : rows6) {
						    cells6 = row.getCell();//明细每一列的值
							double  b = Double.parseDouble(this.getCellByName(cells6, "cxje"));//冲销借款金额
							if(b>0){
						    table.setRow(j); 
							table.setValue("REBZG", this.getCellByName(cells6, "ygbh"));//凭证号
							table.setValue("REBZJ", this.getCellByName(cells6, "ygxm"));//年度
							table.setValue("REBZZ", this.getCellByName(cells6, "fpxxmh"));//行号
							
						    j++;
							}
					  }
				  }*/else if(sumbxje>sumjkje||sumbxje<sumjkje){//1 有借款，报销金额大于借款金额 2 有借款，报销金额小于借款金额
					  loanjson=new getReamiainingLoan().getLoanJson(bxrybh,hjpzh,"IT_TAB_H");
					  JSONArray jomx = JSON.parseArray(loanjson);
					  table.appendRows(sumcxrow+jomx.size());
					  int j=0;
					  for (Row row : rows6) {
						    cells6 = row.getCell();//明细每一列的值
							double  b = Double.parseDouble(Util.null2String(this.getCellByName(cells6, "cxje")));//冲销借款金额
							if(b>0){
						    table.setRow(j); 
							table.setValue("REBZG", this.getCellByName(cells6, "ygbh"));//凭证号
							table.setValue("REBZJ", this.getCellByName(cells6, "ygxm"));//年度
							table.setValue("REBZZ", this.getCellByName(cells6, "fpxxmh"));//行号
						    j++;
							}
					  }
					  
					  for(int k=0;k<jomx.size();k++){
			                JSONObject temp=  jomx.getJSONObject(k);
			                String BELNR = temp.getString("BELNR");
						  	String GJAHR = temp.getString("GJAHR");
						  	String BUZEI = temp.getString("BUZEI");
					  	    table.setRow(j); 
							table.setValue("REBZG", BELNR);//凭证号
							table.setValue("REBZJ", GJAHR);//年度
							table.setValue("REBZZ", BUZEI);//行号
							j++;
					  }
				  }else if(sumbxje==sumjkje){
					  //冲销=借款什么都不会推
				  }
				  rows6=null; cells6=null; 
			}
			 //打印传输值
			rs.writeLog(info+requestid+function.toXML());
			call.excute(function);
			 //获取返回值
			
			 V_SUBRC=exportParameterList.getValue("V_SUBRC").toString();
             V_MESSAGE=exportParameterList.getValue("V_MESSAGE").toString();
             
			 V_SUBRC_Q=exportParameterList.getValue("V_SUBRC_Q").toString();
             V_MESSAGE_Q=exportParameterList.getValue("V_MESSAGE_Q").toString();
             if(V_SUBRC_Q.equals("E")){ //清账失败，也要提交流程，但是提示手工清账
            	 V_MESSAGE=V_MESSAGE+";提示【清账失败请手工处理!】";
             }
            
             E_BELNR=exportParameterList.getValue("E_BELNR").toString();
             //打印返回值
	         rs.writeLog(info+requestid+"retvalue:"+exportParameterList.toXML());
		}catch(Exception e){
			rs.writeLog("调用费用报销出纳接口异常..."+e);
			msg.append("调用费用报销出纳接口异常，"+e);
		}
		main=null; detail=null; property=null; 
		if("E".equals(V_SUBRC)){//返回异常
			flag="0";
			//requestManager.setMessageid("111000");
			msg.append(V_MESSAGE);
			msg.insert(0, "很抱歉，提交失败。原因如下：<br/>");
			requestManager.setMessagecontent(msg.toString());
		}else{//返回成功
			flag="1";
		}
		//修改返回信息
		this.updateForm(requestInfo, E_BELNR, V_MESSAGE);
		return flag;
	}
	
	/**
	 * 更新表单信息
	 * @param rq
	 * @param fanhxx
	 * @return
	 */
	private boolean updateForm(RequestInfo rq,String cnpzh,String cnpzsapfhxx) {
		int formid = rq.getRequestManager().getFormid();
		formid = Math.abs(formid);  
		String requestid = rq.getRequestid();
		RecordSet rs = new RecordSet();
		String sql = "update formtable_main_"+formid+" set cnpzh='"+cnpzh+"',cnpzsapfhxx='"+cnpzsapfhxx+"'where requestid='"+requestid+"'";
		return rs.execute(sql);
	}
	/**
	 * 获取主表字段的值
	 * @param property 主表Property数组
	 * @param name 字段名
	 * @return value 值
	 */
	private String getPropertyByName(Property[] property, String name){
		for(Property p : property){
			if(Util.null2String(name).equalsIgnoreCase(p.getName()))
				return Util.null2String(p.getValue());
		}
		return "";
	}
	/**
	 * 获取明细表字段的值
	 * @param cells 明细某行中列的集合
	 * @param name 字段名
	 * @return value 值
	 */
	private String getCellByName(Cell[] cells, String name){
		for(Cell c : cells){
			if(Util.null2String(name).equalsIgnoreCase(c.getName())){
				return Util.null2String(c.getValue());
			}
		}
		return "";
	}
	

	
	
}
