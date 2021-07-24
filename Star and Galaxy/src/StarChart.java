import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

public class StarChart {
	private static double SUPERNOVA_DISTANCE = 0.25;
	private int width;
	private int height;
	private HashMap<Star, String> starChart;
	private HashMap<String, Star> starChartByName;
	private HashMap<String, String[]> constellationChart;
	private HashSet<Star> deadStar;
	
	public StarChart (int width, int height) {
		this.width = width;
		this.height = height;
		this.starChart = new HashMap<Star, String>();
		this.starChartByName = new HashMap<String, Star>();
		this.constellationChart = new HashMap<String, String[]>();
		this.deadStar = new HashSet<Star>();
	}
	public void addStar (Star star, String name) {
		if (name == "") {
			name = null;
		}
		starChart.put(star, name);
		starChartByName.put(name, star);
	}
	public void addConstellation(String constellationName, String[] starNames) {
		constellationChart.put(constellationName, starNames);		
	}
	public String getName(Star star) {
		return starChart.get(star);
	}
	public Star getStar(String name) {
		return starChartByName.get(name);
	}
	public int supernova(Star star) {
		if (deadStar.contains(star)) {
			return 0;
		}
		for (Star other : starChart.keySet()) {
			if (star.distance(other) <= SUPERNOVA_DISTANCE) {
				deadStar.add(other);
			}
		}
		deadStar.add(star);
		return deadStar.size();
	}
	public void draw(Graphics g, boolean showStarNames) {
		for (Star star : starChart.keySet()) {
			if (deadStar.contains(star)) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(star.pixelColor());
			}
			g.fillRect(star.pixelX(width), star.pixelY(height), star.pixelSize(), star.pixelSize());
		}
		for (String conName : constellationChart.keySet()) {
			String[] starInCon = constellationChart.get(conName);
			int len = starInCon.length;
			HashMap<Star, String> nameStarInCon = new HashMap<>();
			for (int i = 0; i < len; i += 2) {
				Star front = starChartByName.get(starInCon[i]);
				Star back = starChartByName.get(starInCon[i+1]);
				if (!nameStarInCon.containsKey(front)) {
					nameStarInCon.put(front, starInCon[i]);
				}
				if (!nameStarInCon.containsKey(back)) {
					nameStarInCon.put(back, starInCon[i+1]);
				}
				g.setColor(Color.YELLOW);
				g.drawLine(front.pixelX(width), front.pixelY(height), back.pixelX(width), back.pixelY(height));
				
				if (i == len-2) {
					g.drawString(conName, back.pixelX(width), back.pixelY(height));
				}
				if (showStarNames) {
					for (Star item : nameStarInCon.keySet()) {
						g.setColor(Color.WHITE);
						g.drawString(nameStarInCon.get(item), item.pixelX(width), item.pixelY(height));
					}
				}
			}
		}		
	}
}
