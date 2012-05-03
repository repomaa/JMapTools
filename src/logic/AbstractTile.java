package logic;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractTile {
	public int zoom;
	public int x;
	public int y;
	public URL image;
	protected Dimension tileSize;
	
	/**
	 * Instantiates a new Tile with the given x y and z coordinates.
	 * @param x - the x position of the Tile at the given zoom level
	 * @param y - the y position of the Tile at the given zoom level
	 * @param zoom
	 * @throws MalformedURLException
	 */
	public AbstractTile(int x, int y, int zoom) throws MalformedURLException {
		this.x = x;
		this.y = y;
		this.zoom = zoom;
		image = getImage();
		tileSize = getTileSize();
	}
	/**
	 * Gives the URL to the image file for this tile
	 * @return the URL
	 * @throws MalformedURLException
	 */
	protected abstract URL getImage() throws MalformedURLException;
	public abstract Dimension getTileSize();
	public abstract int getMinZoom();
	public abstract int getMaxZoom();
	public abstract long tilesForZoomLevel(int zoom);
}
