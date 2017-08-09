package planner.external.console;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import planner.internal.core.C;
import planner.internal.core.PlanningAssistant;
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
		planningAssistant = new PlanningAssistant();
		in = new Scanner(System.in);
		current = Calendar.getInstance();
	}

	private void menu() {
		System.out.println("View: focus, day, week, month, agenda");
		System.out.println("Actions: add, reschedule, clear, exit");
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
		} else if (action.equals("reschedule")) {
			planningAssistant.planSchedule();
			System.out.println("Schedule re-planned");
			menu();
		} else if (action.equals("clear")) {
			planningAssistant.clean();
			System.out.println("Agenda cleared");
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
		String startDate = in.nextLine();
		System.out.println("Duration (minutes):");
		String minutesStr = in.nextLine();
		System.out.println("Recurring? (y/n):");
		String recurring = in.nextLine();
		Recurrence recurrence = null;
		String endDate = null;
		if (recurring.equals("y")) {
			System.out.println("Scale: daily, weekly, monthly, yearly");
			String scaleStr = in.nextLine();
			int scale = -1;
			while (scale == -1) {
				if (scaleStr.equals("daily")) {
					scale = Calendar.DAY_OF_WEEK;
					scaleStr = "days";
				} else if (scaleStr.equals("weekly")) {
					scale = Calendar.WEEK_OF_MONTH;
					scaleStr = "weeks";
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
			System.out.println("Markers (type:value:index - separated by spaces):");
			String markersStr = in.nextLine();
			List<Marker> markers = new ArrayList<Marker>();
			if (!markersStr.equals("")) {
				String[] markersArr = markersStr.split(" ");

				for (String markerStr : markersArr) {
					String[] marker = markerStr.split(":");
					markers.add(new Marker(Integer.valueOf(marker[0]), Integer.valueOf(marker[1]),
							Integer.valueOf(marker[2])));
				}
			}
			System.out.println("End date (" + C.DATE_TIME_FORMAT + "):");
			endDate = in.nextLine();
			recurrence = new Recurrence(scale, Integer.valueOf(periodStr), markers);
		}
		Event newEvent = new Event(title, details, startDate, Integer.valueOf(minutesStr), recurrence, endDate);
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
		System.out.println("Deadline (" + C.DATE_TIME_FORMAT + "):");
		String deadlineDate = in.nextLine();
		System.out.println("Expected time to complete (minutes):");
		String minutesStr = in.nextLine();
		Task newTask = new Task(title, details, deadlineDate, Integer.valueOf(minutesStr));
		planningAssistant.addTask(newTask);
		planningAssistant.planSchedule();
		System.out.println("Task added:");
		System.out.println(newTask);
		addMain();
	}

	public static void main(String[] args) {
		ConsoleUi ui = new ConsoleUi();
		ui.focusView();
	}
}
