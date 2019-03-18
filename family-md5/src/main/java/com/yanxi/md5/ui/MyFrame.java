package com.yanxi.md5.ui;

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

import org.apache.log4j.Logger;

import com.yanxi.md5.util.ChacterUtils;
import com.yanxi.md5.util.Md5Utils;


@SuppressWarnings("serial")
public class MyFrame extends JFrame implements ActionListener {
	private static Logger logger=Logger.getLogger(MyFrame.class);
	private JButton select_content;
	private JTextField contentTx;
	private File selectedFile;
	private String filePath;
	private JButton md5_content;

	public MyFrame() {

	}
	/**
	 * 初始化frame窗口
	 */
	public void initUI() {
		this.setTitle(ChacterUtils.VERSION);
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
	/**
	 * 初始化控件
	 * 输入框：路径显示框
	 * 选择按钮：选择路径文件功能
	 * json按钮：生成json文本
	 * 文件移动按钮：生成swf和图片文件夹
	 * zip按钮：压缩课件，生成zip文件
	 */
	private void initButton() {
		// =============内容选择框================
		GridLayout gridLayout = new GridLayout(5, 5, 20, 20);
		JPanel content_panel = new JPanel(gridLayout);
		Font font = new Font(ChacterUtils.UI_FONT, 0, 16);
		contentTx = new JTextField(ChacterUtils.UI_FILE_PATH, 15);
		contentTx.setFont(font);
		select_content = new JButton(ChacterUtils.UI_FILE_SELECT);
		select_content.setFont(font);
		//自动生成json文本
		md5_content = new JButton(ChacterUtils.UI_EXEC);
		md5_content.setFont(font);
		
		content_panel.add(contentTx);
		content_panel.add(select_content);
		content_panel.add(md5_content);
		
		this.add(content_panel);
		this.pack();
		initEvent();
	}
	/**
	 * 处理各种按钮点击事件
	 */
	private void initEvent() {
		select_content.addActionListener(this);
		md5_content.addActionListener(this);
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
	/**
	 * 具体的按钮点击实现
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case ChacterUtils.UI_FILE_SELECT:
			openWindowsFileDialog(contentTx);
			break;
		case ChacterUtils.UI_EXEC:
			jsonData();
			break;
		
		}
	}
	/**
	 * 生成txt数据文件
	 */
	private void jsonData() {
		if (filePath != null) {
			jsonCreate();
			//showDialog("json文件生成成功！");
		}
	}
	/**
	 * 具体执行txt生成
	 */
	private void jsonCreate() {
		Md5Utils.clearCache();
		Md5Utils md5Utils=new Md5Utils();
		String message = md5Utils.createFile(new File(filePath),new File( "d:/output.txt"));
		showDialog(message);
	}
	/**
	 * 打开选择文件对话框
	 * @param targetTextfield
	 */
	private void openWindowsFileDialog(JTextField targetTextfield) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setCurrentDirectory(selectedFile);
		chooser.showOpenDialog(this);
		selectedFile = chooser.getSelectedFile();
		if (selectedFile != null) {
			filePath = selectedFile.getAbsolutePath();
			logger.info(filePath);
			String displayTx = filePath.substring(filePath.lastIndexOf("\\") + 1);
			targetTextfield.setText(displayTx);
			logger.info("field:" + targetTextfield.getText().trim());
		}
	}
	/**
	 * 显示对话框
	 * @param msg
	 */
	private void showDialog(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
}
