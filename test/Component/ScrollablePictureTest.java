package test.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import org.junit.Test;

import src.Component.ScrollablePicture;
import src.Component.SpinedLabel;
import src.Model.Voice;

public class ScrollablePictureTest {
	ImageIcon icon = null;
	int maxUnitIncrement = 0;
	ScrollablePicture scrollablePicture;
	/**
	 * Purpose: make ScrollablePicture
	 * Input: ScrollablePicture()
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		scrollablePicture -> not null
	 */
	@Test
	public void testScrollablePicture() {
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		
		icon = new ImageIcon("images/icon.png");
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);

		assertNotNull(scrollablePicture);
	}
	
	/**
	 * Purpose: mouseMoved event
	 * Input: mouseMoved()
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		scrollablePicture -> not null
	 * 		no implementation in the mouseMoved function
	 */
	@Test
	public void testmouseMoved() {
		MouseEvent event = null;
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		scrollablePicture.mouseMoved(event);

		assertNotNull(scrollablePicture);
	}

	/**
	 * Purpose: mouseDragged event
	 * Input: mouseDragged()
	 * Expected: 
	 * 		return Failure
	 * 		I can't control scrollRectToVisible function that in the mouseDragged().
	 */
	@Test
	public void testmouseDragged() {
		MouseEvent event = null;
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		scrollablePicture.mouseDragged(event);

		assertNotNull(scrollablePicture);
	}
	
	/**
	 * Purpose: get Preferred Size
	 * Input: getPreferredSize()
	 * Expected: 
	 * 		return Success
	 * 		getPreferredSize() == new Dimension(320, 480)
	 */
	@Test
	public void testgetPreferredSize() {
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);

		assertEquals(scrollablePicture.getPreferredSize(), new Dimension(320, 480));
	}
	
	/**
	 * Purpose: get Preferred Scrollable View port Size
	 * Input: getPreferredScrollableViewportSize()
	 * Expected: 
	 * 		return Success
	 * 		getPreferredScrollableViewportSize() == new Dimension(320, 480)
	 */
	@Test
	public void testgetPreferredScrollableViewportSize() {
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);

		assertEquals(scrollablePicture.getPreferredScrollableViewportSize(), new Dimension(320, 480));
	}
	
	/**
	 * Purpose: get Scrollable Tracks View port Width
	 * Input: getScrollableTracksViewportWidth()
	 * Expected: 
	 * 		return Success
	 * 		getScrollableTracksViewportWidth() == false
	 */
	@Test
	public void testgetScrollableTracksViewportWidth() {
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		
		assertEquals(scrollablePicture.getScrollableTracksViewportWidth(), false);
	}
	
	
	/**
	 * Purpose: get Scrollable Tracks View port Height
	 * Input: getScrollableTracksViewportHeight()
	 * Expected: 
	 * 		return Success
	 * 		getScrollableTracksViewportHeight() == false
	 */
	@Test
	public void testgetScrollableTracksViewportHeight() {
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		
		assertEquals(scrollablePicture.getScrollableTracksViewportHeight(), false);
	}
	
	/**
	 * Purpose: set Max Unit Increment
	 * Input: setMaxUnitIncrement()
	 * Expected: 
	 * 		return Success
	 * 		scrollablePicture -> not null
	 */
	@Test
	public void testsetMaxUnitIncrement() {
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		scrollablePicture.setMaxUnitIncrement(1);
		assertNotNull(scrollablePicture);
	}
	
	/**
	 * Purpose: get Scrollable Block Increment
	 * Input: getScrollableBlockIncrement()
	 * Expected: 
	 * 		return Failure
	 * 		I can't control SwingConstants.HORIZONTAL
	 */
	@Test
	public void testgetScrollableBlockIncrement() {
		
		Rectangle rectangle = new Rectangle(1, 1, 1, 1);
		int orientation=1; 
		int direction=1;
		
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		
		assertEquals(scrollablePicture.getScrollableBlockIncrement(rectangle, orientation, direction), 0);
	}
	
	/**
	 * Purpose: getScrollableUnitIncrement
	 * Input: getScrollableUnitIncrement()
	 * Expected: 
	 * 		return Failure
	 * 		I can't control SwingConstants.HORIZONTAL
	 */
	@Test
	public void testgetScrollableUnitIncrement() {
		
		Rectangle rectangle = new Rectangle(1, 1, 1, 1);
		int orientation=1; 
		int direction=-1;
		
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		
		assertEquals(scrollablePicture.getScrollableUnitIncrement(rectangle, orientation, direction), 0);
	}
	
	
}
