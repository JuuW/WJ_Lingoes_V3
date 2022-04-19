package cn.wj.lingoes.v2.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public class PanelLeftLeft extends LingoesPanel {
	protected static String key = "PanelLeftLeft";

	public PanelLeftLeft(ContainerList conList) {
		super(conList);
	}

	public JButton btnUtility;
	public JButton btnList;
	public JButton btnSetting;

	@Override
	protected void initView() {
		this.setBackground(new Color(255,255,240));
		btnList = new JButton();
		btnSetting = new JButton();
		btnUtility = new JButton();

		btnList.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-homepage-16px.png"))); // NOI18N
		btnList.setBorder(null);
		btnList.setContentAreaFilled(false);

		btnSetting.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-setting-16px.png"))); // NOI18N
		btnSetting.setBorder(null);
		btnSetting.setContentAreaFilled(false);

		btnUtility.setIcon(new ImageIcon(getClass().getResource("/resource/img/icon-addendum.png"))); // NOI18N
		btnUtility.setBorder(null);
		btnUtility.setContentAreaFilled(false);

		GroupLayout pnlLeftLayout = new GroupLayout(this);
		this.setLayout(pnlLeftLayout);
		pnlLeftLayout.setHorizontalGroup(pnlLeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(pnlLeftLayout.createSequentialGroup()
						.addGroup(pnlLeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(btnUtility, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSetting, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGap(0, 0, 0)));

		pnlLeftLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] { btnUtility, btnList, btnSetting });

		pnlLeftLayout.setVerticalGroup(pnlLeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(pnlLeftLayout.createSequentialGroup().addGap(19, 19, 19)
						.addComponent(btnList, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(5, 5, 5)
						.addComponent(btnSetting, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addGap(5, 5, 5)
						.addComponent(btnUtility, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(222, Short.MAX_VALUE)));

		pnlLeftLayout.linkSize(SwingConstants.VERTICAL, new Component[] { btnUtility, btnList, btnSetting });

	}

	@Override
	protected void register() {

		conList.addContainer(PanelLeftLeft.key, this);
	}

	@Override
	protected void initEvent() {
		btnUtility.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelLeftCenter plc = (PanelLeftCenter) conList.getContainer(PanelLeftCenter.key);
				plc.cardLayout.show(plc, "3");

			}
		});
		btnSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelLeftCenter plc = (PanelLeftCenter) conList.getContainer(PanelLeftCenter.key);
				plc.cardLayout.show(plc, "2");

			}
		});
		btnList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PanelLeftCenter plc = (PanelLeftCenter) conList.getContainer(PanelLeftCenter.key);
				plc.cardLayout.show(plc, "1");

			}
		});
	}

}
