package rtt.ui.content.testsuite;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import rtt.ui.RttPluginUI;
import rtt.ui.content.IClickableContent;
import rtt.ui.content.IContent;
import rtt.ui.content.main.AbstractContent;
import rtt.ui.content.main.ContentIcon;
import rtt.ui.editors.ReferenceEditor;
import rtt.ui.editors.input.ReferenceEditorInput;

public class ReferenceContent extends AbstractContent implements
		IClickableContent {

	private String suiteName;
	private String caseName;
	private int version;

	public ReferenceContent(IContent parent, String suiteName, String caseName,
			int version) {
		super(parent);
		this.suiteName = suiteName;
		this.caseName = caseName;
		this.version = version;
	}

	@Override
	public void doDoubleClick(IWorkbenchPage currentPage) {

		try {
			IEditorPart part = IDE.openEditor(currentPage,
					new ReferenceEditorInput(getProject(), suiteName, caseName,
							version), ReferenceEditor.ID, true);
			
			if (part instanceof ReferenceEditor) {
				((ReferenceEditor) part)
						.setActivePage(ReferenceEditor.PARSER_PAGE_ID);
			}
		} catch (PartInitException e) {
			ErrorDialog.openError(currentPage.getActivePart().getSite()
					.getShell(), "Error", "Could not open editor", new Status(
					Status.ERROR, RttPluginUI.PLUGIN_ID, e.getMessage(), e));
		}
	}

	@Override
	public String getText() {
		return "Reference (" + version + ")";
	}

	@Override
	protected ContentIcon getIcon() {
		return ContentIcon.REFERENCE;
	}

}
