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
 * List Collaborators:
 * Zander Keith,   zkeith@wisc.edu,   LEC001
 * Daniel Levy,    dslevy2@wisc.edu,  LEC001
 * Matthew Holmes, mrholmes@wisc.edu, LEC001
 * Solly Parenti,  sparenti@wisc.edu, LEC001
 * Hyejin Yeon,    hyeon2@wisc.edu,   LEC001
 * 
 * Other Credits: describe other source (websites or people)
 * 
 * Known Bugs: describe known unresolved bugs here
 */
package application;

// Java Import Statements
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.TreeSet;

// JavaFx Import Statements
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main - Launches program window, runs GUI, and holds Farm object data
 * 
 * @author Zander, Daniel, Matthew, Solly, Hyejin
 *
 */
public class Main extends Application {

	// The field farms hold list of farms that user inputs to the program

	static ArrayList<Farm> farms = new ArrayList<Farm>();

	// Fields related to GUI

	private static final int WINDOW_HEIGHT = 640;
	private static final String APP_TITLE = "MilkWeight";

	private BorderPane root = new BorderPane();
	private BorderPane reportPanel = new BorderPane();
	private BorderPane farmReportPanel = new BorderPane();
	private BorderPane notImplementedPanel = new BorderPane();
	private BorderPane homePanel = new BorderPane();

	// Charts and graphs to display to the user
	private VBox chartGroup = new VBox();
	private VBox inputs = new VBox();
	private TableView<ArrayList<StringProperty>> table;
	private BorderPane tableGroup = new BorderPane();
	private PieChart pieChart;
	private CategoryAxis dateAxis = new CategoryAxis();
	private NumberAxis percentAxis = new NumberAxis();
	private BarChart<String, Number> chart = new BarChart<String, Number>(dateAxis, percentAxis);

	// List of Farms, Year, Months for the user to choose from
	private HBox farmIDGroup = new HBox();
	private HBox yearGroup = new HBox();
	private HBox monthGroup = new HBox();
	private HBox submitGroup = new HBox();

	// Stores User's Choice
	private String userFarmChoice = "";
	private String userDateChoice = "";
	private String userMonthChoice = "";
	private String userYearChoice = "";

	// Setup for the Range Report
	HBox startInfo = new HBox();
	HBox endInfo = new HBox();
	VBox bottomInfo = new VBox();

	// Stores user's choice for data range report
	private String startDateChoice = "";
	private String startMonthChoice = "";
	private String startYearChoice = "";
	private String endDateChoice = "";
	private String endMonthChoice = "";
	private String endYearChoice = "";

	// Text Report to the user. I gets used to generate charts/graphs
	private ArrayList<String> textReport = null;

	// Repeatedly updated combo boxes
	private ComboBox<String> farmComboBox = new ComboBox<String>();
	private ComboBox<String> monthComboBox = new ComboBox<String>();
	private ComboBox<String> yearComboBox = new ComboBox<String>();

	// Useful global
	private ObservableList<String> monthItems = FXCollections.observableArrayList("January",
			"February", "March", "April", "May", "June", "July", "August", "September", "October",
			"November", "December");

	/**
	 * Sets up all GUI elements.
	 * 
	 * @param primaryStage main stage for GUI
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Labels with style
		Label fileLabel = new Label("File");
		fileLabel.setFont(new Font("Arial", 18));
		fileLabel.setTextFill(Color.web("#ffffff")); // white color
		Label reportLabel = new Label("Report");
		reportLabel.setFont(new Font("Arial", 18));
		reportLabel.setTextFill(Color.web("#ffffff"));
		Label monthLabel = new Label("Month: ");
		Label yearLabel = new Label("Year: ");
		Label farmIDLabel = new Label("Farm ID: ");
		Label chartLabel = new Label("<Report Name> of No Data");
		Label totalWeightLabel = new Label("Total Weight Sold: No Data");
		Label percentWeightLabel = new Label("Percent of total: No Data");

		// Initialize Drop Downs
		monthComboBox.setItems(monthItems);
		monthComboBox.setOnAction((event) -> {
			// Sets the user's Month choice field if there is a change
			this.userMonthChoice = monthComboBox.getValue();
			// System.out.println("User picked the month : " + userMonthChoice);
		});

		ObservableList<String> yearItems = FXCollections.observableArrayList("No Data Yet");
		yearComboBox.setItems(yearItems);
		yearComboBox.setOnAction((event) -> {
			// Sets the user's Year choice field if there is a change
			this.userYearChoice = yearComboBox.getValue();
			// System.out.println("User picked the year : " + userYearChoice);
		});

		ObservableList<String> farmItems = FXCollections.observableArrayList("No Data Yet");
		farmComboBox.setItems(farmItems);
		farmComboBox.setOnAction((event) -> {
			// Sets the user's Farm choice field if there is a change
			this.userFarmChoice = farmComboBox.getValue();
			// System.out.println("User picked farm : " + userFarmChoice);
		});

		// Home button
		Button homeButton = new Button("Home");
		homeButton.setOnAction(e -> homeButtonAction());

		// --- File import UI functionality ---
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose a CSV file");
		chooser.getExtensionFilters().add(new ExtensionFilter("Milk Data", "*.csv"));

		Button importFileButton = new Button("Import File");
		importFileButton.setOnAction(e -> {
			File chosen = chooser.showOpenDialog(root.getScene().getWindow());
			// The chosen file will be null if the window is closed prematurely.
			if (chosen != null) {
				this.importFileButtonAction(chosen.getAbsolutePath());
			}
		});
		// --- End file import UI functionality ---

		// More Buttons. On clicking it calls an appropriate method 
		Button exportAsCSVButton = new Button("Export as CSV");
		exportAsCSVButton.setOnAction(e -> exportAsCSVButtonAction());

		Button addNewFarmButton = new Button("Add new farm");
		addNewFarmButton.setOnAction(e -> this.addNewFarmButtonAction());
		
		Button addNewMilkDataButton = new Button("Add/edit milk data");
		addNewMilkDataButton.setOnAction(e -> {
			addNewMilkDataButtonAction();
			this.updateComboBoxes(farmComboBox, yearComboBox);
			sortFarms();
		});

		Button removeFarmButton = new Button("Remove Farm");
		removeFarmButton.setOnAction(e -> {
			removeFarmButtonAction();
			this.updateComboBoxes(farmComboBox, yearComboBox);
		});

		Button farmReportButton = new Button("Farm Report");
		farmReportButton.setOnAction(e -> {
			farmReportButtonAction();
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

		// Create left panel of buttons (dependencies first) and pictures
		VBox fileOptionGroup = new VBox();
		fileOptionGroup.getChildren().addAll(fileLabel, importFileButton, exportAsCSVButton,
				addNewFarmButton, addNewMilkDataButton, removeFarmButton);

		VBox reportOptionGroup = new VBox(reportLabel, farmReportButton, annualReportButton,
				monthlyReportButton, dateRangeReportButton);

		ImageView cowImage = new ImageView();
		cowImage.setImage(new Image("file:cow.jpg")); // file: prefix completes a valid relative URI
        
		// A fix to get the pictures to load on a mac
		if(System.getProperty("os.name").toLowerCase().contains("mac")) {
			cowImage.setImage(new Image("cow.jpg")); 
			
		}
		cowImage.setFitHeight(WINDOW_HEIGHT / 8);
		cowImage.setPreserveRatio(true);

		VBox leftOptionPanel = new VBox();
		leftOptionPanel.setSpacing(20); 										
		leftOptionPanel.setPadding(new Insets(15, 15, 15, 15)); 																
		leftOptionPanel.setStyle("-fx-background-color: #1d4c2c;"); //Dark Green
		leftOptionPanel.getChildren().addAll(homeButton, fileOptionGroup, reportOptionGroup,
				cowImage);

		// Create report panel (dependencies first)

		// Create Chart group
		// this image is just a placeholder for the actual chart.
		ImageView placeholdImage = new ImageView();
		placeholdImage.setImage(new Image("basically.png")); // file: prefix completes a valid
																// relative URI
		placeholdImage.setFitHeight(WINDOW_HEIGHT / 2);
		placeholdImage.setPreserveRatio(true);

		chartGroup.setPadding(new Insets(15, 15, 15, 15));
		chartGroup.setStyle("-fx-border-color: black");
		chartGroup.getChildren().setAll(chart, chartLabel, totalWeightLabel, percentWeightLabel);

		// Create Various Submit Groups for all reports

		farmIDGroup.getChildren().addAll(farmIDLabel, farmComboBox);

		yearGroup.getChildren().addAll(yearLabel, yearComboBox);

		monthGroup.getChildren().addAll(monthLabel, monthComboBox);

		// Used for Farm Report

		submitGroup.setSpacing(15.0);

		// Used for Monthly Report
		HBox IDYearSubmitGroup = new HBox();
		IDYearSubmitGroup.getChildren().addAll(farmIDGroup, yearGroup, monthGroup,
				reportSubmitButton);
		IDYearSubmitGroup.setSpacing(15.0);

		reportPanel.setBottom(IDYearSubmitGroup);
		reportPanel.setCenter(chartGroup);
		reportPanel.setPadding(new Insets(15, 15, 15, 15));


		// Home panel with picture instructions, together with a fix to get
		// pictures to show up on a mac.
		ImageView homeTop = new ImageView(new Image("file:hometop.png"));
		if(System.getProperty("os.name").toLowerCase().contains("mac")) {
			homeTop = new ImageView(new Image("hometop.png"));
		}
		homeTop.setFitHeight(200);
		homeTop.setPreserveRatio(true);
		homePanel.setTop(homeTop);
		ImageView homeLeft = new ImageView(new Image("file:homeleft.png"));
		if(System.getProperty("os.name").toLowerCase().contains("mac")) {
			homeLeft = new ImageView(new Image("homeleft.png"));
		}
		homeLeft.setFitHeight(420);
		homeLeft.setPreserveRatio(true);
		ImageView homeRight = new ImageView(new Image("file:homeright.png"));
		if(System.getProperty("os.name").toLowerCase().contains("mac")) {
			homeRight = new ImageView(new Image("homeright.png"));
		}
		homeRight.setFitHeight(420);
		homeRight.setPreserveRatio(true);
		homePanel.setTop(homeTop);
		homePanel.setLeft(homeLeft);
		homePanel.setRight(homeRight);

		homePanel.setMinWidth(800);

		// Not implemented panel, used as a placeholder in GUI setup
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

	/**
	 * This method resets the center panel to the home screen when the home button
	 * is pressed
	 */
	private void homeButtonAction() {
		root.setCenter(homePanel);
		//System.out.println("Home Button Pressed");
	}

