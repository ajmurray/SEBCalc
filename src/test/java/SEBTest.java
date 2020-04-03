import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SEBTest {

    @Test
    public void validE2ETest() {
        open("http://www.seb.ee/eng/loan-and-leasing/leasing/car-leasing#calculator");

        removeCookiePopup();

        $("#headLine").scrollTo();

        $(".customLabel.-CS-co-applicant").click();
        $("#coApplicant").shouldBe(checked);

        $("#netoIncome").setValue("3000");
        $("#netoIncome").shouldHave(exactValue("3000"));

        $("#monthlyFinancialObligations").setValue("1000");
        $("#monthlyFinancialObligations").shouldHave(exactValue("1000"));

        $("#numOfDependants").setValue("2");
        $("#numOfDependants").shouldHave(exactValue("2"));

        $("#leaseSum").shouldBe(visible);
        $(".button.discrete.thickbox.-CS-apply-button").shouldBe(visible);
    }

    @Test
    public void invalidE2ELowIncomeTest() {
        open("http://www.seb.ee/eng/loan-and-leasing/leasing/car-leasing#calculator");

        removeCookiePopup();

        $("#headLine").scrollTo();

        $(".customLabel.-CS-co-applicant").click();
        $("#coApplicant").shouldBe(checked);

        $("#netoIncome").setValue("700");
        $("#netoIncome").shouldHave(exactValue("700"));

        $("#monthlyFinancialObligations").setValue("0");
        $("#monthlyFinancialObligations").shouldHave(exactValue("0"));

        $("#numOfDependants").setValue("0");
        $("#numOfDependants").shouldHave(exactValue("0"));

        $("#leaseSum").shouldNotBe(visible);
        $(".button.discrete.thickbox.-CS-apply-button").shouldNotBe(visible);
    }

    @Test
    public void fieldValidationTest() {
        open("http://www.seb.ee/eng/loan-and-leasing/leasing/car-leasing#calculator");

        removeCookiePopup();

        $("#headLine").scrollTo();

        // Test Co-applicant checkbox
        $(".customLabel.-CS-co-applicant").click();
        $("#coApplicant").shouldBe(checked);

        $(".customLabel.-CS-co-applicant").click();
        $("#coApplicant").shouldNotBe(checked);

        // Test Net Income field
        $("#netoIncome").setValue("-1");
        $("#netoIncome").shouldHave(exactValue("1"));

        $("#netoIncome").setValue("a");
        $("#netoIncome").shouldHave(exactValue(""));

        $("#netoIncome").setValue("!%&*^?+=");
        $("#netoIncome").shouldHave(exactValue(""));

        $("#netoIncome").setValue("123456");
        $("#netoIncome").shouldHave(exactValue("12345"));

        //  Test Financial Obligation field
        $("#monthlyFinancialObligations").setValue("-1");
        $("#monthlyFinancialObligations").shouldHave(exactValue("1"));

        $("#monthlyFinancialObligations").setValue("a");
        $("#monthlyFinancialObligations").shouldHave(exactValue(""));

        $("#monthlyFinancialObligations").setValue("!%&*^?+=");
        $("#monthlyFinancialObligations").shouldHave(exactValue(""));

        $("#monthlyFinancialObligations").setValue("123456");
        $("#monthlyFinancialObligations").shouldHave(exactValue("12345"));

        // Test Dependencies field
        $("#numOfDependants").setValue("-1");
        $("#numOfDependants").shouldHave(exactValue("1"));

        $("#numOfDependants").setValue("a");
        $("#numOfDependants").shouldHave(exactValue(""));

        $("#numOfDependants").setValue("!%&*^?+=");
        $("#numOfDependants").shouldHave(exactValue(""));

        $("#numOfDependants").setValue("0");
        $("#decreaseNumOfDependantsBtn").click();
        $("#numOfDependants").shouldHave(exactValue("0"));

        $("#numOfDependants").setValue("9");
        $("#increaseNumOfDependantsBtn").click();
        $("#numOfDependants").shouldHave(exactValue("9"));

        $("#numOfDependants").setValue("12");
        $("#numOfDependants").shouldNotHave(exactValue("12"));

    }

    public void removeCookiePopup() {
        // remove cookie popup
        if ($(".main.accept-selected").isDisplayed()) {
            $(".main.accept-selected").click();
            $(".main.accept-selected").shouldNotBe(visible);
        }

        $("#netoIncome").waitUntil(visible, 2000);
    }
}
