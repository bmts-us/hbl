/*
 * DateUtils.java
 *
 * Created on May 15 2007, 12:01 PM
 */
package com.ht.offline.borlette.utils;
/*
 * @auteur : Vertus Hector
 *
 */

import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.text.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Time ;
import java.sql.Timestamp;
import java.util.regex.Pattern;
import biz.isman.util.ConvertUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;


public class DateUtils {
	//private static final Log log = LogFactory.getLog(DateUtils.class);
    // defining date format here.
    private static final SimpleDateFormat sdf = new SimpleDateFormat(ConvertUtils.ESCAPE_DATE_PATTERN);
    
    public static final String DEFAULT_TIME_FORMAT	= "hh:mm a";
    
    public static final String DEFAULT_SYSTEM_DATE	= "01/01/2000"; //Default date to calculate time duration, time between and others. Very important.
    
    public static final String DEFAULT_SYSTEM_DATE_FORMAT	= "dd/MM/yyyy hh:mm a"; //Default date time format to calculate time duration, time between and others. Very important.
    
    public DateUtils(){
       super();
    }

    //return the default system date above with the given time.
    public static Date defaultSystemDate(String time) {	
        return stringTodate(DEFAULT_SYSTEM_DATE + " " + time, DEFAULT_SYSTEM_DATE_FORMAT);
    }
    
    //method to display current date
    public static Date dateNow() {
        return new Date();
    }
    
    public static Date dateNow(String format) {
    	Date dateNow = null; 
    	try {
    		dateNow = new SimpleDateFormat(format).parse(new SimpleDateFormat(format).format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return dateNow;
    }
    
    public static Timestamp currentDateTime() {
    	final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());    	
    	return Timestamp.valueOf(sdf1.format(timestamp));
    }    
    
    //method to display current date
    public static String currentDate() {
        return sdf.format(new Date());
    }

    //convert java.util.Date to string
    public static String dateToString(Date date){
		String dat = null;
		try{
            dat = sdf.format(date);
		} catch(Exception ex) { ex.printStackTrace();}
		return dat;
    }
    
    //convert the current to string
    public static String getDate() {       
    	return new SimpleDateFormat("MM/dd/yyyy").format(new Date());
    }
    
    public static Date toDate(String dateString) {
        Date date = null;
        try {
            date = new SimpleDateFormat(ConvertUtils.ESCAPE_DATE_PATTERN).parse(dateString);
        } catch(Exception ex) { ex.printStackTrace();}
        return date;
    }    
    
    //convert string to date
    public static Date getDate(String inDate) {       
        Date convertedDate = null; 
        try {
          //parse the inDate parameter
          convertedDate=sdf.parse(inDate.trim());
        }
        catch(Exception ex) {
          ex.printStackTrace();
          return null;
        }	
    	
        return convertedDate;
    }
    
    public static String getDate(Date date) {
		String	dateString	 = null;
		try{
			if(Utils.isNotNull(date)) {
				dateString = new SimpleDateFormat("MM/dd/yyyy").format(date);
			}
		} catch(Exception ex) { ex.printStackTrace();}
		return dateString;
    }  
    
    public static String getDate(Date date, String format) {
		String	dateString	 = null;
		try{
			if(Utils.isNotNull(date)) {
				dateString = new SimpleDateFormat(format).format(date);
			}
		} catch(Exception ex) { ex.printStackTrace();}
		return dateString;
    }     

    public static String getDateFr(Date date) {
    	return Utils.capitalizeString(ConvertUtils.getDate(date, Utils.getLocale("Fr"), ConvertUtils.DEFAULT));
    }
    
	public static String getShortDateFr(Date date) {
	    return getDay(date)+" "+monthFr(getMonth(date))+" "+getYear(date);
	}
	
	public static String getShortDateEn(Date date) {
	    return monthEn(getMonth(date)) + " "  + getDay(date) + " " + getYear(date);
	}	
	
	public static String getShortDateCr(Date date) {
		return getDay(date)+" "+monthCr(getMonth(date))+" "+getYear(date);
	}
	
	public static String shortDate(Date date, String language) { //language:: en-->English, fr-->French, cr-->Creole
		String shortDate = null;
		
		switch(language) {
			case "en": shortDate = monthEn(getMonth(date))+" "+getDay(date)+" "+getYear(date); break;
			case "en_US": shortDate = monthEn(getMonth(date))+" "+getDay(date)+" "+getYear(date); break;
			case "fr": shortDate = getDay(date)+ " "+monthFr(getMonth(date))+" "+getYear(date); break;
			case "fr_FR": shortDate = getDay(date)+ " "+monthFr(getMonth(date))+" "+getYear(date); break;
			case "ht": shortDate = getDay(date)+" "+monthCr(getMonth(date))+" "+getYear(date); break;
			case "ht_HT": shortDate = getDay(date)+" "+monthCr(getMonth(date))+" "+getYear(date); break;
		}
		
		return shortDate;
	}
    
    //convert java.util.Date to string by passing the date and the format
    public static String dateToString(Date date, String format) {
		String	dat	 = null;
		try{
            dat = new SimpleDateFormat(format).format(date);
		} catch(Exception ex) { ex.printStackTrace();}
		return dat;
    }

    //convert string to java.util.Date
    public static Date stringTodate(String dateInString) {
      return ConvertUtils.getDate(dateInString, "MM/dd/yyyy");
    } 
    
    public static Date stringTodate(String dateInString, String format) {
        return ConvertUtils.getDate(dateInString, format);
    }     
    
    //convert string to java.util.Date
    public static Date stringToDate(Date date, String format) {    	
    	return ConvertUtils.getDate(getDate(date, format), format);
    }    
    
    //parse date from format "MM/dd/yyyy" to "yyyy-MM-dd"
    public static Date parseDate(String dateString) {
    	DateFormat df1 = new SimpleDateFormat("MM/dd/yy");
    	DateFormat df2 = new SimpleDateFormat(ConvertUtils.ESCAPE_DATE_PATTERN); //the default format is yyyy-MM-dd
    	Date date = null;
    	try {
    		date = (Date) df1.parseObject(dateString.trim());    		
    		//System.out.println("========== Parsing date : " + df2.format(date));
    	} catch(Exception ex) { ex.printStackTrace();}
    	return getDate(df2.format(date));
    }  
    
    //function to format a date
    public static Date formatDate(Date dateToFormat, String format) {
	  SimpleDateFormat sdformat = new SimpleDateFormat(format);
	  Date date = null;
	  try {					
		String dateString = sdformat.format(dateToFormat);
		date = sdformat.parse (dateString);    		
	  } catch(Exception ex) { 
		  ex.printStackTrace();
	  }

      return date;
    }   
    
    //call this function in case dateToFormat is on format d/m/yyyy or m/d/yyyy
    public static Date formatDate(String dateToFormat, String format) {
    
      if(ConvertUtils.isBlank(dateToFormat))	{
    	  return null;
      }
      
	  Date date = null;
	  try {
		  String separator = dateToFormat.contains("/") ? "/"
			  		 									: dateToFormat.contains("-") 
			  		 									? "-":null;
		  
		  String[] dmy = ConvertUtils.isNotBlank(separator) ? dateToFormat.split(separator):null;
				  		 
			if(Utils.isNotNull(dmy)) {
				for(int i = 0; i < dmy.length; i++) {
					if(dmy[i].length()==1) {
						dmy[i] = "0".concat(dmy[i]);
					}
				}
				
				dateToFormat = dmy[0].concat(separator).concat(dmy[1]).concat(separator).concat(dmy[2]);
				date = formatDate(ConvertUtils.getDate(dateToFormat, format), format);    						
			}		  
	  } catch(Exception ex) { 
		  ex.printStackTrace();
	  }

      return date;
    }    

    //method to display current date without format
    public static String currentDateWithoutFormat() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        return sdfDate.format(new Date());
    }
    
