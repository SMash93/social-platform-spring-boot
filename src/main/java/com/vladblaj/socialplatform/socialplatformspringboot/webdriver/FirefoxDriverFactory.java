package com.vladblaj.socialplatform.socialplatformspringboot.webdriver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

import javax.validation.constraints.NotNull;

/**
 * @author blajv
 * @version $Id$
 * CLASS-HEADER
 */
public class FirefoxDriverFactory implements ObjectFactory<FirefoxDriver> {

    private WebDriverConfigurationProperties properties;

    FirefoxDriverFactory(@NotNull  WebDriverConfigurationProperties properties) {
        this.properties = properties;
    }

    @Override
    public FirefoxDriver getObject() throws BeansException {
        if (properties.getFirefox().isEnabled()) {
            try {
                return new FirefoxDriver();
            } catch (WebDriverException e) {
                e.printStackTrace();
                // swallow the exception
            }
        }
        return null;
    }
}

