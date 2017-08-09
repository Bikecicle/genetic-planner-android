package planner.internal.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import evolution.core.Genome;
import evolution.core.Population;
import evolution.diagnostics.Log;
import evolution.diagnostics.Relationship;
import processing.core.PApplet;

public class Visualize extends PApplet {

	public static final int viewWidth = 800;
	public static final int viewHeight = 600;
	public static final int borderWidth = 20;

	public static final int nodeRadius = 5;

	public static List<Node> nodes;
	public static List<Edge> edges;

	public static void main(String[] args) {
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
		PApplet.main("planner.internal.core.Visualize");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		Log log = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("data/test.log"));
			log = (Log) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		double maxScore = log.maxScore();
		double minScore = log.minScore();
		double scoreRatio = (viewHeight - 2 * borderWidth) / (maxScore - minScore);
		double genCount = log.generationCount();
		double genRatio = (viewWidth - 2 * borderWidth) / (genCount - 1);

		for (Population pop : log.getGenerations()) {
			int gen = pop.getGeneration();
			for (Genome genome : pop) {
				double score = genome.getScore();
				int id = genome.getId();
				int x = (int) (gen * genRatio + borderWidth);
				int y = viewHeight - (int) ((score - minScore) * scoreRatio) - borderWidth;
				nodes.add(new Node(x, y, id, (ScheduleGenome) genome));
			}
		}

		for (Relationship r : log.getTree()) {
			edges.add(new Edge(r.parent1.getId(), r.child.getId()));
			edges.add(new Edge(r.parent2.getId(), r.child.getId()));
		}
	}

	public void draw() {
		for (Node node : nodes) {
			node.render();
		}
		for (Edge edge : edges) {
			edge.render();
		}
	}

	public void mousePressed() {
		for (Node node : nodes) {
			if (Math.sqrt(Math.pow(node.x - mouseX, 2) + Math.pow(node.y - mouseY, 2)) < nodeRadius * 2) {
				System.out.println(node.genome);
			}
		}
	}

	private class Node {
		public int x;
		public int y;
		public int id;
		public ScheduleGenome genome;

		public Node(int x, int y, int id, ScheduleGenome genome) {
			this.x = x;
			this.y = y;
			this.id = id;
			this.genome = genome;
		}

		public void render() {
			ellipse(x, y, nodeRadius, nodeRadius);
		}
	}

	private class Edge {
		public int parentId;
		public int childId;

		public Edge(int parentId, int childId) {
			this.parentId = parentId;
			this.childId = childId;
		}

		public void render() {
			int x1 = 0;
			int y1 = 0;
			int x2 = 0;
			int y2 = 0;
			for (Node node : nodes) {
				if (node.id == parentId) {
					x1 = node.x;
					y1 = node.y;
				} else if (node.id == childId) {
					x2 = node.x;
					y2 = node.y;
				}
			}
			line(x1, y1, x2, y2);
		}
	}
}
