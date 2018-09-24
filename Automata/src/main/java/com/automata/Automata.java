/**
 * 
 */
package com.automata;

import java.util.*;

/**
 * @author Miguel
 *
 */
@SuppressWarnings("serial")
public abstract class Automata implements IAutomata {
	private String id;
	private Alphabet language;
	private IState initState;
	private HashMap<String, IState> states;

	public Automata() {
		this("DefAutomaton");
	}

	public Automata(String id) {
		this.id = id;
		language = new Alphabet();
		states = new HashMap<String, IState>();
	}

	public void convex() {
		HashMap<String, IState> tmp = new HashMap<String, IState>();
		HashSet<IState> mar = new HashSet<IState>();
		Stack<IState> stack = new Stack<IState>();
		stack.push(initState);
		while (!stack.isEmpty()) {
			IState proces = stack.pop();
			if (!mar.contains(proces)) {
				mar.add(proces);
				HashMap<String, ITransition> tran = proces.getTransitions();
				Iterator<ITransition> iterato = tran.values().iterator();
				while (iterato.hasNext()) {
					stack.push(iterato.next().getStateFinal());
				}
				tmp.put(proces.getId(), proces);
			}
		}

		states = tmp;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void addState(String id) {
		IState state = new State(id);
		addState(state);
	}

	public void addState(IState state) {
		if (states.isEmpty()) {
			initState = state;
		}
		states.put(state.getId(), state);
	}

	public IState getState(String name) {
		return states.get(name);
	}

	public IState getInitState() {
		return initState;
	}

	public void removeState(String id) {
		IState oldState = states.remove(id);
		if (oldState != null) {
			String[] transitions = new String[oldState.getTransitions().keySet()
					.size()];
			if (transitions.length > 1) {
				oldState.getTransitions().keySet().toArray(transitions);
				Arrays.sort(transitions);
				initState = oldState.getTransitions().get(transitions[0])
						.getStateFinal();
			}
		}
	}
	public HashMap<String, ITransition> getTransitions(IState state) {
		return state.getTransitions();
	}

	public Alphabet getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = new Alphabet(language);

	}

	public void addTransition(IState from, ITransition transition) {
		from.addTransition(transition);
	}

	public IState transitionFunction(IState state, String stimulus) {
		return state.getTransitions().get(stimulus).getStateFinal();
	}

	public ArrayList<IState> getExtendendTransition(String stimulus) {
		return getExtendendTransition(initState, stimulus);
	}

	public ArrayList<IState> getExtendendTransition(IState state,
			String stimulus) {
		ArrayList<IState> ret = new ArrayList<IState>();
		for (int i = 0; i < stimulus.length(); i++) {
			String st = stimulus.charAt(i) + "";
			ret.add(state);
			state = transitionFunction(state, st);
		}
		ret.add(state);

		return ret;
	}

	public HashMap<String, IState> getStates() {
		return states;
	}

	public void setInitialState(IState state) {
		initState = state;
		String id = state.getId();
		if (!states.containsKey(id)) {
			states.put(id, state);
		}
	}

	public void setInitialState(String id) {
		initState = states.get(id);

	}

	public void addAll(IState... states) {
		for (IState state : states) {
			this.states.put(state.getId(), state);
		}

	}

	public ITransition getTransition(IState state, String stimulus) {

		return state.getTransitions().get(stimulus);
	}

	/**
	 * this method get the final partiton
	 * 
	 * @param firstPartition
	 *            first partiton with the groups of states with response in
	 *            comon.firstPartition!=null and all groups !=null
	 * @return the final partiton, when Pn-1=Pn
	 */
	public ArrayList<HashSet<IState>> getFinalPartition(
			ArrayList<HashSet<IState>> firstPartition) {
		ArrayList<HashSet<IState>> finalPartition = new ArrayList<HashSet<IState>>();
		Alphabet alphabet = getLanguage();
		char[] alp = alphabet.getAlphabet();
		HashSet<IState> partitionAux = new HashSet<IState>();

		boolean change = false;
		for (int k = 0; k < firstPartition.size();) {
			HashSet<IState> newPartiton = new HashSet<IState>();
			Iterator<IState> iterator = firstPartition.get(k).iterator();

			if (!partitionAux.isEmpty()) {
				iterator = partitionAux.iterator();
				partitionAux = new HashSet<IState>();
			}
			IState previus = iterator.next();
			newPartiton.add(previus);
			while (iterator.hasNext()) {
				IState state = iterator.next();
				boolean same = false;
				for (int i = 0; i < alp.length; i++) {
					IState fP = transitionFunction(previus, alp[i] + "");
					IState sP = transitionFunction(state, alp[i] + "");
					for (HashSet<IState> col : firstPartition) {
						if (col.contains(fP)) {
							same = col.contains(sP);
							break;
						}
					}
				}
				if (same) {
					newPartiton.add(state);
					previus = state;
				} else {
					partitionAux.add(state);
					change = true;
				}

			}
			finalPartition.add(newPartiton);
			if (partitionAux.isEmpty()) {
				k++;
			}

		}
		if (change) {
			return getFinalPartition(finalPartition);
		}
		return finalPartition;

	}
}
