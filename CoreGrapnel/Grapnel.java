package CoreGrapnel;


import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.JointEdge;
import org.jbox2d.dynamics.joints.JointType;
import org.jbox2d.dynamics.joints.PulleyJointDef;
import org.jbox2d.dynamics.joints.WeldJointDef;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.PrimitiveType;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Vertex;
import org.jsfml.graphics.VertexArray;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import bilou.ICoreBase;
import bilou.PhysicWorld;

public class Grapnel implements ICoreBase, Drawable 
{
	// class de création du grappin
	
	// class body alpha (accroche du debut)
	private Body 	alphaBody;
	// class body bravo
	private Body 	bravoBody;
	
	// Body attaché
	private Body   bodyAttach;
	// Nombre de noeuds dans le grappin
	private int 	nbNode;
	// list contenant l'ensemble des nodes
	private List<Body> listNodes;
	// VertexArray
	private VertexArray vectors;
	
	
	private org.jsfml.graphics.CircleShape circleShape;
	
	public Grapnel(Body alpha,Body bravo,Vec2 point, int nbNode)
	{
		this.listNodes = new ArrayList<Body>();
		this.listNodes.add(alpha);
		// on instancie le vertex array
		vectors = new VertexArray(PrimitiveType.LINE_STRIP);
		
		// on crée simplement un objet que l'on va attaché weld
		BodyDef def = new BodyDef();
		def.active = true;
		def.bullet = false;
		def.fixedRotation = false;
		def.position = point;
		def.type = BodyType.DYNAMIC;
		
		// shape
		CircleShape shape = new CircleShape();
		shape.setRadius(0.1f);
		// Fixturedef
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0f;
		fixtureDef.isSensor = true;
		fixtureDef.shape = shape;
		
		// création du body
		bodyAttach = PhysicWorld.getWorldPhysic().createBody(def);
		// fixture
		bodyAttach.createFixture(fixtureDef);
		
		
		
		// on lie en weld l'objet
		WeldJointDef wd = new WeldJointDef();
		wd.initialize(bodyAttach, bravo, point);
		PhysicWorld.getWorldPhysic().createJoint(wd);
		
		this.circleShape = new org.jsfml.graphics.CircleShape();
		this.circleShape.setFillColor(Color.WHITE);
		this.circleShape.setRadius(5f);
		
		// on va créer les objets node intermédiaire
		this.alphaBody = alpha;
		this.bravoBody = bravo;
		// on récupère les positions
		Vec2 posAlpha = this.alphaBody.getPosition();
		Vec2 posBravo = point;
		// on défini le vecteur de direction
		Vec2 dir = posBravo.sub(posAlpha);
		float lenghtTotal = dir.length();
		dir.normalize();
		// on défini la longueur des liens entre les nodes
		float lenght = lenghtTotal / (float)nbNode;
		// on compute les positions
		Vec2 posNode = posAlpha;
		// compute body
		Body bodyNode = this.alphaBody;
		
		// on boucle pour créer les nodes
		for(int i=1;i<nbNode;i++)
		{
			// on créer un node
			posNode = posNode.add(dir.mul(lenght * i));
			bodyNode = this.createNode(posNode, lenght, bodyNode);
			this.listNodes.add(bodyNode);
		}
		
		// on lie ensuite au node attach
		// il faut accroche le nouveau body avec le body previous
		DistanceJointDef distanceDef = new DistanceJointDef();
		distanceDef.initialize(bodyNode,this.bodyAttach,bodyNode.getWorldCenter(),bodyAttach.getWorldCenter());
		distanceDef.length = lenght;
		distanceDef.collideConnected = false;
		distanceDef.frequencyHz = 15f;
		distanceDef.dampingRatio = 1f;
		distanceDef.type = JointType.DISTANCE;
				// création du joint
		DistanceJoint distance = (DistanceJoint) PhysicWorld.getWorldPhysic().createJoint(distanceDef);

		
		this.listNodes.add(bodyAttach);
		
	}
	
	private Body createNode(Vec2 pos,float lenght,Body bodyNodePrevious)
	{
		// creatin du body def
		BodyDef def = new BodyDef();
		def.active = true;
		def.bullet = false;
		def.fixedRotation = false;
		def.position = pos;
		def.type = BodyType.DYNAMIC;
		
		// création du body
		Body body = PhysicWorld.getWorldPhysic().createBody(def);
		// création du shape
		CircleShape circle = new CircleShape();
		circle.m_radius = 0.1f;
		// Fixture definition
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0f;
		fixtureDef.isSensor = true;
		fixtureDef.shape = circle;
		// creation du fixture
		body.createFixture(fixtureDef);
		// on retourne le body créé
		
				
		// il faut accroche le nouveau body avec le body previous
		DistanceJointDef distanceDef = new DistanceJointDef();
		distanceDef.initialize(bodyNodePrevious,body,bodyNodePrevious.getWorldCenter(),body.getWorldCenter());
		distanceDef.length = lenght;
		distanceDef.collideConnected = false;
		distanceDef.frequencyHz = 15f;
		distanceDef.dampingRatio = 1f;
		distanceDef.type = JointType.DISTANCE;
		// création du joint
		DistanceJoint distance = (DistanceJoint) PhysicWorld.getWorldPhysic().createJoint(distanceDef);
		
		// retour du body
		return body;
		
	}
	
	public void destroyGrapnel()
	{
		// on boucle dans la liste des nodes pour détruire les jointure
		for(Body b : this.listNodes)
		{
			// on détruit le body également
			if(b.getJointList() != null)
				PhysicWorld.getWorldPhysic().destroyJoint(b.getJointList().joint);
			if(b != this.alphaBody)
				PhysicWorld.getWorldPhysic().destroyBody(b);
		}
		
		if(this.alphaBody != null && this.bravoBody != null)
		{
		
			if(this.alphaBody.getJointList() != null)
				PhysicWorld.getWorldPhysic().destroyJoint(this.alphaBody.getJointList().joint);
			if(this.bravoBody.getJointList() != null)
				PhysicWorld.getWorldPhysic().destroyJoint(this.bravoBody.getJointList().joint);
			
	
			PhysicWorld.getWorldPhysic().destroyBody(bodyAttach);
		}
		// on vide la liste
		this.listNodes.clear();
	}
	
	@Override
	public void draw(RenderTarget render, RenderStates state) 
	{
		// on vide le vectors
		vectors.clear();
		// on boucle dans la liste des nodes pour crée les point de la ligne
		for(Body b : listNodes)
		{
			// on crée un vertex
			Vertex v = new Vertex(new Vector2f(b.getPosition().x * PhysicWorld.getRatioPixelMeter(),b.getPosition().y * PhysicWorld.getRatioPixelMeter()),Color.WHITE);
			// on ajoute dans le vectors
			vectors.add(v);
			
		}
		
		// render
		render.draw(vectors,state);
		
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
