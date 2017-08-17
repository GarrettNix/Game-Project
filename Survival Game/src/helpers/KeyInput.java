package helpers;

import org.lwjgl.input.Keyboard;

public class KeyInput {
	
	public static final int NUM_KEYS = 32;
	
	public static boolean[] keyState = new boolean[NUM_KEYS];
	public static boolean[] prevKeyState = new boolean[NUM_KEYS];
	
	public static final int W = 0;
	public static final int A = 1;
	public static final int S = 2;
	public static final int D = 3;
	public static final int UP = 4;
	public static final int DOWN = 5;
	public static final int LEFT = 6;
	public static final int RIGHT = 7;
	public static final int SPACE = 8;
	public static final int ENTER = 9;
	public static final int LSHIFT = 10;
	public static final int RSHIFT = 11;
	public static final int LCONTROL = 12;
	public static final int RCONTROL = 13;
	public static final int F = 14;
	public static final int R = 15;
	public static final int Q = 16;
	public static final int ESC = 17;
	public static final int V = 18;
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
		keyState[W]        = Keyboard.isKeyDown(Keyboard.KEY_W);
		keyState[A]        = Keyboard.isKeyDown(Keyboard.KEY_A);
		keyState[S]        = Keyboard.isKeyDown(Keyboard.KEY_S);
		keyState[D]        = Keyboard.isKeyDown(Keyboard.KEY_D);
		keyState[UP]       = Keyboard.isKeyDown(Keyboard.KEY_UP);
		keyState[DOWN]     = Keyboard.isKeyDown(Keyboard.KEY_DOWN);
		keyState[LEFT]     = Keyboard.isKeyDown(Keyboard.KEY_LEFT);
		keyState[RIGHT]    = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
		keyState[SPACE]    = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		keyState[ENTER]    = Keyboard.isKeyDown(Keyboard.KEY_RETURN);
		keyState[LSHIFT]   = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		keyState[RSHIFT]   = Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
		keyState[LCONTROL] = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		keyState[RCONTROL] = Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
		keyState[F] = Keyboard.isKeyDown(Keyboard.KEY_F);
		keyState[R] = Keyboard.isKeyDown(Keyboard.KEY_R);
		keyState[Q] = Keyboard.isKeyDown(Keyboard.KEY_Q);
		keyState[ESC] = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
		keyState[V] = Keyboard.isKeyDown(Keyboard.KEY_V);
	}
	
	public static boolean isDown(int key) {
		return keyState[key];
	}
	
	public static boolean isPressed(int key) {
		return keyState[key] && !prevKeyState[key];
	}
}