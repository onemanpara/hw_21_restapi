package specs;

import api.AuthApi;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Cookie;

import static api.AuthApi.ALLURE_TESTOPS_SESSION;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.CustomApiListener.withCustomTemplates;
import static tests.TestBase.userDataConfig;

public class LogInSpec {

    public final static String
            user = userDataConfig.username(),
            password = userDataConfig.password(),
            userToken = userDataConfig.token();

    public static RequestSpecification getLoginInSpec() {

        AuthApi authApi = new AuthApi();
        String xsrfToken = authApi.getXsrfToken(userToken);
        String authorizationCookie = authApi
                .getAuthorizationCookie(userToken, user, password);

        return RestAssured
                .given()
                .log().all()
                .filter(withCustomTemplates())
                .header("X-XSRF-TOKEN", xsrfToken)
                .cookies("XSRF-TOKEN", xsrfToken,
                        ALLURE_TESTOPS_SESSION, authorizationCookie)
                .contentType(ContentType.JSON);

    }

    public static void setCookies() {
        String authorizationCookie = new AuthApi()
                .getAuthorizationCookie(userToken, user, password);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie(ALLURE_TESTOPS_SESSION, authorizationCookie));
    }

}
