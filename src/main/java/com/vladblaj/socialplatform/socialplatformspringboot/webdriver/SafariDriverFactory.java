package com.vladblaj.socialplatform.socialplatformspringboot.webdriver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

/**
 * @author blajv
 * @version $Id$
 * CLASS-HEADER
 */
public class SafariDriverFactory implements ObjectFactory<SafariDriver> {

    private WebDriverConfigurationProperties properties;

    SafariDriverFactory(WebDriverConfigurationProperties properties) {
        this.properties = properties;
    }

    @Override
    public SafariDriver getObject() throws BeansException {
        if (properties.getSafari().isEnabled()) {
            try {
                return new SafariDriver();
            } catch (WebDriverException e) {
                e.printStackTrace();
                // swallow the exception
            }
        }
        return null;
    }
}
