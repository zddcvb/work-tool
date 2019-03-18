package com.yanxi.animate.tool.ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.yanxi.animate.tool.abs.JollyClassUIAbstract;

public class JollyClassSysUI extends JollyClassUIAbstract {

	private static final long serialVersionUID = -6823992051044609428L;
	private JButton sys_four_btn;
	private JButton sys_three_btn;
	private static final String JOLLYCLASS_SYS_VERSION_3="小水滴课堂V3.0";
	private static final String JOLLYCLASS_SYS_VERSION_4="小水滴课堂V4.0";

	/**
	 * 初始化frame窗口
	 */
	public void initUI() {
		this.setTitle("动画部工具V1.0   邹丹丹");
		this.setSize(400, 500);
		this.setLayout(new FlowLayout());
		setWindowsWidthAndHeight();
		this.setResizable(false);
		initButton();
		this.setVisible(true);
	}

	public void initButton() {
		// =============内容选择框================
		GridLayout gridLayout = new GridLayout(5, 5, 20, 20);
		JPanel content_panel = new JPanel(gridLayout);
		Font font = new Font("微软雅黑", 0, 16);
		sys_three_btn = new JButton(JOLLYCLASS_SYS_VERSION_3);
		sys_three_btn.setFont(font);
		sys_four_btn = new JButton(JOLLYCLASS_SYS_VERSION_4);
		sys_four_btn.setFont(font);
		content_panel.add(sys_three_btn);
		content_panel.add(sys_four_btn);
		this.add(content_panel);
		this.pack();
		initEvent();
	}

	private void initEvent() {
		sys_three_btn.addActionListener(this);
		sys_four_btn.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		case JOLLYCLASS_SYS_VERSION_3:
			
			break;
		case JOLLYCLASS_SYS_VERSION_4:
			//启动3.0制作工具页面，并关闭当前的页面
			JollyClassFourSysUI jollyClassFourSysUI=new JollyClassFourSysUI();
			jollyClassFourSysUI.onStart();
			break;
		default:
			break;
		}
	}
	
	public void onPause(){
		this.setVisible(false);
	}
	public void onStart(){
		initUI();
	}

	@Override
	public void onStop() {
		System.exit(0);
	}
	
}
