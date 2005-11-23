/**
 * <copyright>
 *
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: ModelImporterApplication.java,v 1.14 2005/11/23 23:41:47 emerks Exp $
 */
package org.eclipse.emf.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IPlatformRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import org.eclipse.emf.codegen.ecore.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.codegen.util.CodeGenUtil.StreamProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;


/**
 * @since 2.1.0
 */
public abstract class ModelImporterApplication implements IPlatformRunnable
{
  protected ModelImporter modelImporter;

  protected boolean reload = false;
  protected IPath modelProjectLocationPath;
  protected IPath modelFragmentPath;
  protected IPath editProjectLocationPath;
  protected IPath editFragmentPath;
  protected IPath editorProjectLocationPath;
  protected IPath editorFragmentPath;
  protected IPath testsProjectLocationPath;
  protected IPath testsFragmentPath;
  protected String templatePath;
  protected String copyright;
  protected boolean sdo = false;
  protected String modelPluginID;

  protected List referencedEPackages;
  protected Map referencedGenModelPathToEPackageNSURIs;

  protected boolean quiet = false;

  public ModelImporter getModelImporter()
  {
    if (modelImporter == null)
    {
      modelImporter = createModelImporter();
    }
    return modelImporter;
  }

  protected abstract ModelImporter createModelImporter();

  public Object run(final Object args) throws Exception
  {
    try
    {
      IWorkspaceRunnable runnable = new IWorkspaceRunnable()
        {
          public void run(IProgressMonitor progressMonitor) throws CoreException
          {
            try
            {
              ModelImporterApplication.this.run(progressMonitor, (String[])args);
            }
            catch (Exception exception)
            {
              throw new CoreException(new Status(
                IStatus.ERROR,
                ImporterPlugin.ID,
                0,
                "Error",
                exception));
            }
            finally
            {
              progressMonitor.done();
            }
          }
        };

      ResourcesPlugin.getWorkspace().run(runnable, getProgressMonitor());
      return new Integer(0);
    }
    catch (Exception exception)
    {
      if (!quiet)
      {
        exception.printStackTrace();
      }
      ImporterPlugin.INSTANCE.log(exception);
      return new Integer(1);
    }
  }

  protected IProgressMonitor getProgressMonitor()
  {
    return quiet ? new NullProgressMonitor() : new StreamProgressMonitor(System.out);
  }
  
  protected Monitor getMonitor()
  {
    return quiet ? new BasicMonitor() : new BasicMonitor.Printing(System.out);
  }  

  public void run(IProgressMonitor progressMonitor, String[] arguments) throws Exception
  {
    run(BasicMonitor.toMonitor(progressMonitor), arguments); 
  }

  public void run(Monitor monitor, String[] arguments) throws Exception
  {
    try
    {
      monitor.beginTask("", 1);

      if (arguments.length == 0 || "-help".equalsIgnoreCase(arguments[0].toString()))
      {
        printUsage();
      }
      else
      {
        processArguments(arguments, 0);
        execute(CodeGenUtil.createMonitor(monitor, 1));
      }
    }
    finally
    {
      monitor.done();
    }
  }
    
  public void printUsage()
  {
    System.out.println(getUsage());
  }

  protected abstract StringBuffer getUsage();

  protected static final String NL = System.getProperties().getProperty("line.separator");

  protected StringBuffer appendLine(StringBuffer buffer, String line)
  {
    if (line != null)
    {
      buffer.append(line);
    }
    buffer.append(NL);
    return buffer;
  }

  public void execute(Monitor monitor) throws Exception
  {
    try
    {
      monitor.beginTask("", 6);

      adjustAttributes(CodeGenUtil.createMonitor(monitor, 1));
      adjustModelImporter(CodeGenUtil.createMonitor(monitor, 1));
  
      computeEPackages(CodeGenUtil.createMonitor(monitor, 1));
      adjustEPackages(CodeGenUtil.createMonitor(monitor, 1));
      adjustGenModel(CodeGenUtil.createMonitor(monitor, 1));
  
      doExecute(CodeGenUtil.createMonitor(monitor, 1));
    }
    finally
    {
      monitor.done();
    }
  }

  protected void processArguments(String[] arguments, int index)
  {
    while (index < arguments.length)
    {
      index = processArgument(arguments, index); 
    }
  }

