package com.view;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import com.graphicManagers.ViewDialog;

import manager.AutomataManager;

import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class AutomatonView extends JPanel implements ActionListener, ItemListener {

	public final static String MACHINE = "M";
	public final static String AUTOMATON = "A";

	private static final String ALPHABET_REMOVE = "removeCharacter";
	private static final String STATE_REMOVE = "removeState";
	private static final String ADD_STATE = "Añadir estado";
	private static final String VIEW_GRAPHIC = "Ver grafico";
	private static final String TYPED_NAME = "typed Name";
	private static final String GENERATE = "generate";

	private JTextField textField;
	private JCheckBox chxBinary;
	private JComboBox<Character> comboBox;
	private AutomatonTableModel model;
	private String machineType;
	private JTable table;
	private JButton btnViewGraphic;
	private JButton btnAddState;
	private JButton btnGenerateEquivalent;
	private AutomataViewer viewer;
	private String typeView;
	private ViewDialog viewDialog;

	public AutomatonView(String type, AutomataViewer viewer) {
		machineType = AutomataManager.MEALY;
		this.viewer = viewer;
		this.typeView = type;
		ViewFactory.createDefaultComponentPane(this);
		if (type.equals(MACHINE)) {
			createMachineStateView();
		} else {
			createAutomatonView();
		}

	}

	public void createAutomatonView() {
		initializeDefaultView();
		setFunctionalAlphabet(false);
		btnAddState.setEnabled(false);
		btnAddState.setVisible(false);
		btnGenerateEquivalent.setEnabled(false);
		btnGenerateEquivalent.setVisible(false);
	}

	private void initializeDefaultView() {

		ButtonGroup radioGroup = new ButtonGroup();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		add(lblNombre, gbc_lblNombre);

		textField = new JTextField();
		textField.setActionCommand(TYPED_NAME);
		textField.addActionListener(this);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 4;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblAlfabeto = new JLabel("Alfabeto");
		GridBagConstraints gbc_lblAlfabeto = new GridBagConstraints();
		gbc_lblAlfabeto.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlfabeto.gridx = 1;
		gbc_lblAlfabeto.gridy = 2;
		add(lblAlfabeto, gbc_lblAlfabeto);

		comboBox = new JComboBox<Character>();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 4;
		gbc_comboBox.gridy = 2;
		add(comboBox, gbc_comboBox);

		JRadioButton rdbtnMealy = new JRadioButton("Mealy");
		GridBagConstraints gbc_rdbtnMealy = new GridBagConstraints();
		gbc_rdbtnMealy.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMealy.gridx = 1;
		gbc_rdbtnMealy.gridy = 4;
		add(rdbtnMealy, gbc_rdbtnMealy);
		radioGroup.add(rdbtnMealy);
		rdbtnMealy.setActionCommand(AutomataManager.MEALY);
		rdbtnMealy.addItemListener(this);
		rdbtnMealy.setSelected(true);

		JRadioButton rdbtnMoore = new JRadioButton("Moore");
		GridBagConstraints gbc_rdbtnMoore = new GridBagConstraints();
		gbc_rdbtnMoore.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMoore.gridx = 4;
		gbc_rdbtnMoore.gridy = 4;
		add(rdbtnMoore, gbc_rdbtnMoore);
		radioGroup.add(rdbtnMoore);
		rdbtnMoore.setActionCommand(AutomataManager.MOORE);
		rdbtnMoore.addItemListener(this);

		JLabel lblTablaDeEstados = new JLabel("Tabla de estados");
		GridBagConstraints gbc_lblTablaDeEstados = new GridBagConstraints();
		gbc_lblTablaDeEstados.gridwidth = 6;
		gbc_lblTablaDeEstados.insets = new Insets(0, 0, 5, 5);
		gbc_lblTablaDeEstados.gridx = 1;
		gbc_lblTablaDeEstados.gridy = 5;
		add(lblTablaDeEstados, gbc_lblTablaDeEstados);

		initializeTable();

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 6;
		add(scrollPane, gbc_scrollPane);
		model = new AutomatonTableModel(this);

		btnViewGraphic = new JButton("Ver grafico");
		btnViewGraphic.setActionCommand(VIEW_GRAPHIC);
		btnViewGraphic.addActionListener(this);

		btnAddState = new JButton("Añadir estado");
		btnAddState.setActionCommand(ADD_STATE);
		btnAddState.addActionListener(this);

		GridBagConstraints gbc_btnAddState = new GridBagConstraints();
		gbc_btnAddState.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddState.gridx = 2;
		gbc_btnAddState.gridy = 10;
		add(btnAddState, gbc_btnAddState);

		GridBagConstraints gbc_btnViewGraphic = new GridBagConstraints();
		gbc_btnViewGraphic.insets = new Insets(0, 0, 0, 5);
		gbc_btnViewGraphic.gridx = 4;
		gbc_btnViewGraphic.gridy = 10;
		add(btnViewGraphic, gbc_btnViewGraphic);

		btnGenerateEquivalent = new JButton("Equivalente");
		GridBagConstraints gbc_generateEquivalent = new GridBagConstraints();
//		gbc_generateEquivalent.gridwidth = 6;
		gbc_generateEquivalent.insets = new Insets(0, 0, 0, 5);
		gbc_generateEquivalent.gridx = 3;
		gbc_generateEquivalent.gridy = 11;
		add(btnGenerateEquivalent, gbc_generateEquivalent);

		btnGenerateEquivalent.setActionCommand(GENERATE);
		btnGenerateEquivalent.addActionListener(this);
		btnGenerateEquivalent.setEnabled(false);
		btnGenerateEquivalent.setVisible(false);
	}

	private void initializeTable() {

		table = new JTable() {
			@Override
			public void editingStopped(ChangeEvent e) {
				int row = getEditingRow();
				int col = getEditingColumn();
				super.editingStopped(e); // must call the super code to have a working edition
				if (row == getRowCount() - 1 && col == getColumnCount() - 1) {
					model.addEmptyRow();
				}
			}

			@Override
			public void tableChanged(TableModelEvent e) {
				super.tableChanged(e);
				if (e.getType() == TableModelEvent.UPDATE || e.getType() == TableModelEvent.INSERT) {
					int column = e.getColumn();
					int row = e.getFirstRow();
					if (model != null) {
						if (row >= 0) {
							if (column >= 1) {
								if (!model.validateData(row, column)) {
									ViewFactory.showPopupMessage(table, "Ingrese estado o caracter valido");
									setValueAt("", row, column);
								}else {
									addRowToAutomata(row);
								}
							}  else if (column == 0) {
								addRowToAutomata(row);
								model.setEditable(row, column, false);
							}
						}
					}
				}
			}

		};
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ViewFactory.createDefaultComponentPane(table);
		table.setForeground(Color.BLACK);
		table.setFont(new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 16));
		table.setCellSelectionEnabled(true);
//		table.setSelectionBackground(new Color(152, 54, 40));
		table.setSelectionBackground(new Color(150, 122, 161));
//		table.setSelectionBackground(new Color(153,57,85));

	}

	public void createMachineStateView() {

		initializeDefaultView();

		comboBox.getEditor().getEditorComponent().addKeyListener(createKeyLister());
		comboBox.setComponentPopupMenu(createRemoveJPopMenu(ALPHABET_REMOVE));

		chxBinary = new JCheckBox("Binario");
		GridBagConstraints gbc_chckbxBinary = new GridBagConstraints();
		gbc_chckbxBinary.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxBinary.gridx = 5;
		gbc_chckbxBinary.gridy = 2;
		add(chxBinary, gbc_chckbxBinary);
		chxBinary.setEnabled(true);
		chxBinary.addItemListener(createCheckBoxListener());

		setFunctionalAlphabet(true);
		chxBinary.setSelected(true);
		table.setComponentPopupMenu(createRemoveJPopMenu(STATE_REMOVE));

	}

	private JPopupMenu createRemoveJPopMenu(String command) {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem remove = new JMenuItem("Eliminar");
		remove.setActionCommand(command);
		remove.addActionListener(this);
		menu.add(remove);

		return menu;
	}

	private void revalidateTable() {
		calculateModel();
	}

	public boolean validateData(String data, int type) {
		if (type == AutomatonTableModel.OUTPUT) {
			return viewer.validateLanguage(data);
		} else {
			return viewer.validateState(data);
		}
	}

	private void calculateModel() {

		if (machineType != null) {

			if (machineType == AutomataManager.MEALY) {
				setMealyModel();
			} else {
				setMooreModel();
			}
		}

	}

	public void setMealyModel() {
		machineType = AutomataManager.MEALY;
		changeTableModel();
		viewer.createMachine(machineType);

	}

	private void changeTableModel() {
		String[] columnNames = getColumnsNames();

		if (model != null && columnNames != null) {
			model = new AutomatonTableModel(this);
			model.setColumns(columnNames);
			if (columnNames.length > 5) {
				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table.setModel(model);
			} else {
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				table.setModel(model);
			}
		}

	}

	public void setMooreModel() {
		machineType = AutomataManager.MOORE;
		changeTableModel();
		viewer.createMachine(machineType);

	}

	private String[] getColumnsNames() {
		String[] columnNames = null;
		if (chxBinary != null && chxBinary.isSelected()) {
			columnNames = getBinaryAlphabet();

		} else {
			int alphabetLength = comboBox.getItemCount();
			if (alphabetLength > 0) {
				if (machineType == AutomataManager.MEALY) {
					ArrayList<String> columns = new ArrayList<String>();
					columns.add("Estado");
					for (int i = 0; i < alphabetLength; i++) {
						char c = comboBox.getItemAt(i);
						columns.add(c + "");
						columns.add(c + "-out");
					}
					columnNames = new String[columns.size()];
					columns.toArray(columnNames);
				} else {
					columnNames = new String[2 + alphabetLength];
					columnNames[columnNames.length - 1] = "output";
					columnNames[0] = "Estado";
					for (int i = 1; i < columnNames.length - 1; i++) {
						columnNames[i] = comboBox.getItemAt(i - 1) + "";
					}
				}
			}
		}
		setLanguageToAutomata();

		return columnNames;
	}

	private String[] getBinaryAlphabet() {
		String[] columnNames = null;
		if (machineType == AutomataManager.MEALY) {
			columnNames = new String[5];
			columnNames[1] = "0";
			columnNames[2] = "0-out";
			columnNames[3] = "1";
			columnNames[4] = "1-out";
		} else {
			columnNames = new String[4];
			columnNames[1] = "0";
			columnNames[2] = "1";
			columnNames[3] = "output";
		}

		columnNames[0] = "Estado";

		return columnNames;
	}

	private void setFunctionalAlphabet(boolean state) {
		comboBox.setEditable(state);
		comboBox.setFocusable(state);
		revalidateTable();
	}

	private void setBinaryLanguage() {
		setFunctionalAlphabet(false);
	}

	public void addRowToAutomata(int row) {
		String[] data = new String[model.getColumnCount()];
		String message = "";
		for (int i = 0; i < data.length; i++) {
			String value = (String) (model.getValueAt(row, i) == null ? "" : model.getValueAt(row, i));
			data[i] = model.getColumnName(i) + ":" + value;
		}
		if (data[0].trim().isEmpty()) {
			message = "No se agrego estado, debe agregar el nombre al estado para poder agregarlo";
		} else {
			viewer.addToAutomata(data);
		}

		ViewFactory.showPopupMessage(table, message);

	}

	public void showGraphOnDialog(ViewDialog auxDialog) {
		if (viewDialog != null) {
			viewDialog.dispose();

		}
		if (auxDialog != null) {
			viewDialog = auxDialog;
			viewDialog.setAlwaysOnTop(false);

			viewDialog.showOnCenter();
		}

	}

	private ItemListener createCheckBoxListener() {
		ItemListener listener = new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				int state = e.getStateChange();
				if (state == ItemEvent.SELECTED) {
					setBinaryLanguage();
				} else {
					setFunctionalAlphabet(true);
				}
			}
		};
		return listener;
	}

	private KeyListener createKeyLister() {
		KeyListener listener = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					String text = (String) comboBox.getEditor().getItem();
					if (text.length() > 1) {
						ViewFactory.showPopupMessage(comboBox, "¡Debe ingresar un caracter!");

					} else {
						comboBox.addItem(text.charAt(0));
						revalidateTable();
						ViewFactory.showPopupMessage(comboBox, "¡Caracter agregado!");
					}

				}
			}

		};
		return listener;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ALPHABET_REMOVE)) {
			int selectedIndex = comboBox.getSelectedIndex();
			if (selectedIndex != -1) {
				comboBox.removeItemAt(selectedIndex);
				revalidateTable();
			}
		} else if (e.getActionCommand().equals(STATE_REMOVE)) {
			int[] selectedRows = table.getSelectedRows();
			if (selectedRows.length > 0) {
				model.removeRows(selectedRows);
			}
		} else if (e.getActionCommand().equals(ADD_STATE)) {
			model.addEmptyRow();
			btnGenerateEquivalent.setEnabled(true);
			btnGenerateEquivalent.setVisible(true);
		} else if (e.getActionCommand().equals(VIEW_GRAPHIC)) {
			// TODO
			viewer.showGraphicOnDialog(this.typeView);
		} else if (e.getActionCommand().equals(TYPED_NAME)) {
			// TODO
			viewer.setAutomatonName(textField.getText());
		} else if (e.getActionCommand().equals(GENERATE)) {
			viewer.generateEquivalent();
		}

	}

	public void setLanguageToAutomata() {
		String[] language = null;
		if (chxBinary != null && chxBinary.isSelected()) {
			language = new String[2];
			language[0] = "1";
			language[1] = "2";
		} else if (comboBox.getItemCount() > 0) {
			language = new String[comboBox.getItemCount()];
			for (int i = 0; i < language.length; i++) {
				language[i] = comboBox.getItemAt(i) + "";
			}
		}
		viewer.setLanguageToAutomata(language);
	}

	public void itemStateChanged(ItemEvent e) {
		JRadioButton radio = (JRadioButton) e.getSource();
		String command = radio.getActionCommand();
		if (command.equals(AutomataManager.MEALY)) {
			setMealyModel();
		} else {
			setMooreModel();
		}
	}

	public class AutomatonTableModel extends DefaultTableModel {

		public static final int STATE = 0;
		public static final int OUTPUT = 1;

		private AutomatonView view;
		private ArrayList<Boolean> editables;

		public AutomatonTableModel(AutomatonView view) {
			super();
			this.view = view;
			editables = new ArrayList<Boolean>();
		}

		public void setEditable(int row, int column, boolean isEditable) {
			editables.set(row, isEditable);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if (column == 0) {
				return editables.get(row);
			}
			return super.isCellEditable(row, column);
		}

		public boolean validateData(int row, int column) {
			String data = (String) getValueAt(row, column);
			if (data != null && !data.isEmpty()) {
				if (getColumnName(column).contains("out")) {
					return view.validateData(data, OUTPUT);
				} else {
					return view.validateData(data, STATE);
				}
			}

			return true;
		}

		public void addEmptyRow() {
			this.addRow(new String[1]);
			editables.add(true);
		}

		public void removeColumn(int column) {
			columnIdentifiers.remove(column);
			for (Object row : dataVector) {
				((Vector<?>) row).remove(column);
			}
			fireTableStructureChanged();
		}

		public void setColumns(String... columnNames) {
			clearColumns();
			for (int i = 0; i < columnNames.length; i++) {
				addColumn(columnNames[i]);
			}
		}

		public void removeRows(int... rows) {
			for (int i = rows.length - 1; i >= 0; i--) {
				removeRow(rows[i]);
				editables.remove(i);
			}
		}

		@Override
		public void removeRow(int row) {
			String data = (String) getValueAt(row, 0);
			super.removeRow(row);
			removeInColumns(data);

		}

		private void removeInColumns(String data) {
			for (int i = 1; i < getColumnCount(); i++) {
				for (int j = 0; j < getRowCount(); j++) {
					if (getValueAt(j, i) != null && getValueAt(j, i).equals(data)) {
						setValueAt("", j, i);
					}
				}
			}

		}

		@Override
		public String getColumnName(int column) {
			return (String) columnIdentifiers.elementAt(column);
		}

		private void clearColumns() {
			for (int i = 0; i < columnIdentifiers.size(); i++) {
				removeColumn(i);
			}
		}

	}

}
