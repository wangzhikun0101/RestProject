package com.javaplatform.sap;

import  com.javaplatform.sap.sapdb;
import com.sap.conn.jco.*;

/**
 * 对接生产投料查询接口
 *
 */
public class sapfn01 {
    public String Fn_R1(String strAufnr)
    {
        String strReturn = "";
JCoFunction jcofn=null;
//连接sap
        JCoDestination jcodestination= sapdb.SAPCon();
        try {
            //调用函数
//            jcodestination.getf
            jcofn = jcodestination.getRepository().getFunction("ZHCMMBI001");
            //传入参数
            jcofn.getImportParameterList().setValue("AUFNR",strAufnr);
//抓取表内容


            JCoParameterList tableparm = jcofn.getTableParameterList();
            jcofn.execute(jcodestination);
            //抓取表值

            JCoTable jcotable = tableparm.getTable("ZMMBI013CXTAB");
            int icount = jcotable.getNumRows();
            for (int i = 0; i < icount; i++) {
                jcotable.setRow(i);
                //这里获取sap函数传出内表结构的字段 【传入的字符串为调用函数需要传入的参数名，必须为大写】
                String saufnr = " AUFNR: " +jcotable.getString("AUFNR");//【表结果里面的字段】记住这里BANKL一定是大写的，不然得不到值
                String smatnr = " MATNR: " +jcotable.getString("MATNR");//【表结果里面的字段】记住这里BANKA一定是大写的，不然得不到值
                String smaktx = " MAKTX: " +jcotable.getString("MAKTX");

                strReturn = strReturn + saufnr +"#"+ smatnr +"#"+smaktx+"@@" ;
                //得到了sap数据，然后下面就是你java擅长的部分了，想封装成什么类型的都由你
            }
            //设置传出参数

          String strRcode  =  jcofn.getExportParameterList().getValue("RTNCODE").toString();
          String strRmsg = jcofn.getExportParameterList().getValue("RTNMSG").toString();

          strReturn  = strReturn + "返回编号："+strRcode+";泛微内容："+strRmsg;
        }catch (JCoException ex){
            ex.printStackTrace();
        }

        return strReturn;
    }
}
