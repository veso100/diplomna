package tu.sofia.aez.ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import tu.sofia.aez.om.Dvigatel;

public class DvigatelLibraryWindow extends JFrame implements Runnable {

	private static final long serialVersionUID = -5591244852799784837L;
	
	private JButton editSaveButton;
	private JButton selectDvigatel=new JButton(); 
	public DvigatelLibraryWindow(JButton button) {
		setLayout(new MigLayout("center"));		
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
		
		UIDvigatel dvigatelCharacteristics=new UIDvigatel(new Dvigatel());
		add(dvigatelCharacteristics.getPanel(true,true),"span,align center, wrap, gapy 20");
		
		selectDvigatel.setText("Избери този двигател");
		selectDvigatel.setFont(new Font("Arial",Font.BOLD, 16));
		
		add(selectDvigatel,"span,align center, wrap, gapy 20");
	}
	@Override
	public void run() {
		setVisible(true);
	}
	
	
	
}
