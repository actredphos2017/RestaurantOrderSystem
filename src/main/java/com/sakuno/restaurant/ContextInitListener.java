package com.sakuno.restaurant;

import com.sakuno.restaurant.utils.DatabaseEntrance;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ContextInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DatabaseEntrance.init(
                    Globals.databaseDriver,
                    Globals.databaseUrl,
                    Globals.databaseUserName,
                    Globals.databasePassword,
                    Globals.databaseName
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
