package com.lintao.textreadline;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class SendToEmulator {
    public static void main(String[] args) {
        String content = getClipboardText();
        boolean result = cmd("adb shell am broadcast -a clipper.set -e text " + content);
        System.out.println(result);
    }

    // 获取剪切板中的内容
    public static String getClipboardText()
    {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipT = clip.getContents(null);
        if (clipT != null)
        {
            // 检查内容是否是文本类型
            if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
            {
                try
                {
                    return (String) clipT
                            .getTransferData(DataFlavor.stringFlavor);
                }
                catch (UnsupportedFlavorException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean cmd(String command){
        boolean flag = false;
        try{
            Runtime.getRuntime().exec(command);
            flag = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}
