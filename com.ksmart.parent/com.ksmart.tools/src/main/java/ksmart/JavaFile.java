package ksmart;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JavaFile {
    private static int model = 1;
    private static String newpathkey = null;
    private static String suffix = null;

    public static void dealFile(String srcPath) {
        long start = System.currentTimeMillis();
        System.out.print("srcPath:" + srcPath + "\t");
        File file1 = new File(srcPath);
        String destPath = srcPath.replace(newpathkey, newpathkey + "_dest");
        System.out.print("destPath:" + destPath + "\t");
        File file2 = new File(destPath);
        File file2Path = new File(file2.getParent());
        if (!file2Path.exists()) {
            file2Path.mkdirs();
        }
        try {
            if (!file2.exists()) {
                file2.createNewFile();
            } else {
                file2.delete();
            }
            BufferedReader br = new BufferedReader(new FileReader(file1));
            String line = br.readLine();
//            BufferedWriter bw = new BufferedWriter(new FileWriter(file2, true),1024*10);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file2, true));
            while (line != null) {
                line = replacedata(line);
                bw.write(line + "\r\n");
                line = br.readLine();
            }
            br.close();
            bw.flush();
            bw.close();
            System.out.println("    costMills:" + (System.currentTimeMillis() - start));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String replacedata(String line) {
        if (model == 1) {
            line = replacedata1(line);
        } else if (model == 2) {
            line = replacedata2(line);
        }

        return line;
    }

    private static String replacedata1(String line) {
        line = line.replace(",#,", "\t");
        line = line.replace("^", "");
        line = line.replace("NULL", "");
        line = line.replace("(a#a)", "");
        return line;
    }

    private static String replacedata2(String line) {
        line = line.replace("\"", "");
        return line;
    }

    /**
     * 递归列举出当前指定文件下文件列表
     *
     * @param file
     */
    public static void listFiles(File file, List<String> filesPath) {
        if (!file.isDirectory()) {
            if (suffix != null) {
                if (file.getAbsolutePath().endsWith(suffix)) {
                    System.out.println("addFile====>" + file.getAbsolutePath());
                    filesPath.add(file.getAbsolutePath());
                }
            } else {
                System.out.println("addFile====>" + file.getAbsolutePath());
                filesPath.add(file.getAbsolutePath());
            }
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                listFiles(f, filesPath);
            }
        }
    }

    public static void main(String[] args) {

//        File file = new File("D:\\test\\");
//        model = 2;
//        newpathkey = "test";
//        suffix="txt";

        model = Integer.parseInt(args[0]);
        File file = new File(args[1]);
        newpathkey = args[2];
        if (args.length > 3) {
            suffix = args[3];
        }

        List<String> filesPath = new ArrayList<>();
        listFiles(file, filesPath);
        for (int i = 0; i < filesPath.size(); i++) {
            dealFile(filesPath.get(i));
        }
    }


}