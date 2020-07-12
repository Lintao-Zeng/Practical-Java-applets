package com.lintao.textreadline;
import java.io.*;
import java.sql.*;

public class BczWordExtract {

    public static void main(String[] args) {

        Connection conn = null;
        try {
            String url = "jdbc:sqlite:C:/Users/25343/Documents/Tencent Files/2534324260/FileRecv/MobileFile/lookup.db";//要提取的数据库
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            System.out.println("数据库连接成功！");
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("数据库连接失败！"+e.getMessage());
        }


        try {
            FileReader fr = new FileReader("C:\\Users\\25343\\Desktop\\topic.txt");//单词的topic_id
            BufferedReader bf = new BufferedReader(fr);
            String str;
            String sql;
            while ((str = bf.readLine()) != null) {
                    sql="SELECT * FROM dict_bcz where topic_id=" + str;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
//                    while (rs.next()) {
//                        System.out.println(rs.getString("word"));
//                    }
                    WriteIn(rs.getString("mean_cn"));
            }
            System.out.println("写入完成！");
            bf.close();
            fr.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            System.out.println("查询数据时出错！"+e.getMessage());
        }
    }

    public static void WriteIn(String content) {
        FileWriter fw = null;
        try {
            File f=new File("C:\\Users\\25343\\Desktop\\Model_11\\mean.txt");//将提取的数据追加写入txt
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



