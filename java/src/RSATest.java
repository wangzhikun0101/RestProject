//package com.customization.test;

//import net.sf.json.JSONObject;

//import okhttp3.*;
//import com.alibaba.fastjson.JSONObject;
import  net.sf.json.JSONObject;
import  okhttp3.*;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.net.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class RSATest {


    public static void main1(String[] args) {

        String back =  getRegist();
//        Map<String, Object> result = new JSONObject().;
//        System.out.println(result.toString());
        JSONObject object = JSONObject.fromObject(back);
        String secrit = object.getString("secrit");
        String spk = object.getString("spk");
        String bak2 = applytoken(secrit,spk);

        JSONObject objecttoken = JSONObject.fromObject(bak2);
        String token = objecttoken.getString("token");
        System.out.println("token");
System.out.print(token);

        String userid = RSATest.encrypt("5","utf-8",spk);
        System.out.println("userid");
        System.out.print(userid);
        System.out.println("aaaa");

        String url = "http://119.29.16.192:8082/api/workflow/paService/getCreateWorkflowList";

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("appid","flowapitest")
                .addHeader("token",token)
                .addHeader("userid",userid)
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String respStr =  response.body().string();

            System.out.println("respStr:" +respStr);

        }catch ( Exception e){
            e.getStackTrace();
        }

    }


    public  static String encrypt(String value,  String charset, String rsaPublicFile) {
        if (value != null && !"".equals(value)) {
            String result = "";

            try {
                byte[] msgBytes = value.getBytes(charset);
                PublicKey publicKey =  RSATest.getPublicKey(rsaPublicFile);
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

            byte[] byteKey  = Base64.decodeBase64(key);
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
        String cpk = "AAAAB3NzaC1yc2EAAAADAQABAAABgQDdNolLg4hEkCc/6S3typLWsEImLOwKrqQ/d+r9PxJqA+PzLDEEgMTwHBgMuuLbcSbAJlsKlg3UJUUDMbzwluifpKaL9IFawbbHK5PHwCs1L5m3WHptGtjnqeTDUuiCaGib1Coj9tje/X+6ZOHmRf+bxXQnn0mLyqWfUf5UFFN0mGE7bUY45mkXjqi9cx/kUCmVG2hMfYEoVPZF+Z3xuAwctAok9a00/QiU9om00d71//mVsVgBV9QlkWgIrbznAk7QcyDXgVB9R14vBsDZP48nGy0DZmseL4FThwoFbqOQLLNltVTzOyPy9dXaIXJXqGaSy0jF9EE2Q7JB4pZV7Rl4cGmI8J5F1qi/QunjM3FNbzEO/ZjI3wuq6IXCDwVjGL8DqAanJsTgWUeR7O6ej6HXugrJWpzq6v0CbzjwcM2bCdZ3CPNZLIxz3srzTGu82bOYi2kiYZ/IvEuKepViTsIGNdtdsMm2N3dXRvIGhrLNxLY7/TD/3zNnwnCynuegeEk= shartten@163.com";
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder().build().create(MediaType.parse("application/json; charset=utf-8"),"json");
        Request request = new Request.Builder()
                    .url("http://119.29.16.192:8082/api/ec/dev/auth/regist")
                .addHeader("appid","flowapitest")
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

        String secret_2 = RSATest.encrypt(secrit,"utf-8",spk);

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder()
                .url("http://119.29.16.192:8082/api/ec/dev/auth/applytoken")
                .addHeader("appid","flowapitest")
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

}
