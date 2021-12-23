package com.javaplatform.fn;

import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONObject;
import java.awt.*;
import java.io.File;
import java.util.Properties;
import com.javaplatform.sap.sapfn01;
import com.javaplatform.cfile.*;
import com.javaplatform.db.*;
@RestController
@RequestMapping(value = "/hc",headers = "Content-type=application/json;charset=UTF-8")
public class fnm01 {
    @GetMapping(value = "/it")
    public String  GetMssqlC()
    {
        DbconM dbm=new DbconM();
        FileClass fc= new FileClass();
        String str = "";
        Properties properties1 = fc.readPropertiesFile("application.properties");
        String cfgActive = properties1.getProperty("spring.profiles.active");
        Properties properties = fc.readPropertiesFile(cfgActive+".properties");

        dbm.sql_ip=properties.getProperty("sql_serverip");
        dbm.sql_condion="  id like '193'";
        dbm.sql_id=properties.getProperty("sql_id");
        dbm.sql_pwd=properties.getProperty("sql_pwd");
        dbm.sql_dbname = properties.getProperty("sql_dbname");
        dbm.sql_colname=properties.getProperty("sql_colname");
        dbm.sql_select=properties.getProperty("sql_select");
        Dbcon dbc=new Dbcon();
        str= dbc.getReaderFlow(dbm);
        //System.out.println("hello");
        return str;
    }
    @PostMapping(value ="/dd")
    @ResponseBody
    public String GetString(@RequestBody String body){
        String  sa = "{\"code\":\"SUCCESS\",\"data\":{\"requestid\":229104}}";
        JSONObject object = JSONObject.fromObject(sa);
        System.out.println(body);
        JSONObject object1 = JSONObject.fromObject(object.get("data"));
        return object.get("code")+"&&" +object1.get("requestid") ;
    }

    @GetMapping(value ="/dd1")
    public String GetString1(){
        return "20211222";
    }
    @GetMapping(value = "/sap01/{strAUFNR}")// /{strAUFNR}/{pageSize}
    public  String GeSAPFN(@PathVariable String strAUFNR){
        String strR = "";
        sapfn01 sapf1=new sapfn01();
        strR = sapf1.Fn_R1(strAUFNR);

        return  strR;
    }
}
