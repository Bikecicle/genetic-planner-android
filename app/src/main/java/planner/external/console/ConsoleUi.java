package planner.external.console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import planner.internal.core.C;
import planner.internal.core.PlanningAssistant;
import planner.internal.data.FileSystem;
import planner.internal.item.Event;
import planner.internal.item.Tab;
import planner.internal.item.Task;
import planner.internal.recurrence.Marker;
import planner.internal.recurrence.Recurrence;

public class ConsoleUi {

	private PlanningAssistant planningAssistant;
	private Scanner in;

	private Calendar current;

	public ConsoleUi() {
		planningAssistant = PlanningAssistant.getInstance();
		planningAssistant.setDataManager(new FileSystem());
		in = new Scanner(System.in);
		current = Calendar.getInstance();
	}

	private void menu() {
		System.out.println("View: focus, day, week, month, agenda");
		System.out.println("Actions: add, edit, reschedule, clear, save, exit");
		String action = in.nextLine();
		if (action.equals("focus")) {
			focusView();
		} else if (action.equals("day")) {
			dayView();
		} else if (action.equals("week")) {
			weekView();
		} else if (action.equals("month")) {
			monthView();
		} else if (action.equals("all")) {
			System.out.println(planningAssistant.getSchedule());
			menu();
		} else if (action.equals("agenda")) {
			System.out.println(planningAssistant.getAgenda());
			menu();
		} else if (action.equals("add")) {
			addMain();
		} else if (action.equals("edit")) {
			editView();
		} else if (action.equals("reschedule")) {
			planningAssistant.planSchedule();
			System.out.println("Schedule re-planned");
			menu();
		} else if (action.equals("clear")) {
			planningAssistant.clean();
			System.out.println("Agenda cleared");
			menu();
		} else if (action.equals("save")) {
			System.out.println("Saving...");
			planningAssistant.save();
			System.out.println("Complete");
			menu();
		} else if (action.equals("exit")) {
			System.out.println("Saving...");
			planningAssistant.save();
			System.out.println("Complete");
		} else {
			System.out.println("Get better at input loser");
			menu();
		}
	}

	private void editView() {
		System.out.println("All tabs:");
		List<Tab> tabs = planningAssistant.getAll();
		for (Tab tab : tabs) {
			System.out.println(tab);
		}
		System.out.println("Enter id:");
		int id = in.nextInt();
		in.nextLine();
		Tab tab = planningAssistant.getById(id);
		System.out.println(tab);
		System.out.println("Actions: delete parent, menu");
		String action = in.nextLine();
		if (action.equals("delete parent")) {
			planningAssistant.deleteItem(tab.getParent());
			planningAssistant.planSchedule();
			System.out.println("Parent removed");
			menu();
		} else if (action.equals("menu")) {
			menu();
		} else {
			editView();
		}

	}

	private void focusView() {
		Tab tab = planningAssistant.getFirst();
		if (tab == null) {
			System.out.println("Looks like you've got nothing to do!");
			menu();
		} else {
			System.out.println("Here's what's up next:");
			System.out.println(tab);
			System.out.println("Actions: complete, reschedule, menu");
			String action = in.nextLine();
			if (action.equals("complete")) {
				tab.complete();
				planningAssistant.removeTab(tab);
				focusView();
			} else if (action.equals("reschedule")) {
				planningAssistant.planSchedule();
				focusView();
			} else if (action.equals("menu")) {
				menu();
			} else {
				focusView();
			}
		}
	}

	private void dayView() {
		List<Tab> tabs = planningAssistant.getDay(current);
		System.out.println("To do on day of " + current.getTime() + ":");
		if (tabs.isEmpty()) {
			System.out.println("Nothing at all!");
		} else {
			for (Tab tab : tabs) {
				System.out.println(tab);
			}
		}
		System.out.println("Actions: next, previous, menu");
		String action = in.nextLine();
		if (action.equals("next")) {
			current.add(Calendar.DAY_OF_WEEK, 1);
			dayView();
		} else if (action.equals("previous")) {
			current.add(Calendar.DAY_OF_WEEK, -1);
			dayView();
		} else if (action.equals("menu")) {
			menu();
		} else {
			dayView();
		}
	}

	private void weekView() {
		List<Tab> tabs = planningAssistant.getWeek(current);
		System.out.println("To do during week of " + current.getTime() + ":");
		if (tabs.isEmpty()) {
			System.out.println("Nothing at all!");
		} else {
			for (Tab tab : tabs) {
				System.out.println(tab);
			}
		}
		System.out.println("Actions: next, previous, menu:");
		String action = in.nextLine();
		if (action.equals("next")) {
			current.add(Calendar.WEEK_OF_MONTH, 1);
			weekView();
		} else if (action.equals("previous")) {
			current.add(Calendar.WEEK_OF_MONTH, -1);
			weekView();
		} else if (action.equals("menu")) {
			menu();
		} else {
			weekView();
		}
	}

