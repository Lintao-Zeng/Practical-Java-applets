package com.lintao.demos;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SohuVD {

    static List<String> videos = null;

    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner (System.in);
        System.out.println("请输入当前集数：");
        String current = in.nextLine();
        System.out.println("请输入链接,输入@结束：");
        ArrayList<String> Link = new ArrayList<>();
        while (true){
            String temp = in.nextLine();
            if(temp.equals("@"))
                break;
            else
                Link.add(temp);
        }
        System.out.println("退出循环");

        //下载文件&重命名
        int times = 1;
        videos = new ArrayList<>();

        for (String i : Link) {
            try {
                downloadNet(i,times + ".mp4");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            videos.add("C:\\Users\\Administrator\\Downloads\\"+ times +".mp4");
            times++;
        }

        //文件合并
        File file = new File("C:\\Users\\Administrator\\Downloads\\Video\\" + current + ".mp4");//合并文件导出路径
        System.out.println("开始合并");
        String result = mergeVideo(videos,file);
        System.out.println(result);
        in.close();

        //删除文件
        for (String i : videos) {
           forceDeleteFile(i);
        }
    }

    public static void downloadNet(String url2, String fileName) throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread;
 
        URL url = new URL(url2);
 
        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("C:\\Users\\Administrator\\Downloads\\"+fileName);//下载片段保存路径
 
            byte[] buffer = new byte[1204];

            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            inStream.close();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并视频
     *
     * @param videoList: 所有视频地址集合
     * @param mergeVideoFile: 目标文件
     * @return
     */
    public static String mergeVideo(List<String> videoList, File mergeVideoFile) {

        FileOutputStream fos = null;
        FileChannel fc = null;

        try {
            List<Movie> sourceMovies = new ArrayList<>();
            for (String video : videoList) {
                sourceMovies.add(MovieCreator.build(video));
            }

            List<Track> videoTracks = new LinkedList<>();
            List<Track> audioTracks = new LinkedList<>();

            for (Movie movie : sourceMovies) {
                for (Track track : movie.getTracks()) {
                    if ("soun".equals(track.getHandler())) {
                        audioTracks.add(track);
                    }

                    if ("vide".equals(track.getHandler())) {
                        videoTracks.add(track);
                    }
                }
            }

            Movie mergeMovie = new Movie();
            if (audioTracks.size() > 0) {
                mergeMovie.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }

            if (videoTracks.size() > 0) {
                mergeMovie.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }

            Container out = new DefaultMp4Builder().build(mergeMovie);
            fos = new FileOutputStream(mergeVideoFile);
            fc = fos.getChannel();
            out.writeContainer(fc);
            fc.close();
            fos.close();
            return mergeVideoFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "运行出错";
    }

    public static void forceDeleteFile(String file) throws IOException {
        for (int i=0;i<100;i++){
            System.gc();
            String cmd = "cmd /c del C:\\Users\\Administrator\\Downloads\\*.mp4";
            Runtime rt = Runtime.getRuntime(); // 获取运行时系统
            Process proc = rt.exec(cmd); // 执行命令
        }
    }

}
