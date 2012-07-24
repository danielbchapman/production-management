package com.danielbchapman.production.services;

import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;

//import org.apache.log4j.Logger;
//import org.jboss.varia.scheduler.Schedulable;

import com.danielbchapman.production.beans.TaskDaoRemote;
import com.danielbchapman.production.entity.Task;

public class ReminderService //implements Schedulable
{
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.jboss.varia.scheduler.Schedulable#perform(java.util.Date, long)
//	 */
//	@Override
//	public void perform(Date date, long repetition)
//	{
//		System.out.println("--SCHEDULER SERVICE RUNNING--[" + date + " , " + repetition + "]");
//
//		try
//		{
//			TaskDaoRemote reminderDaoObj = (TaskDaoRemote) new InitialContext().lookup("TaskDao/remote");
//			Logger log = Logger.getLogger(getClass());
//			log.info("--SCHEDULER SERVICE RUNNING--[" + date + " , " + repetition + "]");
//			ArrayList<Task> none = reminderDaoObj.getTaskReminderNone();
//			ArrayList<Task> email = reminderDaoObj.getTaskReminderEmail();
//			ArrayList<Task> urgent = reminderDaoObj.getTaskReminderUrget();
//
//			log.info("Reminder NONE: (" + none.size() + ")");
//			for (Task r : none)
//				log.info(r);
//
//			log.info("Reminder EMAIL: (" + email.size() + ")");
//			for (Task r : email)
//				log.info(r);
//
//			log.info("Reminder URGENT: (" + urgent.size() + ")");
//			for (Task r : urgent)
//				log.info(r);
//		}
//		catch (NamingException e)
//		{
//			throw new RuntimeException(e.getMessage(), e);
//		}
//	}

}
