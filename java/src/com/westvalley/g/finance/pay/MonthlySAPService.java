package com.westvalley.g.finance.pay;


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
public class MonthlySAPService extends BaseAction{
	private String info = "月结付款出纳生成凭证:";	
	public String execute(RequestInfo requestInfo) {
		//获取本地数据源
		RecordSet rs = new RecordSet();
		SysBasicService sysb=new SysBasicService();
		String flag = "0";
		RequestManager requestManager = requestInfo.getRequestManager();
		StringBuilder msg=new StringBuilder();
        String requestid = requestInfo.getRequestid();//请求ID   

		String V_SUBRC="";//返回值类型
        String  V_MESSAGE="";//返回值信息
        String  E_BELNR="";//返回凭证
		//主表
		MainTableInfo main = requestInfo.getMainTableInfo();//主表
		Property[] property = main.getProperty();//主表字段
		//明细表
		DetailTableInfo detail = requestInfo.getDetailTableInfo();//所有明细表
		try{
			SAPCall call = new SAPCall();	
			JCoFunction function = call.getFunction("ZRFC_ACC_DOCUMENT_POST");//创建函数	
			//赋值表头信息
			JCoParameterList tableParameterList = function.getTableParameterList();
		    JCoParameterList importParameterList = function.getImportParameterList();
		    JCoParameterList exportParameterList = function.getExportParameterList();
			JCoStructure structure =importParameterList.getStructure("DOC_HEADER");			//主表
			
			String HEADER_TXT = Util.null2String(this.getPropertyByName(property, "bz"));//事由
			String COMP_CODE = Util.null2String(this.getPropertyByName(property, "gsdm"));//公司代码
			String DOC_DATE = Util.null2String(this.getPropertyByName(property, "gzrq"));//会计凭证记账日期
			String DOC_TYPE = Util.null2String(this.getPropertyByName(property, "pzlx"));//会计凭证类型
			structure.setValue("HEADER_TXT", HEADER_TXT);
			structure.setValue("COMP_CODE", COMP_CODE);			
			structure.setValue("DOC_DATE", sysb.changeToDateYYYYMMDD(DOC_DATE));			
			structure.setValue("PSTNG_DATE", sysb.changeToDateYYYYMMDD(DOC_DATE));
			structure.setValue("FISC_YEAR", sysb.changeToDateYYYY(DOC_DATE));
			structure.setValue("FIS_PERIOD", sysb.changeToDateMM(DOC_DATE));
			structure.setValue("DOC_TYPE", DOC_TYPE);	
			if(detail != null && detail.getDetailTableCount() > 0){
				JCoTable tablemx = tableParameterList.getTable("DOC_ITEM");//获取表名
				Row[] rows1=null;
				Cell[] cells1=null;
				DetailTable d_table1 = detail.getDetailTable(0);//第2个明细表
				rows1 = d_table1.getRow();
				d_table1 = null;
				Row[] rows2=null;
				Cell[] cells2=null;
				DetailTable d_table2 = detail.getDetailTable(1);//第3个明细表
				rows2 = d_table2.getRow();
				d_table2 = null;
				int sumrow=rows1.length+rows2.length;
				tablemx.appendRows(sumrow);
                int i=0;
				//借方明细
				  for (Row row : rows1) {
				    tablemx.setRow(i); 
				  	cells1 = row.getCell();//明细每一列的值
				  	String jzm = this.getCellByName(cells1, "jzm");//
				  	String km = this.getCellByName(cells1, "gyskm");//
				  	String gysbh=this.getCellByName(cells1, "gysbh");//
				  	String jzje=this.getCellByName(cells1, "jzje");//
					String wb=this.getCellByName(cells1, "wb");//
					tablemx.setValue("BSCHL", jzm);//记账码
					tablemx.setValue("GL_ACCOUNT", km);//科目
					tablemx.setValue("VENDOR_NO", gysbh);//供应商编码
					tablemx.setValue("AMT_DOCCUR", jzje);//金额
					tablemx.setValue("ITEM_TEXT", wb);//文本
					tablemx.setValue("CURRENCY", "CNY");//汇率
					tablemx.setValue("COSTCENTER", "");//成本中心
					tablemx.setValue("ORDERID", "");//订单编号
					tablemx.setValue("TAX_CODE", "");//税代码
					tablemx.setValue("CUSTOMER", "");//客户编码 默认无
					tablemx.setValue("PROFIT_CTR", "");//利润中心 默认无
					i++;
				  }
				  rows1=null; cells1=null; 
				//贷方明细
				  for (Row row : rows2) {
					tablemx.setRow(i); 
				  	cells2 = row.getCell();//明细每一列的值
				  	String jzm = this.getCellByName(cells2, "jzm");
				  	String km = this.getCellByName(cells2, "km");
				  	String jzje=this.getCellByName(cells2, "jzje");
					String wb=this.getCellByName(cells2, "wb");
					tablemx.setValue("BSCHL", jzm);
					tablemx.setValue("GL_ACCOUNT", km);
					tablemx.setValue("CURRENCY", "CNY");
					tablemx.setValue("AMT_DOCCUR", jzje);
					tablemx.setValue("ITEM_TEXT", wb);
					tablemx.setValue("UMSKZ", "");
					tablemx.setValue("VENDOR_NO", "");//供应商
					tablemx.setValue("COSTCENTER", "");//成本中心
					tablemx.setValue("TAX_CODE", "");
					tablemx.setValue("ORDERID", "");//订单号
					tablemx.setValue("CUSTOMER", "");
					tablemx.setValue("PROFIT_CTR", "");
					i++;
				  }
				  rows2=null; cells2=null; 
			}
			rs.writeLog(info+function.toXML());
			call.excute(function);
			 //获取返回值
             V_SUBRC=exportParameterList.getValue("V_SUBRC").toString();
             V_MESSAGE=exportParameterList.getValue("V_MESSAGE").toString();
             E_BELNR=exportParameterList.getValue("E_BELNR").toString();
	         String  E_BUKRS=exportParameterList.getValue("E_BUKRS").toString();
	         String  E_GJAHR=exportParameterList.getValue("E_GJAHR").toString();
	         //打印返回值
	         rs.writeLog(info+requestid+"retvalue:"+exportParameterList.toXML());
			//修改返回信息
			this.updateForm(requestInfo, E_BELNR, V_MESSAGE);
		}catch(Exception e){
			rs.writeLog("调用创建月结付款接口异常..."+e);
			msg.append("调用月结付款接口异常，"+e);
		}
		main=null; detail=null; property=null; 
		if("S".equals(V_SUBRC)){//返回成功
			flag="1";
		}else{//返回异常
			flag="0";
		//	requestManager.setMessageid("111000");
			msg.append(V_MESSAGE);
			//msg.insert(0, "很抱歉，提交失败。原因如下：<br/>");
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
	private boolean updateForm(RequestInfo rq,String pzh,String sapxxfh) {
		int formid = rq.getRequestManager().getFormid();
		formid = Math.abs(formid);  
		String requestid = rq.getRequestid();
		RecordSet rs = new RecordSet();
		String sql = "update formtable_main_"+formid+" set pzh='"+pzh+"',sapxxfh='"+sapxxfh+"'where requestid='"+requestid+"'";
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
