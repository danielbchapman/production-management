package com.danielbchapman.utility.escaper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FxSqlEscaper extends Application
{
	public static void main(String ... args)
	{
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		final VBox root = new VBox();
		final TextArea from = new TextArea();
		final TextArea to = new TextArea();
		from.setPromptText("Input SQL Text Here");
		
		final Button process = new Button("Escape SQL");
		final CheckBox check = new CheckBox("Reverse?");
		
		HBox controls = new HBox();
		controls.setPadding(new Insets(5, 10, 5, 10));
		controls.setSpacing(10);
		controls.getChildren().addAll(process, check);
		process.onActionProperty().set(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0)
			{
				if(check.isSelected())
					to.setText(FxSqlEscaper.stripEscapes(from.getText()));
				else
					to.setText(FxSqlEscaper.escapeSql(from.getText()));
			}});
		
		root.getChildren().addAll(from, controls, to);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("SQL Escaper | Utility");
		stage.show();
	}

	public static String escapeSql(String input)
	{
		String text = String.copyValueOf(input.toCharArray());
		BufferedReader reader = new BufferedReader(new StringReader(text));
		String line;
		StringBuilder builder = new StringBuilder();
		try
		{
			for(; (line = reader.readLine()) != null;)
			{
				builder.append("\"");
				line = line.replace("\"", "\\\"").replace("\t", "  ");
				builder.append(line);
				builder.append(" \\n\" +\r\n");
			}

			String newString = builder.toString();
			int index = newString.lastIndexOf("+");
			newString = String.copyValueOf(newString.toCharArray(), 0, index) + ";";
			return newString;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
	}
	public static String stripEscapes(String input)
	{
		String text = String.copyValueOf(input.toCharArray());
		BufferedReader reader = new BufferedReader(new StringReader(text));
		String line;
		StringBuilder builder = new StringBuilder();
		try
		{
			for(; (line = reader.readLine()) != null;)
			{
//				char[] ripQuotes = line.replaceFirst("s+/", "").toCharArray();
//				
//				/* Strips "<line>\n " + */
//				if(ripQuotes.length >= 6)	
//					line = new String(Arrays.copyOfRange(ripQuotes, 1, ripQuotes.length - 5));
				
				line = line
						//^[\s]+|[\s\n\r\"]+$
						.replaceAll("^[\\s]+|[\\s\\n\\r\\+]+$", "")
						.replaceAll("^[\\\"]|[\\\"]+$", "")
						.replaceAll("\\\\\"", "\"")
						.replaceAll("  ", "\t")
						.replaceAll("\\n", "");
				builder.append(line);
				builder.append("\n");
			}

			String newString = builder.toString();
//			int index = newString.lastIndexOf("+");
//			newString = String.copyValueOf(newString.toCharArray(), 0, index) + ";";
			return newString;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
