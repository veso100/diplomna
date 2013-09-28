package tu.sofia.aez.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;

public class DvigatelLibraryWindow extends JFrame {

	private static final long serialVersionUID = -5591244852799784837L;
	
	private JButton editSaveButton;
	private UIDvigatel dvigatel;
	private UIDvigatel dvigatelCharacteristics;
	private JButton selectDvigatel=new JButton(); 
	public DvigatelLibraryWindow(UIDvigatel uiDvigatel, JButton button) {
		setLayout(new MigLayout("center"));		
		dvigatel=uiDvigatel;
		editSaveButton=button;
		setResizable(false);
		setSize(UIConstants.LIBRARY_WIDTH, UIConstants.LIBRARY_HEIGHT);
		setTitle("Изберете двигател от библиотеката");
		setVisible(true);
		JLabel title=new JLabel("Изберете двигател от списъка");
		title.setFont(new Font("Arial",Font.BOLD, 22));
		add(title,"span,align center, wrap, gapy 20");
		
		String dvigateli[] = { "Java", "Perl", "Python", "C++", "Basic", "C#" };
		
		JList<String> dvigatelList=new JList<String>(dvigateli);
		
		dvigatelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dvigatelList.setLayoutOrientation(JList.VERTICAL);
		dvigatelList.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(dvigatelList);
		listScroller.setPreferredSize(new Dimension(700, 200));
		add(listScroller,"span,align center, wrap, gapy 20");
		
		dvigatelCharacteristics=new UIDvigatel(dvigatel.getDvigatel());
		add(dvigatelCharacteristics.getPanel(true,true),"span,align center, wrap, gapy 20");
		
		selectDvigatel.setText("Избери този двигател");
		selectDvigatel.setFont(new Font("Arial",Font.BOLD, 16));
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
		add(selectDvigatel,"span,align center, wrap, gapy 20");
	}
	
	
	
}
