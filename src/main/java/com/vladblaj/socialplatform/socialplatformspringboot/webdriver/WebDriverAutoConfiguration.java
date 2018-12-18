package com.vladblaj.socialplatform.socialplatformspringboot.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

import static org.openqa.selenium.chrome.ChromeDriverService.createDefaultService;

/**
 * Autoconfigure a {@link WebDriver} based on what's available, falling back to {@link HtmlUnitDriver}
 * if none other is available.
 *
 * @author Greg Turnquist
 */
// tag::1[]
@Configuration
@ConditionalOnClass(WebDriver.class)
@EnableConfigurationProperties(
        WebDriverConfigurationProperties.class)
@Import({ChromeDriverFactory.class,
        FirefoxDriverFactory.class, SafariDriverFactory.class})
public class WebDriverAutoConfiguration {
// end::1[]

    // tag::2[]
    @Autowired
    WebDriverConfigurationProperties properties;
    // end::2[]

    // tag::3[]
    @Primary
    @Bean(destroyMethod = "quit")
    @ConditionalOnMissingBean(WebDriver.class)
    public WebDriver webDriver(
            FirefoxDriverFactory firefoxDriverFactory,
            SafariDriverFactory safariDriverFactory,
            ChromeDriverFactory chromeDriverFactory) {

        WebDriver driver = firefoxDriverFactory.getObject();

        if (driver == null) {
            driver = safariDriverFactory.getObject();
        }

        if (driver == null) {
            driver = chromeDriverFactory.getObject();
        }

        if (driver == null) {
            driver = new HtmlUnitDriver();
        }

        return driver;
    }
    // end::3[]

    // tag::5[]
    @Bean(destroyMethod = "stop")
    @Lazy
    public ChromeDriverService chromeDriverService() {
        System.setProperty("webdriver.chrome.driver",
                "ext/chromedriver");
        return createDefaultService();
    }
    // end::5[]

}