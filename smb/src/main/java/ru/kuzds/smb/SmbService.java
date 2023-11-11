package ru.kuzds.smb;

import jcifs.CIFSContext;
import jcifs.CloseableIterator;
import jcifs.SmbResource;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthenticator;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Properties;

@Slf4j
@Service
public class SmbService {

    @Value("${smb.url}")
    private String smbUrl;
    @Value("${smb.username}")
    private String smbUsername;
    @Value("${smb.password}")
    private String smbPassword;

    private CIFSContext auth;

    @PostConstruct
    private void init() throws Exception {
        Properties prop = new Properties();
        CIFSContext baseContext = new BaseContext(new PropertyConfiguration(prop));
        auth = baseContext.withCredentials(new NtlmPasswordAuthenticator( null, smbUsername, smbPassword));

        try (SmbFile smbRoot = new SmbFile(smbUrl, auth)) {
            if (!smbRoot.exists()) {
                throw smbDirectoryNotFoundException(smbUrl);
            }
        }
    }


    /**
     * Переносим файлы из smb-target в smb-backup папку, а затем копируем локально в dest папку
     *
     * @param targetDirectory smb-папка
     * @param backupDirectory backup smb-папка
     * @param destDirectory   локальная папка, куда будет произведено копирование
     */
    public void backupAndToLocal(String targetDirectory, String backupDirectory, String destDirectory) throws Exception {

        String smbTargetPathStr = smbUrl + targetDirectory;
        String smbBackupPathStr = smbUrl + backupDirectory;

        try (SmbFile smbRoot = new SmbFile(smbTargetPathStr, auth)) {
            if (!smbRoot.exists()) {
                throw smbDirectoryNotFoundException(smbTargetPathStr);
            }
        }

        try (SmbFile target = new SmbFile(smbTargetPathStr, auth);
             SmbFile backup = new SmbFile(smbBackupPathStr, auth);
             CloseableIterator<SmbResource> iterator = target.children()
        ) {
            // Создаем backup папку в случае ее отсутствия
            backup.mkdirs();
            // Создаем локальную папку
            Files.createDirectories(Paths.get(destDirectory));

            while (iterator.hasNext()) {
                SmbResource smbResource = iterator.next();

                if (smbResource.isDirectory()) {
                    continue;
                }

                try (SmbFile smbBackup = new SmbFile(smbBackupPathStr + smbResource.getName(), auth)) {
                    try {
                        // Перемещаем файл в backup папку
                        smbResource.renameTo(smbBackup);
                    } catch (Exception e) {
                        // Файл занят другим процессом
                        continue;
                    }

                    // Теперь сохраняем файл локально
                    Path emptyFile = Paths.get(destDirectory + smbResource.getName());
                    try (InputStream inputStream = smbBackup.openInputStream()) {
                        Files.copy(inputStream, emptyFile, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
    }

    /**
     * Переносим файлы из локальной target папки, в smb-dest папку
     *
     * @param targetDirectory локальная папка, откуда копируем все файлы
     * @param destDirectory   smb-папка, куда копируем
     */
    public void saveFromLocalToSmb(String targetDirectory, String destDirectory) throws Exception {

        String smbDirectoryStr = smbUrl + destDirectory;
        try (SmbFile directory = new SmbFile(smbDirectoryStr, auth)) {
            // Создаем backup папку в случае ее отсутствия
            directory.mkdirs();

            File[] files = Paths.get(targetDirectory).toFile().listFiles();
            for (File localfile : Objects.requireNonNull(files)) {
                if (!localfile.renameTo(localfile)) {
                    // Файл занят другим процессом
                    continue;
                }

                try (SmbFile smbFile = new SmbFile(smbDirectoryStr + localfile.getName(), auth);
                     SmbFileOutputStream output = new SmbFileOutputStream(smbFile, true);
                     InputStream input = Files.newInputStream(localfile.toPath())
                ) {
                    copyStream(input, output);
                }
            }
        }
    }

    /**
     * @param body      тело файла
     * @param directory smb-папка c создаваемым файлом
     * @param fileName  создаваемый smb-файл
     */
    public void saveBodyToSmb(byte[] body, String directory, String fileName) throws Exception {
        String smbDirectoryStr = smbUrl + directory;

        try (SmbFile smbDirectory = new SmbFile(smbDirectoryStr, auth)) {
            smbDirectory.mkdirs();
        }

        String smbFileName = smbDirectoryStr + normalize(fileName);
        try (SmbFile smbFile = new SmbFile(smbFileName, auth)) {

            try (SmbFileOutputStream out = new SmbFileOutputStream(smbFile, true)) {
                out.write(body);
            }
        }
    }

    /**
     * убираем возможные "../../"
     */
    private static String normalize(String fileName) {
        return Paths.get(fileName).getFileName().toString();
    }

    private static RuntimeException smbDirectoryNotFoundException(String directory) {
        return new RuntimeException("Каталог smb-ресурса не существует (" + directory + ")");
    }

    private static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024]; // Adjust if you want
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
}
