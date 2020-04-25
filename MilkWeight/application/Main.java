/**
 * Main.java created by zkeith on personal laptop in MilkWeight
 *
 * Author: 		Zander Keith (zkeith@wisc.edu)
 * Date: 		Apr 14, 2020
 * 
 * Course: 		CS400
 * Semester: 	Spring 2020
 * Lecture: 	001
 * 
 * IDE: 		Eclipse IDE for Java Developers
 * Version: 	2020-03
 * 
 * OS:			Windows 10
 * 
 * List Collaborators: Name, email@wisc.edu, lecture number
 * 
 * Other Credits: describe other source (websites or people)
 * 
 * Known Bugs: describe known unresolved bugs here
 */
package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main - Launches program window, runs GUI, and holds Farm object data
 * 
 * @author zkeith
 *
 */
public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	//
	// FARM
	//

	static ArrayList<Farm> farms = new ArrayList<Farm>();

	//
	// GUI STUFF
	//

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 640;
	private static final String APP_TITLE = "MilkWeight";

	private BorderPane root = new BorderPane();
	private BorderPane reportPanel = new BorderPane();
	private BorderPane notImplementedPanel = new BorderPane();
	private BorderPane homePanel = new BorderPane();
	private VBox chartGroup = new VBox();
	private VBox inputs = new VBox();
	
	// Chart stuff
	private CategoryAxis dateAxis = new CategoryAxis();
	private NumberAxis percentAxis = new NumberAxis();
	private BarChart<String,Number> chart = new BarChart<String,Number>(dateAxis, percentAxis);
	
	
	// User Input stuff
	private String userFarmChoice = "";
	private String userDateChoice ="";
	private String userMonthChoice = "";
	private String userYearChoice = "";
	
	// Test Report to the user
	private ArrayList<String> textReport = null;
	
	//Repeatedly updated combo boxes
	private ComboBox<String> farmComboBox = new ComboBox<String>();
	private ComboBox<String> monthComboBox = new ComboBox<String>();
	private ComboBox<String> yearComboBox = new ComboBox<String>();
	
	/**
	 * Sets up all GUI elements.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Labels
		Label fileLabel = new Label("File");
		Label reportLabel = new Label("Report");
		Label monthLabel = new Label("Month: ");
		Label yearLabel = new Label("Year: ");
		Label farmIDLabel = new Label("Farm ID: ");
		Label farmReportLabel = new Label("Farm Report");
		Label chartLabel = new Label("Farm no., Date + Report");
		Label totalWeightLabel = new Label("Total Weight Sold: + weight + lb");
		Label percentWeightLabel = new Label("Percent of total + something%");

		// Text Fields
		TextField farmIDField = new TextField("");
		TextField yearField = new TextField("");
		TextField inputText = new TextField();

		// Initialize Drop Downs
		ObservableList<String> monthItems = FXCollections.observableArrayList("January",
				"February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December", "ALL");
		monthComboBox.setItems(monthItems);
		monthComboBox.setOnAction((event) -> {
			this.userMonthChoice = monthComboBox.getValue();
			System.out.println("User picked the month : "+ userMonthChoice);
		});
		ObservableList<String> yearItems = FXCollections.observableArrayList("No Data Yet");
		yearComboBox.setItems(yearItems);
		
		yearComboBox.setOnAction((event) -> {
			this.userYearChoice = yearComboBox.getValue();
			System.out.println("User picked the year : "+ userYearChoice);
		});

		ObservableList<String> farmItems = FXCollections.observableArrayList("No Data Yet");
		farmComboBox.setItems(farmItems);
		farmComboBox.setOnAction((event) -> {
			this.userFarmChoice = farmComboBox.getValue();
			System.out.println("User picked farm : "+ userFarmChoice);
		});

		// Buttons
		Button homeButton = new Button("Home");
		homeButton.setOnAction(e -> homeButtonAction());

		// used in the event a user wants to input a file or farm
		Button inputSubmit = new Button("Submit");

		
		inputSubmit.setOnAction((event) -> this.importFileButtonAction(inputText));
		
		Button importFileButton = new Button("Import File");
		importFileButton.setOnAction(new InputHandler(importFileButton, 
				"Enter file name. Example : C:\\Users\\<User>\\eclipse-workspace\\CS400ATeam\\MilkWeight\\csv\\small\\2019-1.csv",
				inputSubmit, inputText));

		Button exportAsCSVButton = new Button("Export as CSV");
		exportAsCSVButton.setOnAction(e -> exportAsCSVButtonAction());

		Button addNewFarmButton = new Button("Add new farm");
		addNewFarmButton.setOnAction(e -> this.addNewFarmButtonAction());


		Button addNewMilkDataButton = new Button("Add new milk data");
		addNewMilkDataButton.setOnAction(e -> {addNewMilkDataButtonAction();
		this.updateComboBoxes(farmComboBox, yearComboBox);
		});

		Button editMilkDataButton = new Button("Edit milk data");
		editMilkDataButton.setOnAction(e -> editMilkDataButtonAction());

		Button farmReportButton = new Button("Farm Report");
		farmReportButton.setOnAction(e -> {farmReportButtonAction();
		this.updateComboBoxes(farmComboBox, yearComboBox);
		});

		Button annualReportButton = new Button("Annual Report");
		annualReportButton.setOnAction(e -> annualReportButtonAction());

		Button monthlyReportButton = new Button("Monthly Report");
		monthlyReportButton.setOnAction(e -> monthlyReportButtonAction());

		Button dateRangeReportButton = new Button("Date Range Report");
		dateRangeReportButton.setOnAction(e -> dateRangeReportButtonAction());

		
		Button reportSubmitButton = new Button("View Report");		
		reportSubmitButton.setOnAction(e -> this.reportSubmitButtonAction());

		// Create left panel of buttons (dependencies first)
		VBox fileOptionGroup = new VBox();
		fileOptionGroup.getChildren().addAll(fileLabel, importFileButton, exportAsCSVButton,
				addNewFarmButton, addNewMilkDataButton, editMilkDataButton);

		VBox reportOptionGroup = new VBox(reportLabel, farmReportButton, annualReportButton,
				monthlyReportButton, dateRangeReportButton);

		ImageView cowImage = new ImageView();
		cowImage.setImage(new Image("cow.jpg"));
		cowImage.setFitHeight(WINDOW_HEIGHT / 8);
		cowImage.setPreserveRatio(true);

		VBox leftOptionPanel = new VBox();
		leftOptionPanel.setSpacing(20); // TODO no magic numbers in spacing. define constants
										// elsewhere
		leftOptionPanel.setPadding(new Insets(15, 15, 15, 15)); // TODO verify insets are allowed
																// (javafx.geometry)
		leftOptionPanel.setStyle("-fx-background-color: #00FF00;");
		leftOptionPanel.getChildren().addAll(homeButton, fileOptionGroup, reportOptionGroup,
				cowImage);

		// Create report panel (dependencies first)

		// Create Chart group
		// this image is just a placeholder for the actual chart.
		ImageView placeholdImage = new ImageView();
		placeholdImage.setImage(new Image("basically.png"));
		placeholdImage.setFitHeight(WINDOW_HEIGHT / 2);
		placeholdImage.setPreserveRatio(true);

		chartGroup.setPadding(new Insets(15, 15, 15, 15));
		chartGroup.setStyle("-fx-border-color: black");
		chartGroup.getChildren().setAll(chart, chartLabel, totalWeightLabel,
				percentWeightLabel);

		// Create ID/Year/Submit group
		HBox farmIDGroup = new HBox();
		farmIDGroup.getChildren().addAll(farmIDLabel, farmComboBox);

		HBox yearGroup = new HBox();
		yearGroup.getChildren().addAll(yearLabel, yearComboBox);

		HBox monthGroup = new HBox();
		monthGroup.getChildren().addAll(monthLabel, monthComboBox);

		HBox IDYearSubmitGroup = new HBox();
		IDYearSubmitGroup.getChildren().addAll(farmIDGroup, yearGroup, monthGroup, reportSubmitButton);
		IDYearSubmitGroup.setSpacing(15.0);

		reportPanel.setBottom(IDYearSubmitGroup);
		reportPanel.setCenter(chartGroup);
		reportPanel.setPadding(new Insets(15, 15, 15, 15));

		// Home panel with instructions
		homePanel.setTop(new Label(
				"Welcome! \n How to use: \n Go to import CSV file and input path to file you want to read, then click submit \n Then go to Farm Report to see data. There you can select a farm ID and year to see data."));
		homePanel.setMinWidth(800);

		// Not implemented panel
		notImplementedPanel.setTop(new Label("NOT IMPLEMENTED"));
		notImplementedPanel.setCenter(placeholdImage);

		// Add panels to root pane
		root.setLeft(leftOptionPanel);
		root.setCenter(homePanel);
		Scene mainScene = new Scene(root);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	
	private void homeButtonAction() {
		root.setCenter(homePanel);
		System.out.println("Home Button Pressed");
	}

	private void importFileButtonAction(TextField inputText) {
		System.out.println("User entered: \"" + inputText.getText() + "\"");
		// This is super cringe rn sorry team, just here to make it work and no further
		// Shouldn't take long to move though, just this one line is what's needed to get the input
		try {
			Main.farms = Report.parseFile(inputText.getText(), farms);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("File Read");
			alert.setHeaderText(null);
			alert.setContentText("Successful! Your file has been recorded.");
			alert.showAndWait();
		}
		catch(Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("File Error");
			alert.setHeaderText(null);
			alert.setContentText("We are sorry. We were unable to read the file. Please check your file path and try again.");
			alert.showAndWait();
		}			
		inputText.clear();
		this.updateComboBoxes(farmComboBox, yearComboBox);
	}
	

	private void exportAsCSVButtonAction() {
		root.setCenter(notImplementedPanel);
		System.out.println("Export as CSV File Button Pressed");
	}

	private void addNewFarmButtonAction() {
		System.out.println("User wants to enter a new farm!");
		VBox newFarmBox = new VBox();
		TextField farmName = new TextField("Ex: Farm 999");
		farmName.setOnMouseClicked(clicked->farmName.clear());
		Button addFarm = new Button("Add");
		newFarmBox.getChildren().addAll(new Label("Enter the name of the farm: "), farmName, addFarm);
		root.setCenter(newFarmBox);
		addFarm.setOnAction(userClickedAdd -> {
			boolean dupe = false;
			this.userFarmChoice = farmName.getText();
			System.out.println("The user wants to add a new farm named " + this.userFarmChoice);
			for (Farm f : farms) {
				if (f.getFarmID().equals(this.userFarmChoice)) {
					Alert alert = new Alert(AlertType.ERROR);
					System.out.println("user is trying to enter a duplicate farm, warnning message popped");
					alert.setTitle("This farm already exists!");
					alert.setHeaderText("Farm ID entered : " + this.userFarmChoice);
					alert.setContentText("Farm ID you entered already exists in our system."+System.lineSeparator()+"Please use other options to add data.");
					alert.showAndWait();
					dupe = true;
				}
			}
			if (!dupe) {
				Farm newFarm = new Farm(this.userFarmChoice);
				farms.add(newFarm);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("New Farm Added");
				alert.setHeaderText(null);
				alert.setContentText("Successful! New farm ID "+this.userFarmChoice+" added.");
				alert.showAndWait();
				System.out.println("A new farm named "+ this.userFarmChoice+" added");
			}			
		});			
	}
	

	private void addNewMilkDataButtonAction() {
		System.out.println("Add new milk data Button Pressed");
		root.setCenter(new Label("No farm availble in the system."));
		ComboBox<String> farmIDs = new ComboBox<String>();
		if (farms.size()==0) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("There is no farm in our system.");
			alert.setHeaderText(null);
			alert.setContentText("Please add a new farm first.");
			alert.showAndWait().ifPresent(response -> {
			     if (response == ButtonType.OK) {
			         this.addNewFarmButtonAction();
			         return;
			     }
			 });
			
		}
		else {				
			ObservableList<String> newFarms = FXCollections.observableArrayList();
			farms.forEach(e -> newFarms.add(e.getFarmID()));
			farmIDs.setItems(newFarms);
			
		farmIDs.setOnAction(e-> {
			this.userFarmChoice = farmIDs.getValue();
		    System.out.println("User wants to enter data for Farm ID " +  userFarmChoice);
			
		});
		
		HBox farmBar = new HBox();
		farmBar.getChildren().add(new Label("Farm ID : "));
		farmBar.getChildren().add(farmIDs);
		HBox yearBar = new HBox();
		yearBar.getChildren().add(new Label("Year : "));
		TextField yearText = new TextField("Ex: 2019");
		yearBar.getChildren().add(yearText);
		yearText.setOnMouseClicked(e->yearText.clear());
		HBox monthBar = new HBox();
		monthBar.getChildren().add(new Label("Month : "));
		ComboBox<String> months = new ComboBox<String>();
		ObservableList<String> text = FXCollections.observableArrayList("January",
						"February", "March", "April", "May", "June", "July", "August", "September",
						"October", "November", "December");
		months.setItems(text);
		monthBar.getChildren().add(months);
		months.setOnAction(e-> {
			this.userMonthChoice = months.getValue();
			System.out.println("User selected month of " + this.userMonthChoice);		
		});
		HBox milkBar = new HBox();
		milkBar.getChildren().add(new Label ("Amount of Milk Sold (lb) :"));
		TextField milkText = new TextField("Ex: 293489");
		milkBar.getChildren().add(milkText);
		milkText.setOnMouseClicked(e->milkText.clear());
		HBox dateBar = new HBox();
		ComboBox<String> dates = new ComboBox<String>();
		ObservableList<String> numbers = FXCollections.observableArrayList();
		for (int i = 1; i < 32; i ++ ) {
			numbers.add(Integer.toString(i));
		}
		dates.setItems(numbers);
		dateBar.getChildren().addAll(new Label("Day: "),dates);
		
		dates.setOnAction(e-> {
			this.userDateChoice=dates.getValue();
			System.out.println("User chose the date " + userDateChoice );
		});
		VBox mainPannel = new VBox();
		Button record = new Button("Record");
		mainPannel.getChildren().addAll(farmBar,monthBar,dateBar,yearBar,milkBar,record);		
		root.setCenter(mainPannel);

		record.setOnAction(e -> {
			int weight = 0;
			int year = 0;

			boolean ready = true;
			System.out.println("User wants to record a new data");
			try {

				year = Integer.parseInt(yearText.getText());
			}
			catch (Exception exp) {
				ready = false;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Please check your year input.");
				alert.setHeaderText("You Entered: " + yearText.getText());
				alert.setContentText("Year can only be an integer value!");
				alert.showAndWait();
			}
			try {
				weight = Integer.parseInt(milkText.getText());
			}
			catch (Exception exp) {
				ready = false;
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Please check your milk weight input.");
				alert.setHeaderText("You Entered: " + milkText.getText());
				alert.setContentText("Weight can only be an integer value!");
				alert.showAndWait();
			}
			if(ready) {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("The following data is being recorded.");
				alert.setHeaderText("Adding Data for Farm ID"+ this.userFarmChoice +".");
				alert.setContentText("Date: "+this.userMonthChoice+" " +this.userDateChoice+", "+year+"."+
				System.lineSeparator()+"Weight: "+weight+" lb");
				final Optional<ButtonType> result = alert.showAndWait();
				 if (result.isPresent() && result.get() == ButtonType.OK) {
					for(Farm f: this.farms) {
						if (f.getFarmID().equals(this.userFarmChoice)) {
							System.out.println("New data added");
							f.addInput(year, this.userMonthChoice, Integer.parseInt(userDateChoice), weight);
							break;
						}
					}
				yearText.setText("Ex: 2019");
				milkText.setText("Ex: 293489");
			    }

				
			}
			
		});
		}

	}

	private void editMilkDataButtonAction() {
		root.setCenter(notImplementedPanel);
		System.out.println("Edit milk data Button Pressed");
	}

	private void farmReportButtonAction() {
		System.out.println("Farm Report Button Pressed");
		root.setCenter(reportPanel);
		
		//GRAPHING
		chart.getData().clear(); // Clear chart
		Series[] seriesArray = new Series[farms.size()]; // make array for series
		int index = 0;
		for (Farm farm : farms) {
			seriesArray[index] = new XYChart.Series(); // Create new series
			// Set newly added series' name to that of farm
			seriesArray[index].setName(farm.getFarmID());
			// Add all months data to series
			for (int i = 1; i < 13; i++) {
				//TODO dynamically set year
				seriesArray[index].getData().add(new XYChart.Data(Integer.toString(i), farm.getTotalWeightMonth(2019, i)));
			}
			index++;
		}
		
		for (int i = 0; i < farms.size(); i++) {
			chart.getData().add(seriesArray[i]);
		}
		//GRAPHING
		
	}

	private void annualReportButtonAction() {
		System.out.println("Annual Report Button Pressed");
		root.setCenter(notImplementedPanel);
	}

	private void monthlyReportButtonAction() {
		System.out.println("Monthly Report Button Pressed");
		root.setCenter(notImplementedPanel);
	}

	private void dateRangeReportButtonAction() {
		System.out.println("Date Range Report Button Pressed");
		root.setCenter(notImplementedPanel);
	}
	private void updateComboBoxes(ComboBox farmComboBox, ComboBox yearComboBox) {
		if (farms.size()!=0) {				
			ObservableList<String> newFarms = FXCollections.observableArrayList();
			ObservableList<String> newYearItems = FXCollections.observableArrayList();
			TreeSet<String> allYears = new TreeSet<String>();
			farms.forEach(e -> allYears.addAll(e.getYearList()));
			farms.forEach(e -> newFarms.add(e.getFarmID()));
			newYearItems.addAll(allYears);
			newYearItems.add("ALL");
			yearComboBox.setItems(newYearItems);
			farmComboBox.setItems(newFarms);
		}			
	}
	private void reportSubmitButtonAction() {
		System.out.println("User may have selected farmID, Year, and Month pressed submit button.");
		Farm userFarm = null;
		for (Farm f : farms) {
			if (f.getFarmID().equals(userFarmChoice)) {
				userFarm = f;
			}
		}
		try {
			if (userYearChoice.equals("ALL")) this.userYearChoice = "-1";
			this.textReport = Report.farmReport(userFarm, 
					Integer.parseInt(this.userYearChoice), this.userMonthChoice);
			System.out.println("User Selected Report is produced");
			
			if (this.textReport==null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Time Range");
				alert.setHeaderText("Year: All, " + "Month: " + this.userMonthChoice +" is not supported.");
				alert.setContentText("We are sorry."+System.lineSeparator()+"We don't support reports for specific months across all years.");
				alert.showAndWait();
			}
			else {							
			chartGroup.getChildren().setAll(chart,new Label(this.textReport.get(0)),
					new Label(this.textReport.get(1)), new Label(this.textReport.get(2)));
			}
			
		} catch (Exception e) {

		}
		
	}


	/**
	 * EventHandler to create text field when a user want to input text data.
	 * 
	 */
	class InputHandler implements EventHandler<ActionEvent> {
		Button button;
		String text;
		Button submit;
		TextField textBox;

		InputHandler(Button button, String text, Button submit, TextField textBox) {
			this.button = button;
			this.text = text;
			this.submit = submit;
			this.textBox = textBox;
		}

		@Override
		public void handle(ActionEvent arg0) {
			textBox.setPromptText(text);
			inputs = new VBox(textBox, submit);
			root.setCenter(inputs);
		}
	}

	class InputSubmitHandler implements EventHandler<ActionEvent> {
		TextField textBox;
		Button submit;

		InputSubmitHandler(Button submit, TextField textBox) {
			this.textBox = textBox;
		}

		@Override
		public void handle(ActionEvent arg0) {
			System.out.println("User entered: \"" + textBox.getText() + "\"");
			// This is super cringe rn sorry team, just here to make it work and no further
			// Shouldn't take long to move though, just this one line is what's needed to get the input
			try {
				Main.farms = Report.parseFile(textBox.getText(), farms);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(farms.size());
			textBox.clear();
			
		}
	}

	
	/**
	 * TODO method header needed?
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
