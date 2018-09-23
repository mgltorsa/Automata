/**
 * 
 */
package com.statesMachine;

import java.util.*;

import com.automata.*;


/**
 * @author Miguel
 * @author alejandro 
 * class used for modeling a Automata of type Mealy
 *
 */
public class Moore extends Automata implements IMoore{


	public Moore() {
		// TODO Auto-generated constructor stub
	}

	public String function(IState state, String stimulus) {
		IMooreState stateM=(IMooreState) getTransition(state, stimulus).getStateFinal();
		return stateM.getResponse();
	} 
	public void addState(String id,String response) {
		IState state=new MooreState(id, response);
		getStates().put(id, state);
	}
	
	public IAutomata getEquivalent() {
		HashMap<String,HashSet<IState>> p1=new HashMap<String,HashSet<IState>>();
		HashMap<String,IState> states=getStates();
		ArrayList<IState> statesValues=new ArrayList<IState>(states.values());
		for (IState iState : statesValues) {
			IMooreState tmp=(IMooreState) iState;
			String stimulus=tmp.getResponse();
			if(!p1.containsKey(stimulus)) {
				p1.put(stimulus, new HashSet<IState>());
			}
			p1.get(stimulus).add(tmp);
		}
		ArrayList<HashSet<IState>> tmp=getFinalPartition(new ArrayList<HashSet<IState>>(p1.values()));
		IMoore moore=new Moore();
		Alphabet alphabet=getLanguage();
		char[] alp=alphabet.getAlphabet();
		moore.setLanguage(alphabet.toString());
		HashMap<String,HashSet<IState>> automata=new HashMap<String, HashSet<IState>>();
		int i=1;
		for (HashSet<IState> c : tmp) {
			automata.put("Q."+i, c);
			String rs=((IMooreState) c.iterator().next()).getResponse();
			moore.addState("Q."+i,rs);
			i++;
		}
		ArrayList<String> keys=new ArrayList<String>(automata.keySet());
		for (String string : keys) {
			HashSet<IState> proces=automata.get(string);
			IState first=proces.iterator().next();
			for (char st : alp) {
				IState second=transitionFunction(first, st+"");
				for (String key : keys) {
					if(automata.get(key).contains(second)) {
						ITransition tr=new Transition(st+"",moore.getState(key));
						moore.addTransition(moore.getState(string),tr);
						break;
					}
				}
				
			}
			
		}
			
			
		
		return moore;
	}

	

	public static void main(String[] args) {
		IMoore moore=new Moore();
		moore.setLanguage("a,b");
		moore.addState("A","1");
		moore.addState("B","0");
		moore.addState("C","1");
		moore.addState("D","0");
		moore.addState("E","0");
		moore.addState("F","0");
		moore.addState("G","0");
		moore.addTransition(moore.getState("A"),new Transition("a",moore.getState("B")));
		moore.addTransition(moore.getState("A"),new Transition("b",moore.getState("E")));
		
		moore.addTransition(moore.getState("B"),new Transition("a",moore.getState("F")));
		moore.addTransition(moore.getState("B"),new Transition("b",moore.getState("C")));
		
		moore.addTransition(moore.getState("C"),new Transition("a",moore.getState("D")));
		moore.addTransition(moore.getState("C"),new Transition("b",moore.getState("F")));
		
		moore.addTransition(moore.getState("D"),new Transition("a",moore.getState("G")));
		moore.addTransition(moore.getState("D"),new Transition("b",moore.getState("C")));
		
		moore.addTransition(moore.getState("E"),new Transition("a",moore.getState("F")));
		moore.addTransition(moore.getState("E"),new Transition("b",moore.getState("F")));
		
		moore.addTransition(moore.getState("F"),new Transition("a",moore.getState("F")));
		moore.addTransition(moore.getState("F"),new Transition("b",moore.getState("F")));
		
		moore.addTransition(moore.getState("G"),new Transition("a",moore.getState("F")));
		moore.addTransition(moore.getState("G"),new Transition("b",moore.getState("F")));
		
		IAutomata equi=moore.getEquivalent();
		HashMap<String, IState> states=equi.getStates();
		Alphabet al=equi.getLanguage();
		char[] len=al.getAlphabet();
		String line="    ";
		for (int i = 0; i < len.length; i++) {
			line+=len[i]+"  ";
		}
		System.out.println(line);
		ArrayList<String> keys=new ArrayList<String>(states.keySet());
		for (String string : keys) {
			String l=equi.getState(string)+"  ";
			for (int i = 0; i < len.length; i++) {
				l+=equi.transitionFunction(equi.getState(string),len[i]+"")+"  ";
			}
			System.out.println(l);
		}
		
		

	}

}
