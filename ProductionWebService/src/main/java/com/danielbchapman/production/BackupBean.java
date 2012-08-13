package com.danielbchapman.production;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import lombok.Data;

import com.danielbchapman.production.Utility.Namespace;
import com.danielbchapman.production.beans.BackupBeanRemote;
import com.danielbchapman.production.entity.*;

@Data
public class BackupBean implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String tableData;
	private String tableName;
	
	@SuppressWarnings("unchecked")
	public void createBackup(ActionEvent evt)
	{
		//@formatter:off
		tableData = 
				Utility.getObjectFromContext(BackupBeanRemote.class, Namespace.PRODUCTION)
				.backupTables(
						//Top Level Abstractions
						Season.class,
						Options.class,
						Department.class,
//						Employee.class, //Unused
						
						//Calendar
						PerformanceSchedule.class,
						Week.class,
						Day.class,
						Event.class,
						Performance.class,
						
						//Venues
						City.class,
						Venue.class,
						VenueLog.class,
						PerformanceAdvance.class,
						Hotel.class,
						Hospital.class,
						PointOfInterest.class,
						
						//Budgets
						Budget.class,
						BudgetEntry.class,
						BudgetAdjustingEntry.class,
						PettyCash.class,
						PettyCashEntry.class,
						Reimbursement.class,						
						
						//Contacts
						ContactGroup.class,
						Contact.class,
						LinkedContact.class,
						
						//Crap
						Vendor.class,
						Task.class,
						TaskStatusUpdate.class
						);
		//@formatter:on
	}
}
