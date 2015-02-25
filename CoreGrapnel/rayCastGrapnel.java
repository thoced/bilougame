package CoreGrapnel;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

import bilou.PhysicWorld;

public class rayCastGrapnel implements RayCastCallback, Drawable 
{
	// Shape
	private CircleShape shape;
	
	public rayCastGrapnel()
	{
		// instance du shape
		shape = new CircleShape();
		// paramètrage du shape
		shape.setFillColor(Color.RED);
		// taille du shape
		shape.setRadius(4f);
		
	}
	
	@Override
	public float reportFixture(Fixture fix, Vec2 point, Vec2 normal, float fraction)
	{
		//si la fraction est suppérieur à 0
		if(fraction > 0)
			shape.setPosition(point.x * PhysicWorld.getRatioPixelMeter(),point.y * PhysicWorld.getRatioPixelMeter());
		
		return fraction;
	}

	@Override
	public void draw(RenderTarget render, RenderStates state) 
	{
		// affichage du shape
		render.draw(shape,state);
	}

}
