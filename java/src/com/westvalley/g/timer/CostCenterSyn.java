package com.westvalley.g.timer;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.westvalley.g.sap.SAPCall;
import com.westvalley.g.sysbasic.SysBasicService;

import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;
import weaver.interfaces.schedule.CronJob;

/**
 * 抓取成本中心数据
 * ZRFC_GET_CSKT 获取成本中心
导出表;IT_TAB
KOKRS	控制范围	               CHAR	4
KOSTL	成本中心	               CHAR	10
DATBI	有效期截止日期 	           DATS  	8
KTEXT	一般姓名 	               CHAR	20
LTEXT	描述	                   CHAR	40

 */
public class CostCenterSyn extends BaseCronJob implements CronJob {
	/**
	 * 定时器同步
	 */
	public void execute() {
		RecordSet rs=new RecordSet();
		SysBasicService sbs=new SysBasicService();
		try {
			SAPCall call = new SAPCall();	
			JCoFunction function = call.getFunction("ZRFC_GET_CSKT");//创建函数	
		
			//赋值表头信息
			JCoParameterList importParameterList = function.getImportParameterList();
			
			JCoParameterList tableParameterList = function.getTableParameterList();
			
			//第1个表名
			JCoTable table = tableParameterList.getTable("IT_TAB");//获取表名
			
			call.excute(function);
			
			//获取返回值
			JCoParameterList exportParameterList = function.getTableParameterList();
			JCoTable t1 = exportParameterList.getTable("IT_TAB");
			
			for(int j=0;j<t1.getNumRows();j++){
				t1.setRow(j);
				String KOKRS=t1.getValue("KOKRS").toString();
				String KOSTL=t1.getValue("KOSTL").toString();
				String DATBI=sbs.changeDateFormat(t1.getValue("DATBI").toString());
				String KTEXT=t1.getValue("KTEXT").toString();
				String LTEXT=t1.getValue("LTEXT").toString();
				//删除此成本中心的，因为名字可能更换
				RecordSet rsOfDelete=new RecordSet();
				String sqlOfDelete="delete from WV_CostCenter where KOSTL='"+KOSTL+"'";
				boolean flagOfDelete=rsOfDelete.execute(sqlOfDelete);
				rs.writeLog("flagOfDelete："+flagOfDelete);
				if(flagOfDelete){
					RecordSet rsOfInsert=new RecordSet();
					String sqlOfInsert="insert into WV_CostCenter(KOKRS,KOSTL,DATBI,KTEXT,LTEXT) values('"+KOKRS+"','"+KOSTL+"','"+DATBI+"','"+KTEXT+"','"+LTEXT+"')";
					boolean flagOfInsert=rsOfInsert.execute(sqlOfInsert);
					if(flagOfInsert){
					}else{
						rs.writeLog("插入成本中心失败..."+sqlOfInsert+"。");
					}
				}
			}
		} catch (Exception e) {
			rs.writeLog("调用成本中心查询接口异常..."+e);
		}
	}
	
}
