package tu.sofia.aez.ui;

import javax.swing.JPanel;

import tu.sofia.aez.om.Dvigatel;

public class UIDvigatel {
	
	private Dvigatel dvigatel;
	public UIDvigatel(Dvigatel dvg){
		this.dvigatel=dvg;
	}
	
	public JPanel getReadOnlyPanel(){
		return null;
	}
	public JPanel getEditPanel(){
		return null;
		
	}
	
}
