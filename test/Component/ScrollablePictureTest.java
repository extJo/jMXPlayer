package test.Component;

import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

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
	 * Input: 
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
	 * Input: 
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
	 * Input: 
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
	
	
}
