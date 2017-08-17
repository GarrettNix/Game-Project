package helpers;

import org.lwjgl.input.Mouse;

public class MouseInput {
	
	public static final int NUM_BUTTONS = 2;
	
	public static boolean[] buttonState = new boolean[NUM_BUTTONS];
	public static boolean[] prevButtonState = new boolean[NUM_BUTTONS];
	
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	
	public static void update() {
		for(int i = 0; i < NUM_BUTTONS; i++) {
			prevButtonState[i] = buttonState[i];
		}
		buttonState[LEFT] = Mouse.isButtonDown(0);
		buttonState[RIGHT] = Mouse.isButtonDown(1);
	}
	
	public static boolean isPressed(int button) {
		return buttonState[button];
	}
	
	public static boolean isClicked(int button) {
		return buttonState[button] && !prevButtonState[button];
	}
	
}