package tu.sofia.aez.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import tu.sofia.aez.om.Dvigatel;
import tu.sofia.aez.util.ExcelService;

public class DvigatelLibraryWindow extends JFrame {

	private static final long serialVersionUID = -5591244852799784837L;
	private JList<String> dvigatelList;
	private JButton editSaveButton;
	private UIDvigatel dvigatel;
	private UIDvigatel dvigatelCharacteristics;
	private JButton selectDvigatel = new JButton();
	private List<Dvigatel> loadedDvigatels = new ArrayList<Dvigatel>();

	public DvigatelLibraryWindow(UIDvigatel uiDvigatel, JButton button) {
		loadDvigatels();
		setLayout(new MigLayout("center"));
		dvigatel = uiDvigatel;
		editSaveButton = button;
		setResizable(false);
		setSize(UIConstants.LIBRARY_WIDTH, UIConstants.LIBRARY_HEIGHT);
		setTitle("Изберете двигател от библиотеката");
		setVisible(true);
		JLabel title = new JLabel("Изберете двигател от списъка");
		title.setFont(new Font("Arial", Font.BOLD, 22));
		add(title, "span,align center, wrap, gapy 20");

		List<String> dvigatelNames = new ArrayList<String>();
		for (Dvigatel dvg : loadedDvigatels) {
			dvigatelNames.add(dvg.getName());
		}

	    dvigatelList = new JList<String>(dvigatelNames.toArray(new String[dvigatelNames.size()]));

		dvigatelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dvigatelList.setLayoutOrientation(JList.VERTICAL);
		dvigatelList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(dvigatelList);
		listScroller.setPreferredSize(new Dimension(700, 200));
		add(listScroller, "span,align center, wrap, gapy 20");
		dvigatelList.addListSelectionListener(new DvigatelListSelectionListener());

		dvigatelCharacteristics = new UIDvigatel(dvigatel.getDvigatel());
		add(dvigatelCharacteristics.getPanel(false, true), "span,align center, wrap, gapy 20");

		selectDvigatel.setText("Избери този двигател");
		selectDvigatel.setFont(new Font("Arial", Font.BOLD, 16));
		selectDvigatel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DvigatelLibraryWindow.this.dvigatelCharacteristics.toDvigatel();
				dvigatel.setDvigatel(dvigatelCharacteristics.getDvigatel());
				dvigatel.fromDvigatel();
				dvigatel.refreshUI();
				editSaveButton.setEnabled(true);
				DvigatelLibraryWindow.this.setVisible(false);

			}
		});
		add(selectDvigatel, "span,align center, wrap, gapy 20");
	}

	private class DvigatelListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())
				return;
			DvigatelLibraryWindow.this.selectDvigatel();
		}

	}

	private void selectDvigatel() {
		int index=0;
		index=dvigatelList.getSelectedIndex();
		dvigatelCharacteristics.setDvigatel(loadedDvigatels.get(index));
		dvigatelCharacteristics.fromDvigatel();
	}

	private void loadDvigatels() {
		try {
			ExcelService excelService = new ExcelService();
			loadedDvigatels = excelService.loadDvigateli();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
