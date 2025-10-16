package in.reqres;


import data.*;
import helpers.DataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static Specification.Specification.*;
import static io.restassured.RestAssured.given;

/**
 * @author IliaDuhov
 */
public class Tests {

    @Test
    public void checkUniqueAvatarFileNames(){
        installSpec(requestSpecification(),responseSpecification200());
        List<UserDto> users = given()
                .when()
                .get("/api/users?page=2")
                .then()
                .log().all()
                .extract()
                .jsonPath()
                .getList("data", UserDto.class);

        Set<String> avatarFileNames = new HashSet<>();
        for (UserDto user : users) {
            String avatarUrl = user.getAvatar();
            String fileName = avatarUrl.substring(avatarUrl.lastIndexOf("/") + 1);
            avatarFileNames.add(fileName);
        }

        Assert.assertEquals(users.size(), avatarFileNames.size(), "количество пользователей с аватарми: " + users.size()
        + "количество пользователей с уникальными названиями файлов аватаров: " + avatarFileNames.size());
        deleteSpec();
    }

    @Test(dataProvider = "successfulLoginData", dataProviderClass = DataProvider.class)
    public void successfulLoginTest(String email, String password){
        installSpec(requestSpecification(),responseSpecification200());
        UserLoginRequest request = new UserLoginRequest();
        request.setPassword(password);
        request.setEmail(email);
        UserLogingResponse response = given()
                .when()
                .body(request)
                .post("/api/login")
                .then()
                .log().all()
                .extract().as(UserLogingResponse.class);

        Assert.assertFalse(response.getToken().isEmpty(), "ожидаемый ответ: " + response.getToken() +
                "полученный ответ: null");
        deleteSpec();
    }

    @Test(dataProvider = "unsuccessfulLoginData", dataProviderClass = DataProvider.class)
    public void unSuccessfulLoginTest(String email, String password){
        installSpec(requestSpecification(),responseSpecification400());
        UserLoginRequest request = new UserLoginRequest();
        request.setPassword(password);
        request.setEmail(email);

        ErrorResponse response = given()
                .body(request)
                .when()
                .post("/api/login")
                .then()
                .log().all()
                .extract().as(ErrorResponse.class);

        Assert.assertEquals(response.getError(), "Missing password", "ожидаемый ответ: " + response.getError());
        deleteSpec();
    }

    @Test
    public void checkDataSortedByYear(){
        installSpec(requestSpecification(),responseSpecification200());
        ResourceResponse response = given()
                .when()
                .get("/api/unknown")
                .then()
                .log().all()
                .extract().as(ResourceResponse.class);

        List<Integer> years = response.getData().stream()
                .map(ResourceData::getYear)
                .collect(Collectors.toList());

        List<Integer> sortedYears = years.stream()
                .sorted()
                .collect(Collectors.toList());

        Assert.assertEquals(years, sortedYears, "Данные не отсортированы по годам. Исходный порядок: " + years +
                        ", ожидаемый порядок: " + sortedYears);
        deleteSpec();

    }

    @Test(dataProvider = "provideDataToCheckNumberOfTags", dataProviderClass = DataProvider.class)
    public void checkNumberOfTags(int tagCount) {
        String responseBody = given()
                .when()
                .get("https://gateway.autodns.com/")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .body().asString();

        int count = 0;
        int index = 0;

        while (index < responseBody.length()) {
            int tagStart = responseBody.indexOf('<', index);
            if (tagStart == -1) break;

            int tagEnd = responseBody.indexOf('>', tagStart);
            if (tagEnd == -1) break;

            String tagContent = responseBody.substring(tagStart + 1, tagEnd).trim();
            if (!tagContent.startsWith("/") && !tagContent.endsWith("/")) {
                String tagName = tagContent.split("\\s+")[0];
                if (!tagName.isEmpty()) {
                    count++;
                }
            }
            index = tagEnd + 1;
        }
        Assert.assertEquals(count, tagCount, "найдено тэгов: " + count + " ожидалось найти: " + tagCount);
    }
}
