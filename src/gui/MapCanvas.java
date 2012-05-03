package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import logic.AbstractTile;

public class MapCanvas extends Canvas {
	
	private static final long serialVersionUID = -2796051354879076562L;
	AbstractTile[][] tiles;
	public MapCanvas(AbstractTile[][] tiles) {
		init(tiles);
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		Graphics2D g2 = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		g2.drawRect(0, 0, width, height);
		for(int y = 0; y < tiles.length; y++)
			for(int x = 0; x < tiles[0].length; x++) {
				try {
					if(tiles[y][x] != null)
						g2.drawImage(ImageIO.read(tiles[y][x].image), x * tiles[y][x].getTileSize().width, y * tiles[y][x].getTileSize().height, this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
	}
	/**
	 * Initializes the canvas with a new AbstractTile[][]
	 * @param tiles - the AbstractTile[][]
	 */
	public void init(AbstractTile[][] tiles) {
		this.tiles = tiles;
		if(tiles != null && tiles[0][0] != null)
			this.setMaximumSize(new Dimension(tiles[0][0].getTileSize().width * tiles[0].length, tiles[0][0].getTileSize().height * tiles.length));
	}
}
