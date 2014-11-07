package com.danielbchapman.production.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import lombok.Getter;
import lombok.ToString;

import com.danielbchapman.production.entity.Day;

@ToString
public class MonthDto
{
	@Getter
	private ArrayList<Day> days = new ArrayList<Day>(42);
	
	public ArrayList<Day> getDaysPadForRender()
	{
		Calendar start = utc(getDays().get(0).getDate());
		Calendar end = utc(getDays().get(days.size() - 1).getDate());
		
		ArrayList<Day> padFront = new ArrayList<Day>();
		if(start.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			for(int i = start.get(Calendar.DAY_OF_WEEK); i >= Calendar.SUNDAY; i--)
			{
				start.add(Calendar.DAY_OF_YEAR, -1);
				padFront.add(Day.emptyDay(start.getTime()));
			}
				
		}
		
		ArrayList<Day> padRear = new ArrayList<Day>();		
		if(end.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
		{
			for(int i = end.get(Calendar.DAY_OF_WEEK); i <= Calendar.SATURDAY; i++)
			{
				end.add(Calendar.DAY_OF_YEAR, 1);
				padRear.add(Day.emptyDay(start.getTime()));
			}
		}
		
		ArrayList<Day> copy = new ArrayList<Day>();
		
		for(Day d : padFront)
			copy.add(d);
		for(Day d : getDays())
			copy.add(d);
		for(Day d : padRear)
			copy.add(d);
		
		return copy;

	}
	public static ArrayList<MonthDto> createMonths(ArrayList<Day> days)
	{
		if(days == null || days.size() == 0)
			return new ArrayList<MonthDto>();
		
		ArrayList<MonthDto> months = new ArrayList<MonthDto>();
		
		if(days.size() == 1)
		{
			months.add(new MonthDto(days));
			return months;
		}
		
		ArrayList<Day> currentMonth = new ArrayList<Day>(42);
		
		if(days.size() > 2)
		{
			Calendar start = utc(days.get(0).getDate());
			int monthId = start.get(Calendar.MONTH);
			
			for(Day day : days)
			{
				Calendar dayTime = utc(day.getDate());
				
				if(dayTime.get(Calendar.MONTH) != monthId)
				{
					//create month
					months.add(new MonthDto(currentMonth));
					//clear
					monthId = dayTime.get(Calendar.MONTH);
					currentMonth.clear();
				}
				
				currentMonth.add(day);
			}
		}
				
		return months;
	}
	
	public MonthDto(ArrayList<Day> days)
	{
		this.days = new ArrayList<Day>();
		for(Day d : days)
			this.days.add(d);
	}
	
	public String toString()
	{
		HashMap<Integer, Integer> counts = new HashMap<Integer, Integer>();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		
		StringBuilder builder = new StringBuilder();
		
		int i = 0;
		
		for(Day d : getDaysPadForRender())
		{
			cal.setTime(d.getDate());
			Integer key = cal.get(Calendar.MONTH);
			Integer count = counts.get(key);
			counts.put(key, count == null ? 1 : ++count);
			
			builder.append("[");
			builder.append(i);
			builder.append("] ");
			builder.append(d);
			builder.append("\n");
			i++;
		}
		
		int max = 0;
		int maxKey = -1;
		for(Integer key : counts.keySet())
			if(maxKey == -1)
			{
				maxKey = key;
				max = counts.get(maxKey);
			}
			else
			{
				if(max < counts.get(maxKey))
				{
					maxKey = key;
					max = counts.get(maxKey);
				}
			}
		cal.set(Calendar.MONTH, maxKey);
		SimpleDateFormat calDf = new SimpleDateFormat("MMMMMM");
		StringBuilder likely = new StringBuilder();
		likely.append(calDf.format(cal.getTime()));
		likely.append("\n------------------------------------------------\n");
		likely.append(builder.toString());
		return likely.toString();
	}
	
	private final static Calendar utc(Date date)
	{
		Calendar ret = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		ret.setTime(date);
		return ret;
	}
}
