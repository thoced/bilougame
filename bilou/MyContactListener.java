package bilou;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import CorePlayer.RobotBase;


public class MyContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) 
	{
		// TODO Auto-generated method stub
			
			do
			{
			
				Object tempA = contact.m_fixtureA.m_body.getUserData();
				Object tempB = contact.m_fixtureB.m_body.getUserData();
					
				if(tempA != null && tempA.getClass().getSuperclass() == RobotBase.class)
				{
					((RobotBase)tempA).setIsground(true);
					
				}
				
	
				if(tempB != null && tempB.getClass().getSuperclass() == RobotBase.class)
				{
					((RobotBase)tempB).setIsground(true);
				}
				
			contact = contact.getNext();
			
			}while(contact != null);
			
	}

	@Override
	public void endContact(Contact contact)
	{
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
