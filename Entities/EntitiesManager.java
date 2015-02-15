package Entities;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.event.Event;

import bilou.Camera;
import bilou.ICoreBase;

public class EntitiesManager implements ICoreBase
{
	// small robot
	private SmallRobotView playerSmallRobot;
	// big robot
	private BigRobotView playerBigRobot;
	
	// reférence vers le robot selectioné
	private EntitieBase playerSelected;
	
	
	public EntitiesManager()
	{
		
	}
	
	public void SwitchPlayer()
	{
		// inverse la selection
		if(playerSmallRobot.pControl.isSelected)
		{
			playerSmallRobot.pControl.setSelected(false);
			playerBigRobot.pControl.setSelected(true);
			
		}
		else
		{
			playerSmallRobot.pControl.setSelected(true);
			playerBigRobot.pControl.setSelected(false);
			
		}
		
		
		
		
	}
	
	@Override
	public void update(Time deltaTime)
	{
		// TODO Auto-generated method stub
		playerSmallRobot.Update(deltaTime);
		playerBigRobot.Update(deltaTime);
	}

	

	@Override
	public void loadContent() 
	{
		// TODO Auto-generated method stub
		//Load content du TextureManager
		playerSmallRobot = new SmallRobotView();
		playerSmallRobot.LoadContent();
		playerSmallRobot.SetPosition(new Vector2f(1000,-400));
		
		playerBigRobot = new BigRobotView();
		playerBigRobot.LoadContent();
		playerBigRobot.SetPosition(new Vector2f(30,-400));
		
		// initialisation du robot selectionné
		playerSelected = playerSmallRobot;
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
	public void catchEvent(Event e) 
	{
		// TODO Auto-generated method stub
		this.playerSelected.SetEvent(e);
		
	}
	@Override
	public void draw(RenderTexture render, RenderStates state) 
	{
		
		render.draw(playerSmallRobot.getSpritePlayer());
		
		render.draw(playerBigRobot.getSpritePlayer());
		
	}
	
}
