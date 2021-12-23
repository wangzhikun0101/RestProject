package com.westvalley.g.finance.pay;

import weaver.conn.RecordSet;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.westvalley.g.sap.SAPCall;
import com.westvalley.g.sysbasic.SysBasicService;

public class getReamiainingLoan {
/**
 * 
 * @param LIFNR SAP员工编号
 * @param BELNR 会计凭证号
 * @param tablename 借贷表   IT_TAB_H   IT_TAB_S
 * H贷方表；如果费用端调用S表查询，出纳端调用H表后，判断费用端的凭证号与H表的凭证号是否一致，如果一致则返回字段
 * @return
 */
	public String getLoanJson(String LIFNR,String BELNR,String tablename){
		RecordSet rs=new RecordSet();
		SysBasicService sbs=new SysBasicService();
				StringBuffer sb=new StringBuffer();
				sb.append("[");
				try {
					SAPCall call = new SAPCall();	
					JCoFunction function = call.getFunction("ZRFC_GET_BSIK");//创建函数	
					//赋值表头信息
					JCoParameterList importParameterList = function.getImportParameterList();
					importParameterList.setValue("LIFNR", LIFNR);
					call.excute(function);			
					//获取返回值
					JCoParameterList exportParameterList = function.getTableParameterList();
					JCoTable t1 = exportParameterList.getTable(tablename);
					rs.writeLog("LIFNR:"+LIFNR);
					for(int j=0;j<t1.getNumRows();j++){
						t1.setRow(j);
						   if(BELNR.equals(t1.getValue("BELNR").toString())){
						   sb.append("{\"BUKRS\":\""+t1.getValue("BUKRS").toString()+"\",");//公司代码
						   sb.append("\"LIFNR\":\""+t1.getValue("LIFNR").toString()+"\",");//供应商或债权人的帐号
						   sb.append("\"NAME1\":\""+t1.getValue("NAME1").toString()+"\",");//名称
						   sb.append("\"BELNR\":\""+t1.getValue("BELNR").toString()+"\",");//凭证号
						   sb.append("\"GJAHR\":\""+t1.getValue("GJAHR").toString()+"\",");//年度
						   sb.append("\"BUZEI\":\""+t1.getValue("BUZEI").toString()+"\",");//行号
						   sb.append("\"BUDAT\":\""+sbs.changeDateFormat(t1.getValue("BUDAT").toString())+"\",");//凭证中的过账日期
						   sb.append("\"WRBTR\":\""+t1.getValue("WRBTR").toString()+"\"");//凭证货币金额
						   sb.append("}"); 
						   sb.append(","); 
						   }
					}
				} catch (Exception e) {
					rs.writeLog("调用个人供应商欠款余额查询接口异常..."+e);
				}
				String sbstr=sb.toString();
				if(sbstr.indexOf(",")>-1){
					sbstr=sbstr.substring(0,sbstr.length() - 1);
				}
				sbstr=sbstr+"]";
				rs.writeLog("sbstr:"+sbstr);
				return sbstr;
	}

}
