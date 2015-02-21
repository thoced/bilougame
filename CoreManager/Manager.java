package CoreManager;

import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;

public class Manager 
{
	// class manager static
	private static RenderTexture renderWindow;

	/**
	 * @return the renderWindow
	 */
	public static RenderTexture getRenderWindow() {
		return renderWindow;
	}

	/**
	 * @param renderWindow the renderWindow to set
	 */
	public static void setRenderWindow(RenderTexture renderWindow) {
		Manager.renderWindow = renderWindow;
	}

	
	
	
}
