package com.westvalley.g.timer;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.westvalley.g.sap.SAPCall;

import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;
import weaver.interfaces.schedule.CronJob;

/**
 * 抓取物料数据
 */
public class MaterialsSyn extends BaseCronJob implements CronJob {

	/**
	 * 定时器同步
	 */
	public void execute() {
		RecordSet rs=new RecordSet();
		try {
			SAPCall call = new SAPCall();	
			JCoFunction function = call.getFunction("ZRFC_GET_MATNR");//创建函数	
		
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
				String wulbh=t1.getValue("MATNR").toString();//物料编号
				String wulms=t1.getValue("MAKTX").toString();//物料描述
				String jildw=t1.getValue("MEINS").toString();//基本计量单位
				String zuixpl=t1.getValue("BSTMI").toString();//最小批量
				String sherz=t1.getValue("BSTRF").toString();//采购订单数量的舍入值
				String jiaohsj=t1.getValue("PLIFZ").toString();//计划交货时间（天）
				String biaozjg=t1.getValue("STPRS").toString();//标准价格
				String yidjg=t1.getValue("VERPR").toString();//移动平均价格/周期单价
				String zhisf=t1.getValue("VPRSV").toString();//价格控制指示符
				String gongyswlbh=t1.getValue("ZZZSBM").toString();//供应商物料编码，新增字段2019-05-14
				String jingz=t1.getValue("NTGEW").toString();//净重，新增字段2019-05-23
				String baogmc=t1.getValue("ZBGMC").toString();//报关名称，新增字段2019-05-23
				
				//删除此供应商编号的，因为名字可能更换
				RecordSet rsOfDelete=new RecordSet();
				String sqlOfDelete="delete from wv_materials where wulbh='"+wulbh+"'";
				boolean flagOfDelete=rsOfDelete.execute(sqlOfDelete);
				//rs.writeLog("flagOfDelete："+flagOfDelete);
				if(flagOfDelete){
					RecordSet rsOfInsert=new RecordSet();
					String sqlOfInsert="insert into wv_materials(wulbh,wulms,jildw,zuixpl,sherz,jiaohsj,biaozjg,yidjg,zhisf,gongyswlbh,jingz,baogmc)"+
							" values('"+wulbh+"','"+wulms+"','"+jildw+"','"+zuixpl+"','"+sherz+"','"+jiaohsj+"','"+biaozjg+"','"+yidjg+"','"+zhisf+"','"+gongyswlbh+"','"+jingz+"','"+baogmc+"')";
					boolean flagOfInsert=rsOfInsert.execute(sqlOfInsert);
					if(flagOfInsert){
						rs.writeLog("插入物料成功..."+sqlOfInsert+"。");
					}else{
						rs.writeLog("插入物料失败..."+sqlOfInsert+"。");
					}
				}
			}
		} catch (Exception e) {
			rs.writeLog("调用物料查询接口异常..."+e);
		}
	}
	
}
