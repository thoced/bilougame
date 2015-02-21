package CoreGrapnel;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.JointType;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Time;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;
import bilou.PhysicWorld;

public class Grapnel implements ICoreBase, Drawable 
{
	// class de création du grappin
	
	// class body alpha (accroche du debut)
	private Body 	alphaBody;
	// class body bravo
	private Body 	braboBody;
	// Nombre de noeuds dans le grappin
	private int 	nbNode;
	
	
	public Grapnel(Body alpha,Body bravo, int nbNode)
	{
		// affectation des variable
		this.alphaBody = alpha;
		this.braboBody = bravo;
		// affectation du nombre de noeuds
		this.nbNode = nbNode;
		if(this.nbNode < 1)this.nbNode = 1; // on évite ainsi de diviser ensuite par 0
		// création de la liste des node virtuel
		// on détermine le vecteur qui relie le alpha et le bravo
		Vec2 posAlpha = this.alphaBody.getPosition();
		Vec2 posBravo = this.braboBody.getPosition();
		Vec2  diff = posBravo.sub(posAlpha);
		// obtention de la longueur entre l'alpha et le bravo
		float length = diff.length();
		// on normalise pour obtenir le vecteur direction
		diff.normalize();
		// on connait la distance et le nombre de noeuds voulu, on divise pour obtenir l'intervale
		// entre chaque noeud virtuel
		float lengthNode = length / this.nbNode;
		
		// on crée la position de départ
		Vec2 pos = posAlpha;
		// on place le premier body node
		Body Bodynode = this.alphaBody;
		
		// on boucle au nombre de node voulu
		for(int i=1;i<=this.nbNode;i++)
		{
			// on crée la position du node
			pos = pos.add(diff.mul(lengthNode * i));
			// on crée le node intermédiaire et onb le récupère
			 Bodynode = this.createNode(pos,lengthNode,Bodynode);
		}
		
		// on relie les tout au Bravo final
		
		DistanceJointDef distanceDef = new DistanceJointDef();
		distanceDef.initialize(Bodynode, this.braboBody, Bodynode.getWorldCenter(), this.braboBody.getWorldCenter());
		distanceDef.length = lengthNode;
		distanceDef.collideConnected = false;
		distanceDef.type = JointType.DISTANCE;

		// création du joint
		DistanceJoint distance = (DistanceJoint) PhysicWorld.getWorldPhysic().createJoint(distanceDef);
				
		
		
	}
	
	private Body createNode(Vec2 pos,float lenght,Body bodyNodePrevious)
	{
		// creatin du body def
		BodyDef def = new BodyDef();
		def.active = true;
		def.bullet = false;
		def.fixedRotation = true;
		def.position = pos;
		def.type = BodyType.DYNAMIC;
		
		// création du body
		Body body = PhysicWorld.getWorldPhysic().createBody(def);
		// création du shape
		CircleShape circle = new CircleShape();
		circle.m_radius = 2f;
		// Fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0f;
		fixtureDef.shape = circle;
		// creation du fixture
		body.createFixture(fixtureDef);
		// on retourne le body créé
		
		// il faut accroche le nouveau body avec le body previous
		DistanceJointDef distanceDef = new DistanceJointDef();
		distanceDef.initialize(bodyNodePrevious,body,bodyNodePrevious.getWorldCenter(),body.getWorldCenter());
		distanceDef.length = lenght;
		distanceDef.collideConnected = false;
		distanceDef.type = JointType.DISTANCE;

		// création du joint
		DistanceJoint distance = (DistanceJoint) PhysicWorld.getWorldPhysic().createJoint(distanceDef);
		
		return body;
		
	}
	
	@Override
	public void draw(RenderTarget arg0, RenderStates arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Time deltaTime) {
		// TODO Auto-generated method stub
		
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
