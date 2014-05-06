/* This file was generated with JastAdd2 (http://jastadd.org) version R20121112 (r872) */
package siple.ast;

import java.util.*;

import rtt.annotations.Parser;
import siple.*;
import siple.semantics.*;
import siple.semantics.State.*;

/**
 * @production UMinus : {@link UnaryExpression};
 * @ast node
 * @declaredat ./../../specifications/semantics/ast.ast:45
 */
public class UMinus extends UnaryExpression implements Cloneable {
	/**
	 * @apilevel low-level
	 */
	public void flushCache() {
		super.flushCache();
		Type_visited = -1;
	}

	/**
	 * @apilevel internal
	 */
	public void flushCollectionCache() {
		super.flushCollectionCache();
	}

	/**
	 * @apilevel internal
	 */
	@SuppressWarnings({ "unchecked", "cast" })
	public UMinus clone() throws CloneNotSupportedException {
		UMinus node = (UMinus) super.clone();
		node.Type_visited = -1;
		node.in$Circle(false);
		node.is$Final(false);
		return node;
	}

	/**
	 * @apilevel internal
	 */
	@SuppressWarnings({ "unchecked", "cast" })
	public UMinus copy() {
		try {
			UMinus node = (UMinus) clone();
			if (children != null)
				node.children = (ASTNode[]) children.clone();
			return node;
		} catch (CloneNotSupportedException e) {
		}
		System.err.println("Error: Could not clone node of type "
				+ getClass().getName() + "!");
		return null;
	}

	/**
	 * Create a deep copy of the AST subtree at this node. The copy is dangling,
	 * i.e. has no parent.
	 * 
	 * @return dangling copy of the subtree at this node
	 * @apilevel low-level
	 */
	@SuppressWarnings({ "unchecked", "cast" })
	public UMinus fullCopy() {
		try {
			UMinus tree = (UMinus) clone();
			tree.setParent(null);// make dangling
			if (children != null) {
				tree.children = new ASTNode[children.length];
				for (int i = 0; i < children.length; ++i) {
					if (children[i] == null) {
						tree.children[i] = null;
					} else {
						tree.children[i] = ((ASTNode) children[i]).fullCopy();
						((ASTNode) tree.children[i]).setParent(tree);
					}
				}
			}
			return tree;
		} catch (CloneNotSupportedException e) {
			throw new Error("Error: clone not supported for "
					+ getClass().getName());
		}
	}

	/**
	 * @ast method
	 * @aspect Interpretation
	 * @declaredat ./../../specifications/semantics/Interpretation.jrag:157
	 */
	public Object Value(State vm) {
		if (!IsCorrectLocal())
			throw new InterpretationException();
		switch (Type().domain) {
		case Integer:
			return -(Integer) getOperand().Value(vm);
		default:
			return -(Float) getOperand().Value(vm);
		}
	}

	/**
	 * @ast method
	 * 
	 */
	public UMinus() {
		super();

	}

	/**
	 * @ast method
	 * 
	 */
	public UMinus(Expression p0) {
		setChild(p0, 0);
	}

	/**
	 * @apilevel low-level
	 * @ast method
	 * 
	 */
	protected int numChildren() {
		return 1;
	}

	/**
	 * @apilevel internal
	 * @ast method
	 * 
	 */
	public boolean mayHaveRewrite() {
		return false;
	}

	/**
	 * Replaces the Operand child.
	 * 
	 * @param node
	 *            The new node to replace the Operand child.
	 * @apilevel high-level
	 * @ast method
	 * 
	 */
	public void setOperand(Expression node) {
		setChild(node, 0);
	}

	/**
	 * Retrieves the Operand child.
	 * 
	 * @return The current node used as the Operand child.
	 * @apilevel high-level
	 * @ast method
	 * 
	 */
	public Expression getOperand() {
		return (Expression) getChild(0);
	}

	/**
	 * Retrieves the Operand child.
	 * <p>
	 * <em>This method does not invoke AST transformations.</em>
	 * </p>
	 * 
	 * @return The current node used as the Operand child.
	 * @apilevel low-level
	 * @ast method
	 * 
	 */
	public Expression getOperandNoTransform() {
		return (Expression) getChildNoTransform(0);
	}

	/**
	 * @apilevel internal
	 */
	protected int Type_visited = -1;

	/**
	 * @attribute syn
	 * @aspect TypeAnalysis
	 * @declaredat ./../../specifications/semantics/TypeAnalysis.jrag:80
	 */
	@SuppressWarnings({ "unchecked", "cast" })
	@Parser.Node.Compare
	public Type Type() {
		ASTNode$State state = state();
		if (Type_visited == state().boundariesCrossed)
			throw new RuntimeException(
					"Circular definition of attr: Type in class: ");
		Type_visited = state().boundariesCrossed;
		Type Type_value = Type_compute();
		Type_visited = -1;
		return Type_value;
	}

	/**
	 * @apilevel internal
	 */
	private Type Type_compute() {
		return getOperand().Type().isReal() || getOperand().Type().isInteger() ? getOperand()
				.Type() : Type.ERROR_TYPE;
	}

	/**
	 * @apilevel internal
	 */
	public ASTNode rewriteTo() {
		return super.rewriteTo();
	}
}