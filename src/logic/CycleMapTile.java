package logic;

import java.net.MalformedURLException;

public class CycleMapTile extends AbstractOSMTile {
	
	public CycleMapTile(int x, int y, int zoom) throws MalformedURLException {
		super(x, y, zoom);
	}

	@Override
	protected String getBaseURL() {
		return "http://a.tile.opencyclemap.org/cycle/";
	}

	@Override
	public int getMinZoom() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxZoom() {
		// TODO Auto-generated method stub
		return 17;
	}

}
