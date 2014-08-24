package rtt.annotations.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationProcessor {
	
	private static final AnnotationProcessor INSTANCE = 
			new AnnotationProcessor();
	
	private Map<Class<?>, ClassElement> vistedClasses;
	
	public AnnotationProcessor() {
		vistedClasses = new HashMap<>();
	}
	
	public ClassElement getElement(Class<?> objectType) {
		if (objectType == null) {
			throw new IllegalArgumentException("Object type must not be null.");
		}
		
		if (!vistedClasses.containsKey(objectType)) {
			vistedClasses.put(objectType, createClassElement(objectType));
		}
		
		return vistedClasses.get(objectType);
	}

	private ClassElement createClassElement(Class<?> objectType) {
		Class<?> superClass = objectType.getSuperclass();
		if (superClass != null && superClass != Object.class) {
			return new ClassElement(objectType, getElement(superClass));
		} else {
			return new ClassElement(objectType);
		}				
	}

	/**
	 * <p>Returns *all* {@link Constructor}s of a given object type, 
	 * which contain the given type of {@link Annotation}.</p>
	 * @param objectType the type of object 
	 * @param annotationType the type of annotation
	 * @return a {@link List} of {@link Constructor}s
	 */
	public static List<Constructor<?>> getConstructors(Class<?> objectType,
			Class<? extends Annotation> annotation) {
		
		ClassElement element = INSTANCE.getElement(objectType);
		return element.getConstructors(annotation);		
	}
	
	/**
	 * <p>Returns *all* {@link Method}s of a given object type, 
	 * which contain the given type of {@link Annotation}.</p>
	 * @param objectType the type of object 
	 * @param annotationType the type of annotation
	 * @return a {@link List} of {@link Method}s
	 */
	public static List<Method> getMethods(Class<?> objectType,
			Class<? extends Annotation> annotation) {

		ClassElement element = INSTANCE.getElement(objectType);
		return element.getMethods(annotation);		
	}
	
	/**
	 * <p>Returns a *single* {@link Method} which contains the given {@link Annotation}.<br />
	 * <i>Note</i>: if more than one {@link Method} was found, <code>null</code> will be returned.</p>
	 * @param objectType the type of object
	 * @param annotationType the type of annotation
	 * @return a {@link Method} object or <code>null</code>.
	 */
	public static Method getSingleMethod(Class<?> objectType, Class<? extends Annotation> annotationType) {
		List<Method> annotatedMethods = getMethods(objectType, annotationType);
		if (annotatedMethods != null && annotatedMethods.size() == 1) {
			return annotatedMethods.get(0);
		}
		
		return null;
	}

	/**
	 * <p>Returns *all* {@link Field}s of a given object type, 
	 * which contain the given type of {@link Annotation}.</p>
	 * @param objectType the type of object 
	 * @param annotationType the type of annotation
	 * @return a {@link List} of {@link Field}s
	 */
	public static List<Field> getFields(Class<?> objectType,
			Class<? extends Annotation> annotation) {
		
		ClassElement element = INSTANCE.getElement(objectType);
		return element.getFields(annotation);		
	}
	
	/**
	 * Returns an actual annotation of a given class.
	 * @param objectType
	 * @param annotationType
	 * @return an {@link Annotation}
	 */
	public static <A extends Annotation> A getAnnotation(
			Class<?> objectType, Class<A> annotationType) {
		
		if (objectType.isAnnotationPresent(annotationType)) {
			return objectType.getAnnotation(annotationType);
		}

		for (Class<?> interfaceObject : objectType.getInterfaces()) {
			if (interfaceObject.isAnnotationPresent(annotationType)) {
				return interfaceObject.getAnnotation(annotationType);
			}
		}

		return null;
	}

	/**
	 * Returns {@code true}, if class has specified {@link Annotation}.
	 * @param objectType
	 * @param annotationType
	 * @return {@code true}, if class has {@link Annotation}.
	 */
	public static boolean hasAnnotation(Class<?> objectType, 
			Class<? extends Annotation> annotationType) {
		
		return getAnnotation(objectType, annotationType) != null;
	}

}
