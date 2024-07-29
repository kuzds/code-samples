# web-ssl
.crt/.cer - форматы самоподписанного сертификата (сожержит публичный ключ) в Windows (различается только поведением в Windows при двойном нажатии

Создание и разница truststore и keystore в SSL с keytool
https://sky.pro/wiki/java/sozdanie-i-raznitsa-trust-store-i-key-store-v-ssl-s-keytool/
Хранилища ключей (KeyStores) предназначены для хранения закрытых ключей и сертификатов, защищены они паролем хранилища ключей. В свою очередь, хранилища доверенных сертификатов (TrustStores) содержат открытые сертификаты, которым мы безоговорочно доверяем.

Пример
https://www.thomasvitale.com/https-spring-boot-ssl-certificate/


```bash
# Generate an SSL certificate in a keystore
keytool -genkey -alias server_keystore -keyalg RSA -storetype PKCS12 -keystore server_keystore.p12 -storepass password -validity 3650
# keytool -genkey -alias server_keystore -keyalg RSA -keysize 2048 -storetype JKS -keystore server_keystore.jks -validity 3650 -storepass password 
```
```bash
# Extract an SSL certificate from a keystore
keytool -export -alias server_keystore -rfc -keystore server_keystore.p12 -file exported.cert -storepass password
```

Импорт сертификата в keystore/truststore
https://stackoverflow.com/a/373307/22064892
```bash
# Создать новый keystore/truststore, либо импортировать в уже имеющийся 
#keytool -import -alias client_truststore -file exported.cer -keystore client_truststore.p12 -storepass password
keytool -import -alias client_truststore -file exported.cert -keystore client_truststore.jks -storepass password
```
Trust this certificate: [Yes]


