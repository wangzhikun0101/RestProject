package com.example.oarest.oa;
import com.example.oarest.rest.*;

import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;
import com.example.oarest.db.*;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/api",headers ="Content-type=application/json;charset=UTF-8" )
public class OAtest {
//@GetMapping(value = "/oaflow")
  @PostMapping(value = "/oaflow")
  @ResponseBody
    public String SapCall(@RequestBody String body) {//
        String stra = "",SAPM="",SAPD="";
     //String body = "zsldh:201912020001::werks:2012::name_gc:东莞惠昌电子有限公司内销工厂::mblnr:5000033468::bktxt:191260124::matnr:120203000116::bismt:P01041E7::zxqzk:一般::usnam:MM019::maktx:PCB-94V0-T1.0-L4-(78-9102-3278-0)-C::zshsl:504.000::meins:PCS::lifnr:30015::name_gys:深圳崇达多层线路板有限公司::zzzs:崇达::zzzsbm:N/A::ztype:空白线路板::zsllx1:101$zeile:1::zsllx:101::charg:1912020013::licha:4819::menge:504.000::meins:PCS::lgort:NYDJ::budat:20191202::bz:N/A::zdays:-379";
       //String body ="zsldh:202112100004::werks:2011::matnr:908021600018::zxqzk:一般::maktx:铁壳-C5210(1/2H)-T0.3-Ni1~3um、Sn(2~4S)um::zshsl:62000.000::zmeins:PCS::charg:2009080010::bz:死循环导致::zsllx1:322$";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        RSATest RST = new RSATest();
        try {
           body = body.replace("\n", "");
            Integer Iindex = body.indexOf("$");
             SAPM = body.substring(0, Iindex);
            Integer Iindex1 = 0;
            Iindex1 = Iindex +1;
             if (Iindex1.equals(body.length()) )
             {
             }
             else {
                 SAPD = body.substring(Iindex + 1, body.length());
             }
            if (SAPM.equals("")) {
                if (SAPM.equals("")) {
                    System.out.println("{\"SAPM\":\"传入参数不能为空！\"}");
                }
            }

             stra = RST.Run(SAPM, SAPD);
            JSONObject object = JSONObject.fromObject(stra);
            JSONObject object1 =null;
            if (object.get("data").toString() == "" || object.get("data") == null) {
                stra = object.get("code") + ",流程失败";
            }
            else {
                object1 = JSONObject.fromObject(object.get("data"));
                stra = object.get("code") + "," + object1.get("requestid");
            }

        }catch (Exception e){
            System.out.println("日期"+df.format(new Date()) +"：异常信息:"+e.toString()+"{sapM:"+SAPM+"},sapD:{"+SAPD+"}");
            stra =  "失败，参数异常";
        }
        return stra ;
    }
    @GetMapping(value = "/test")
        public List test(String test1)
        {
            String[] parts1 = test1.split("::?");
            List lt1 =  new ArrayList();
            for (int i = 0; i < parts1.length; i += 2) {
                Map map = new HashMap();
                map.put("fieldName",parts1[i].toString());
                map.put("fieldValue",parts1[i + 1].toString());
                lt1.add(map);
            }
            return lt1;

    }
   @GetMapping (value = "/dd")
    public String SCDATA()
    {
        String str= "20211213";
        String StrY= str.substring(0,4);
        String StrM= str.substring(4,6);
        String StrD= str.substring(6,8);

    return   StrY+"-"+StrM +"-"+ StrD+"\\V2：IP:"+new RSATest().strTestDev();
//        Properties properties = new RSATest().readPropertiesFile("application.properties");
//        String aa = properties.getProperty("FlowID");
//DbconM dbm= new DbconM();
//dbm.sql_dbname = "ecology";
//dbm.sql_condion="id like '193'";
//dbm.sql_colname="workflowname";
//dbm.sql_id="sa";
//dbm.sql_ip="172.31.11.50";
//dbm.sql_pwd="ecology";
//dbm.sql_select="select top 1 "+dbm.sql_colname+" from workflow_base where ";
//Dbcon dbc=new Dbcon();
////
//         str = dbc.getReaderFlow(dbm);
//
//        return str;
    }



}
