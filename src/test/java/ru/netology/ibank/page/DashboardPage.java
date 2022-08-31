package ru.netology.ibank.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.ibank.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection depositButton = $$x("//span[contains(text(),'Пополнить')]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    private final ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(String id) {
        String text = cards.findBy(text(DataHelper.getCard(id).getNumber().substring(12, 16))).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void depositToSecond() {
        depositButton.last().click();
    }

    public void depositToFirst() {
        depositButton.first().click();
    }
}