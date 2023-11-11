package ru.kuzds.smb;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SmbApplication implements CommandLineRunner {

    private final SmbService smbService;

    public static void main(String[] args) {
        SpringApplication.run(SmbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        smbService.backupAndToLocal(
                "response/20231009/",
                "response/20231009-backup/",
                "./dest/"
        );

        smbService.saveBodyToSmb("SOME BODY".getBytes(), "response/", "text.txt");

        smbService.saveFromLocalToSmb("./dest/", "response/20231009/");
    }
}
