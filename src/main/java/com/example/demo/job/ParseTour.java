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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ParseTour {

    // Web driver
    private WebDriver webDriver;

    // error counter
    private int errorCount = 0;
    // if more than 10 error - pzdc
    private int totalErrorCount = 0;

    private int slipCounter = 0;

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
            if (!FunSunRestController.allLinks.isEmpty()) {
                for (String allLink : FunSunRestController.allLinks) {
                    webDriver = new ChromeDriver(options);
                    webDriver.manage().window().setSize(dimension);
                    webDriver.get(allLink);
                    removeBanner(webDriver);
                    funSunParse(webDriver, 3);
                    funSunParse(webDriver, 4);
//                    funSunParse(webDriver, 6);
//                    funSunParse(webDriver, 7);
//                    funSunParse(webDriver, 9);
//                    funSunParse(webDriver, 10);
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

        slipCounter = 0;
        errorCount = 0;
        totalErrorCount = 0;
        System.out.println("DONE");
    }

    private void funSunParse(WebDriver webDriver, int hotel) {
        sleep(webDriver);
        try {
            String rating;
            String price;
            String reviews;

            hotelName = webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(1) > div.flex.space-between.line-height > div.hotel-card__about-stars-location > div:nth-child(2) > p > a")).getText();
            location = webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(1) > div.flex.space-between.line-height > div.hotel-card__about-stars-location > div.hotel-card__about-location")).getText();
            flyDate = webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(3) > div:nth-child(2) > div > div > div.hotel-card__about-fly-date")).getText();
            countNight = webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(3) > div:nth-child(2) > div > div > div.hotel-card__about-fly-details")).getText();
            reviews = webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(1) > div.flex.space-between.line-height > div.hotel-card__about-rating-reviews.flex.line-height > div.hotel-card__about-number-of-reviews")).getText();
            rating = webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(1) > div.flex.space-between.line-height > div.hotel-card__about-rating-reviews.flex.line-height > div.flex > div.hotel-card__about-rating")).getText();
            price = webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(3) > div:nth-child(2) > div > a")).getText();

            try {
                this.price = Integer.parseInt(price.replaceAll("[^\\d.]", ""));
                this.reviews = Integer.parseInt(reviews.replaceAll("[^\\d.]", ""));
                this.rating = Float.parseFloat(rating.replaceAll("[^\\d.]", ""));
            } catch (NumberFormatException e) {
                errorLog("NumberFormatException");
                parseErrorRepeat(webDriver, hotel);
            }

            // click to know tour url
            webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(1) > div.flex.space-between.line-height > div.hotel-card__about-stars-location > div:nth-child(2) > p > a")).click();
            // create array with tabs
            ArrayList<String> tab = new ArrayList<>(webDriver.getWindowHandles());
            // switch to new tab
            webDriver.switchTo().window(tab.get(1));
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
        webDriver.navigate().refresh();
        if (errorCount <= 3) {
            funSunParse(webDriver, hotel);
        }
        errorCount++;
    }

    private void removeBanner(WebDriver webDriver) {
        sleep(webDriver);
        if (errorCount <= 3) {
            try {
                webDriver.findElement(By.cssSelector("#popmechanic-form-79921 > div.popmechanic-main > div.popmechanic-content > div.popmechanic-block.popmechanic-text-block > div.popmechanic-inputs > a")).click();
            } catch (NoSuchElementException e) {
                errorLog("Banner error");
            }
        }
        errorCount++;
    }


    private void workWithDataBase() {
        // if there are no data, save to database

    // TODO error

        if (!Objects.equals(tourInfoRepo.findByHotelName(hotelName).getHotelName(), hotelName) || !Objects.equals(tourInfoRepo.findByHotelName(hotelName).getFlyDate(), flyDate)) {
            tourInfoEntity = new TourInfoEntity(hotelName, price, location, flyDate, countNight, rating, reviews, currentUrl, 0);
            tourService.saveTour(tourInfoEntity);
        }
        // update data in DB
        if (!Objects.equals(tourInfoRepo.findByHotelName(hotelName).getTourPrice(), price) && !Objects.equals(tourInfoRepo.findByHotelName(hotelName).getFlyDate(), flyDate)) {
            // difference in price
            int priceDifference = tourInfoEntity.getTourPrice() - price;
            tourService.updateTour(new TourInfoEntity(hotelName, price, location, flyDate, countNight, rating, reviews, currentUrl, priceDifference));
        }

        errorCount = 0;
    }

    private void sleep(WebDriver webDriver) {
        slipCounter++;
        int random;
        try {
            if (slipCounter <= 1) {
                random = new Random().nextInt(25000) + 15000;
                Thread.sleep(random);
            } else {
                random = new Random().nextInt(10000) + 8000;
                Thread.sleep(random);
                ((JavascriptExecutor) webDriver).executeScript("window.stop();");
            }
        } catch (InterruptedException e) {
            errorLog("Thread sleep exception");
        }
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
