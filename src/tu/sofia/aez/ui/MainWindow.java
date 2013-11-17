package tu.sofia.aez.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import tu.sofia.aez.om.DS;
import tu.sofia.aez.om.RDSPOT;
import tu.sofia.aez.om.RDSS;
import tu.sofia.aez.om.RejimEnum;
import tu.sofia.aez.om.RejimNaSpirane;

public class MainWindow {

	private JFrame frame = new JFrame("ТУ София - динамично спиране на асинхронен двигател");
	private JPanel rejimPanel = new JPanel();
	private VariantsPanel variantsPanel = new VariantsPanel();
	private JLabel title = new JLabel("Технически унивеситет - София");
	private JLabel kTitle = new JLabel("Катедра автоматизация на електрозадвижванията");
	private JLabel labelIzborRejim = new JLabel("Изберете режим: ");
	private JLabel subTitle = new JLabel("Изследване на режимите на динамично спиране на асинхронен двигател");
	private JButton calculateButton = new JButton();
	private RejimNaSpirane rejimNaSpirane=new DS();
	public static final String DS_RADIO = "DS";
	public static final String RDSS_RADIO = "RDSS";
	public static final String RDSPOT_RADIO = "RDSPOT";
	private ButtonGroup rejimButtonGroup = new ButtonGroup();
	private JRadioButton dsRadio = new JRadioButton("ДС");
	private JRadioButton rdssRadio = new JRadioButton("РДСС");
	private JRadioButton rdspotRadio = new JRadioButton("РДСПОТ");
	private UIDvigatel dvigatelHolder = new UIDvigatel(calculateButton);
	private RejimChangedListener rejimChangedListener = new RejimChangedListener();

	public MainWindow() {
		/*
		 * 
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * panel.setLayout(new MigLayout("center")); panel.add(title, "wrap");
		 * DataSetService dataSetService = new DataSetService(); panel.add(new
		 * ChartingService
		 * ().prepareChartPanel(dataSetService.createSampleDataset()), "wrap");
		 * panel.add(new UIDvigatel().getReadOnlyPanel(),"wrap");
		 */

		// FRAME PROPERTIES
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new MigLayout("center"));
		rejimPanel.setLayout(new MigLayout("center"));
		frame.setResizable(false);
		frame.setSize(UIConstants.WINDOW_WIDTH, UIConstants.WINDOW_HEIGHT);
		frame.setVisible(true);

		// RADIO BUTONI ZA IZBOR NA REJIM
		dsRadio.setActionCommand(DS_RADIO);
		rdssRadio.setActionCommand(RDSS_RADIO);
		rdspotRadio.setActionCommand(RDSPOT_RADIO);

		rejimButtonGroup.add(dsRadio);
		rejimButtonGroup.add(rdssRadio);
		rejimButtonGroup.add(rdspotRadio);
		dsRadio.addActionListener(rejimChangedListener);
		dsRadio.setSelected(true);
		rdssRadio.addActionListener(rejimChangedListener);
		rdspotRadio.addActionListener(rejimChangedListener);

		rejimPanel.setPreferredSize(new Dimension(UIConstants.PANEL_WIDTH, 30));
		rejimPanel.setBorder(new LineBorder(Color.blue));

		labelIzborRejim.setFont(new Font("Arial", Font.PLAIN, 18));
		rejimPanel.add(labelIzborRejim, "pos 30 10");
		rejimPanel.add(dsRadio);
		rejimPanel.add(rdssRadio);
		rejimPanel.add(rdspotRadio);

		// Glaven prozorec - zaglavia
		title.setFont(new Font("Arial", Font.BOLD, 28));
		title.setForeground(Color.blue);
		frame.add(title, "span,align center, wrap");
		
		kTitle.setFont(new Font("Arial", Font.BOLD, 24));
		frame.add(kTitle, "span,align center, wrap,gapy 10");
		
		subTitle.setFont(new Font("Arial", Font.PLAIN, 24));

		frame.add(subTitle, "span,align center, wrap,gapy 10");
		frame.add(rejimPanel, "span,align center, wrap, gapy 30");
		// VARIANTI ZA REJIMA
		frame.add(variantsPanel, "span,align center, wrap, gapy 20");
		
		frame.add(dvigatelHolder.getPanel(true), "span,align center, wrap, gapy 20");
		dvigatelHolder.setRejim(RejimEnum.DS);
		calculateButton.setText("Пресметни резултатите");
		calculateButton.setFont(new Font("Arial", Font.BOLD, 18));
		calculateButton.setPreferredSize(new Dimension(300, 30));
		calculateButton.setEnabled(false);
		calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dvigatelHolder.toDvigatel();
				new ResultsWindow(MainWindow.this.dvigatelHolder,
						MainWindow.this.rejimNaSpirane, MainWindow.this.variantsPanel);
			}
		});
		frame.add(calculateButton, "span,align center, wrap, gapy 20");

	}

	public class RejimChangedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == DS_RADIO) {
				if (rejimNaSpirane == null || !(rejimNaSpirane instanceof DS)) {
					switchToDSRejim();
				}
			} else if (e.getActionCommand() == RDSS_RADIO) {
				if (rejimNaSpirane == null || !(rejimNaSpirane instanceof RDSS)) {
					switchToRDSSRejim();
				}
			} else {
				if (rejimNaSpirane == null || !(rejimNaSpirane instanceof RDSPOT)) {
					switchToRDSPOTRejim();
				}
			}

		}

	}


	private void switchToDSRejim() {
		rejimNaSpirane = new DS();
		dvigatelHolder.setRejim(RejimEnum.DS);
	}

	private void switchToRDSSRejim() {
		rejimNaSpirane = new RDSS();
		dvigatelHolder.setRejim(RejimEnum.RDSS);
	}

	private void switchToRDSPOTRejim() {
		rejimNaSpirane = new RDSPOT();
		dvigatelHolder.setRejim(RejimEnum.RDSPOT);
	}

	public Runnable getRunnable() {
		return new Runnable() {
			public void run() {
				draw();
			}
		};
	}

	public void draw() {
		frame.setVisible(true);
	}

}
