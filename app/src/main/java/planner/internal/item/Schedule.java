package planner.internal.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Schedule implements Iterable<Tab>, Serializable{

	private static final long serialVersionUID = -3734613331222933635L;
	private Node head;
	private int size;

	public Schedule() {
		// Head is a dummy node
		head = new Node(null, null);
		size = 0;
	}

	public boolean add(Tab newTab) {
		// Check if schedule is empty
		if (head.next == null) {
			head.next = new Node(newTab, null);
			size++;
			return true;
		}
		// Check if new tab is the earliest on the schedule
		if (head.next.tab.compareTo(newTab) > 0) {
			head.next.tab.compareTo(newTab);
			head.next = new Node(newTab, head.next);
			size++;
			return true;
		}
		Node temp = head.next;
		// Move forward to proper time or the end of the schedule
		while (temp.next != null && temp.next.tab.compareTo(newTab) < 0)
			temp = temp.next;
		// Check if the tab overlaps with the previous
		if (temp.tab.getEnd() > newTab.getStart())
			return false;
		// Add to end
		if (temp.next == null) {
			temp.next = new Node(newTab, null);
			size++;
			return true;
		}
		// Check if the new tab overlaps with the next one
		if (newTab.getEnd() > temp.next.tab.getStart())
			return false;
		// Add the new tab at the proper time
		temp.next = new Node(newTab, temp.next);
		size++;
		return true;
	}
	
	public int addAll(List<Tab> newTabs) {
		int added = 0;
		for (Tab newTab : newTabs) {
			if (add(newTab))
				added++;
		}
		return added;
	}
	
	public List<Tab> getDay(Calendar day) {
		List<Tab> tabs = new ArrayList<Tab>();
		for (Tab tab : this) {
			Calendar start = tab.getStartDate();
			if (day.get(Calendar.YEAR) == start.get(Calendar.YEAR)
					&& day.get(Calendar.MONTH) == start.get(Calendar.MONTH)
					&& day.get(Calendar.WEEK_OF_MONTH) == start.get(Calendar.WEEK_OF_MONTH)
					&& day.get(Calendar.DAY_OF_WEEK) == start.get(Calendar.DAY_OF_WEEK)) {
				tabs.add(tab);
			} else if (start.after(day)) {
				break;
			}
		}
		return tabs;
	}

	public List<Tab> getWeek(Calendar week) {
		List<Tab> tabs = new ArrayList<Tab>();
		for (Tab tab : this) {
			Calendar start = tab.getStartDate();
			if (week.get(Calendar.YEAR) == start.get(Calendar.YEAR)
					&& week.get(Calendar.MONTH) == start.get(Calendar.MONTH)
					&& week.get(Calendar.WEEK_OF_MONTH) == start.get(Calendar.WEEK_OF_MONTH)) {
				tabs.add(tab);
			} else if (start.after(week)) {
				break;
			}
		}
		return tabs;
	}

	public List<Tab> getMonth(Calendar month) {
		List<Tab> tabs = new ArrayList<Tab>();
		for (Tab tab : this) {
			Calendar start = tab.getStartDate();
			if (month.get(Calendar.YEAR) == start.get(Calendar.YEAR)
					&& month.get(Calendar.MONTH) == start.get(Calendar.MONTH)) {
				tabs.add(tab);
			} else if (start.after(month)) {
				break;
			}
		}
		return tabs;
	}
	
	public List<Tab> getYear(Calendar year) {
		List<Tab> tabs = new ArrayList<Tab>();
		for (Tab tab : this) {
			Calendar start = tab.getStartDate();
			if (year.get(Calendar.YEAR) == start.get(Calendar.YEAR)) {
				tabs.add(tab);
			} else if (start.after(year)) {
				break;
			}
		}
		return tabs;
	}
	
	public List<Tab> getAll() {
		List<Tab> tabs = new ArrayList<Tab>();
		for (Tab tab : this) {
			tabs.add(tab);
		}
		return tabs;
	}
	
	public boolean remove(Tab tab) {
		Node temp = head;
		while (temp.next != null) {
			if (temp.next.tab == tab) {
				temp.next = temp.next.next;
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	
	public Tab removeFirst() {
		if (!isEmpty()) { 
			Tab tab = head.next.tab;
			head.next = head.next.next;
			return tab;
		}
		return null;
	}

	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return head.next == null;
	}

	@Override
	public Iterator<Tab> iterator() {
		return new Iterator<Tab>() {
			
			public Node current = head;
			
			@Override
			public boolean hasNext() {
				return current.next != null;
			}

			@Override
			public Tab next() {
				if (!hasNext())
					return null;
				current = current.next;
				return current.tab;
			}
		};
	}
	
	private class Node implements Serializable{

		private static final long serialVersionUID = 4757061294462156453L;
		public Tab tab;
		public Node next;

		public Node(Tab tab, Node next) {
			this.next = next;
			this.tab = tab;
		}

	}
	
	@Override
	public String toString() {
		String str = "";
		for (Tab tab : this) {
			str += tab + "\n";
		}
		return str;
	}
	
	
}
