package com.vladblaj.socialplatform.socialplatformspringboot.webdriver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

/**
 * @author blajv
 * @version $Id$
 * CLASS-HEADER
 */
public class ChromeDriverFactory implements ObjectFactory<ChromeDriver> {

    private WebDriverConfigurationProperties properties;

    ChromeDriverFactory(WebDriverConfigurationProperties properties) {
        this.properties = properties;
    }

    @Override
    public ChromeDriver getObject() throws BeansException {
        if (properties.getChrome().isEnabled()) {
            try {
                return new ChromeDriver();
            } catch (WebDriverException e) {
                e.printStackTrace();
                // swallow the exception
            }
        }
        return null;
    }
}

