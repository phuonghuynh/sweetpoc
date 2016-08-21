package com.spoc;

import com.install4j.api.Util;
import com.install4j.api.actions.InstallAction;
import com.install4j.api.context.Context;
import com.install4j.api.context.InstallerContext;
import com.install4j.api.context.UserCanceledException;
import org.sonatype.install4j.common.Variables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by Phuonghqh on 8/20/16.
 */
public class SourceDirInstallAction implements InstallAction {

  private Context context;

  public boolean install(InstallerContext installerContext) throws UserCanceledException {
    String sourceDir = Variables.getStringVariable(installerContext, "SourceDir");
    Util.logInfo(this, String.format("selected folder: %s", sourceDir));

    Properties properties = new Properties();
    File[] propertiesFiles = new File(sourceDir).listFiles((dir, name) -> name.endsWith(".properties"));
    for (File propertiesFile : propertiesFiles) {
      try {
        Properties props = new Properties();
        props.load(new FileInputStream(propertiesFile));
        properties.putAll(props);
      }
      catch (IOException e) {
      }
    }

    if (properties.keySet().size() == 0) {
      Util.showMessage("No properties found");
      return true;
    }

    File installDir = installerContext.getInstallationDirectory();
    String iniFilePath = installDir.getAbsolutePath() + "/" + "SafeConsole.ini";
    Util.logInfo(this, String.format("init file %s", iniFilePath));
    try {
      File iniFile = new File(iniFilePath);
      iniFile.createNewFile();
      properties.store(new FileOutputStream(iniFilePath, false), "from custom code");
      Util.logInfo(this, String.format("copied %d properties to %s", properties.keySet().size(), iniFilePath));
    }
    catch (IOException e) {
      Util.logError(this, e.getLocalizedMessage());
    }

    return true;
  }

  public void rollback(InstallerContext installerContext) {
  }

  public boolean isRollbackSupported() {
    return true;
  }

  public void init(Context context) {
    this.context = context;
  }
}
