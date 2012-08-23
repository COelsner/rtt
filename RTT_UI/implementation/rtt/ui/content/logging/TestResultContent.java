package rtt.ui.content.logging;

import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.graphics.Image;

import rtt.core.archive.logging.Comment;
import rtt.core.archive.logging.Failure;
import rtt.core.archive.logging.Result;
import rtt.ui.content.IColumnableContent;
import rtt.ui.content.IContent;
import rtt.ui.content.main.AbstractContent;
import rtt.ui.content.main.ContentIcon;

public class TestResultContent extends AbstractContent implements IColumnableContent {

	private Result result;
	private ContentIcon icon;
	
	public TestResultContent(IContent parent, Result result) {
		super(parent);
		this.result = result;
		icon = getContentIcon(result);
		
		for (Failure failure : result.getFailure()) {
			childs.add(new FailureContent(this, failure));
		}
	}
	
	public String getComment() {
		String commentText = "";
		
		Comment comment = result.getComment();
		if (comment != null && comment.getValue() != null) {
			 commentText = comment.getValue();
		}
		
		return commentText;
	}

	@Override
	public String getText() {
		String text = "[" + result.getTestsuite() + "/" + result.getTestcase() + "]";
		if (result.getComment() != null && !result.getComment().getValue().equals("")) {
			String comment = result.getComment().getValue();
			if (comment.length() > 15) {
				comment = comment.substring(0, 14) + "...";
			}
			
			text += " - " + comment;
		}
		
		return text;
	}

	@Override
	protected ContentIcon getIcon() {
		return icon;
	}
	
	private ContentIcon getContentIcon(Result result) {
		switch (result.getType()) {
		case FAILED:
			return ContentIcon.FAILED;
			
		case PASSED:
			return ContentIcon.PASSED;
			
		case SKIPPED:
			return ContentIcon.SKIPPED;

		default:
			return ContentIcon.PLACEHOLDER;
		}
	}

	@Override
	public String getText(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return result.getType().toString();

		case 1:
			String message = "Testcase: " + result.getTestcase() + " - Testsuite: " + result.getTestsuite();
			
			if (!getComment().equals("")) {
				message += " (" + getComment() + ")";
			}
			
			return message;
			
		default:
			return "";
		}
	}

	@Override
	public Image getImage(int columnIndex, LocalResourceManager resourceManager) {
		if (columnIndex == 0) {
			return getImage(resourceManager);
		}
		
		return null;
	}

	public Result getTestresult() {
		return result;
	}
}
