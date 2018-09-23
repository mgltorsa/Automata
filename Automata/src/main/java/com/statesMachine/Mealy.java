/**
 * 
 */
package com.statesMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.automata.*;

/**
 * @author Miguel
 * @author alejandro
 * class used for modeling a Automata of type Mealy
 */
@SuppressWarnings("serial")
public class Mealy extends Automata implements IMealy{

	public String function(IState state, String stimulus) {
		IMealyTransition transition=(IMealyTransition) getTransition(state, stimulus);
		
		return transition.getResponse();
	}
	public void addTransition(IState stateInitial, IState stateFinal, String stimulus, String response) {
		IMealyTransition tran=new MealyTransition(stimulus,response,stateFinal);
		addTransition(stateInitial, tran);
		
	}
	public IAutomata getEquivalent() {
		Alphabet alphabet=getLanguage();
		char[] alp=alphabet.getAlphabet();
		ArrayList<HashSet<IState>> p1=new ArrayList<HashSet<IState>>();
		HashMap<String,IState> states=(HashMap<String, IState>) getStates().clone();
		ArrayList<IState> statesValues=new ArrayList<IState>(states.values());
		int size=statesValues.size();
		boolean[] process=new boolean[size];
		for (int i=0;i<size;i++) {
			if(process[i]) {
				break;
			}
			IState iState=statesValues.get(i);
			HashSet<IState> group=new HashSet<IState>();
			group.add(iState);
			for (int j=i+1;j<size;j++) {
				IState iState1=statesValues.get(j);

				boolean same=true;
				for (char sti : alp) {
					if(i!=j&&!process[j]) {
						IMealyTransition tr=(IMealyTransition) getTransition(iState, sti+"");
						IMealyTransition tra=(IMealyTransition) getTransition(iState1, sti+"");
						same=same&&tra.getResponse().equals(tr.getResponse());
						if(!same) {
							break;
						}
					}
				}
				if(same&&!process[j]) {
					group.add(iState1);
					process[j]=true;
				}
			}
			process[i]=true;
			p1.add(group);

		}
		ArrayList<HashSet<IState>> tmp=getFinalPartition(p1);
		IMealy mealy=new Mealy();
		mealy.setLanguage(alphabet.toString());
		HashMap<String,HashSet<IState>> automata=new HashMap<String, HashSet<IState>>();
		int i=1;
		for (HashSet<IState> c : tmp) {
			automata.put("Q."+i, c);
			mealy.addState("Q."+i);
			i++;
		}
		
		
		ArrayList<String> keys=new ArrayList<String>(automata.keySet());
		for (String string : keys) {
			HashSet<IState> proces=automata.get(string);
			IState first=proces.iterator().next();
			if(getInitState()!=null) {
				if(proces.contains(getInitState())) {
					mealy.setInitialState(mealy.getState(string));
				}
			}
			for (char st : alp) {
				IMealyTransition tran=(IMealyTransition) getTransition(first, st+"");
				IState second=tran.getStateFinal();
				for (String key : keys) {
					if(automata.get(key).contains(second)) {
						mealy.addTransition(mealy.getState(string), mealy.getState(key), st+"", tran.getResponse());
						break;
					}
				}
				
			}
			
		}
		
			
		return mealy;
	}
	public static void main(String[] args) {
		IMealy mealy=new Mealy();
		mealy.setLanguage("a,b");
		mealy.addState("A");
		mealy.addState("B");
		mealy.addState("C");
		mealy.addState("D");
		mealy.addState("E");
		mealy.addState("F");
		mealy.addState("G");
		mealy.addTransition(mealy.getState("A"),mealy.getState("B"),"a","0");
		mealy.addTransition(mealy.getState("A"),mealy.getState("E"),"b","1");
		
		mealy.addTransition(mealy.getState("B"),mealy.getState("F"),"a","1");
		mealy.addTransition(mealy.getState("B"),mealy.getState("C"),"b","1");
		
		mealy.addTransition(mealy.getState("C"),mealy.getState("D"),"a","0");
		mealy.addTransition(mealy.getState("C"),mealy.getState("F"),"b","1");
		
		mealy.addTransition(mealy.getState("D"),mealy.getState("G"),"a","1");
		mealy.addTransition(mealy.getState("D"),mealy.getState("C"),"b","1");
		
		mealy.addTransition(mealy.getState("E"),mealy.getState("F"),"a","1");
		mealy.addTransition(mealy.getState("E"),mealy.getState("F"),"b","1");
		
		mealy.addTransition(mealy.getState("F"),mealy.getState("F"),"a","1");
		mealy.addTransition(mealy.getState("F"),mealy.getState("F"),"b","1");
		
		mealy.addTransition(mealy.getState("G"),mealy.getState("F"),"a","1");
		mealy.addTransition(mealy.getState("G"),mealy.getState("F"),"b","1");
		
			
		IAutomata equi=mealy.getEquivalent();
		HashMap<String, IState> states=equi.getStates();
		Alphabet al=equi.getLanguage();
		char[] len=al.getAlphabet();
		String line="      ";
		for (int i = 0; i < len.length; i++) {
			line+=len[i]+"     ";
		}
		System.out.println(line);
		ArrayList<String> keys=new ArrayList<String>(states.keySet());
		for (String string : keys) {
			String l=equi.getState(string)+"  ";
			for (int i = 0; i < len.length; i++) {
				IMealyTransition tra=(IMealyTransition) equi.getTransition(equi.getState(string), len[i]+"");
				l+=tra.getStateFinal()+","+tra.getResponse()+"   ";
			}
			System.out.println(l);
		}
		
		

	}


	
	
	

}
