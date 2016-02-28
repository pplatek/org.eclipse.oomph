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
package org.eclipse.oomph.targlets.provider;

import org.eclipse.oomph.base.Annotation;
import org.eclipse.oomph.base.BasePackage;
import org.eclipse.oomph.base.util.BaseSwitch;
import org.eclipse.oomph.p2.RepositoryList;
import org.eclipse.oomph.targlets.Targlet;
import org.eclipse.oomph.targlets.TargletFactory;
import org.eclipse.oomph.targlets.TargletPackage;
import org.eclipse.oomph.targlets.util.TargletAdapterFactory;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ChildCreationExtenderManager;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class TargletItemProviderAdapterFactory extends TargletAdapterFactory
    implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender
{
  /**
   * This keeps track of the root adapter factory that delegates to this adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ComposedAdapterFactory parentAdapterFactory;

  /**
   * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected IChangeNotifier changeNotifier = new ChangeNotifier();

  /**
   * This helps manage the child creation extenders.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(TargletEditPlugin.INSTANCE, TargletPackage.eNS_URI);

  /**
   * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected Collection<Object> supportedTypes = new ArrayList<Object>();

  private boolean showOnlyActiveRepositoryList;

  /**
   * This constructs an instance.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  public TargletItemProviderAdapterFactory()
  {
    this(false);
  }

  public TargletItemProviderAdapterFactory(boolean showOnlyActiveRepositoryList)
  {
    this.showOnlyActiveRepositoryList = showOnlyActiveRepositoryList;

    supportedTypes.add(IEditingDomainItemProvider.class);
    supportedTypes.add(IStructuredItemContentProvider.class);
    supportedTypes.add(ITreeItemContentProvider.class);
    supportedTypes.add(IItemLabelProvider.class);
    supportedTypes.add(IItemPropertySource.class);
  }

  public final boolean isShowOnlyActiveRepositoryList()
  {
    return showOnlyActiveRepositoryList;
  }

  public final void setShowOnlyActiveRepositoryList(boolean showOnlyActiveRepositoryList)
  {
    this.showOnlyActiveRepositoryList = showOnlyActiveRepositoryList;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.TargletContainer} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TargletContainerItemProvider targletContainerItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.TargletContainer}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createTargletContainerAdapter()
  {
    if (targletContainerItemProvider == null)
    {
      targletContainerItemProvider = new TargletContainerItemProvider(this);
    }

    return targletContainerItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.Targlet} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TargletItemProvider targletItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.Targlet}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated NOT
   */
  @Override
  public Adapter createTargletAdapter()
  {
    if (targletItemProvider == null)
    {
      targletItemProvider = new TargletItemProvider(this)
      {
        @Override
        public Collection<?> getChildren(Object object)
        {
          Collection<?> children = super.getChildren(object);

          if (showOnlyActiveRepositoryList)
          {
            RepositoryList activeRepositoryList = ((Targlet)object).getActiveRepositoryList();
            for (Iterator<?> it = children.iterator(); it.hasNext();)
            {
              Object child = it.next();
              if (child instanceof RepositoryList && child != activeRepositoryList)
              {
                it.remove();
              }
            }
          }

          return children;
        }
      };
    }

    return targletItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.ComponentExtension} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ComponentExtensionItemProvider componentExtensionItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.ComponentExtension}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createComponentExtensionAdapter()
  {
    if (componentExtensionItemProvider == null)
    {
      componentExtensionItemProvider = new ComponentExtensionItemProvider(this);
    }

    return componentExtensionItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.ComponentDefinition} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ComponentDefinitionItemProvider componentDefinitionItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.ComponentDefinition}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createComponentDefinitionAdapter()
  {
    if (componentDefinitionItemProvider == null)
    {
      componentDefinitionItemProvider = new ComponentDefinitionItemProvider(this);
    }

    return componentDefinitionItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.FeatureGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FeatureGeneratorItemProvider featureGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.FeatureGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createFeatureGeneratorAdapter()
  {
    if (featureGeneratorItemProvider == null)
    {
      featureGeneratorItemProvider = new FeatureGeneratorItemProvider(this);
    }

    return featureGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.PluginGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PluginGeneratorItemProvider pluginGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.PluginGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createPluginGeneratorAdapter()
  {
    if (pluginGeneratorItemProvider == null)
    {
      pluginGeneratorItemProvider = new PluginGeneratorItemProvider(this);
    }

    return pluginGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.ComponentDefGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ComponentDefGeneratorItemProvider componentDefGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.ComponentDefGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createComponentDefGeneratorAdapter()
  {
    if (componentDefGeneratorItemProvider == null)
    {
      componentDefGeneratorItemProvider = new ComponentDefGeneratorItemProvider(this);
    }

    return componentDefGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.ComponentExtGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ComponentExtGeneratorItemProvider componentExtGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.ComponentExtGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createComponentExtGeneratorAdapter()
  {
    if (componentExtGeneratorItemProvider == null)
    {
      componentExtGeneratorItemProvider = new ComponentExtGeneratorItemProvider(this);
    }

    return componentExtGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.CSpecGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CSpecGeneratorItemProvider cSpecGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.CSpecGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createCSpecGeneratorAdapter()
  {
    if (cSpecGeneratorItemProvider == null)
    {
      cSpecGeneratorItemProvider = new CSpecGeneratorItemProvider(this);
    }

    return cSpecGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.CSpexGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CSpexGeneratorItemProvider cSpexGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.CSpexGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createCSpexGeneratorAdapter()
  {
    if (cSpexGeneratorItemProvider == null)
    {
      cSpexGeneratorItemProvider = new CSpexGeneratorItemProvider(this);
    }

    return cSpexGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.SiteGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SiteGeneratorItemProvider siteGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.SiteGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createSiteGeneratorAdapter()
  {
    if (siteGeneratorItemProvider == null)
    {
      siteGeneratorItemProvider = new SiteGeneratorItemProvider(this);
    }

    return siteGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.CategoryGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CategoryGeneratorItemProvider categoryGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.CategoryGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createCategoryGeneratorAdapter()
  {
    if (categoryGeneratorItemProvider == null)
    {
      categoryGeneratorItemProvider = new CategoryGeneratorItemProvider(this);
    }

    return categoryGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.ProductGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ProductGeneratorItemProvider productGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.ProductGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createProductGeneratorAdapter()
  {
    if (productGeneratorItemProvider == null)
    {
      productGeneratorItemProvider = new ProductGeneratorItemProvider(this);
    }

    return productGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.ProjectNameGenerator} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ProjectNameGeneratorItemProvider projectNameGeneratorItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.ProjectNameGenerator}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createProjectNameGeneratorAdapter()
  {
    if (projectNameGeneratorItemProvider == null)
    {
      projectNameGeneratorItemProvider = new ProjectNameGeneratorItemProvider(this);
    }

    return projectNameGeneratorItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.oomph.targlets.DropinLocation} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DropinLocationItemProvider dropinLocationItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.oomph.targlets.DropinLocation}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createDropinLocationAdapter()
  {
    if (dropinLocationItemProvider == null)
    {
      dropinLocationItemProvider = new DropinLocationItemProvider(this);
    }

    return dropinLocationItemProvider;
  }

  /**
   * This returns the root adapter factory that contains this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ComposeableAdapterFactory getRootAdapterFactory()
  {
    return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
  }

  /**
   * This sets the composed adapter factory that contains this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory)
  {
    this.parentAdapterFactory = parentAdapterFactory;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object type)
  {
    return supportedTypes.contains(type) || super.isFactoryForType(type);
  }

  /**
   * This implementation substitutes the factory itself as the key for the adapter.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter adapt(Notifier notifier, Object type)
  {
    return super.adapt(notifier, this);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object adapt(Object object, Object type)
  {
    if (isFactoryForType(type))
    {
      Object adapter = super.adapt(object, type);
      if (!(type instanceof Class<?>) || ((Class<?>)type).isInstance(adapter))
      {
        return adapter;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public List<IChildCreationExtender> getChildCreationExtenders()
  {
    return childCreationExtenderManager.getChildCreationExtenders();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain)
  {
    return childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResourceLocator getResourceLocator()
  {
    return childCreationExtenderManager;
  }

  /**
   * This adds a listener.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void addListener(INotifyChangedListener notifyChangedListener)
  {
    changeNotifier.addListener(notifyChangedListener);
  }

  /**
   * This removes a listener.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void removeListener(INotifyChangedListener notifyChangedListener)
  {
    changeNotifier.removeListener(notifyChangedListener);
  }

  /**
   * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void fireNotifyChanged(Notification notification)
  {
    changeNotifier.fireNotifyChanged(notification);

    if (parentAdapterFactory != null)
    {
      parentAdapterFactory.fireNotifyChanged(notification);
    }
  }

  /**
   * This disposes all of the item providers created by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void dispose()
  {
    if (targletContainerItemProvider != null)
    {
      targletContainerItemProvider.dispose();
    }
    if (targletItemProvider != null)
    {
      targletItemProvider.dispose();
    }
    if (componentExtensionItemProvider != null)
    {
      componentExtensionItemProvider.dispose();
    }
    if (componentDefinitionItemProvider != null)
    {
      componentDefinitionItemProvider.dispose();
    }
    if (featureGeneratorItemProvider != null)
    {
      featureGeneratorItemProvider.dispose();
    }
    if (pluginGeneratorItemProvider != null)
    {
      pluginGeneratorItemProvider.dispose();
    }
    if (componentDefGeneratorItemProvider != null)
    {
      componentDefGeneratorItemProvider.dispose();
    }
    if (componentExtGeneratorItemProvider != null)
    {
      componentExtGeneratorItemProvider.dispose();
    }
    if (cSpecGeneratorItemProvider != null)
    {
      cSpecGeneratorItemProvider.dispose();
    }
    if (cSpexGeneratorItemProvider != null)
    {
      cSpexGeneratorItemProvider.dispose();
    }
    if (siteGeneratorItemProvider != null)
    {
      siteGeneratorItemProvider.dispose();
    }
    if (categoryGeneratorItemProvider != null)
    {
      categoryGeneratorItemProvider.dispose();
    }
    if (productGeneratorItemProvider != null)
    {
      productGeneratorItemProvider.dispose();
    }
    if (projectNameGeneratorItemProvider != null)
    {
      projectNameGeneratorItemProvider.dispose();
    }
    if (dropinLocationItemProvider != null)
    {
      dropinLocationItemProvider.dispose();
    }
  }

  /**
   * A child creation extender for the {@link BasePackage}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static class BaseChildCreationExtender implements IChildCreationExtender
  {
    /**
     * The switch for creating child descriptors specific to each extended class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static class CreationSwitch extends BaseSwitch<Object>
    {
      /**
       * The child descriptors being populated.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      protected List<Object> newChildDescriptors;

      /**
       * The domain in which to create the children.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      protected EditingDomain editingDomain;

      /**
       * Creates the a switch for populating child descriptors in the given domain.
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain)
      {
        this.newChildDescriptors = newChildDescriptors;
        this.editingDomain = editingDomain;
      }

      /**
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      @Override
      public Object caseAnnotation(Annotation object)
      {
        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createTargletContainer()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createTarglet()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createComponentExtension()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createComponentDefinition()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createFeatureGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createPluginGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createComponentDefGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createComponentExtGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createCSpecGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createCSpexGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createSiteGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createCategoryGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createProductGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createProjectNameGenerator()));

        newChildDescriptors.add(createChildParameter(BasePackage.Literals.ANNOTATION__CONTENTS, TargletFactory.eINSTANCE.createDropinLocation()));

        return null;
      }

      /**
       * <!-- begin-user-doc -->
       * <!-- end-user-doc -->
       * @generated
       */
      protected CommandParameter createChildParameter(Object feature, Object child)
      {
        return new CommandParameter(null, feature, child);
      }

    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain)
    {
      ArrayList<Object> result = new ArrayList<Object>();
      new CreationSwitch(result, editingDomain).doSwitch((EObject)object);
      return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourceLocator getResourceLocator()
    {
      return TargletEditPlugin.INSTANCE;
    }
  }

}
