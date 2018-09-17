package com.view;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.BoxLayout;

import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;




public class AutomatonView extends JPanel {
	private JTable table;

	
	public AutomatonView(String title) {
		setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 16), null));
		setFocusable(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));		
		
		table = new JTable();
		TableModel model = new DefaultTableModel() {
			
				Class[] columnTypes = new Class[] {
					String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			
		};
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Estados"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		add(table);
		
	}

}
