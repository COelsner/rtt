package rtt.ui.content.logging;

import java.util.Calendar;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;

import rtt.core.archive.logging.Detail;
import rtt.core.archive.logging.Entry;
import rtt.core.archive.logging.EntryType;
import rtt.ui.content.IClickableContent;
import rtt.ui.content.IColumnableContent;
import rtt.ui.content.IContent;
import rtt.ui.content.main.AbstractContent;
import rtt.ui.content.main.ContentIcon;

public class LogEntryContent extends AbstractContent implements
		IColumnableContent, IClickableContent {

	private Entry entry;
	protected Calendar calendar;
	
	public LogEntryContent(IContent parent, Entry entry) {
		super(parent);
		this.entry = entry;
		
		calendar = DatatypeConverter.parseDate(entry.getDate().toXMLFormat());
		
		if (entry.getDetail() != null && !entry.getDetail().isEmpty()) {
			for (Detail detail : entry.getDetail()) {
				childs.add(new LogDetailContent(this, detail));
			}
		}
	}

	private ContentIcon getContentIcon(Entry entry) {
		switch (entry.getType()) {
		case INFO:
			return ContentIcon.INFO;
			
		case GENERATION:
			return ContentIcon.GENERATION;
			
		case TESTRUN:
			return ContentIcon.TESTRUN;
			
		case ARCHIVE:
			return ContentIcon.ARCHIVE;
			
		default:
			return ContentIcon.PLACEHOLDER;
		}
	}

	public EntryType getType() {
		return entry.getType();
	}

	@Override
	public String getText() {
		return "LogEntry";
	}

	@Override
	protected ContentIcon getIcon() {
		return getContentIcon(entry);
	}

	@Override
	public String getText(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return entry.getType().toString();

		case 1:
			return entry.getMsg() + " " + entry.getSuffix();

		case 2:
			return getFormatedDate();

		default:
			return "";
		}
	}
	
	public Calendar getCalendar() {
		return calendar;
	}
	
	public String getFormatedDate() {
		return String.format("%1$te.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", calendar);
	}

	@Override
	public Image getImage(int columnIndex, LocalResourceManager resourceManager) {
		if (columnIndex == 0) {
			return getImage(resourceManager);
		}
		
		return null;
	}

	@Override
	public void doDoubleClick(IWorkbenchPage currentPage) {
		MessageDialog.openInformation(currentPage.getWorkbenchWindow()
				.getShell(), entry.getType().toString(),
				entry.getMsg() + entry.getSuffix());
	}
}
