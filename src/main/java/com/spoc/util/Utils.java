package com.spoc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Phuonghqh on 8/21/16.
 */
public class Utils {

  public static Properties loadFrom(String sourceDir) throws IOException {
    Properties properties = new Properties();
    File[] propertiesFiles = new File(sourceDir).listFiles((dir, name) -> name.endsWith(".properties"));
    for (File propertiesFile : propertiesFiles) {
      Properties props = new Properties();
      props.load(new FileInputStream(propertiesFile));
      properties.putAll(props);
    }
    return properties;
  }
}
