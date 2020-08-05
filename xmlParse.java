package com.lintao.dailycode;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class xmlParse {

    public static void node(NodeList list){
        String temp = "";
        int count = 1;
        for (int i = 0; i <list.getLength(); i++) {
            Node node = list.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j = 0; j <childNodes.getLength() ; j++) {
                if (childNodes.item(j).getNodeType()==Node.ELEMENT_NODE) {
//                    System.out.print(childNodes.item(j).getNodeName() + ":");
//                    System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
                        if(count % 3 != 0)
                        temp = temp + childNodes.item(j).getFirstChild().getNodeValue() + "/";

                        if (count % 3 == 0){
                            System.out.println("https://mvnrepository.com/artifact/" + temp + "\n");
                            temp = "";
                        }
                        count++;

                }
            }
        }
    }

    public static void main(String[] args) {
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("query.xml");
            NodeList sList = d.getElementsByTagName("dependency");
            node(sList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
