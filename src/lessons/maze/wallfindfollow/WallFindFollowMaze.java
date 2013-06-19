package lessons.maze.wallfindfollow;

import java.awt.Color;
import java.io.IOException;

import jlm.core.model.lesson.ExecutionProgress;
import jlm.core.model.lesson.ExerciseTemplated;
import jlm.core.model.lesson.Lesson;
import jlm.universe.BrokenWorldFileException;
import jlm.universe.Direction;
import jlm.universe.Entity;
import jlm.universe.World;
import jlm.universe.bugglequest.AbstractBuggle;
import jlm.universe.bugglequest.Buggle;
import jlm.universe.bugglequest.BuggleWorld;
import jlm.universe.bugglequest.exception.AlreadyHaveBaggleException;
import jlm.universe.bugglequest.exception.NoBaggleUnderBuggleException;

public class WallFindFollowMaze extends ExerciseTemplated {

	public WallFindFollowMaze(Lesson lesson) throws IOException, BrokenWorldFileException {
		super(lesson);
		tabName = "Escaper";
		nameOfCorrectionEntity = "lessons.maze.wallfollower.WallFollowerMazeEntity"; // Use the same entity than in previous exercise
		
		/* Create initial situation */
		BuggleWorld myWorlds[] = new BuggleWorld[2];
		myWorlds[0] = new BuggleWorld("Labyrinth", 1, 1); 
		myWorlds[0].readFromFile("lessons/maze/wallfollower/WallFollowerMaze");
		new Buggle(myWorlds[0], "Thésée", 4, 10, Direction.NORTH, Color.black, Color.lightGray);
		
		myWorlds[1] = new BuggleWorld("Labyrinth2", 1, 1); 
		myWorlds[1].readFromFile("lessons/maze/wallfollower/WallFollowerMaze2");
		new Buggle(myWorlds[1], "ZoroRorronoa", 4, 10, Direction.NORTH, Color.black, Color.lightGray);
		
		setup(myWorlds);
	}

	
	// to shorten loading time
	@Override
	protected void computeAnswer(){
		AbstractBuggle b = (AbstractBuggle)answerWorld.get(0).getEntities().get(0);
		b.setPosFromLesson(11, 5);
		
		AbstractBuggle b2 = (AbstractBuggle)answerWorld.get(1).getEntities().get(0);
		b2.setPosFromLesson(11, 5);
		try {
			b.pickUpBaggle();
			b2.pickUpBaggle();
		} catch (NoBaggleUnderBuggleException e) {
			e.printStackTrace();
		} catch (AlreadyHaveBaggleException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public void check() {
		lastResult = new ExecutionProgress();
		for (World w: this.getWorlds(WorldKind.CURRENT)) 
			for (Entity e:w.getEntities()) {
				lastResult.totalTests++;
				if (!((AbstractBuggle) e).isCarryingBaggle())
					lastResult.details += "Buggle "+e.getName()+" didn't find the baggle";
				else
					lastResult.passedTests++;
			}
	}
}
