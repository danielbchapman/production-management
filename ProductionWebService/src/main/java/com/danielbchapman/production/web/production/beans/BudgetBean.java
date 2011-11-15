package com.danielbchapman.production.web.production.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultStreamedContent;

import com.danielbchapman.production.AbstractPrintController;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.BudgetDaoRemote;
import com.danielbchapman.production.beans.OptionsDaoRemote;
import com.danielbchapman.production.entity.Budget;
import com.danielbchapman.production.entity.BudgetAdjustingEntry;
import com.danielbchapman.production.entity.BudgetEntry;
import com.danielbchapman.production.entity.Department;
import com.danielbchapman.production.entity.EntryType;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.web.production.beans.DepartmentBean.DepartmentWrapper;
import com.danielbchapman.production.web.schedule.beans.LoginBean;

@SessionScoped
public class BudgetBean
{
	public final static String REPORTING_DIRCTORY = "budgets";

	private Budget activeBudget;
	private Double actualizedTotal = 0.00;
	private Double allocation = 0.00;

	private BudgetCreationVariables budgetCreationVariables = new BudgetCreationVariables();
	private BudgetDaoRemote budgetDaoObj;
	private BudgetEntryVariables budgetEntryVariables = new BudgetEntryVariables();
	private ArrayList<SelectItem> budgets;
	private Long budgetSelectionTarget;
	private ArrayList<EntryWrapper> entries;

	private Double estimateTotal = 0.00;
	private OptionsDaoRemote optionsDao;
	private AllBudgetPrintController printAll = new AllBudgetPrintController();
	private SingleBudgetPrintController printSingle = new SingleBudgetPrintController();

	public BudgetBean()
	{
	}

	public void doAddEntry(ActionEvent evt)
	{
		if(evt != null)
		{
			if(!Utility.validateLength(budgetEntryVariables.memo, 3, "Please enter a memo."))
				return;

			getBudgetDao().saveEntry(
					budgetEntryVariables.credit == true ? budgetEntryVariables.amount
							: -budgetEntryVariables.amount, activeBudget, budgetEntryVariables.memo,
					budgetEntryVariables.estimated);

			Utility.raiseInfo("Entry Added", "An entry for $" + budgetEntryVariables.amount
					+ " has been entered.");
			entries = null;
			budgetEntryVariables.clear();
			getEntries();
		}
	}

	/**
	 * 
	 * Generating the budgets will manually create a series of 0.00 balance budgets with a standard
	 * nomenclature. This is recommended and then altering further. If you need another budget you
	 * probably need a separate production or department.cription>
	 * 
	 * @param evt
	 *          JSF Spec
	 * 
	 */
	public void doAutomatedCreateBudget(ActionEvent evt)
	{

		DepartmentBean bean = Utility.getBean(DepartmentBean.class);
		SeasonBean season = Utility.getBean(SeasonBean.class);
		ArrayList<DepartmentWrapper> departments = bean.getDepartmentWrappers();
		ArrayList<Budget> budgets = getBudgetDao().getAllBudgets(season.getSeason());

		HashSet<Long> matched = new HashSet<Long>();
		HashSet<Department> toCreate = new HashSet<Department>();
		for(Budget b : budgets)
			matched.add(b.getDepartment().getId());

		for(DepartmentWrapper w : departments)
			if(!matched.contains(w.getDepartment().getId()))
				toCreate.add(w.getDepartment());

		for(Department d : toCreate)
		{
			Budget newBudget = new Budget();
			newBudget.setDate(new Date());
			newBudget.setName(season.getSeason().getName() + " - " + d.getName());
			newBudget.setSeason(season.getSeason());
			newBudget.setStartingBudget(0.00);
			newBudget.setDepartment(d);
			getBudgetDao().saveBudget(newBudget);

			Utility.raiseInfo("Budget Created", "The budget " + newBudget.getName()
					+ " was created with a base amount of $" + newBudget.getStartingBudget());
		}
		refreshAll(evt);
	}

	/**
	 * Create a budget for the selected production <br />
	 * <b>Parameters (request context):</b>
	 * <ul>
	 * <li><b>createBudgetDepartment</b> - the budget department</li>
	 * <li><b>createBudgetAmount</b> - the budget amount (Double)</li>
	 * </ul>
	 * 
	 * @param evt
	 * @see {@link ActionEvent}
	 * 
	 */
	public void doCreateBudget(ActionEvent evt)
	{
		if(evt != null)
		{
			Long deptId = budgetCreationVariables.department;
			Double amount = budgetCreationVariables.amount;

			Department dept = Utility.getBean(DepartmentBean.class).getDepartment(deptId);
			Season season = Utility.getBean(SeasonBean.class).getSeason();

			Budget newBudget = new Budget();
			newBudget.setDate(new Date());
			newBudget.setName(season.getName() + " - " + dept.getName());
			newBudget.setSeason(season);
			newBudget.setDepartment(dept);
			newBudget.setStartingBudget(amount == null ? 0.00 : amount);

			getBudgetDao().saveBudget(newBudget);
			refreshAll(evt);

			Utility.raiseInfo("Budget Created", "The budget " + newBudget.getName()
					+ " was created with a base amount of $" + newBudget.getStartingBudget());
		}
	}

