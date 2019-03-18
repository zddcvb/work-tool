package com.jollyclass.json.ui;

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
import com.jollyclass.json.domain.ClassDetail;
import com.jollyclass.json.utils.ChacterUtils;
import com.jollyclass.json.utils.FileUtils;
import com.jollyclass.json.utils.GsonUtils2;
import com.jollyclass.json.utils.JsonFileUtils;

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements ActionListener {
	private JButton select_content;
	private JTextField contentTx;
	private File selectedFile;
	private JButton submit_content;
	private String filePath;

	public MyFrame() {

	}
	/**
	 * 初始化窗体按钮
	 */
	public void initUI() {
		this.setTitle(ChacterUtils.UI_TITLE);
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
	 * 初始化界面按钮
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
		submit_content = new JButton(ChacterUtils.UI_EXEC);
		submit_content.setFont(font);
		content_panel.add(contentTx);
		content_panel.add(select_content);
		content_panel.add(submit_content);
		this.add(content_panel);
		this.pack();
		initEvent();
	}
	/**
	 * 初始化点击事件
	 */
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
	/**
	 * 点击事件具体操作
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case ChacterUtils.UI_FILE_SELECT:
			openWindowsFileDialog(contentTx);
			break;
		case ChacterUtils.UI_EXEC:
			submitData();
			this.pack();
			break;
		}
	}
	/**
	 * 执行操作
	 */
	private void submitData() {
		if (filePath != null) {
			//exec();
			exec_1();
		}
	}
	/**
	 * 打开文件夹选择菜单
	 * @param targetTextfield 文本框
	 */
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
	@SuppressWarnings("unused")
	private void exec() {
		// String filePath="G:\\内网通缓存\\李勇\\4个拆分文件\\北风呼呼吹";
		JsonFileUtils jsonFileUtils = new JsonFileUtils();
		jsonFileUtils.readFile(filePath);
		ClassDetail classDetail = jsonFileUtils.createDomain(filePath);
		String result = JsonFileUtils.mHandleMsg;
		if (result != "") {
			showDialog(result);
			JsonFileUtils.mHandleMsg = "";
			return;
		}
		String jsonData = GsonUtils2.objectToJson(classDetail);
		System.out.println(jsonData);
		String savePath = filePath + "\\" + filePath.substring(filePath.lastIndexOf("\\") + 1) + ".txt";
		FileUtils.writeData(savePath, jsonData);
		showDialog(ChacterUtils.JSON_SUCCESS);
	}
	/**
	 * 执行生成操作
	 */
	@SuppressWarnings("static-access")
	private void exec_1(){
		JsonFileUtils jsonFileUtils = new JsonFileUtils();
		String message = jsonFileUtils.readFile(filePath);
		if (message!="") {
			showDialog(message);
			jsonFileUtils.mHandleMsg="";
			return;
		}else{
			showDialog(ChacterUtils.JSON_SUCCESS);
		}
	}

	/**
	 * 错误时提示错误信息
	 * 
	 * @param msg
	 */
	private void showDialog(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
}
