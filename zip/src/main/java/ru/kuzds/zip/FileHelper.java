package ru.kuzds.zip;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileHelper {

    public static String getParentDirectoryFromJar() {
        ApplicationHome applicationHome = new ApplicationHome(ZipApplication.class);
        String jarPath = applicationHome.getDir().toString();

        if (jarPath.endsWith("\\classes")) { // this is needed if you plan to run the app using Spring Tools Suit play button.
            jarPath = jarPath.replaceAll("\\\\target\\\\classes", "");
        }

        return jarPath;
    }

    /**
     * Удалить папку рекурсивно
     */
    public static void delete(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                delete(file);
            }
        }
        directoryToBeDeleted.delete();
    }

    /**
     * @param targetDir директория, куда будет помещен разархивированные файлы
     * @param bytes     данные
     * @return Строку с описанием ошибки иначе null
     */
    public static String saveAndUnzip(String targetDir, byte[] bytes) {

        if (!hasZipSignature(bytes)) {
            return "Формат архива отличен от zip";
        }

        File tempDir = new File(targetDir + "/temp");
        File tempFile = new File(targetDir + "/temp/" + UUID.randomUUID() + ".zip");
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            return "Проблемы с файловой системой (1)";
        }

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "Проблемы с файловой системой (2)";
        }

        try (ZipFile zipFile = new ZipFile(tempFile)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String destPath = targetDir + "/" + entry.getName();
                if (!isValidDestPath(targetDir, destPath)) {
                    return "В архиве найдена уязвимость";
                }

                if (entry.isDirectory()) {
                    File file = new File(destPath);
                    if (file.exists()) {
                        return "Архив содержит папку с уже существующем именем";
                    }
                    if (!file.mkdirs()) {
                        return "Не удалось создать папку";
                    }
                } else {
                    try (InputStream inputStream = zipFile.getInputStream(entry);
                         FileOutputStream outputStream = new FileOutputStream(destPath)
                    ) {
                        int nextByte = inputStream.read();
                        while (nextByte != -1) {
                            outputStream.write(nextByte);
                            nextByte = inputStream.read();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Не удалось разархивировать файл";
        } finally {
            delete(tempDir);
        }

        return null;
    }

    /**
     * Проверяем на наличие The Zip Slip Problem (Не выходит ли destPath за границы targetDir)
     */
    private static boolean isValidDestPath(String targetDirStr, String destPathStr) {
        // validate the destination path of a ZipFile entry,
        // and return true or false telling if it's valid or not.

        Path targetDir = Paths.get(targetDirStr);
        Path destPath = Paths.get(destPathStr);
        Path destPathNormalized = destPath.normalize(); //remove ../../ etc.

        return destPathNormalized.toString().startsWith(targetDir.toString());
    }

    /**
     * @param bytes данные
     * @return true = данные имеют формат zip
     */
    private static boolean hasZipSignature(byte[] bytes) {
        if (bytes == null || bytes.length < 4) {
            return false;
        }

        if (bytes[0] != (byte) 0x50) {
            return false;
        }

        if (bytes[1] != (byte) 0x4B) {
            return false;
        }

        if (bytes[2] != (byte) 0x03 && bytes[2] != (byte) 0x05 && bytes[2] != (byte) 0x07) {
            return false;
        }

        return bytes[3] == (byte) 0x04 || bytes[3] == (byte) 0x06 || bytes[3] == (byte) 0x08;
    }
}
