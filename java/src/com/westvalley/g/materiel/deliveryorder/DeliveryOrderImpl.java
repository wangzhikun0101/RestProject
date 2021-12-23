package com.westvalley.g.materiel.deliveryorder;

import com.weaver.general.Util;
import com.westvalley.g.materiel.GUtil;

import weaver.conn.RecordSet;

/**
 * 
 * 修改明细实现类
 *
 */
public class DeliveryOrderImpl implements IDeliveryOrder {

	@Override
	public ReturnBean updateScheduleById(AfferentBean afferentBean) {
		RecordSet rs=new RecordSet();
		String id=Util.null2String(afferentBean.getId());
		String wulbh=Util.null2String(afferentBean.getWulbh());//物料编号
		String wulms=Util.null2String(afferentBean.getWulms());//物料描述
		String danw=Util.null2String(afferentBean.getDanw());//单位
		String xuqsl=Util.null2String(afferentBean.getXuqsl());//需求数量
		String canggy=Util.null2String(afferentBean.getCanggy());//仓管员
		String shiy=Util.null2String(afferentBean.getShiy());//事由
		String shijlysl=Util.null2String(afferentBean.getShijlysl());//实际领用数量
		String lingyrq=Util.null2String(afferentBean.getLingyrq());//领用日期
		GUtil gUtil=new GUtil();
		canggy=gUtil.getHrmId(canggy);//转为工号
		String sql="update formtable_main_24_dt1"
					+" set"
					+" wpbh='"+wulbh+"',"
					+" wlms='"+wulms+"',"
					+" dw='"+danw+"',"
					+" xqsl='"+xuqsl+"',"
					+" cgy='"+canggy+"',"
					+" sy='"+shiy+"',"
					+" sjlysl='"+shijlysl+"',"
					+" lyrq='"+lingyrq+"'"
					+" where id='"+id+"'";
		boolean flag=rs.execute(sql);
		ReturnBean rb=new ReturnBean();
		if(flag){
			rs.execute("更新成功，"+sql);
			rb.setZhuangtm("S");
			rb.setXinx("更新成本中心发料单成功。");
		}else{
			rs.execute("更新失败，"+sql);
			rb.setZhuangtm("E");
			rb.setXinx("更新成本中心发料单失败。");
		}
		if("".equals(id)){
			rb.setZhuangtm("E");
			rb.setXinx("请输入明细ID");
		}
		return rb;
	}
	
}
