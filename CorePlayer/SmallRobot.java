package CorePlayer;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;

import bilou.PhysicWorld;
import CoreTexturesManager.TexturesManager;
import Entities.SmallRobotControl;
import Entities.RobotBase.SENS;

public class SmallRobot extends RobotBase 
{
	
		
	// PHYSIQUE - CONTROL
		
		// bounds
		private FloatRect bounds;
		// Vecteur velocité
		private Vector2f velocity = Vector2f.ZERO;
		//  vitesse
		private float speed = 256.0f;
		// acceleration
		private float acceleration = 1.0f;
		// vecteur de direction
		private Vector2f direction = Vector2f.ZERO;
		// vecteur du jump
		private Vector2f jumpVector = new Vector2f(0,-1);
		
		// la touche space est elle enfoncée
		private boolean isSpace = false;
		
		
	
	public SmallRobot(Vector2f pos)
	{
		// creatin du body jbox2d
				bodyDef = new BodyDef();
			//	bodyDef.position.set(new Vec2(0f,0.9f));
				bodyDef.position = new Vec2(pos.x / PhysicWorld.getRatioPixelMeter(),pos.y / PhysicWorld.getRatioPixelMeter());
				bodyDef.type = BodyType.DYNAMIC;
				//bodyDef.gravityScale = 0f;
				
				
				
				body = PhysicWorld.getWorldPhysic().createBody(bodyDef);
				body.setUserData(this);
				

				// initialisation du body
			
				//body.setFixedRotation(true);
				
				/*MassData md = new MassData();
				body.getMassData(md);
				md.center.set(0f,5f);
				body.setMassData(md);
				body.applyAngularImpulse(180f);*/

				//
				fixture = new FixtureDef();
				PolygonShape poly = new PolygonShape();
				poly.setAsBox(1, 1);
				
				
				fixture.shape = poly;
				fixture.density = 1.0f;
				fixture.friction = 0.5f;
				fixture.restitution = 0.0f;
				ff = body.createFixture(fixture);
					
				
				// specifie en premier que le petit robot est selectionné
				this.setSelected(true);
	}
		
	@Override
	public void update(Time deltaTime) {
		
		// TODO Auto-generated method stub
		super.update(deltaTime);
		
		// PHYSIQUE ET CONTROL
		// si le robot est selectionné
				if(this.isSelected)
				{
			
					if(  Keyboard.isKeyPressed(Keyboard.Key.D))
					{
						// si la touche D, la direction va vers la droite
						body.applyForce(new Vec2(256,0),body.getWorldCenter());
						
						
						// sens selectionné
						this.typeSens = SENS.DROITE;
						
					
						
					}else if( Keyboard.isKeyPressed(Keyboard.Key.Q))
					{
						// si la touche Q, la direction va vers la gauche
						body.applyForce(new Vec2(-256,0),body.getWorldCenter());
						
						// sens selectionné
						this.typeSens = SENS.GAUCHE;
									
					
					}
					
					if(!this.isSpace && this.isground && Keyboard.isKeyPressed(Keyboard.Key.SPACE))
					{
						body.applyLinearImpulse(new Vec2(0,-72), body.getWorldCenter());
						this.isground = false;
						this.isSpace = true;
					}
					else if(!Keyboard.isKeyPressed(Keyboard.Key.SPACE))
						this.isSpace = false;
				
				}
				
				// on récupère le vecteur velocity
				if(body.getLinearVelocity().lengthSquared() == 0f)
					this.typeSens = SENS.PAUSE;
		
	
				
		// VIEW ET ANIMATION

		// update de l'animation
		spritePlayer.setTextureRect(vectorAnim[indAnim]);
		
		// choix de l'anim
		timeAnim = Time.add(deltaTime, timeAnim);
		
		
	}

	/* (non-Javadoc)
	 * @see CorePlayer.RobotBase#loadContent()
	 */
	@Override
	public void loadContent()
	{
		// TODO Auto-generated method stub
		super.loadContent();
		
		spritePlayer = new Sprite(TexturesManager.GetTextureByName("playerSmallRobot"));
		spritePlayer.setTextureRect(new IntRect(0,0,64,64));
		
		// taille de l'image
		Vector2i size = TexturesManager.GetTextureByName("playerSmallRobot").getSize();
		
		// initialisation du vecteur d'animation
		vectorAnim = new IntRect[24]; // 12 étant le nombre d'animation pour le player
		// on crée les floatrect
		int x = 0;
		int y = 0;
		for(int i=0;i<vectorAnim.length;i++)
		{
			vectorAnim[i] = new IntRect(x,y,64,64);
			x+=64;
			if(x>=size.x)
			{
				x=0;
				y+=64;
				if(y >= size.y)
				{
					break;
				}
			}
			
		}
	}
	
	
	
}
