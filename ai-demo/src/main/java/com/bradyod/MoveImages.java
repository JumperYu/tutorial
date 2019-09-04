package com.bradyod;

import com.csvreader.CsvWriter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version 1.0    2019/9/3
 */
public class MoveImages {

    public static void main(String[] args) throws IOException {
        fromXmlToFindJpg(false, true);
    }

    public static void fromXmlToFindJpg(boolean copyImage, boolean writeCsv) throws IOException {
        String root = "C:\\work\\dev\\camera_class_win10";
        String annotations = "\\annotations";
        String dataset = "\\dataset";

        Path path = Paths.get(root.concat(annotations));
        final Map<String, Integer> countMap = Maps.newHashMap();
//        final List<String[]> manifest = Lists.newLinkedList();
        final Map<String, List<String>> manifest = Maps.newHashMap();
        try (Stream<Path> paths = Files.walk(path)) {
            paths.forEach(p -> {
                if (p.toString().endsWith(".xml")) {
                    SAXReader read = new SAXReader();
                    try {
                        org.dom4j.Document doc = read.read(p.toFile());
                        Element rootElement = doc.getRootElement();

                        String picPath = rootElement.element("path").getText();
                        String filename = rootElement.element("filename").getText();

                        Element objElement = rootElement.element("object");
                        String className = objElement.elementText("name");
                        if (Objects.equals(className, "6_trapezoid")) {
                            className = "5_right_round";
                            objElement.element("name").setText("5_right_round");
                        }
                        int count = 1;
                        if (countMap.containsKey(className)) {
                            count = countMap.get(className) + 1;
                        }
                        countMap.put(className, count);

                        if (copyImage) {
                            Files.copy(Paths.get(picPath), Paths.get(root.concat("\\").concat(className).concat("\\").concat(filename)), StandardCopyOption.REPLACE_EXISTING);
                        }

                        List<String> files = Lists.newLinkedList();
                        if (manifest.containsKey(className)) {
                            files = manifest.get(className);
                            files.add(filename.replaceAll("\\.jpg", ""));
                        }
                        manifest.put(className, files);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (writeCsv) {
            writeManifest(root.concat(dataset).concat("\\manifest.csv"), manifest, countMap);
        }

        System.out.println(countMap);
    }

    private static void writeManifest(String manifestPath, Map<String, List<String>> manifest, Map<String, Integer> countMap) throws IOException {
        System.out.println("writing csv lines: " + manifest.size());
        CsvWriter csvWriter = new CsvWriter(manifestPath);
        csvWriter.writeComment("className,fileName,".concat(countMap.toString()));
        manifest.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            entry.getValue().stream().forEach(fileName -> {
                try {
                    csvWriter.writeRecord(new String[]{entry.getKey(), fileName});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        csvWriter.close();
    }

    public static void toJpgSuffix() {
        String win10Path = "C:\\work\\dev\\camera_class_win10\\5_right_round\\";
        Path path = Paths.get("C:\\work\\dev\\camera_class\\5_right_round\\");
        try (Stream<Path> paths = Files.walk(path)) {
            paths.forEach(p -> {
                if (!Files.isDirectory(p)) {
                    Path copyFilePath = Paths.get(win10Path.concat(p.getFileName().toString()).concat(".jpg"));
                    try {
                        Files.copy(p, copyFilePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
