package com.example.demo.job;

import com.example.demo.entity.FunSunSearchBySelectorEntity;
import com.example.demo.entity.LogErrorCodeEntity;
import com.example.demo.entity.ParseLinkEntity;
import com.example.demo.entity.TourInfoEntity;
import com.example.demo.repository.FunSunSelectorRepo;
import com.example.demo.repository.TourInfoRepo;
import com.example.demo.service.FunSunSelectorService;
import com.example.demo.service.LogErrorCodeService;
import com.example.demo.service.ParseLinkService;
import com.example.demo.service.TourService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ParseTour {

    private boolean shouldRun = true;
    private int totalErrorCount = 0;

    private String hotelName = null;
    private String currentUrl = null;
    private int price;

    private FunSunSearchBySelectorEntity funSunSearchBySelectorEntity;

    // log error
    private final LogErrorCodeService logErrorCodeService;
    private final TourInfoRepo tourInfoRepo;
    private final TourService tourService;
    private final ParseLinkService parseLinkService;
    private final FunSunSelectorService funSunSelectorService;
    private final FunSunSelectorRepo funSunSelectorRepo;

    public ParseTour(LogErrorCodeService logErrorCodeService, TourInfoRepo tourInfoRepo, TourService tourService, ParseLinkService parseLinkService, FunSunSelectorService funSunSelectorService,
                     FunSunSelectorRepo funSunSelectorRepo) {
        this.logErrorCodeService = logErrorCodeService;
        this.tourInfoRepo = tourInfoRepo;
        this.tourService = tourService;
        this.parseLinkService = parseLinkService;
        this.funSunSelectorService = funSunSelectorService;
        this.funSunSelectorRepo = funSunSelectorRepo;
    }

    //one-hour delay
    @Scheduled(fixedDelay = 3_600_000)
    public void startParse() {

        funSunSearchBySelectorEntity = funSunSelectorRepo.findByForWhichSite("funsun");
        if (funSunSearchBySelectorEntity == null) {
            return;
        }

        shouldRun = true;
        System.out.println("================== start Scheduled ==================");
        System.out.println("================== start ChromeOptions ==================");
        ChromeOptions options = getChromeOptions();

        System.out.println("================== start sleep ==================");
        sleep();
        System.out.println("================== Loop get link from DB================== ");
        for (ParseLinkEntity parseLinkEntity : parseLinkService.findAll()) {
            System.out.println("================== start WebDriverManager ==================");
            WebDriverManager.chromedriver().setup();
            WebDriver webDriver = null;
            if (!shouldRun) {
                return;
            }
            System.out.println("================== start WebDriver ================== ");
            try {
                webDriver = new ChromeDriver(options);
                System.out.println("================== start sleep ==================");
                sleep();
                System.out.println("================== start get link ==================");
                webDriver.get(parseLinkEntity.getLink());
                System.out.println("================== start sleep ==================");
                sleep();
                System.out.println("================== start parser ==================");
                funSunParse(webDriver);
                System.out.println("================== done with Link ==================");
                webDriverQuit(webDriver, "no Error");
            } catch (SessionNotCreatedException e) {
                errorLog("SessionNotCreatedException");
                shouldRun = false;
                webDriverQuit(webDriver, "SessionNotCreatedException");
            } catch (TimeoutException e) {
                errorLog("TimeoutException");
                shouldRun = false;
                webDriverQuit(webDriver, "SessionNotCreatedException");
            }
        }

        System.out.println("===========Stop execution===========");
    }

    private void funSunParse(WebDriver webDriver) {
        System.out.println("================== start sleep ==================");
        sleep();
        System.out.println("================== Try get info ==================");
        try {
            hotelName = webDriver.findElement(By.cssSelector(funSunSearchBySelectorEntity.getHotelName())).getText();
            String price = webDriver.findElement(By.cssSelector(funSunSearchBySelectorEntity.getPrice())).getText();
            this.price = Integer.parseInt(price.replaceAll("[^\\d.]", ""));
            currentUrl = webDriver.getCurrentUrl();

            System.out.println(hotelName);
            System.out.println(price);
            // work with DataBase
            System.out.println("================== Start work with DB ==================");
            workWithDataBase();

        } catch (NoSuchElementException exception) {
            errorLog("NoSuchElementException");
            webDriverQuit(webDriver, "NoSuchElementException");
            shouldRun = false;
        } catch (NumberFormatException exception) {
            errorLog("NumberFormatException");
            webDriverQuit(webDriver, "NumberFormatException");
            shouldRun = false;
        } catch (TimeoutException exception) {
            errorLog("TimeoutException");
            webDriverQuit(webDriver, "TimeoutException");
            shouldRun = false;
        }
    }

    private void webDriverQuit(WebDriver webDriver, String error) {
        System.out.println(error);
        webDriver.quit();
    }

    private void workWithDataBase() {
        // if there are no data, save to database or update
        TourInfoEntity tourInfoEntity = tourInfoRepo.findByHotelName(hotelName);
        if (tourInfoEntity == null) {
            tourInfoEntity = new TourInfoEntity(hotelName, price, price, currentUrl, 0);
            tourService.saveTour(tourInfoEntity);
        } else {
            int priceDifference = tourInfoEntity.getTourPrice() - price;
            tourInfoEntity.setOldTourPrice(tourInfoEntity.getTourPrice());
            tourInfoEntity.setTourPrice(price);
            tourInfoEntity.setDifferenceInPrice(priceDifference);
            tourService.updateTour(tourInfoEntity);
        }

        System.out.println("================== End work with DB ==================");
    }

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

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");                // Включаем headless режим
        options.addArguments("--incognito");               // Включаем инкогнито
        options.addArguments("--disable-extensions");      // Отключаем расширения
        options.addArguments("--disable-gpu");             // Отключаем GPU
        options.addArguments("--no-sandbox");              // Отключаем песочницу
        options.addArguments("--disable-dev-shm-usage");   // Отключаем /dev/shm для сервера
        options.addArguments("--window-size=1920,1080");   // Устанавливаем размер окна браузера
        options.addArguments("--disable-blink-features=AutomationControlled"); // Отключаем обнаружение Selenium
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        // Меняем User-Agent на стандартный пользовательский
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

        // Отключение автоматической идентификации Selenium через переменные
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // Отключаем автоматизацию на уровне JS для более сложных проверок
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        return options;
    }

    private void sleep() {
        int rand = new Random().nextInt(5000) + 8000;
        System.out.println(rand / 1000 + " sec");
        try {
            Thread.sleep(rand);
        } catch (InterruptedException e) {
            errorLog("Thread sleep exception");
        }
    }

}
