package utd.project.campus.bot.knowledge.processor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import utd.project.campus.bot.knowledge.model.Category;

public class KnowledgeGraphMaster {
	private final Map<String, KnowledgeGraphMaster> children = new HashMap<String, KnowledgeGraphMaster>();
	private int size = 0;
	private Category category;
	private String name;

	private KnowledgeGraphMaster(String name) {
		this.name = name;
	}

	public KnowledgeGraphMaster() {
	}

	public KnowledgeGraphMaster(List<Category> categories) {
		append(categories);
	}

	private void append(Category category, String[] elements, int index) {
		KnowledgeGraphMaster child = children.get(elements[index]);
		if (child == null)
			appendChild(child = new KnowledgeGraphMaster(elements[index]));

		int nextIndex = index + 1;
		if (elements.length <= nextIndex)
			child.category = category;
		else
			child.append(category, elements, nextIndex);
	}

	private void appendChild(KnowledgeGraphMaster child) {
		children.put(child.name, child);
	}

	private KnowledgeGraphMaster[] children(String name) {
		return new KnowledgeGraphMaster[] { children.get("_"), children.get(name), children.get("*") };
	}

	private boolean isWildcard() {
		return ("_".equals(name) || "*".equals(name));
	}

	private Category match(Matcher match, int index) {
		if (isWildcard())
			return matchWildcard(match, index);

		if (!name.equals(match.getMatchPath(index)))
			return null;

		int nextIndex = index + 1;
		if (match.getMatchPathLength() <= nextIndex)
			return category;

		return matchChildren(match, nextIndex);
	}

	private Category matchChildren(Matcher match, int nextIndex) {
		KnowledgeGraphMaster[] nodes = children(match.getMatchPath(nextIndex));
		for (int i = 0, n = nodes.length; i < n; i++) {
			Category category = (nodes[i] != null ? nodes[i].match(match, nextIndex) : null);

			if (category != null)
				return category;
		}

		return null;
	}

	private Category matchWildcard(Matcher match, int index) {
		int n = match.getMatchPathLength();
		for (int i = index; i < n; i++) {
			Category category = matchChildren(match, i);
			if (category != null) {
				match.appendWildcard(index, i);
				return category;
			}
		}

		if (category != null)
			match.appendWildcard(index, n);
		return category;
	}

	public void append(List<Category> categories) {
		for (Category category : categories)
			append(category);
	}

	public void append(Category category) {
		String matchPath[] = category.getMatchPath();
		append(category, matchPath, 0);
		size++;
	}

	public Category match(Matcher match) {
		return matchChildren(match, 0);
	}

	public int size() {
		return size;
	}

	public String toString(String prefix) {
		StringBuffer display = new StringBuffer();
		display.append(name + " : ").append("\n");
		for (Iterator<Map.Entry<String, KnowledgeGraphMaster>> childrenIterator = children.entrySet().iterator(); childrenIterator.hasNext();) {
			Map.Entry<String, KnowledgeGraphMaster> childEntry = childrenIterator.next();
			display.append(prefix).append(childEntry.getValue().toString(prefix + "\t"));
			
			if (childrenIterator.hasNext()) {
				display.append("\n");
			}
		}
		return display.toString();
	}
}
