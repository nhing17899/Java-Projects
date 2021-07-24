import java.util.Scanner;
import java.awt.Polygon;

public class DecomposableShape {
	private class PointNode {
		private int x;
		private int y;
		private double imp;
		private PointNode prev;
		private PointNode next;
		public PointNode (int x, int y) {
			this.x = x;
			this.y = y;
			this.imp = 0;
			this.prev = null;
			this.next = null;
		}
		public double calculateImportance() {
			int xP = this.x;
			int yP = this.y;
			int xL = this.prev.x;
			int yL = this.prev.y;
			int xR = this.next.x;
			int yR = this.next.y;
			double lp = Math.sqrt(Math.pow(xP - xL,2) + Math.pow(yP - yL,2));
			double pr = Math.sqrt(Math.pow(xR - xP,2) + Math.pow(yR - yP,2));
			double lr = Math.sqrt(Math.pow(xR - xL,2) + Math.pow(yR - yL,2));
			this.imp = lp + pr - lr;
			return imp;
		}
		public String toString() {
			return "x = " + x + ", y = " + y + ", importance = " + this.calculateImportance();
		}
	}
	
	private int numIni = 0;
	private int numCurr = 0;
	private PointNode first;
	private ArrayStack<PointNode> st = new ArrayStack<PointNode>();
	public DecomposableShape (Scanner input) {
		PointNode before = null;
		while (input.hasNextLine()) {
			 this.numIni++;
			 String line = input.nextLine();
			 String[] data = line.split(",");
			 int x = Integer.parseInt(data[0]);
			 int y = Integer.parseInt(data[1]);
			 PointNode current = new PointNode(x,y);
			 
			 if (before == null) {
				 before = current;
				 first = current;
			 }
			 else {
				 before.next = current;
				 current.prev = before;
				 before = before.next;
			 }
			 if (!input.hasNext()) {
				 before.next = first;
				 first.prev = before;
			 }
		}
		numCurr = numIni;
	}
	public Polygon toPolygon() {
		System.out.println(numCurr);
		int[] xpoints = new int[numCurr];
		int[] ypoints = new int[numCurr];
		
		PointNode temp = first;
		int i = 0;
		while (i < numCurr) {
			xpoints[i] = temp.x;
			ypoints[i] = temp.y;
			temp = temp.next;
			i++;
		}
		Polygon poly = new Polygon(xpoints, ypoints, numCurr);
		return poly;
	}
	public void setToSize (int target) {
		int numToSize = target * numIni / 100;
		boolean toRemove = true;
		
		if (numToSize > numCurr) {
			toRemove = false;
		}
		
		if (toRemove) {
			int numRemoved = numCurr - numToSize;
		
			for (int i = 0; i < numRemoved; i++) {
				double minImp = first.calculateImportance();
				PointNode temp = first;
				PointNode minNode = temp;
				do {
					temp.imp = temp.calculateImportance();
					if (temp.imp < minImp) {
						minImp = temp.imp;
						minNode = temp;
					}
					temp = temp.next;
				} while (temp != first);
				
				st.push(minNode);
				
				if (minNode == first) {
					first = first.next;
				}
				
				minNode.prev.next = minNode.next;
				minNode.next.prev = minNode.prev;
				numCurr--;
			}
		}
		else {
			int numAdded = numToSize - numCurr;
			
			for (int i = 0; i < numAdded; i++) {
				PointNode nodePopped = st.pop();
				PointNode before = first;
				PointNode after = first.next;
				for (int j = 0; j < numCurr; j++) {
					if (nodePopped.prev == before && nodePopped.next == after) {
						nodePopped.next = after;
						after.prev = nodePopped;
						nodePopped.prev = before;
						before.next = nodePopped;
						
						numCurr++;
						break;
					}
					before = before.next;
					after = before.next;
				}
			}
		}
	}
	public String toString() {
		String res = "";
		PointNode current = first;
		do {
			res += current.toString();
			current = current.next;
		} while (current != first);
		return res;
	}
}
