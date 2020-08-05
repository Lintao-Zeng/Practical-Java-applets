package com.lintao.dailycode;

import java.io.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class DownloadFileHttpCilent {

    public static void main(String[] args) throws Exception {
        DownloadFileHttpCilent client = new DownloadFileHttpCilent();

        try {
            FileReader fr = new FileReader("C:\\Users\\25343\\Desktop\\Model_11\\mean_single_out.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                client.download("https://fanyi.baidu.com/gettts?lan=zh&text="+str+"&spd=5&source=web","C:\\Users\\25343\\Desktop\\AudioDownloader\\"+str+".mp3");
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void download(String url, String fileName) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            int responseCode = response.getStatusLine().getStatusCode();

            System.out.println("Request Url: " + request.getURI());
            System.out.println("Response Code: " + responseCode);

            if (responseCode!=200) {
                String temp = fileName.replace("C:\\Users\\25343\\Desktop\\AudioDownloader\\","");
                temp = temp.replace(".mp3","");
                failed(temp);
            }

            InputStream is = entity.getContent();

            FileOutputStream fos = new FileOutputStream(new File(fileName));

            int inByte;
            while ((inByte = is.read()) != -1) {
                fos.write(inByte);
            }

            is.close();
            fos.close();
            client.close();

            System.out.println("File Download Completed!!!");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void failed(String content) {
        FileWriter fw = null;
        try {
//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f=new File("C:\\Users\\25343\\Desktop\\Model_11\\failed.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}