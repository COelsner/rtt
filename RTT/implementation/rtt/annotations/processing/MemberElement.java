package rtt.annotations.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

abstract class MemberElement<T extends AnnotatedElement> {
		private Map<Class<? extends Annotation>, Map<String, T>> elements;
		private ClassElement classElement;
		
		public MemberElement(ClassElement classElement) {
			this.elements = new HashMap<>();
			this.classElement = classElement;
		}
		
		public Map<String, T> getAnnotatedElements(
				Class<? extends Annotation> annotation) {
			
			if (!elements.containsKey(annotation)) {				
				elements.put(annotation, 
						createElements(classElement, annotation));
			}
			
			return elements.get(annotation);
		}

		protected abstract Map<String, T> createElements(
				ClassElement classElement,
				Class<? extends Annotation> annotation);
	}