	/**
	 * This method is called when the user hits the import file button
	 * @param inputText - a CSV file that the user selects
	 */
	private void importFileButtonAction(String inputText) {
		// We use the parseFile method from the Report class to try to read the file 
		// and update the farm information.
		try {
			Main.farms = Report.parseFile(inputText, farms);
			Alert alert = new Alert(AlertType.INFORMATION); //Successful farm reading!
			alert.setTitle("File Read");
			alert.setHeaderText(null);
			alert.setContentText("Successful! Your file has been recorded.");
			alert.showAndWait();
		} catch (FileNotFoundException e) { // Error if we can't find the file
			Alert alert = new Alert(AlertType.WARNING); 
			alert.setTitle("File Path Error");
			alert.setHeaderText(null);
			alert.setContentText(
					"We are sorry. We were unable to read the file. Please check your file path and try again.");
			alert.showAndWait();
		} catch (NumberFormatException e) { // Error if the file is not in the proper format
			Alert alert = new Alert(AlertType.WARNING); 
			alert.setTitle("Invalid Input");
			alert.setHeaderText(
					"There was a problem processing your file" + System.lineSeparator()
							+ "due to the following line in your file:" + e.getMessage());
			alert.setContentText("We are sorry. We were unable to fully process your file."
					+ System.lineSeparator() + "Please consider removing the line that contains"
					+ e.getMessage() + System.lineSeparator()
					+ "Only the date before this line has been processed.");
			alert.showAndWait();
		} catch (ArrayIndexOutOfBoundsException e) { // Error if the file is missing data
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Missing Data");
			alert.setHeaderText(
					"There might be missing data in your file at Line " + e.getMessage());
			alert.setContentText("We are sorry. We were unable to fully process your file."
					+ System.lineSeparator()
					+ "Please make sure your file does not contain missing data."
					+ System.lineSeparator() + "Only the lines before " + e.getMessage()
					+ " has been processed.");
			alert.showAndWait();
		} catch (Exception e) { // Error if there was some duplicate data
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Duplicate Data");
			alert.setHeaderText("There might be some duplicate data in your file.");
			alert.setContentText("All duplicate data have been overwritten."
					+ System.lineSeparator() + "Consider exporting it as a csv file"
					+ System.lineSeparator() + "" + "to see the current data in our system.");
			alert.showAndWait();
		}
		// Since farm and year data might have changed, we need to update the combo boxes
		this.updateComboBoxes(farmComboBox, yearComboBox);
	}

	/**
	 * Handles exporting data as a csv page when button is pressed.  This method
	 * creates a csv file containing all of the information in the system, every day for every farm.
	 */
	private void exportAsCSVButtonAction() {
		//System.out.println("User wants to export their work.");

		// Setting up the Export as CSV Page
		VBox exportBox = new VBox();
		TextField filePathField = new TextField();
		TextField fileNameField = new TextField();
		filePathField.setPromptText("Ex: /Users/Solly/Desktop");
		fileNameField.setPromptText("Ex: AprilFarmOutput");
		Button exportFileButton = new Button("Export File");
		exportBox.getChildren().addAll(
				new Label("Enter the path where you would like the file saved: "), filePathField,
				new Label("Enter the name to give the output file: "), fileNameField,
				exportFileButton);
		root.setCenter(exportBox);
		
		// Called when user decides to export their file
		exportFileButton.setOnAction(userClick -> {
			boolean pathExists;
			File filePath = new File(filePathField.getText());
			pathExists = filePath.exists();

			if (pathExists) {
				File fileName = new File(filePath, fileNameField.getText() + ".csv");
				boolean nameAlreadyExists = fileName.exists();
				boolean writeFile = true; // Used to give user option to not overwrite a file
				
				// Checking if the user would like to overwrite the file if it already exists
				if (nameAlreadyExists) {
					writeFile = false;
					Alert nameAlert = new Alert(AlertType.CONFIRMATION);
					nameAlert.setTitle(null);
					nameAlert.setContentText(
							"A file with that name already exists." + System.lineSeparator()
									+ "Pressing \"OK\" will overwrite the existing file.");
					final Optional<ButtonType> nameResult = nameAlert.showAndWait();
					if (nameResult.isPresent() && nameResult.get() == ButtonType.OK) {
						writeFile = true;
					}
				}
				
				// Writing the output file
				if (writeFile) {
					try {
						if (!nameAlreadyExists) {
							fileName.createNewFile();
						}
						FileWriter output = new FileWriter(fileName);
						output.write("date,farm_id,weight\n");
						// We need to loop through all the farms, then years, and months, and days to get all of the data
						for (Farm farm : farms) {
							for (Integer year : farm.getYearIntList()) {
								for (int month = 1; month <= 12; month++) {
									for (int day = 1; day <= 31; day++) {
										int dayWeight = farm.getTotalWeightDay(year, month, day);
										if (dayWeight != 0) {
											String data = Integer.toString(year) + "-"
													+ Integer.toString(month) + "-"
													+ Integer.toString(day) + ",";
											data += farm.getFarmID() + ","
													+ Integer.toString(dayWeight) + "\n";
											output.write(data);
										}
									}
								}
							}
						}
						// Closing the writer
						output.close();
						
						// Alerting the user that the file was successfully written
						Alert confirmExport = new Alert(AlertType.CONFIRMATION);
						confirmExport.setTitle("Success!");
						confirmExport.setContentText("The file was created successfully.");
						confirmExport.showAndWait();
					} catch (Exception e) {
						Alert fileMakingError = new Alert(AlertType.ERROR);
						fileMakingError.setTitle("Error");
						fileMakingError
								.setContentText("Sorry, there was an error writing the file.");
						fileMakingError.showAndWait();
					}
				}
			} 
			// If the path doesn't exist, we alert the user
			else {
				Alert pathAlert = new Alert(AlertType.ERROR);
				pathAlert.setTitle("Error");
				pathAlert.setContentText("There was an error finding the correct path.");
				pathAlert.showAndWait();
			}
		});

		//System.out.println("Export as CSV File Button Pressed");
	}

