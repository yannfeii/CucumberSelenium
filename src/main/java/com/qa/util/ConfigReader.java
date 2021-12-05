package com.qa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private Properties prop;

    public Properties initProp() {

        prop = new Properties();

        /**
         * This method is used to load the properties from config.properties file
         * @return it returns Properties prop object
         */
        try {
            FileInputStream ip = new FileInputStream("./src/test/java/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }
}
