package logic;

import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractOSMTile extends AbstractTile {
	
	protected static Dimension size = new Dimension(256, 256);
	protected String baseURL;
	
	/**
	 * {@inheritDoc}
	 */
	public AbstractOSMTile(int x, int y, int zoom) throws MalformedURLException {
		super(x, y, zoom);
	}
	/**
	 * Returns the base URL in witch the tiles are stored
	 * @return the base URL
	 */
	protected abstract String getBaseURL();

	@Override
	protected URL getImage() throws MalformedURLException {
		baseURL = getBaseURL();
		return new URL(baseURL + zoom + "/" + x + "/" + y + ".png");
	}
	@Override
	public Dimension getTileSize() {
		return size;
	}
	@Override
	public long tilesForZoomLevel(int zoom) {
		return (long)Math.pow(2, 2*zoom);
	}
}

