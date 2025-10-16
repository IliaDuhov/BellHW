package in.reqres;


import data.ErrorResponse;
import data.User;
import data.UserLogingResponse;
import data.UserLoginRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

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

    @Test
    public void successfulLoginTest(){
        UserLoginRequest request = new UserLoginRequest();
        request.setPassword("cityslicka");
        request.setEmail("eve.holt@reqres.in");
        UserLogingResponse response = given()
                .when()
                .contentType("application/json")
                .header("x-api-key","reqres-free-v1")
                .body(request)
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(UserLogingResponse.class);

        Assert.assertFalse(response.getToken().isEmpty());
    }

    @Test
    public void unSuccessfulLoginTest(){
        UserLoginRequest request = new UserLoginRequest();
        request.setPassword(null);
        request.setEmail("eve.holt@reqres.in");

        ErrorResponse response = given()
                .contentType("application/json")
                .header("x-api-key","reqres-free-v1")
                .body(request)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract().as(ErrorResponse.class);

        Assert.assertEquals(response.getError(), "Missing password");
    }

}
