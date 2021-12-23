package com.westvalley.g.sysbasic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public   class SysBasicService {
	/**
	 * 日期格式转换   Fri Dec 31 00:00:00 GMT+08:00 9999 转换成  yyyy-MM-dd
	 * tangqf 20190424
	 * @param datestr
	 * @return
	 */
	public String changeDateFormat(String datestr){
		 SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
		 SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
		 Date d;
		try {
			d = sf.parse(datestr);
			String f = s.format(d);
			return f;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	
	/**
	 * 转换日期格式为为YYYYMMDD
	 */
	public String changeToDateYYYYMMDD(String dates){
		if(!trim(dates).equals("")){
			return dates.replaceAll("-", "");
		}else{
			return "";
		}
	}
	
	/**
	 * 转换日期格式为为YYYY
	 */
	public String changeToDateYYYY(String dates){
		if(!trim(dates).equals("")){
			 String date= dates.replaceAll("-", "");
			 String YYYY=date.substring(0,4);
			    return YYYY;
		}else{
			return "";
		}
	}
	/**
	 * 转换日期格式为为MM
	 */
	public String changeToDateMM(String dates){
		if(!trim(dates).equals("")){
			 String date= dates.replaceAll("-", "");
			 String MM=date.substring(4,6);
			    return MM;
		}else{
			return "";
		}
	}
	
	/**去除s前后空格及null返回空串*/
	public String trim(String s){
		if(s!=null&&s!=""){
			s=s.trim();
			if("null".equals(s)) return "";
		}else{
			s="";
		}
		return s;
	}
	/**
	 * 根据输入的日期和格式，返回对应格式的日期
	 * @param date 日期
	 * @param sformat 日期格式 yyyyMMdd  
	 * @return
	 */
	public  String changeDateFormat(String datestr,String sformat) {
		  SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		  Date date;
		try {
			date = formatter.parse(datestr);
			String dateString = formatter.format(date);
			return dateString;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	
	
	
}
