package com.example.demo.job;

import com.example.demo.controllers.FunSunRestController;
import com.example.demo.entity.LogErrorCodeEntity;
import com.example.demo.entity.TourInfoEntity;
import com.example.demo.repository.TourInfoRepo;
import com.example.demo.service.LogErrorCodeService;
import com.example.demo.service.TourService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Random;

@Component
public class ParseTour {

    // Web driver
    private WebDriver webDriver;

    // error counter
    private int errorCount = 0;
    // if more than 10 error - pzdc
    private int totalErrorCount = 0;


    String hotelName = null;
    String location = null;
    String flyDate = null;
    String countNight = null;
    int reviews = 0;
    float rating = 0.0F;
    int price = 0;
    String currentUrl = null;

    // log error
    private final LogErrorCodeService logErrorCodeService;

    // Tour entity, repos, service
    private TourInfoEntity tourInfoEntity;
    private final TourInfoRepo tourInfoRepo;
    private final TourService tourService;

    public ParseTour(TourInfoRepo tourInfoRepo, TourService tourService, LogErrorCodeService logErrorCodeService) {
        this.tourInfoRepo = tourInfoRepo;
        this.tourService = tourService;
        this.logErrorCodeService = logErrorCodeService;
    }

    //    @Scheduled(fixedDelay = 25_000_000)
    @Scheduled(fixedDelay = 10_000)
    public void startParse() {
        String url = "https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-18&maxStartDate=2024-08-21&minNightsCount=10" +
                "&maxNightsCount=14&adults=2&flightTypes=charter&distanceSea=04093636-6324-4c8e-878b-a27871420711&stars=5&mealTypes=10004,8&regions=18535&sort=max&toursOnPage=60";

        ChromeOptions options = new ChromeOptions();

        // load page Strategy
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        // hide browser
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");

        Dimension dimension = new Dimension(1920, 1080);

        try {
            if (!FunSunRestController.parsLinks.isEmpty()) {
                for (String allLink : FunSunRestController.parsLinks) {
                    webDriver = new ChromeDriver(options);
                    webDriver.manage().window().setSize(dimension);
                    webDriver.get(allLink);
//                    removeBanner(webDriver);
                    sleep();
                    funSunParse(webDriver, 1);
                    funSunParse(webDriver, 2);
                    funSunParse(webDriver, 4);
                    funSunParse(webDriver, 5);
                    funSunParse(webDriver, 7);
                    funSunParse(webDriver, 8);
                    funSunParse(webDriver, 11);
//                    funSunParse(webDriver, 12);
//                    funSunParse(webDriver, 13);
//                    funSunParse(webDriver, 15);
//                    funSunParse(webDriver, 16);
                    System.out.println("done");
                    webDriver.quit();
                }
            }
        } catch (ConcurrentModificationException e) {
            errorLog("Mod array");
            return;
        }

        errorCount = 0;
        totalErrorCount = 0;
        System.out.println("DONE");
    }

    private void funSunParse(WebDriver webDriver, int hotel) {
        try {
            // click to tour card
            sleep();
            webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_text > div.hotelCard__description-header_text-name")).click();
            sleep();
            // create array with tabs
            ArrayList<String> tab = new ArrayList<>(webDriver.getWindowHandles());
            // switch to new tab
            webDriver.switchTo().window(tab.get(1));

            String rating;
            String price;
            String reviews;

            hotelName = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-right > div.hotel__description-title > h1")).getText();
            location = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-right > div.hotel__description-location > p")).getText();
            flyDate = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div:nth-child(5) > div > div.hotel__hotels-filtersWrapper > div.v-hotel-wrapper > div > div > div.hotel__hotels-dates > div.hotel__hotels-date.active.hotel__hotels-date-active > span")).getText();
            countNight = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div:nth-child(5) > div > div.hotel__hotels-filtersWrapper > div.hotel__hotels-sliders > div.hotel__hotels-nights-meals > div.hotel__hotels-meals-wrapper > div > div")).getText();
            reviews = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-photos > div.hotel__description-photos__1 > div.hotel__description-reviews > div > div")).getText();
            rating = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-photos > div.hotel__description-photos__1 > div.hotel__description-reviews > h5")).getText();
            price = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-right > div.hotel__description-price > div.hotel__description-price-numbers > h2\n")).getText();

            try {
                this.price = Integer.parseInt(price.replaceAll("[^\\d.]", ""));
                this.reviews = Integer.parseInt(reviews.replaceAll("[^\\d.]", ""));
                this.rating = Float.parseFloat(rating.replaceAll("[^\\d.]", ""));
            } catch (NumberFormatException e) {
                errorLog("NumberFormatException");
                parseErrorRepeat(webDriver, hotel);
            }


            // get url
            currentUrl = webDriver.getCurrentUrl();
            // close new tab
            webDriver.close();
            // switch to old tab
            webDriver.switchTo().window(tab.get(0));

        } catch (org.openqa.selenium.NoSuchElementException e) {
            errorLog("NoSuchElementException");
            parseErrorRepeat(webDriver, hotel);
        } catch (ElementClickInterceptedException e) {
            errorLog("ClickInterceptedException");
            parseErrorRepeat(webDriver, hotel);
        }

        workWithDataBase();
    }

    // repeat method if error
    private void parseErrorRepeat(WebDriver webDriver, int hotel) {
        // TODO remove link
        webDriver.navigate().refresh();
        if (errorCount <= 3) {
            errorCount++;
            funSunParse(webDriver, hotel);
        }
    }

    private void removeBanner(WebDriver webDriver) {
        sleep();
        if (errorCount <= 3) {
            errorCount++;
            try {
                webDriver.findElement(By.cssSelector("#popmechanic-form-79921 > div.popmechanic-main > div.popmechanic-content > div.popmechanic-block.popmechanic-text-block > div.popmechanic-inputs > a")).click();
            } catch (NoSuchElementException e) {
                errorLog("Banner error");
            }
        }
    }


    private void workWithDataBase() {
        // if there are no data, save to database

        // TODO error


        if (tourInfoRepo.findAll().isEmpty()) {
            tourInfoEntity = new TourInfoEntity(hotelName, price, location, flyDate, countNight, rating, reviews, currentUrl, 0);
            tourService.saveTour(tourInfoEntity);
        }

        if (tourInfoRepo.findByURL(currentUrl) == null) {
            tourInfoEntity = new TourInfoEntity(hotelName, price, location, flyDate, countNight, rating, reviews, currentUrl, 0);
            tourService.saveTour(tourInfoEntity);
        }

        tourInfoRepo.findByURL(currentUrl).setDifferenceInPrice(tourInfoRepo.findByURL(currentUrl).getTourPrice() - price);

        errorCount = 0;
    }

    private void sleep() {
        System.out.println("sleep start");
        int random = new Random().nextInt(30000) + 20000;
        try {
            Thread.sleep(random);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("sleep stop");
    }

    // Error log to DB
    private void errorLog(String errorCode) {
        System.out.println(errorCode);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        LogErrorCodeEntity logErrorCodeEntity = new LogErrorCodeEntity(errorCode, dateFormat.format(date), "Fun Sun");
        logErrorCodeService.saveErrorLog(logErrorCodeEntity);

        if (totalErrorCount >= 10) {
            totalErrorCount = 0;
            errorLog("Pizdec");
            webDriver.quit();
            return;
        }

        totalErrorCount++;
    }

}
