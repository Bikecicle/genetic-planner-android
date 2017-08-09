package planner.internal.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Item implements Serializable {
	
	private static final long serialVersionUID = -7943195019888260373L;
	protected String title;
	protected String details;

	protected List<Tab> tabs;

	public Item(String title, String details) {
		this.title = title;
		this.details = details;
		this.tabs = new ArrayList<Tab>();
	}
	
	public abstract List<Tab> generateTabs(long[] gene);
	
	public abstract void complete(Tab tab);

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}
	
	public void clean() {
		tabs.clear();
	}

}
