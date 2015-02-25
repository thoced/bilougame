package CoreFx;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jsfml.system.Vector2f;

import CoreManager.Manager;
import bilou.PhysicWorld;

public class rayCast implements RayCastCallback
{

	@Override
	public float reportFixture(Fixture fix, Vec2 pos, Vec2 arg2, float arg3)
	{
		return 0;
	}

}
