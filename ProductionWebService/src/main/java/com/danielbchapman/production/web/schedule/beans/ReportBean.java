package com.danielbchapman.production.web.schedule.beans;

import javax.faces.component.UIForm;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.Tab;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.ReportingBeanRemote;
import com.danielbchapman.production.beans.ReportingBeanRemote.ReportingType;

public class ReportBean
{
	private TabWrapper budget;
	private TabWrapper budgetEstimate;
	private ReportingBeanRemote reporting;
	private Tab[] tabs;
	private TabWrapper weekly;

	/**
	 * @return the budget
	 */
	public TabWrapper getBudget()
	{
		if(budget == null)
			budget = new TabWrapper(ReportingType.BUDGET);
		
		return budget;
	}

	/**
	 * @return the budgetEstimate
	 */
	public TabWrapper getBudgetEstimate()
	{
		if(budgetEstimate == null)
			budgetEstimate = new TabWrapper(ReportingType.BUDGET_ESTIMATE);
		return budgetEstimate;
	}
	
	private ReportingBeanRemote getReportingRemote()
	{
		if(reporting == null)
			reporting = Utility.getObjectFromContext(ReportingBeanRemote.class, Utility.Namespace.PRODUCTION);
		return reporting;
	}
	/**
	 * @return the weekly
	 */
	public TabWrapper getWeekly()
	{
		if(weekly == null)
			weekly = new TabWrapper(ReportingType.WEEKLY);
		
		return weekly;
	}
	
	
	private void populateTab(TabWrapper tab)
	{
		DataTable table = new DataTable();
//		//Filename
//		Column column = new Column();
//		column.setHeaderText("File Name");
//		column.setValueExpression("value", Utility.getValueExpression("#{fileType.name}"));
//		
		//Commands
		table.setId("table");
		/*Add to the "UIForm--the only child of the tab */
		tab.form.getChildren().add(table);
		// Panel wrapper = new Panel();
		// wrapper.getChildren().add()
		// tab.getChildren().
	}

	public class TabWrapper
	{
		private Tab tab;
		private UIForm form;
		private ReportingType type;
		private TabWrapper(ReportingType type)
		{
			tab = new Tab();
			form = new UIForm();
			this.type = type;
			tab.setTitle(type.getName());
			tab.getChildren().add(form);
			populateTab(this);
		}
		
		/**
		 * @return the tab
		 */
		public Tab getTab()
		{
			return tab;
		}
		
		/**
		 * @param tab the tab to set
		 */
		public void setTab(Tab tab)
		{
			this.tab = tab;
		}
		
		
	}
//	private Table createTable() {
//    //Create the Table Dynamically
//     Table table = new Table();
//     table.setId("table1");
//     table.setTitle("Dynamically Created Table"); 
//     table.setPaginateButton(true);
//     table.setPaginationControls(true);
//     
//     // Create the Table Row group dynamically
//     TableRowGroup rowGroup = new TableRowGroup();
//     rowGroup.setId("rowGroup1");  
//     rowGroup.setSourceVar("currentRow");  
//     rowGroup.setValueBinding("sourceData", getApplication().
//           createValueBinding("#{Page1.tripDataProvider}"));
//     rowGroup.setRows(5);
//     
//     // Add the table row group to the table as a child
//     table.getChildren().add(rowGroup);
//     
//     // Create the first table Column
//     TableColumn tableColumn1 = new TableColumn();
//     tableColumn1.setId("tableColumn1");
//     tableColumn1.setHeaderText("Trip ID");
//     
//     // Add the first table Column to the table row group
//     rowGroup.getChildren().add(tableColumn1);
//     
//     // Create the Static text and set its value as TRIPID
//     StaticText staticText1 = new StaticText();
//     staticText1.setValueBinding("text", getApplication().
//         createValueBinding("#{currentRow.value['TRIP.TRIPID']}"));
//     
//     // Add the static text to the table column1
//     tableColumn1.getChildren().add(staticText1);
//     
//     // Create the second table Column
//     TableColumn tableColumn2 = new TableColumn();
//     tableColumn2.setId("tableColumn2"); 
//     tableColumn2.setHeaderText("Trip Date");
//     
//     // Add the second table Column to the table row group
//     rowGroup.getChildren().add(tableColumn2);
//     
//     // Create the Static text and set its value as DEPDATE
//     StaticText staticText2 = new StaticText();
//     staticText2.setValueBinding("text", getApplication().
//                createValueBinding("#{currentRow.value['TRIP.DEPDATE']}"));
//     
//     // Add the Static Text2 to the table column2
//     tableColumn2.getChildren().add(staticText2);
//     
//     // Create the third table Column
//     TableColumn tableColumn3 = new TableColumn();
//     tableColumn3.setId("tableColumn3"); 
//     tableColumn3.setHeaderText("Departure City");
//     
//     // Add the third table Column to the table row group
//     rowGroup.getChildren().add(tableColumn3);
//     
//     // Create the Static text and set its value as DEPDATE
//     StaticText staticText3 = new StaticText();
//     staticText3.setValueBinding("text", getApplication().
//              createValueBinding("#{currentRow.value['TRIP.DEPCITY']}"));
//     
//     // Add the Static Text3 to the table column3
//     tableColumn3.getChildren().add(staticText3);
//
//     return table;
// }	
}
