/*
 * Copyright (c) 2014, 2015 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.oomph.p2.internal.core;

import org.eclipse.oomph.p2.P2Exception;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.equinox.internal.p2.engine.ISurrogateProfileHandler;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.index.IIndex;
import org.eclipse.equinox.p2.query.IQuery;
import org.eclipse.equinox.p2.query.IQueryResult;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 */
@SuppressWarnings("restriction")
public final class LazyProfile extends org.eclipse.equinox.internal.p2.engine.Profile
{
  private final LazyProfileRegistry registry;

  private final File profileDirectory;

  private SoftReference<org.eclipse.equinox.internal.p2.engine.Profile> delegate;

  private org.eclipse.equinox.internal.p2.engine.Profile parent;

  public LazyProfile(LazyProfileRegistry registry, String profileId, File profileDirectory)
  {
    super(registry.getProvisioningAgent(), profileId, null, null);
    this.registry = registry;
    this.profileDirectory = profileDirectory;
  }

  public synchronized org.eclipse.equinox.internal.p2.engine.Profile getDelegate(boolean loadOnDemand)
  {
    if (delegate != null)
    {
      org.eclipse.equinox.internal.p2.engine.Profile referent = delegate.get();
      if (referent != null)
      {
        return referent;
      }

      delegate = null;
    }

    if (!loadOnDemand)
    {
      return null;
    }

    String profileId = getProfileId();
    org.eclipse.equinox.internal.p2.engine.Profile referent = registry.loadProfile(profileId, profileDirectory);
    if (referent == null)
    {
      throw new P2Exception("Profile '" + profileId + "' could not be loaded from " + profileDirectory);
    }

    referent.setParent(parent);
    delegate = new SoftReference<org.eclipse.equinox.internal.p2.engine.Profile>(referent);
    return referent;
  }

  private org.eclipse.equinox.internal.p2.engine.Profile getDelegate()
  {
    return getDelegate(true);
  }

  @Override
  public IQueryResult<IInstallableUnit> query(IQuery<IInstallableUnit> query, IProgressMonitor monitor)
  {
    return getDelegate().query(query, monitor);
  }

  @Override
  public IProfile getParentProfile()
  {
    return getDelegate().getParentProfile();
  }

  @Override
  public synchronized void setParent(org.eclipse.equinox.internal.p2.engine.Profile parent)
  {
    this.parent = parent;

    org.eclipse.equinox.internal.p2.engine.Profile delegate = getDelegate(false);
    if (delegate != null)
    {
      delegate.setParent(parent);
    }
  }

  @Override
  public boolean isRootProfile()
  {
    return getDelegate().isRootProfile();
  }

  @Override
  public void addSubProfile(String subProfileId) throws IllegalArgumentException
  {
    getDelegate().addSubProfile(subProfileId);
  }

  @Override
  public void removeSubProfile(String subProfileId) throws IllegalArgumentException
  {
    getDelegate().removeSubProfile(subProfileId);
  }

  @Override
  public boolean hasSubProfiles()
  {
    return getDelegate().hasSubProfiles();
  }

  @Override
  public List<String> getSubProfileIds()
  {
    return getDelegate().getSubProfileIds();
  }

  @Override
  public String getProperty(String key)
  {
    return getDelegate().getProperty(key);
  }

  @Override
  public String getLocalProperty(String key)
  {
    return getDelegate().getLocalProperty(key);
  }

  @Override
  public void setProperty(String key, String value)
  {
    getDelegate().setProperty(key, value);
  }

  @Override
  public void removeProperty(String key)
  {
    getDelegate().removeProperty(key);
  }

  @Override
  public synchronized IIndex<IInstallableUnit> getIndex(String memberName)
  {
    return getDelegate().getIndex(memberName);
  }

  @Override
  public Iterator<IInstallableUnit> everything()
  {
    return getDelegate().everything();
  }

  @Override
  public Object getManagedProperty(Object client, String memberName, Object key)
  {
    return getDelegate().getManagedProperty(client, memberName, key);
  }

  @Override
  public IQueryResult<IInstallableUnit> available(IQuery<IInstallableUnit> query, IProgressMonitor monitor)
  {
    return getDelegate().available(query, monitor);
  }

  @Override
  public String getInstallableUnitProperty(IInstallableUnit iu, String key)
  {
    return getDelegate().getInstallableUnitProperty(iu, key);
  }

  @Override
  public String setInstallableUnitProperty(IInstallableUnit iu, String key, String value)
  {
    return getDelegate().setInstallableUnitProperty(iu, key, value);
  }

  @Override
  public String removeInstallableUnitProperty(IInstallableUnit iu, String key)
  {
    return getDelegate().removeInstallableUnitProperty(iu, key);
  }

  @Override
  public Map<String, String> getLocalProperties()
  {
    return getDelegate().getLocalProperties();
  }

  @Override
  public Map<String, String> getProperties()
  {
    return getDelegate().getProperties();
  }

  @Override
  public IProvisioningAgent getProvisioningAgent()
  {
    return getDelegate().getProvisioningAgent();
  }

  @Override
  public void addProperties(Map<String, String> properties)
  {
    getDelegate().addProperties(properties);
  }

  @Override
  public void addInstallableUnit(IInstallableUnit iu)
  {
    getDelegate().addInstallableUnit(iu);
  }

  @Override
  public void removeInstallableUnit(IInstallableUnit iu)
  {
    getDelegate().removeInstallableUnit(iu);
  }

  @Override
  public Map<String, String> getInstallableUnitProperties(IInstallableUnit iu)
  {
    return getDelegate().getInstallableUnitProperties(iu);
  }

  @Override
  public void clearLocalProperties()
  {
    getDelegate().clearLocalProperties();
  }

  @Override
  public boolean isChanged()
  {
    return getDelegate().isChanged();
  }

  @Override
  public void setChanged(boolean isChanged)
  {
    getDelegate().setChanged(isChanged);
  }

  @Override
  public void clearInstallableUnits()
  {
    getDelegate().clearInstallableUnits();
  }

  @Override
  public org.eclipse.equinox.internal.p2.engine.Profile snapshot()
  {
    return getDelegate().snapshot();
  }

  @Override
  public void addInstallableUnitProperties(IInstallableUnit iu, Map<String, String> properties)
  {
    getDelegate().addInstallableUnitProperties(iu, properties);
  }

  @Override
  public void clearInstallableUnitProperties(IInstallableUnit iu)
  {
    getDelegate().clearInstallableUnitProperties(iu);
  }

  @Override
  public void clearOrphanedInstallableUnitProperties()
  {
    getDelegate().clearOrphanedInstallableUnitProperties();
  }

  @Override
  public long getTimestamp()
  {
    return getDelegate().getTimestamp();
  }

  @Override
  public void setTimestamp(long millis)
  {
    getDelegate().setTimestamp(millis);
  }

  @Override
  public void setSurrogateProfileHandler(ISurrogateProfileHandler surrogateProfileHandler)
  {
    getDelegate().setSurrogateProfileHandler(surrogateProfileHandler);
  }

  @Override
  public String toString()
  {
    return getProfileId();
  }
}
