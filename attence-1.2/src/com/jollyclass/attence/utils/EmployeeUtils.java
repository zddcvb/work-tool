package com.jollyclass.attence.utils;

import java.util.ArrayList;
import java.util.List;
import com.jollyclass.attence.domain.Employee;
/**
 * 员工工具类
 * @author saber
 *
 */
public class EmployeeUtils {
	/**
	 * 从原始数据中去除重复选出的日期选项，并删除星期六和星期日的数据，保存新的数据集合
	 * @param list 需要处理的原始数据
	 * @return 新的list集合
	 */
	public static List<Employee> deleteRepeatData(List<Employee> list) {
		List<Employee> employees=new ArrayList<Employee>(list);
		for (int i = 0; i < employees.size(); i++) {
			Employee employee = employees.get(i);
			for (int j = i + 1; j < employees.size(); j++) {
				Employee comp = employees.get(j);
				if (employee.getName().equals(comp.getName())) {
					if (employee.getAttenceDate().equals(comp.getAttenceDate())) {
						employees.remove(comp);
					}
				}else{
					if (comp.getWeek().equals("星期六")||comp.getWeek().equals("星期日")) {
						employees.remove(comp);
					}
				}
			}
			if (employee.getWeek().equals("星期六")||employee.getWeek().equals("星期日")) {
				employees.remove(employee);
			}
		}
		return list;
	}
}
