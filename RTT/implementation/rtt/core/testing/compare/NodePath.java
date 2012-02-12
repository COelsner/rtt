package rtt.core.testing.compare;

import java.util.LinkedList;
import java.util.List;

import rtt.core.archive.output.Node;
import rtt.core.manager.Printer;

public class NodePath {

	List<Node> path;

	public NodePath(Node root) {
		path = new LinkedList<Node>();
		path.add(root);
	}

	public String getXPath(Integer attributePos) {

		String xp = "/archiveLog";
		Integer curPos = null;
		Node curNode = null;
		for (int i = 0; i < path.size(); i++) {
			curNode = path.get(i);

			xp = xp + "/node";
			if (curPos != null) {
				xp += "[" + (curPos + 1) + "]";
			}

			if (i != path.size() - 1)
				xp += "/children";

			curPos = null;

			if (i < path.size() - 1
					&& curNode.getChildren().getNode().size() > 1)
				curPos = curNode.getChildren().getNode().indexOf(
						path.get(i + 1));

		}

		if (attributePos != null) {
			xp += "/nodeAttribute";
			if (curNode.getAttributes().getAttribute().size() > 1)
				xp += "[" + (attributePos + 1) + "]";
		}

		return xp;
	}

	NodePath(List<Node> path, Node newNode) {
		this.path = new LinkedList<Node>();
		this.path.addAll(path);
		this.path.add(newNode);
	}

	public NodePath concat(Node newNode) {
		return new NodePath(path, newNode);
	}

	@Override
	public String toString() {
		String offset = new String();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < path.size(); i++) {
			builder.append(offset + Printer.PrintNode(path.get(i)) + "\n");
			offset += " ";

		}
		return builder.toString();

	}
	
}