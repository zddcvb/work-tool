package com.jollyclass.zipDic.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements ActionListener {
	private JButton select_content;
	private JTextField contentTx;
	private File selectedFile;
	private JButton submit_content;
	private String filePath;

	public MyFrame() {

	}

	public void initUI() {
		this.setTitle("盒子3.0课件压缩");
		this.setSize(400, 500);
		this.setLayout(new FlowLayout());
		setFrame();
		this.setResizable(false);
		initButton();
		this.setVisible(true);
	}

	/**
	 * 窗口居中显示
	 */
	private void setFrame() {
		int windowWidth = this.getWidth(); // 获得窗口宽
		int windowHeight = this.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);// 设置窗口居中显示
	}

	private void initButton() {
		// =============内容选择框================
		GridLayout gridLayout = new GridLayout(5, 5, 20, 20);
		JPanel content_panel = new JPanel(gridLayout);
		Font font = new Font("微软雅黑", 0, 16);
		contentTx = new JTextField("请输入路径", 15);
		contentTx.setFont(font);
		select_content = new JButton("选择目标文件");
		select_content.setFont(font);
		submit_content = new JButton("执行");
		submit_content.setFont(font);
		content_panel.add(contentTx);
		content_panel.add(select_content);
		content_panel.add(submit_content);
		this.add(content_panel);
		this.pack();
		initEvent();
	}

	private void initEvent() {
		select_content.addActionListener(this);
		submit_content.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("closed");
				super.windowClosed(e);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case "选择目标文件":
			openWindowsFileDialog(contentTx);
			break;
		case "执行":
			submitData();
			this.pack();
			break;
		}
	}

	private void submitData() {
		if (filePath != null) {
			exec();
			showDialog("压缩成功！");			
		}
	}

	private void openWindowsFileDialog(JTextField targetTextfield) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setCurrentDirectory(selectedFile);
		chooser.showOpenDialog(this);
		selectedFile = chooser.getSelectedFile();
		if (selectedFile != null) {
			filePath = selectedFile.getAbsolutePath();
			System.out.println(filePath);
			String displayTx = filePath.substring(filePath.lastIndexOf("\\") + 1);
			targetTextfield.setText(displayTx);
			System.out.println("field:" + targetTextfield.getText().trim());
		}
	}

	/**
	 * 执行自动生成json
	 */
	private void exec() {
		ZipUtils zipUtils=new ZipUtils();
		try {
			zipUtils.zip(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void showDialog(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
}
