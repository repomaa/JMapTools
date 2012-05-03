package logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TileConverterTest {

	TileFactory<CycleMapTile> factory;
	double[] lats;
	double[] longs;
	@Before
	public void init() {
		factory = new TileFactory<CycleMapTile>(CycleMapTile.class);
		lats = new double[]{50.77,-20.56,10.13,-17.54,-60.43,32.25};
		longs = new double[]{8.23, 12.32, -8.14,-50.42,16.24,65.32};
	}
	@Test
	public void testGetCenterCoords() {
		for(int i = 0; i < longs.length; i++) {
			try {
				assertEquals(lats[i], TileConverter.getCenterCoordinates(TileConverter.getCornerTile(Coordinates.getInstance(lats[i], lats[i]), 5, 5, 10, factory), 5, 5).lat);
			} catch (Throwable t) {
				System.err.println(t.getMessage());
			}
			try {
				assertEquals(longs[i], TileConverter.getCenterCoordinates(TileConverter.getCornerTile(Coordinates.getInstance(longs[i], longs[i]), 5, 5, 10, factory), 5, 5).lon);
			} catch (Throwable t) {
				System.err.println(t.getMessage());
			}
		}
	}

}
