package rtt.ui.handlers.config;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;

import rtt.core.archive.configuration.Configuration;
import rtt.core.exceptions.RTTException;
import rtt.ui.content.main.ProjectContent;
import rtt.ui.dialogs.ConfigurationDialog;
import rtt.ui.handlers.AbstractSelectionHandler;

public class ConfigAddHandler extends AbstractSelectionHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ProjectContent projectContent = this.getProjectContent(event);
		
		ConfigurationDialog configDialog = new ConfigurationDialog(
				getParentShell(event), projectContent);

		if (configDialog.open() == Dialog.OK) {
			try {
				Configuration config = configDialog.getConfiguration();
				projectContent.addConfiguration(config, configDialog.isDefault());				
			} catch (RTTException e) {
				throw new ExecutionException("Could not add configuration.", e);
			}
		}

		return null;
	}
}
