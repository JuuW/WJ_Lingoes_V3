package cn.wj.lingoes.v2.view;

import javax.swing.JPanel;

import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public abstract class LingoesPanel extends JPanel {

	public static String key = "LingoesPanel";

	protected ContainerList conList;

	public LingoesPanel(ContainerList conList) {
		super();
		this.conList = conList;
		register();
		initView();
		initEvent();
	}

	protected abstract void register();
	protected abstract void initView();
	protected abstract void initEvent();

}