	/**
	 * Deletes the active budget
	 * 
	 * @param evt
	 *          JSF Spec
	 * 
	 */
	public void doDeleteActive(ActionEvent evt)
	{
		if(activeBudget == null)
		{
			Utility.raiseWarning("Unable to Delete", "There is no active budget available");
			return;
		}

		if(!((LoginBean) Utility.getBean("loginBean")).isAdmin())
		{
			Utility.raiseFatal("Security Violation", "Only administrators can access budgets");
			Utility.redirect("logout.jsp");
		}

		getBudgetDao().deleteBudget(activeBudget);
		refreshAll(evt);
	}

	/**
	 * Saves all changes to the current budget object and refreshes the data to reflect this. This is
	 * available to administrator only.
	 * 
	 * @param evt
	 *          JSF Spec
	 * 
	 */
	public void doEditBudget(ActionEvent evt)
	{
		if(activeBudget == null)
		{
			Utility.raiseWarning("Unable to edit", "There is no active budget available");
			return;
		}

		if(!Utility.getBean(LoginBean.class).isAdmin())
		{
			Utility.raiseFatal("Security Violation", "Only administrators can access budgets");
			return;
		}

		getBudgetDao().saveBudget(activeBudget);
		Utility.raiseInfo("Budget Saved", "The budget " + activeBudget.getName() + " was updated.");
		refreshAll(evt);
	}

	public Budget getActiveBudget()
	{
		return activeBudget;
	}

	public Double getActualizedTotal()
	{
		return actualizedTotal;
	}

	public Double getAllocation()
	{
		return allocation;
	}

	public BudgetCreationVariables getBudgetCreationVariables()
	{
		return budgetCreationVariables;
	}

	public BudgetEntryVariables getBudgetEntryVariables()
	{
		return budgetEntryVariables;
	}

	public DefaultStreamedContent getBudgetListing()
	{
		Utility.raiseInfo("Reporting Disabled", "Reporting is temporarily disabled.");
		return null;
	}

	public ArrayList<SelectItem> getBudgets()
	{
		if(budgets == null)
		{
			SeasonBean season = Utility.getBean(SeasonBean.class);
			budgets = new ArrayList<SelectItem>();
			for(Budget b : getBudgetDao().getAllBudgets(season.getSeason()))
				budgets.add(new SelectItem(b.getId(), b.getName()));
		}
		return budgets;
	}

	public Long getBudgetSelectionTarget()
	{
		return budgetSelectionTarget;
	}

	/**
	 * This is simply a test of the charting.
	 * 
	 * @return a list of values to create a useful chart
	 * 
	 */
	public ArrayList<ChartField> getBugetAllocationChart()
	{
		ArrayList<ChartField> fields = new ArrayList<ChartField>();
		Double remaining = allocation + estimateTotal + actualizedTotal;
		fields.add(new ChartField(remaining >= 0 ? "Remaining" : "Overages", remaining >= 0 ? remaining
				: -remaining));
		fields.add(new ChartField("Estimated", -estimateTotal));
		fields.add(new ChartField("Actualized", -actualizedTotal));
		return fields;
	}

	public ArrayList<EntryWrapper> getEntries()
	{
		if(entries == null)
		{
			ArrayList<BudgetEntry> data = getBudgetDao().getBudgetEntries(activeBudget);
			entries = new ArrayList<EntryWrapper>();

			estimateTotal = 0.00;
			actualizedTotal = 0.00;
			allocation = activeBudget.getStartingBudget();

			for(BudgetEntry e : data)
			{
				if(e.isEstimated())
					estimateTotal += e.getCalculatedAmount();
				else
					actualizedTotal += e.getCalculatedAmount();

				entries.add(new EntryWrapper(e));
			}
		}

		return entries;
	}

	public Double getEstimateTotal()
	{
		return estimateTotal;
	}

	public DefaultStreamedContent getMasterListing()
	{
		Utility.raiseInfo("Reporting Disabled", "Reporting is temporarily disabled.");
		return null;
	}

	/**
	 * @return the printAll
	 */
	public AllBudgetPrintController getPrintAll()
	{
		return printAll;
	}

	/**
	 * @return the printSingle
	 */
	public SingleBudgetPrintController getPrintSingle()
	{
		return printSingle;
	}

