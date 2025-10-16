package helpers;

public class DataProvider {

    @org.testng.annotations.DataProvider(name = "successfulLoginData")
    public Object[][] provideLoginData() {
        return new Object[][] {
                {"eve.holt@reqres.in", "cityslicka"}
        };
    }

    @org.testng.annotations.DataProvider(name = "unsuccessfulLoginData")
    public Object[][] provideUnsuccessfulLoginData() {
        return new Object[][] {
                {"eve.holt@reqres.in", null}
        };
    }

    @org.testng.annotations.DataProvider(name = "provideDataToCheckNumberOfTags")
    public Object[][] provideDataToCheckNumberOfTags() {
        return new Object[][] {
                {15}
        };
    }
}
