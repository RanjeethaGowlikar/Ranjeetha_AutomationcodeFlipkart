
package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {

    static ChromeDriver driver;
    public static WebDriverWait wait;

    @BeforeSuite
    public void createdriver() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).browserVersion("125.0.6422.61").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void TestCase01() throws InterruptedException {
        driver.get("https://www.flipkart.com/");

        try {
            WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(By.className("_30XB9F")));
            popup.click();
        } catch (Exception e) {
            System.out.println("pop is not found");
        }

        WebElement searchBox = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//input[@title='Search for Products, Brands and More']")));
        searchBox.sendKeys("Washing Machine");
        searchBox.submit();

        WebElement popularity = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Popularity']")));
        popularity.click();

        List<WebElement> ratings = driver.findElements(By.className("Y1HWO0"));
        int count = 0;
        try {
            for (WebElement rating : ratings) {
                String rateText = rating.getText();
                double Ratings = Double.parseDouble(rateText);
                System.out.println("ratings :" + Ratings);
                if (Ratings <= 4) {
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println("print successfully");
        }
        System.out.println("count less than or equal to 4 is :" + count);
    }

    @Test
    public void TestCase02() throws InterruptedException {

        driver.get("https://www.flipkart.com/");
        Thread.sleep(3000);
        WebElement searchBox1 = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//input[@title='Search for Products, Brands and More']")));

        searchBox1.clear();
        Thread.sleep(3000);
        searchBox1.sendKeys("iPhone");
        searchBox1.submit();

        List<WebElement> iphones = driver.findElements(By.className("tUxRFH"));
        try {
            for (WebElement iPhone : iphones) {
                WebElement titleElement = iPhone.findElement(By.className("KzDlHZ"));
                String title = titleElement.getText();

                WebElement DiscountElement = iPhone.findElement(By.className("UkUFwK"));
                String Discounts = DiscountElement.getText();

                int discountPercentage = Integer.parseInt(Discounts.replaceAll("[^0-9]", ""));

                if (discountPercentage > 17) {
                    System.out.println("Title : " + title + "| Discount:" + Discounts);
                }
            }
        } catch (Exception e) {

        }

    }

    @Test
    public void TestCase03() throws InterruptedException {

        driver.get("https://www.flipkart.com/");
        WebElement searchBox2 = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//input[@title='Search for Products, Brands and More']")));
        searchBox2.clear();
        Thread.sleep(3000);
        searchBox2.sendKeys("Coffee Mug");
        searchBox2.submit();

        WebElement customerRatings = driver.findElement(By.xpath("(//div[@class='XqNaEv'])[1]"));
        customerRatings.click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("nt6sNV")));

        List<WebElement> coffeeMugs = driver.findElements(By.className("slAVV4"));

        List<Item> items = new ArrayList<>();

        for (WebElement coffeeMug : coffeeMugs) {
            try {
                WebElement titleElement = coffeeMug.findElement(By.className("wjcEIp"));
                String title = titleElement.getText();
                Thread.sleep(6000);
                System.out.println("print title :" + title);
                WebElement imageElement = coffeeMug.findElement(By.className("DByuf4"));
                String imageUrl = imageElement.getAttribute("src");
                Thread.sleep(4000);
                System.out.println("print Url: " + imageUrl);

                WebElement reviewsElement = coffeeMug.findElement(By.className("Wphh3N"));
                String reviewsText = reviewsElement.getText().split(" ")[0].replaceAll(",", "");
                int reviewsCount = Integer.parseInt(reviewsText);

                items.add(new Item(title, imageUrl, reviewsCount));
                System.out.println("add the reviews:" + items);

                if (String.valueOf(reviewsCount).length() == 5) {
                    items.add(new Item(title, imageUrl, reviewsCount));
                }
                System.out.println("Add the reviews: " + reviewsCount);
            } catch (Exception ignored) {
               
            }
        }
        for (Item item : items) {
            System.out.println("Title: " + item.title + ", Image URL: " + item.imageUrl);
        }

    }

    static class Item {
        String title;
        String imageUrl;
        int reviews;

        Item(String title, String imageUrl, int reviews) {
            this.title = title;
            this.imageUrl = imageUrl;
            this.reviews = reviews;
        }
    }

    @AfterSuite
    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }
}