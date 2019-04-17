
import java.util.LinkedList;


public class AgentMinMax extends GameAgent{

	public AgentMinMax(int agentType, int position, int peopleInCar) {
		super(agentType, position, peopleInCar);
	}

	
	@Override
	double calcHeuristic(StateNode node) {
		
		double h1=HeuristicCalculator.calculateGameF(node,Main.mostHeavyEdge,true);
		double h2=HeuristicCalculator.calculateGameF(node,Main.mostHeavyEdge,false);
		return h1-h2;
	}



	@Override
	void sortChildren(LinkedList<StateNode> children, StateNode parent) {
		
		if(parent.depth % 2 == 0) // agent from min to max 
			parent.children.sort(stateNodeComparatorMinToMax);
	    else // enemy from min to max
	        parent.children.sort(stateNodeComparatorMaxToMin);
		
		
		
	}


	@Override
	void updateParentsScore(StateNode parent, StateNode child) {
		if(parent.depth % 2 == 0) { // agent
			if((child.agentScore-child.opponentScore) > (parent.agentScore-parent.opponentScore) || parent.next==null) {
				parent.agentScore = child.agentScore;
				parent.opponentScore = child.opponentScore;
				parent.next = child;
			}
		}
	    else { // enemy
	    	if((child.opponentScore-child.agentScore) > (parent.opponentScore-parent.agentScore) || parent.next==null) {
	    		parent.agentScore = child.agentScore;
				parent.opponentScore = child.opponentScore;
				parent.next = child;
	    	}
	    }
		
		
	}
	
	
	boolean cutTree(StateNode child, StateNode parent) {
		
		if(parent.next==null)
			return false;
		
		if(parent.depth % 2 == 0) { // parent is agent, child is enemy
			if(parent.prev!=null) {
				int pDiff = parent.agentScore - parent.opponentScore;
				int prevDiff = parent.prev.agentScore - parent.prev.opponentScore;
				if(pDiff>prevDiff) return true;
			}
		}
		else { // parent is enemy, child is agent
			if(parent.prev!=null) {
				int pDiff = parent.agentScore - parent.opponentScore;
				int prevDiff = parent.prev.agentScore - parent.prev.opponentScore;
				if(pDiff<prevDiff) return true;
			}
		} 
		return false;
	}

}
