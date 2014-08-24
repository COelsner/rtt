package rtt.core.manager;

import rtt.core.archive.output.Element;

public class Printer {

	static public String printElement(Element element) {
		StringBuilder builder = new StringBuilder();
		builder.append("Node: ");
		
		if (element == null) {
			builder.append("<null>");
			return builder.toString();
		}		
		
		builder.append(element.getGeneratorName());
		builder.append(" - ");
		builder.append(element.getGeneratorType());
		
		if (element.isInformational()) {
			builder.append(" [INFORMATIONAL]");
		}
	
		return builder.toString();
	}

}
