package textToExcel;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class TextToExcelGui extends Application {
	
	private TxtReader excelCreator;
	private String scanPath;

	
	@Override
    public void start(Stage primaryStage){
		BorderPane root = new BorderPane();
		Label title = new Label("Choose Invoice Text File");
		Label spacer = new Label("  ");
		Button openChooser = new Button("Choose Invoice Text File");
		Button startConversion = new Button("Start Conversion");
		Button startOver = new Button("Start Over");
		primaryStage.setTitle("Text to Excel Converter");
		Scene scene = new Scene(root,500, 200);
		HBox top = new HBox();
		HBox center = new HBox();
	    top.setMinHeight(50);
	    top.setStyle("-fx-background-color: #4DBD33; -fx-font-weight:bold;");
	    top.setPadding(new Insets(20,20,20,50));
	    center.setPadding(new Insets(20,20,10,50));
		startOver.setPadding(new Insets(10,20,10,20));
	    center.getChildren().add(openChooser);
		top.getChildren().add(title);
		root.setTop(top);
		root.setCenter(center);
		primaryStage.setScene(scene);
		primaryStage.show();
	
		
		openChooser.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
		    	FileChooser fileChooser = new FileChooser();
		    	fileChooser.setTitle("Choose Text File");
		    	File selectedFile = fileChooser.showOpenDialog(primaryStage);
		    	if(selectedFile != null){
		    		scanPath = selectedFile .getAbsolutePath();
		    		title.setText("Text File selected");
		    		center.getChildren().clear();
		    		Label pathText = new Label("File to convert:\n" + scanPath);
		    		center.getChildren().add(pathText);
		    		HBox bottom = new HBox();
		    		bottom.getChildren().addAll(openChooser, spacer, startConversion);
		    		bottom.setPadding(new Insets(0,0,20,50));
		    		root.setBottom(bottom);
				}
			}
		});
		
		startConversion.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
		    	try {
		    		excelCreator = new TxtReader();
					excelCreator.start(scanPath);
				} catch (IOException e) {}
		    	
				title.setText("Your File has been processed");
			    root.getChildren().clear();
			    root.setTop(top);
			    center.getChildren().clear();
			    center.getChildren().add(startOver);
			    root.setCenter(center);
			}	
		});
		
		startOver.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
		    	title.setText("Choose Text File");
		    	root.getChildren().clear();
		    	center.getChildren().clear();
			    center.getChildren().add(openChooser);
		    	root.setTop(top);
			    root.setCenter(center);
			}	
		});
	
	}
	
	public static void main(String[] args){
		launch(args);
	}
}