	private void monthView() {
		List<Tab> tabs = planningAssistant.getMonth(current);
		System.out.println("To do during month of " + current.getTime() + ":");
		if (tabs.isEmpty()) {
			System.out.println("Nothing at all!");
		} else {
			for (Tab tab : tabs) {
				System.out.println(tab);
			}
		}
		System.out.println("Actions: next, previous, menu:");
		String action = in.nextLine();
		if (action.equals("next")) {
			current.add(Calendar.MONTH, 1);
			monthView();
		} else if (action.equals("previous")) {
			current.add(Calendar.MONTH, -1);
			monthView();
		} else if (action.equals("menu")) {
			menu();
		} else {
			monthView();
		}
	}

	private void addMain() {
		System.out.println("Add: event, task, menu");
		String action = in.nextLine();
		if (action.equals("event")) {
			addEvent();
		} else if (action.equals("task")) {
			addTask();
		} else if (action.equals("menu")) {
			menu();
		} else {
			addMain();
		}
	}

	private void addEvent() {
		System.out.println("Title:");
		String title = in.nextLine();
		System.out.println("Details:");
		String details = in.nextLine();
		System.out.println("Date (" + C.DATE_TIME_FORMAT + "):");
		long startDate = dateInput();
		System.out.println("Duration (" + C.TIME_FORMAT + "):");
		long duration = timeInput();
		System.out.println("Recurring? (y/n):");
		String recurring = in.nextLine();
		Event newEvent = null;
		if (recurring.equals("y")) {
			System.out.println("Scale: daily, weekly, monthly, yearly");
			String scaleStr = in.nextLine();
			int scale = -1;
			List<Marker> markers = new ArrayList<Marker>();
			while (scale == -1) {
				if (scaleStr.equals("daily")) {
					scale = Calendar.DAY_OF_WEEK;
					scaleStr = "days";
				} else if (scaleStr.equals("weekly")) {
					scale = Calendar.WEEK_OF_MONTH;
					scaleStr = "weeks";
					System.out.println("Days of week (day:index - separated by spaces):");
					String daysOfWeekStr = in.nextLine();
					String markersStr[] = daysOfWeekStr.split(" ");
					for (String markerStr : markersStr) {
						String markerFieldsStr[] = markerStr.split(":");
						markers.add(new Marker(Calendar.DAY_OF_WEEK, Integer.parseInt(markerFieldsStr[0]),
								Integer.parseInt(markerFieldsStr[1])));
					}
				} else if (scaleStr.equals("monthly")) {
					scale = Calendar.MONTH;
					scaleStr = "months";
				} else if (scaleStr.equals("yearly")) {
					scale = Calendar.YEAR;
					scaleStr = "years";
				}
			}
			System.out.println("Period (every x " + scaleStr + "):");
			String periodStr = in.nextLine();
			System.out.println("End date (" + C.DATE_TIME_FORMAT + "):");
			long endDate = dateInput();
			Recurrence recurrence = new Recurrence(scale, Integer.valueOf(periodStr), markers);
			newEvent = new Event(title, details, startDate, duration, recurrence, endDate);
		} else {
			newEvent = new Event(title, details, startDate, duration);
		}
		planningAssistant.addEvent(newEvent);
		planningAssistant.planSchedule();
		System.out.println("Event added:");
		System.out.println(newEvent);
		addMain();
	}

	private void addTask() {
		System.out.println("Title:");
		String title = in.nextLine();
		System.out.println("Details:");
		String details = in.nextLine();
		System.out.println("Input days from now?: y/n");
		String format = in.nextLine();
		long deadline = 0;
		if (format.equals("y")) {
			System.out.println("Days:");
			int days = in.nextInt();
			in.nextLine();
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_WEEK, days);
			deadline = c.getTimeInMillis();
		} else {
			System.out.println("Deadline (" + C.DATE_TIME_FORMAT + "):");
			deadline = dateInput();
		}
		System.out.println("Expected time to complete (" + C.TIME_FORMAT + "):");
		long minutes = timeInput() / C.MINUTE;
		Task newTask = new Task(title, details, deadline, minutes);
		planningAssistant.addTask(newTask);
		planningAssistant.planSchedule();
		System.out.println("Task added:");
		System.out.println(newTask);
		addMain();
	}

	public long dateInput() {
		String date = in.nextLine();
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat(C.DATE_TIME_FORMAT).parse(date));
		} catch (ParseException e) {
			return dateInput();
		}
		return c.getTimeInMillis();
	}

	public long timeInput() {
		String time = in.nextLine();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		try {
			c.setTime(new SimpleDateFormat(C.TIME_FORMAT).parse(time));
		} catch (ParseException e) {
			return timeInput();
		}
		return c.getTimeInMillis();
	}

	public static void main(String[] args) {
		ConsoleUi ui = new ConsoleUi();
		ui.focusView();
	}
}
