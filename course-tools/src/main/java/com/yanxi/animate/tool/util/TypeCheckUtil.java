package com.yanxi.animate.tool.util;

public class TypeCheckUtil {
	public static String[] videoTypes={"avi","mpg","mpeg","mov","flv","rmvb","rm","3gp"};
	public static String[] imgTypes={"jpg","jpeg","tiff","tga","bmp"};
	public static String[] audioTypes={"wav","mp3","ogg",""};
	
	public static boolean checkTypes(String[] arrs,String type){
		for (String arr : arrs) {
			if (!type.equals(arr)) {
				return false;
			}
		}
		return true;
	}
}
