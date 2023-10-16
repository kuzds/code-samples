package ru.kuzds.integrationsmb;

import jcifs.DialectVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.smb.dsl.Smb;
import org.springframework.integration.smb.session.SmbSessionFactory;

import java.io.File;

@SpringBootApplication
public class IntegrationSmbApplication {

    @Value("${smb.host}")
    private String smbHost;
    @Value("${smb.username}")
    private String smbUsername;
    @Value("${smb.password}")
    private String smbPassword;
    @Value("${smb.shareAndDir}")
    private String smbShareAndDir;

    public static void main(String[] args) {
        SpringApplication.run(IntegrationSmbApplication.class, args);
    }

    @Bean
    public SmbSessionFactory smbSessionFactory() {
        SmbSessionFactory smbSession = new SmbSessionFactory();
        smbSession.setHost(smbHost);
//        smbSession.setPort(445);
//        smbSession.setDomain("myDomain");
        smbSession.setUsername(smbUsername);
        smbSession.setPassword(smbPassword);
        smbSession.setShareAndDir(smbShareAndDir);
        smbSession.setSmbMinVersion(DialectVersion.SMB210);
        smbSession.setSmbMaxVersion(DialectVersion.SMB311);
        return smbSession;
    }

    @Bean
    public IntegrationFlow smbInboundFlow() {
        return IntegrationFlows
                .from(Smb.inboundAdapter(smbSessionFactory())
                                .preserveTimestamp(true)
                                .remoteDirectory("/20231009") // smbHost + smbShareAndDir + remoteDirectory
//                                .regexFilter(".*\\.txt$")
                                .localFilename(f -> f.toUpperCase() + ".a")
                                .localDirectory(new File("./smb_files")),
                        e -> e.id("smbInboundAdapter")
                                .autoStartup(true)
                                .poller(Pollers.fixedDelay(5000)))
                .handle(m -> {
                    System.out.println(m.getPayload());
                })

                .get();
    }
}
