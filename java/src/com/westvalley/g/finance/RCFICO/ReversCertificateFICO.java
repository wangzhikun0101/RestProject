package com.westvalley.g.finance.RCFICO;


import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.westvalley.g.sap.SAPCall;
import weaver.conn.RecordSet;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.action.BaseAction;


/*
 *copy right (C) 2019-2020,惠昌电子
 *ClasName :SysTranRequest
 *Author:shartten(王)
 *E-mail:
 *Date:2020-09-10 19:15
 *Description:
 */
public class ReversCertificateFICO extends BaseAction {
    /*主函数执行 传递参数*/
    @Override
    public String execute(RequestInfo  requestInfo)
    {
        String flag="1";
        // 获取流程主表信息
        Property[] properties = requestInfo.getMainTableInfo().getProperty();

        String s_RC="",s_Year= "",s_jt = "";//1.SAP凭证,年份,集团
        String s_return = "",s_msg = "";//返回标志，返回信息
        StringBuilder msg=new StringBuilder();

        for (Property p : properties) {
            if ("mk".equals(p.getName())) {//1.凭证
                s_RC=p.getValue();
            }
            if (p.getName().equals("jt")) { //获取集团
                s_jt = p.getValue();
            }
            if(p.getName().equals("pzcsh")){ //年份
                s_Year = p.getValue();
            }

        }

    //调用sap 接口并初始化 相关传入参数

        RecordSet rs=new RecordSet();
        SAPCall call=null;
        JCoFunction function=null;

        try {
            call = new SAPCall();
            function = call.getFunction("ZHCMIGO001");//创建函数
            //初始化 导入导出集合参数
            JCoParameterList importParameterList = function.getImportParameterList();
            JCoParameterList exportParameterList = function.getExportParameterList();
            importParameterList.setValue("MBLNR",s_RC); //凭证号
            importParameterList.setValue("CLIENT",s_jt); //集团
            importParameterList.setValue("MJAHR",s_Year);//年份
            call.excute(function);
            s_return = exportParameterList.getValue("RTNCODE").toString().equals("1")?"传输成功":"传输失败";
            s_msg = exportParameterList.getValue("RTNMSG").toString();
            if (exportParameterList.getValue("RTNCODE").toString().equals("0"))
            {
                requestInfo.getRequestManager().setMessageid("SysTranRequest");
                requestInfo.getRequestManager().setMessagecontent(s_msg);
                return "0";
            }
            this.updateForm(requestInfo,s_return, s_msg,s_jt);//回写sap返回值，返回描述，集团，系统标识

        }catch (Exception e) {
            flag = "0";//
            rs.writeLog("物料凭证冲销异常1!"+ e.toString());
        }
        return flag;
    }
    private boolean updateForm(RequestInfo rq,String s_cljg,String s_clxx,String s_jt) {
        int formid = rq.getRequestManager().getFormid();
        formid = Math.abs(formid);
        String requestid = rq.getRequestid();
        RecordSet rs = new RecordSet();
        String sql = "update formtable_main_"+formid+" set cljg='"+s_cljg+"',clxx='"+s_clxx+"' where requestid='"+requestid+"'";
        return rs.execute(sql);
    }
}