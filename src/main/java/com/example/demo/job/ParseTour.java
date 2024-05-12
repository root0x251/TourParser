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
    int flyDate = 0;
    int countNight = 0;
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
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
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
                        e.printStackTrace();
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
        List<String> arrayWithTab = null;
        try {
            // scroll to element
            WebElement button = webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(3) > div:nth-child(3) > div > div > div > div.hotel-card__payment-methods-item.sber > div.hotel-card__payment-methods-item-text"));
            Actions actions = new Actions(webDriver);
            actions.moveToElement(button);
            actions.perform();
            // go to hotel
            webDriver.findElement(By.cssSelector("#searchResult > div.tour-search-content__wrap-result > div > div:nth-child(" + hotel + ") > div > div.flex > div.hotel-card__about.flex.line-height > div:nth-child(1) > div.flex.space-between.line-height > div.hotel-card__about-stars-location > div:nth-child(2) > p > a")).click();
            //create array with tab
            sleep();
            arrayWithTab = new ArrayList<>(webDriver.getWindowHandles());
            // switch to second tab
            webDriver.switchTo().window(arrayWithTab.get(1));
            // get url
            currentUrl = webDriver.getCurrentUrl();

            String rating;
            String price;
            String reviews;
            String flyDate;
            String countNight;

            hotelName = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-right > div.hotel__description-title > h1")).getText();
            location = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-right > div.hotel__description-location > p")).getText();

            button = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div:nth-child(5) > div > h2"));
            actions = new Actions(webDriver);
            actions.moveToElement(button);



            try {
                reviews = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-photos > div.hotel__description-photos__1 > div.hotel__description-reviews > div > div")).getText();
                rating = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.hotel__description.container > div.hotel__description-wrapper > div.hotel__description-photos > div.hotel__description-photos__1 > div.hotel__description-reviews > h5")).getText();
                price = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div:nth-child(5) > div > div.hotel__hotels-filtersWrapper > div.v-hotel-wrapper > div > div > div.hotel__hotels-dates > div.hotel__hotels-date.active.hotel__hotels-date-active > h5.hotel__hotels-date-price")).getText();
                flyDate = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div:nth-child(5) > div > div.hotel__hotels-filtersWrapper > div.v-hotel-wrapper > div > div > div.hotel__hotels-dates > div.hotel__hotels-date.active.hotel__hotels-date-active > span")).getText();
                countNight = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div:nth-child(5) > div > div.hotel__hotels-filtersWrapper > div.hotel__hotels-sliders > div.hotel__hotels-nights-meals > div.hotel__hotels-meals-wrapper > div > div.hotel__hotels-meal.active")).getText();

                this.flyDate = Integer.parseInt(flyDate.replaceAll("[^\\d.]", ""));
                this.countNight = Integer.parseInt(countNight.replaceAll("[^\\d.]", ""));
                this.price = Integer.parseInt(price.replaceAll("[^\\d.]", ""));
                this.reviews = Integer.parseInt(reviews.replaceAll("[^\\d.]", ""));
                this.rating = Float.parseFloat(rating.replaceAll("[^\\d.]", ""));

            } catch (NumberFormatException | NoSuchElementException e) {
                this.reviews = 0;
                this.rating = 0;
            }


            webDriver.close();
            webDriver.switchTo().window(arrayWithTab.get(0));
            arrayWithTab.clear();


        } catch (org.openqa.selenium.NoSuchElementException e) {
            errorLog("NoSuchElementException", webDriver);
            if (arrayWithTab != null) {
                if (arrayWithTab.size() > 2) {
                    webDriver.close();
                    webDriver.switchTo().window(arrayWithTab.get(0));
                    arrayWithTab.clear();
                }
            }
            return;
        } catch (ElementClickInterceptedException e) {
            errorLog("ClickInterceptedException", webDriver);
            if (arrayWithTab != null) {
                if (arrayWithTab.size() > 2) {
                    webDriver.close();
                    webDriver.switchTo().window(arrayWithTab.get(0));
                    arrayWithTab.clear();
                }
            }
            return;
        } catch (JavascriptException exception) {
            errorLog("JavascriptException", webDriver);
            if (arrayWithTab != null) {
                if (arrayWithTab.size() > 2) {
                    webDriver.close();
                    webDriver.switchTo().window(arrayWithTab.get(0));
                    arrayWithTab.clear();
                }
            }
            return;
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (NoSuchWindowException e) {
            e.printStackTrace();
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
            tourInfoRepo.updateHotelPrice(price, tourInfoRepo.findByURL(currentUrl).getTourPrice() - price, currentUrl);
        }
    }

    private void sleep() {
        int random = new Random().nextInt(15000) + 10000;
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
