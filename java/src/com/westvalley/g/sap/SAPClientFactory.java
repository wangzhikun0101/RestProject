package com.westvalley.g.sap;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import weaver.conn.RecordSet;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAPClientFactory {
	static String ABAP_AS_POOLED = "ABAP_PUBLIC_130_SAPJCO_POOL";
	
	/**
	 * 初始化配置文件
	 */
	private static void createDataFile() {
		//WEAVER/RESIN
		File cfg = new File(ABAP_AS_POOLED + ".jcoDestination");
		if (!cfg.exists()) {
			Properties properties = new Properties();
			properties.setProperty(DestinationDataProvider.JCO_ASHOST, "172.18.1.199");//sap服务器地址
			properties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");// 系统编号
			properties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");// 集团号
			properties.setProperty(DestinationDataProvider.JCO_USER, "OA_RFC");// 帐号
			properties.setProperty(DestinationDataProvider.JCO_PASSWD, "654321");// 密码
			properties.setProperty(DestinationDataProvider.JCO_LANG, "zh");// 语言
			RecordSet rs=new RecordSet();
			try {
				FileOutputStream fos = new FileOutputStream(cfg, false);
				properties.store(fos, "properties for sapconntion !");
				rs.writeLog("1");
				fos.close();
			} catch (Exception e) {
				rs.writeLog("2");
				throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
			}
		}
	}
	
	/**
	 * 获取SAP链接
	 * @return
	 * @throws JCoException
	 */
	public static JCoDestination getJcoConnection() throws JCoException {
		createDataFile();
		return JCoDestinationManager.getDestination(ABAP_AS_POOLED);
	}
}
