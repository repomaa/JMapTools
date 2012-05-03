package logic;

import java.util.HashSet;

/**
 * A Coordinate pair
 * @author jokke
 * @see Coordinate
 */
public class Coordinates {
	public Coordinate lat;
	public Coordinate lon;
	private static CoordinatesSet coords;
	/**
	 * Instantiates a new Coordinates with the given coordinates
	 * @param lat - the latitude in degrees
	 * @param lon - the longitude in degrees
	 */
	private Coordinates(double lat, double lon) {
		this.lat = Latitude.getInstance(lat);
		this.lon = Longitude.getInstance(lon);
		coords.add(this);
	}
	/**
	 * Returns an instance of Coordinates for the given coordinates
	 * @param lat - the latitude in degrees
	 * @param lon - the longitude in degrees
	 * @return an instance of Coordinates matching the parameters
	 */
	public static Coordinates getInstance(double lat, double lon) {
		if(coords != null) {
			Coordinates candidate = coords.get(lat, lon);
			if(candidate != null)
				return candidate;
			return new Coordinates(lat, lon);
		}
		coords = new CoordinatesSet();
		return new Coordinates(lat, lon);
	}
	private static class CoordinatesSet extends HashSet<Coordinates> {
	
		private static final long serialVersionUID = 4905566267372279106L;

		public Coordinates get(double lat, double lon) {
			for(Coordinates current : this)
				if(current.lat.value == lat && current.lon.value == lon)
					return current;
			return null;
		}
	}
	public String toString() {
		return "(" + lat.toString() + "|" + lon.toString() + ")";
	}
}
