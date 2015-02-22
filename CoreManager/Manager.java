package CoreManager;

import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.RenderWindow;

import CoreFx.ManagerFx;

public class Manager 
{
	// class manager static
	private static RenderTexture renderTexture;
	
	private static RenderWindow renderWindow;
	
	private static ManagerFx managerFx;

	
	
	/**
	 * @return the managerFx
	 */
	public static ManagerFx getManagerFx() {
		return managerFx;
	}

	/**
	 * @param managerFx the managerFx to set
	 */
	public static void setManagerFx(ManagerFx managerFx) {
		Manager.managerFx = managerFx;
	}

	/**
	 * @return the renderTexture
	 */
	public static RenderTexture getRenderTexture() {
		return renderTexture;
	}

	/**
	 * @param renderTexture the renderTexture to set
	 */
	public static void setRenderTexture(RenderTexture renderTexture) {
		Manager.renderTexture = renderTexture;
	}

	/**
	 * @return the renderWindow
	 */
	public static RenderWindow getRenderWindow() {
		return renderWindow;
	}

	/**
	 * @param renderWindow the renderWindow to set
	 */
	public static void setRenderWindow(RenderWindow renderWindow) {
		Manager.renderWindow = renderWindow;
	}

	
	
	
	
}
