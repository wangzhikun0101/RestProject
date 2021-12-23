package com.westvalley.g.timer;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.weaver.general.Util;
import com.westvalley.g.sap.SAPCall;

import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;
import weaver.interfaces.schedule.CronJob;

/**
 * 抓取供应商数据
 */
public class SuppliersSyn extends BaseCronJob implements CronJob {

	/**
	 * 定时器同步
	 */
	public void execute() {
		RecordSet rs=new RecordSet();
		try {
			SAPCall call = new SAPCall();	
			JCoFunction function = call.getFunction("ZRFC_GET_LIFNR");//创建函数	
		
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
				//打印返回值
				rs.writeLog("LIFNR的值："+t1.getValue("LIFNR").toString());
				rs.writeLog("NAME1的值："+t1.getValue("NAME1").toString());
				rs.writeLog("FAX_NUMBER的值："+t1.getValue("FAX_NUMBER").toString());
				String suppliercode=t1.getValue("LIFNR").toString();
				String suppliername=t1.getValue("NAME1").toString();
				String sapcode=Util.null2String(t1.getValue("FAX_NUMBER").toString());
				String kem=t1.getValue("AKONT").toString();
				//删除此供应商编号的，因为名字可能更换
				//查询此工号的ID，然后此ID的field5是否存在或者是否为空
				if(!"".equals(sapcode)){
					RecordSet rs1=new RecordSet();
					boolean flag1=rs1.execute("select t1.id as tt from cus_fielddata t1,hrmresource t2"
							+" where t1.id=t2.id and t1.scopeid='-1' and t2.workcode='"+sapcode+"'");
					RecordSet rs2=new RecordSet();
					RecordSet rs3=new RecordSet();
					if(flag1 && rs1.getColCounts()>0){
						while(rs1.next()){
							//修改
							boolean flag2=rs2.execute("update cus_fielddata set field5='"+suppliercode+"'"
									+" where scopeid='-1' and id='"+rs1.getString("tt")+"'");
							if(flag2){
								rs2.writeLog("修改SAP编码成功：update cus_fielddata set field5='"+suppliercode+"'"
										+" where scopeid='-1' and id='"+rs1.getString("tt")+"'");
							}else{
								rs2.writeLog("修改SAP编码失败：update cus_fielddata set field5='"+suppliercode+"'"
										+" where scopeid='-1' and id='"+rs1.getString("tt")+"'");
							}
						}
					}else{
						//插入
						rs1=new RecordSet();
						flag1=rs1.execute("select t1.id as tt from hrmresource t1 where t1.workcode='"+sapcode+"'");
						if(flag1){
							while(rs1.next()){
								boolean flag3=rs3.execute("insert into cus_fielddata(scope,scopeid,id,field5)"
										+" values('HrmCustomFieldByInfoType','-1','"+rs1.getString("tt")+"','"+suppliercode+"')");
								if(flag3){
									rs3.writeLog("插入SAP编码成功：insert into cus_fielddata(scope,scopeid,id,field5)"
											+" values('HrmCustomFieldByInfoType','-1','"+rs1.getString("tt")+"','"+suppliercode+"')");
								}else{
									rs3.writeLog("插入SAP编码失败：insert into cus_fielddata(scope,scopeid,id,field5)"
											+" values('HrmCustomFieldByInfoType','-1','"+rs1.getString("tt")+"','"+suppliercode+"')");
								}
							}
						}
					}//插入
				}
				
				RecordSet rsOfDelete=new RecordSet();
				String sqlOfDelete="delete from WV_SUPPLIERS where suppliercode='"+suppliercode+"'";
				boolean flagOfDelete=rsOfDelete.execute(sqlOfDelete);
				rs.writeLog("flagOfDelete："+flagOfDelete);
				if(flagOfDelete){
					rs.writeLog("删除供应商成功..."+sqlOfDelete+"。");
				}else{
					rs.writeLog("删除供应商失败..."+sqlOfDelete+"。");
				}
				RecordSet rsOfInsert=new RecordSet();
				String sqlOfInsert="insert into WV_SUPPLIERS(suppliercode,suppliername,kem) values('"+suppliercode+"','"+suppliername+"','"+kem+"')";
				boolean flagOfInsert=rsOfInsert.execute(sqlOfInsert);
				if(flagOfInsert){
					rs.writeLog("插入供应商成功..."+sqlOfInsert+"。");
				}else{
					rs.writeLog("插入供应商失败..."+sqlOfInsert+"。");
				}
			}
		} catch (Exception e) {
			rs.writeLog("调用供应商查询接口异常..."+e);
		}
	}
	
}
