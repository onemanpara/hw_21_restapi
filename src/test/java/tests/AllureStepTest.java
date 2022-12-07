package tests;

import helpers.StepGenerator;
import models.Step;
import models.StepList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static specs.LogInSpec.*;

public class AllureStepTest extends TestBase {

    StepList stepList = new StepList();
    Step step = new Step();
    StepGenerator stepGenerator = new StepGenerator();

    @Test
    void createStepTest() {
        step("Создаём шаг тест-кейса и отправляем его с помощью API", () -> {
        String stepName = stepGenerator.generateStep();
        step.setName(stepName);
        stepList.setSteps(List.of(step));
        given(getLoginInSpec())
                .contentType(JSON)
                .body(stepList)
                .when()
                .post("/api/rs/testcase/13469/scenario")
                .then()
                .log().all();});
        step("Подкладываем авторизационную куку", () -> setCookies());
        step("Проверяем, что шаг тест-кейса создался", () -> {
        open("/project/1733/test-cases/13469");
        $(".TestCaseScenarioStep__name").shouldHave(text(step.getName()));});
    }

}
