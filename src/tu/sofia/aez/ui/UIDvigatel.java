package tu.sofia.aez.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import tu.sofia.aez.om.Dvigatel;
import tu.sofia.aez.om.RejimEnum;
import tu.sofia.aez.om.Veriga;
import tu.sofia.aez.util.ExcelService;

public class UIDvigatel {

	private static final String EDIT_TEXT = "Редактирай";
	private static final String SAVE_TEXT = "Използвай тези стойности";
	public static final int INPUT_COLUMNS = 7;
	private Dvigatel dvigatel;
	private Veriga veriga = new Veriga();

	private JPanel resultPanel = new JPanel();
	// Dvigatel
	private JTextField pNTextField = new JTextField(INPUT_COLUMNS);
	private JTextField U1nTextField = new JTextField(INPUT_COLUMNS);
	private JTextField U2nTextField = new JTextField(INPUT_COLUMNS);
	private JTextField IoTextField = new JTextField(INPUT_COLUMNS);
	private JTextField NnTextField = new JTextField(INPUT_COLUMNS);
	private JTextField NoTextField = new JTextField(INPUT_COLUMNS);
	private JTextField X2TextField = new JTextField(INPUT_COLUMNS);
	private JTextField R1TextField = new JTextField(INPUT_COLUMNS);
	private JTextField R2TextField = new JTextField(INPUT_COLUMNS);
	private JTextField nameTextField = new JTextField(INPUT_COLUMNS);

	private JLabel pNLabel = new JLabel("Номинална мощност (Pn)");
	private JLabel U1nLabel = new JLabel("Номинално напрежение на статора (U1n)");
	private JLabel U2nLabel = new JLabel("Номинално напрежение на ротора (U2n)");
	private JLabel IoLabel = new JLabel("Фазен ток на празен ход (Io)");
	private JLabel NnLabel = new JLabel("Номинална скорост (Nn)");
	private JLabel NoLabel = new JLabel("Скорост на празен ход (No)");
	private JLabel X2Label = new JLabel("Реактивно съпротивление на разсейване (X2)");
	private JLabel R1Label = new JLabel("Активно съпротивление на статора (r1)");
	private JLabel R2Label = new JLabel("Активно съпротивление на ротора (R2)");

	private JLabel nameLabel = new JLabel("Име");

	// Veriga
	private JTextField IpnTextField = new JTextField(INPUT_COLUMNS);
	private JTextField RshTextField = new JTextField(INPUT_COLUMNS);
	private JTextField RdTextField = new JTextField(INPUT_COLUMNS);
	private JTextField ImiuMinTextField = new JTextField(INPUT_COLUMNS);
	private JTextField ImiuMaxTextField = new JTextField(INPUT_COLUMNS);

	private JLabel IpnLabel = new JLabel("Iпн");
	private JLabel RshLabel = new JLabel("Rш/r2");
	private JLabel RdLabel = new JLabel("Rд/r2");
	private JLabel ImiuMinLabel = new JLabel("Iμ min");
	private JLabel ImiuMaxLabel = new JLabel("Iμ max");
	private RejimEnum rejim;

	private JButton selectFromLibrary = new JButton();
	private JButton addToLibrary = new JButton();
	private JButton editSaveButton = new JButton();
	private JButton calculateButton;
	private SaveButtonActionListener saveAction = new SaveButtonActionListener();
	private EditButtonActionListener editAction = new EditButtonActionListener();

	public UIDvigatel(Dvigatel dvg, Veriga veriga, JButton calculateButton) {
		this.dvigatel = dvg;
		this.calculateButton = calculateButton;
		this.veriga = veriga;
	}

	public UIDvigatel(JButton calculateButton) {
		dvigatel = new Dvigatel();
		this.calculateButton = calculateButton;
	}

	public UIDvigatel(Dvigatel dvg, Veriga veriga) {
		this.dvigatel = dvg;
		this.veriga = veriga;
	}

	public Veriga getVeriga() {
		return veriga;
	}

