package cn.wj.lingoes.v2.view;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;

import cn.wj.lingoes.v2.MainFrameV3;
import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public class PanelLeftCenterUtility extends LingoesPanel {
	protected static String key = "PanelLeftCenterUtility";
	private JButton btnContryCode;

	public PanelLeftCenterUtility(ContainerList conList) {
		super(conList);
	}

	@Override
	protected void initView() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnContryCode = new JButton("Country Code");
		this.add(btnContryCode);
	}

	@Override
	protected void register() {

		conList.addContainer(PanelLeftCenterUtility.key, this);
	}

	@Override
	protected void initEvent() {
		btnContryCode.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				PanelCenter pc = (PanelCenter) conList.getContainer(PanelCenter.key);
				InputStream ips = MainFrameV3.class.getResourceAsStream("/resource/contrycode.html");
				BufferedReader bReader = new BufferedReader(new InputStreamReader(ips));
				StringBuilder sb = new StringBuilder();
				String s = "";
				try {
					while ((s = bReader.readLine()) != null) {
						sb.append(s);
					}
					bReader.close();
					pc.displayHTML(sb.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

	}
}
