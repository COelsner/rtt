package rtt.annotation.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import rtt.annotation.editor.model.ClassElement;
import rtt.annotation.editor.model.ClassElement.ClassType;
import rtt.annotation.editor.model.ClassElementReference;
import rtt.annotation.editor.model.ClassModelFactory;
import rtt.annotation.editor.model.ElementReference;
import rtt.annotation.editor.model.FieldElement;
import rtt.annotation.editor.model.MethodElement;

public class ClassElementTests {

	private static final String PACKAGENAME = "abc.def.ghi";
	private static final String CLASSNAME = "TestClassName";	
	private static final String SUPERCLASS = "x.y.z.SuperClass";
	
	private ClassElement element;
	private ClassModelFactory factory;
	
	@Before
	public void setUp() throws Exception { 
		factory = ClassModelFactory.getFactory();
		element = factory.createClassElement(null);		
	}
	
	@Test
	public void testEmptyElement() throws Exception {
		assertEquals("Class name", null, element.getName());
		assertEquals("Package name", null, element.getPackageName());
		assertEquals("Type", ClassType.CONCRETE, element.getType());
		
		assertFalse(element.hasInterfaces());
		assertNull(element.getInterfaces());
		
		assertFalse(element.hasSuperClass());
		assertNull(element.getSuperClass());
		
		assertTrue(checkFieldsSize(0));
		assertTrue(checkMethodsSize(0));
	}
	
	private boolean checkFieldsSize(int size) {
		return element.getFields() != null 
				&& element.getFields().size() == size;
	}

	private boolean checkMethodsSize(int size) {
		return element.getMethods() != null 
				&& element.getMethods().size() == size;
	}
	
	@Test
	public void testSetSimpleProperties() throws Exception {
		element.setName(CLASSNAME);		
		assertEquals("Class name", CLASSNAME, element.getName());
		
		element.setPackageName(PACKAGENAME);		
		assertEquals("Package name", PACKAGENAME, element.getPackageName());
		
		element.setType(ClassType.ABSTRACT);
		assertEquals("Class Type", ClassType.ABSTRACT, element.getType());
		
		element.setSuperClass(createSuperClass(SUPERCLASS));		
		assertTrue(element.hasSuperClass());
		assertEquals("Superclass", createSuperClass(SUPERCLASS), element.getSuperClass());
		
		List<ClassElementReference> interfaces = createInterfaces("InterfaceA", "InterfaceB");
		element.setInterfaces(interfaces);
		assertTrue(element.hasInterfaces());
		assertEquals("Interfaces", interfaces, element.getInterfaces());
	}
	
	private ClassElementReference createSuperClass(String superClassName) {
		return new ClassElementReference(superClassName);
	}
	
	private List<ClassElementReference> createInterfaces(String... interfaces) {
		List<ClassElementReference> result = new ArrayList<ClassElementReference>();
		for (String interfaceName : interfaces) {
			result.add(new ClassElementReference(interfaceName));
		}
		
		return result;
	}
	
	@Test
	public void testAddField() throws Exception {
		String fieldName = "TestFieldName";
		
		FieldElement field = factory.createFieldElement(element, fieldName);
		
		int oldSize = element.getFields().size();		
		element.addField(field);
		int newSize = element.getFields().size();
		assertEquals("Field list size", oldSize + 1, newSize);
		
		assertTrue("Field list contains", element.getFields().contains(field));
		
		FieldElement field2 = factory.createFieldElement(element, fieldName);
		
		oldSize = newSize;
		element.addField(field2);
		newSize = element.getFields().size();
		assertEquals("Field list size", oldSize, newSize);
	}
	
	@Test
	public void testAddMethod() throws Exception {
		String methodName = "TestMethodName";
		
		MethodElement method = factory.createMethodElement(element, methodName);
		
		int oldSize = element.getMethods().size();
		element.addMethod(method);
		int newSize = element.getMethods().size();
		assertEquals("Method list size", oldSize + 1, newSize);
		
		assertTrue("Method List contains", element.getMethods().contains(method));
		
		MethodElement method2 = factory.createMethodElement(element, methodName);
		
		oldSize = newSize;
		element.addMethod(method2);
		newSize = element.getMethods().size();
		assertEquals("Method list size", oldSize, newSize);
	}
	
	@Test
	public void testEquals() throws Exception {
		element.setName(CLASSNAME);
		element.setPackageName(PACKAGENAME);
		element.setType(ClassType.ABSTRACT);
		
		ClassElement element2 = factory.createClassElement(null);
		assertFalse(checkEqual(element2));
		
		element2.setName(CLASSNAME);
		assertFalse(checkEqual(element2));
		
		element2.setPackageName(PACKAGENAME);
		assertFalse(checkEqual(element2));
		
		element2.setType(ClassType.ABSTRACT);
		assertTrue(checkEqual(element2));
		
		element.setSuperClass(createSuperClass(SUPERCLASS));
		assertFalse(checkEqual(element2));
		element2.setSuperClass(createSuperClass(SUPERCLASS));
		assertTrue(checkEqual(element2));
		
		element.setInterfaces(createInterfaces("IntA", "IntB"));
		assertFalse(checkEqual(element2));
		element2.setInterfaces(createInterfaces("IntA", "IntB"));
		assertTrue(checkEqual(element2));
	}

	private boolean checkEqual(ClassElement element2) {
		return element.equals(element2) && element2.equals(element);
	}
}
