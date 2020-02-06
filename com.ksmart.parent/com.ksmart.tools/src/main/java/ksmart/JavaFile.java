package ksmart;

import java.io.*;


public class JavaFile {
    public static void CreateFile(String path1, String path2) {
//        String path1 = "D:\\test\\data\\" + src;
//        String path2 = "D:\\test\\res\\" + des;
        File file1 = new File(path1);
//StringBuilder sb=new StringBuilder();
        File file2 = new File(path2);
        try {
            if (!file2.exists()) {
                file2.createNewFile();
            }else{
                file2.delete();
            }
            BufferedReader br = new BufferedReader(new FileReader(file1));
            String line = br.readLine();
//            BufferedWriter bw = new BufferedWriter(new FileWriter(file2, true),1024*10);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file2, true));
            while (line != null) {
                line=  replacedata( line);
                bw.write(line + "\r\n");
                line = br.readLine();
            }
            br.close();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String replacedata(String line){
        line= line.replace(",#,","\t");
        line=line.replace("^","");
        line=line.replace("NULL","");
        line=line.replace("(a#a)","");
       return line;
    }

    public static void main(String[] args) {
        String path1="D:\\test\\src\\ecp_conperf_warranty.txt";
        String path2="D:\\test\\dest\\ecp_conperf_warranty.txt";
        CreateFile( path1,  path2);
    }




}