  protected int processArgument(String[] arguments, int index)
  {
    if (arguments[index].equalsIgnoreCase("-reload"))
    {
      reload = true;
    }
    else if (arguments[index].equalsIgnoreCase("-modelProject"))
    {
      modelProjectLocationPath = new Path(new File(arguments[++index]).getAbsolutePath());
      modelFragmentPath = new Path(arguments[++index]);
    }
    else if (arguments[index].equalsIgnoreCase("-editProject"))
    {
      editProjectLocationPath = new Path(new File(arguments[++index]).getAbsolutePath());
      editFragmentPath = new Path(arguments[++index]);
    }
    else if (arguments[index].equalsIgnoreCase("-editorProject"))
    {
      editorProjectLocationPath = new Path(new File(arguments[++index]).getAbsolutePath());
      editorFragmentPath = new Path(arguments[++index]);
    }
    else if (arguments[index].equalsIgnoreCase("-testsProject"))
    {
      testsProjectLocationPath = new Path(new File(arguments[++index]).getAbsolutePath());
      testsFragmentPath = new Path(arguments[++index]);
    }
    else if (arguments[index].equalsIgnoreCase("-modelPluginID"))
    {
      modelPluginID = arguments[++index];
    }
    else if (arguments[index].equalsIgnoreCase("-copyright"))
    {
      copyright = arguments[++index];
    }
    else if (arguments[index].equalsIgnoreCase("-sdo"))
    {
      sdo = true;
    }
    else if (arguments[index].equalsIgnoreCase("-quiet"))
    {
      quiet = true;
    }
    else if (arguments[index].equalsIgnoreCase("-templatePath"))
    {
      templatePath = URI.createFileURI(new File(arguments[++index]).getAbsolutePath()).toString();
    }
    else if (arguments[index].equalsIgnoreCase("-refGenModel"))
    {
      if (referencedGenModelPathToEPackageNSURIs == null)
      {
        referencedGenModelPathToEPackageNSURIs = new HashMap();
      }

      IPath genModelPath = new Path(new File(arguments[++index]).getAbsolutePath());
      Set ePackageNSURIs = (Set)referencedGenModelPathToEPackageNSURIs.get(genModelPath);
      if (ePackageNSURIs == null)
      {
        ePackageNSURIs = new HashSet();
        referencedGenModelPathToEPackageNSURIs.put(genModelPath, ePackageNSURIs);
      }

      do
      {
        ePackageNSURIs.add(arguments[++index]);
      }
      while (index + 1 < arguments.length && !arguments[index + 1].startsWith("-"));
    }
    else
    {
      handleUnrecognizedArgument(arguments[index]);
    }
    return index + 1;
  }

  protected void handleUnrecognizedArgument(String argument)
  {
    throw new IllegalArgumentException("Unrecognized argument: '" + argument + "'");
  }

