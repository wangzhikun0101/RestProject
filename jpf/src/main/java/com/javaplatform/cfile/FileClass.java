package com.javaplatform.cfile;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class FileClass {

    /**
     *
     * @param fileName 文件名称
     * @return 返回属性对象
     */
    public Properties readPropertiesFile(String fileName) {
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
            String DirPath = "D:\\wzktest\\target\\" + new SimpleDateFormat("yyyyMM").format(new Date());
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
}
