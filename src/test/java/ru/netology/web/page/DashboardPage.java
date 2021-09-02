package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.apache.commons.lang3.StringUtils.trim;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
//    private ElementsCollection cards = $$(".list__item");
//    private ElementsCollection buttons = $$(".button__content");


    public DashboardPage() {
        heading.shouldBe(visible);
    }

}
