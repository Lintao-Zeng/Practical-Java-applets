package com.lintao.dailycode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class P3 implements Runnable {

    public static JTextArea textArea;
    public static JLabel jLabel;
    public static JLabel jLabel1;
    private JFrame frame;
    // 全局的位置变量，用于表示鼠标在窗口上的位置
    static Point origin = new Point();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        P3 myThread = new P3();
        Thread thread = new Thread(myThread);
        thread.start();
    }

    public static String[] TxtToArr(String name) {
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

    /**
     * Create the application.
     */
    public P3() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 380, 290);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);         //设置窗体显示在最顶端。本实例的核心代码
//        com.sun.awt.AWTUtilities.setWindowOpacity(frame, 0.5F);// 设置整个窗体的不透明度为0.5
        frame.setOpacity(0.5F);
        frame.getContentPane().setBackground(Color.blue);

        frame.addMouseListener(new MouseAdapter() {
            // 按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
            public void mousePressed(MouseEvent e) {
                // 当鼠标按下的时候获得窗口当前的位置
                origin.x = e.getX();
                origin.y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            // 拖动（mouseDragged 指的不是鼠标在窗口中移动，而是用鼠标拖动）
            public void mouseDragged(MouseEvent e) {
                // 当鼠标拖动时获取窗口当前位置
                Point p = frame.getLocation();
                // 设置窗口的位置
                // 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
                frame.setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });

        textArea = new JTextArea();
        textArea.setFont(new Font("none", 0, 25));
        textArea.setBackground(Color.red);
        textArea.setForeground(Color.green);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        Font font = new Font("none", 0, 20);

        jLabel = new JLabel("1", JLabel.CENTER);
        jLabel.setFont(font);
        jLabel.setBounds(160, 0, 30, 29);
        jLabel.setOpaque(true);  //此句是重点，设置背景颜色必须先将它设置为不透明的，因为默认是透明的。。。
        jLabel.setBackground(Color.red);
        jLabel.setForeground(Color.green);
        frame.add(jLabel);

        jLabel1 = new JLabel("1", JLabel.CENTER);
        jLabel1.setFont(font);
        jLabel1.setBounds(0, 0, 60, 29);
        jLabel1.setOpaque(true);
        jLabel1.setBackground(Color.red);
        jLabel1.setForeground(Color.green);
        frame.add(jLabel1);

        JScrollPane scrollpane = new JScrollPane();//创建滚动条面板
        scrollpane.setBounds(0, 30, 380, 260);//自定义该面板位置并设置大小为100*50
        scrollpane.setViewportView(textArea);//（这是关键！不是用add）把text1组件放到滚动面板里
        frame.add(scrollpane);//将滚动条面板加到窗体
    }

    @Override
    public void run() {

        try {
            P3 window = new P3();
            window.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] words = TxtToArr("C:\\Users\\25343\\Desktop\\Model_11\\piece2.txt");
        int count = 0;

        while (true) {
            int time = 15;
            if (count == words.length) {
                count = 0;
            }
            jLabel1.setText(String.valueOf(count));
            jLabel1.paintImmediately(jLabel1.getBounds());
            textArea.setText(words[count]);
            textArea.paintImmediately(textArea.getBounds());
            count++;

            while (time >= 0) {
                jLabel.setText(String.valueOf(time));
                jLabel.paintImmediately(jLabel.getBounds());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time--;
            }

        }
    }
}