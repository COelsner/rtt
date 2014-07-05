package rtt.annotation.editor.data.asm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import rtt.annotation.editor.controller.rules.Annotation;
import rtt.annotation.editor.model.ClassElement;
import rtt.annotation.editor.model.ClassModel;

final class ReadFileWalker extends AbstractFileWalker {

	static final class ClassElementAnnotationAdapter extends ClassVisitor {
		
		private ClassElement element = null;

		protected ClassElementAnnotationAdapter() {
			super(Opcodes.ASM5);
		}
		
		public ClassElementAnnotationAdapter setElement(ClassElement element) {
			this.element = element;
			return this;
		}

		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			if (NODE_DESC.equals(desc) && element != null) {
				element.setAnnotation(Annotation.NODE);
			}

			return super.visitAnnotation(desc, visible);
		}
	}
	
	public ReadFileWalker(ClassModel model) {
		super(model);
	}
	
	@Override
	protected void processData(Path file) throws IOException {
		ClassReader reader = new ClassReader(Files.readAllBytes(file));
		ClassElementAnnotationAdapter adapter = new ClassElementAnnotationAdapter();
		
		String className = computeClassName(reader.getClassName());
		String packageName = computePackageName(reader.getClassName());
		
		ClassElement classElement = factory.createClassElement(model);
		classElement.setName(className);
		classElement.setPackageName(packageName);
		
		// FIXME read interfaces and super class 
//		classElement.setInterfaces(reader.getInterfaces());
//		classElement.setSuperClass(reader.getSuperName());
		
		reader.accept(adapter.setElement(classElement), ClassReader.SKIP_CODE);
		
		model.addClassElement(classElement);
	}
	
	
}