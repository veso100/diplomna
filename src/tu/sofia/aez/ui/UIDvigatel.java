package tu.sofia.aez.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import tu.sofia.aez.om.Dvigatel;

public class UIDvigatel {

	private static final String EDIT_TEXT = "Редактирай";
	private static final String SAVE_TEXT = "Задай този двигател";
	public static final int INPUT_COLUMNS = 7;
	private Dvigatel dvigatel;

	private JPanel resultPanel = new JPanel();
	private JTextField pNTextField = new JTextField(INPUT_COLUMNS);
	private JTextField U1nTextField = new JTextField(INPUT_COLUMNS);
	private JTextField U2nTextField = new JTextField(INPUT_COLUMNS);
	private JTextField IoTextField = new JTextField(INPUT_COLUMNS);
	private JTextField ImNTextField = new JTextField(INPUT_COLUMNS);
	private JTextField NnTextField = new JTextField(INPUT_COLUMNS);
	private JTextField NoTextField = new JTextField(INPUT_COLUMNS);
	private JTextField X2TextField = new JTextField(INPUT_COLUMNS);
	private JTextField R1TextField = new JTextField(INPUT_COLUMNS);
	private JTextField R2TextField = new JTextField(INPUT_COLUMNS);
	private JTextField WnTextField = new JTextField(INPUT_COLUMNS);
	private JTextField WoTextField = new JTextField(INPUT_COLUMNS);
	private JTextField IpnTextField = new JTextField(INPUT_COLUMNS);

	private JLabel pNLabel = new JLabel("Номинална мощност");
	private JLabel U1nLabel = new JLabel("Номинално напрежение на статора");
	private JLabel U2nLabel = new JLabel("Номинално напрежение на ротора");
	private JLabel IoLabel = new JLabel("Фазен ток на празен ход");
	private JLabel ImNLabel = new JLabel("Номинален намагнитващ ток");
	private JLabel NnLabel = new JLabel("Номинална скорост");
	private JLabel NoLabel = new JLabel("Скорост на празен ход");
	private JLabel X2Label = new JLabel("Реактивно съпротивление на разсейване");
	private JLabel R1Label = new JLabel("Активно съпротивление на статора");
	private JLabel R2Label = new JLabel("Активно съпротивление на ротора");
	private JLabel WnLabel = new JLabel("Номинална ъглова скорост");
	private JLabel WoLabel = new JLabel("Синхронна ъглова скорост");
	private JLabel IpnLabel = new JLabel("Ипн");

	private JButton selectFromLibrary = new JButton();
	private JButton editSaveButton = new JButton();
	private JButton calculateButton;
	private SaveButtonActionListener saveAction = new SaveButtonActionListener();
	private EditButtonActionListener editAction = new EditButtonActionListener();

	public UIDvigatel(Dvigatel dvg, JButton calculateButton) {
		this.dvigatel = dvg;
		this.calculateButton = calculateButton;
	}

	public UIDvigatel(JButton calculateButton) {
		dvigatel = new Dvigatel();
		this.calculateButton = calculateButton;
	}

	public UIDvigatel(Dvigatel dvg) {
		this.dvigatel = dvg;
	}

	public JPanel getReadOnlyPanel() {
		return getPanel(false);
	}

	public JPanel getEditPanel() {
		return getPanel(true);
	}

	public JPanel getPanel(boolean editable) {
		return getPanel(editable, false);
	}

	public JPanel getPanel(boolean editable, boolean simple) {

		resultPanel.setLayout(new MigLayout("center"));

		if (!simple)
			resultPanel.setPreferredSize(new Dimension(UIConstants.PANEL_WIDTH, 220));
		else
			resultPanel.setPreferredSize(new Dimension(700, 220));

		resultPanel.setBorder(new LineBorder(Color.blue));
		if (!simple) {
			JLabel title = new JLabel("Изберете двигател: ");
			title.setFont(new Font("Arial", Font.PLAIN, 18));
			resultPanel.add(title, "span, wrap, gapx 20");
		}
		fromDvigatel();
		setEditable(editable);
		if (!simple) {
			resultPanel.add(pNLabel, "gapy 20");
			resultPanel.add(pNTextField);
			resultPanel.add(U1nLabel);
			resultPanel.add(U1nTextField);
			resultPanel.add(U2nLabel);
			resultPanel.add(U2nTextField, "wrap");

			resultPanel.add(IoLabel);
			resultPanel.add(IoTextField);
			resultPanel.add(ImNLabel);
			resultPanel.add(ImNTextField);
			resultPanel.add(NnLabel);
			resultPanel.add(NnTextField, "wrap");

			resultPanel.add(NoLabel);
			resultPanel.add(NoTextField);
			resultPanel.add(X2Label);
			resultPanel.add(X2TextField);
			resultPanel.add(R1Label);
			resultPanel.add(R1TextField, "wrap");

			resultPanel.add(R2Label);
			resultPanel.add(R2TextField);
			resultPanel.add(WnLabel);
			resultPanel.add(WnTextField);
			resultPanel.add(WoLabel);
			resultPanel.add(WoTextField, "wrap");

			resultPanel.add(IpnLabel);
			resultPanel.add(IpnTextField, "wrap");
			if (editable) {
				editSaveButton.setText(SAVE_TEXT);

				editSaveButton.addActionListener(saveAction);
			} else {
				editSaveButton.setText(EDIT_TEXT);
				editSaveButton.addActionListener(editAction);
			}
			JPanel buttonBar = new JPanel(new MigLayout());
			selectFromLibrary.setActionCommand("openLibrary");
			selectFromLibrary.addActionListener(new SelectFromLibraryActionListener());
			selectFromLibrary.setText("Изберете от библиотеката");
			selectFromLibrary.setPreferredSize(new Dimension(200, 20));
			editSaveButton.setPreferredSize(new Dimension(200, 20));
			buttonBar.add(editSaveButton);
			buttonBar.add(selectFromLibrary);
			resultPanel.add(buttonBar, "span,align center");
		}else{
			resultPanel.add(pNLabel, "gapy 20");
			resultPanel.add(pNTextField);
			resultPanel.add(U1nLabel);
			resultPanel.add(U1nTextField, "wrap");
			
			resultPanel.add(U2nLabel);
			resultPanel.add(U2nTextField);
			resultPanel.add(IoLabel);
			resultPanel.add(IoTextField, "wrap");
			
			resultPanel.add(ImNLabel);
			resultPanel.add(ImNTextField);
			resultPanel.add(NnLabel);
			resultPanel.add(NnTextField, "wrap");

			resultPanel.add(NoLabel);
			resultPanel.add(NoTextField);
			resultPanel.add(X2Label);
			resultPanel.add(X2TextField, "wrap");
			
			resultPanel.add(R1Label);
			resultPanel.add(R1TextField);
			resultPanel.add(R2Label);
			resultPanel.add(R2TextField, "wrap");
			
			resultPanel.add(WnLabel);
			resultPanel.add(WnTextField);
			resultPanel.add(WoLabel);
			resultPanel.add(WoTextField, "wrap");

			resultPanel.add(IpnLabel);
			resultPanel.add(IpnTextField, "wrap");
		}
		return resultPanel;
	}

