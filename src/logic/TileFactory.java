package logic;

import java.awt.Dimension;
import java.util.HashSet;

/**
 * A factory for creating tiles of type T
 * @author jokke
 *
 * @param <T> - the type of tiles to be created
 */
public class TileFactory<T extends AbstractTile> {
	private TilesSet tiles;
	private Class<T> tileType;
	private T referenceTile;
	
	/**
	 * Instantiates the factory with the given type
	 * @param type - the tile type
	 */
	public TileFactory(Class<T> type) {
		tiles = new TilesSet();
		tileType = type;
		try {
			referenceTile = tileType.getConstructor(new Class[] {int.class, int.class, int.class}).newInstance(0,0,1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * Returns a Tile for the given parameters
	 * @param x - the x position of a tile on the given zoom level
	 * @param y - the y position of a tile on the given zoom level 
	 * @param zoom - the zoom level
	 * @return a Tile matching the parameters
	 */
	public T getTile(int x, int y, int zoom)  {
		T candidate = tiles.get(x, y, zoom);
		if(candidate == null) {
			try {
				candidate = tileType.getConstructor(new Class[]{int.class, int.class, int.class}).newInstance(x,y,zoom);
				tiles.add(candidate);
			} catch(Exception e) {
				
			}
		}
		return candidate;
	}	
	private class TilesSet extends HashSet<T> {
	
		private static final long serialVersionUID = 2014765894130279598L;

		public T get(int x, int y, int zoom) {
			for(T current : this)
				if(current.x == x && current.y == y && current.zoom == zoom)
					return current;
			return null;
		}
	}
	/**
	 * Gives the tile size for the tiles being created by this factory
	 * @return - the tile size
	 */
	public Dimension getTileSize() {
		return referenceTile.getTileSize();
	}
	/**
	 * Returns the lowest possible zoom level for the tiles created by this factory
	 * @return the minimum zoom level
	 */
	public int getMinZoom() {
		return referenceTile.getMinZoom();
	}
	/**
	 * Returns the highest possible zoom level for the tiles created by this factory
	 * @return the maximum zoom level
	 */
	public int getMaxZoom() {
		return referenceTile.getMaxZoom();
	}
	/**
	 * Returns the amount of tiles for a given zoom level
	 * @param zoom - the zoom level
	 * @return the amount of tiles used in this zoom level
	 */
	public long tilesForZoomLevel(int zoom) {
		return referenceTile.tilesForZoomLevel(zoom);
	}
}
