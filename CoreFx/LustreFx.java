package CoreFx;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import CoreTexturesManager.TexturesManager;
import bilou.PhysicWorld;

public class LustreFx extends BaseFx 
{
	// list
	private List<Body> listNodes;
	// Position
	private Vector2f position;
	// body point fixe de la lampe
	private Body bodyPointFix;
	// body de la lampe
	private Body bodyLampe;
	// nb de nodes
	private int nbNode = 2;
	// texture de la partie fix de la lampe
	private Texture textureFix;
	// texture de la partie mobile physique
	private Texture textureMobile;
	// sprite fix
	private Sprite spriteFix;
	// sprite mobile
	private Sprite spriteMobile;
	
	
	public LustreFx(Vector2f pos,float size)
	{
		
		
		// on inscrit la position
		this.position = pos;
		// on crée le body fix
		BodyDef def = new BodyDef();
		def.active = true;
		def.bullet = false;
		def.fixedRotation = true;
		def.type = BodyType.STATIC;
		def.position = PhysicWorld.convertToM2VEC(this.position);
		// creation du shape
		CircleShape circle = new CircleShape();
		circle.m_radius = 0.1f;
		// création du body
		bodyPointFix = PhysicWorld.getWorldPhysic().createBody(def);
		// creation du fixture def
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.isSensor = true;
		// creation du fixture
		bodyPointFix.createFixture(fixtureDef);
		
		// creation des 4 nodes
		listNodes = new ArrayList<Body>();
		// diff size
		Vector2f diffsize = new Vector2f(0,size / this.nbNode);
		// création des position
		Vector2f posNode = this.position;
		
		for(int i=0;i<this.nbNode;i++)
		{
			posNode = Vector2f.add(posNode, diffsize);
			// création du node
			Body b = this.createNode(posNode);
			// ajout dans la liste
			this.listNodes.add(b);
		}
		
		// creation du dernier node contenant la texture 
		BodyDef bdef = new BodyDef();
		bdef.position = PhysicWorld.convertToM2VEC(Vector2f.add(posNode, diffsize));
		bdef.type = BodyType.DYNAMIC;
		bdef.active = true;
		bdef.bullet = false;
		// creation du shape
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(1f, 1f);
		// creation du body
		bodyLampe = PhysicWorld.getWorldPhysic().createBody(bdef);
		// création du fixturedef
		FixtureDef fd = new FixtureDef();
		fd.isSensor = false;
		fd.shape = poly;
		// créatoin du fixture
		bodyLampe.createFixture(fd);
		
		// création des texture
		// chargement de la texture du lustre fix
		textureFix = TexturesManager.GetTextureByName("lustrefix.png");
		// chargement de la texture de la partie mobile
		textureMobile = TexturesManager.GetTextureByName("lustremobil.png");
		// création des sprites
		spriteFix = new Sprite(textureFix);
		spriteFix.setPosition(this.position);
		spriteMobile = new Sprite(textureMobile);
	
		
	}
	
	private Body createNode(Vector2f pos)
	{
		// crétion du body def
		BodyDef def = new BodyDef();
		def.position = PhysicWorld.convertToM2VEC(pos);
		def.active = true;
		def.type = BodyType.DYNAMIC;
		def.fixedRotation = true;
		// création du shape
		CircleShape circle = new CircleShape();
		circle.m_radius = 0.1f;
		// body
		Body body = PhysicWorld.getWorldPhysic().createBody(def);
		// FixtureDef
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.isSensor = true;
		// fixture
		body.createFixture(fixtureDef);
		// retour du body
		return body;
	}

	/* (non-Javadoc)
	 * @see CoreFx.BaseFx#draw(org.jsfml.graphics.RenderTarget, org.jsfml.graphics.RenderStates)
	 */
	@Override
	public void draw(RenderTarget render, RenderStates state)
	{
		// TODO Auto-generated method stub
		super.draw(render, state);
		
		// affichage du texture fix
		spriteMobile.setPosition(PhysicWorld.convertToPixels(new Vector2f(bodyLampe.getPosition().x,bodyLampe.getPosition().y)));
		
	}

}
