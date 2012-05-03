package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import logic.AbstractTile;
import logic.Coordinates;
import logic.CycleMapTile;

/**
 * The main frame 
 * @author jokke
 *
 */
public class MapFrame extends JFrame {
	
	private static final long serialVersionUID = -65641733081137327L;
	private MapPanel mapPanel;
	public MapFrame() {
		setPreferredSize(new Dimension(600, 600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	/**
	 * Sets a new MapPanel as content pane
	 * @param type - the class of the tile type to be used
	 * @param coords - the initial coordinates of the map view
	 * @param zoom - the initial zoom level
	 */
	public <T extends AbstractTile> void setMapPanel(Class<T> type, Coordinates coords, int zoom) {
		if(mapPanel != null)
			mapPanel.setTileType(type);
		else {
			mapPanel = new MapPanel(type, coords, zoom);
			setContentPane(mapPanel);
			addKeyListener(mapPanel);
		}
		pack();
	}
	public static void main(String[] args) {
		MapFrame frame = new MapFrame();
		frame.setVisible(true);
		frame.setMapPanel(CycleMapTile.class, Coordinates.getInstance(0, 0), 0);
	}
}
