package com.shilov.spring_reservation;

import com.shilov.spring_reservation.common.config.SpringConfig;
import com.shilov.spring_reservation.view.AppInitializer;
import com.shilov.spring_reservation.view.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        applicationContext.getBean(AppInitializer.class).initApp();
        applicationContext.getBean(View.class).dispatchMainMenuOutput(true);
    }
}
