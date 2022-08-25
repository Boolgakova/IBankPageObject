package ru.netology.ibank.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement cardFirst = $x("//div[contains(@data-test-id,\"92df3f1c-a033-48e6-8390-206f6b1f56c0\")]");
    private SelenideElement cardSecond = $x("//div[contains(@data-test-id,\"0f3f5c2a-249e-4c3d-8287-09f7a039391d\")]");
    private ElementsCollection depositButton = $$x("//span[contains(text(),'Пополнить')]");
    private SelenideElement reload = $("[data-test-id=action-reload]");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    private final ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(String id) {
        val id1 = cardFirst.getAttribute("data-test-id");
        val id2 = cardSecond.getAttribute("data-test-id");
        String text = null;
        if (id.equals("1")) {
            for (SelenideElement card : cards) {
                if (card.getAttribute("data-test-id").equals(id1)) {
                    String text1 = card.text();
                    text = text1;
                }
            }
        }
        if (id.equals("2")) {
            for (SelenideElement card : cards) {
                if (card.getAttribute("data-test-id").equals(id2)) {
                    val text2 = card.text();
                    text = text2;
                }
            }
        }
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