package ru.netology.ibank.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.ibank.data.DataHelper;
import ru.netology.ibank.page.DashboardPage;
import ru.netology.ibank.page.LoginPage;
import ru.netology.ibank.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @BeforeEach
    void openAndLogin() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("1") + 1000;
        int expected1 = dashboardPage.getCardBalance("2") - 1000;
        dashboardPage.depositToFirst();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("2"), "1000");
        int actual = dashboardPage.getCardBalance("1");
        int actual1 = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("2") + 1000;
        int expected1 = dashboardPage.getCardBalance("1") - 1000;
        dashboardPage.depositToSecond();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("1"), "1000");
        int actual = dashboardPage.getCardBalance("2");
        int actual1 = dashboardPage.getCardBalance("1");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }

    @Test
    void shouldTransferTotalDeposit() {
        var dashboardPage = new DashboardPage();
        String balance = String.valueOf(dashboardPage.getCardBalance("1"));
        int expected1 = dashboardPage.getCardBalance("1") + dashboardPage.getCardBalance("2");
        dashboardPage.depositToSecond();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("1"), balance);
        int expected = 0;
        int actual = dashboardPage.getCardBalance("1");
        int actual1 = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }

    @Test
    void shouldTransferTwice() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("2") + 4000;
        int expected1 = dashboardPage.getCardBalance("1") - 4000;
        dashboardPage.depositToSecond();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("1"), "1000");
        dashboardPage.depositToSecond();
        transferPage.moneyTransfer(DataHelper.getCard("1"), "3000");
        int actual = dashboardPage.getCardBalance("2");
        int actual1 = dashboardPage.getCardBalance("1");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }


    @Test
    void shouldNotTransferMoreThanBalance() {
        var dashboardPage = new DashboardPage();
        int expected = dashboardPage.getCardBalance("1");
        int expected1 = dashboardPage.getCardBalance("2");
        String balance = String.valueOf(dashboardPage.getCardBalance("1") + 1000);
        dashboardPage.depositToSecond();
        var transferPage = new TransferPage();
        transferPage.moneyTransfer(DataHelper.getCard("1"), balance);
        transferPage.getError();
        int actual = dashboardPage.getCardBalance("1");
        int actual1 = dashboardPage.getCardBalance("2");

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected1, actual1);
    }
}