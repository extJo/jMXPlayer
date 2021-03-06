package test.Model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import src.Model.Voice;
import src.Util.CustomColor;

public class VoiceTest {
	Voice voice;
	final String voiceName = "hello";

	@Before
	public void setUp() {
		voice = new Voice(voiceName);
	}
	
	/**
	 * Purpose: is visible at voice  
	 * Input: 
	 * Expected: not Null
	 *
	 * return SUCCESS
	 * 
	 * isVisible of voice is false -> not Null 
	 */
	@Test
	public void testIsVisible() {
		assertNotNull(voice.isVisible());
	}
	
	/**
	 * Purpose: set visible to voice  
	 * Input: changedVisible = true
	 * Expected: true
	 *
	 * return SUCCESS
	 * 
	 * visible of voice -> changedVisible
	 */
	@Test
	public void testSetVisible() {
		boolean changedVisible = true;
		voice.setVisible(changedVisible);
		assertEquals(changedVisible, voice.isVisible());
	}
	
	/**
	 * Purpose: get name of voice  
	 * Input: 
	 * Expected: hello
	 *
	 * return SUCCESS
	 * 
	 * hello
	 */
	@Test
	public void testGetName() {
		assertEquals(voiceName, voice.getName());
	}

	/**
	 * Purpose: set name of voice  
	 * Input: world
	 * Expected: 
	 *
	 * return SUCCESS
	 * 
	 * hello -> world
	 */
	@Test
	public void testSetName() {
		voice.setName("world");
		assertEquals("world", voice.getName());
	}

	/**
	 * Purpose: get color of voice  
	 * Input: 
	 * Expected: instance of Color 
	 *
	 * return SUCCESS
	 * 
	 * instance of Color
	 */
	@Test
	public void testGetColor() {
		assertTrue(voice.getColor() instanceof Color);
	}
	
	/**
	 * Purpose: set color of voice  
	 * Input: CustomColor.getNextColor()
	 * Expected: CustomColor.getNextColor() -> color of voice
	 *
	 * return SUCCESS
	 * 
	 * CustomColor.getNextColor() -> color of voice
	 */
	@Test
	public void testSetColor() {
		Color color = CustomColor.getNextColor();
		voice.setColor(color);
		assertEquals(color, voice.getColor());
	}

	
}
