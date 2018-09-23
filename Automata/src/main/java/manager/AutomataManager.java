package manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import com.automata.IAutomata;
import com.automata.IState;
import com.automata.ITransition;
import com.automata.Transition;
import com.statesMachine.IMealy;
import com.statesMachine.IMealyTransition;
import com.statesMachine.IMooreState;
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
	private static SpriteManager spriteManager = null;

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
		return automaton.getLanguage().toString().contains(character);
	}

	public boolean existState(String id) {

		return automaton.getState(id) != null;
	}

	private void addState(HashMap<String, String> data) {
		if (!existState(data.get(STATE))) {
			if (automaton instanceof Moore) {
				IState state = createMooreState(data);
				automaton.addState(state);
			} else {
				automaton.addState(data.get(STATE));
			}
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
			transition = new Transition(data.get(TRANSITION_INPUT), automaton.getState(data.get(TO_STATE)));
		}

		if (!automaton.getState(data.get(STATE)).getTransitions().containsKey(transition.getstimulus())) {
			automaton.addTransition(automaton.getState(data.get(STATE)), transition);
		}
	}

	private ITransition createMealyTransition(HashMap<String, String> data) {
		ITransition transition = null;
		transition = new MealyTransition(data.get(TRANSITION_INPUT), data.get(OUTPUT),
				automaton.getState(data.get(TO_STATE)));

		return transition;
	}

	public void removeState(String id) {
		System.out.println("removed " + id);
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
		return parseGraph(automaton);
	}

	private Graph parseGraph(IAutomata automaton) {
		Graph graph = null;
		if (automaton != null && !automaton.getStates().isEmpty()) {
			System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
			graph = new MultiGraph(automaton.getId(), false, false);
			spriteManager = new SpriteManager(graph);
			IState initState = automaton.getInitState();
			HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
			Stack<IState> stack = new Stack<IState>();
			setNodesModelToGraph(initState, automaton, graph);
			graphicGraphDfs(initState, stack, graph, visited);
			while (!stack.isEmpty()) {
				IState tempState = stack.pop();
				graphicGraphDfs(tempState, stack, graph, visited);
			}
		}
		return graph;
	}

	private void setNodesModelToGraph(IState initState, IAutomata automaton, Graph graph) {
		graph.addNode(initState.getId());
		setInitialNodeView(graph.getNode(initState.getId()), graph);
		for (IState state : automaton.getStates().values()) {
			graph.addNode(state.getId());
			setDefaultNodeView(graph.getNode(state.getId()), graph);
		}

	}

	private void setDefaultNodeView(Node node, Graph graph) {
		node.setAttribute("ui.style", "size: 40px; fill-color: rgb(133,207,255) ; stroke-mode: plain; "
				+ "stroke-width: 2px; stroke-color: #CCF; shadow-mode: gradient-radial; shadow-width: "
				+ "10px; shadow-color: white; shadow-offset: 0px; text-color: black; " + "text-style: italic ;");

	}

	private void setInitialNodeView(Node node, Graph graph) {
		node.setAttribute("ui.style", "size: 40px; fill-color: rgb(185,244,99) ; stroke-mode: plain; "
				+ "stroke-width: 2px; stroke-color: #CCF; shadow-mode: gradient-radial; shadow-width: "
				+ "10px; shadow-color: white; shadow-offset: 0px; text-color: black; " + "text-style: bold-italic ;");

	}

	private void setDefaultEdgeModel(Edge edge, Graph graph) {
		edge.addAttribute("ui.style",
				"fill-color: rgb(250,124,97) ; text-color: black; text-style: italic ; text-size: 12px ; size: 3px ;");
		String[] ids = edge.getId().split("-");
		String stimulus = ids[1].split(":")[1];

		Sprite sprite = spriteManager.addSprite(edge.getId() + stimulus);
		sprite.attachToEdge(edge.getId());
	}

	private void graphicGraphDfs(IState state, Stack<IState> stack, Graph graph, HashMap<String, Boolean> visited) {
		visited.put(state.getId(), true);
		Iterator<String> keys = state.getTransitions().keySet().iterator();
		while (keys.hasNext()) {
			ITransition transition = state.getTransitions().get(keys.next());
			IState finalState = transition.getStateFinal();
			if (graph != null) {
				String keyEdge = state.getId() + "-" + finalState.getId() + ":" + transition.getstimulus();
				graph.addEdge(keyEdge, state.getId(), finalState.getId());
				setDefaultEdgeModel(graph.getEdge(keyEdge), graph);
			}
			if (!visited.containsKey(finalState.getId())) {
				stack.push(transition.getStateFinal());
			}
		}
	}

	public Graph getEquivalentGraphicGraph() {
		return parseGraph(equivalent);

	}

	public void generateEquivalent() {
		equivalent = automaton.getEquivalent();

	}

	public boolean canSerializeMachine() {
		if (automaton != null) {
			return true;
		}
		return false;
	}

	public void serializeMachine(String path) {
		try {
			FileWriter writer = new FileWriter(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void load(String path) {
		// TODO Auto-generated method stub

	}

	public HashMap<String, String> getDataMachine() {

		return getData(automaton);
	}

	private HashMap<String, String> getData(IAutomata automaton) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", automaton.getId());
		int columnCount = 0;
		if (automaton instanceof Mealy) {
			map.put("type", MEALY);
			columnCount = (1 + (automaton.getLanguage().getAlphabet().length * 2));
			map.put("column-count", "" + columnCount);
		} else {
			map.put("type", MOORE);
			columnCount = 2 + (automaton.getLanguage().getAlphabet().length);
			map.put("column-count", "" + columnCount);
		}
		map.put("states-count", automaton.getStates().size() + "");
		map.put("alphabet-count", automaton.getLanguage().getAlphabet().length + "");

		map.put("alphabet", automaton.getLanguage().toString());

		createRows(map, automaton);

		return map;
	}

	private void createRows(HashMap<String, String> map, IAutomata automaton) {
		int rows = 0;
		ArrayList<IState> arr = getStatesArray(automaton);

		for (int k = 0; k < arr.size(); k++) {

			IState state = arr.get(k);
			String line = "";
			ITransition[] transitions = new ITransition[state.getTransitions().values().size()];
			line = state.getId();
			if (automaton instanceof Mealy) {
				for (int i = 0; i < transitions.length; i++) {
					String idFinal = transitions[i].getStateFinal().getId();
					line += ","+transitions[i].getstimulus() + "," + idFinal + ","
							+ transitions[i].getstimulus() + "," + ((IMealyTransition) transitions[i]).getResponse();

				}
				map.put(rows + "", line);
			} else {
				for (int i = 0; i < transitions.length; i++) {
					String idFinal = transitions[i].getStateFinal().getId();
					line += "," + transitions[i].getstimulus() + "," + idFinal;
				}
				line+="," + ((IMooreState) state).getResponse();
				map.put(rows + "", line);

			}
			rows++;
		}

	}

	private ArrayList<IState> getStatesArray(IAutomata automaton2) {
		ArrayList<IState> arr = new ArrayList<IState>();
		IState initState = automaton.getInitState();

		HashMap<String, IState> states = (HashMap<String, IState>) automaton.getStates().clone();
		states.remove(initState.getId());
		arr.add(initState);

		Iterator<IState> iterator = states.values().iterator();
		while (iterator.hasNext()) {
			arr.add(iterator.next());
		}
		return arr;
	}

	public HashMap<String, String> getDataEquivalent() {
		return getData(equivalent);
	}

}
