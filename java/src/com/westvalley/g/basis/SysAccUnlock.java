package com.westvalley.g.basis;


import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.westvalley.g.sap.SAPCall;
import weaver.conn.RecordSet;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;

import javax.swing.text.Document;


/*
 *copy right (C) 2020-2025,惠昌电子
 *ClasName :SysAccUnlock
 *Author:shartten(王)
 *E-mail:
 *Date:2020-12-10 19:15
 *Description:
 */
public class SysAccUnlock extends BaseAction {
    /*主函数执行 传递参数*/
    @Override
    public String execute(RequestInfo  requestInfo)
    {
        String flag="1";
        // 获取流程主表信息
        Property[] properties = requestInfo.getMainTableInfo().getProperty();

        String s_account="",s_firstname = "",s_jssm = "",stype = "";//1.SAP模块,配置传输号,集团
        String s_return = "",s_msg = "";//返回标志，返回信息
        StringBuilder msg=new StringBuilder();
        String strjs= "",strczmm = "";
        char s_unlock=  'a',s_reset = 'a';
        for (Property p : properties) {
            if ("sapzh".equals(p.getName())) {//1.账户
                s_account=p.getValue();
            }
            if (p.getName().equals("js")) { //解锁/重置
                strjs= p.getValue();
            }
            if (p.getName().equals("jssm")) { //解锁描述
                s_jssm= p.getValue();
            }
            if (p.getName().equals("zzmm")) { //重置密码
                strczmm= p.getValue();
            }
            if(p.getName().equals("xtbs")){ //姓名
                s_firstname = p.getValue();
            }
        }

        if (strjs.equals("0")) {
            s_unlock = 'X';
        }
        if (strczmm.equals("0")) {
            s_reset = 'X';
        }


    //调用sap 接口并初始化 相关传入参数

        RecordSet rs=new RecordSet();

        rs.writeLog(" 获取操作标识 选中为1 不选为空 ： js："+ strjs +"czmm："+strczmm);
        SAPCall call=null;
        JCoFunction function=null;

        try {
            call = new SAPCall();
            function = call.getFunction("ZHCBASIS001");//创建函数
            //初始化 导入导出集合参数
            JCoParameterList importParameterList = function.getImportParameterList();
            JCoParameterList exportParameterList = function.getExportParameterList();
            importParameterList.setValue("ACCOUNT",s_account); //账号
            importParameterList.setValue("UNLOCK",s_unlock); //解锁
            importParameterList.setValue("RESETPWD",s_reset);//重置
           // importParameterList.setValue("FIRSTNAME",s_firstname);//重置
            call.excute(function);

            //s_msg = exportParameterList.getValue("PASSWORD").toString();

            s_return  = exportParameterList.getValue("RTNCODE").toString().equals("1")?"操作成功":"操作失败";
            //s_msg = exportParameterList.getValue("RTNMSG").toString();
            s_msg = exportParameterList.getValue("RTNMSG").toString();
            if(s_reset == 'X') {
                s_msg =  s_msg + " 重置后的密码是："+exportParameterList.getValue("PASSWORD").toString();
            }
            if (exportParameterList.getValue("RTNCODE").toString().equals("0"))
            {
                requestInfo.getRequestManager().setMessageid("ZHCBASIS001");
                requestInfo.getRequestManager().setMessagecontent(s_msg);
               return "0";
            }
            this.updateForm(requestInfo,s_account, s_jssm,s_msg);//回写sap返回值，返回描述，集团，系统标识


        }catch (Exception e) {
            flag = "0";//
            rs.writeLog("解锁SAP账号异常1!"+ e.toString());
        }
        return flag;
    }
    private boolean updateForm(RequestInfo rq,String s_zh,String s_jssm,String s_jsjg) {
        int formid = rq.getRequestManager().getFormid();
        formid = Math.abs(formid);
        String requestid = rq.getRequestid();
        RecordSet rs = new RecordSet();
        String sql = "update formtable_main_"+formid+" set sapzh='"+s_zh+"',jssm='"+s_jssm+"',jsjg='"+s_jsjg+"' where requestid='"+requestid+"'";
        return rs.execute(sql);
    }
}