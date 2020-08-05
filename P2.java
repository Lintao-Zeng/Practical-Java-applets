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

public class P2 implements Runnable{

    public static JTextArea textArea;
    private JFrame frame;
    // 全局的位置变量，用于表示鼠标在窗口上的位置
    static Point origin = new Point();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    P2 window = new P2();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        P2 myThread=new P2();
        Thread thread=new Thread(myThread);
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
    public P2() {
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
        com.sun.awt.AWTUtilities.setWindowOpacity(frame, 0.5F);// 设置整个窗体的不透明度为0.5
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
        Font font = new Font("shabi",0,25);
        textArea.setFont(font);
        textArea.setBackground(Color.red);
        textArea.setForeground(Color.green);
//        textArea.setText("测试一");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollpane=new JScrollPane();//创建滚动条面板
        scrollpane.setBounds(0,30,380,260);//自定义该面板位置并设置大小为100*50
        scrollpane.setViewportView(textArea);//（这是关键！不是用add）把text1组件放到滚动面板里
        frame.add(scrollpane);//将滚动条面板加到窗体
    }

    @Override
    public void run() {
        while (true){

            String[] words = TxtToArr("C:\\Users\\25343\\Desktop\\Model_11\\piece2.txt");
            int count = 0;
            while (true){
                try {
                    textArea.setText(words[count]);
                    textArea.paintImmediately(textArea.getBounds());
                    count++;
                    if (count == words.length){
                        count = 0;
                    }
                    Thread.sleep(15 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}