package com.yanxi.data.ui;

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

import com.yanxi.data.utils.ChacterUtils;
import com.yanxi.data.utils.FileMoveUtils;
import com.yanxi.data.utils.JsonFileUtils;
import com.yanxi.data.utils.ZipUtils;

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements ActionListener {
	private static Logger logger=Logger.getLogger(MyFrame.class);
	private JButton select_content;
	private JTextField contentTx;
	private File selectedFile;
	private JButton submit_content;
	private String filePath;
	private JButton zip_content;
	private JButton json_content;

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
		Font font = new Font("微软雅黑", 0, 16);
		contentTx = new JTextField("请输入路径", 15);
		contentTx.setFont(font);
		select_content = new JButton("选择目标文件");
		select_content.setFont(font);
		//自动生成json文本
		json_content = new JButton("1-json生成");
		json_content.setFont(font);
		//自动创建swf、图片文件夹，并将数据规整到swf文件夹下
		submit_content = new JButton("2-文件移动");
		submit_content.setFont(font);
		//自动压缩swf和图片文件，并规整到课件目录下，生成zip文件
		zip_content = new JButton("3-文件压缩");
		zip_content.setFont(font);
		content_panel.add(contentTx);
		content_panel.add(select_content);
		content_panel.add(json_content);
		content_panel.add(submit_content);
		content_panel.add(zip_content);
		this.add(content_panel);
		this.pack();
		initEvent();
	}
	/**
	 * 处理各种按钮点击事件
	 */
	private void initEvent() {
		select_content.addActionListener(this);
		json_content.addActionListener(this);
		submit_content.addActionListener(this);
		zip_content.addActionListener(this);
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
		case "选择目标文件":
			openWindowsFileDialog(contentTx);
			break;
		case "1-json生成":
			jsonData();
			break;
		case "2-文件移动":
			moveData();
			this.pack();
			break;
		case "3-文件压缩":
			zipData();
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
		JsonFileUtils jsonFileUtils = new JsonFileUtils();
		String message = jsonFileUtils.readFile(filePath);
		if (message != "") {
			showDialog(message);
			jsonFileUtils.clearMsg();
			return;
		} else {
			showDialog(ChacterUtils.JSON_SUCCESS);
		}
	}
	/**
	 * 生成zip文件
	 */
	private void zipData() {
		if (filePath != null) {
			zipFile();
			showDialog("文件压缩成功！");
		}
	}
	/**
	 * 执行zip压缩
	 * 
	 */
	private void zipFile() {
		ZipUtils zipUtils = new ZipUtils();
		try {
			zipUtils.zip(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 移动数据，生成swf和图片文件夹
	 */
	private void moveData() {
		if (filePath != null) {
			moveFile();
			showDialog("移动成功！");
		}
	}

	/**
	 * 执行移动数据功能
	 */
	private void moveFile() {
		FileMoveUtils utils = new FileMoveUtils();
		try {
			utils.moveFile(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
