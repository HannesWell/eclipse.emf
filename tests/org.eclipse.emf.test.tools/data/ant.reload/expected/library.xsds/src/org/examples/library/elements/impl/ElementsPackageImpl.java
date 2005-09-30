/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.examples.library.elements.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.emf.ecore.xml.type.impl.XMLTypePackageImpl;

import org.examples.library.LibraryPackage;

import org.examples.library.elements.Book;
import org.examples.library.elements.BookCategory;
import org.examples.library.elements.ElementsFactory;
import org.examples.library.elements.ElementsPackage;
import org.examples.library.elements.Writer;

import org.examples.library.elements.util.ElementsValidator;

import org.examples.library.hr.HrPackage;

import org.examples.library.hr.impl.HrPackageImpl;

import org.examples.library.impl.LibraryPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ElementsPackageImpl extends EPackageImpl implements ElementsPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final String copyright = "This is my code.";

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass bookEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass writerEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EEnum bookCategoryEEnum = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType bookCategoryObjectEDataType = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EDataType uuidEDataType = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.examples.library.elements.ElementsPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ElementsPackageImpl()
  {
    super(eNS_URI, ElementsFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this
   * model, and for any others upon which it depends.  Simple
   * dependencies are satisfied by calling this method on all
   * dependent packages before doing anything else.  This method drives
   * initialization for interdependent packages directly, in parallel
   * with this package, itself.
   * <p>Of this package and its interdependencies, all packages which
   * have not yet been registered by their URI values are first created
   * and registered.  The packages are then initialized in two steps:
   * meta-model objects for all of the packages are created before any
   * are initialized, since one package's meta-model objects may refer to
   * those of another.
   * <p>Invocation of this method will not affect any packages that have
   * already been initialized.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ElementsPackage init()
  {
    if (isInited) return (ElementsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementsPackage.eNS_URI);

    // Obtain or create and register package
    ElementsPackageImpl theElementsPackage = (ElementsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof ElementsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new ElementsPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    XMLTypePackageImpl.init();

    // Obtain or create and register interdependencies
    HrPackageImpl theHrPackage = (HrPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(HrPackage.eNS_URI) instanceof HrPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(HrPackage.eNS_URI) : HrPackage.eINSTANCE);
    LibraryPackageImpl theLibraryPackage = (LibraryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(LibraryPackage.eNS_URI) instanceof LibraryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(LibraryPackage.eNS_URI) : LibraryPackage.eINSTANCE);

    // Create package meta-data objects
    theElementsPackage.createPackageContents();
    theHrPackage.createPackageContents();
    theLibraryPackage.createPackageContents();

    // Initialize created meta-data
    theElementsPackage.initializePackageContents();
    theHrPackage.initializePackageContents();
    theLibraryPackage.initializePackageContents();

    // Register package validator
    EValidator.Registry.INSTANCE.put
      (theElementsPackage, 
       new EValidator.Descriptor()
       {
         public EValidator getEValidator()
         {
           return ElementsValidator.INSTANCE;
         }
       });

    // Mark meta-data to indicate it can't be changed
    theElementsPackage.freeze();

    return theElementsPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getBook()
  {
    return bookEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBook_Title()
  {
    return (EAttribute)bookEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBook_Pages()
  {
    return (EAttribute)bookEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBook_Category()
  {
    return (EAttribute)bookEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBook_Author()
  {
    return (EAttribute)bookEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getBook_Uuid()
  {
    return (EAttribute)bookEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getWriter()
  {
    return writerEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getWriter_Books()
  {
    return (EAttribute)writerEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EEnum getBookCategory()
  {
    return bookCategoryEEnum;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getBookCategoryObject()
  {
    return bookCategoryObjectEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EDataType getUUID()
  {
    return uuidEDataType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ElementsFactory getElementsFactory()
  {
    return (ElementsFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    bookEClass = createEClass(BOOK);
    createEAttribute(bookEClass, BOOK__TITLE);
    createEAttribute(bookEClass, BOOK__PAGES);
    createEAttribute(bookEClass, BOOK__CATEGORY);
    createEAttribute(bookEClass, BOOK__AUTHOR);
    createEAttribute(bookEClass, BOOK__UUID);

    writerEClass = createEClass(WRITER);
    createEAttribute(writerEClass, WRITER__BOOKS);

    // Create enums
    bookCategoryEEnum = createEEnum(BOOK_CATEGORY);

    // Create data types
    bookCategoryObjectEDataType = createEDataType(BOOK_CATEGORY_OBJECT);
    uuidEDataType = createEDataType(UUID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    XMLTypePackageImpl theXMLTypePackage = (XMLTypePackageImpl)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
    HrPackageImpl theHrPackage = (HrPackageImpl)EPackage.Registry.INSTANCE.getEPackage(HrPackage.eNS_URI);

    // Add supertypes to classes
    writerEClass.getESuperTypes().add(theHrPackage.getPerson());

    // Initialize classes and features; add operations and parameters
    initEClass(bookEClass, Book.class, "Book", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getBook_Title(), theXMLTypePackage.getString(), "title", null, 1, 1, Book.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBook_Pages(), theXMLTypePackage.getInt(), "pages", null, 1, 1, Book.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBook_Category(), this.getBookCategory(), "category", "Mystery", 1, 1, Book.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBook_Author(), theXMLTypePackage.getAnyURI(), "author", null, 1, 1, Book.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getBook_Uuid(), this.getUUID(), "uuid", null, 0, 1, Book.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(writerEClass, Writer.class, "Writer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getWriter_Books(), theXMLTypePackage.getAnyURI(), "books", null, 0, -1, Writer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Initialize enums and add enum literals
    initEEnum(bookCategoryEEnum, BookCategory.class, "BookCategory");
    addEEnumLiteral(bookCategoryEEnum, BookCategory.MYSTERY_LITERAL);
    addEEnumLiteral(bookCategoryEEnum, BookCategory.SCIENCE_FICTION_LITERAL);
    addEEnumLiteral(bookCategoryEEnum, BookCategory.BIOGRAPHY_LITERAL);
    addEEnumLiteral(bookCategoryEEnum, BookCategory.IT_LITERAL);

    // Initialize data types
    initEDataType(bookCategoryObjectEDataType, BookCategory.class, "BookCategoryObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
    initEDataType(uuidEDataType, byte[].class, "UUID", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

    // Create resource
    createResource(eNS_URI);

    // Create annotations
    // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
    createExtendedMetaDataAnnotations();
  }

  /**
   * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createExtendedMetaDataAnnotations()
  {
    String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
    addAnnotation
      (bookEClass, 
       source, 
       new String[] 
       {
       "name", "Book",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getBook_Title(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "title"
       });		
    addAnnotation
      (getBook_Pages(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "pages"
       });		
    addAnnotation
      (getBook_Category(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "category"
       });		
    addAnnotation
      (getBook_Author(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "author"
       });		
    addAnnotation
      (getBook_Uuid(), 
       source, 
       new String[] 
       {
       "kind", "attribute",
       "name", "uuid"
       });		
    addAnnotation
      (bookCategoryEEnum, 
       source, 
       new String[] 
       {
       "name", "BookCategory"
       });		
    addAnnotation
      (bookCategoryObjectEDataType, 
       source, 
       new String[] 
       {
       "name", "BookCategory:Object",
       "baseType", "BookCategory"
       });		
    addAnnotation
      (uuidEDataType, 
       source, 
       new String[] 
       {
       "name", "UUID",
       "baseType", "http://www.eclipse.org/emf/2003/XMLType#hexBinary",
       "length", "16"
       });		
    addAnnotation
      (writerEClass, 
       source, 
       new String[] 
       {
       "name", "Writer",
       "kind", "elementOnly"
       });		
    addAnnotation
      (getWriter_Books(), 
       source, 
       new String[] 
       {
       "kind", "element",
       "name", "books"
       });
  }

} //ElementsPackageImpl
