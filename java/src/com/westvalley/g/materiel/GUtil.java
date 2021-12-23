package com.westvalley.g.materiel;

import weaver.conn.RecordSet;

/**
 * 
 * 工具类
 *
 */
public class GUtil {
	/**
	 * 根据物料ID获取物料类型
	 * @param id
	 * @return
	 */
	public String huoQWuLiaoLeiXing(String id){
		RecordSet rs=new RecordSet();
		String sql="select wllx from uf_wllx where id='"+id+"'";
		boolean flag=rs.execute(sql);
		String wullx="";
		if(flag){
			while(rs.next()){
				wullx=rs.getString("wllx");
			}
		}
		return wullx;
	}
	
	/**
	 * 根据物料类型获取ID
	 * @param wullx
	 * @return
	 */
	public String getId(String wullx){
		RecordSet rs=new RecordSet();
		String sql="select id from uf_wllx where wllx='"+wullx+"'";
		boolean flag=rs.execute(sql);
		String id="";
		if(flag){
			while(rs.next()){
				id=rs.getString("id");
			}
		}
		return id;
	}
	
	/**
	 * 根据ID查询工号
	 * @param id 人员ID
	 * @return
	 */
	public String getWorkcode(String id){
		RecordSet rs=new RecordSet();
		String sql="select workcode from hrmresource where id='"+id+"'";
		boolean flag=rs.execute(sql);
		String workcode="";
		if(flag){
			while(rs.next()){
				workcode=rs.getString("workcode");
			}
		}
		return workcode;
	}
	
	/**
	 * 根据工号查询ID
	 * @param workcode 工号
	 * @return
	 */
	public String getHrmId(String workcode){
		RecordSet rs=new RecordSet();
		String sql="select id from hrmresource where workcode='"+workcode+"'";
		boolean flag=rs.execute(sql);
		String id="";
		if(flag){
			while(rs.next()){
				id=rs.getString("id");
			}
		}
		return id;
	}
}
