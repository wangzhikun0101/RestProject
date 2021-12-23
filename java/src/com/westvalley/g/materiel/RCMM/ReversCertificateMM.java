package com.westvalley.g.materiel.RCMM;


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
public class ReversCertificateMM extends BaseAction {
    /*主函数执行 传递参数*/
    @Override
    public String execute(RequestInfo  requestInfo)
    {
        String flag="1";
        // 获取流程主表信息
        Property[] properties = requestInfo.getMainTableInfo().getProperty();

        String s_RC="",s_Year= "",s_jt = "", s_date ="",s_user= "",s_remark = "";//1.SAP凭证,年份,集团,日期，申请人，备注
        String s_return = "",s_msg = "";//返回标志，返回信息
        StringBuilder msg=new StringBuilder();

        for (Property p : properties) {
            if ("pzbh".equals(p.getName())) {//1.凭证
                s_RC=p.getValue();
            }
            if (p.getName().equals("jt")) { //获取集团
                s_jt = p.getValue();
            }
            if(p.getName().equals("pzcsnf")){ //年份
                s_Year = p.getValue();
            }
            if(p.getName().equals("gzrq")){ //日期
                s_date = p.getValue().replace("-", "");
            }
            if(p.getName().equals("cxsqr")){ //申请人
                s_user = p.getValue();
            }
            if(p.getName().equals("cxyy")){ //备注
                s_remark = p.getValue();
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
            importParameterList.setValue("BUDAT",s_date);//日期
            call.excute(function);
            s_return = exportParameterList.getValue("RTNCODE").toString().equals("1")?"传输成功":"传输失败";
            s_msg = exportParameterList.getValue("RTNMSG").toString();
            if (exportParameterList.getValue("RTNCODE").toString().equals("1"))
            {
                requestInfo.getRequestManager().setMessageid("SysTranRequest");
                requestInfo.getRequestManager().setMessagecontent(s_msg);
                this.updateForm(requestInfo,s_jt,s_remark,s_user,s_Year,s_RC,s_date,s_msg);
                return "1";
            }
            else
            {
                requestInfo.getRequestManager().setMessagecontent(s_msg);
                this.updateForm(requestInfo,s_jt,s_remark,s_user,s_Year,s_RC,s_date,s_msg);
                return "0";
            }

//s_return, s_msg,

            //回写sap返回值，返回描述，集团，系统标识
        }catch (Exception e) {
            flag = "0";//
            rs.writeLog("物料凭证冲销异常1!"+ e.toString());
        }
        return flag;
    }
    private boolean updateForm(RequestInfo rq,String s_jt,String cxyn,String cxsqr,String pzcsnf,
                               String pzbh,String gzrq,String czjg) {
        int formid = rq.getRequestManager().getFormid();
        formid = Math.abs(formid);
        String requestid = rq.getRequestid();
        RecordSet rs = new RecordSet();
        String sql = "update formtable_main_"+formid+" set cxyy='"+cxyn +"',gzrq='"+ gzrq+"'," +
                "cxsqr='"+ cxsqr+"',pzcsnf='"+ pzcsnf+"',pzbh='"+ pzbh+"'" +
                ",cxjg = '" +czjg+
                "' where requestid='"+requestid+"'";
        return rs.execute(sql);
    }
}