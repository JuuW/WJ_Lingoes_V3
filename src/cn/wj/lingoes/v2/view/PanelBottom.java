package cn.wj.lingoes.v2.view;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JLabel;

import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public class PanelBottom extends LingoesPanel {
	
	protected static String key = "PanelBottom";
	
	public PanelBottom(ContainerList conList) {
		super(conList);
	}


	private JLabel lblFooter;
	

	@Override
	protected void initView() {
		this.setBackground(new Color(255,255,240));
		lblFooter = new JLabel();
		lblFooter.setText("Copyright © 2022-2022 Lingoes for MacOS");
		GroupLayout pnlBottomLayout = new GroupLayout(this);
		this.setLayout(pnlBottomLayout);
		pnlBottomLayout.setHorizontalGroup(pnlBottomLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
				pnlBottomLayout.createSequentialGroup().addComponent(lblFooter).addGap(0, 329, Short.MAX_VALUE)));
		pnlBottomLayout.setVerticalGroup(pnlBottomLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(pnlBottomLayout.createSequentialGroup().addGap(0, 0, 0).addComponent(lblFooter).addGap(0, 0,
						Short.MAX_VALUE)));

	}


	@Override
	protected void register() {

		conList.addContainer(PanelBottom.key, this);
	}


	@Override
	protected void initEvent() {
		
	}


}
