package rtt.ui.content.history;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import rtt.core.archive.history.Version;
import rtt.core.manager.data.IHistoryManager;
import rtt.core.manager.data.OutputDataManager;
import rtt.ui.RttPluginUI;
import rtt.ui.content.IClickableContent;
import rtt.ui.content.IContent;
import rtt.ui.editors.ReferenceEditor;
import rtt.ui.editors.input.ReferenceEditorInput;

public class ReferenceVersionContent extends AbstractVersionContent<OutputDataManager> implements IClickableContent {

	public ReferenceVersionContent(IContent parent, Version version,
			IHistoryManager manager) {
		super(parent, version, (OutputDataManager) manager);
	}
	@Override
	public String getText() {
		return "Reference";
	}

	@Override
	public void doDoubleClick(IWorkbenchPage currentPage) {
		try {
			IEditorPart part = IDE.openEditor(currentPage, new ReferenceEditorInput(
					manager, version.getNr()), ReferenceEditor.ID, true);

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

}
