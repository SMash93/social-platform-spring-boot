package com.vladblaj.socialplatform.socialplatformspringboot;

import com.vladblaj.socialplatform.socialplatformspringboot.webdriver.WebDriverAutoConfiguration;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ClassUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author blajv
 * @version $Id$
 * CLASS-HEADER
 */
public class WebDriverAutoConfigurationTests {
    private AnnotationConfigApplicationContext context;

    @After
    public void close() {
        if (this.context != null) {
            this.context.close();
        }
    }

    private void load(Class<?>[] configs, String... environment) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext
                .register(WebDriverAutoConfiguration.class);
        if (configs.length > 0) {
            applicationContext.register(configs);
        }
        EnvironmentTestUtils
                .addEnvironment(applicationContext, environment);
        applicationContext.refresh();
        this.context = applicationContext;
    }

    @Test
    public void fallbackToNonGuiModeWhenAllBrowsersDisabled() {
        load(new Class[]{},
                "com.vladblaj.webdriver.firefox.enabled:false",
                "com.vladblaj.webdriver.safari.enabled:false",
                "com.vladblaj.webdriver.chrome.enabled:false");

        WebDriver driver = context.getBean(WebDriver.class);
        assertThat(ClassUtils.isAssignable(TakesScreenshot.class,
                driver.getClass())).isFalse();
        assertThat(ClassUtils.isAssignable(HtmlUnitDriver.class,
                driver.getClass())).isTrue();
    }
}
