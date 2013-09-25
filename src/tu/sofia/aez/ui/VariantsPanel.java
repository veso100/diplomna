package tu.sofia.aez.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import tu.sofia.aez.om.CondenzatornoSpirane;
import tu.sofia.aez.om.DS;
import tu.sofia.aez.om.DSVarianti;
import tu.sofia.aez.om.RDSPOT;
import tu.sofia.aez.om.RDSPOTVarianti;
import tu.sofia.aez.om.RDSS;
import net.miginfocom.swing.MigLayout;

public class VariantsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8804859679762193880L;
	private Variants variant;

	private static final String DSvariant1Radio = "DS Variant 1";
	private static final String DSvariant2Radio = "DS Variant 2";
	private static final String DSvariant3Radio = "DS Variant 3";
	private static final String DSvariant4Radio = "DS Variant 4";
	private static final String DSvariant5Radio = "DS Variant 5";

	private JRadioButton dSvariant1Button = new JRadioButton();
	private JRadioButton dSvariant2Button = new JRadioButton();
	private JRadioButton dSvariant3Button = new JRadioButton();
	private JRadioButton dSvariant4Button = new JRadioButton();
	private JRadioButton dSvariant5Button = new JRadioButton();

	private JRadioButton rdspotVariant1Button = new JRadioButton();
	private JRadioButton rdspotVariant2Button = new JRadioButton();
	private JRadioButton rdspotVariant3Button = new JRadioButton();

	private static final String RDSPOTVariant1Radio = "RDSPOT Variant 1";
	private static final String RDSPOTVariant2Radio = "RDSPOT Variant 2";
	private static final String RDSPOTVariant3Radio = "RDSPOT Variant 3";

	private ButtonGroup rdspotButtonGroup = new ButtonGroup();

	private ButtonGroup dSbuttonGroup = new ButtonGroup();

	private DsVariantsChangedListener dsVariantsChangedListener = new DsVariantsChangedListener();
	private RdspotVariantsChangedListener rdspotVariantsChangedListener = new RdspotVariantsChangedListener();
	public VariantsPanel(Variants variant) {
		super();
		this.variant = variant;
		refreshPanel();
	}

	public VariantsPanel() {
		super();
		this.variant = Variants.DS;
		refreshPanel();
	}

	public void refreshPanel() {
		removeAll();
		updateUI();
		setLayout(new MigLayout("center"));

		if (Variants.DS.equals(variant) || Variants.RDSS.equals(variant)) {

			add(new JLabel(new ImageIcon(DSVarianti.VARIANT1.getResource())));
			add(new JLabel(new ImageIcon(DSVarianti.VARIANT2.getResource())));
			add(new JLabel(new ImageIcon(DSVarianti.VARIANT3.getResource())));
			add(new JLabel(new ImageIcon(DSVarianti.VARIANT4.getResource())));
			add(new JLabel(new ImageIcon(DSVarianti.VARIANT5.getResource())), "wrap");

			dSvariant1Button.addActionListener(dsVariantsChangedListener);
			dSvariant1Button.setActionCommand(DSvariant1Radio);

			dSvariant2Button.addActionListener(dsVariantsChangedListener);
			dSvariant2Button.setActionCommand(DSvariant2Radio);

			dSvariant3Button.addActionListener(dsVariantsChangedListener);
			dSvariant3Button.setActionCommand(DSvariant3Radio);

			dSvariant4Button.addActionListener(dsVariantsChangedListener);
			dSvariant4Button.setActionCommand(DSvariant4Radio);

			dSvariant5Button.addActionListener(dsVariantsChangedListener);
			dSvariant5Button.setActionCommand(DSvariant5Radio);

			dSvariant1Button.setSelected(true);
			dSbuttonGroup.add(dSvariant1Button);
			dSbuttonGroup.add(dSvariant2Button);
			dSbuttonGroup.add(dSvariant3Button);
			dSbuttonGroup.add(dSvariant4Button);
			dSbuttonGroup.add(dSvariant5Button);

			add(dSvariant1Button, "align center");
			add(dSvariant2Button, "align center");
			add(dSvariant3Button, "align center");
			add(dSvariant4Button, "align center");
			add(dSvariant5Button, "align center");

		}

		if (Variants.RDSPOT.equals(variant)) {
			add(new JLabel(new ImageIcon(RDSPOTVarianti.VARIANT1.getResource())));
			add(new JLabel(new ImageIcon(RDSPOTVarianti.VARIANT2.getResource())));
			add(new JLabel(new ImageIcon(RDSPOTVarianti.VARIANT3.getResource())), "wrap");

			rdspotVariant1Button.addActionListener(rdspotVariantsChangedListener);
			rdspotVariant1Button.setActionCommand(RDSPOTVariant1Radio);

			rdspotVariant2Button.addActionListener(rdspotVariantsChangedListener);
			rdspotVariant2Button.setActionCommand(RDSPOTVariant2Radio);

			rdspotVariant3Button.addActionListener(rdspotVariantsChangedListener);
			rdspotVariant3Button.setActionCommand(RDSPOTVariant3Radio);
			
			rdspotVariant1Button.setSelected(true);
			
			rdspotButtonGroup.add(rdspotVariant1Button);
			rdspotButtonGroup.add(rdspotVariant2Button);
			rdspotButtonGroup.add(rdspotVariant3Button);
			
			add(rdspotVariant1Button, "align center");
			add(rdspotVariant2Button, "align center");
			add(rdspotVariant3Button, "align center");
			

		}

	}

	public class RdspotVariantsChangedListener implements ActionListener {
		private RDSPOTVarianti rdspotVariant = RDSPOTVarianti.VARIANT1;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == RDSPOTVariant1Radio) {
				if (!RDSPOTVarianti.VARIANT1.equals(rdspotVariant)) {
					rdspotVariant = RDSPOTVarianti.VARIANT1;
				}
			} else if (e.getActionCommand() == RDSPOTVariant2Radio) {
				if (!RDSPOTVarianti.VARIANT2.equals(rdspotVariant)) {
					rdspotVariant = RDSPOTVarianti.VARIANT2;
				}
			} else {
				if (!RDSPOTVarianti.VARIANT3.equals(rdspotVariant)) {
					rdspotVariant = RDSPOTVarianti.VARIANT3;
				}
			}
			System.out.println("VARIANT: " + rdspotVariant);

		}

	}

	public class DsVariantsChangedListener implements ActionListener {
		private DSVarianti dsVariant = DSVarianti.VARIANT1;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == DSvariant1Radio) {
				if (!DSVarianti.VARIANT1.equals(dsVariant)) {
					dsVariant = DSVarianti.VARIANT1;
				}
			} else if (e.getActionCommand() == DSvariant2Radio) {
				if (!DSVarianti.VARIANT2.equals(dsVariant)) {
					dsVariant = DSVarianti.VARIANT2;
				}
			} else if (e.getActionCommand() == DSvariant3Radio) {
				if (!DSVarianti.VARIANT3.equals(dsVariant)) {
					dsVariant = DSVarianti.VARIANT3;
				}
			} else if (e.getActionCommand() == DSvariant4Radio) {
				if (!DSVarianti.VARIANT4.equals(dsVariant)) {
					dsVariant = DSVarianti.VARIANT4;
				}
			} else {
				if (!DSVarianti.VARIANT5.equals(dsVariant)) {
					dsVariant = DSVarianti.VARIANT5;
				}
			}
			System.out.println("VARIANT: " + dsVariant);

		}

	}

	public Variants getVariant() {
		return variant;
	}

	public void setVariant(Variants variant) {
		this.variant = variant;
	}

	public enum Variants {
		DS, RDSPOT, RDSS, CONDENZATORNO
	}
}
