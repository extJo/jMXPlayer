/**
 * jMXPlayer, a GUI to IEEE PAR1599 (MX) data
 * Copyright © 2010 Riccardo Attilio Galli <riccardo@sideralis.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package src.View;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.Component.HighLights;
import src.Controller.CanvasResizedListener;
import src.Model.GraphicInstance;
import src.Model.GraphicInstanceGroup;
import src.Component.ImageCanvas;
import javax.swing.JLayeredPane;
import javax.swing.JFrame;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;

import org.jdesktop.layout.GroupLayout;
import java.awt.event.ComponentAdapter;
import javax.swing.WindowConstants;
import java.util.TimerTask;
import java.util.Timer;

/**
 *
 * @author Riquito
 */
public class PartitureWindow extends Window {
	private HighLights marks;
	private GraphicInstanceGroup graphicGroup;
	private ImageCanvas mainCanvas;
	private BufferedImage currentImage = null;
	private BufferedImage nextImage = null;
	private GraphicInstance currentGraphicInstance;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JLayeredPane layeredPane;
	// End of variables declaration//GEN-END:variables

	/**
	 * Creates new form PartitureWindow
	 */
	public PartitureWindow(HighLights marks, GraphicInstanceGroup group) {
		this.graphicGroup = group;
		this.marks = marks;
	}
	
	@Override
	public void render() {
		initComponents();
	}

	@Override
	public void clearAll() {	
	}
	
	public ImageCanvas getCanvas() {
		return this.mainCanvas;
	}

	public void resetPrefetching() {
		this.nextImage = null;
	}

	public GraphicInstanceGroup getGroup() {
		return graphicGroup;
	}

	public HighLights getMarks() {
		return marks;
	}

	/**
	 * legge un'immagine da un filepath
	 * 
	 * @imgPath è una path relativa alla directory Resources
	 */
	private BufferedImage readImage(String imgPath) {
		BufferedImage image = null;

		try {
			image = ImageIO.read(new File(imgPath));
		} catch (IOException e) {
			System.err.println("Errore nel caricamento dell'immagine di uno spartito");
			e.printStackTrace();
		}
		return image;
	}

	private void prefetchNextPage(GraphicInstance instance) {
		if (instance != null) {
			BufferedImage image = readImage(instance.getImagePath());
			this.nextImage = image;
			// System.out.println("Prefetch di "+gi.getImagePath());
		}
	}

	/*
	 * load the first image in the MXData group
	 */
	public void loadFirstPage() {
		this.loadPage(this.graphicGroup.getInstances().get(0));
	}

	/*
	 * redraw the image, checking for window dimension
	 */
	private void refreshCanvas() {
		this.mainCanvas.setBounds(0, 0, this.layeredPane.getWidth(), this.layeredPane.getHeight());
		this.mainCanvas.setImage(this.currentImage);
	}

	/*
	 * load a partiture page
	 */
	private void loadPage(GraphicInstance instance) {
		this.setCurrentGraphicInstance(instance);

		if (this.nextImage == null) {
			this.currentImage = this.readImage(instance.getImagePath());
		} else {
			this.currentImage = this.nextImage;
			this.nextImage = null;
		}

		this.refreshCanvas();

		this.marks.hideAllLabel();

		// fare delayed fetch della prossima se esistente
		Timer delayedPrefetch = new Timer();

		final GraphicInstance next = graphicGroup.getNextInstance();

		delayedPrefetch.schedule(new TimerTask() {
			public void run() {
				prefetchNextPage(next);
			}
		}, 800);
	}

	/*
	 * load the next image in the MXData group, if any
	 */
	public void loadNextPage() {
		if (this.graphicGroup.getNextInstance() != null) {
			this.loadPage(this.graphicGroup.getNextInstance());
		}
	}

	/*
	 * load the previous image in the MXData group, if any
	 */
	public void loadPrevPage() {
		if (this.graphicGroup.getPreviousInstance() != null) {
			this.loadPage(this.graphicGroup.getPreviousInstance());
		}
	}
	
	private ComponentAdapter componentResizeEventAdapter = new ComponentAdapter() {
		public void componentResized(ComponentEvent event) {
			formComponentResized(event);
		}
	};

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		layeredPane = new JLayeredPane();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addComponentListener(componentResizeEventAdapter);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(layout.createSequentialGroup()
				.addContainerGap().add(layeredPane, GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.LEADING).add(GroupLayout.TRAILING,
				layout.createSequentialGroup().addContainerGap()
						.add(layeredPane, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE).addContainerGap()));

		this.mainCanvas = new ImageCanvas();
		this.layeredPane.add(this.mainCanvas, 0);

		this.marks.setBackgroundPanel(this.layeredPane);

		final HighLights cpMarks = this.marks;
		this.mainCanvas.addCanvasResizeEventListener(new CanvasResizedListener() {
			public void onCanvasResized(int xOffset, int yOffset, double ratio) {
				cpMarks.setXAdjust(xOffset);
				cpMarks.setYAdjust(yOffset);
				cpMarks.setScaling(ratio);
			}
		});

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void formComponentResized(ComponentEvent event) {// GEN-FIRST:event_formComponentResized
		this.refreshCanvas();
	}// GEN-LAST:event_formComponentResized

	public GraphicInstance getCurrentGraphicInstance() {
		return currentGraphicInstance;
	}

	public void setCurrentGraphicInstance(GraphicInstance currentGraphicInstance) {
		this.currentGraphicInstance = currentGraphicInstance;
	}
}
