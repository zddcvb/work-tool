package com.sunvision.pngtojpeg.test;

import org.junit.Test;

import com.sunvision.pngtojpeg.main.PngToJpeg;

public class PngToJpegTest {
	@Test
	public void testConvertPref(){
		String name=PngToJpeg.convertPref("d:/1.png");
		System.out.println(name);
	}
	@Test
	public void testconvertPng(){
		PngToJpeg.convertPng("d:/png");
	}
	@Test
	public void testimageConvert(){
		PngToJpeg.imageConvert("d:/png/背景.png");
	}
}
