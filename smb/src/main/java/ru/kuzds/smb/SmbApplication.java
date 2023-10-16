package ru.kuzds.smb;

import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.CloseableIterator;
import jcifs.SmbResource;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

@Slf4j
@SpringBootApplication
public class SmbApplication implements CommandLineRunner {

    @Value("${smb.url}")
    private String smbUrl;
    @Value("${smb.username}")
    private String smbUsername;
    @Value("${smb.password}")
    private String smbPassword;

    private final String targetDirectory = "response/20231009/";
    private final String destDirectory = "./dest/";


    public static void main(String[] args) {
        SpringApplication.run(SmbApplication.class, args);
    }

    @Bean
    CIFSContext cifsContext() throws CIFSException {
        Properties prop = new Properties();
        CIFSContext baseContext = new BaseContext(new PropertyConfiguration(prop));
        return baseContext.withCredentials(new NtlmPasswordAuthentication(baseContext, null, smbUsername, smbPassword));
    }

    @Override
    public void run(String... args) throws Exception {
        CIFSContext auth = cifsContext();
        String smbTargetPathStr = smbUrl + targetDirectory;
        try (SmbFile smbRoot = new SmbFile(smbTargetPathStr, auth)) {
            if (!smbRoot.exists()) {
                throw new RuntimeException("Каталог smb-ресурса не существует (" + smbTargetPathStr + ")");
            }
        }

        try (SmbFile smbDirectory = new SmbFile(smbTargetPathStr, auth);
             CloseableIterator<SmbResource> iterator = smbDirectory.children()
        ) {

            while (iterator.hasNext()) {
                SmbResource smbResource = iterator.next();
                if (smbResource.isDirectory()) {
                    continue;
                }

                try {
                    smbResource.renameTo(smbResource);
                } catch (Exception e) {
                    // Файл занят другим процессом
                    log.info("Пропущен {}", smbResource.getName());
                    continue;
                }

                // Теперь сохраняем файл локально
                Path emptyFile = Paths.get(destDirectory + smbResource.getName());
                Files.createDirectories(emptyFile.getParent());

                try (InputStream inputStream = smbResource.openInputStream()) {
                    Files.copy(inputStream, emptyFile, StandardCopyOption.REPLACE_EXISTING);
                }
                log.info("Файл {} скопирован успешно", smbResource.getName());
            }
        }
    }

    private static void createSmbDirectories(String folder, CIFSContext auth) throws Exception {
        try (SmbFile smbFolder = new SmbFile(folder, auth)) {
            String parent = smbFolder.getParent();
            try (SmbFile smbParent = new SmbFile(parent, auth)) {
                if (!smbParent.exists()) {
                    createSmbDirectories(parent, auth);
                }
            }

            if (!smbFolder.exists()) {
                smbFolder.mkdir();
            }
        }
    }
}
