package cn.wj.lingoes.v2.view;

import java.awt.CardLayout;
import java.awt.Color;

import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public class PanelLeftCenter extends LingoesPanel {
	protected static String key = "PanelLeftCenter";

	public PanelLeftCenter(ContainerList conList) {
		
		
		super(conList);
		
	}

	public CardLayout cardLayout; 

	private LingoesPanel pnlList;
	private LingoesPanel pnlSetting;
	private LingoesPanel pnlUtility;
//  private JSplitPane splitPane;

//  homepagePanel = new PanelLeftCenterList();
//  settingPanel = new SettingPanel(this, languageApp);
//  addendumPanel = new AddendumPanel(splitPane, languageApp);

	@Override
	protected void initView() {
		this.setBackground(new Color(255,255,240));
		cardLayout = new CardLayout();
		this.setLayout(cardLayout);	

		pnlList = new PanelLeftCenterList(this.conList);
		pnlSetting = new PanelLeftCenterSetting(this.conList);
		pnlUtility = new PanelLeftCenterUtility(this.conList);
		
		//pnlList.setBackground(Color.black);
		//pnlSetting.setBackground(Color.yellow);
		//pnlUtility.setBackground(Color.blue);
//
//		PanelLeftLeft leftleft = (PanelLeftLeft) this.conList.getContainer(PanelLeftLeft.key);
//
////		add(pnlList, leftleft.getBtnList().getIcon().toString());
////		add(pnlSetting, leftleft.getBtnSetting().getIcon().toString());
////		add(pnlUtility, leftleft.getBtnUtility().getIcon().toString());
//		
		this.add(pnlList,"1" );
	    this.add(pnlSetting,"2" );
		this.add(pnlUtility ,"3");
		//cardLayout.show(this, "1");
		
		
	//	JButton btnList = new JButton("dd");
		

		

	}

	@Override
	protected void register() {

		conList.addContainer(PanelLeftCenter.key, this);
	}

	@Override
	protected void initEvent() {
		
	}

}
