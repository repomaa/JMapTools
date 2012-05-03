package gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import logic.AbstractTile;
import logic.Coordinates;
import logic.TileConverter;
import logic.TileFactory;

/**
 * A Panel showing a map
 * @author jokke
 *
 */
public class MapPanel extends JPanel implements MouseListener, MouseWheelListener, ComponentListener, KeyListener {
	
	private static final long serialVersionUID = 7442964416722669405L;
	private TileFactory<? extends AbstractTile> factory;
	private Coordinates coords;
	private AbstractTile[][] tiles;
	private int zoom;
	private MapCanvas canvas;
	private AbstractTile cornerTile;
	
	/**
	 * Creates a new MapPanel with the given tile type, initial coordinates and zoom
	 * @param clazz - the class of the tile type to be used to draw the map
	 * @param coords - the initial coordinates of the center of the view 
	 * @param zoom - the initial zoom level
	 */
	public <T extends AbstractTile> MapPanel(Class<T> clazz, Coordinates coords, int zoom) {
		setTileType(clazz);
		setCoordinates(coords);
		this.zoom = zoom;
		setBorder(BorderFactory.createEtchedBorder());
		canvas = new MapCanvas(null);
		add(canvas);
		addComponentListener(this);
		addMouseWheelListener(this);
	}
	/**
	 * Sets the tile type to be used to draw the map
	 * @param clazz - the class of the tile type
	 */
	public <T extends AbstractTile> void setTileType(Class<T> clazz) {
		try {
			factory = new TileFactory<T>(clazz);
		} catch (Exception e) {
			
		}
	}
	/**
	 * Sets the coordinates where the center of the screen should be
	 * @param coords - the coordinates to set
	 */
	public void setCoordinates(Coordinates coords) {
		this.coords = coords;
	}
	/**
	 * Zooms the view in or out according to the amount
	 * @param amount - the amount of steps to zoom in or out (positive for in negative for out)
	 */
	public void zoom(int amount) {
		int newZoom = zoom + amount;
		int minZoom = factory.getMinZoom();
		int maxZoom = factory.getMaxZoom();
		if(newZoom > maxZoom)
			zoom = maxZoom;
		else if(newZoom < minZoom)
			zoom = minZoom;
		else
			zoom = newZoom;
		drawTiles();
		validate();
	}
	/**
	 * Draws the map
	 */
	private void drawTiles() {
		SwingWorker<Object, Object> loadWorker = new SwingWorker<Object, Object>() {

			@Override
			protected Object doInBackground() throws Exception {
				tiles = new AbstractTile[getTileCountY()][getTileCountX()];
				cornerTile = TileConverter.getCornerTile(coords, tiles.length, tiles[0].length, zoom, factory);
				System.out.println("Coords after: " + coords);
				for(int y = 0; y < tiles.length; y++)
					for(int x = 0; x < tiles[0].length; x++) {
						tiles[y][x] = factory.getTile(cornerTile.x + x, cornerTile.y + y, zoom);
					}
				canvas.init(tiles);
				canvas.paint(getGraphics());
				validate();
				return null;
			}
		};
		loadWorker.execute();
		
	}
	/**
	 * Gets the amount of tile columns that should be fit into the panel
	 * @return the amount of tile columns
	 */
	private int getTileCountX() {
		int maxXTiles = (int)Math.ceil((double)getWidth() / (double)factory.getTileSize().width);
		if(Math.pow(maxXTiles, 2) > factory.tilesForZoomLevel(zoom))
			return (int)Math.sqrt(factory.tilesForZoomLevel(zoom));
		return maxXTiles;
	}
	/**
	 * Gets the amount of tile rows that should be fit into the panel
	 * @return the amount of tile rows
	 */
	private int getTileCountY() {
		int maxYTiles = (int)Math.ceil((double)getHeight() / (double)factory.getTileSize().height);
		if(Math.pow(maxYTiles, 2) > factory.tilesForZoomLevel(zoom))
			return (int)Math.sqrt(factory.tilesForZoomLevel(zoom));
		return maxYTiles;
	}
	/**
	 * Moves the map so that the given coordinates are in the center
	 * @param coords - the coordinates that should be centered
	 */
	public void moveToCoords(Coordinates coords) {
		this.coords = coords;
		System.out.println(coords);
		drawTiles();
	}
	/**
	 * Moves the map a by given amount of tiles
	 * @param x - the amount of tiles to move horizontally
	 * @param y - the amount of tiles to move vertically
	 */
	public void moveTiles(int x, int y) {
		moveToCoords(TileConverter.getCenterCoordinates(cornerTile.x + x, cornerTile.y + y, zoom, tiles.length, tiles[0].length));
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoom(-e.getWheelRotation());
	}
	@Override
	public void componentResized(ComponentEvent e) {
		drawTiles();
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent e) {
		drawTiles();
	}
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			moveTiles(0, -1);
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			moveTiles(0, 1);
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			moveTiles(-1, 0);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			moveTiles(1, 0);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
