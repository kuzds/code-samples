package ru.kuzds.jaxb;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class XmlHelperTest {

    private static final ru.kuzds.jaxb.ObjectFactory USER_OBJ_FACTORY = new ru.kuzds.jaxb.ObjectFactory();

    @Test
    void test() {

        User user = new User();
        user.setId(1);
        user.setEmail("kuzds@bk.ru");
        user.setSex(Sex.MALE);

        String body = XmlHelper.marshal(USER_OBJ_FACTORY.createUser(user));
        User unmarshalledUser = XmlHelper.unmarshall(body, User.class);

        Assertions.assertThat(user)
                .usingRecursiveComparison()// если есть @Data, то не нужно
                .isEqualTo(unmarshalledUser);
    }

}