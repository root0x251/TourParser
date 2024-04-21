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
import java.util.NoSuchElementException;
import java.util.*;

@Component
public class ParseTour {

    // array with browser tabs
    private ArrayList<String> switchTabs;

    // error counter
    private int errorCount = 0;
    // if
    private int totalErrorCount = 0;

    private int slipCounter = 0;

    String hotelName = null;
    String about = null;
    String aboutMore = null;
    String beach = null;
    String currentUrl = null;
    int price;
    int countReviews;
    float reviews;

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
        String url = "https://fstravel.com/searchtour/country/africa/egypt?departureCityId=244707&arrivalCountryId=18498&minStartDate=2024-08-22" +
                "&maxStartDate=2024-08-22&minNightsCount=12&maxNightsCount=15&adults=2&flightTypes=charter&sort=max&stars=5,4&mealTypes=10004,10002,8";

        ChromeOptions options = new ChromeOptions();

        // load page Strategy
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        // hide browser
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");

        WebDriver webDriver = new ChromeDriver(options);



// ====================
        if (FunSunRestController.allLinks.isEmpty()) {
            FunSunRestController.allLinks.add(url);
        }

        for (String allLink : FunSunRestController.allLinks) {
            System.out.println(FunSunRestController.allLinks.size());
        }
// =======================


        webDriver.get(url);

        sleep(webDriver);
        funSunParse(webDriver, 3);
        funSunParse(webDriver, 4);
        funSunParse(webDriver, 6);
        funSunParse(webDriver, 7);
        funSunParse(webDriver, 9);
        funSunParse(webDriver, 10);
        funSunParse(webDriver, 12);
        funSunParse(webDriver, 13);
        funSunParse(webDriver, 15);
        funSunParse(webDriver, 16);
        System.out.println("done");

        webDriver.quit();
        slipCounter = 0;
        System.out.println("DONE");
    }

    private void funSunParse(WebDriver webDriver, int hotel) {
        try {
            System.out.println(2);
//            sleep(webDriver);
            webDriver.findElement(By.xpath("//*[@id=\"searchResult\"]/div[2]/div/div[" + hotel + "]/div/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/p/a")).click();
            switchTabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(switchTabs.get(1));
            sleep(webDriver);

            hotelName = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-right > div.hotel__description-title > h1")).getText();
            about = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-bottom > div.hotel__description-description > p")).getText();

            try {
                // remove unnecessary (from price) and convert to int
                String price = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-right > div.hotel__description-price > div.hotel__description-price-numbers > h2")).getText();
                this.price = Integer.parseInt(price.replaceAll("[^\\d.]", ""));
                // remove unnecessary elements (from count reviews)
                String countReviews = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-photos > div.hotel__description-photos__1 > div.hotel__description-reviews > div > div")).getText();
                this.countReviews = Integer.parseInt(countReviews.replaceAll("[^\\d.]", ""));
                reviews = Float.parseFloat(webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-photos > div.hotel__description-photos__1 > div.hotel__description-reviews > h5")).getText());
            } catch (NumberFormatException e) {
                reviews = 0.11F;
            } catch (NoSuchElementException exception) {
                // page not load
                closeBrowserTab(webDriver, hotel);
            }

            // more info about hotel
            webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-bottom > div.hotel__description-description > div")).click();
            aboutMore = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.about-hotel-popup > div > div:nth-child(2)")).getText();
            // more about beach
            webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.about-hotel-popup > div > div.about-hotel-popup__menu > div:nth-child(6)")).click();
            beach = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.about-hotel-popup > div > div:nth-child(7)")).getText();

            currentUrl = webDriver.getCurrentUrl();

            // work with DataBase
            workWithDataBase();

        } catch (NoSuchElementException exception) {
            if (errorCount <= 2) {
                // if error (pop up banner/DDOS)
                errorLog("NoSuchElementException (Pop up)");
                closeBrowserTab(webDriver, hotel);
            }
        } catch (ElementClickInterceptedException exception) {
            // if error (pop up banner/DDOS)
            errorLog("ElementClickInterceptedException (DDOS/Pop up)");
            closeBrowserTab(webDriver, hotel);
        } finally {
            // close tab tour, switch to first tab
            try {
                if (switchTabs.size() == 2) {
                    webDriver.close();
                    switchTabs.remove(1);
                    errorCount = 0;
                }
            } catch (NullPointerException exception) {
                errorLog("Null point exp (array)");
                System.exit(0);
            }
            // switch to first tab
            webDriver.switchTo().window(switchTabs.get(0));
        }
    }

    private void closeBrowserTab(WebDriver webDriver, int hotel) {
        webDriver.switchTo().window(switchTabs.get(1));
        webDriver.close();
        webDriver.switchTo().window(switchTabs.get(0));
        switchTabs.remove(1);
        errorCount++;
        funSunParse(webDriver, hotel);
    }

    private void workWithDataBase() {
        // if there are no data, save to database
        if (tourInfoRepo.findByHotelName(hotelName) == null) {
            tourInfoEntity = new TourInfoEntity(hotelName, price, about, aboutMore, beach, reviews, countReviews, currentUrl, 0);
            tourService.saveTour(tourInfoEntity);
        }
        // update data in DB
        if (!Objects.equals(tourInfoRepo.findByHotelName(hotelName).getTourPrice(), price)) {
            // difference in price
            int priceDifference = tourInfoEntity.getTourPrice() - price;
            tourService.updateTour(new TourInfoEntity(hotelName, price, about, aboutMore, beach, reviews, countReviews, currentUrl, priceDifference));
        }

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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        LogErrorCodeEntity logErrorCodeEntity = new LogErrorCodeEntity(errorCode, dateFormat.format(date), "Fun Sun");
        logErrorCodeService.saveErrorLog(logErrorCodeEntity);

        if (totalErrorCount >= 10) {
            totalErrorCount = 0;
            errorLog("Pizdec");
            System.exit(0);
        }

        totalErrorCount++;
    }

}
