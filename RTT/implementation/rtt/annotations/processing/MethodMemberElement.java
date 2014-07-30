package rtt.annotations.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import rtt.core.utils.RTTLogging;

final class MethodMemberElement extends	MemberElement<Method> {
	
	private static final String VOID_NOT_ALLOWED = "Ignoring method '$' - A void return type is not allowed";
	private static final String PARAMETERS_NOT_ALLOWED = "Ignoring method '$' - Parameters are not allowed";

	public MethodMemberElement(ClassElement classElement) {
		super(classElement);
	}

	@Override
	protected Map<String, Method> createElements(ClassElement classElement,
			Class<? extends Annotation> annotation) {
		
		Map<String, Method> annotatedMethods = new HashMap<>();
		
		ClassElement parentElement = classElement.getParentElement();
		if (parentElement != null) {
			annotatedMethods.putAll(parentElement.getMethodMap(annotation));
		}
		
		Class<?> objectType = classElement.getType();
		for (Method method : objectType.getDeclaredMethods()) {
			if (method.isAnnotationPresent(annotation) && isAllowed(method)) {
				annotatedMethods.put(method.getName(), method);
			}
		}
		
		return annotatedMethods;
	}

	private boolean isAllowed(Method method) {
		if (method.getReturnType() == Void.TYPE) {
			RTTLogging.warn(VOID_NOT_ALLOWED.replace("$", method.getName()));
			return false;
		}
		
		if (method.getParameterTypes().length > 0) {
			RTTLogging.warn(PARAMETERS_NOT_ALLOWED.replace("$", method.getName()));
			return false;
		}
		
		return true;
	}
}