package bilou;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.event.Event;



public class PhysicWorld implements ICoreBase {

	// class world physic JBOX2D	 
	private static World worldPhysic;
	
	// gravity physic JBOX2D
	private static Vec2 gravity;
	
	// parent static
	private PhysicWorld pw;
	
	// ratio (pixels / m)
	private static float ratio = 32.0f;
	
	// Slow motion
	private static boolean slowMotion = false;
	
	private static float valueMotion = 1f/60f;
	
	public PhysicWorld()
	{
		pw = this;
		// initilisation du gravity
		gravity = new Vec2(0,55);
		// instance du world physis
		worldPhysic = new World(gravity);
		worldPhysic.setContactListener(new MyContactListener());
		
		// setContinuousPhysics
		worldPhysic.setContinuousPhysics(true);
	}
	

	
	/**
	 * @return the slowMotion
	 */
	public static boolean isSlowMotion()
	{
		return slowMotion;
	}

	/**
	 * @param slowMotion the slowMotion to set
	 */
	public static void setSlowMotion(boolean slowMotion) 
	{
		PhysicWorld.slowMotion = slowMotion;
		// modification du slowmotion
		if(PhysicWorld.slowMotion)
			PhysicWorld.valueMotion = 1f/512f;
		else
			PhysicWorld.valueMotion = 1f/60f;
	}



	public static Vec2 convertToM2VEC(Vector2f mouseCoord)
	{
		Vector2f temp =  Vector2f.div(mouseCoord,PhysicWorld.getRatioPixelMeter());
		return new Vec2(temp.x,temp.y);
	}
	
	public static Vec2 convertToPixelsVEC(Vector2f p)
	{
		Vector2f temp =  Vector2f.mul(p,PhysicWorld.getRatioPixelMeter());
		return new Vec2(temp.x,temp.y);
	}
	
	public static Vector2f convertToM2(Vector2f mouseCoord)
	{
		return Vector2f.div(mouseCoord,PhysicWorld.getRatioPixelMeter());
	}
	
	public static Vector2f convertToPixels(Vector2f p)
	{
		return Vector2f.mul(p,PhysicWorld.getRatioPixelMeter());
	}
	/**
	 * @return the ratio
	 */
	public static float getRatioPixelMeter() {
		return ratio;
	}

	/**
	 * @return the worldPhysic
	 */
	public static World getWorldPhysic() {
		return worldPhysic;
	}


	/**
	 * @param worldPhysic the worldPhysic to set
	 */
	public static void setWorldPhysic(World worldPhysic) {
		PhysicWorld.worldPhysic = worldPhysic;
	}


	@Override
	public void update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		worldPhysic.step(PhysicWorld.valueMotion,2, 1);
		
	}


	@Override
	public void loadContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reloadContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catchEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
