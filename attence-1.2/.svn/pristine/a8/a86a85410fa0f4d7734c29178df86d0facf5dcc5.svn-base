package com.jollyclass.attence.test;

import java.util.List;

import org.junit.Test;

import com.jollyclass.attence.utils.AttenceUtils;
import com.jollyclass.attence.utils.CommonUtils;

public class AttenceTest {
	public AttenceUtils utils;
	@Test
	public void testAttence() {
		String[] seconds = CommonUtils.getSeconds("2017-01-13 09:55:46");
		for (String string : seconds) {
			System.out.println("secodes:" + string);
		}
	}

	@Test
	public void testAttence_1() {
		utils=new AttenceUtils("d:/15_attlog.dat", null, null, null, null, null);
		List<String[]> readAttenceSource = utils.readAttenceSource();
		for (String[] strings : readAttenceSource) {
			System.out.print("strings:");
			for (String string : strings) {
				System.out.print(string+"  ");
			}
			System.out.println("");
			System.out.println("===========");
		}
	}

	@Test
	public void testAttence_2() {
		String week=CommonUtils.getweek("2017-06-01 08:37:36");
		System.out.println(week);
	}
	@Test
	public void testCommon(){
		int count = CommonUtils.getResultByMonth("2017-06");
	}
	@Test
	public void testCommon_2(){
		List<String> weekIndexs=CommonUtils.getResultsByMonth("2017-06");
	}
}
