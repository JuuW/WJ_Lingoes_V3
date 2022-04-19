package cn.wj.lingoes.v2.view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import cn.wj.lingoes.v2.utility.ContainerList;

@SuppressWarnings("serial")
public abstract class PopUpFrame extends JFrame {
	public static String key = "PopUpFrame";

	protected ContainerList conList;
	
	public PopUpFrame(ContainerList conList) {
		super();
		this.conList = conList;
		initView();
		initEvent();
		register();
		setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	
	protected abstract void register();
	protected abstract void initView();
	protected abstract void initEvent();
}
