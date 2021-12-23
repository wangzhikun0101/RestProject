package com.javaplatform.sap;
import  java.io.File;
import  java.io.FileOutputStream;
import java.util.Properties;
import org.slf4j.Logger;
import  com.sap.conn.jco.JCoDestination;
import  com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import  com.sap.conn.jco.ext.DestinationDataProvider;
import com.javaplatform.cfile.*;

public class sapdb {
    /**
     * 配置账号密码
     */
    private  static  final String ABAP_AS_POOLED = "ABAP_PUBLIC_130_SAPJCO_POOL";
    static {
        FileClass fc =new FileClass();
        Properties properties1 = fc.readPropertiesFile("application.properties");
        String strActiveFile = properties1.getProperty("spring.profiles.active");
        Properties properties = fc.readPropertiesFile(strActiveFile+".properties");
        Properties conProperty =new Properties();
        conProperty.setProperty(DestinationDataProvider.JCO_ASHOST,properties.getProperty("sap_ip"));
        conProperty.setProperty(DestinationDataProvider.JCO_SYSNR,properties.getProperty("sap_sysnr"));//系统编号 sap_sysnr
        conProperty.setProperty(DestinationDataProvider.JCO_CLIENT,properties.getProperty("sap_client"));//SAP 集团编号 sap_client
        conProperty.setProperty(DestinationDataProvider.JCO_USER,properties.getProperty("sap_user"));//SAP 账号 sap_user
        conProperty.setProperty(DestinationDataProvider.JCO_PASSWD,properties.getProperty("sap_pwd"));//密码 sap_pwd
        conProperty.setProperty(DestinationDataProvider.JCO_LANG,properties.getProperty("sap_lang"));//系统语言 sap_lang
        createDataFile(ABAP_AS_POOLED,"jcoDestination",conProperty);
    }

    /**
     * 创建SAP接口属性文件
     * @param name
     * @param suffix
     * @param properties
     */
    private  static  void createDataFile(String name,String suffix,Properties properties) {
        File cfg = new File(name + "." + suffix);
        if (cfg.exists()) {
            cfg.deleteOnExit();
        }
        try {
            FileOutputStream fos = new FileOutputStream(cfg, false);
            properties.store(fos, "fto sap");
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("SAP 未能连接，请找开发！", e);
        }
    }

    /**
     * 获取SAP 数据连接对象
     * @return
     * @throws RuntimeException
     */
    public  static JCoDestination SAPCon() throws RuntimeException {
        JCoDestination destination = null;
        try {
            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        } catch (JCoException e) {
            e.printStackTrace();
            //throw new Exception("SAP 未能连接，请找开发！"+ e.toString());
        }
        return destination;
    }


}
