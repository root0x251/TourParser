package com.example.demo.job;

import com.example.demo.entity.LinkEntity;
import com.example.demo.entity.SelectorEntity;
import com.example.demo.entity.TourEntity;
import com.example.demo.service.impl.LinkImpl;
import com.example.demo.service.impl.SelectorImpl;
import com.example.demo.service.impl.TourImpl;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class ParseTour {

    private boolean shouldRun = true;
    private int totalErrorCount = 0;
    private String hotelName = null;
    private String currentUrl = null;
    private int price = 0;

    private final LinkImpl link;
    private final SelectorImpl selector;
    private final TourImpl tour;

    public ParseTour(LinkImpl link, SelectorImpl selector, TourImpl tour) {
        this.link = link;
        this.selector = selector;
        this.tour = tour;
    }


    //one-hour delay

    @Scheduled(fixedDelay = 10_000)
    public void initParse() {

//        for (SelectorEntity selectorEntity : selector.findAll()) {
//            System.out.println(selectorEntity);
//            System.out.println(selectorEntity.getLinks());
//        }
//
//        for (LinkEntity linkEntity : link.findAll()) {
//            System.out.println(linkEntity);
//        }
//
//        for (TourEntity tourEntity : tour.findAll()) {
//            System.out.println(tourEntity);
//        }




//        ChromeOptions options = getChromeOptions();
//        WebDriverManager.chromedriver().setup();
//        WebDriver webDriver = new ChromeDriver(options);
//
//        webDriverQuit(webDriver, "ok");

    }
    private void startParse(WebDriver webDriver) {

    }

    private void workWithDataBase() {

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
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.668.70 Safari/537.36");

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

    private void webDriverQuit(WebDriver webDriver, String error) {
        System.out.println(error);
        if (webDriver != null) webDriver.quit();
    }

    private void errorLog(String errorCode) {

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
