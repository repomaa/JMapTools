package logic;

/**
 * Handles Conversion between coordinates in different projections
 * @author jokke
 *
 */
public class TileConverter {
	
	/**
	 * Gives the coordinates of the upper left corner of a tile
	 * @param tile - the tile to get the coordinates from
	 * @return a instance of Coordinates with the coordinates of the tile
	 */
	public static Coordinates getCoordinates(AbstractTile tile) {
		return getCoordinates(tile.x, tile.y, tile.zoom);
	}
	
	/**
	 * Gives the Coordinates for a given x, y and z value
	 * @param x - the x value of a Mercado projection
	 * @param y - the y value of a Mercado projection
	 * @param zoom - the zoom level
	 * @return the Coordinates for the given parameter
	 */
	public static Coordinates getCoordinates(double x, double y, int zoom) {
		double lon = x / Math.pow(2.0, zoom) * 360.0 - 180;
		 double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, zoom);
		double lat = Math.toDegrees(Math.atan(Math.sinh(n)));
		return Coordinates.getInstance(lon, lat);
	}
	
	/**
	 * Gives the tile for given coordinates, zoom level and tile factory
	 * @param coords - the coordinates to get the tile from
	 * @param zoom - the zoom level
	 * @param factory - the factory which is used to create the tile
	 * @return a tile for the given parameters
	 */
	public static AbstractTile getTile(Coordinates coords, int zoom,
			TileFactory<? extends AbstractTile> factory) {
		int x = (int)Math.floor(getX(coords.lon, zoom));
		int y = (int)Math.floor(getY(coords.lat, zoom));
		return factory.getTile(x, y, zoom);
	}
	/**
	 * Gives the x coordinate of a mercado projection for given longitude and zoom level
	 * @param lon - the longitude
	 * @param zoom - the zoom level
	 * @return an x coordinate for the given parameters
	 */
	public static double getX(Coordinate lon, int zoom) {
		return (lon.value + 180) / 360 * (1<<zoom);
	}
	/**
	 * Gives the y coordinate of a mercado projection for given latitude and zoom level
	 * @param lat - the latitude
	 * @param zoom - the zoom level
	 * @return a y coordinate for the given parameters
	 */
	public static double getY(Coordinate lat, int zoom) {
		return (1 - Math.log(Math.tan(Math.toRadians(lat.value)) + 1 / Math.cos(Math.toRadians(lat.value))) / Math.PI) / 2 * (1<<zoom);
	}
	public static AbstractTile getCornerTile(Coordinates coords, int height, int width, int zoom, TileFactory<? extends AbstractTile> factory) {
		double xCenter = getX(coords.lon, zoom);
		double yCenter = getY(coords.lat, zoom);
		return factory.getTile((int)Math.floor(xCenter - (double)width/2.0), (int)Math.floor(yCenter - (double)height/2.0), zoom);
	}
	/**
	 * Gives the coordinates of the center of a view with height * width tiles and cornerTile as the upper left tile of the view
	 * @param cornerTile - the left- and uppermost tile in the view
	 * @param height - the amount tile rows in the view
	 * @param width - the amount of tile columns in the view
	 * @return the coordinates for the given parameters
	 */
	public static <T extends AbstractTile> Coordinates getCenterCoordinates(T cornerTile,
			int height, int width) {
		return getCenterCoordinates(cornerTile.x, cornerTile.y, cornerTile.zoom, height, width);
	}
	/**
	 * Gives the coordinates of the center of a view for given mercado coordinates, zoom level and dimensions of the view
	 * @param x - the x coordinate
	 * @param y - the y coordinate
	 * @param zoom - the zoom level
	 * @param height - the amount of tile columns in the view 
	 * @param width - the amount of tile rows in the view
	 * @return the coordinates for the given parameters
	 */
	public static Coordinates getCenterCoordinates(int x, int y, int zoom,
			int height, int width) {
		double xCenter = width / 2.0;
		double yCenter = height / 2.0;
		return getCoordinates(x + xCenter, y + yCenter, zoom);
		
	}
}
