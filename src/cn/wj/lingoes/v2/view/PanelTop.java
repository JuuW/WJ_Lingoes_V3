package cn.wj.lingoes.v2.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import cn.wj.lingoes.v2.MainFrameV3;
import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public class PanelTop extends LingoesPanel {
	public static String key = "PanelTop";

	public PanelTop(ContainerList conList) {
		super(conList);
	}

	// public JButton btnBackward;
//	public JButton btnForward;

	public JComboBox<String> cbbWord;
	// private DefaultComboBoxModel<WordAndIndex> cbbModel;
	public JButton btnSearch;

	// public JComboBox<String> cbbSearchEngine;
	// public JButton btnWebSearch;

	public JTextField tfWord;

	@Override
	protected void initView() {
		this.setBackground(new Color(255, 255, 240));
		// btnBackward = new JButton("<");
		// btnForward = new JButton(">");
		cbbWord = new JComboBox<String>();
		// cbbModel = new DefaultComboBoxModel<>();
		// cbbWord.setModel(cbbModel);

		cbbWord.setEditable(true);
//		btnWebSearch = new JButton("SearchOnline");
//		cbbSearchEngine = new JComboBox<>();
//
//		cbbSearchEngine.addItem("有道词典");
//		cbbSearchEngine.addItem("金山词霸");
//		cbbSearchEngine.addItem("中文维基百科");
//		cbbSearchEngine.addItem("百度");

		tfWord = (JTextField) cbbWord.getEditor().getEditorComponent();
		tfWord.setBackground(new Color(255, 255, 200));
		Font font = new Font("Arial", Font.BOLD, 40);
		// tfWord.setFont(font);
		tfWord.setHorizontalAlignment(JTextField.CENTER);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		btnSearch = new JButton("");

		btnSearch.setIcon(new ImageIcon(getClass().getResource("/resource/img/lingoes_64x64.png"))); // NOI18N
		btnSearch.setBorder(null);
		btnSearch.setContentAreaFilled(false);
		add(btnSearch);

		cbbWord.setFont(font);

		cbbWord.setModel(new DefaultComboBoxModel<>());
		add(cbbWord);

	}

	@Override
	protected void register() {

		conList.addContainer(PanelTop.key, this);
	}

	@Override
	protected void initEvent() {

//		btnWebSearch.addActionListener(new java.awt.event.ActionListener() {
//			@Override
//			public void actionPerformed(java.awt.event.ActionEvent evt) {
//				PanelCenter pc = (PanelCenter) conList.getContainer(PanelCenter.key);
//				PanelTop pt = (PanelTop) conList.getContainer(PanelTop.key);
//				String engine = (String) cbbSearchEngine.getSelectedItem();
//				String word = tfWord.getText().trim().toLowerCase();
//				try {
//					pc.displayOnline(word, engine);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		});

		btnSearch.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				MainFrameV3 mf = (MainFrameV3) conList.getContainer(MainFrameV3.key);
				PanelTop pt = (PanelTop) conList.getContainer(PanelTop.key);

				pt.addItemindroplist(mf.LastWord);
				pt.tfWord.setText(mf.LastWord);
				doWordSearch(mf.LastWord);
				pt.cbbWord.setSelectedItem(mf.LastWord);
			}
		});

		tfWord.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// btnStartActionPerformed();
				// setBounds();
			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String word = tfWord.getText().trim().toLowerCase();
					doWordSearch(word);
				}
			}

		});

		cbbWord.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					tfWord.setText(cbbWord.getSelectedItem().toString());
					String word = tfWord.getText().trim().toLowerCase();
					doWordSearch(word);
				}
			}

		});
	}

	public void doWordSearch(String word) {
		PanelLeftCenterList pcList = (PanelLeftCenterList) conList.getContainer(PanelLeftCenterList.key);
		PanelLeftLeft pcleft = (PanelLeftLeft) conList.getContainer(PanelLeftLeft.key);
		PanelLeftCenter pcCenter = (PanelLeftCenter) conList.getContainer(PanelLeftCenter.key);
		if (pcList.isVisible() == false) {
			pcleft.btnList.doClick();
			pcCenter.revalidate();
		}

		PanelCenter pc = (PanelCenter) conList.getContainer(PanelCenter.key);
		try {
			pc.display(word);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public void addItemindroplist(String word) {
		// WordAndIndex wai = new WordAndIndex(word, -1);
		// int indexOfTypingText = cbbModel.getIndexOf(wai);
		// if (indexOfTypingText == -1) {
		// wai.setIndex(cbbModel.getSize()+1);

		boolean isExist = false;
		int size = cbbWord.getModel().getSize();
		for (int i = 0; i < size; i++) {
			if (cbbWord.getModel().getElementAt(i).toString().equals(word)) {
				isExist = true;
				break;
			}
		}
		if (!isExist) {
			cbbWord.addItem(word);
		}
		// } else {
		// wai = cbbModel.getElementAt(indexOfTypingText);
		// cbbModel.removeElementAt(indexOfTypingText);
		// cbbModel.addElement(wai);
		// }
	}

}
