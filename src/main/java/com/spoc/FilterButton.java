package com.spoc;

import com.install4j.api.Util;
import com.install4j.api.context.Context;
import com.install4j.api.formcomponents.AbstractFormComponent;
import com.install4j.runtime.beans.formcomponents.DirectoryChooserComponent;
import com.spoc.util.Utils;
import org.sonatype.install4j.common.Variables;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Phuonghqh on 8/21/16.
 */
public class FilterButton extends AbstractFormComponent {

  private JButton filterButton = new JButton("Filter");

  @Override
  public JComponent createCenterComponent() {
    Context context = getContext();
    filterButton.addActionListener(new AbstractAction() {

      @Override
      public void actionPerformed(ActionEvent event) {
        try {
          DirectoryChooserComponent folderChooser = (DirectoryChooserComponent) Arrays.stream(getFormEnvironment().getFormComponents())
            .filter(fc -> fc instanceof DirectoryChooserComponent)
            .findFirst()
            .get();
          if (folderChooser.checkCompleted()) {
            Util.logInfo(this, context.getVariableNames().toString());
            String sourceDir = Variables.getStringVariable(context, "SourceDir");
            Util.logInfo(this, String.format("selected folder: %s", sourceDir));
            Properties properties = Utils.loadFrom(sourceDir);
            Util.logInfo(this, String.format("loadedddd %d properties", properties.size()));
          }
        }
        catch (IOException e) {
          Util.logError(this, e.getLocalizedMessage());
        }
      }
    });
    return filterButton;
  }

  @Override
  public boolean isFillCenterHorizontal() {
    return false;
  }
}
