package com.vladblaj.socialplatform.socialplatformspringboot.ops;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;


@Component
    public class SocialPlatformHealthIndicator implements HealthIndicator {

       @Override
       public Health health() {
         try {
           URL url =
             new URL("https://socialplatform.cfapps.io/");
             HttpURLConnection conn =
               (HttpURLConnection) url.openConnection();
             int statusCode = conn.getResponseCode();
             if (statusCode >= 200 && statusCode < 300) {
               return Health.up().build();
             } else {
                 return Health.down()
                  .withDetail("HTTP Status Code", statusCode)
                  .build();
             }
         } catch (IOException e) {
             return Health.down(e).build();
         }
       }
    }