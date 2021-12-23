import com.sun.org.apache.bcel.internal.generic.NEW;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.*;
import org.apache.commons.net.util.Base64;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.text.SimpleDateFormat;
public class WZK {

    public static void main2(String[] args) {
        String str= "20210909";
        String StrY= str.substring(0,4);
        String StrM= str.substring(4,6);
        String StrD= str.substring(6,8);

        String aa=  StrY+"-"+StrM +"-"+ StrD;
    }
    public static void main(String[] args) {

        String back =  getRegist();
       JSONObject object = JSONObject.fromObject(back);

       String secrit = object.getString("secrit");//"bef19204-94e5-42a3-a662-794a5ee4f58b";//
        String spk = object.getString("spk");
        System.out.println("secrit");
        System.out.println(secrit);
        System.out.println("spk");
        System.out.println(spk);
        String bak2 = applytoken(secrit,spk);

       JSONObject objecttoken = JSONObject.fromObject(bak2);
        String token = objecttoken.getString("token");//"10ebcdb9-c0c9-46e7-8b0c-9c6c4a0620e6";//objecttoken.getString("token");
               // "1a5bea08-603e-4939-9633-dd45354a8c37";//
        System.out.println("当前 token:" + token);

        String userid = encrypt("1334","utf-8",spk);
               // "b8M/zIR5A/P62pSni1353vhFBQ3eG1/NfX1sdTKl0C1jRPLtz8V77V5XWldizdLDOuSqRODQCe7b/Ov159anGqijbOTvtNteQF/UCdtpgJD4gF721vlNsphYjreJiCxHX5Xx4H8RZfO6G1A5N6NwFCH+6WFU+E1Y3erlI9QHCpUR92B1SPkGAcb1UAAlL5diUxSqgfVrWU5f5iC24LK00+Zb2aFy3TiFdUA2teVCSeS7QNZroYTqz7s8pH/7FQ1pe5sr1EGDfxT6ZP7S68dXqXt9CWKP4CMaw0jffi3hgC5H38t0VVrnlH5Ig6M5MXJ/dkG9Jcoagd/oNda2MwgcJA==";
             //RSATest.encrypt("1334","utf-8",spk);
        String url = "http://172.31.11.50/api/workflow/paService/getCreateWorkflowList";
        String urldo = "http://172.31.11.50/api/workflow/paService/doCreateRequest";
        String urldo2 = "http://172.31.11.50/services/WorkflowService?wsdl";
        System.out.println("token");
        System.out.println(token);
        System.out.println("userid");
        System.out.println(userid);

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(6000, TimeUnit.SECONDS)
                .readTimeout(6000, TimeUnit.SECONDS)
                .build();
       String SapCon = "xm:wzk::xb:nan::nl:222";
        JSONArray jsn = JSONArray.fromObject(SmainData(SapCon));
        StringBuilder SB1 =  new StringBuilder();
//        SB1.append("[{").append("\"xm\":\"shartten\"").append(",")
//                .append("\"xb\":\"man\"").append(",")
//                .append("\"nl\":\"207\"")
//                .append("}]");
        RequestBody body = new FormBody.Builder() .add("mainData",jsn.toString())
                .add("requestName","test")
                .add("workflowId","194").build();
//        Request request = new Request.Builder()
//                .url(url)
//                .addHeader("appid","dgwisechamp")
//                .addHeader("token",token)
//                .addHeader("userid",userid)
//                .post(body)
//                .build();

        Request request1 = new Request.Builder()
                .url(urldo)
                .addHeader("appid","dgwisechamp")
                .addHeader("token",token)
                .addHeader("userid",userid)
               // .addHeader("skipsession", "0")//heads.put("skipsession", "1");

                .post(body)
                .build();
        try{
           // Response response = client.newCall(request).execute();
            Response response1 = client.newCall(request1).execute();
           // String respStr =  response.body().string();
            String respStr1 =  response1.body().string();
           // System.out.println("respStr:" +respStr);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            System.out.println("respStr1:" +respStr1);

        }catch ( Exception e){
            e.getStackTrace();
        }

    }


    public  static String encrypt(String value,  String charset, String rsaPublicFile) {
        if (value != null && !"".equals(value)) {
            String result = "";

            try {
                byte[] msgBytes = value.getBytes(charset);
                PublicKey publicKey =  getPublicKey(rsaPublicFile);
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

    public static PublicKey getPublicKey(String key) {

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
    public static String getRegist() {
        String cpk = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDdNolLg4hEkCc/6S3typLWsEImLOwKrqQ/d+r9PxJqA+PzLDEEgMTwHBgMuuLbcSbAJlsKlg3UJUUDMbzwluifpKaL9IFawbbHK5PHwCs1L5m3WHptGtjnqeTDUuiCaGib1Coj9tje/X+6ZOHmRf+bxXQnn0mLyqWfUf5UFFN0mGE7bUY45mkXjqi9cx/kUCmVG2hMfYEoVPZF+Z3xuAwctAok9a00/QiU9om00d71//mVsVgBV9QlkWgIrbznAk7QcyDXgVB9R14vBsDZP48nGy0DZmseL4FThwoFbqOQLLNltVTzOyPy9dXaIXJXqGaSy0jF9EE2Q7JB4pZV7Rl4cGmI8J5F1qi/QunjM3FNbzEO/ZjI3wuq6IXCDwVjGL8DqAanJsTgWUeR7O6ej6HXugrJWpzq6v0CbzjwcM2bCdZ3CPNZLIxz3srzTGu82bOYi2kiYZ/IvEuKepViTsIGNdtdsMm2N3dXRvIGhrLNxLY7/TD/3zNnwnCynuegeEk= shartten@163.com";

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(6000, TimeUnit.SECONDS)
                .readTimeout(6000, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url("http://172.31.11.50/api/ec/dev/auth/regist")
                .addHeader("appid","dgwisechamp")
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
    public static String  applytoken(String secrit,String spk)  {

        String secret_2 = encrypt(secrit,"utf-8",spk);

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(6000, TimeUnit.SECONDS)
                .readTimeout(6000, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url("http://172.31.11.50/api/ec/dev/auth/applytoken")
                .addHeader("appid","dgwisechamp")
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
    public static List SmainData(String strCon)
    {
        List list = new ArrayList();

        String[] parts = strCon.split("::?");

        for (int i = 0; i < parts.length; i += 2) {
            Map map = new HashMap();
            map.put("fieldName", parts[i].toString());
            map.put("fieldValue", parts[i + 1].toString());
            list.add(map);
        }
//        Map fieldmap =  new HashMap();
//        fieldmap.put("fieldName","xm");
//        fieldmap.put("fieldValue","shartten");
//        Map fieldmap1 =  new HashMap();
//        fieldmap1.put("fieldName","xb");
//        fieldmap1.put("fieldValue","男");
//        Map fieldmap2 =  new HashMap();
//        fieldmap2.put("fieldName","nl");
//        fieldmap2.put("fieldValue","208");
//        list.add(fieldmap);
//        list.add(fieldmap1);
//        list.add(fieldmap2);
        return list;
    }
}
