package com.westvalley.g.wisecommon;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.westvalley.g.materiel.GUtil;
import com.westvalley.g.sap.SAPCall;
import weaver.conn.RecordSet;
import weaver.soa.workflow.request.Cell;
import weaver.soa.workflow.request.DetailTable;
import weaver.soa.workflow.request.DetailTableInfo;
import weaver.soa.workflow.request.Property;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.Row;
import weaver.workflow.action.BaseAction;
import weaver.workflow.request.RequestManager;

public class MaterialFactoryMove extends BaseAction  {
    /**
     * 操作：
     * 		回写成败相关信息
     */
    @Override
    public String execute(RequestInfo requestInfo) {
        String flag="1";
        // 获取流程主表信息
        Property[] properties = requestInfo.getMainTableInfo().getProperty();

        String str_gc="";//1.调出工厂-主表gc
        String str_drgc="";//1.调入工厂-主表drgc

        String str_dcck="";//1.调出仓库-主表dcck
        String str_drck="";//1.调入仓库-主表drck
        String strid = ""+requestInfo.getRequestManager().getFormid();
        String type="";//返回类型
        String message="";//返回信息

        StringBuilder msg=new StringBuilder();
      if (requestInfo.getRequestManager().getFormid() == -94) //工厂间仓库调拨
      {
          for (Property p : properties) {
              if ("gc".equals(p.getName())) {//1.调出工厂
                  str_gc = p.getValue();
              }
              if ("drgc".equals(p.getName())) {//1.调入工厂
                  str_drgc = p.getValue();
              }
              if ("dcck".equals(p.getName())) {//1.调出仓库
                  str_dcck = p.getValue();
              }
              if ("drck".equals(p.getName())) {//1.调入仓库
                  str_drck = p.getValue();
              }
          }
      }
        else if (requestInfo.getRequestManager().getFormid() == -49) //工厂内仓库调拨
        {
            for (Property p : properties) {
                if ("gc".equals(p.getName())) {//1.调出工厂
                    str_gc = p.getValue();
                    str_drgc = p.getValue();
                }
                if ("dcck".equals(p.getName())) {//1.调出仓库
                    str_dcck = p.getValue();
                }
                if ("drck".equals(p.getName())) {//1.调入仓库
                    str_drck = p.getValue();
                }
            }
        }


        RecordSet rs=new RecordSet();

        //明细
        DetailTableInfo detailTableInfo = requestInfo.getDetailTableInfo();
        //明细表可能有多个
        DetailTable[] detailTables = detailTableInfo.getDetailTable();
        //行对象
        Row[] rows = detailTables[0].getRow();
        int i=0;
        int j=0;
        //判断物料类型

        String saphcprdh="";
        SAPCall call = null;
        JCoFunction function = null;
        try {
            call = new SAPCall();
            function = call.getFunction("ZHCMMBI311");//创建函数
        }catch(Exception e){
            e.printStackTrace();
            rs.writeLog("调用仓库间调拨接口异常，"+e);
        }
        //赋值表头信息
        JCoParameterList importParameterList = function.getImportParameterList();
        JCoParameterList exportParameterList = function.getExportParameterList();

       importParameterList.setValue("MOVE_TYPE", "04"); //移动类型
        importParameterList.setValue("WERKS_F", str_gc); //调出工厂
        importParameterList.setValue("WERKS_T", str_drgc); //调入工厂
        importParameterList.setValue("LGORT_F", str_dcck); //调出仓库
        importParameterList.setValue("LGORT_T", str_drck); //调入仓库
        importParameterList.setValue("FORMID", strid); //调入仓库
        JCoParameterList tableParameterList = function.getTableParameterList();

        JCoTable table = tableParameterList.getTable("ZSMM001");//获取表名
        for (Row r : rows) {
            // 列对象
            Cell[] cells = r.getCell();
            String str_wlsbbh1="";//2.物料编号-明细wlbh
            String str_sl="";//3.本次调拨数量
            String str_ph ="";//4.批次
            for (Cell c : cells) {//读取列
                if ("wlsbbh1".equals(c.getName())) {
                    str_wlsbbh1=c.getValue();
                } else if ("sl".equals(c.getName())) {
                    str_sl=c.getValue();
                } else if ("ph".equals(c.getName())) {
                    str_ph=c.getValue();
                }
            }

            try {
                table.appendRows(1);
                table.setRow(i++);
                //1.订单类型（采购）


                //5.物料编号
                table.setValue("MATNR", str_wlsbbh1.replaceAll("^(0+)", ""));//去零
                //6.数量
                table.setValue("ZSQTY", str_sl);
                //7.基本计量单位
                table.setValue("CHARG", str_ph);
            } catch (Exception e) {
                rs.writeLog("调用仓库调拨接口异常..."+e);
                msg.append("第"+i+"行明细错误，返回信息为：调用采购创建接口异常，"+e+"<br/>");
            }
        }//for

        try {
            call.excute(function);
        } catch (JCoException e) {
            e.printStackTrace();
            rs.writeLog("执行仓库调拨接口失败.."+e);
        }
        //打印传输值
        rs.writeLog(function.toXML());

        //获取返回值

        rs.writeLog("RTNCODE 的值："+exportParameterList.getValue("RTNCODE").toString()+"</br>");
        rs.writeLog("RTNMSG的值："+exportParameterList.getValue("RTNMSG").toString()+"</br>");
        rs.writeLog("E_MBLNR的值："+exportParameterList.getValue("E_MBLNR").toString()+"</br>");
        rs.writeLog("E_MBLNR1的值："+exportParameterList.getValue("E_MBLNR1").toString()+"</br>");
        if(!"1".equals(function.getExportParameterList().getValue("RTNCODE").toString())){
            flag="0";//调拨失败
            msg.append("调拨失败："+exportParameterList.getValue("RTNMSG").toString()+"。<br/>");
        }
        if(!"0".equals(function.getExportParameterList().getValue("RTNCODE").toString())){
            flag="1";//调拨失败
            msg.append(""+exportParameterList.getValue("RTNMSG").toString()+"。<br/>");
        }

        //修改返回信息
      //  if(!"".equals(msg.toString())){
      //      this.updateForm(requestInfo, msg.toString(), saphcprdh);
       // }else{
      //      this.updateForm(requestInfo, "采购申请成功。", saphcprdh);
      //  }

        RequestManager requestManager = requestInfo.getRequestManager();
        if(flag=="0"){
            requestManager.setMessageid("111000");
            msg.insert(0, "很抱歉，提交失败。原因如下：<br/>");
            requestManager.setMessagecontent(msg.toString());
        }
        else if(flag=="1") {
            requestManager.setMessageid("111000");
            msg.insert(0, "提交成功：<br/>");
            requestManager.setMessagecontent(msg.toString());
        }


        return flag;
    }

    /**
     * 更新表单信息
     * @param rq
     * @param fanhxx
     * @return
     */
    private boolean updateForm(RequestInfo rq) {
        //String fanhxx,String saphcprdh
        int formid = rq.getRequestManager().getFormid();
        formid = Math.abs(formid);
        String requestid = rq.getRequestid();
        RecordSet rs = new RecordSet();
        //String sql = "update formtable_main_"+formid+" set sapxxfh='"+fanhxx+"',saphcprdh='"+saphcprdh+"' where requestid='"+requestid+"'";
        return true;//rs.execute(sql);
    }
}
