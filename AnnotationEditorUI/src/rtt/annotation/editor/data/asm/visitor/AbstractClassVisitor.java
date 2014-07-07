package rtt.annotation.editor.data.asm.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import rtt.annotation.editor.model.ModelElement;
import rtt.annotations.Parser.Node;
import rtt.annotations.Parser.Node.Compare;
import rtt.annotations.Parser.Node.Informational;

public abstract class AbstractClassVisitor<T extends ModelElement<?>> extends ClassVisitor {

	protected T element;

	public AbstractClassVisitor(T modelElement, ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
		this.element = modelElement;
	}
	
	public T getElement() {
		return element;
	}
}
