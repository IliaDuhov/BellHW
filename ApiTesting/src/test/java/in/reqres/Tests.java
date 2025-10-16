package in.reqres;


import data.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;


public class Tests {

    @Test
    public void checkUniqueAvatarFileNames(){
        List<User> users = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("data", User.class);

        Set<String> avatarFileNames = new HashSet<>();
        for (User user : users) {
            String avatarUrl = user.getAvatar();
            String fileName = avatarUrl.substring(avatarUrl.lastIndexOf("/") + 1);
            avatarFileNames.add(fileName);
        }

        Assert.assertEquals(users.size(), avatarFileNames.size(), "количество пользователей с аватарми: " + users.size()
        + "количество пользователей с уникальными названиями файлов аватаров: " + avatarFileNames.size());
    }

}
