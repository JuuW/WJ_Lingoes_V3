/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.wj.lingoes.v2.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import cn.wj.lingoes.v2.utility.ContainerList;

/**
 *
 * @author USER
 */
@SuppressWarnings("serial")
public class PanelLeft extends LingoesPanel {

	
	protected static String key = "PanelLeft";

	public PanelLeft(ContainerList conList) {
		super(conList);
	}

//    private JSplitPane splitPane;
//    
//    private final Border defaultBorder = new JButton().getBorder();
//    private final Border pnLeftButtonHighLightBorder = BorderFactory
//            .createCompoundBorder(new LineBorder(new Color(153,153,153), 1), new LineBorder(Color.WHITE, 5));
//    private final String homepageKey = getClass().getResource("/pictures/icon-homepage-16px.png").toString();
//    private DefaultComboBoxModel<WordAndIndex> cbbModelOfMainForm;
//    private DictionaryEnum dicEnum;
//    private LanguageAppEnum languageApp;

	private JPanel pnlLeftCenter;
	public JPanel getPnlCenter() {
		return pnlLeftCenter;
	}

	public void setPnlCenter(JPanel pnlCenter) {
		this.pnlLeftCenter = pnlCenter;
	}

	public JPanel getPnlLeft() {
		return pnlLeftleft;
	}

	public void setPnlLeft(JPanel pnlLeft) {
		this.pnlLeftleft = pnlLeft;
	}

	private JPanel pnlLeftleft;

//	public PanelLeft() {
//		// this.dicEnum = dicEnum;
//		// this.splitPane = splitPane;
//		// this.languageApp = languageApp;
//		// this.splitPane.setEnabled(false);
//		// this.cbbModelOfMainForm = cbbModel;
//
//		initView();
////		initComponentManuallys();
////		initEvents();
//	}

//	private void initComponentManuallys() {
//        pnLeftCenter.setLayout(cardLayout);
//        
//        homepagePanel = new HomepagePanel(dicEnum, splitPane, languageApp);
//        settingPanel = new SettingPanel(this, languageApp);
//        addendumPanel = new AddendumPanel(splitPane, languageApp);
//        
//        btHomepage.setBorder(pnLeftButtonHighLightBorder);
//        
//        pnLeftCenter.add(homepagePanel, btHomepage.getIcon().toString());
//        pnLeftCenter.add(settingPanel, btSetting.getIcon().toString());
//        pnLeftCenter.add(addendumPanel, btAddendum.getIcon().toString());
//	}

//	private void initEvents() {
//		// pnLeftLeftButtonsEvents();
//	}

//    private void pnLeftLeftButtonsEvents() {
//        final Component[] components = pnLeftLeft.getComponents();
//        for (Component component : components) {
//            if (component instanceof JButton) {
//                final JButton button = (JButton) component;
//                button.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent arg0) {
//                        // Đưa các nút đang được click ở panel khác về trạng thái bình thường
//                        homepagePanel.setNormalAllLabel();
//                        settingPanel.setNormalAllLabel();
//                        addendumPanel.setNormalAllLabel();
//
//                        String key = button.getIcon().toString();
//                        cardLayout.show(pnLeftCenter, key);
//                        
//                        disableHighLightButtons(components);
//                        button.setBorder(pnLeftButtonHighLightBorder);
//                        
//                        if(key.equals(homepageKey)){
//                            splitPane.setEnabled(false);
//                        } else {
//                            splitPane.setEnabled(true);
//                        }
//                    }
//                });
//            }
//        }
//    }

	//private void disableHighLightButtons(Component... components) {
//        for (Component component : components) {
//            if (component instanceof JButton) {
//                final JButton button = (JButton) component;
//                button.setBorder(defaultBorder);
//            }
//        }
	//}

	// public int searchWord(String text, BiPredicate func){
	// return homepagePanel.searchWord(text, func);
	// }

	// public boolean isBtHomepageActive(){
//        System.out.println(btHomepage.isEnabled());
//        return btHomepage.isShowing();
	// }

	public void activeBtHomepage() {
//        if(homepagePanel.isVisible() == false){
//            btHomepage.doClick();
//            pnLeftCenter.revalidate();
//        }
	}

	public void showWordAtIndex(int index) {
		// homepagePanel.showWordAtIndex(index);
	}

	public void setNewDictionary() {
//        if(homepagePanel.setNewDictionary()){
//            cbbModelOfMainForm.removeAllElements();
//        }
	}

	public void setNewLanguageApp() {
//        LanguageAppEnum choosenLanguageApp = (LanguageAppEnum) JOptionPane.showInputDialog(null, 
//                                            languageApp.getValue("set_dic_kind_mess"), 
//                                            languageApp.getValue("set_dic_kind_title"), 
//                                            JOptionPane.PLAIN_MESSAGE, 
//                                            null, 
//                                            LanguageAppEnum.values(), 
//                                            languageApp);
//        if(choosenLanguageApp == null){
//            return;
//        }
//        
//        if(choosenLanguageApp == languageApp){
//            JOptionPane.showMessageDialog(null, languageApp.getValue("already_set_new_language"));
//            setNewLanguageApp();
//        } else {
//            this.languageApp = choosenLanguageApp;
//            settingPanel.setLanguageApp(languageApp);
//            addendumPanel.setLanguageApp(languageApp);
//            homepagePanel.setLanguageApp(languageApp);
//            PanelCenter panelCenter = (PanelCenter) splitPane.getRightComponent();
//            panelCenter.setLanguageApp(languageApp);
//            JOptionPane.showMessageDialog(null, languageApp.getValue("complete_set_new_language"));
//        }
	}

	protected void initView() {
		this.setBackground(new Color(255,255,240));
		setLayout(new BorderLayout());

		pnlLeftleft = new PanelLeftLeft(this.conList);
		pnlLeftCenter = new PanelLeftCenter(this.conList);
		
		add(pnlLeftleft, BorderLayout.WEST);
		add(pnlLeftCenter, BorderLayout.CENTER);

		// homepagePanel = new HomepagePanel(dicEnum, splitPane, languageApp);
//      settingPanel = new SettingPanel(this, languageApp);
//      addendumPanel = new AddendumPanel(splitPane, languageApp);
//      
//      btHomepage.setBorder(pnLeftButtonHighLightBorder);
//      
//      pnLeftCenter.add(homepagePanel, btHomepage.getIcon().toString());
//      pnLeftCenter.add(settingPanel, btSetting.getIcon().toString());
//      pnLeftCenter.add(addendumPanel, btAddendum.getIcon().toString());

	}

	@Override
	protected void register() {
		conList.addContainer(PanelLeft.key, this);
	}

	@Override
	protected void initEvent() {
		
	}
}
