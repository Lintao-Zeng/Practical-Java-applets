package com.lintao.textreadline;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ClipboardListener {
    public static void main(String[] args) throws AWTException {

        String OldContent = "";
        String content = "";
        Robot r = new Robot();

        while (true){//监听剪贴板内容是否改变，刷新频率：秒
           content = getSystemClipboard();
            if(!content.equals(OldContent)){
                WriteIn(content);
                OldContent = content;
                System.out.println("已写入");
            }else {
                System.out.println("没有变化");
            }

            r.delay(1000);//延时处理，1秒
        }

    }

    public static String getSystemClipboard(){
        Clipboard sysClb = null;
        sysClb = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = sysClb.getContents(null);
        try {
            if (null != t && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String)t.getTransferData(DataFlavor.stringFlavor);
                return text;
            }
        } catch (UnsupportedFlavorException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void WriteIn(String content) {
        FileWriter fw = null;
        try {
            File f=new File("C:\\Users\\25343\\Desktop\\WriteIn.txt");//将新的剪贴板数据追加写入txt
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

