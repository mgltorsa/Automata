package com.view;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PerformerView extends JFrame implements ActionListener{

	public final static String CREATE="Create state machine";
	public final static String LOAD="Load file";
	public final static String ABOUT="About program";

	private JPanel contentPane;
	private static PerformerView performerView;
	
	public static void main(String[] args) {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			PerformerView frame = PerformerView.getInstance();
			frame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	private PerformerView() {
		ViewFactories.createDefaultFrame(this);
//		setBackground(ViewFactories.FrameBackground.getColor());
//		setForeground(ViewFactories.FrameForeground.getColor());
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setBounds(100, 100, 540, 360);
//		contentPane = new JPanel();
//		contentPane.setBackground(ViewFactories.PaneBackground.getColor());
//		contentPane.setForeground(ViewFactories.PaneForeground.getColor());
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
		contentPane = (JPanel) this.getContentPane();
		setResizable(false);
		createPaneButtons();

	}
	public static PerformerView getInstance() {
		if(performerView ==null) {
			performerView = new PerformerView();
		}
		return performerView;
	}

	private void createPaneButtons() {
		contentPane.setLayout(null);
		ButtonPanel createMachine = new ButtonPanel();
		createMachine.setBounds(38, 40, 151, 109);
		createMachine.setBackground(ViewFactories.ButtonPaneBackgorund.getColor());
		createMachine.setCommand(CREATE);
		createMachine.setMessage(CREATE);
		contentPane.add(createMachine);
		
		ButtonPanel loadFile = new ButtonPanel();
		loadFile.setBounds(175, 169, 151, 109);
		loadFile.setBackground(ViewFactories.ButtonPaneBackgorund.getColor());
		loadFile.setCommand(LOAD);
		loadFile.setMessage(LOAD);
		contentPane.add(loadFile);
		
		ButtonPanel instructions = new ButtonPanel();
		instructions.setBounds(307, 40, 151, 109);
		instructions.setBackground(ViewFactories.ButtonPaneBackgorund.getColor());
		instructions.setCommand(ABOUT);
		instructions.setMessage(ABOUT);

		contentPane.add(instructions);
		addListeners(createMachine,loadFile,instructions);

	}

	private void addListeners(ButtonPanel... panels) {
		for(ButtonPanel panel : panels) {
			panel.addContainerListener(this);
		}
		
	}

	@Override
	public void dispose() {

		super.dispose();
		System.exit(0);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals(CREATE)) {
			showCreateMachine();
		}else if(command.equals(LOAD)) {
			loadFile();
		}else if(command.equals(ABOUT)) {
			showAboutPane();
		}
	}

	private void showAboutPane() {
		// TODO Auto-generated method stub
		
	}

	private void loadFile() {		
		
	}

	private void showCreateMachine() {
		MainView.getInstance().setVisible(true);
		this.setExtendedState(ICONIFIED);
	}

	
}
