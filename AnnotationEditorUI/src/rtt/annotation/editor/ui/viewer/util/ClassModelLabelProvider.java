package rtt.annotation.editor.ui.viewer.util;

import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import rtt.annotation.editor.model.ClassElement;
import rtt.annotation.editor.model.ClassElement.ClassType;
import rtt.annotation.editor.model.ClassModel;
import rtt.annotation.editor.model.ClassModel.PackageElement;
import rtt.annotation.editor.ui.viewer.util.ClassModelContentProvider.Detail;

public class ClassModelLabelProvider extends LabelProvider implements ILabelProvider, IFontProvider, IColorProvider {

	private static final Color BLUE = JFaceResources.getColorRegistry().get(JFacePreferences.HYPERLINK_COLOR);
	private static final Font ITALIC_FONT = JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT);
	
	@Override
	public String getText(Object element) {
		if (element instanceof ClassModel) {
			return ((ClassModel) element).toString();
		}
		
		if (element instanceof PackageElement) {
			return ((PackageElement) element).getName();
		}
		
		if (element instanceof ClassElement) {
			ClassElement classElement = (ClassElement) element;
			
			StringBuilder builder = new StringBuilder();
			builder.append(classElement.getName());
			
			switch (classElement.getType()) {
			case INTERFACE:
				builder.append(" [I]");
				break;
				
			case ABSTRACT:
				builder.append(" [A]");
				break;
				
			default:
				break;
			}
	
			return builder.toString();
		}
		
		if (element instanceof Detail) {
			return ((Detail) element).label;
		}
		
		return super.getText(element);
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof ClassElement) {
			ClassElement classElement = (ClassElement) element;
			if (classElement.hasAnnotation()) {
				return BLUE;
			}			
		}
		
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	@Override
	public Font getFont(Object element) {
		if (element instanceof ClassElement) {
			ClassElement classElement = (ClassElement) element;			
			
			if (classElement.getType() == ClassType.INTERFACE ||
					classElement.getType() == ClassType.ABSTRACT) {
				return ITALIC_FONT;
			}
		}
		return null;
	}
}
