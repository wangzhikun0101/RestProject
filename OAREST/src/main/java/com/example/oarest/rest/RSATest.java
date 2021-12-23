package com.example.oarest.rest;

import com.example.oarest.db.Dbcon;
import com.example.oarest.db.DbconM;
import javafx.application.Application;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.net.util.Base64;
import javax.crypto.Cipher;
import javax.xml.bind.annotation.XmlType;
import java.awt.*;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.io.IOException;
import java.nio.file.*;
import java.io.File;
import java.nio.charset.StandardCharsets;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;
import  java.math.BigDecimal;
public class RSATest {

    //101 来料检验报告单，322 来料复检报告单; 9 产成品，6 固定资产，4 代表 生产设备 非生产设备
    public String strFlowOA =  "",strMatnr = "";
    public String Run(String strContent,String strDetail) {
        DbconM dbm= new DbconM();
        String strFlowID="",strFlowName = "",strCPK="",strAPPID = "",strIP = "",strOAID = "",strDTNAME="";
        Properties properties1 = readPropertiesFile("application.properties");
        String strActiveFile = properties1.getProperty("spring.profiles.default");
        Properties properties = readPropertiesFile(strActiveFile+".properties");

        strCPK = properties.getProperty("CPK");
        strAPPID = properties.getProperty("APPID");
        strIP = properties.getProperty("ADDRIP");
        strOAID =  properties.getProperty("OAID");

//        dbm.sql_ip=properties.getProperty("sql_serverip");
//
//        dbm.sql_condion="id like '"+strFlowID+"'";
//        dbm.sql_id=properties.getProperty("sql_id");
//        dbm.sql_pwd=properties.getProperty("sql_pwd");
//        dbm.sql_dbname = properties.getProperty("sql_dbname");
//        dbm.sql_colname=properties.getProperty("sql_colname");
//        dbm.sql_select=properties.getProperty("sql_select");
//        Dbcon dbc=new Dbcon();
//     //   dbc.getReaderFlow(dbm);

        strFlowName = "来料检验报告单";
        strDTNAME =  properties.getProperty("DTNAME");
        strFlowID = properties.getProperty("FlowID");

        String back =  getRegist(strCPK,strAPPID,strIP);
        JSONObject object = JSONObject.fromObject(back);

        String secrit = object.getString("secrit");
        String spk = object.getString("spk");
        String bak2 = applytoken(secrit,spk,strIP,strAPPID);

        JSONObject objecttoken = JSONObject.fromObject(bak2);
        String token = objecttoken.getString("token");
        String userid = encrypt(strOAID,"utf-8",spk);
        String url = "http://"+strIP+"/api/workflow/paService/doCreateRequest";
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        List ltm = new ArrayList();
        ltm = SmainData(strContent);
        if (strFlowOA.equals("322") || strFlowOA.equals("349"))
        {
            if (strMatnr.equals("9")) //判断材料类型
            {
                strFlowName = "成品复检报告单";
                strFlowID = properties.getProperty("FlowID03");
            }
            else if (strMatnr.equals("4"))
            {

            }
            else {
                strFlowName = "来料复检报告单";
                strDTNAME = properties.getProperty("DTNAME02");
                strFlowID = properties.getProperty("FlowID02");
            }
        }
        JSONArray JsonM = JSONArray.fromObject(ltm);
        List ltd = new ArrayList();
        JSONArray JsonD = null;
        if(strDetail.trim().equals("") || strDetail.equals(null)) //为空的时候不处理
        {

        }
        else {
            ltd = DetailData(strDetail, strDTNAME);
             JsonD = JSONArray.fromObject(ltd);
        }
        RequestBody body = null;
         if (strDetail.trim().equals("") || strDetail.equals(null)) //明细表为空时不传明细参数
         {
             body = new FormBody.Builder().add("mainData", JsonM.toString())
                     .add("requestName", strFlowName)
                     .add("workflowId", strFlowID).build();
         }
         else if (strMatnr.equals("4"))
         {

         }
         else {
             body = new FormBody.Builder().add("mainData", JsonM.toString())
                     .add("detailData", JsonD.toString())
                     .add("requestName", strFlowName)
                     .add("workflowId", strFlowID).build();
         }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("appid",strAPPID)
                .addHeader("token",token)
                .addHeader("userid",userid)
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String respStr =  response.body().string();
            //写入日志
            CreateLog(respStr);
            return respStr;
           // System.out.println("respStr:" +respStr);

        }catch ( Exception e){
           System.out.println("调用异常：shartten==" + e.toString());
           return "调用异常：shartten==" + e.toString();
        }

    }


    protected String encrypt(String value,  String charset, String rsaPublicFile) {
        if (value != null && !"".equals(value)) {
            String result = "";

            try {
                byte[] msgBytes = value.getBytes(charset);
                    PublicKey publicKey = getPublicKey(rsaPublicFile);
                    Cipher cipher =  Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(1, publicKey);

                    byte[] results = cipher.doFinal(msgBytes);

                    return Base64.encodeBase64String(results).replace("\r\n","");

            } catch (Exception var10) {
                var10.printStackTrace();
                return result;
            }
        } else {
            return value;
        }
    }

    protected  PublicKey getPublicKey(String key) {

        try {

            byte[] byteKey  =   Base64.decodeBase64(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 注册
     * @return
     */
    protected String getRegist(String cpk,String appid,String address) {
       // String cpk = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDdNolLg4hEkCc/6S3typLWsEImLOwKrqQ/d+r9PxJqA+PzLDEEgMTwHBgMuuLbcSbAJlsKlg3UJUUDMbzwluifpKaL9IFawbbHK5PHwCs1L5m3WHptGtjnqeTDUuiCaGib1Coj9tje/X+6ZOHmRf+bxXQnn0mLyqWfUf5UFFN0mGE7bUY45mkXjqi9cx/kUCmVG2hMfYEoVPZF+Z3xuAwctAok9a00/QiU9om00d71//mVsVgBV9QlkWgIrbznAk7QcyDXgVB9R14vBsDZP48nGy0DZmseL4FThwoFbqOQLLNltVTzOyPy9dXaIXJXqGaSy0jF9EE2Q7JB4pZV7Rl4cGmI8J5F1qi/QunjM3FNbzEO/ZjI3wuq6IXCDwVjGL8DqAanJsTgWUeR7O6ej6HXugrJWpzq6v0CbzjwcM2bCdZ3CPNZLIxz3srzTGu82bOYi2kiYZ/IvEuKepViTsIGNdtdsMm2N3dXRvIGhrLNxLY7/TD/3zNnwnCynuegeEk= shartten@163.com";

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url("http://"+address+"/api/ec/dev/auth/regist")
                .addHeader("appid",appid)
                .addHeader("cpk",cpk)
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String respStr =  response.body().string();
            return  respStr;

        }catch ( Exception e){
            e.getStackTrace();
        }

        return "";
    }

    /**
     * 获取token
     * @param secrit
     * @param spk
     * @return
     */
    protected String  applytoken(String secrit,String spk,String address,String strappid)  {

        String secret_2 = encrypt(secrit,"utf-8",spk);

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url("http://"+address+"/api/ec/dev/auth/applytoken")
                .addHeader("appid",strappid)
                .addHeader("secret",secret_2)
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String respStr =  response.body().string();
            return  respStr;

        }catch ( Exception e){
            e.getStackTrace();
        }

        return "";
    }
    public  List SmainData(String strCon)
    {
        String[] parts = strCon.split("::?");
        List list = new ArrayList();

    for (int i = 0; i < parts.length; i += 2) {
        Map map = new HashMap();
        map.put("fieldName", parts[i].trim().toUpperCase());
        switch (parts[i].trim().toUpperCase()) {
            case "MATNR":
                strMatnr = parts[i + 1].trim().toString();
                strMatnr = strMatnr.substring(0, 1);
                String strM = parts[i + 1].trim().toString();
                map.put("fieldValue",strM );
                break;
            case "ZSLLX1":
                int ZLX = Integer.parseInt(parts[i + 1].trim());
                map.put("fieldValue", ZLX);
                strFlowOA = parts[i + 1].trim();
                break;
            case "ZEILE":
                map.put("fieldValue", parts[i + 1].trim());
                break;
            case "ZSHSL":
                BigDecimal b11 = new BigDecimal(parts[i + 1].trim());
                map.put("fieldValue", b11);
                break;
            case "MENGE":
                BigDecimal b1 = new BigDecimal(parts[i + 1].trim());
                map.put("fieldValue", b1);
                break;
            case "BUDAT":
                if (parts[i + 1].trim().length() == 8) {
                    String strFdate = parts[i + 1].trim();
                    String StrY = strFdate.substring(0, 4);
                    String StrM = strFdate.substring(4, 6);
                    String StrD = strFdate.substring(6, 8);
                    String strDate = StrY + "-" + StrM + "-" + StrD;
                    map.put("fieldValue", strDate);
                } else {
                    map.put("fieldValue", null);
                }
                break;
            default:
                if (parts[i + 1].equals(".")) {
                    map.put("fieldValue", null);
                } else {
                    map.put("fieldValue", parts[i + 1].trim().toString());
                }
                break;
        }

        list.add(map);
    }

        return list;
    }

    public List DetailData(String strDetail,String strDtname)
    {
        List lt = new ArrayList();
        Map inMap = new HashMap();
        String[] parts = strDetail.split("∵");
        List dtlist = new ArrayList();
        Map dtMap = new HashMap();
        List lttemp = new ArrayList();
        List workflowRequestTableRecords = new ArrayList();
        for (int i = 0; i < parts.length; i += 1) {

            Map workflowRequestTableFieldsMap = new HashMap();


            int Rowid = 0;
            workflowRequestTableFieldsMap.put("recordOrder", Rowid);
            lttemp = null;
            lttemp = SmainData(parts[i]);

            workflowRequestTableFieldsMap.put("workflowRequestTableFields",lttemp);

            workflowRequestTableRecords.add(workflowRequestTableFieldsMap);
            if (i == 0) {
                dtMap.put("tableDBName", strDtname);
            }
            if (i == parts.length-1) {
                dtMap.put("workflowRequestTableRecords", workflowRequestTableRecords);
                dtlist.add(dtMap);
            }
        }

        return dtlist;
    }

    /**
     *
     * @param fileName 文件名称
     * @return 返回属性对象
     */
    public    Properties readPropertiesFile(String fileName) {
        try {
            Resource resource = new ClassPathResource(fileName);
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            return props;
        } catch (Exception e) {
            System.out.println("————读取配置文件：" + fileName + "出现异常，读取失败————");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * OA 创建流程 生成日志
     */
    public void CreateLog(String strLogC)throws IOException
    {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String DirPath = "D:\\wise\\DevlopProject\\target\\" + new SimpleDateFormat("yyyyMM").format(new Date());
            String FilePath = DirPath + "\\" + df.format(new Date()) + ".txt";
            strLogC = dft.format((new Date())) + "写入内容： \n " + strLogC;
            //如果文件夹不存在就创建
            boolean bldir = CheckPathExist(DirPath, true);

            // 从JDK1.7开始提供的方法
            // 使用Files.write创建一个文件并写入
            boolean blf = CheckPathExist(FilePath, false);
            //如果文件不存在
            if (!blf) {
                Files.write(Paths.get(FilePath),
                        strLogC.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            } else {
                // 追加写模式
                Files.write(
                        Paths.get(FilePath),
                        strLogC.getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND);
            }
        }catch (Exception ie)
        {

        }

    }
    /**
     *
     */
    private boolean CheckPathExist(String filePath,boolean bdir) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            //file.mkdirs();
            //file.createNewFile();
            if (bdir) {
                file.mkdirs();
                return  true;
            }
            return false;
        }
        return true;
    }

    public  String strTestDev()
    {
        Properties properties = readPropertiesFile("application.properties");
       String strEFile = properties.getProperty("spring.profiles.default");
        Properties properties1 = readPropertiesFile(strEFile+".properties");
        return properties1.getProperty("ADDRIP");
    }

}
