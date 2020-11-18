package com.lintao.demos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class AutoProgramming {

    public static FileWriter fileWriter;//创建文本文件

    static {
        try {
            fileWriter = new FileWriter("C:\\Users\\Administrator\\Desktop\\out.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {

        String[] arr = {"Delay 203","Delay 46","Delay 62","Delay 485","Delay 31","Delay 16","Delay 394","Delay 25"};

        String[] content = readTxt("C:\\Users\\Administrator\\Desktop\\in.txt");

        for(int i=0;i<content.length;i++) {

            String verify = content[i];

            List<String> list = Stream.iterate(0, n -> ++n).limit(verify.length()).map(n -> "" + verify.charAt(n)).collect(Collectors.toList());

            int index = 0;
            for (String str : list) {
                if (index == arr.length) {
                    index = 0;
                    shuffle(arr);
                }
                //writeIn("SayString \"" + str + "\"\r\n" + arr[index]);
                writeIn("SayString \"" + str + "\"");
                index++;
            }
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("写入完成");

    }

    /*
    * 打乱数组
    * */
    private static Random rand = new Random();
    public static <T> void swap(T[] a, int i, int j){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public static <T> void shuffle(T[] arr) {
        int length = arr.length;
        for ( int i = length; i > 0; i-- ){
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }

    /*
    * 按行读取txt
    * */
    public static String[] readTxt(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(name);
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
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            array[i] = s;
        }
        // 返回数组
        return array;
    }

    /*
    * 写入txt文件
    * */
public static void writeIn(String content) throws IOException {
        fileWriter.write(content+"\r\n");
        fileWriter.flush();
}

}
