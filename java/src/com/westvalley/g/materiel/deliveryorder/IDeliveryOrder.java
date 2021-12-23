package com.westvalley.g.materiel.deliveryorder;


public interface IDeliveryOrder {
	
	/**
	 * 更新明细信息
	 * @param afferentBean
	 * @return
	 */
	public ReturnBean updateScheduleById(AfferentBean afferentBean);
	
}
