package run;

import static helpers.Render.beginSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;

import gameState.GameStateManager;

/**
 * 
 * @author Garrett
 * 
 * This is the class that runs the game.
 * It initializes an instance of SurvivalGame in the main() method.
 * This calls the beginSession() method of Render, and starts the game loop.
 * The game loop will cycle through, collecting input, updating the game, and
 * rendering, until the user closes the game window. Closing the game window
 * will break the loop and call Display.destroy() to cleanup the game and exit.
 *
 */

public class SurvivalGame {
	
	//private int FPS; // (this may be used to set custom framerates in the future)
	
	public SurvivalGame() {
		//FPS = showFPSPrompt();
		beginSession();
		
		GameStateManager gsm = new GameStateManager();
		
		while (!Display.isCloseRequested()) {
			try {
				gsm.update();
				gsm.render();
			
				Display.update();
				Display.sync(60);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				JOptionPane.showMessageDialog(null, "Game had an error on this cycle. Stack trace: " + sw.toString());
				e.printStackTrace();
			}
		}
		Display.destroy();
	}
	
	/**
	private int showFPSPrompt() {
		String[] options = {"30", "60", "90", "120"};
		String input = (String) JOptionPane.showInputDialog(null, "Selected desired framerate:", "Framerate Selection", JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.shoeMessageDialog(null, "Invalid input. Framerate set to default of 60.");
			return 60;
		}
	}
	*/
	
	public static void main(String[] args) {
		new SurvivalGame();
	}
}