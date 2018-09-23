package manager;

import java.util.HashMap;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

import com.automata.IAutomata;
import com.automata.IState;
import com.automata.ITransition;
import com.automata.Transition;
import com.statesMachine.Mealy;
import com.statesMachine.MealyTransition;
import com.statesMachine.Moore;
import com.statesMachine.MooreState;

public class AutomataManager {
	public final static String MEALY = "MEALY";
	public final static String MOORE = "MOORE";
	private final static String STATE = "state";
	private final static String OUTPUT = "output";
	private final static String TRANSITION_INPUT = "t_input";
	private final static String TO_STATE = "to_state";

	private IAutomata automaton;
	private IAutomata equivalent;

	public void createMachine(String type) {
		if (type.equals(MEALY)) {
			automaton = new Mealy();
		} else if (type.equals(MOORE)) {
			automaton = new Moore();
		}
	}

	public boolean validateLanguage(String character) {
		return automaton.getLanguage().contains(character);
	}

	public boolean existState(String id) {

		return automaton.getState(id) != null;
	}

	private void addState(HashMap<String, String> data) {
		if (automaton instanceof Moore) {
			IState state = createMooreState(data);
			automaton.addState(state);
		} else {
			automaton.addState(data.get(STATE));
		}
		
	}

	private IState createMooreState(HashMap<String, String> data) {
		IState state = new MooreState(data.get(STATE), data.get(OUTPUT));
		return state;
	}

	public void addTransition(HashMap<String, String> data) {
		ITransition transition = null;
		if (automaton instanceof Mealy) {
			transition = createMealyTransition(data);
		} else {
			transition = new Transition(data.get(TRANSITION_INPUT), automaton.getState(TO_STATE));
		}

		automaton.addTransition(automaton.getState(data.get(STATE)), transition);
	}

	private ITransition createMealyTransition(HashMap<String, String> data) {
		ITransition transition = null;
		if (automaton instanceof Mealy) {
			transition = new MealyTransition(data.get(TRANSITION_INPUT), data.get(OUTPUT),
					automaton.getState(TO_STATE));
		}

		return transition;
	}

	public void removeState(String id) {
		System.out.println("removed "+id);
		automaton.removeState(id);

	}

	public void addToAutomata(String... data) {
		if (automaton instanceof Moore) {
			addToMoore(data);
		} else {
			addToMealy(data);
		}

	}

	private void addToMealy(String[] info) {
		// DATA FORMAT:
		// Where inputi, out and outi is some character of the language.EstadoId is id
		// of some state and Output is the output for moore state
		// If is mealy:
		// {Estado:id,input1:estadoId,input1:out1,input2:estadoId,input2:out2....}
		String stateId = info[0].split(":")[1];
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(STATE, stateId);
		addState(data);
		for (int i = 1; i < info.length - 1; i += 2) {
			String[] transition = info[i].split(":");
			String outputInfo[] = info[i + 1].split(":");
			if (outputInfo.length > 1) {
				String outputTransition = outputInfo[1];
				data.put(TRANSITION_INPUT, transition[0]);
				data.put(OUTPUT, outputTransition);
				data.put(TO_STATE, transition[1]);
				if (!outputTransition.isEmpty() && !transition[1].isEmpty()) {
					addTransition(data);
				}
			}
		}

	}

	private void addToMoore(String... info) {
		// DATA FORMAT:
		// Where inputi, out and outi is some character of the language.EstadoId is id
		// of some state and Output is the output for moore state
		// If is Moore: {Estado:id,input1:estadoId,input2:estadoId....,Output:output}
		HashMap<String, String> data = new HashMap<String, String>();
		data.put(STATE, info[0]);
		data.put(OUTPUT, info[info.length - 1]);

		addState(data);
		for (int i = 1; i < info.length - 1; i++) {
			String[] infoTransition = info[i].split(":");
			data.put(TRANSITION_INPUT, infoTransition[0]);
			data.put(TO_STATE, infoTransition[1]);
			if (!data.get(TO_STATE).isEmpty()) {
				addTransition(data);
			}
		}

	}

	public void setLanguage(String... language) {
		String lang = "";
		for (int i = 0; i < language.length - 1; i++) {
			lang += language[i] + ",";
		}
		lang += language[language.length - 1];
		automaton.setLanguage(lang);
	}

	public void setName(String id) {
		automaton.setId(id);
		
	}

	public Graph getMachineGraphicGraph() {
		// TODO Auto-generated method stub
		return convertToGraph(automaton);
	}

	private Graph convertToGraph(IAutomata automaton) {
		Graph graph= new MultiGraph(automaton.getId());
		automaton.getInitState();
		
		return graph;
	}

	public Graph getEquivalentGraphicGraph() {
		return convertToGraph(equivalent);
		
	}

	public void generateEquivalent() {
		// TODO Auto-generated method stub
		
	}

}
