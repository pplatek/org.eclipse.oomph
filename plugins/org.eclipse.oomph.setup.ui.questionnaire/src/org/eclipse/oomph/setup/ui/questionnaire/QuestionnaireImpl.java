/*
 * Copyright (c) 2014 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.oomph.setup.ui.questionnaire;

import org.eclipse.oomph.preferences.PreferencesFactory;
import org.eclipse.oomph.preferences.util.PreferencesUtil;
import org.eclipse.oomph.preferences.util.PreferencesUtil.PreferenceProperty;
import org.eclipse.oomph.setup.SetupTaskContainer;
import org.eclipse.oomph.setup.User;
import org.eclipse.oomph.setup.ui.Questionnaire;
import org.eclipse.oomph.setup.ui.SetupUIPlugin;
import org.eclipse.oomph.setup.ui.recorder.RecorderTransaction;
import org.eclipse.oomph.ui.UIUtil;

import org.eclipse.emf.common.util.URI;

import org.eclipse.swt.widgets.Shell;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Eike Stepper
 */
public class QuestionnaireImpl extends Questionnaire
{
  public QuestionnaireImpl()
  {
  }

  @Override
  protected void doPerform(final Shell parentShell, boolean force)
  {
    performOutsideUIThread(parentShell, force);
  }

  private void performOutsideUIThread(final Shell parentShell, boolean force)
  {
    RecorderTransaction transaction = RecorderTransaction.open();

    try
    {
      SetupTaskContainer rootObject = transaction.getRootObject();
      if (rootObject instanceof User)
      {
        User user = (User)rootObject;
        if (user.getQuestionnaireDate() == null || force)
        {
          final Map<URI, String> preferences = new HashMap<URI, String>();
          UIUtil.syncExec(new Runnable()
          {
            public void run()
            {
              GearShell shell = new GearShell(parentShell);
              Map<URI, String> result = shell.openModal();
              if (result != null)
              {
                preferences.putAll(result);
              }
            }
          });

          URI uri = PreferencesFactory.eINSTANCE.createURI(GearAnimator.RECORDER_PREFERENCE_KEY);
          if (preferences.containsKey(uri))
          {
            boolean enabled = Boolean.parseBoolean(preferences.remove(uri));
            user.setPreferenceRecorderDefault(enabled);
          }

          if (!preferences.isEmpty())
          {
            boolean inIDE = !SetupUIPlugin.isInstallerProduct();
            for (Entry<URI, String> entry : preferences.entrySet())
            {
              String path = PreferencesFactory.eINSTANCE.convertURI(entry.getKey());
              transaction.setPolicy(path, true);

              if (inIDE)
              {
                PreferenceProperty property = new PreferencesUtil.PreferenceProperty(path);
                property.set(entry.getValue());
              }
            }

            transaction.setPreferences(preferences);
          }

          user.setQuestionnaireDate(new Date());
          transaction.setForceDirty(true);
          transaction.commit();
        }
      }
    }
    finally
    {
      transaction.close();
    }
  }
}