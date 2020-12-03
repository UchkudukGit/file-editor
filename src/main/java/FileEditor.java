import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FileEditor {

    public static List<File> getFilesFromFolder(String path) {
        List<File> fileList = new ArrayList<>();
        try {
            File folder = new File(path);
            File[] folderEntries = folder.listFiles();
            for (File file : folderEntries) {
                if (file.isDirectory()) {
                    fileList.addAll(getFilesFromFolder(file.getAbsolutePath()));
                    continue;
                }
                fileList.add(file);
            }
        } catch (Exception e) {
            new RuntimeException("Ошибка при получении списка файлов в папке " + path);
        }
        return fileList;
    }

    public static void updateFilesFromFolder(String path, Function<File, File> function) {
        getFilesFromFolder(path)
                .forEach(file -> {
                            try {
                                function.apply(file);
                            } catch (Exception e) {
                                throw new RuntimeException("Ошибка при измении файла " + file.getAbsolutePath(), e);
                            }
                        }
                );
    }

    public static void updateFilesFromFolderByStringList(String path, Function<List<String>, List<String>> function) {
        updateFilesFromFolder(path, file -> {
            try {
                ArrayList<String> stringList = new ArrayList<>(Arrays.asList(FileUtils
                        .readFileToString(file, "UTF-8")
                        .split("\r\n")));
                List<String> newFileStrList = function.apply(stringList);
                String newFileStr = String.join("\r\n", newFileStrList);
                FileUtils.writeStringToFile(file, newFileStr, "UTF-8");
                return file;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
//        getFilesFromFolder(path)
//                .forEach(file -> {
//                            try {
//                                ArrayList<String> stringList = new ArrayList<>(Arrays.asList(FileUtils
//                                        .readFileToString(file, "UTF-8")
//                                        .split("\r\n")));
//                                List<String> newFileStrList = function.apply(stringList);
//                                String newFileStr = String.join("\r\n", newFileStrList);
//                                FileUtils.writeStringToFile(file, newFileStr, "UTF-8");
//                            } catch (Exception e) {
//                                throw new RuntimeException("?????? ??? ?????????? ????? " + file.getAbsolutePath(), e);
//                            }
//                        }
//                );
    }
}