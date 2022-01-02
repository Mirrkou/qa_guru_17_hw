package hw17;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RestTest {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
    }

    @Test
    @DisplayName("Successful open Gift Cards page (UI)")
    void openGiftCardsPage() {
        step("Open main page", () ->
                open("http://demowebshop.tricentis.com/"));
        step("Click to gift cards page", () ->
                $("[class='top-menu']").find(byText("Gift Cards"))).click();
        step("Check text $100 Physical Gift Card", () ->
                $("[data-productid='4']").shouldHave(text("$100 Physical Gift Card")));
    }

    @Test
    @DisplayName("Successful add $100 Physical Gift Card to wishlist (API)")
    void addGiftCardToWishlist() {
        String body = "giftcard_4.RecipientName=Ivan&giftcard_4.SenderName=Oleg&giftcard_4.Message=Surprise&addtocart_4.EnteredQuantity=1";
        step("Add gift card to wishlist", () -> {
            given()
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .body(body)
                    .when()
                    .post("/addproducttocart/details/4/2")
                    .then()
                    .statusCode(200)
                    .body("updatetopwishlistsectionhtml", is("(1)"))
                    .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"));
        });
    }
}