	public boolean isCanHasABudget()
	{
		return activeBudget != null;
	}

	/**
	 * Refresh all pertinent data-structures
	 * 
	 * @param evt
	 *          The signature for JSF--is not used, call via null to refresh outside the framework
	 */
	public void refreshAll(ActionEvent evt)
	{
		budgetCreationVariables = new BudgetCreationVariables();
		activeBudget = null;
		actualizedTotal = 0.00;
		allocation = 0.00;
		estimateTotal = 0.00;
		entries = null;
		budgets = null;
		budgetSelectionTarget = null;
	}

	public void selectBudget(ActionEvent evt)
	{
		if(budgetSelectionTarget != null)
		{
			activeBudget = getBudgetDao().getBudget(budgetSelectionTarget);
			entries = null;
			getEntries();
		}
		else
		{
			Utility.raiseWarning("Selection Failed",
					"The system was not able to select the budget, please try again");
			refreshAll(evt);
		}
	}

	public void setBudgetSelectionTarget(Long budgetSelectionTarget)
	{
		this.budgetSelectionTarget = budgetSelectionTarget;
	}

	private BudgetDaoRemote getBudgetDao()
	{
		if(budgetDaoObj == null)
			budgetDaoObj = Utility.getObjectFromContext(BudgetDaoRemote.class,
					Utility.Namespace.PRODUCTION);
		return budgetDaoObj;
	}

	private OptionsDaoRemote getOptionsDao()
	{
		if(optionsDao == null)
			optionsDao = Utility.getObjectFromContext(OptionsDaoRemote.class,
					Utility.Namespace.PRODUCTION);
		return optionsDao;
	}

	public class AllBudgetPrintController extends AbstractPrintController
	{

		public AllBudgetPrintController()
		{
			super(REPORTING_DIRCTORY + File.separator + "multi", "sub");
		}

		@Override
		protected Collection<?> getData()
		{
			Season selection = Utility.getBean(SeasonBean.class).getSeason();
			return getBudgetDao().getAllBudgets(selection);
		}

		@Override
		protected Map<String, Object> getParameters()
		{
			return null;
		}

		@Override
		protected String getReportName()
		{
			return "Season Budgets";
		}
	}

	public class BudgetCreationVariables
	{
		private double amount = 0.00;
		private Long department;

		public double getAmount()
		{
			return amount;
		}

		public Long getDepartment()
		{
			return department;
		}

		public void setAmount(double amount)
		{
			this.amount = amount;
		}

		public void setDepartment(Long department)
		{
			this.department = department;
		}
	}

	public class BudgetEntryVariables
	{
		private Double amount = 0.00;
		private boolean credit = false;;
		private boolean estimated = true;
		private String memo = "";

		public void clear()
		{
			memo = "";
			credit = false;
			estimated = true;
			amount = 0.00;
		}

		public Double getAmount()
		{
			return amount;
		}

		public String getMemo()
		{
			return memo;
		}

		public boolean isCredit()
		{
			return credit;
		}

		public boolean isEstimated()
		{
			return estimated;
		}

		public void setAmount(Double amount)
		{
			this.amount = amount;
		}

		public void setCredit(boolean credit)
		{
			this.credit = credit;
		}

		public void setEstimated(boolean estimated)
		{
			this.estimated = estimated;
		}

		public void setMemo(String memo)
		{
			this.memo = memo;
		}
	}

	public class ChartField
	{
		private Double amount;
		private String label;

		public ChartField(String label, Double amount)
		{
			this.label = label;
			this.amount = amount;
		}

		public Double getAmount()
		{
			return amount;
		}

		public String getLabel()
		{
			return label;
		}

		public void setAmount(Double amount)
		{
			this.amount = amount;
		}

		public void setLabel(String label)
		{
			this.label = label;
		}
	}

	public class EntryWrapper
	{
		private Double adjustmentAmount = 0.00;
		private String adjustmentMemo;
		private boolean credit;
		private ArrayList<AdjustmentWrapper> adjustments;
		private BudgetEntry entry;

		public EntryWrapper(BudgetEntry entry)
		{
			this.entry = entry;
		}

		/**
		 * Change this entry from an estimate to a full value. (Set estimated false).
		 * 
		 * @param evt
		 */
		public void doActualize(ActionEvent evt)
		{
			entry.setEstimated(false);
			getBudgetDao().saveEntry(entry);
			entries = null;
			getEntries();
		}

