package CoreFx;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.ContactEdge;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import bilou.PhysicWorld;
import CorePlayer.PlayerManager;
import CoreTexturesManager.TexturesManager;

public class DoorFx extends BaseFx 
{
	// sprite de l'objet
	private Sprite spriteDoor;
	private Texture textureDoor;
	// body door
	private Body bodyDoor;
	// activation
	private boolean active = false;
	// course
	private float course;
	// position start (pour définir la position de départ)
	private Vec2 posStart;
	// timeStart
	private Time timeStart;
	// time addition
	private Time timeAdd = new Clock().restart();
	
	public DoorFx(Vector2f pos,float course,boolean sens,float speed,float lenghtDetect)
	{
		super();
		
		this.course = course;
		// chargement du fichier image
		textureDoor = TexturesManager.GetTextureByName("door01.png");
		spriteDoor = new Sprite(textureDoor);
		// on positionne l'origine
		spriteDoor.setOrigin(new Vector2f(textureDoor.getSize().x /2, textureDoor.getSize().y /2));
		// on calcul le body 
		BodyDef bdef = new BodyDef();
		bdef.active = true;
		bdef.bullet = false;
		bdef.fixedRotation = true;
		bdef.position = PhysicWorld.convertToM2VEC(pos);
		bdef.type = BodyType.KINEMATIC;
		// shape
		PolygonShape shape = new PolygonShape();
		Vector2i size = textureDoor.getSize();
		
		/*float minX = -(size.x / PhysicWorld.getRatioPixelMeter()) / 2;
		float maxX = -minX;
		float minY = -(size.y / PhysicWorld.getRatioPixelMeter() / 2);
		float maxY = -minY;
		List<Vec2> v = new ArrayList<Vec2>(4);
		Vec2 a1 = new Vec2(minX,minY);
		Vec2 a2 = new Vec2(maxX,minY);
		Vec2 a3 = new Vec2(maxX,maxY);
		Vec2 a4 = new Vec2(minX,maxY);
		v.add(a1);
		v.add(a2);
		v.add(a3);
		v.add(a4);
		Vec2[] tt = new Vec2[4];
		v.toArray(tt);
		shape.set((Vec2[]) tt, v.size());*/
		shape.setAsBox((size.x / PhysicWorld.getRatioPixelMeter())/2  , (size.y / PhysicWorld.getRatioPixelMeter())/2);
		// FixtureDef
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0f;
		fixtureDef.shape = shape;
		// creation du body
		bodyDoor = PhysicWorld.getWorldPhysic().createBody(bdef);
		// créatoin du fixture
		bodyDoor.createFixture(fixtureDef);
		// position start
		this.posStart = bodyDoor.getPosition().clone();
		
	}
	
	
	@Override
	public void update(Time deltaTime) 
	{
		// TODO Auto-generated method stub
		super.update(deltaTime);
		
		// on récupère le body actor
		Body actor = PlayerManager.getSmallRobot().getBody();
		
		ContactEdge edge = bodyDoor.getContactList();
		if(edge != null && edge.other == actor)
		{
			this.active = true;
			this.bodyDoor.setLinearVelocity(new Vec2(0,-1f));
			this.timeStart = new Clock().restart();
			this.timeAdd = this.timeStart;
		}
		
		if(this.bodyDoor.getPosition().sub(this.posStart).length() >= this.course)
		{
			this.bodyDoor.setLinearVelocity(new Vec2(0,0));
			this.posStart = this.bodyDoor.getPosition().clone();
			this.active = false;
		}
		
		
		this.timeAdd = Time.add(this.timeAdd, deltaTime);
			if(!this.active && this.timeAdd.asSeconds() > 3f)
				this.bodyDoor.setLinearVelocity(new Vec2(0,1f));
	}

	
	@Override
	public void draw(RenderTarget render, RenderStates state) {
		// TODO Auto-generated method stub
		super.draw(render, state);
		// on positionne l'image
		spriteDoor.setPosition(bodyDoor.getPosition().x * PhysicWorld.getRatioPixelMeter(),bodyDoor.getPosition().y * PhysicWorld.getRatioPixelMeter());
		// affichage
		render.draw(spriteDoor,state);
		
	}
	
}