	private void setEditable(boolean editable) {
		pNTextField.setEditable(editable);
		U1nTextField.setEditable(editable);
		U2nTextField.setEditable(editable);
		IoTextField.setEditable(editable);
		ImNTextField.setEditable(editable);
		NnTextField.setEditable(editable);
		NoTextField.setEditable(editable);
		X2TextField.setEditable(editable);
		R1TextField.setEditable(editable);
		R2TextField.setEditable(editable);
		WnTextField.setEditable(editable);
		WoTextField.setEditable(editable);
		IpnTextField.setEditable(editable);
	}

	private void fromDvigatel() {
		pNTextField.setText(dvigatel.getpN() + "");
		U1nTextField.setText(dvigatel.getU1n() + "");
		U2nTextField.setText(dvigatel.getU2n() + "");
		IoTextField.setText(dvigatel.getIo() + "");
		ImNTextField.setText(dvigatel.getImN() + "");
		NnTextField.setText(dvigatel.getNn() + "");
		NoTextField.setText(dvigatel.getNo() + "");
		X2TextField.setText(dvigatel.getX2() + "");
		R1TextField.setText(dvigatel.getR1() + "");
		R2TextField.setText(dvigatel.getR2() + "");
		WnTextField.setText(dvigatel.getWn() + "");
		WoTextField.setText(dvigatel.getWo() + "");
		IpnTextField.setText(dvigatel.getIpn() + "");
	}

	private void toDvigatel() {
		dvigatel.setpN(Double.parseDouble(pNTextField.getText()));
		dvigatel.setU1n(Double.parseDouble(U1nTextField.getText()));
		dvigatel.setU2n(Double.parseDouble(U2nTextField.getText()));
		dvigatel.setIo(Double.parseDouble(IoTextField.getText()));
		dvigatel.setImN(Double.parseDouble(ImNTextField.getText()));
		dvigatel.setNn(Double.parseDouble(NnTextField.getText()));
		dvigatel.setNo(Double.parseDouble(NoTextField.getText()));
		dvigatel.setX2(Double.parseDouble(X2TextField.getText()));
		dvigatel.setR1(Double.parseDouble(R1TextField.getText()));
		dvigatel.setR2(Double.parseDouble(R2TextField.getText()));
		dvigatel.setWn(Double.parseDouble(WnTextField.getText()));
		dvigatel.setWo(Double.parseDouble(WoTextField.getText()));
		dvigatel.setIpn(Double.parseDouble(IpnTextField.getText()));
	}

	class SaveButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setEditable(false);
			editSaveButton.setText(EDIT_TEXT);
			calculateButton.setEnabled(true);
			toDvigatel();
			editSaveButton.removeActionListener(saveAction);
			editSaveButton.addActionListener(editAction);
		}
	}

	class EditButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setEditable(true);
			editSaveButton.setText(SAVE_TEXT);
			editSaveButton.removeActionListener(editAction);
			calculateButton.setEnabled(false);
			editSaveButton.addActionListener(saveAction);
		}
	}

	class SelectFromLibraryActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			editSaveButton.setEnabled(false);
			DvigatelLibraryWindow libraryWindow = new DvigatelLibraryWindow(editSaveButton);
			libraryWindow.setVisible(true);
			libraryWindow.setAlwaysOnTop(true);
		}
	}

	public Dvigatel getDvigatel() {
		return dvigatel;
	}

	public void setDvigatel(Dvigatel dvigatel) {
		this.dvigatel = dvigatel;
	}

}
