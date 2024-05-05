package com.example.demo.job;

import com.example.demo.controllers.FunSunRestController;
import com.example.demo.entity.LogErrorCodeEntity;
import com.example.demo.entity.TourInfoEntity;
import com.example.demo.repository.TourInfoRepo;
import com.example.demo.service.LogErrorCodeService;
import com.example.demo.service.TourService;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ParseTour {

    // log error
    private final LogErrorCodeService logErrorCodeService;
    private final TourInfoRepo tourInfoRepo;
    private final TourService tourService;
    // counter to remove link
    int idLinkInWork;
    int countForRemoveLink;

    String hotelName = null;
    String location = null;
    String flyDate = null;
    String countNight = null;
    int reviews = 0;
    float rating = 0.0F;
    int price = 0;
    String currentUrl = null;

    // if more than 10 error - pzdc
    private int totalErrorCount;
    // Tour entity, repos, service
    private TourInfoEntity tourInfoEntity;

    public ParseTour(TourInfoRepo tourInfoRepo, TourService tourService, LogErrorCodeService logErrorCodeService) {
        this.tourInfoRepo = tourInfoRepo;
        this.tourService = tourService;
        this.logErrorCodeService = logErrorCodeService;
    }

    int counterForTEst = 0;

    //    @Scheduled(fixedDelay = 25_000_000)
    @Scheduled(fixedDelay = 10_000)
    public void startParse() {
        counterForTEst++;
        ChromeOptions options = new ChromeOptions();

        // load page Strategy
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        // hide browser
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--blink-settings=imagesEnabled=false");
        options.addArguments("--incognito");
        options.addArguments("--disable-gpu");
        WebDriver webDriver = null;

        if (counterForTEst == 1) {
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-01&maxStartDate=2024-08-03&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-04&maxStartDate=2024-08-06&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-07&maxStartDate=2024-08-07&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-08&maxStartDate=2024-08-08&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-09&maxStartDate=2024-08-11&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");

            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-13&maxStartDate=2024-08-14&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-15&maxStartDate=2024-08-15&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-16&maxStartDate=2024-08-17&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-18&maxStartDate=2024-08-18&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-19&maxStartDate=2024-08-20&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-21&maxStartDate=2024-08-23&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
            FunSunRestController.parsLinks.add("https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-24&maxStartDate=2024-08-25&minNightsCount=10&maxNightsCount=14&adults=2&flightTypes=charter&sort=max&mealTypes=10004,8&distanceSea=04093636-6324-4c8e-878b-a27871420711&regions=18535&cities=117164,21094,122878,117154,117163&stars=4,5");
        }

        // TODO check response status

        // TODO how check if selenium webdriver is open
        try {
            if (!FunSunRestController.parsLinks.isEmpty()) {
                for (int i = 0; i < FunSunRestController.parsLinks.size(); i++) {
                    webDriver = new ChromeDriver(options);
                    totalErrorCount = 0;
                    countForRemoveLink = 0;
                    idLinkInWork = i;
                    webDriver.get(FunSunRestController.parsLinks.get(i));
                    sleep();

                    try {
                        for (int j = 1; j < 15; j++) {
                            funSunParse(webDriver, j);
                        }
                    } catch (NoSuchSessionException ignore) {
                    }

                    try {
                        System.out.println("webDriver.quit() Array = " + i);
                        webDriver.manage().deleteAllCookies();
                        webDriver.quit();
                    } catch (TimeoutException e) {
                        System.out.println("timeout 108");
                        webDriver.navigate().refresh();
                        System.out.println("timeout 108");
                    }
                }

            }
        } catch (ConcurrentModificationException e) {
            errorLog("Mod array", webDriver);
            return;
        }

        System.out.println("DONE");
        System.out.println("counterForTEst ======================== " + counterForTEst);
//        как удадтьб webDriver
    }

    private void funSunParse(WebDriver webDriver, int hotel) {
        try {
            try {
                // scroll to element
                WebElement button = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-flight_price > div.hotelCard__description-flight_price-information > div"));
                Actions actions = new Actions(webDriver);
                actions.moveToElement(button);
                actions.perform();
                // get url
                webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_text > div.hotelCard__description-header_text-name")).click();
                List<String> arrayWithTab = new ArrayList<>(webDriver.getWindowHandles());
                webDriver.switchTo().window(arrayWithTab.get(1));
                currentUrl = webDriver.getCurrentUrl();
                webDriver.close();
                webDriver.switchTo().window(arrayWithTab.get(0));
                arrayWithTab.clear();
            } catch (NoSuchElementException | ElementClickInterceptedException e) {
                return;
            }

            String rating;
            String price;
            String reviews;

            hotelName = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_text > div.hotelCard__description-header_text-name")).getText();
            location = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_text > div.hotelCard__description-header_text-location")).getText();
            flyDate = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-flight_price > div.hotelCard__description-flight_price-definition > div.hotelCard__description-flight_price-definition_information")).getText();
            countNight = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-flight_price > div.hotelCard__description-flight_price-definition > div.hotelCard__description-flight_price-definition_information")).getText();


            try {
                reviews = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_icons > div.hotelCard__description-header_icons-text")).getText();
                rating = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_icons > div.hotelCard__description-header_icons-flex > div.hotelCard__description-header_icons-rating > span")).getText();
                price = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-flight_price > div.hotelCard__description-flight_price-information > div")).getText();
                this.price = Integer.parseInt(price.replaceAll("[^\\d.]", ""));
                this.reviews = Integer.parseInt(reviews.replaceAll("[^\\d.]", ""));
                this.rating = Float.parseFloat(rating.replaceAll("[^\\d.]", ""));
            } catch (NumberFormatException | NoSuchElementException e) {
                this.reviews = 0;
                this.rating = 0;
            }

        } catch (org.openqa.selenium.NoSuchElementException e) {
            errorLog("NoSuchElementException", webDriver);
        } catch (ElementClickInterceptedException e) {
            errorLog("ClickInterceptedException", webDriver);
        } catch (JavascriptException exception) {
            errorLog("JavascriptException", webDriver);
        } catch (TimeoutException e) {
            System.out.println("timeout 169");
            webDriver.navigate().refresh();
            System.out.println("timeout 169");
        } catch (NoSuchWindowException ignore) {

        }
        // no such element
        if (currentUrl != null) {
            if (this.price <= 220000 && this.rating >= 4) {

                System.out.print(hotelName);
                System.out.println(" " + this.price);

                workWithDataBase();
            }
        }
    }

    private void workWithDataBase() {
        // if there are no data, save to database
        if (tourInfoRepo.findAll().isEmpty()) {
            tourInfoEntity = new TourInfoEntity(hotelName, price, location, flyDate, countNight, rating, reviews, currentUrl, 0);
            tourService.saveTour(tourInfoEntity);
        }
        // save if we don't have tour
        if (tourInfoRepo.findByURL(currentUrl) == null) {
            tourInfoEntity = new TourInfoEntity(hotelName, price, location, flyDate, countNight, rating, reviews, currentUrl, 0);
            tourService.saveTour(tourInfoEntity);
        }
        // update tour price
        if (tourInfoRepo.findByURL(currentUrl).getTourPrice() != price) {
            tourInfoRepo.findByURL(currentUrl).setDifferenceInPrice(tourInfoRepo.findByURL(currentUrl).getTourPrice() - price);
        }
    }

    private void sleep() {
        int random = new Random().nextInt(6000) + 3000;
        try {
            Thread.sleep(random);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Error log to DB
    private void errorLog(String errorCode, WebDriver webDriver) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();

        if (totalErrorCount == 20) {
            totalErrorCount = 0;
            LogErrorCodeEntity logErrorCodeEntity = new LogErrorCodeEntity("PZDC", dateFormat.format(date), "Fun Sun");
            logErrorCodeService.saveErrorLog(logErrorCodeEntity);
            webDriver.quit();
            return;
        }

        if (countForRemoveLink == 15) {
            countForRemoveLink = 0;
            System.out.println("more than 25 error");
//            FunSunRestController.parsLinks.remove(idLinkInWork);
            LogErrorCodeEntity logErrorCodeEntity = new LogErrorCodeEntity("incorrect link", dateFormat.format(date), "Fun Sun");
            logErrorCodeService.saveErrorLog(logErrorCodeEntity);
            return;
        }

        LogErrorCodeEntity logErrorCodeEntity = new LogErrorCodeEntity(errorCode, dateFormat.format(date), "Fun Sun");
        logErrorCodeService.saveErrorLog(logErrorCodeEntity);

        countForRemoveLink++;
        totalErrorCount++;

    }

}
