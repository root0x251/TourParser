package com.example.demo.job;

import com.example.demo.entity.LogErrorCodeEntity;
import com.example.demo.entity.ParseLinkEntity;
import com.example.demo.entity.TourInfoEntity;
import com.example.demo.repository.TourInfoRepo;
import com.example.demo.service.LogErrorCodeService;
import com.example.demo.service.ParseLinkService;
import com.example.demo.service.TourService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Component
public class ParseTour {
    // error counter
    private int errorCount = 0;
    // if
    private int totalErrorCount = 0;

    private int slipCounter = 0;

    String hotelName = null;
    String currentUrl = null;
    int price;

    // log error
    private final LogErrorCodeService logErrorCodeService;
    // Tour entity, repos, service
    private TourInfoEntity tourInfoEntity;
    private final TourInfoRepo tourInfoRepo;
    private final TourService tourService;
    private final ParseLinkService parseLinkService;

    public ParseTour(LogErrorCodeService logErrorCodeService, TourInfoRepo tourInfoRepo, TourService tourService, ParseLinkService parseLinkService) {
        this.logErrorCodeService = logErrorCodeService;
        this.tourInfoRepo = tourInfoRepo;
        this.tourService = tourService;
        this.parseLinkService = parseLinkService;
    }

    //    @Scheduled(fixedDelay = 25_000_000)
    @Scheduled(fixedDelay = 3_600_000)
    public void startParse() {
        System.out.println("================== start Scheduled ==================");
        System.out.println("================== start ChromeOptions ==================");
        ChromeOptions options = getChromeOptions();

        System.out.println("================== start sleep ==================");
        sleep();

        System.out.println("================== start WebDriverManager ==================");
//        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        System.out.println("================== Loop get link from DB================== ");
        for (ParseLinkEntity link : parseLinkService.findAll()) {
            System.out.println("================== start WebDriver ================== ");
            try {
                WebDriver webDriver = new ChromeDriver(options);
//                WebDriver webDriver = new ChromeDriver(options);
                System.out.println("================== start sleep ==================");
                sleep();
                System.out.println("================== start get link ==================");
                webDriver.get(link.getLink());
                System.out.println("================== start sleep ==================");
                sleep(webDriver);
                System.out.println("================== start parser ==================");
                funSunParse(webDriver);
                System.out.println("================== done with Link ==================");
            } catch (SessionNotCreatedException e) {
                errorLog("SessionNotCreatedException");
            }
        }

//        for (String link : FunSunRestController.allLinks) {
//        }

        System.out.println("===========Stop execution===========");
        slipCounter = 0;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");                // Включаем headless режим
        options.addArguments("--incognito");               // Включаем инкогнито
        options.addArguments("--disable-extensions");      // Отключаем расширения
        options.addArguments("--disable-gpu");             // Отключаем GPU
        options.addArguments("--no-sandbox");              // Отключаем песочницу
//        options.addArguments("--disable-dev-shm-usage");   // Отключаем /dev/shm для сервера
//        options.addArguments("--window-size=1920,1080");   // Устанавливаем размер окна браузера
//        options.addArguments("--disable-blink-features=AutomationControlled"); // Отключаем обнаружение Selenium

        //        // Меняем User-Agent на стандартный пользовательский
//        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
//
//        // Отключение автоматической идентификации Selenium через переменные
//        Map<String, Object> prefs = new HashMap<>();
//        prefs.put("credentials_enable_service", false);
//        prefs.put("profile.password_manager_enabled", false);
//        options.setExperimentalOption("prefs", prefs);
//
//        // Отключаем автоматизацию на уровне JS для более сложных проверок
//        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("useAutomationExtension", false);

        return options;
    }

    private void funSunParse(WebDriver webDriver) {
        System.out.println("================== Try get info ==================");
        try {
            hotelName = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div:nth-child(2) > div.hotelInfoHead > div.flex > div:nth-child(1) > h1")).getText();
            String price = webDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div:nth-child(2) > div.hotel-info-box > div:nth-child(2) > div > div.pay > div.pay__price > div")).getText();
            this.price = Integer.parseInt(price.replaceAll("[^\\d.]", ""));
            currentUrl = webDriver.getCurrentUrl();
            // work with DataBase
            System.out.println("================== Start work with DB ==================");
            workWithDataBase();

        } catch (NoSuchElementException exception) {
            errorLog("NoSuchElementException");
            closeBrowser(webDriver);
        } catch (NumberFormatException exception) {
            errorLog("NoSuchElementException");
            closeBrowser(webDriver);
        } catch (TimeoutException exception) {
            errorLog("TimeoutException");
        }
    }

    private void closeBrowser(WebDriver webDriver) {
        webDriver.quit();
    }

    private void workWithDataBase() {
        // if there are no data, save to database
        if (tourInfoRepo.findByHotelName(hotelName) == null) {
            tourInfoEntity = new TourInfoEntity(hotelName, price, price, currentUrl, 0);
            tourService.saveTour(tourInfoEntity);
        }
        // update data in DB
        if (!Objects.equals(tourInfoRepo.findByHotelName(hotelName).getTourPrice(), price)) {
            // difference in price
            int priceDifference = tourInfoRepo.findByHotelName(hotelName).getTourPrice() - price;
            TourInfoEntity tourInfoEntity = tourInfoRepo.findByHotelName(hotelName);
            tourInfoEntity.setOldTourPrice(tourInfoEntity.getTourPrice());
            tourInfoEntity.setTourPrice(price);
            tourInfoEntity.setDifferenceInPrice(priceDifference);
            tourService.updateTour(tourInfoEntity);
        }

        System.out.println("================== End work with DB ==================");

    }

    private void sleep(WebDriver webDriver) {
        slipCounter++;
        int random;
        try {
            if (slipCounter <= 1) {
                random = new Random().nextInt(2000) + 5000;
                Thread.sleep(random);
            } else {
                random = new Random().nextInt(4000) + 8000;
                Thread.sleep(random);
                ((JavascriptExecutor) webDriver).executeScript("window.stop();");
            }
        } catch (InterruptedException e) {
            errorLog("Thread sleep exception");
        }
    }

    private void sleep() {
        try {
            Thread.sleep(new Random().nextInt(2000) + 5000);
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

        System.out.println("!!!!!!!!!!!!!!!!!!!!!   " + errorCode);

        totalErrorCount++;
    }

}
