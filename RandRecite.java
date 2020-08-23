package com.lintao.dailycode;

import java.io.*;
import java.util.ArrayList;

public class RandRecite {

    public static String MyFile = "C:\\Users\\25343\\Desktop\\start.txt";

    public static void main(String args[]) throws IOException {

        int num = randnum();
        int[] arr = readtxt(MyFile);
        boolean flag = true;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num) {
                flag = false;
            }
        }

        if (flag == true) {
//            System.out.println(num);
            Runtime.getRuntime().exec("explorer C:\\Users\\25343\\Desktop\\test\\"+num+".txt");
            writeIn(MyFile, num);
        } else {
            main(args);
        }

    }

    public static int[] readtxt(String FileName) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(FileName);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            array[i] = Integer.parseInt(s);
        }
        // 返回数组
        return array;

    }

    public static int randnum() {
        //产生一个随机数
        int max = 4, min = 0;
        int ran1 = (int) (Math.random() * (max - min) + min);
        return ran1;
    }

    public static void writeIn(String FileName, int content) {
        FileWriter fw = null;
        try {
            File f = new File(FileName);
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