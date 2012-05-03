package logic;

import java.util.HashSet;

/**
 * A Class representing a Coordinate
 * @author jokke
 *
 */
public class Coordinate {
	public double value;
	private static CoordinateSet coords;
	
	/**
	 * Instantiates the Coordinate with the given value
	 * @param value - the value in degrees for this Coordinate
	 */
	public Coordinate(double value) {
		this.value = value;
		coords.add(this);
	}
	/**
	 * Returns an instance of Coordinate for the given value
	 * @param value - the value in degrees for the Coordinate
	 * @return - an instance of Coordinate matching the value
	 */
	public static Coordinate getInstance(double value) {
		if(coords != null) {
			Coordinate candidate = coords.get(value);
			if(candidate != null)
				return candidate;
			return new Coordinate(value);
		}
		coords = new CoordinateSet();
		return new Coordinate(value);
	}
	private static class CoordinateSet extends HashSet<Coordinate> {
		
		private static final long serialVersionUID = -6236655069899040776L;

		public Coordinate get(double value) {
			for(Coordinate current : this)
				if(current.value == value)
					return current;
			return null;
		}
	}
	public String toString() {
		return String.valueOf(value);
	}
}