  protected void adjustAttributes(Monitor monitor)
  {
    try
    {
      monitor.beginTask("", 1);

      if (modelProjectLocationPath == null)
      {
        modelProjectLocationPath = new Path(new File(".").getAbsolutePath());
      }
  
      if (modelFragmentPath == null)
      {
        modelFragmentPath = new Path(".");
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected void adjustModelImporter(Monitor monitor)
  {
    try
    {
      monitor.beginTask("", 1);

      ModelImporter modelImporter = getModelImporter();
      modelImporter.setUsePlatformURI(false);
      modelImporter.setGenModelProjectLocation(modelProjectLocationPath);
      if (modelPluginID != null)
      {
        modelImporter.setModelPluginID(modelPluginID);
      }
      if (modelProjectLocationPath != null)
      {
        modelImporter.setModelPluginDirectory(modelProjectLocationPath + "/./" + modelFragmentPath + "/.");
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected void handleGenModelPath(IPath genModelFullPath)
  {
    ModelImporter modelImporter = getModelImporter();
    if (reload)
    {
      try
      {
        modelImporter.defineOriginalGenModelPath(genModelFullPath);
      }
      catch (DiagnosticException exception)
      {
        throw new RuntimeException(exception);
      }
    }
    else
    {
      modelImporter.setGenModelContainerPath(genModelFullPath.removeLastSegments(1));
      modelImporter.setGenModelFileName(genModelFullPath.lastSegment());
    }
  }

  protected final void computeEPackages(Monitor monitor) throws Exception
  {
    try
    {
      monitor.beginTask("", 1);
      getModelImporter().computeEPackages(CodeGenUtil.createMonitor(monitor, 1));
    }
    finally
    {
      monitor.done();
    }
  }

  protected void adjustEPackages(Monitor monitor)
  {
    try
    {
      monitor.beginTask("", 1);

      if (referencedGenModelPathToEPackageNSURIs != null)
      {
        for (Iterator i = referencedGenModelPathToEPackageNSURIs.entrySet().iterator(); i.hasNext();)
        {
          Map.Entry entry = (Map.Entry)i.next();
          IPath genModelPath = (IPath)entry.getKey();
          Set ePackageNSURIs = (Set)entry.getValue();
  
          ResourceSet resourceSet = getModelImporter().createResourceSet();
          Resource resource = resourceSet.getResource(URI.createFileURI(genModelPath.toOSString()), true);
          GenModel referencedGenModel = (GenModel)resource.getContents().get(0);
          for (Iterator j = referencedGenModel.getGenPackages().iterator(); j.hasNext();)
          {
            GenPackage genPackage = (GenPackage)j.next();
            if (ePackageNSURIs.contains(genPackage.getEcorePackage().getNsURI()))
            {
              getModelImporter().getReferencedGenPackages().add(genPackage);
            }
          }
        }
      }
    }
    finally
    {
      monitor.done();
    }
  }
  
  protected void handleQualifiedEPackageName(EPackage ePackage)
  {
    String packageName = ePackage.getName();
    int index = packageName.lastIndexOf(".");
    if (index != -1)
    {
      getModelImporter().getEPackageInfo(ePackage).setBasePackage(packageName.substring(0, index));
      ePackage.setName(packageName.substring(index + 1));
    }
  }  

  protected void handleEPackage(EPackage ePackage, boolean generate)
  {
    getModelImporter().getEPackageInfo(ePackage).setGenerate(generate);
    if (!generate)
    {
      if (referencedEPackages == null)
      {
        referencedEPackages = new ArrayList();
      }
      referencedEPackages.add(ePackage);
    }
  }

  protected void adjustGenModel(Monitor monitor)
  {
    try
    {
      monitor.beginTask("", 1);

      GenModel genModel = getModelImporter().getGenModel();
      if (editProjectLocationPath != null)
      {
        genModel.setEditDirectory(editProjectLocationPath + "/./" + editFragmentPath + "/.");
      }
      if (editorProjectLocationPath != null)
      {
        genModel.setEditorDirectory(editorProjectLocationPath + "/./" + editorFragmentPath + "/.");
      }
      if (templatePath != null)
      {
        genModel.setTemplateDirectory(templatePath);
        genModel.setDynamicTemplates(true);
      }
      if (copyright != null)
      {
        genModel.setCopyrightText(copyright);
      }
      if (sdo)
      {
        setSDODefaults(genModel);
      }
    }
    finally
    {
      monitor.done();
    }
  }

  protected void setSDODefaults(GenModel genModel)
  {
    Generator.setSDODefaults(genModel);
  }

  protected void doExecute(Monitor monitor) throws Exception
  {
    try
    {
      monitor.beginTask("", 3);
      getModelImporter().prepareGenModelAndEPackages(CodeGenUtil.createMonitor(monitor, 1));
      handleReferencedEPackages();
      getModelImporter().saveGenModelAndEPackages(CodeGenUtil.createMonitor(monitor, 1));
    }
    finally
    {
      monitor.done();
    }
  }
  
  /**
   * Handles the referencedEPackages contributed by the -refPackage argument option.
   */
  protected void handleReferencedEPackages()
  {
    // Add a dummy GenModel for referenced packages.
    //
    if (referencedEPackages != null && !referencedEPackages.isEmpty())
    {
      ModelImporter modelImporter = getModelImporter();
      GenModel genModel = modelImporter.getGenModel();
      Resource genModelResource = genModel.eResource();

      GenModel referencedGenModel = (GenModel)EcoreUtil.create(genModel.eClass()); 
      genModelResource.getContents().add(referencedGenModel);
      referencedGenModel.initialize(referencedEPackages);
      genModel.getUsedGenPackages().addAll(referencedGenModel.getGenPackages());
      referencedGenModel.getForeignModel().addAll(genModel.getForeignModel());
      modelImporter.traverseGenPackages(referencedGenModel.getGenPackages());

      for (Iterator i = referencedGenModel.getGenPackages().iterator(); i.hasNext();)
      {
        GenPackage genPackage = (GenPackage)i.next();
        EPackage ePackage = genPackage.getEcorePackage();
        if (ePackage.eResource() == null)
        {
           modelImporter.addToResource(ePackage, genModelResource.getResourceSet());
        }       
        
        // Special case for a reference to Ecore to ensure that flag settings are respected and are set only for Ecore itself.
        //
        if (EcorePackage.eNS_URI.equals(ePackage.getNsURI()))
        {
          if (referencedGenModel.getGenPackages().size() == 1)
          {
            referencedGenModel.setModelName("ecore");
            referencedGenModel.setBooleanFlagsField("eFlags");
            referencedGenModel.setBooleanFlagsReservedBits(8);
          }
          else
          {
            i.remove();
            referencedGenModel.getUsedGenPackages().add(genPackage);
            GenModel ecoreGenModel = (GenModel)EcoreUtil.create(genModel.eClass());
            genModel.eResource().getContents().add(ecoreGenModel);
            ecoreGenModel.getGenPackages().add(genPackage);
            ecoreGenModel.setBooleanFlagsField("eFlags");
            ecoreGenModel.setBooleanFlagsReservedBits(8);
            ecoreGenModel.getForeignModel().addAll(genModel.getForeignModel());
            ecoreGenModel.setModelName("ecore");
          }
        }
        else if (referencedGenModel.getModelName() == null)
        {
          referencedGenModel.setModelName(genPackage.getPackageName());
        }
      }
    }    
  }
}
