package com.mydao.nsop.client.util;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class ConfigFileUtil {

    public void writeTimeFile(){
        OutputStream os =null;
        OutputStreamWriter fos = null;
        BufferedWriter bw = null;
        try {

            //File file = ResourceUtils.getFile(":threshoValue.txt");
            String  path = System.getProperty("user.dir");
            File file = new File(path+"/config/threshoValue.txt");
            if(!file.exists()){
                file.mkdir();
            }
            os = new FileOutputStream(file);
            fos = new OutputStreamWriter(os);
            bw = new BufferedWriter(fos);
            bw.write(DateUtil.dateToShortCode(new Date()));
            bw.flush();
            fos.close();
            bw.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 读文件
     * @return
     */
    public String readTimeFromFile(){
        String time ="";
        InputStream in=null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            //读操作
            String  path = System.getProperty("user.dir");
            File file = new File(path+"/config/threshoValue.txt");
            if(!file.exists()){
                file.mkdir();
            }

            in  = new FileInputStream(file);
            isr = new InputStreamReader(in);
            br = new BufferedReader(isr);
            String line;
            while((line=br.readLine())!=null){
                time = line;
            }
            br.close();
            isr.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(isr!=null){
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return time;
    }
}
