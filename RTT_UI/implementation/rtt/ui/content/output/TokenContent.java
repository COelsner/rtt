package rtt.ui.content.output;

import java.util.ArrayList;
import java.util.List;

import rtt.core.archive.output.Attribute;
import rtt.core.archive.output.Token;
import rtt.ui.content.AbstractContent;
import rtt.ui.content.IContent;
import rtt.ui.content.internal.ContentIcon;
import rtt.ui.content.internal.SimpleTypedContent;
import rtt.ui.content.internal.SimpleTypedContent.ContentType;

public class TokenContent extends AbstractContent implements IContent {
	
	private Token token;
	
	public TokenContent(IContent parent, Token token) {
		super(parent);
		
		this.token = token;
		
		List<IContent> attributes = new ArrayList<IContent>();
		if (token.getAttributes() != null) {
			for (Attribute attribute : token.getAttributes().getAttribute()) {
				attributes.add(new AttributeContent(this, attribute));
			}
		}
		childs.add(new SimpleTypedContent(this, ContentType.ATTRIBUTES, attributes));
	}

	@Override
	public String getText() {
		return "<Token> " + token.getSimpleName();
	}

	@Override
	protected ContentIcon getIcon() {
		return ContentIcon.TOKEN;
	}

	public Token getToken() {
		return token;
	}

}
