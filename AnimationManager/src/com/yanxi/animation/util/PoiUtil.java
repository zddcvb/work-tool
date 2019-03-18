package com.yanxi.animation.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.yanxi.animation.domain.Department;
import com.yanxi.animation.domain.Employee;
import com.yanxi.animation.domain.Project;
import com.yanxi.animation.domain.Role;
import com.yanxi.animation.service.DepartmentService;
import com.yanxi.animation.service.EmployeeService;
import com.yanxi.animation.service.RoleService;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * excel表格读取和写入的工具类
 * 
 * @author 邹丹丹
 *
 */
public class PoiUtil {
	/**
	 * 项目表中的类目
	 */
	private String[] titles = { "序号", "项目", "动画名称", "难易级别", "负责人", "完成个数", "参与人员", "审核年月", "备注" };
	private static Logger logger = Logger.getLogger(PoiUtil.class);
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 读取项目信息
	 * 
	 * @param filePath
	 *            读取的文件地址
	 * @return 返回list集合
	 */
	public List<Project> readProjectData(String filePath) {
		logger.info("================readProjectData====================");
		List<Project> projects = new ArrayList<>();
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook book = Workbook.getWorkbook(is);
			Sheet sheet = book.getSheet(0);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			for (int i = 2; i < rows; i++) {
				Project project = new Project();
				for (int j = 0; j < columns; j++) {
					String contents = sheet.getCell(j, i).getContents();
					if (j == 1) {
						project.setProjectName(contents);
					} else if (j == 2) {
						project.setAnimationName(contents);
					} else if (j == 4) {
						if (!StringUtils.isEmpty(contents)) {
							if (contents.contains("/")) {
								String id = getIdFromManyEmployee(contents);
								project.setEmployeeId(id);
							} else {
								logger.info("==========only one=======");
								logger.info(contents);
								Employee employee = employeeService.findByName(contents);
								if (employee != null) {
									project.setEmployeeId(employee.getId() + "");
								}else{
									project.setEmployeeId("null");
								}
							}
						} else {
							logger.info("isEmpty");
							project.setEmployeeId("null");
						}
					} else if (j == 5) {
						if (contents.equals("")) {
							project.setCompletedNum(1);
						} else {
							project.setCompletedNum(Integer.parseInt(contents));
						}
					} else if (j == 6) {
						if (!StringUtils.isEmpty(contents)) {
							if (contents.contains("/")) {
								String id = getIdFromManyEmployee(contents);
								project.setJoinId(id);
							} else {
								logger.info("==========only one=======");
								logger.info(contents);
								Employee employee = employeeService.findByName(contents);
								if (employee != null) {
									project.setJoinId(employee.getId() + "");
								}
							}
						} else {
							logger.info("============isEmpty==================");
							project.setJoinId("null");
						}
					} else if (j == 3) {
						project.setHardLevel(contents);
					} else if (j == 8) {
						project.setDescription(contents);
					} else if (j == 7) {
						project.setYear("2017年");
					}
				}
				projects.add(project);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		logger.info(projects);
		return projects;
	}

	private String getIdFromManyEmployee(String contents) {
		logger.info("==========many employee=======");
		String[] split = contents.split("/");
		String id = "";
		for (String string : split) {
			Employee employee = employeeService.findByName(string.trim());
			if (employee != null) {
				id += employee.getId() + "/";
				logger.info("id:" + id);
			}
		}
		return id;
	}

	/**
	 * 获取Department的数据表
	 * 
	 * @param filePath
	 *            读取的文件地址
	 * @return 返回list集合
	 */
	public List<Department> readDeptData(String filePath) {
		logger.info("===========readDeptData==============");
		List<Department> depts = new ArrayList<>();
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook book = Workbook.getWorkbook(is);
			Sheet sheet = book.getSheet(0);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			for (int i = 2; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					Department dept = new Department();
					String contents = sheet.getCell(j, i).getContents();
					if (j == 1) {
						dept.setDeptName(contents);
						depts.add(dept);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		logger.info(depts);
		return depts;
	}

	/**
	 * 获取Department的数据表
	 * 
	 * @param filePath
	 *            读取的文件地址
	 * @return 返回一个list集合
	 */
	public List<Role> readRoleData(String filePath) {
		logger.info("==============readRoleData=============");
		List<Role> roles = new ArrayList<>();
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook book = Workbook.getWorkbook(is);
			Sheet sheet = book.getSheet(0);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			for (int i = 2; i < rows; i++) {
				Role role = new Role();
				for (int j = 0; j < columns; j++) {
					String contents = sheet.getCell(j, i).getContents();
					if (j == 1) {
						role.setRoleName(contents);
					} else if (j == 2) {
						Department department = departmentService.findByName(contents);
						logger.info(department);
						role.setDeptId(department.getId());
					}
				}
				roles.add(role);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		System.out.println(roles);
		return roles;
	}

	/**
	 * 从员工信息表中读取员工信息，保存到list集合中
	 * 
	 * @param filePath
	 *            文件地址
	 * @return list集合
	 */
	public List<Employee> readEmployeeData(String filePath) {
		logger.info("================readEmployeeData=================");
		List<Employee> employees = new ArrayList<>();
		try {
			InputStream is = new FileInputStream(filePath);
			Workbook book = Workbook.getWorkbook(is);
			Sheet sheet = book.getSheet(0);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			for (int i = 2; i < rows; i++) {
				Employee employee = new Employee();
				for (int j = 0; j < columns; j++) {
					String contents = sheet.getCell(j, i).getContents();
					if (j == 0) {
						Department department = departmentService.findByName(contents);
						employee.setDeptId(department.getId());
					} else if (j == 1) {
						employee.setName(contents);
					} else if (j == 2) {
						employee.setGendar(contents);
					} else if (j == 4) {
						Role role = roleService.findByName(contents);
						employee.setRoleId(role.getId());
					} else if (j == 5) {
						employee.setPersonId(contents);
					} else if (j == 6) {
						employee.setDegree(contents);
					} else if (j == 7) {
						employee.setGraduationSchool(contents);
					} else if (j == 8) {
						employee.setProfission(contents);
					} else if (j == 9) {
						employee.setFamilyAddress(contents);
					} else if (j == 10) {
						employee.setNowAddress(contents);
					} else if (j == 11) {
						employee.setTelephone(contents);
					} else if (j == 12) {
						employee.setWorkDate(contents);
					} else if (j == 13) {
						if (contents.equals(MessageField.IS_WORKING)) {
							employee.setWork(true);
						} else if (contents.equals(MessageField.WORKED)) {
							employee.setWork(false);
						}
					} else if (j == 3) {
						employee.setAge(Integer.parseInt(contents));
					}
				}
				employees.add(employee);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
		logger.info(employees);
		return employees;
	}

	/**
	 * 创建表格，生成表格对象
	 * 
	 * @param projects
	 *            需要写入的数据
	 * @param nameInfo
	 *            传递的员工姓名
	 * @param savePath
	 *            保存的地址
	 * @return 返回消息字符串
	 */
	public String createExcel(List<Project> projects, String nameInfo, String savePath) {
		logger.info("==============开始创建excel==========");
		String message = "";
		// 生成表格对象
		HSSFWorkbook book = createWorkBook(projects, nameInfo);
		// 写入到磁盘中
		if (book == null) {
			message = MessageField.CREATE_BOOK_WRONG;
		} else {
			message = writeExcelFile(book, savePath + nameInfo + MessageField.EXCEL_SUFFIX);
		}
		return message;
	}

	/**
	 * 获得hssfworkbook对象
	 * 
	 * @param projects
	 *            需要写入到book中的数据
	 * @param nameInfo
	 *            员工姓名，用于设置表格标题文本
	 * @return hssfworkbook对象
	 */
	private HSSFWorkbook createWorkBook(List<Project> projects, String nameInfo) {
		logger.info("==============createWorkBook==========");
		// 创建数据
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet("sheet1");
		CellStyle cellStyle = createStyle(book, (short) 12, false);
		CellStyle contentStyle = createStyle(book, (short) 14, true);
		CellStyle titleStyle = createStyle(book, (short) 16, true);
		if (projects.size() > 0) {
			logger.info("============start sheet===============");
			for (int i = 0; i < projects.size() + 2; i++) {
				logger.info("============start row===============");
				HSSFRow row = sheet.createRow(i);
				for (int j = 0; j < titles.length; j++) {
					logger.info("============start cell===============");
					HSSFCell cell = row.createCell(j);
					sheet.setColumnWidth(j, 3500);
					int rowNum = row.getRowNum();
					row.setHeightInPoints(50);
					if (rowNum == 0) {
						CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, titles.length - 1);
						sheet.addMergedRegion(rangeAddress);
						cell.setCellValue(MessageField.EXCEL_NAME + nameInfo);
						cell.setCellStyle(titleStyle);
						break;
					} else if (rowNum == 1) {
						cell.setCellValue(titles[j]);
						cell.setCellStyle(contentStyle);
					} else {
						Project project = projects.get(i - 2);
						if (j == 0) {
							cell.setCellValue(i - 1);
						} else if (j == 1) {
							cell.setCellValue(project.getProjectName());
						} else if (j == 2) {
							cell.setCellValue(project.getAnimationName());
						} else if (j == 3) {
							cell.setCellValue(project.getHardLevel());
						} else if (j == 4) {
							String employeeId = project.getEmployeeId();
							logger.info("================getEmployeeId=======");
							setEmployeeName(cell, employeeId);
						} else if (j == 5) {
							cell.setCellValue(project.getCompletedNum());
						} else if (j == 6) {
							String employeeId = project.getJoinId();
							logger.info("================getJoinId======="+project.getAnimationName());
							setEmployeeName(cell, employeeId);
						} else if (j == 7) {
							cell.setCellValue(project.getYear());
						} else if (j == 8) {
							cell.setCellValue(project.getDescription());
						}
						cell.setCellStyle(cellStyle);
					}
				}
			}
		}
		return book;
	}

	private void setEmployeeName(HSSFCell cell, String employeeId) {
		logger.info(employeeId.equals("null"));
		if (!employeeId.equals("null")) {
			if (employeeId.contains("/")) {
				String[] split = employeeId.split("/");
				String name = "";
				for (String string : split) {
					Employee employee = employeeService.findById(Integer.parseInt(string));
					name += employee.getName() + "/";
				}
				cell.setCellValue(name);
			} else {
				Employee employee = employeeService.findById(Integer.parseInt(employeeId));
				cell.setCellValue(employee.getName());
			}
		} else {
			cell.setCellValue("null");
		}
	}

	/**
	 * 设置单元格样式
	 * 
	 * @param book
	 *            hssfworkbook对象
	 * @param fontSize
	 *            字体大小
	 * @param isBold
	 *            是否加粗
	 * @return hssfcellstyle样式对象
	 */
	private CellStyle createStyle(HSSFWorkbook book, short fontSize, boolean isBold) {
		logger.info("=============createStyle=================");
		CellStyle cellStyle = book.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		HSSFFont font = book.createFont();
		font.setFontName(MessageField.UI_FONTNAME);
		font.setBold(isBold);
		font.setFontHeightInPoints(fontSize);
		cellStyle.setFont(font);
		return cellStyle;
	}

	/**
	 * 将book写入磁盘文件中
	 * 
	 * @param book
	 *            hssfworkbook对象
	 * @param savePath
	 *            保存的地址
	 * @return 返回消息字符串
	 */
	private String writeExcelFile(HSSFWorkbook book, String savePath) {
		logger.info("================writeExcelFile================");
		OutputStream os = null;
		try {
			os = new FileOutputStream(savePath);
			book.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return MessageField.CREATE_EXCEL_SUCCESS;
	}

}