	public void setVeriga(Veriga veriga) {
		this.veriga = veriga;
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

	public void setRejim(RejimEnum rej) {

		editSaveButton.setText(SAVE_TEXT);
		editSaveButton.removeActionListener(editAction);
		editSaveButton.removeActionListener(saveAction);
		calculateButton.setEnabled(false);
		editSaveButton.addActionListener(saveAction);
		rejim = rej;
		/*
		 * if (RejimEnum.DS.equals(rej)) { ImiuMaxTextField.setEditable(false);
		 * ImiuMaxTextField.setText(""); ImiuMinTextField.setEditable(false);
		 * ImiuMinTextField.setText(""); rejim = RejimEnum.DS; } else if
		 * (RejimEnum.RDSS.equals(rej)) { ImiuMaxTextField.setEditable(true);
		 * ImiuMaxTextField.setText("" + 0d);
		 * ImiuMinTextField.setEditable(false); ImiuMinTextField.setText("");
		 * rejim = RejimEnum.RDSS; } else { ImiuMaxTextField.setEditable(true);
		 * ImiuMaxTextField.setText("" + 0d);
		 * ImiuMinTextField.setEditable(true); ImiuMinTextField.setText("" +
		 * 0d); rejim = RejimEnum.RDSPOT; }
		 */
		setEditable(true);
	}

	public JPanel getPanel(boolean editable, boolean simple) {

		resultPanel.setLayout(new MigLayout("center"));
		addTextChangeListeners();
		if (!simple)
			resultPanel.setPreferredSize(new Dimension(UIConstants.PANEL_WIDTH, 220));
		else
			resultPanel.setPreferredSize(new Dimension(700, 220));

		resultPanel.setBorder(new LineBorder(Color.blue));
		if (!simple) {
			JLabel title = new JLabel("Изберете двигател и параметри: ");
			title.setFont(new Font("Arial", Font.PLAIN, 18));
			resultPanel.add(title, "span, wrap");
		}
		fromDvigatel();
		setEditable(editable);
		if (!simple) {
			resultPanel.add(nameLabel, "gapy 20");
			resultPanel.add(nameTextField);
			resultPanel.add(pNLabel);
			resultPanel.add(pNTextField);
			resultPanel.add(IpnLabel);
			resultPanel.add(IpnTextField, "wrap");

			resultPanel.add(U1nLabel);
			resultPanel.add(U1nTextField);
			resultPanel.add(U2nLabel);
			resultPanel.add(U2nTextField);
			resultPanel.add(RshLabel);
			resultPanel.add(RshTextField, "wrap");

			resultPanel.add(NnLabel);
			resultPanel.add(NnTextField);
			resultPanel.add(NoLabel);
			resultPanel.add(NoTextField);
			resultPanel.add(RdLabel);
			resultPanel.add(RdTextField, "wrap");

			resultPanel.add(IoLabel);
			resultPanel.add(IoTextField);
			resultPanel.add(X2Label);
			resultPanel.add(X2TextField);
			resultPanel.add(ImiuMinLabel);
			resultPanel.add(ImiuMinTextField, "wrap");

			resultPanel.add(R1Label);
			resultPanel.add(R1TextField);

			resultPanel.add(R2Label);
			resultPanel.add(R2TextField);
			resultPanel.add(ImiuMaxLabel);
			resultPanel.add(ImiuMaxTextField, "wrap");

			if (editable) {
				editSaveButton.setText(SAVE_TEXT);

				editSaveButton.addActionListener(saveAction);
			} else {
				editSaveButton.setText(EDIT_TEXT);
				editSaveButton.addActionListener(editAction);
			}
			JPanel buttonBar = new JPanel(new MigLayout());
			addToLibrary.setActionCommand("addToLibrary");
			addToLibrary.addActionListener(new AddToLibraryActionListener());
			addToLibrary.setText("Добави към библиотеката");
			addToLibrary.setPreferredSize(new Dimension(200, 20));

			selectFromLibrary.setActionCommand("openLibrary");
			selectFromLibrary.addActionListener(new SelectFromLibraryActionListener());
			selectFromLibrary.setText("Изберете от библиотеката");
			selectFromLibrary.setPreferredSize(new Dimension(200, 20));
			editSaveButton.setPreferredSize(new Dimension(200, 20));
			buttonBar.add(editSaveButton);
			buttonBar.add(selectFromLibrary);
			buttonBar.add(addToLibrary);
			resultPanel.add(buttonBar, "span,align center");
		} else {

			resultPanel.add(pNLabel, "gapy 20");
			resultPanel.add(pNTextField);
			resultPanel.add(nameLabel);
			resultPanel.add(nameTextField, "wrap");
			resultPanel.add(U1nLabel);
			resultPanel.add(U1nTextField);

			resultPanel.add(U2nLabel);
			resultPanel.add(U2nTextField, "wrap");

			resultPanel.add(IoLabel);
			resultPanel.add(IoTextField);

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

		}
		return resultPanel;
	}

	private void setEditable(boolean editable) {

		// Dvigatel
		pNTextField.setEditable(editable);
		U1nTextField.setEditable(editable);
		U2nTextField.setEditable(editable);
		IoTextField.setEditable(editable);
		NnTextField.setEditable(editable);
		NoTextField.setEditable(editable);
		X2TextField.setEditable(editable);
		R1TextField.setEditable(editable);
		R2TextField.setEditable(editable);
		nameTextField.setEditable(editable);
		// Veriga
		IpnTextField.setEditable(editable);
		RshTextField.setEditable(editable);
		RdTextField.setEditable(editable);
		if (editable) {
			System.out.println("REJIM 3:" + rejim + " : " + this);
			if (RejimEnum.DS.equals(rejim)) {
				ImiuMaxTextField.setEditable(false);
				ImiuMaxTextField.setText("");
				ImiuMinTextField.setEditable(false);
				ImiuMinTextField.setText("");
				rejim = RejimEnum.DS;
			} else if (RejimEnum.RDSS.equals(rejim)) {
				ImiuMaxTextField.setEditable(true);
				ImiuMaxTextField.setText("" + 0d);
				ImiuMinTextField.setEditable(false);
				ImiuMinTextField.setText("");
				rejim = RejimEnum.RDSS;
			} else {
				ImiuMaxTextField.setEditable(true);
				ImiuMaxTextField.setText("" + 0d);
				ImiuMinTextField.setEditable(true);
				ImiuMinTextField.setText("" + 0d);
				rejim = RejimEnum.RDSPOT;
			}
		}else{
			ImiuMaxTextField.setEditable(false);
			ImiuMinTextField.setEditable(false);
		}
		
		System.out.println("REJIM 2:" + rejim + " : " + this);
	}

	public void fromDvigatel() {
		fromDvigatel(false);
	}

	public void fromDvigatel(boolean onlyDvigatel) {
		pNTextField.setText(dvigatel.getpN() + "");
		U1nTextField.setText(dvigatel.getU1n() + "");
		U2nTextField.setText(dvigatel.getU2n() + "");
		IoTextField.setText(dvigatel.getIo() + "");
		NnTextField.setText(dvigatel.getNn() + "");
		NoTextField.setText(dvigatel.getNo() + "");
		X2TextField.setText(dvigatel.getX2() + "");
		R1TextField.setText(dvigatel.getR1() + "");
		R2TextField.setText(dvigatel.getR2() + "");
		nameTextField.setText(dvigatel.getName() + "");

		if (!onlyDvigatel) {
			IpnTextField.setText(veriga.getIpN() + "");
			RshTextField.setText(veriga.getRsh() + "");
			RdTextField.setText(veriga.getRd() + "");
			ImiuMaxTextField.setText(veriga.getImiuMax() + "");
			ImiuMinTextField.setText(veriga.getImiuMin() + "");
		}
	}

	public void addTextChangeListeners() {

		DocumentListener docListener = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				UIDvigatel.this.addToLibrary.setEnabled(true);

			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				UIDvigatel.this.addToLibrary.setEnabled(true);

			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				UIDvigatel.this.addToLibrary.setEnabled(true);

			}
		};
		pNTextField.getDocument().addDocumentListener(docListener);
		U1nTextField.getDocument().addDocumentListener(docListener);
		U2nTextField.getDocument().addDocumentListener(docListener);
		IoTextField.getDocument().addDocumentListener(docListener);
		NnTextField.getDocument().addDocumentListener(docListener);
		NoTextField.getDocument().addDocumentListener(docListener);
		X2TextField.getDocument().addDocumentListener(docListener);
		R1TextField.getDocument().addDocumentListener(docListener);
		R2TextField.getDocument().addDocumentListener(docListener);
		nameTextField.getDocument().addDocumentListener(docListener);

	}

	public void toDvigatel() {
		dvigatel.setpN(Double.parseDouble(pNTextField.getText()));
		dvigatel.setU1n(Double.parseDouble(U1nTextField.getText()));
		dvigatel.setU2n(Double.parseDouble(U2nTextField.getText()));
		dvigatel.setIo(Double.parseDouble(IoTextField.getText()));
		dvigatel.setNn(Double.parseDouble(NnTextField.getText()));
		dvigatel.setNo(Double.parseDouble(NoTextField.getText()));
		dvigatel.setX2(Double.parseDouble(X2TextField.getText()));
		dvigatel.setR1(Double.parseDouble(R1TextField.getText()));
		dvigatel.setR2(Double.parseDouble(R2TextField.getText()));
		dvigatel.setName(nameTextField.getText());

		veriga.setIpN(Double.parseDouble(IpnTextField.getText()));
		veriga.setRsh(Double.parseDouble(RshTextField.getText()));
		veriga.setRd(Double.parseDouble(RdTextField.getText()));
		if (!RejimEnum.DS.equals(rejim)) {
			veriga.setImiuMax(Double.parseDouble(ImiuMaxTextField.getText()));
			if (RejimEnum.RDSPOT.equals(rejim))
				veriga.setImiuMin(Double.parseDouble(ImiuMinTextField.getText()));
		}

	}

	class SaveButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("SAVE");
			if (!UIDvigatel.this.validateInputFields()) {
				setEditable(false);
				editSaveButton.setText(EDIT_TEXT);
				calculateButton.setEnabled(true);
				toDvigatel();
				editSaveButton.removeActionListener(saveAction);
				editSaveButton.addActionListener(editAction);
			}
		}

	}

	private boolean isNotValidDouble(String input) {
		try {
			double result = Double.parseDouble(input);
			if (result < 0)
				return true;
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	private boolean foundErrorsInField(JTextField input) {
		if (isNotValidDouble(input.getText())) {
			Border border = BorderFactory.createLineBorder(Color.red);
			input.setBorder(border);
			return true;
		} else {
			input.setBorder(UIManager.getBorder("TextField.border"));
			return false;
		}
	}

	private boolean validateInputFields() {
		boolean foundErrors = false;

		foundErrors = foundErrorsInField(pNTextField) || foundErrors;
		foundErrors = foundErrorsInField(U1nTextField) || foundErrors;
		foundErrors = foundErrorsInField(U2nTextField) || foundErrors;
		foundErrors = foundErrorsInField(IoTextField) || foundErrors;
		foundErrors = foundErrorsInField(NnTextField) || foundErrors;
		foundErrors = foundErrorsInField(NoTextField) || foundErrors;
		foundErrors = foundErrorsInField(X2TextField) || foundErrors;
		foundErrors = foundErrorsInField(R1TextField) || foundErrors;
		foundErrors = foundErrorsInField(R2TextField) || foundErrors;
		foundErrors = foundErrorsInField(IpnTextField) || foundErrors;

		foundErrors = foundErrorsInField(RshTextField) || foundErrors;
		foundErrors = foundErrorsInField(RdTextField) || foundErrors;
		if (RejimEnum.RDSPOT.equals(rejim))
			foundErrors = foundErrorsInField(ImiuMinTextField) || foundErrors;
		if (!RejimEnum.DS.equals(rejim))
			foundErrors = foundErrorsInField(ImiuMaxTextField) || foundErrors;

		return foundErrors;
	}

	class EditButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("EDIT");
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
			DvigatelLibraryWindow libraryWindow = new DvigatelLibraryWindow(UIDvigatel.this);
			libraryWindow.setVisible(true);
			libraryWindow.setAlwaysOnTop(false);
		}
	}

	class AddToLibraryActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (!validateInputFields()) {
					if (nameTextField.getText().isEmpty()) {
						Border border = BorderFactory.createLineBorder(Color.red);
						nameTextField.setBorder(border);
					} else {
						nameTextField.setBorder(UIManager.getBorder("TextField.border"));
						UIDvigatel.this.toDvigatel();
						UIDvigatel.this.addToLibrary.setEnabled(false);
						new ExcelService().addDvigatel(UIDvigatel.this.dvigatel);
					}

				}
			} catch (InvalidFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public Dvigatel getDvigatel() {
		return dvigatel;
	}

	public void setDvigatel(Dvigatel dvigatel) {
		this.dvigatel = dvigatel;
	}

	public void refreshUI() {
		resultPanel.updateUI();
	}

}
