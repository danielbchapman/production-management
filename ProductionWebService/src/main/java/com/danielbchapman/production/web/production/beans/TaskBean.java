package com.danielbchapman.production.web.production.beans;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.TaskDaoRemote;
import com.danielbchapman.production.entity.Priority;
import com.danielbchapman.production.entity.Task;
import com.danielbchapman.production.entity.TaskStatusUpdate;

/**
 * A simple web bean (session) that provides backs the todolist
 * 
 */
public class TaskBean
{
	public final static String NOTHING = "NOTHING";

	private ArrayList<SelectItem> priorities;
	private DefaultTreeNode rootNode;
	private TaskDaoRemote taskDao;
	private Task loadedTask = new Task();

	public void closeItem(ActionEvent evt)
	{
		if(evt == null)
			return;

		Long id = (Long) evt.getComponent().getAttributes().get("reminderId");
		Task reminder = getTaskDao().getItem(id);
		reminder.setComplete(true);
		getTaskDao().saveItem(reminder, Utility.getUserPrinciple());
		Utility.raiseInfo("Item Closed", "Task #" + id
				+ " was closed and is available in inactive items.");
		refreshItems(null);
	}

	public Task getLoadedTask()
	{
		return loadedTask;
	}

	public ArrayList<SelectItem> getPriorities()
	{
		if(priorities == null)
		{
			priorities = new ArrayList<SelectItem>();

			for(Priority p : Priority.values())
				priorities.add(new SelectItem(p));

		}
		return priorities;
	}

	public DefaultTreeNode getRootNode()
	{
		if(rootNode == null)
		{
			rootNode = new DefaultTreeNode("menu", "All Tasks", null);
			System.out.println("Root->" + rootNode.getType());

			DefaultTreeNode open = new DefaultTreeNode("menu", "All Open Tasks", rootNode);

			SeasonBean pb = (SeasonBean) Utility.getBean("seasonBean");
			ArrayList<Task> items = getTaskDao().getActiveTopLevelItems(pb.getSeason());

			for(Task t : items)
			{
				String type = "open";
				if(t.getComplete())
					type = "completed";

				DefaultTreeNode parent = new DefaultTreeNode(type, t, open);
				populateChildTree(parent, t);
			}

			DefaultTreeNode closed = new DefaultTreeNode("menu", "All Closed Tasks", rootNode);
			items = getTaskDao().getClosedTopLevelItems(pb.getSeason());

			for(Task t : items)
			{
				String type = "open";
				if(t.getComplete())
					type = "completed";
				DefaultTreeNode parent = new DefaultTreeNode(type, t, closed);
				populateChildTree(parent, t);
			}
		}
		return rootNode;
	}

	public boolean isCanHasTaskToEditKThxBye()
	{
		return loadedTask.getId() != null;
	}

	public void onTaskNodeSelect(NodeSelectEvent evt)
	{
		if(evt.getTreeNode().getData() instanceof Task)
		{
			Task task = (Task) evt.getTreeNode().getData();
			Utility.raiseInfo("Node Selected", "The node " + task.getLabel() + " has been selected.");
			loadedTask = getTaskDao().getItem(task.getId());
		}
	}

	public void openItem(ActionEvent evt)
	{
		if(evt == null)
			return;

		Long id = (Long) evt.getComponent().getAttributes().get("reminderId");
		Task reminder = getTaskDao().getItem(id);
		reminder.setComplete(false);
		getTaskDao().saveItem(reminder, Utility.getUserPrinciple());
		Utility.raiseInfo("Item Opened", "Task #" + id
				+ " was opened and is available in active items.");
		refreshItems(null);
	}

	/**
	 * 
	 * @param evt
	 *          used so the UI can call a refresh explicitly-- null is not checked so this can be used
	 *          without creating events.
	 * 
	 */
	public void refreshItems(ActionEvent evt)
	{
		rootNode = null;
	}

	public void saveEdit(ActionEvent evt)
	{
		Utility.raiseError("NOT IMPLEMENTED", "Failboat Sailed....");
	}

	public void saveNewItem(ActionEvent evt)
	{
		if(evt == null)
			return;

		String notes = (String) evt.getComponent().getAttributes().get("newNotes");
		Date date = (Date) evt.getComponent().getAttributes().get("newDate");
		String priority = (String) evt.getComponent().getAttributes().get("newPriority");
		String department = (String) evt.getComponent().getAttributes().get("newDepartment");
		String label = (String) evt.getComponent().getAttributes().get("newLabel");

		if(notes == null || notes.trim().length() < 3 || label == null || label.trim().length() < 3)
		{
			Utility.raiseError("Error Saving", "You must enter descriptive notes and a label");
			return;
		}

		Task newReminder = new Task();
		newReminder.setDepartment(department);
		newReminder.setDescription(notes);
		newReminder.setComplete(false);
		newReminder.setLabel(label);
		newReminder.setPriority(Priority.parseValue(priority));
		newReminder.setTargetDate(date);
		newReminder.setSeason(((SeasonBean) Utility.getBean("seasonBean")).getSeason());

		getTaskDao().saveItem(newReminder, Utility.getUserPrinciple());

		Utility.raiseInfo("Status Saved", notes);

		refreshItems(evt);
	}

	public void setLoadedTask(Task loadedTask)
	{
		this.loadedTask = loadedTask;
	}

	public void updateStatus(ActionEvent evt)
	{
		if(evt == null)
			return;

		Long id = (Long) evt.getComponent().getAttributes().get("noteId");
		String notes = (String) evt.getComponent().getAttributes().get("noteValue");
		if(notes == null || notes.trim().length() < 3)
		{
			Utility.raiseError("Error Saving", "You must enter a descriptive status.");
			return;
		}
		getTaskDao().saveStatusUpdate(getTaskDao().getItem(id), notes, Utility.getUserPrinciple());
		Utility.raiseInfo("Status Saved", notes);

		refreshItems(null);
	}

	private TaskDaoRemote getTaskDao()
	{
		if(taskDao == null)
			taskDao = Utility.getObjectFromContext(TaskDaoRemote.class, Utility.Namespace.PRODUCTION);

		return taskDao;
	}

	private void populateChildTree(DefaultTreeNode parent, Task sub)
	{
		if(sub == null)
			return;

		ArrayList<Task> children = getTaskDao().getSubTasks(sub);
		for(Task t : children)
		{
			String type = "open";
			if(t.getComplete())
				type = "completed";

			DefaultTreeNode child = new DefaultTreeNode(type, t, parent);
			populateChildTree(child, t);
		}

	}

	/**
	 * A simple wrapper so that the updates/etc can be part of a single element via a direct
	 * reference.
	 * 
	 *************************************************************************** 
	 * @author Daniel B. Chapman
	 * @since May 11, 2011
	 * @link http://www.theactingcompany.org
	 *************************************************************************** 
	 */
	public class TaskWrapper
	{

		private ArrayList<TaskStatusUpdate> history;
		private Task task;

		public TaskWrapper()
		{
			task = new Task();// JSF NPE Dodge
		}

		public TaskWrapper(Task task)
		{

			this.task = task;
		}

		public ArrayList<TaskStatusUpdate> getHistory()
		{
			if(history == null)
			{
				if(task == null)
					return new ArrayList<TaskStatusUpdate>();// NPE Dodge--per request
				else
					history = getTaskDao().getHistoryForItem(task);
			}
			return history;
		}

		public Task getTask()
		{
			return task;
		}
	}
}