    //Combine a date and a time into a single date-time using java.util.Date
    public static Date combineDateTime(Date date, Date time) {
    	Calendar calendarA = Calendar.getInstance();
    	calendarA.setTime(date);
    	Calendar calendarB = Calendar.getInstance();
    	calendarB.setTime(time);
     
    	calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
    	calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
    	calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
    	calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));
     
    	Date result = calendarA.getTime();
    	return result;
    }    

    //convert java.util.Date time to string by passing the date and the time format
    public static String timeToString(Date time, String format){
		String date = null;
		try{
			date = new SimpleDateFormat(format).format(time);        
		} catch(Exception ex) { ex.printStackTrace();}
		return date;
    }

    //method to convert a time to string
    public String currentTime(Date time) {
        //SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        return  timeToString(time, "HH:mm:ss");
    }

    //method to display current date and time on format date_time
    public String currentDateTime(char separator) {
        return currentDate().concat(separator + timeToString());
    }

    //Default method to display date and time
    public static String dateTime(Date date) {
        String datetime = "";
        try {
           datetime = dateToString(date,"yyyy-MM-dd").concat(", ").concat(timeToString(date, "HH:mm:ss"));
        }
        catch(Exception ex) { ex.printStackTrace();}

        return datetime;
    }

    //Default method to display date and time by passing the date and time format
    public String dateTime(Date date, String dateformat, String timeformat) {
        String datetime = "";
        try {
           datetime = dateToString(date,dateformat).concat(", ").concat(timeToString(date, timeformat));
        }
        catch(Exception ex) { ex.printStackTrace();}

        return datetime;
    }

    //Default method to display date and time
    public String dateTime(Date date, Time time) {
        String datetime = "";
        try {
           datetime = dateToString(date,"yyyy-MM-dd").concat(", ").concat(timeToString(time));
        }
        catch(Exception exp) {}

        return datetime;
    }

    //getting the first day of the current month (system date to java.sql.Date for JDBC)
    public java.sql.Date firstDayOfMonth() throws Exception {
      Calendar c = Calendar.getInstance();
      int first = 1;

      String now = (c.get(Calendar.MONTH)+1) + "/" + first + "/" + c.get(Calendar.YEAR);

      return new java.sql.Date(sdf.parse(now).getTime());
    }

    //getting last date of the current month (system date to java.sql.Date for JDBC)
    public java.sql.Date lastDayOfMonth() throws Exception {
      Calendar c = Calendar.getInstance();

      //int val = month();

      String now = (c.get(Calendar.MONTH)+1) + "/" + daysOfCurrentMonth() + "/" + c.get(Calendar.YEAR);

      return new java.sql.Date(sdf.parse(now).getTime());
    }

	//--------------------------------------------------------------------------
	public static String generateAMonthPeriod(Date date) {
		int month = getMonth(date);
		int day = getDay(date);
		int year = getYear(date);

		if(month == 1 && (day == 30 || day == 31)){
			month = 2;
			if (year % 4 == 0) day = 29;
			else day = 28;
		} else if(month == 12 && day > 1) {
			year++;
			month = 1;
		} else if(day == 1) {
			day = daysOfCurrentMonth();
		} else {
			day--;
			month++;
		}

		String mm = month < 10? "0"+month : String.valueOf(month);
		String dd = day < 10? "0"+day : String.valueOf(day);

		return dateToString(date,"yyyy-MM-dd") + "," + year + "-" + mm + "-" + dd;
	}

    //----------------------------------------------------------------------------------------------------------------------------------------
    public static int getYear(Date date){    	  
    	  SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
    	 return Integer.parseInt(simpleDateformat.format(date));
    }  
    
    public static int getMonth(Date date){    	  
  	   SimpleDateFormat simpleDateformat=new SimpleDateFormat("MM");
  	   return Integer.parseInt(simpleDateformat.format(date));
    }  
    
    public static int getDay(Date date){    	  
   	   SimpleDateFormat simpleDateformat=new SimpleDateFormat("dd");
   	   return Integer.parseInt(simpleDateformat.format(date));
    }
    
    //will return number of days between date before and date after. Example: diffBetweenDates(2019-09-13, 2019-09-20)=7.
    //If date after place before date before will return a negative value.
    public static int diffBetweenDates(Date dateBefore, Date dateAfter) {
	       dateBefore = formatDate(dateBefore, "yyyy-MM-dd");
	       dateAfter = formatDate(dateAfter, "yyyy-MM-dd");
	       long difference = dateAfter.getTime() - dateBefore.getTime();
	       float daysBetween = (difference / (1000*60*60*24));
            /* You can also convert the milliseconds to days using this method
             * float daysBetween = 
             *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
             */
	       
	       return (int)daysBetween;
    }
   //-----------------------------------------------------------------------------------------------------------------------------------------
    //display the current time on the standard format
    @SuppressWarnings("static-access")
    public static Date timeNow() {
		Date time = null;
		try{
			String now  = new SimpleDateFormat("HH:mm:ss").format(new Date());			
			DateFormat timeformat = DateFormat.getTimeInstance(DateFormat.getTimeInstance().DEFAULT, Locale.getDefault());
			time = timeformat.parse(now);
		} catch(Exception ex){ ex.printStackTrace(); }

		return time;
    }
    
    public static Time getCurrentTime() {
    	Calendar cal = Calendar.getInstance(); 
    	return new Time( cal.getTime().getTime() );
    }

    //convert java.util.Date time to string by passing the time format
    @SuppressWarnings("static-access")
	public Date timeNow(String tformat) {
		Date time = null;
		try{
			String     now        = new SimpleDateFormat(tformat).format(new Date());
			DateFormat timeformat = DateFormat.getTimeInstance(DateFormat.getTimeInstance().DEFAULT, Locale.getDefault());
			time = timeformat.parse(now);
		} catch(Exception exp){}

		return time;
    }

    //jdbc time type
    public static Time currentTime() {
        Calendar cal = Calendar.getInstance();
        return new Time(cal.getTime().getTime());
    }

    //method to convert sql current time to string
    public static String timeToString() {
        Calendar cal  =  Calendar.getInstance();
        Time     time =  new Time(cal.getTime().getTime());
        return time.toString();
    }

    //method to convert a given sql time to string
    public static String timeToString(Time time) {
        return Utils.isNotNull(time) ? new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(time) : null;
    }
    
    public static String timeToString(Time time, String format) {
        return Utils.isNotNull(time) ? new SimpleDateFormat(format).format(time) : null;
    }    
    
    public static Time parseStringToTime(String timeString) {    	
    	return Utils.isNotNull(timeString) ? parseStringToTime(timeString, DEFAULT_TIME_FORMAT) : null;  //default time format
    }
    
    public static Time parseStringToTime(String timeString, String format) {    	
    	SimpleDateFormat sdf = new SimpleDateFormat(format); 
    	Time time = null;
    	try { 
    		long ms = sdf.parse(timeString).getTime(); 
    		time = new Time(ms); 
    	} catch(ParseException pex) {}    	
    	
    	return time;  //time.valueOf(timeString); //we could use this
    }    

    public static Time stringToTime(String timeString) { //time must be under format hh:mm:ss   		    	
    	return Utils.isNotNull(timeString) ? Time.valueOf(timeString) : null;
    } 
    
	//method to calculate the difference between two times, returning value will be the duration.
	public static Time timeDuration(Time time1, Time time2) {
		Time duration = null; 
		
		try {
			long diff = time2.getTime() - time1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			
			//make sure hours and minutes are two digits.
			String hours = String.valueOf(diffHours).length()==1 ? "0".concat(String.valueOf(diffHours)): String.valueOf(diffHours);
			String minutes = String.valueOf(diffMinutes).length()==1 ? "0".concat(String.valueOf(diffMinutes)): String.valueOf(diffMinutes);
			
			Date timeDiff = DateUtils.defaultSystemDate(hours+":"+minutes+" am"); //timeFormat.parse(hours+":"+minutes+":00");
			duration = new Time(timeDiff.getTime());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return duration;
	}
	
	//method to calculate the difference between two times, returning value will be the duration in string under format hh:mm.
	public static String timeDurationToString(Time time1, Time time2) {
		String duration = null; 
		
		try {
			long diff = time2.getTime() - time1.getTime();
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			
			//make sure hours and minutes  are two digits.
			String hours = String.valueOf(diffHours).length()==1 ? "0".concat(String.valueOf(diffHours)): String.valueOf(diffHours);
			String minutes = String.valueOf(diffMinutes).length()==1 ? "0".concat(String.valueOf(diffMinutes)): String.valueOf(diffMinutes);

			duration = hours+":"+minutes;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return duration;
	}
	
	//method to calculate the difference between two times, returning value will be the duration in double.
	public static double timeDurationToDouble(Time time1, Time time2) {
		String timeDuration = timeDurationToString(time1, time2).replace(":", ".");
		return Double.parseDouble(timeDuration);
	}	
	
	public static double timeToDouble(Time time) { //time must be under format hh:mm
		return Double.parseDouble(time.toString().replace(":", "."));
	}
	
	public static double timeToDouble(String time) { //time must be under format hh:mm
		return Double.parseDouble(time.replace(":", "."));
	}	

	//cast java.sql.Time to java.util.Date
	public static Date timeToDate(Time time) { 
		return time;
	}	
	
	//cast java.util.Date to java.sql.Time
	public static Time dateToTime(Date date) { 
		return new Time(date.getTime());
	}
	
	//method to get meridian AM/PM from a given time
	public static String timeMeridian(Time time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		return calendar.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault()).trim();
	}
	
	//Times comparison--------------------
    public static boolean isTimeBefore(String atime, String other) {	//time must be under format hh:mm:ss

        Date aDate = new Date(Time.valueOf(atime).getTime());

        Date otherDate = new Date(Time.valueOf(other).getTime());

        return aDate.before(otherDate);
    }

    //time must be under format hh:mm:ss
    public static boolean isTimeAfter(String atime, String other) {
        return !isTimeBefore(atime, other);
    }   
	
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		@SuppressWarnings("deprecation")
	   public Date dateFromLatestTreeMonth() throws Exception {
	    Calendar cal = Calendar.getInstance();
	    int firstday = 1;
	    String latesthreemonth =  (cal.get(Calendar.MONTH)-1) + "/" + firstday + "/" + cal.get(Calendar.YEAR); //never change this format in order to be able parse the string to date
		Date date = new Date(latesthreemonth); 
	    return date;
	   }
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	   
	   public static int daysOfCurrentMonth() {
	       Calendar cal = Calendar.getInstance();
	       int nbr_day = 0;
	
	       switch(cal.get(Calendar.MONTH)+1) {
	           case 1    : nbr_day = 31; break; //return the number of day of the current month
	           case 2    : nbr_day = 28; break; //or 29
	           case 3    : nbr_day = 31; break;
	           case 4    : nbr_day = 30; break;
	           case 5    : nbr_day = 31; break;
	           case 6    : nbr_day = 30; break;
	           case 7    : nbr_day = 31; break;
	           case 8    : nbr_day = 31; break;
	           case 9    : nbr_day = 30; break;
	           case 10   : nbr_day = 31; break;
	           case 11   : nbr_day = 30; break;
	           case 12   : nbr_day = 31; break;
	       }
	       
	       return nbr_day;
	   }
	
	   public static String month(int monthValue) {
	       String month = "";
	       switch(monthValue) {
	           case 1    : month = "Jannuary"; break;
	           case 2    : month = "Febraury"; break;
	           case 3    : month = "March"; break;
	           case 4    : month = "April"; break;
	           case 5    : month = "May"; break;
	           case 6    : month = "June"; break;
	           case 7    : month = "July"; break;
	           case 8    : month = "August"; break;
	           case 9    : month = "September"; break;
	           case 10   : month = "October"; break;
	           case 11   : month = "November"; break;
	           case 12   : month = "December"; break;
	       }
	       return month;
	   }
	   
	   public static String monthFr(int monthValue) {
	       String month = "";
	       switch(monthValue) {
	           case 1    : month = "Jan."; break;
	           case 2    : month = "Fév."; break;
	           case 3    : month = "Mar."; break;
	           case 4    : month = "Avr."; break;
	           case 5    : month = "Mai"; break;
	           case 6    : month = "Juin"; break;
	           case 7    : month = "Jul."; break;
	           case 8    : month = "Aoû."; break;
	           case 9    : month = "Sep."; break;
	           case 10   : month = "Oct."; break;
	           case 11   : month = "Nov."; break;
	           case 12   : month = "Déc."; break;
	       }
	       return month;
	   }	
	   
	    public static String monthEn(int monthValue) {
	        String month = "";
	        switch(monthValue) {
	            case 1    : month = "Jan."; break;
	            case 2    : month = "Feb."; break;
	            case 3    : month = "Mar."; break;
	            case 4    : month = "Apr."; break;
	            case 5    : month = "May"; break;
	            case 6    : month = "Jun."; break;
	            case 7    : month = "Jul."; break;
	            case 8    : month = "Aug."; break;
	            case 9    : month = "Sep."; break;
	            case 10   : month = "Oct."; break;
	            case 11   : month = "Nov."; break;
	            case 12   : month = "Dec."; break;
	        }
	        return month;
	    }

	    public static String monthCr(int monthValue) {
	        String month = "";
	        switch(monthValue) {
	            case 1    : month = "Jan."; break;
	            case 2    : month = "Fev."; break;
	            case 3    : month = "Mas."; break;
	            case 4    : month = "Avr."; break;
	            case 5    : month = "Me."; break;
	            case 6    : month = "Jen."; break;
	            case 7    : month = "Jiy."; break;
	            case 8    : month = "Out."; break;
	            case 9    : month = "Sep."; break;
	            case 10   : month = "Okt."; break;
	            case 11   : month = "Nov."; break;
	            case 12   : month = "Des."; break;
	        }
	        return month;
	    }	   
	   
	   public static String day(int dayValue) {
	       String day = "";
	       switch(dayValue) {
	           case 1    : day = "Sunday"; break;
	           case 2    : day = "Monday"; break;
	           case 3    : day = "Tuesday"; break;
	           case 4    : day = "Wednesday"; break;
	           case 5    : day = "Thursday"; break;
	           case 6    : day = "Friday"; break;
	           case 7    : day = "Saturday"; break;
	       }
	       
	       return day;
	   }
	   
	   public static String dayFr(int dayValue) {
	       String day = "";
	       switch(dayValue) {
	           case 1    : day = "Dim."; break;
	           case 2    : day = "Lun."; break;
	           case 3    : day = "Mar."; break;
	           case 4    : day = "Mer."; break;
	           case 5    : day = "Jeu."; break;
	           case 6    : day = "Ven."; break;
	           case 7    : day = "Sam."; break;
	       }
	       
	       return day;
	   }	   
	   
	   public static String longHour(String shortHour) {
			String time = null;
			
			switch(shortHour) {
				case "00" : time = "12"; break;
				case "01" : time = "13"; break;
				case "02" : time = "14"; break;
				case "03" : time = "15"; break;
				case "04" : time = "16"; break;
				case "05" : time = "17"; break;
				case "06" : time = "18"; break;
				case "07" : time = "19"; break;
				case "08" : time = "20"; break;
				case "09" : time = "21"; break;
				case "10" : time = "22"; break;
				case "11" : time = "23"; break;			
				case "12" : time = "12"; break; //twelve must set to twelve			
			}
			
			return time;
	   }
		
	   public static String shortHour(String longHour) {
			String time = null;
			int hour = Integer.parseInt(longHour);
			
			if(hour >= 12) {
				switch(longHour) {
					case "00" : time = "12"; break;				
					case "13" : time = "01"; break;
					case "14" : time = "02"; break;
					case "15" : time = "03"; break;
					case "16" : time = "04"; break;
					case "17" : time = "05"; break;
					case "18" : time = "06"; break;
					case "19" : time = "07"; break;
					case "20" : time = "08"; break;
					case "21" : time = "09"; break;
					case "22" : time = "10"; break;
					case "23" : time = "11"; break;	
					case "12" : time = "12"; break; //twelve must set to twelve		
				}
			} else time = longHour;
			
			return time;
		}	   
	   
	   /*by providing a given month return the begin date and the end date of the month
	     first element is the begin date, and second element is the end date */
	   public static Date[] datesFromMonth(int monthValue, int year) {
		   Date[] dates = new Date[2];
		   
		   if((monthValue<=0 && monthValue > 12) || (year <= 0) || String.valueOf(year).length() != 4) {
			   return null;
		   }
		   
		   final String partialDate = (monthValue < 10 ? "0".concat(String.valueOf(monthValue)) : String.valueOf(monthValue))
				   					  .concat("/").concat(String.valueOf(year));
		   
		   String date = "01/".concat(partialDate);
		   Date beginDate = null, endDate = null;

		   switch(monthValue) {
	           case 1    :  beginDate = stringTodate(date, "dd/MM/yyyy");
	           				date = "31/".concat(partialDate);
	           				endDate = stringTodate(date, "dd/MM/yyyy");
	           				
	           				dates[0] = beginDate;
	           				dates[1] = endDate;
	           				break;
           				
	           case 2    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			  				date = "28/".concat(partialDate);
			  				endDate = stringTodate(date, "dd/MM/yyyy");
			  				
			  				dates[0] = beginDate;
			  				dates[1] = endDate;
			  				break;
  				
	           case 3    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			  				date = "31/".concat(partialDate);
			  				endDate = stringTodate(date, "dd/MM/yyyy");
			  				
			  				dates[0] = beginDate;
			  				dates[1] = endDate;
			  				break;
  				
	           case 4    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			  				date = "30/".concat(partialDate);
			  				endDate = stringTodate(date, "dd/MM/yyyy");
			  				
			  				dates[0] = beginDate;
			  				dates[1] = endDate;
			  				break; 
			  				
	           case 5    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			  				date = "31/".concat(partialDate);
			  				endDate = stringTodate(date, "dd/MM/yyyy");
			  				
			  				dates[0] = beginDate;
			  				dates[1] = endDate;
			  				break;
  				
	           case 6    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			  				date = "30/".concat(partialDate);
			  				endDate = stringTodate(date, "dd/MM/yyyy");
			  				
			  				dates[0] = beginDate;
			  				dates[1] = endDate;
			  				break;
			  				
	           case 7    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			  				date = "31/".concat(partialDate);
			  				endDate = stringTodate(date, "dd/MM/yyyy");
			  				
			  				dates[0] = beginDate;
			  				dates[1] = endDate;
			  				break;
				
			  case 8    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			 				date = "31/".concat(partialDate);
			 				endDate = stringTodate(date, "dd/MM/yyyy");
			 				
			 				dates[0] = beginDate;
			 				dates[1] = endDate;
			 				break;
		
			  case 9    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			 				date = "30/".concat(partialDate);
			 				endDate = stringTodate(date, "dd/MM/yyyy");
			 				
			 				dates[0] = beginDate;
			 				dates[1] = endDate;
			 				break;
		
			  case 10    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			 				date = "31/".concat(partialDate);
			 				endDate = stringTodate(date, "dd/MM/yyyy");
			 				
			 				dates[0] = beginDate;
			 				dates[1] = endDate;
			 				break; 
 				
			  case 11    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			 				date = "30/".concat(partialDate);
			 				endDate = stringTodate(date, "dd/MM/yyyy");
			 				
			 				dates[0] = beginDate;
			 				dates[1] = endDate;
			 				break;
		
			  case 12    :  beginDate = stringTodate(date, "dd/MM/yyyy");
			 				date = "31/".concat(partialDate);
			 				endDate = stringTodate(date, "dd/MM/yyyy");
			 				
			 				dates[0] = beginDate;
			 				dates[1] = endDate;
			 				break;  			  				
			}	   
		   
		   return dates;
	   }
	
	/***************************************************************************************************************************************************/
	   //method to check if a date is in a range
	 public static boolean dateRange(Date dateMin, Date dateMax, Date date) {
		 return ((date.after(dateMin)) || (date.equals(dateMin))) &&
				 ((date.before(dateMax)) || (date.equals(dateMax)));
	
	 }
	 //method to compare dates
	 public static boolean checkDate(Date dateMin, Date dateMax) {
	     boolean check = false;
	     
	     if(dateMin.after(dateMax) || dateMin.equals(dateMax))
	         check = false; //not a good date
	     else if (dateMin.before(dateMax))
	         check = true; //this a good date because datemin must be before datemax
	     
	     return check;
	 }
	 /***************************************************************************************************************************************************/
	 /*method to check if a String is a valid date by parsing the String with an instance
	   of the SimpleDateFormat class and returns true or false.*/
	 public static boolean isValidDate(String inDate, String dateFormat) {
		    if (inDate == null)
		      return false;
	
		    if (inDate.trim().length() != sdf.toPattern().length())
		      return false;
	
		    sdf.setLenient(false);
		    
		    try {
		      //parse the inDate parameter
		    	sdf.parse(inDate.trim());
		    }
		    catch (ParseException pe) {
		      pe.printStackTrace();	
		      return false;
		    }
		    return true;
	}		 
	 
	//same method as isValidDate above 
	public static boolean isValidDate(String inDate) {	 	
		 	return isValidDate(inDate, ConvertUtils.ESCAPE_DATE_PATTERN);
	} 
	
	//method to check if a string is valid date by respecting the default date pattern
	public static boolean isAdate(String dateText) {  	  
	    // Check it is of the correct format i.e. dddd-dd-dd  
	    boolean isCorrectFormat = Pattern.matches("\\d{4}-\\d{2}-\\d{2}", dateText);  
	
	    // Check that we can parse it.  
	    SimpleDateFormat sdf = new SimpleDateFormat(ConvertUtils.ESCAPE_DATE_PATTERN);  //default date pattern
	    sdf.setLenient(false);  
	    boolean parseable = sdf.parse(dateText, new ParsePosition(0)) != null; 
	    
	    return isCorrectFormat && parseable;  
	}
	 
	public static boolean isAdate(String inDate, String format) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(format); //"dd-MM-yyyy HH:mm:ss:ms"
	        dateFormat.setLenient(false);
	        
	        try {
	            dateFormat.parse(inDate.trim());
	        } catch (ParseException pe) {
	            return false;
	        }
	        
	        return true;
	}
	
	//method to check the standard american date format
	public static boolean datePattern(String dateText) {
		return Pattern.matches("\\d{2}/\\d{2}/\\d{4}", dateText); //format :: MM/dd/yyyy 
	}
	
	/***************************************************************************************************************************************************/
	/*method to check if a date range is within another date range
	 *@startDate1-endDate1 is the main date range
	 *@startDate2-endDate2 is the range to check 
	*/
	public static boolean isWithinDateRange(Date startDate1, Date endDate1,Date startDate2, Date endDate2) {
		return startDate2.after(startDate1) && endDate2.before(endDate1);
	}
	 
	//same method as above but startDate end endDate are include ; Example : >= daterange <=
	public static boolean isWithinDateRangeIn(Date startDate1, Date endDate1,Date startDate2, Date endDate2) {
		//if(((startDate2==startDate1) || (startDate2.after(startDate1))) && (endDate2.before(endDate1) || (endDate2==endDate1)))
		return ((startDate2.equals(startDate1)) || (startDate2.after(startDate1))) && (endDate2.before(endDate1) || (endDate2.equals(endDate1)));
	}
	
	public static boolean isWithinDateRange(Date startdate, Date enddate, Date datetosearch) {
		return (datetosearch.after(startdate)) && (datetosearch.before(enddate));
	}
	
	public static boolean isWithinDateRangeIn(Date startdate, Date enddate, Date datetosearch) {
		startdate = formatDate(startdate,"yyyy-MM-dd");
		enddate = formatDate(enddate,"yyyy-MM-dd");
		datetosearch = formatDate(datetosearch,"yyyy-MM-dd");

		return ((datetosearch.after(startdate)) || (datetosearch.equals(startdate))) && ((datetosearch.before(enddate)) || (datetosearch.equals(enddate)));
	}
	
	/*method to check if a time range is within another time range
	 *@startTime1-endTime1 is the main time range
	 *@startTime2-endTime2 is the range to check 
	*/
	//method to check if a time is between two times. Time format must be hh:mm a
	public static boolean isWithinTimeRangeIn(String startTime, String endTime, String time) {	
			Date beginTime  = defaultSystemDate(startTime);
			Date finishTime = defaultSystemDate(endTime);
			Date checkedTime= defaultSystemDate(time);
	
			return isWithinDateRangeIn(beginTime, finishTime, checkedTime);
	}   
	
	//method to check if a range of time 2 is between range time1. Time format must be hh:mm a
	public static boolean isWithinTimeRangeIn(String startTime1, String endTime1,String startTime2, String endTime2) {
			return (isWithinTimeRangeIn(startTime1, endTime1, startTime2) && isWithinTimeRangeIn(startTime1, endTime1, endTime2));		
	} 
	
	public static boolean isValidTimeRange(String startTime, String endTime) {
		return defaultSystemDate(startTime).before(defaultSystemDate(endTime));	
	}
	/***************************************************************************************************************************************************/
	
	 public static boolean isDateEqual(Date date1, Date date2) {
	    Calendar cal1 = Calendar.getInstance();
	    Calendar cal2 = Calendar.getInstance();
	    cal1.setTime(date1);
	    cal2.setTime(date2);

		 return (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) && (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)) &&
				 (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
	 }
	/***************************************************************************************************************************************************/
	 public static boolean isDaterangeIn(Date[] daterangeParent, Date[] daterangeChild) { //check if a date range is inside another
		 return (daterangeChild[0].after(daterangeParent[0])) && (daterangeChild[1].before(daterangeParent[1]));
	 }
	 /***************************************************************************************************************************************************/ 
	 public static int getMonths(Date startdate, Date enddate) {
		 return ConvertUtils.countDays(startdate, enddate)/30;
	 }
	 /***************************************************************************************************************************************************/ 
	 //Date is comparable, so add them all to a list, and use Collections.max() to find the greatest (latest) date
	 public static Date getMostRecentDate(List<Date> dates) {
		  return Collections.max(dates); //return the greatest date.
	 } 
 
	 // Time class to make difference between to times :: diff = difference(start, stop);
	 public class Heure {
		    int seconds;
		    int minutes;
		    int hours;
		    
		    public Heure(int hours, int minutes, int seconds) {
		        this.hours = hours;
		        this.minutes = minutes;
		        this.seconds = seconds;
		    }

		    /*public static void main(String[] args) {
		        Time start = new Time(12, 34, 55),
		                stop = new Time(8, 12, 15),
		                diff;
		        diff = difference(start, stop);
		        System.out.printf("TIME DIFFERENCE: %d:%d:%d - ", start.hours, start.minutes, start.seconds);
		        System.out.printf("%d:%d:%d ", stop.hours, stop.minutes, stop.seconds);
		        System.out.printf("= %d:%d:%d\n", diff.hours, diff.minutes, diff.seconds);
		    }*/

		    public Heure difference(Heure start, Heure stop) {
		    	Heure diff = new Heure(0, 0, 0);
		        if(stop.seconds > start.seconds){
		            --start.minutes;
		            start.seconds += 60;
		        }

		        diff.seconds = start.seconds - stop.seconds;
		        if(stop.minutes > start.minutes){
		            --start.hours;
		            start.minutes += 60;
		        }

		        diff.minutes = start.minutes - stop.minutes;
		        diff.hours = start.hours - stop.hours;

		        return(diff);
		    }
		}
	 
	 //this function will return the begin and end date of the current month.
	 public static Date[] beginAndEndDate() {
         LocalDate currentDate = LocalDate.now();
         
         //default time zone
     	 ZoneId defaultZoneId = ZoneId.systemDefault();
     	
         Date beginDate = Date.from(currentDate.withDayOfMonth(1).atStartOfDay(defaultZoneId).toInstant());
         Date endDate = Date.from(currentDate.withDayOfMonth(currentDate.lengthOfMonth()).atStartOfDay(defaultZoneId).toInstant());
         Date[] pairDate = {beginDate, endDate};
         return pairDate;
	 }
	 
	 public static Date[] beginAndEndDate(int month) {
		 Date bdate = formatDate(month+"/01/"+getYear(dateNow()), "MM/dd/yyyy"); //stringTodate(month+"/01/"+getYear(dateNow()));
		 Calendar cal = Calendar.getInstance();
		 Date edate = formatDate(month+"/"+cal.getActualMaximum(Calendar.DATE)+"/"+getYear(dateNow()), "MM/dd/yyyy"); //stringTodate(month+"/"+cal.getActualMaximum(Calendar.DATE)+"/"+getYear(dateNow()));
		 Date[] pairDate = {bdate, edate};
         return pairDate;
	 }
	 
}