	/**
	 * Handles adding new farm for which data can be added
	 */
	private void addNewFarmButtonAction() {
		// Setting up the page to prompt the user to enter a farm
		VBox newFarmBox = new VBox();
		TextField farmName = new TextField();
		farmName.setPromptText("Ex: Farm 999");
		Button addFarm = new Button("Add");
		newFarmBox.getChildren().addAll(new Label("Enter the name of the farm: "), farmName,
				addFarm);
		root.setCenter(newFarmBox);

		addFarm.setOnAction(userClickedAdd -> {
			boolean dupe = false;
			this.userFarmChoice = farmName.getText();
			farmName.clear();
			//System.out.println("The user wants to add a new farm named " + this.userFarmChoice);
			
			// Checking if the farm is already in the system.
			for (Farm f : farms) {
				if (f.getFarmID().equals(this.userFarmChoice)) {
					// If farm is already in the system, we tell the user where to enter in data for that farm
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("This farm already exists!");
					alert.setHeaderText("Farm ID entered : " + this.userFarmChoice);
					alert.setContentText("Farm ID you entered already exists in our system."
							+ System.lineSeparator() + "Please use other options to add data.");
					alert.showAndWait();
					dupe = true;
				}
			}
			// If there is no duplicate, we add the farm to the list and then let the user know
			if (!dupe) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("New Farm To Be Added");
				alert.setHeaderText(null);
				alert.setContentText("The Farm \"" + this.userFarmChoice + "\" is being added.");
				final Optional<ButtonType> addResult = alert.showAndWait();

				if (addResult.isPresent() && addResult.get() == ButtonType.OK) {
					//System.out.println("A new farm named " + this.userFarmChoice + " added");
					Farm newFarm = new Farm(this.userFarmChoice);
					farms.add(newFarm);
				}
			}
		});
	}

	/**
	 * Handles adding new farm data for a specific farm and day when button is
	 * pressed
	 */
	private void addNewMilkDataButtonAction() {
		//System.out.println("Add new milk data Button Pressed");
		root.setCenter(new Label("No farm available in the system."));
		ComboBox<String> farmIDs = new ComboBox<String>();
		
		// If there are no farms in the system, we prompt the user to add a farm first
		if (farms.size() == 0) {
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

		} else {
			// Getting a list of farms for the user to select from
			ObservableList<String> newFarms = FXCollections.observableArrayList();
			farms.forEach(e -> newFarms.add(e.getFarmID()));
			farmIDs.setItems(newFarms);
			farmIDs.setOnAction(e -> {
				this.userFarmChoice = farmIDs.getValue();
				//System.out.println("User wants to enter data for Farm ID " + userFarmChoice);
			});
			// Setting up prompts to get farm info from the user
			HBox farmBar = new HBox();
			farmBar.getChildren().add(new Label("Farm ID : "));
			farmBar.getChildren().add(farmIDs);
			
			// Getting the year info from the user
			HBox yearBar = new HBox();
			yearBar.getChildren().add(new Label("Year : "));
			TextField yearText = new TextField();
			yearText.setPromptText("Ex: 2019");
			yearBar.getChildren().add(yearText);
			yearText.setOnMouseClicked(e -> yearText.clear());

			// Getting the month info from the user
			HBox monthBar = new HBox();
			monthBar.getChildren().add(new Label("Month : "));
			ComboBox<String> months = new ComboBox<String>();
			ObservableList<String> text = FXCollections.observableArrayList("January", "February",
					"March", "April", "May", "June", "July", "August", "September", "October",
					"November", "December");
			months.setItems(text);
			monthBar.getChildren().add(months);
			months.setOnAction(e -> {
				this.userMonthChoice = months.getValue();
				//System.out.println("User selected month of " + this.userMonthChoice);
			});

			// Getting the milk info from the user
			HBox milkBar = new HBox();
			milkBar.getChildren().add(new Label("Amount of Milk Sold (lb) :"));
			TextField milkText = new TextField();
			milkText.setPromptText("Ex: 293489");
			milkBar.getChildren().add(milkText);
			milkText.setOnMouseClicked(e -> milkText.clear());

			// Getting the date info from the user
			HBox dateBar = new HBox();
			ComboBox<String> dates = new ComboBox<String>();
			ObservableList<String> numbers = FXCollections.observableArrayList();
			for (int i = 1; i < 32; i++) {
				numbers.add(Integer.toString(i));
			}
			dates.setItems(numbers);
			dateBar.getChildren().addAll(new Label("Day: "), dates);

			dates.setOnAction(e -> {
				this.userDateChoice = dates.getValue();
				//System.out.println("User chose the date " + userDateChoice);
			});

			VBox mainPanel = new VBox();
			Button record = new Button("Record");
			mainPanel.getChildren().addAll(farmBar, monthBar, dateBar, yearBar, milkBar, record);
			root.setCenter(mainPanel);

			record.setOnAction(e -> {
				int weight = 0;
				int year = 0;
				boolean ready = true;

				//System.out.println("User wants to record a new data");

				// We try to get the info from user, making sure format is correct
				try {
					year = Integer.parseInt(yearText.getText());
				} catch (Exception exp) { // Error if user doesn't enter integer for year
					ready = false;
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Please check your year input.");
					alert.setHeaderText("You Entered: " + yearText.getText());
					alert.setContentText("Year can only be an integer value!");
					alert.showAndWait();
				}

				try {
					weight = Integer.parseInt(milkText.getText());
				} catch (Exception exp) { // Error if user doesn't enter integer for weight
					ready = false;
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Please check your milk weight input.");
					alert.setHeaderText("You Entered: " + milkText.getText());
					alert.setContentText("Weight can only be an integer value!");
					alert.showAndWait();
				}

				// Finally adding in the new data
				if (ready) {

					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("The following data is being recorded.");
					alert.setHeaderText("Adding Data for Farm ID: " + this.userFarmChoice + ".");
					alert.setContentText("Date: " + this.userMonthChoice + " "
							+ this.userDateChoice + ", " + year + "." + System.lineSeparator()
							+ "Weight: " + weight + " lb" + System.lineSeparator()
							+ "If there is an existing weight for this date,"
							+ System.lineSeparator() + "this new weight will overwrite it.");
					final Optional<ButtonType> result = alert.showAndWait();

					if (result.isPresent() && result.get() == ButtonType.OK) {
						for (Farm f : farms) { // Loop through the farms until we find a match
							if (f.getFarmID().equals(this.userFarmChoice)) {
								// Once we find a match, we'll add the new information
								try {
									f.addInput(year, this.userMonthChoice,
											Integer.parseInt(userDateChoice), weight);
								} 
								// Alerting the user if there was data that was overwritten
								catch (Exception e1) { 
									Alert alert2 = new Alert(AlertType.INFORMATION);
									alert2.setTitle(e1.getMessage());
									alert2.setHeaderText("Data Overwritten!");
									alert2.setContentText("There was an existing data for: "
											+ this.userMonthChoice + " " + this.userDateChoice
											+ ", " + year + "." + System.lineSeparator()
											+ "The new weight " + weight
											+ " lb replaced the existing value.");
									alert2.showAndWait();

								}
								break;
							}
						}
						yearText.setPromptText("Ex: 2019");
						milkText.setPromptText("Ex: 293489");
					}
				}
			});
		}
	}

	/**
	 * Handles removing a farm
	 */
	private void removeFarmButtonAction() {
		// First, we get a list of the farms in the system
		ComboBox<String> farmIDs = new ComboBox<String>();
		ObservableList<String> newFarms = FXCollections.observableArrayList();
		farms.forEach(e -> newFarms.add(e.getFarmID()));
		farmIDs.setItems(newFarms);
		farmIDs.setOnAction(e -> {
			this.userFarmChoice = farmIDs.getValue();
		});
		// Getting info from user about which farm they'd like to remove
		VBox removeFarmBox = new VBox();
		removeFarmBox.getChildren().add(new Label("Select the farm to remove:"));
		HBox farmBar = new HBox();
		removeFarmBox.getChildren().add(farmBar);
		farmBar.getChildren().add(new Label("Farm ID : "));
		farmBar.getChildren().add(farmIDs);
		Button removeFarm = new Button("Remove");
		removeFarm.setOnAction(e -> {
			int farmIndex = -1;
			// Looping through the farms to find the match
			for (int i = 0; i < farms.size(); i++) {
				if (farms.get(i).getFarmID().equals(userFarmChoice)) {
					farmIndex = i;
				}
			}
			// Getting confirmation from the user before we remove the farm
			Alert removeAlert = new Alert(AlertType.CONFIRMATION);
			removeAlert.setContentText(
					"Are you sure you would like to remove " + userFarmChoice + "?");
			final Optional<ButtonType> result = removeAlert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				farms.remove(farmIndex);
			}

		});
		removeFarmBox.getChildren().add(removeFarm);
		root.setCenter(removeFarmBox);

	}

	/**
	 * Handler for setting up the visual report for a specified farm
	 */
	private void farmReportButtonAction() {
		sortFarms();
		//System.out.println("Farm Report Button Pressed");

		// Create new button to be used specifically for farm report
		Button farmReportSubmitButton = new Button("View Farm Report");
		farmReportSubmitButton.setOnAction(e -> this.farmReportSubmitButtonAction());

		// Set up table for farm report
		table = new TableView<ArrayList<StringProperty>>();
		table.setEditable(false);

		// Add farm report fields to table
		TableColumn<ArrayList<StringProperty>, String> monthCol = new TableColumn<>("Month");
		TableColumn<ArrayList<StringProperty>, String> totalWeightCol = new TableColumn<>(
				"Total Weight");
		TableColumn<ArrayList<StringProperty>, String> percentWeightCol = new TableColumn<>(
				"Percent of All Farms");
		monthCol.setMinWidth(100);
		totalWeightCol.setMinWidth(150);
		percentWeightCol.setMinWidth(150);

		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(monthCol, totalWeightCol, percentWeightCol);

		monthCol.setCellValueFactory(e -> e.getValue().get(0));
		totalWeightCol.setCellValueFactory(e -> e.getValue().get(1));
		percentWeightCol.setCellValueFactory(e -> e.getValue().get(2));

		tableGroup.setCenter(table);
		tableGroup.setRight(pieChart);
		farmReportPanel.setPadding(new Insets(15, 15, 15, 15));
		farmReportPanel.setCenter(tableGroup);

		// Add relevant buttons for farm report
		submitGroup.getChildren().clear();
		submitGroup.getChildren().addAll(farmIDGroup, yearGroup, farmReportSubmitButton);
		farmReportPanel.setBottom(submitGroup);
		root.setCenter(farmReportPanel);

	}

	/**
	 * Creates an report for a specified year upon pressing the annual report button
	 */
	private void annualReportButtonAction() {
		// Currently, we don't allow the user to select ALL years for the annual report
		this.updateComboBoxesWithoutAll(farmComboBox, yearComboBox);

		sortFarms();
		// Create new button to be used specifically for annual report
		Button annualReportSubmitButton = new Button("View Annual Report");
		annualReportSubmitButton.setOnAction(e -> this.annualReportSubmitButtonAction());

		// Set up table for farm report
		table = new TableView<ArrayList<StringProperty>>();
		table.setEditable(false);

		// Add farm report fields to table
		TableColumn<ArrayList<StringProperty>, String> monthCol = new TableColumn<>("Farm ID");
		TableColumn<ArrayList<StringProperty>, String> totalWeightCol = new TableColumn<>(
				"Total Weight");
		TableColumn<ArrayList<StringProperty>, String> percentWeightCol = new TableColumn<>(
				"Percent of All Farms");
		monthCol.setMinWidth(100);
		totalWeightCol.setMinWidth(150);
		percentWeightCol.setMinWidth(150);

		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(monthCol, totalWeightCol, percentWeightCol);

		monthCol.setCellValueFactory(e -> e.getValue().get(0));
		totalWeightCol.setCellValueFactory(e -> e.getValue().get(1));
		percentWeightCol.setCellValueFactory(e -> e.getValue().get(2));

		// set up pie chart for report
		pieChart = new PieChart();

		pieChart.setTitle("Total for Year");

		tableGroup.setCenter(table);
		tableGroup.setRight(pieChart);
		farmReportPanel.setPadding(new Insets(15, 15, 15, 15));
		farmReportPanel.setCenter(tableGroup);

		// Add relevant buttons for farm report
		submitGroup.getChildren().clear();
		submitGroup.getChildren().addAll(yearGroup, annualReportSubmitButton);
		farmReportPanel.setBottom(submitGroup);
		root.setCenter(farmReportPanel);
	}

	/**
	 * Creates an report for a specified month upon pressing the monthly report
	 * button
	 */
	private void monthlyReportButtonAction() {
		// Currently, we don't allow the user to select ALL years for the monthly report 
		this.updateComboBoxesWithoutAll(farmComboBox, yearComboBox);
		sortFarms();
		// Create new button to be used specifically for monthly report
		Button monthlyReportSubmitButton = new Button("View Monthly Report");
		monthlyReportSubmitButton.setOnAction(e -> this.monthlyReportSubmitButtonAction());

		// Set up table for farm report
		table = new TableView<ArrayList<StringProperty>>();
		table.setEditable(false);

		// Add farm report fields to table
		TableColumn<ArrayList<StringProperty>, String> monthCol = new TableColumn<>("Farm ID");
		TableColumn<ArrayList<StringProperty>, String> totalWeightCol = new TableColumn<>(
				"Total Weight");
		TableColumn<ArrayList<StringProperty>, String> percentWeightCol = new TableColumn<>(
				"Percent of All Farms");
		monthCol.setMinWidth(100);
		totalWeightCol.setMinWidth(150);
		percentWeightCol.setMinWidth(150);

		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(monthCol, totalWeightCol, percentWeightCol);

		monthCol.setCellValueFactory(e -> e.getValue().get(0));
		totalWeightCol.setCellValueFactory(e -> e.getValue().get(1));
		percentWeightCol.setCellValueFactory(e -> e.getValue().get(2));

		// set up pie chart for report
		pieChart = new PieChart();

		pieChart.setTitle("Total for Month");

		tableGroup.setCenter(table);
		tableGroup.setRight(pieChart);
		farmReportPanel.setPadding(new Insets(15, 15, 15, 15));
		farmReportPanel.setCenter(tableGroup);

		// Add relevant buttons for farm report
		submitGroup.getChildren().clear();
		submitGroup.getChildren().addAll(yearGroup, monthGroup, monthlyReportSubmitButton);
		farmReportPanel.setBottom(submitGroup);
		root.setCenter(farmReportPanel);
	}

	/**
	 * Creates a visual data representation for a range of dates
	 */
	private void dateRangeReportButtonAction() {
		//System.out.println("Range Report Button Pressed");
		sortFarms();
		// Create new button to be used specifically for range report
		Button rangeReportSubmitButton = new Button("View Range Report");
		rangeReportSubmitButton.setOnAction(e -> this.rangeReportSubmitButtonAction());

		// Set up table for farm report
		table = new TableView<ArrayList<StringProperty>>();
		table.setEditable(false);

		// Add farm report fields to table
		TableColumn<ArrayList<StringProperty>, String> monthCol = new TableColumn<>("Farm ID");
		TableColumn<ArrayList<StringProperty>, String> totalWeightCol = new TableColumn<>(
				"Total Weight");
		TableColumn<ArrayList<StringProperty>, String> percentWeightCol = new TableColumn<>(
				"Percent of All Farms");
		monthCol.setMinWidth(100);
		totalWeightCol.setMinWidth(150);
		percentWeightCol.setMinWidth(150);

		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		table.getColumns().addAll(monthCol, totalWeightCol, percentWeightCol);

		monthCol.setCellValueFactory(e -> e.getValue().get(0));
		totalWeightCol.setCellValueFactory(e -> e.getValue().get(1));
		percentWeightCol.setCellValueFactory(e -> e.getValue().get(2));

		// set up pie chart for report
		pieChart = new PieChart();

		pieChart.setTitle("Total for Range");

		tableGroup.setCenter(table);
		tableGroup.setRight(pieChart);
		farmReportPanel.setPadding(new Insets(15, 15, 15, 15));
		farmReportPanel.setCenter(tableGroup);

		// In the range report, we need a different set up from previous reports
		// Getting 2 pieces of information (start date and end date) from the user
		startInfo.getChildren().clear();
		endInfo.getChildren().clear();
		bottomInfo.getChildren().clear();
		startInfo.getChildren().add(new Label("Start Year: "));
		endInfo.getChildren().add(new Label("End Year: "));

		// Setting up year boxes
		ComboBox<String> startYear = new ComboBox<String>();
		ComboBox<String> endYear = new ComboBox<String>();
		startYear.setItems(yearComboBox.getItems());
		endYear.setItems(yearComboBox.getItems());

		// Getting the year information from the user
		startYear.setOnAction(e -> {
			this.startYearChoice = startYear.getValue();
			//System.out.println("User chose the start year " + startYearChoice);
		});
		endYear.setOnAction(e -> {
			this.endYearChoice = endYear.getValue();
			//System.out.println("User chose the end year " + endYearChoice);
		});

		startInfo.getChildren().add(startYear);
		endInfo.getChildren().add(endYear);

		startInfo.getChildren().add(new Label("Start Month: "));
		endInfo.getChildren().add(new Label("End Month: "));

		// Setting up month boxes
		ComboBox<String> startMonth = new ComboBox<String>();
		ComboBox<String> endMonth = new ComboBox<String>();
		startMonth.setItems(monthComboBox.getItems());
		startMonth.getItems().remove("ALL");
		endMonth.setItems(monthComboBox.getItems());
		endMonth.getItems().remove("ALL");
		
		// Getting month information from the user
		startMonth.setOnAction(e -> {
			this.startMonthChoice = startMonth.getValue();
			//System.out.println("User chose the start month " + startMonthChoice);
		});
		endMonth.setOnAction(e -> {
			this.endMonthChoice = endMonth.getValue();
			//System.out.println("User chose the end Month " + endMonthChoice);
		});
		startInfo.getChildren().add(startMonth);
		endInfo.getChildren().add(endMonth);

		// Setting up date boxes
		startInfo.getChildren().add(new Label("Start Date: "));
		endInfo.getChildren().add(new Label("End Date: "));

		ComboBox<String> startDate = new ComboBox<String>();
		ComboBox<String> endDate = new ComboBox<String>();
		ObservableList<String> numbers = FXCollections.observableArrayList();
		for (int i = 1; i < 32; i++) { // Data added to invalid days is not recorded
			numbers.add(Integer.toString(i));
		}
		startDate.setItems(numbers);
		endDate.setItems(numbers);

		// Getting date information from user
		startDate.setOnAction(e -> {
			this.startDateChoice = startDate.getValue();
			//System.out.println("User chose the starting date " + startDateChoice);
		});
		endDate.setOnAction(e -> {
			this.endDateChoice = endDate.getValue();
			//System.out.println("User chose the end date " + endDateChoice);
		});
		startInfo.getChildren().add(startDate);
		endInfo.getChildren().add(endDate);

		endInfo.getChildren().add(rangeReportSubmitButton);
		// Add relevant buttons for range report
		bottomInfo.getChildren().addAll(startInfo, endInfo);
		farmReportPanel.setBottom(bottomInfo);
		root.setCenter(farmReportPanel);
	}

	/**
	 * Updates farm and year combo boxes.  Called after there have been changes
	 * to either the set of farms or the set of years in which we have info. This
	 * methods also updates yearComboBox to include ALL years
	 * @param farmComboBox
	 * @param yearComboBox
	 */
	private void updateComboBoxes(ComboBox<String> farmComboBox, ComboBox<String> yearComboBox) {
		// We only change the combo boxes if there are currently farms in the system
		if (farms.size() != 0) {
			// Getting the new farms and the new years
			ObservableList<String> newFarms = FXCollections.observableArrayList();
			ObservableList<String> newYearItems = FXCollections.observableArrayList();
			TreeSet<String> allYears = new TreeSet<String>();
			farms.forEach(e -> allYears.addAll(e.getYearSet()));
			farms.forEach(e -> newFarms.add(e.getFarmID()));
			newYearItems.addAll(allYears);
			newYearItems.add("ALL");
			// Adding that new info to the comboboxes
			yearComboBox.setItems(newYearItems);
			farmComboBox.setItems(newFarms);
		}
		sortFarms();
	}

	/**
	 * Updates farm and year combo boxes without giving the user the option to 
	 * select ALL years
	 * 
	 * @param farmComboBox
	 * @param yearComboBox
	 */
	private void updateComboBoxesWithoutAll(ComboBox<String> farmComboBox,
			ComboBox<String> yearComboBox) {
		// We only change the combo boxes if there are farms in the system
		if (farms.size() != 0) {
			// Getting the new farm and new year information
			ObservableList<String> newFarms = FXCollections.observableArrayList();
			ObservableList<String> newYearItems = FXCollections.observableArrayList();
			TreeSet<String> allYears = new TreeSet<String>();
			farms.forEach(e -> allYears.addAll(e.getYearSet()));
			farms.forEach(e -> newFarms.add(e.getFarmID()));
			newYearItems.addAll(allYears);
			// Adding that info to the combo boxes
			yearComboBox.setItems(newYearItems);
			farmComboBox.setItems(newFarms);
		}
		sortFarms();
	}

	/**
	 * Handler for pushing submit after entering a farm
	 */
	private void farmReportSubmitButtonAction() {
		//System.out.println("User may have selected farmID, Year pressed submit button.");
		sortFarms();
		// Set up for the chart
		ObservableList<ArrayList<StringProperty>> reportData = FXCollections
				.observableArrayList();
		ObservableList<XYChart.Data> lineChartData = FXCollections.observableArrayList();
		ArrayList<StringProperty> reportRow;
		table.getItems().clear();
		Farm userFarm = null;
		// Finding the user selected farm
		for (Farm f : farms) {
			if (f.getFarmID().equals(userFarmChoice)) {
				userFarm = f;
			}
		}
		// Iterate through all months
		for (int month = 0; month < 12; month++) {
			try {
				if (userYearChoice.equals("ALL")) {
					this.userYearChoice = "-1";
				}
				this.textReport = Report.farmReport(userFarm,
						Integer.parseInt(this.userYearChoice), monthItems.get(month));
				//Adding the information from that month to the chart
				reportRow = new ArrayList<StringProperty>();
				reportRow.add(0, new SimpleStringProperty(this.textReport.get(0)));
				reportRow.add(1, new SimpleStringProperty(this.textReport.get(1)));
				reportRow.add(2, new SimpleStringProperty(this.textReport.get(2)));
				reportData.add(reportRow);
				// Adding in the information to the line chart (the picture)
				lineChartData
						.add(new XYChart.Data(month + 1, Double.parseDouble(textReport.get(3))));

			} catch (Exception e) { // Shouldn't be errors, since we are only inputting allowable farms and months

			}
		}
		// If we haven't already done so, add a export button to allow user to export these results
		if (submitGroup.getChildren().size() == 3) {
			Button farmReportExportButton = new Button("Export Report Results");
			farmReportExportButton.setOnAction(e -> this.farmReportExportFile());
			submitGroup.getChildren().add(farmReportExportButton);
		}

		table.setItems(reportData);
		// defining the axes for the line chart
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		// Always label your axes!
		xAxis.setLabel("Month");
		yAxis.setLabel(userFarm.getFarmID() + "'s percent of the total of all farms");
		LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		// Giving line chart a title
		if (this.userYearChoice.equals("-1")) {
			lineChart.setTitle("Farm Report from All Available Years" + System.lineSeparator()
					+ "Farm ID: " + userFarm.getFarmID());
		} else {
			lineChart.setTitle("Farm Report for Year " + this.userYearChoice
					+ System.lineSeparator() + "Farm ID: " + userFarm.getFarmID());
		}
		// Information needed for line chart
		XYChart.Series series = new XYChart.Series();
		for (int i = 0; i < lineChartData.size(); i++) {
			series.getData().add(lineChartData.get(i));
		}
		lineChart.getData().add(series);
		lineChart.autosize();
		lineChart.setLegendVisible(false);

		// Setting the line chart to show to the screen
		tableGroup.setRight(lineChart);
	}

	/**
	 * Creates a file for export with the farm data
	 */
	private void farmReportExportFile() {
		// Setting up the farm report export page prompting user for information
		VBox exportBox = new VBox();
		TextField filePathField = new TextField();
		TextField fileNameField = new TextField();
		filePathField.setPromptText("Ex: /Users/Solly/Desktop");
		fileNameField.setPromptText("Ex: Farm1_2019_Output");
		Button exportFileButton = new Button("Export File");
		exportBox.getChildren().addAll(
				new Label("Enter the path where you would like the file saved: "), filePathField,
				new Label("Enter the name to give the output file: "), fileNameField,
				exportFileButton);
		root.setCenter(exportBox);

		// Called when user decides to export their file
		exportFileButton.setOnAction(userClick -> {
			File filePath = new File(filePathField.getText());
			boolean pathExists = filePath.exists();

			if (pathExists) {
				File fileName = new File(filePath, fileNameField.getText() + ".txt");
				boolean nameAlreadyExists = fileName.exists();
				boolean writeFile = true; // Used to give user option to overwrite file

				// Checking if the user would like to overwrite an existing file
				if (nameAlreadyExists) {
					writeFile = false;
					Alert nameAlert = new Alert(AlertType.CONFIRMATION);
					nameAlert.setTitle(null);
					nameAlert.setContentText(
							"A file with that name already exists." + System.lineSeparator()
									+ "Pressing \"OK\" will overwrite the existing file.");
					final Optional<ButtonType> nameResult = nameAlert.showAndWait();
					if (nameResult.isPresent() && nameResult.get() == ButtonType.OK) {
						writeFile = true;
					}
				}

				// Writing the output file
				if (writeFile) {
					try {
						Farm userFarm = null;
						// Finding the farm the user selected
						for (Farm f : farms) {
							if (f.getFarmID().equals(userFarmChoice)) {
								userFarm = f;
							}
						}

						if (!nameAlreadyExists) {
							fileName.createNewFile();
						}

						FileWriter output = new FileWriter(fileName);
						// The first line of output file tells the user what is in the file
						String titleString = "Farm Report for " + userFarmChoice;
						if (userYearChoice.equals("ALL")) {
							titleString += " using all available data.";
							this.userYearChoice = "-1";
						} else {
							titleString += " for the year " + userYearChoice;
						}

						output.write(titleString + "\n");
						// Second line labels the columns
						output.write("Month, Total Weight, Percent of All Farms\n");

						// Loop through all months to get info from each month
						for (int month = 0; month < 12; month++) {
							this.textReport = Report.farmReport(userFarm,
									Integer.parseInt(this.userYearChoice), monthItems.get(month));
							String monthString = "";
							monthString += textReport.get(0) + ", ";
							monthString += textReport.get(1) + ", ";
							monthString += textReport.get(2) + "\n";
							output.write(monthString);

						}
						output.close();

						// Let the user know it was a success
						Alert confirmExport = new Alert(AlertType.CONFIRMATION);
						confirmExport.setTitle("Success!");
						confirmExport.setContentText("The file was created successfully.");
						confirmExport.showAndWait();
						// Remove the export button until user wants to export another report
						submitGroup.getChildren().remove(3);
						// Taking the user back to the farm report screen
						this.farmReportButtonAction();
					} catch (Exception e) { // Error if there was an issue writing the file
						Alert fileMakingError = new Alert(AlertType.ERROR);
						fileMakingError.setTitle("Error");
						fileMakingError
								.setContentText("Sorry, there was an error writing the file.");
						fileMakingError.showAndWait();
					}
				}
			} else { // Error if the user provided path is invalid
				Alert pathAlert = new Alert(AlertType.ERROR);
				pathAlert.setTitle("Error");
				pathAlert.setContentText("There was an error finding the correct path.");
				pathAlert.showAndWait();
			}
		});
	}

	/**
	 * Handles the submit button for requesting a report for yearly data
	 */
	private void annualReportSubmitButtonAction() {
		sortFarms();
		//Set up for the table and the pie chart
		ObservableList<ArrayList<StringProperty>> reportData = FXCollections
				.observableArrayList();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		ArrayList<StringProperty> reportRow;
		table.getItems().clear();
		// Getting the info from each farm for the year
		for (Farm f : farms) {
			try {
				this.textReport = Report.annualReport(f, Integer.parseInt(this.userYearChoice));
				reportRow = new ArrayList<StringProperty>();
				reportRow.add(0, new SimpleStringProperty(this.textReport.get(0)));
				reportRow.add(1, new SimpleStringProperty(this.textReport.get(1)));
				reportRow.add(2, new SimpleStringProperty(this.textReport.get(2)));
				// Adding the info to the table
				reportData.add(reportRow);

				// Adding the info to the pie chart
				pieChartData.add(new PieChart.Data(this.textReport.get(0),
						Integer.parseInt(this.textReport.get(1))));

			} catch (Exception e) {
				System.out.print("");
			}
		}

		//System.out.println("User Selected Report is produced");

		// If we haven't already done so, add a export button
		if (submitGroup.getChildren().size() == 2) {
			Button annualReportExportButton = new Button("Export Report Results");
			annualReportExportButton.setOnAction(e -> this.annualReportExportFile());
			submitGroup.getChildren().add(annualReportExportButton);
		}

		table.setItems(reportData);
		// Setting the pie chart and the table to show on the screen
		pieChart = new PieChart(pieChartData);
		pieChart.setTitle("Total for Year");
		tableGroup.setRight(pieChart);

	}

	/**
	 * Handles the submit button for requesting a report for monthly data
	 */
	private void monthlyReportSubmitButtonAction() {
		sortFarms();
		// Setting up the table and the pie chart
		ObservableList<ArrayList<StringProperty>> reportData = FXCollections
				.observableArrayList();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		ArrayList<StringProperty> reportRow;
		table.getItems().clear();
		// Looping through the farms, getting the info from the given month
		for (Farm f : farms) {
			try {
				this.textReport = Report.monthlyReport(f, Integer.parseInt(this.userYearChoice),
						this.userMonthChoice);
				reportRow = new ArrayList<StringProperty>();
				reportRow.add(0, new SimpleStringProperty(this.textReport.get(0)));
				reportRow.add(1, new SimpleStringProperty(this.textReport.get(1)));
				reportRow.add(2, new SimpleStringProperty(this.textReport.get(2)));
				// Adding the month info to the table
				reportData.add(reportRow);

				// Adding the month info to the pie chart
				pieChartData.add(new PieChart.Data(this.textReport.get(0),
						Integer.parseInt(this.textReport.get(1))));

			} catch (Exception e) {
				System.out.print("");
			}
		}

		//System.out.println("User Selected Report is produced");

		// If we haven't already done so, add a export button
		if (submitGroup.getChildren().size() == 3) {
			Button monthlyReportExportButton = new Button("Export Report Results");
			monthlyReportExportButton.setOnAction(e -> this.monthlyReportExportFile());
			submitGroup.getChildren().add(monthlyReportExportButton);
		}

		table.setItems(reportData);
		// Setting the pie chart and table to show on the screen
		pieChart = new PieChart(pieChartData);
		pieChart.setTitle("Total for Month");
		tableGroup.setRight(pieChart);

	}

	/**
	 * Handles the submit button for requesting a report for a range of dates
	 */
	private void rangeReportSubmitButtonAction() {
		sortFarms();
		// Setting up the table and the pie chart
		ObservableList<ArrayList<StringProperty>> reportData = FXCollections
				.observableArrayList();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		ArrayList<StringProperty> reportRow;
		table.getItems().clear();
		
		// Looping through the farms, getting the info from that range
		for (Farm f : farms) {
			try {
				this.textReport = Report.rangeReport(f, Integer.parseInt(this.startYearChoice),
						this.startMonthChoice, Integer.parseInt(this.startDateChoice),
						Integer.parseInt(this.endYearChoice), this.endMonthChoice,
						Integer.parseInt(this.endDateChoice));
				reportRow = new ArrayList<StringProperty>();
				reportRow.add(0, new SimpleStringProperty(this.textReport.get(0)));
				reportRow.add(1, new SimpleStringProperty(this.textReport.get(1)));
				reportRow.add(2, new SimpleStringProperty(this.textReport.get(2)));
				// Adding the data to the table
				reportData.add(reportRow);
				// Adding the data to the pie chart
				pieChartData.add(new PieChart.Data(this.textReport.get(0),
						Integer.parseInt(this.textReport.get(1))));

			}
			// Alerting the user if the range is not a valid range
			catch (IllegalArgumentException rangeExcpn) { 
				Alert rangeAlert = new Alert(AlertType.ERROR);
				rangeAlert.setContentText(
						"That is not a valid range. The start date should occur before the end date.");
				rangeAlert.showAndWait();
				if (rangeAlert.getResult() == ButtonType.OK) {
					dateRangeReportButtonAction();
				}
			} catch (Exception e) {
				System.out.print("");
			}
		}

		//System.out.println("User Selected Report is produced");

		// If we haven't already done so, add a export button
		if (endInfo != null && endInfo.getChildren() != null
				&& endInfo.getChildren().size() == 7) {
			Button rangeReportExportButton = new Button("Export Report Results");
			rangeReportExportButton.setOnAction(e -> this.rangeReportExportFile());
			endInfo.getChildren().add(rangeReportExportButton);
		}

		table.setItems(reportData);
		// Setting the pie chart and the table to show on the screen
		pieChart = new PieChart(pieChartData);
		pieChart.setTitle("Total for Range");
		tableGroup.setRight(pieChart);

	}

	/**
	 * Creates an export file for the annual report
	 */
	private void annualReportExportFile() {
		// Setting up the annual report export page, prompting user for infp
		VBox exportBox = new VBox();
		TextField filePathField = new TextField();
		TextField fileNameField = new TextField();
		filePathField.setPromptText("Ex: /Users/Solly/Desktop");
		fileNameField.setPromptText("Ex: 2019_Output");
		Button exportFileButton = new Button("Export File");
		exportBox.getChildren().addAll(
				new Label("Enter the path where you would like the file saved: "), filePathField,
				new Label("Enter the name to give the output file: "), fileNameField,
				exportFileButton);
		root.setCenter(exportBox);

		// Called when user is ready to export their file
		exportFileButton.setOnAction(userClick -> {
			File filePath = new File(filePathField.getText());
			boolean pathExists = filePath.exists();

			if (pathExists) {
				File fileName = new File(filePath, fileNameField.getText() + ".txt");
				boolean nameAlreadyExists = fileName.exists();
				boolean writeFile = true;

				// Checking if the user would like to overwrite an existing file
				if (nameAlreadyExists) {
					writeFile = false;
					Alert nameAlert = new Alert(AlertType.CONFIRMATION);
					nameAlert.setTitle(null);
					nameAlert.setContentText(
							"A file with that name already exists." + System.lineSeparator()
									+ "Pressing \"OK\" will overwrite the existing file.");
					final Optional<ButtonType> nameResult = nameAlert.showAndWait();
					if (nameResult.isPresent() && nameResult.get() == ButtonType.OK) {
						writeFile = true;
					}
				}

				// Writing the output file
				if (writeFile) {
					try {
						if (!nameAlreadyExists) {
							fileName.createNewFile();
						}
						FileWriter output = new FileWriter(fileName);
						// First line of output tells user what is in report
						String titleString = "Farm Report for " + userYearChoice;
						output.write(titleString + "\n");

						// Second line of output labels the columns
						output.write("Farm ID, Total Weight, Percent of All Farms\n");
						// Looping through each farm, getting info for the year
						for (Farm f : farms) {
							String farmString = "";
							this.textReport = Report.annualReport(f,
									Integer.parseInt(this.userYearChoice));
							farmString += textReport.get(0) + ", ";
							farmString += textReport.get(1) + ", ";
							farmString += textReport.get(2) + "\n";
							// Writing that info to the file
							output.write(farmString);
						}
						output.close();

						// Alert the user the file was successfully written
						Alert confirmExport = new Alert(AlertType.CONFIRMATION);
						confirmExport.setTitle("Success!");
						confirmExport.setContentText("The file was created successfully.");
						confirmExport.showAndWait();
						// Remove the annual report export file button until user creates new report
						submitGroup.getChildren().remove(2);
						// Takes user back to annual report page
						this.annualReportButtonAction();
					} catch (Exception e) { // Error writing file
						Alert fileMakingError = new Alert(AlertType.ERROR);
						fileMakingError.setTitle("Error");
						fileMakingError
								.setContentText("Sorry, there was an error writing the file.");
						fileMakingError.showAndWait();
					}
				}
			} else { // Error if user provides a bad path
				Alert pathAlert = new Alert(AlertType.ERROR);
				pathAlert.setTitle("Error");
				pathAlert.setContentText("There was an error finding the correct path.");
				pathAlert.showAndWait();
			}
		});
	}

	/**
	 * Creates an export file with a monthly report
	 */
	private void monthlyReportExportFile() {
		// Setting up the monthly report export page, getting info from user
		VBox exportBox = new VBox();
		TextField filePathField = new TextField();
		TextField fileNameField = new TextField();
		filePathField.setPromptText("Ex: /Users/Solly/Desktop");
		fileNameField.setPromptText("Ex: April2019_Output");
		Button exportFileButton = new Button("Export File");
		exportBox.getChildren().addAll(
				new Label("Enter the path where you would like the file saved: "), filePathField,
				new Label("Enter the name to give the output file: "), fileNameField,
				exportFileButton);
		root.setCenter(exportBox);

		// Called when user is ready to export their file
		exportFileButton.setOnAction(userClick -> {
			File filePath = new File(filePathField.getText());
			boolean pathExists = filePath.exists();
		
			if (pathExists) {
				File fileName = new File(filePath, fileNameField.getText() + ".txt");
				boolean nameAlreadyExists = fileName.exists();
				boolean writeFile = true;

				// Checking if the user would like to overwrite the file if it already exists
				if (nameAlreadyExists) {
					writeFile = false;
					Alert nameAlert = new Alert(AlertType.CONFIRMATION);
					nameAlert.setTitle(null);
					nameAlert.setContentText(
							"A file with that name already exists." + System.lineSeparator()
									+ "Pressing \"OK\" will overwrite the existing file.");
					final Optional<ButtonType> nameResult = nameAlert.showAndWait();
					if (nameResult.isPresent() && nameResult.get() == ButtonType.OK) {
						writeFile = true;
					}
				}

				// Writing the output file
				if (writeFile) {
					try {
						if (!nameAlreadyExists) {
							fileName.createNewFile();
						}
						FileWriter output = new FileWriter(fileName);
						// First line of report tells user what is in report
						String titleString = "Farm Report for " + userMonthChoice + " "
								+ userYearChoice;
						output.write(titleString + "\n");

						// Second line of report label the columns
						output.write("Farm ID, Total Weight, Percent of All Farms\n");
						// Getting info from each farm for the given month
						for (Farm f : farms) {
							String farmString = "";
							this.textReport = Report.monthlyReport(f,
									Integer.parseInt(this.userYearChoice), this.userMonthChoice);
							farmString += textReport.get(0) + ", ";
							farmString += textReport.get(1) + ", ";
							farmString += textReport.get(2) + "\n";
							output.write(farmString);
						}
						output.close();

						// Alert the user the file was successfully created
						Alert confirmExport = new Alert(AlertType.CONFIRMATION);
						confirmExport.setTitle("Success!");
						confirmExport.setContentText("The file was created successfully.");
						confirmExport.showAndWait();
						//Removing the export button until another report is created
						submitGroup.getChildren().remove(3);
						// Takes the user back to the monthly report page
						this.monthlyReportButtonAction();
					} catch (Exception e) { //Error making file
						Alert fileMakingError = new Alert(AlertType.ERROR);
						fileMakingError.setTitle("Error");
						fileMakingError
								.setContentText("Sorry, there was an error writing the file.");
						fileMakingError.showAndWait();
					}
				}
			} else { // Error if user path is not valid
				Alert pathAlert = new Alert(AlertType.ERROR);
				pathAlert.setTitle("Error");
				pathAlert.setContentText("There was an error finding the correct path.");
				pathAlert.showAndWait();
			}
		});
	}

	/**
	 * Creates an export file of data from a range of dates
	 */
	private void rangeReportExportFile() {
		// Setting up the monthly report export page
		VBox exportBox = new VBox();
		TextField filePathField = new TextField();
		TextField fileNameField = new TextField();
		filePathField.setPromptText("Ex: /Users/Solly/Desktop");
		fileNameField.setPromptText("Ex: January-March2019_Output");
		Button exportFileButton = new Button("Export File");
		exportBox.getChildren().addAll(
				new Label("Enter the path where you would like the file saved: "), filePathField,
				new Label("Enter the name to give the output file: "), fileNameField,
				exportFileButton);
		root.setCenter(exportBox);

		// Called when user is ready to export their file
		exportFileButton.setOnAction(userClick -> {
			File filePath = new File(filePathField.getText());
			boolean pathExists = filePath.exists();

			if (pathExists) {
				File fileName = new File(filePath, fileNameField.getText() + ".txt");
				boolean nameAlreadyExists = fileName.exists();
				boolean writeFile = true;

				// Checking if user would like to overwrite a file if it already exists
				if (nameAlreadyExists) {
					writeFile = false;
					Alert nameAlert = new Alert(AlertType.CONFIRMATION);
					nameAlert.setTitle(null);
					nameAlert.setContentText(
							"A file with that name already exists." + System.lineSeparator()
									+ "Pressing \"OK\" will overwrite the existing file.");
					final Optional<ButtonType> nameResult = nameAlert.showAndWait();
					if (nameResult.isPresent() && nameResult.get() == ButtonType.OK) {
						writeFile = true;
					}
				}

				if (writeFile) {
					try {
						if (!nameAlreadyExists) {
							fileName.createNewFile();
						}
						FileWriter output = new FileWriter(fileName);
						// First line of file tells user what is in report
						String titleString = "Farm Report starting at " + this.startMonthChoice
								+ " " + this.startDateChoice + " " + this.startYearChoice;
						titleString += " and ending at " + this.endMonthChoice + " "
								+ this.endDateChoice + " " + this.endYearChoice;
						output.write(titleString + "\n");

						// Second line of file labels the columns
						output.write("Farm ID, Total Weight, Percent of All Farms\n");
						// Getting the info from each farm for that range
						for (Farm f : farms) {
							String farmString = "";
							this.textReport = Report.rangeReport(f,
									Integer.parseInt(this.startYearChoice), this.startMonthChoice,
									Integer.parseInt(this.startDateChoice),
									Integer.parseInt(this.endYearChoice), this.endMonthChoice,
									Integer.parseInt(this.endDateChoice));
							farmString += textReport.get(0) + ", ";
							farmString += textReport.get(1) + ", ";
							farmString += textReport.get(2) + "\n";
							output.write(farmString);
						}
						output.close();

						// Alert the user the file was successfully created
						Alert confirmExport = new Alert(AlertType.CONFIRMATION);
						confirmExport.setTitle("Success!");
						confirmExport.setContentText("The file was created successfully.");
						confirmExport.showAndWait();
						// Remove the export button until another report is created
						endInfo.getChildren().remove(7);
						// Take the user back to dateRange report page
						this.dateRangeReportButtonAction();
					} catch (Exception e) { // Error writing file
						Alert fileMakingError = new Alert(AlertType.ERROR);
						e.printStackTrace();
						fileMakingError.setTitle("Error");
						fileMakingError
								.setContentText("Sorry, there was an error writing the file.");
						fileMakingError.showAndWait();
					}
				}
			} else { // Error if invalid path
				Alert pathAlert = new Alert(AlertType.ERROR);
				pathAlert.setTitle("Error");
				pathAlert.setContentText("There was an error finding the correct path.");
				pathAlert.showAndWait();
			}
		});
	}

	/**
	 * Handles when user selects the report submit button
	 */
	private void reportSubmitButtonAction() {
		sortFarms();
		System.out
				.println("User may have selected farmID, Year, and Month pressed submit button.");
		Farm userFarm = null;
		// Looping through the farms to find the user choice.
		for (Farm f : farms) {
			if (f.getFarmID().equals(userFarmChoice)) {
				userFarm = f;
			}
		}
		// Getting the info from that farm for the specified year and month
		try {
			if (userYearChoice.equals("ALL"))
				this.userYearChoice = "-1";
			this.textReport = Report.farmReport(userFarm, Integer.parseInt(this.userYearChoice),
					this.userMonthChoice);
			//System.out.println("User Selected Report is produced");

			// 
			if (this.textReport == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Invalid Time Range");
				alert.setHeaderText(
						"Year: All, " + "Month: " + this.userMonthChoice + " is not supported.");
				alert.setContentText("We are sorry." + System.lineSeparator()
						+ "We don't support reports for specific months across all years.");
				alert.showAndWait();
			} else { // Setting the graph correctly
				chartGroup.getChildren().setAll(chart, new Label(this.textReport.get(0)),
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

		//Constructor for a given button and textfield
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

	/**
	 * Sorts the farm list alphabetically by farm ID
	 */
	private void sortFarms() {
		farms.sort(new Comparator<Farm>() {
			@Override
			public int compare(Farm farm1, Farm farm2) {
				return farm1.getFarmID().compareToIgnoreCase(farm2.getFarmID());
			}
		});
	}

	// A hander to deal with certain user inputs
	class InputSubmitHandler implements EventHandler<ActionEvent> {
		TextField textBox;
		Button submit;

		InputSubmitHandler(Button submit, TextField textBox) {
			this.textBox = textBox;
		}

		@Override
		public void handle(ActionEvent arg0) {
			try {
				Main.farms = Report.parseFile(textBox.getText(), farms);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(farms.size());
			textBox.clear();

		}
	}

	/**
	 * Main method which launches GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}