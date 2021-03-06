package test.Component;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import org.junit.Test;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import src.Component.ToggleButton;
import src.Util.ResourceLoader;

public class ToggleButtonTest {
	/**
	 * Purpose: create Simple Toggle Button  
	 * Input: 
	 * Expected: equal to JToggleButton with empty text, no icon, 
	 * doesn't selected, doesn't enable, no actionListener
	 *
	 * return SUCCESS
	 * 
	 * equal 
	 */
	@Test
	public void testCreateSimpleToggleButton() {
		assertTrue(new ToggleButton.Builder().build() instanceof JToggleButton);
	}
	
	/**
	 * Purpose: create Toggle Button with text  
	 * Input: text = hello
	 * Expected: equal to JToggleButton with "hello" text, no icon, 
	 * doesn't selected, doesn't enable, no actionListener
	 *
	 * return SUCCESS
	 * 
	 * equal 
	 */
	@Test
	public void testCreateWithTextToggleButton() {
		String text = "hello";
		JToggleButton toggleButton = new ToggleButton.Builder().text(text).build(); 
		assertEquals(toggleButton.getText(), text);
	}
	
	/**
	 * Purpose: create Toggle Button with Icon  
	 * Input: Icon = ResourceLoader.getResourceUrl("http://jjalbang.today/jj237.jpg", "")
	 * Expected: equal to JToggleButton with empty text, 
	 * "ResourceLoader.getResourceUrl("http://jjalbang.today/jj237.jpg", "")" icon, 
	 * doesn't selected, doesn't enable, no actionListener
	 *
	 * return SUCCESS
	 * 
	 * equal 
	 */
	@Test
	public void testCreateWithIconToggleButton() throws MalformedURLException {
		Icon icon = new ImageIcon(ResourceLoader.getResourceUrl("http://jjalbang.today/jj237.jpg", ""));
		JToggleButton toggleButton = new ToggleButton.Builder().icon(icon).build(); 
		assertEquals(toggleButton.getIcon(), icon);
	}
	
	/**
	 * Purpose: create Toggle Button with selected  
	 * Input: select = true
	 * Expected: equal to JToggleButton with empty text, empty icon, 
	 * selected, doesn't enable, no actionListener
	 *
	 * return SUCCESS
	 * 
	 * equal 
	 */
	@Test
	public void testCreateWithSelectedToggleButton() {
		JToggleButton toggleButton = new ToggleButton.Builder().select(true).build(); 
		assertTrue(toggleButton.isSelected());
	}
	
	/**
	 * Purpose: create Toggle Button with enabled  
	 * Input: enable = true
	 * Expected: equal to JToggleButton with empty text, empty icon, 
	 * doesn't selected, enable, no actionListener
	 *
	 * return SUCCESS
	 * 
	 * equal 
	 */
	@Test
	public void testCreateWithEnabledToggleButton() {
		JToggleButton toggleButton = new ToggleButton.Builder().enable(true).build(); 
		assertTrue(toggleButton.isEnabled());
	}
	
	/**
	 * Purpose: create Toggle Button with actionListener  
	 * Input: actionListener
	 * Expected: equal to JToggleButton with empty text, empty icon, 
	 * doesn't selected, doesn't enable, actionListener
	 *
	 * return SUCCESS
	 * 
	 * equal 
	 */
	@Test
	public void testCreateWithActionListenerToggleButton() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			}
		};
		
		JToggleButton toggleButton = new ToggleButton.Builder().actionListener(listener).build();
		
		assertEquals(toggleButton.getActionListeners()[0], listener);
	}
}
