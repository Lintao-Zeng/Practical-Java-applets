package com.lintao.textreadline;

import java.io.*;

public class AudioMix {

 public static void main(String args[]) throws Exception {

     File file = null;
     FileOutputStream out = null;
     File file2 = null;
     FileInputStream inpu = null;
     char alph;
     String Lalph = "";

     try {
         FileReader fr = new FileReader("C:\\Users\\25343\\Desktop\\Model_11\\word.txt");
         BufferedReader bf = new BufferedReader(fr);
         String str;
         // 按行读取字符串
         while ((str = bf.readLine()) != null) {

             file = new File("C:\\Users\\25343\\Desktop\\Mix\\" + str + ".mp3");
             out = new FileOutputStream(file);

             for (int i=0;i<str.length();i++){
                 alph = str.charAt(i);

                 if(Character.isLowerCase(alph) || Character.isUpperCase(alph)) {//判断是否为字母
                     if(Character.isUpperCase(alph)) {//判断是否是大写字母
                         Lalph = String.valueOf(alph).toLowerCase();//将大写字母转为小写字母
                     }else {
                         Lalph = String.valueOf(alph);
                     }
                         file2 = new File("C:\\Users\\25343\\Downloads\\Music\\" + Lalph + ".mp3");
                         inpu = new FileInputStream(file2);
                         mix(inpu, out);
                 }else{
                    System.out.println("没有此字符：" + alph);
                 }
             }
         }
         bf.close();
         fr.close();
     } catch (IOException e) {
         e.printStackTrace();
     }

     System.out.println("合并完成");

 }

  


 public static void mix(FileInputStream inpu, FileOutputStream out) throws Exception {

     byte b[] = new byte[1024];
     int len = 0;

     //将inpu的内容追加到out
     while ((len = inpu.read(b))!= -1)
     {
         for(int i = 0;i < len;i++)
         {
             out.write(b[i]);
         }
     }
     inpu.close();
 }

}