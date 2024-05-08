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
import org.openqa.selenium.remote.SessionId;
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
    @Scheduled(fixedDelay = 18_000_000)
    public void startParse() {
        SessionId sessionId;
        counterForTEst++;
        ChromeDriver webDriver = null;

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
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    // check for close browser
                    if (webDriver != null) {
                        System.out.println("not null");
                        webDriver.quit();
                        webDriver = null;
                    }
                    webDriver = new ChromeDriver(getChromeOptions());
                    sessionId = webDriver.getSessionId();
                    totalErrorCount = 0;
                    countForRemoveLink = 0;
                    idLinkInWork = i;
                    webDriver.get(FunSunRestController.parsLinks.get(i));
                    sleep();
                    System.out.println("Array " + i);
                    try {
                        for (int j = 3; j < 15; j++) {
                            funSunParse(webDriver, j);
                        }
                    } catch (NoSuchSessionException ignore) {
                        System.out.println(126);
                    }
                    try {
                        if (webDriver.getSessionId() == sessionId) {
                            System.out.println("webDriver.quit()");
                            System.out.println("===========" + i);
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            System.out.println();
                            webDriver.manage().deleteAllCookies();
                            webDriver.quit();
                            webDriver = null;
                        } else {
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                            System.out.println("==========");
                        }
                    } catch (TimeoutException e) {
                        System.out.println(135);
                        e.printStackTrace();
                        System.out.println(("Page load Timeout Occured. Quiting !!! 184"));
                    } catch (NoSuchWindowException e) {
                        return;
                    }
                }

            }
        } catch (ConcurrentModificationException e) {
            System.out.println(142);
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
                arrayWithTab = null;
            } catch (NoSuchElementException | ElementClickInterceptedException e) {
                System.out.println(171);
                return;
            } catch (WebDriverException e) {
                return;
            }

            String rating;
            String price;
            String reviews;
            String flyDate;

            hotelName = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_text > div.hotelCard__description-header_text-name")).getText();
            location = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_text > div.hotelCard__description-header_text-location")).getText();

            flyDate = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-flight_price > div.hotelCard__description-flight_price-definition > div.hotelCard__description-flight_price-definition_information")).getText();

            if (flyDate.length() == 30) {
                this.flyDate = flyDate.substring(0, 5);
                this.countNight = flyDate.substring(22, 24);
            }
            if (flyDate.length() == 31) {
                this.flyDate = flyDate.substring(0, 6);
                this.countNight = flyDate.substring(23, 25);
            }


            try {
                reviews = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_icons > div.hotelCard__description-header_icons-text")).getText();
                rating = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-header > div.hotelCard__description-header_icons > div.hotelCard__description-header_icons-flex > div.hotelCard__description-header_icons-rating > span")).getText();
                price = webDriver.findElement(By.cssSelector("#app > div > div.v-main > div.container > div > div > div.search-content > div.search-content__cards.tour-search-content__result > div.search-cards-container > div:nth-child(" + hotel + ") > div.hotelCard__description > div.hotelCard__description-flight_price > div.hotelCard__description-flight_price-information > div")).getText();
                this.price = Integer.parseInt(price.replaceAll("[^\\d.]", ""));
                this.reviews = Integer.parseInt(reviews.replaceAll("[^\\d.]", ""));
                this.rating = Float.parseFloat(rating.replaceAll("[^\\d.]", ""));
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println(194);
                this.reviews = 0;
                this.rating = 0;
            }

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println(200);
            errorLog("NoSuchElementException", webDriver);
        } catch (ElementClickInterceptedException e) {
            System.out.println(203);
            errorLog("ClickInterceptedException", webDriver);
        } catch (JavascriptException exception) {
            System.out.println(206);
            errorLog("JavascriptException", webDriver);
        } catch (NoSuchWindowException e) {
            System.out.println(209);
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(210);
            e.printStackTrace();
            System.out.println(("Page load Timeout Occured. Quiting !!! 184"));
        }


        // no such element
        if (currentUrl != null) {
            if (this.price <= 220000 && this.rating >= 4) {
                workWithDataBase();
            }
        }

        hotelName = null;
        price = 0;
        location = null;
        flyDate = null;
        countNight = null;
        rating = 0;
        reviews = 0;
        currentUrl = null;
    }

    private boolean isEmpty(WebDriver webDriver, String cssString) {
        return webDriver.findElement(By.cssSelector(cssString)).getText().isEmpty();
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
        // update tour
        if (tourInfoRepo.findByURL(currentUrl).getTourPrice() != price) {
//            tourInfoRepo.findByURL(currentUrl).setDifferenceInPrice(tourInfoRepo.findByURL(currentUrl).getTourPrice() - price);
            System.out.println(tourInfoRepo.findByURL(currentUrl).getTourPrice());
            System.out.println(currentUrl);
            tourInfoRepo.updatePrice(tourInfoRepo.findByURL(currentUrl).getTourPrice() - price, currentUrl);
        }
        tourInfoEntity = null;
    }

    private void sleep() {
        int random = new Random().nextInt(35000) + 20000;
        try {
            Thread.sleep(random);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    // Error log to DB

    private void errorLog(String errorCode, WebDriver webDriver) {
        System.out.println(errorCode);
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

    // Chrome Options
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        // load page Strategy
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--blink-settings=imagesEnabled=false");
        options.addArguments("--incognito");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--Disable-extensions");
        return options;
    }

}
