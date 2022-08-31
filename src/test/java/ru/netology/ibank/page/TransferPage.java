package ru.netology.ibank.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.ibank.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transfer = $("[data-test-id=action-transfer]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public DashboardPage moneyTransfer(DataHelper.CardInfo from, String howMuch) {
        amount.setValue(howMuch);
        fromField.setValue(from.getNumber());
        transfer.click();
        return new DashboardPage();
    }

    public void getError() {
        error.shouldBe(Condition.visible);
    }
}
