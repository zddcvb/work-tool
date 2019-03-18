package com.jollyclass.backup.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * 备份文件文件工具类
 * 
 * @author 邹丹丹
 *
 */
public class BackUpUtils {
	private List<String> fileList = new ArrayList<>();

	/**
	 * 生成备份清单
	 * 
	 * @param path
	 *            需要读取的文件路径
	 */
	public void createBackUpFile(String path, boolean flag) {
		// 第一步：遍历文件夹，获取每个文件的数据
		// List<String> fileList = readFile(path);
		List<String> fileList = FileUtils.getFileInfo(new File(path), flag);
		// 第二步：生成txt
		// String txtFile = path + "\\备份" + ".txt";

		// creatTxt(fileList, txtFile);
		// 第三步：生成表格
		String name = path.substring(path.lastIndexOf("\\"));
		String xlsFile = "";
		if (flag) {
			xlsFile = path + "\\" + name + "备份清单-1.xls";
		} else {
			xlsFile = path + "\\" + name + "备份清单.xls";
		}
		createXls(fileList, xlsFile);
	}

	/**
	 * 创建xls备份表格
	 * 
	 * @param fileList
	 *            需要生成表格的数据
	 * @param xlsFile
	 *            xls表格保存的地址
	 * @return 布尔值
	 */
	private Boolean createXls(List<String> datas, String xlsFile) {
		// List<String> datas = createListData(fileList);
		// System.out.println(datas);
		HSSFWorkbook book = createBook(datas);
		OutputStream os = null;
		try {
			os = new FileOutputStream(xlsFile);
			book.write(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 创建表格文件
	 * 
	 * @param datas
	 *            需要创建表格的原始数据
	 * @return hssfworkbook对象
	 */
	private HSSFWorkbook createBook(List<String> datas) {
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("备份表");
		HSSFCellStyle cellStyle = setCellstyle(book);
		int len = recreateData(datas);
		System.out.println("len:" + len);
		for (int i = 0; i < datas.size() + 2; i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < len; j++) {
				HSSFCell cell = row.createCell(j);
				sheet.setColumnWidth(j, 6000);
				int rowNum = row.getRowNum();
				if (rowNum == 0) {
					CellRangeAddress cra = new CellRangeAddress(0, 0, 0, len - 1);
					sheet.addMergedRegion(cra);
					cell.setCellValue("部门备份文件清单");
					cell.setCellStyle(cellStyle);
					break;
				} else if (rowNum == 1) {
					if (j < len - 4) {
						cell.setCellValue("文件夹-" + (j + 1));
					} else if (j == len - 1) {
						cell.setCellValue("最后修改时间");
					} else if (j == len - 2) {
						cell.setCellValue("文件创建时间");
					} else if (j == len - 3) {
						cell.setCellValue("文件容量");
					} else if (j == len - 4) {
						cell.setCellValue("文件名称");
					}
					cell.setCellStyle(cellStyle);
				} else {
					String string = datas.get(i - 2);
					String replaceStr = string.replace("\\", "__");
					String[] strs = replaceStr.split("__");
					if (j < strs.length - 4) {
						String data = strs[j];
						if (j == 0) {
							data = data.replace(":", "");
						}
						cell.setCellValue(data);
					} else if (j == len - 1) {
						cell.setCellValue(strs[strs.length - 1]);
					} else if (j == len - 2) {
						cell.setCellValue(strs[strs.length - 2]);
					} else if (j == len - 3) {
						cell.setCellValue(strs[strs.length - 3]);
					} else if (j == len - 4) {
						cell.setCellValue(strs[strs.length - 4]);
					} else {
						cell.setCellValue("");
					}
					cell.setCellStyle(cellStyle);
				}
			}

		}
		return book;
	}

	/**
	 * 重构素材，获取集合中最长的条目
	 * 
	 * @param datas
	 *            需要重构的集合
	 * @return 最长的条目
	 */
	private int recreateData(List<String> datas) {
		int len = 0;
		// 得到条目的最大长度
		for (String string : datas) {
			String replaceStr = string.replace("\\", "__");
			// System.out.println(replaceStr);
			String[] strs = replaceStr.split("__");
			if (strs.length > len) {
				len = strs.length;
			}
		}
		return len;
	}

	/**
	 * 设置表格样式
	 * 
	 * @param book
	 *            book对象
	 * @return hssfcellstyle对象
	 */
	private static HSSFCellStyle setCellstyle(HSSFWorkbook book) {
		HSSFCellStyle cellStyle = book.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		HSSFFont font = book.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 12);
		font.setBold(false);
		cellStyle.setFont(font);
		return cellStyle;
	}

	/**
	 * 将集合中的数据写入txt文档中
	 * 
	 * @param fileList
	 *            需要写入的数据
	 * @param txtFile
	 *            需要保存的txt文档地址
	 * @return 布尔值
	 */
	@SuppressWarnings("unused")
	private Boolean creatTxt(List<String> fileList, String txtFile) {
		if (fileList.size() > 0) {
			StringBuilder builder = new StringBuilder();
			for (String string : fileList) {
				File file = new File(string);
				String usableSpace = FileUtils.getDataSize(file.length());
				String createTime = FileUtils.getCreateTime(file);
				String lastModifyTime = FileUtils.getLastModifyTime(file);
				builder.append(string + "\\" + usableSpace + "\\" + createTime + "\\" + lastModifyTime + "\n");
			}
			// System.out.println(builder.toString());
			return writeTxt(builder.toString(), txtFile);
		}
		return false;
	}

	/**
	 * 根据集合数据，获取文件的其他属性：文件大小、创建时间、最后修改的时间
	 * 
	 * @param fileList
	 *            需要修改的集合数据
	 * @return list集合，新的
	 */
	@SuppressWarnings("unused")
	private List<String> createListData(List<String> fileList) {
		List<String> targetStrs = new ArrayList<>();
		if (fileList.size() > 0) {
			for (String string : fileList) {
				File file = new File(string);
				String usableSpace = FileUtils.getDataSize(file.length());
				String createTime = FileUtils.getCreateTime(file);
				String lastModifyTime = FileUtils.getLastModifyTime(file);
				String data = string + "\\" + usableSpace + "\\" + createTime + "\\" + lastModifyTime;
				targetStrs.add(data);
			}
		}
		return targetStrs;
	}

	/**
	 * 将数据写入txt文档中
	 * 
	 * @param string
	 *            需要写入的字符串数据
	 * @param txtFile
	 *            保存的地址
	 * @return 布尔值
	 */
	private Boolean writeTxt(String string, String txtFile) {
		BufferedWriter bufw = null;
		try {
			bufw = new BufferedWriter(new FileWriter(txtFile));
			bufw.write(string);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (bufw != null) {
					bufw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据地址读取文件，获得文件数据
	 * 
	 * @param path
	 *            文件地址
	 * @return 集合数据
	 */
	@SuppressWarnings("unused")
	private List<String> readFile(String path) {

		File soureFile = new File(path);
		File[] listFiles = soureFile.listFiles();
		if (listFiles.length == 0) {
			fileList.add(path);
		} else {
			for (File file : listFiles) {
				String filePath = file.getAbsolutePath();
				// System.out.println("filepath:" + filePath);
				if (file.isFile()) {
					fileList.add(filePath);
				} else {
					fileList.add(filePath);
					readFile(filePath);
				}
			}
		}
		// System.out.println(fileList.size());
		return fileList;
	}

}
