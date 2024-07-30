# web-ssl
.crt/.cer - форматы самоподписанного сертификата (сожержит публичный ключ) в Windows (различается только поведением в Windows при двойном нажатии

Создание и разница truststore и keystore в SSL с keytool
https://sky.pro/wiki/java/sozdanie-i-raznitsa-trust-store-i-key-store-v-ssl-s-keytool/
Хранилища ключей (KeyStores) предназначены для хранения закрытых ключей и сертификатов, защищены они паролем хранилища ключей. В свою очередь, хранилища доверенных сертификатов (TrustStores) содержат открытые сертификаты, которым мы безоговорочно доверяем.

Примеры  
https://www.thomasvitale.com/https-spring-boot-ssl-certificate/  
https://www.java-success.com/ssl-in-java-with-keytool-to-generate-public-private-key-pair/

Сгенерировать SSL сертификат в хранилище ключей
```bash
keytool -genkey -alias server_keystore -keyalg RSA -storetype PKCS12 -keystore server_keystore.p12 -storepass password -validity 3650 -noprompt -dname "CN=localhost, OU=Test, O=Test, L=Test, S=Test, C=RU"
```

Получить информацию о SSL сертификатах в хранилище
```bash
keytool -list -keystore server_keystore.p12 -V
```

Извлечь SSL сертификат из хранилища
```bash
keytool -export -alias server_keystore -rfc -keystore server_keystore.p12 -file exported.cert -storepass password
```

Импорт сертификата в keystore/truststore (Создать новый keystore/truststore, либо импортировать в уже имеющийся)   
https://stackoverflow.com/a/373307/22064892
```bash
keytool -import -alias client_truststore -file exported.cert -keystore client_truststore.jks -storepass password
```