		/**
		 * Add an adjustment based on the adjustment fields in the wrapper.
		 * 
		 * @param evt
		 */
		public void doAddAdjustment(ActionEvent evt)
		{
			double amt = credit ? adjustmentAmount : -adjustmentAmount;

			if(!Utility.validateLength(adjustmentMemo, 3, "Please enter a memo."))
				return;

			getBudgetDao().saveAdjustingEntry(amt, entry, adjustmentMemo, EntryType.PENDING);

			Utility.raiseInfo("Adjustment Added", "Adjustment of " + amt + " recorded.");
			adjustmentMemo = null;
			adjustmentAmount = 0.00;
			credit = false;
			adjustments = null;

			entries = null;
			getEntries();
		}

		/**
		 * Cancel this entry by adding an adjusting entry for the composite total for this entry. If the
		 * entry is an estimate delete the entry from the database.
		 * 
		 * @param evt
		 */
		public void doCancelEntry(ActionEvent evt)
		{
			getBudgetDao().cancelEntry(entry);

			if(entry.isEstimated())
				Utility.raiseInfo("Entry Canceled", "The estimate has been deleted");
			else
				Utility.raiseInfo("Entry Canceled", "An adjusting entry has been recorded.");

			entries = null;
			getEntries();
		}

		/**
		 * Move this entry to confirmed status and refresh the list.
		 * 
		 * @param evt
		 */
		public void doConfirmEntry(ActionEvent evt)
		{
			entry.setConfirmed(true);
			getBudgetDao().saveEntry(entry);

			Utility.raiseInfo("Entry Confirmed", entry.getCalculatedAmount() + ": " + entry.getNote());
			entries = null;
		}

		/**
		 * @return the adjustmentAmount
		 */
		public double getAdjustmentAmount()
		{
			return adjustmentAmount;
		}

		/**
		 * @return the adjustmentMemo
		 */
		public String getAdjustmentMemo()
		{
			return adjustmentMemo;
		}

		public ArrayList<AdjustmentWrapper> getAdjustments()
		{
			if(adjustments == null)
			{
				adjustments = new ArrayList<AdjustmentWrapper>();

				for(BudgetAdjustingEntry a : getBudgetDao().getAdjustingEntries(entry))
					adjustments.add(new AdjustmentWrapper(a));
			}
			return adjustments;
		}

		public BudgetEntry getEntry()
		{
			return entry;
		}

		public boolean isCanHasAdjustingEntries()
		{
			if(getAdjustments().size() == 0)
				return false;

			return true;
		}

		/**
		 * @return the credit
		 */
		public boolean isCredit()
		{
			return credit;
		}

		/**
		 * @param adjustmentAmount
		 *          the adjustmentAmount to set
		 */
		public void setAdjustmentAmount(double adjustmentAmount)
		{
			this.adjustmentAmount = adjustmentAmount;
		}

		/**
		 * @param adjustmentMemo
		 *          the adjustmentMemo to set
		 */
		public void setAdjustmentMemo(String adjustmentMemo)
		{
			this.adjustmentMemo = adjustmentMemo;
		}

		public void setAdjustments(ArrayList<AdjustmentWrapper> adjustments)
		{
			this.adjustments = adjustments;
		}

		/**
		 * @param credit
		 *          the credit to set
		 */
		public void setCredit(boolean credit)
		{
			this.credit = credit;
		}

		public void setEntry(BudgetEntry entry)
		{
			this.entry = entry;
		}

		public class AdjustmentWrapper
		{
			private BudgetAdjustingEntry adjustment;

			public AdjustmentWrapper(BudgetAdjustingEntry adjustment)
			{
				this.adjustment = adjustment;
			}

			/**
			 * Add an additional (negative) adjusting entry to this entry so that the amounts cancel each
			 * other.
			 * 
			 * @param evt
			 */
			public void doCancelAdjustment(ActionEvent evt)
			{
				getBudgetDao().cancelEntry(adjustment);
				adjustments = null;
				entries = null;
				getEntries();
			}

			/**
			 * Set the adjustment type to Confirmed and refresh the structure.
			 * 
			 * @param evt
			 */
			public void doConfirmAdjustment(ActionEvent evt)
			{
				adjustment.setEntryType(EntryType.CONFIRMED);
				getBudgetDao().saveAdjustingEntry(adjustment);
				/* Fire Refresh */
				adjustments = null;
			}

			public BudgetAdjustingEntry getAdjustment()
			{
				return adjustment;
			}

			public void setAdjustment(BudgetAdjustingEntry adjustment)
			{
				this.adjustment = adjustment;
			}
		}
	}

	public class SingleBudgetPrintController extends AbstractPrintController
	{
		public SingleBudgetPrintController()
		{
			super(REPORTING_DIRCTORY + File.separator + "single", "sub");
		}

		@Override
		protected Collection<?> getData()
		{
			return getActiveBudget().getEntries();
		}

		@Override
		protected Map<String, Object> getParameters()
		{
			return null;
		}

		@Override
		protected String getReportName()
		{
			return "Single Budget Report";
		}

	}
}
