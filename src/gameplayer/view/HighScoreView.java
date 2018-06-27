
package gameplayer.view;
import java.util.List;
import java.util.Map;

import data.DataRead;
import data.DataWrite;
import gameplayer.buttons.IGamePlayerButton;
import gameplayer.buttons.RestartButton;
import gameplayer.buttons.SwitchGameButton;
import gameplayer.controller.Controller;
import gameplayer.menu.PauseMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HighScoreView extends BranchScreenView {
	private Scene highScoreScene;
	private Stage myStage;
	private TableView highScoreTable;
	private TableColumn firstCol;
	private TableColumn secondCol;
	private BorderPane highScorePane;	
	private HBox requestRecordLayout;
	private Button submitButton;
	private TextField userNameField;
	private Double finalScore;
	private String finalGameName;
	private Button facebookAuthButton;
	private final String domain = "https://www.google.com/";
	private final String appId = "190769674886367";
	private final String authUrl = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="+appId+"&redirect_uri="+domain+"&scope=public_profile,"
			+ "user_photos,user_friends,user_hometown";
	private PauseMenu pauseMenu;
	
	//Data Structure to Display High Scores
	private ObservableList<Person> data;
	private RestartButton restartButton;
	private SwitchGameButton switchButton;
	
	/**
	 * HighScoreView is 
	 * @param gameName 
	 * @param score
	 */
	public HighScoreView(Stage stage) {
		myStage = stage;
		setupHighScoreTable();
		//displayHighScores();
		highScoreScene = initializeScreen();
		showRecordInput();
		//showScores();
	}
	
	@Override
	public Scene initializeScreen() {
		highScorePane = new BorderPane();
		switchButton = new SwitchGameButton(myStage);
		highScorePane.setRight(switchButton);
		Scene currentScene = new Scene(highScorePane,WIDTH_SIZE,HEIGHT_SIZE);
		return currentScene;
	}

	public void setGameName(String gameName) {
		finalGameName = gameName;
	}
	
	public void setScore(Double score) {
		finalScore = score;
	}
	
	@Override
	public Scene getScene() {
		return highScoreScene;
	}
	
	public void setupHighScoreTable() {
		highScoreTable = new TableView();
		firstCol = new TableColumn("Name");
		secondCol = new TableColumn("Score");
		secondCol.setSortType(TableColumn.SortType.DESCENDING);
		firstCol.prefWidthProperty().bind(highScoreTable.widthProperty().divide(2));
		secondCol.prefWidthProperty().bind(highScoreTable.widthProperty().divide(2));
		highScoreTable.getColumns().addAll(firstCol,secondCol);
		highScoreTable.setMaxHeight(500);
		highScoreTable.setMaxWidth(300);
	}
	
	public void displayHighScores() {
        firstCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("name"));
        secondCol.setCellValueFactory(
                new PropertyValueFactory<Person, Double>("score"));
        highScoreTable.setItems(data);
	}
	
	/**
	 * Displays a textfield that allows a person to enter their name.
	 */
	public void showRecordInput() {
		requestRecordLayout = new HBox(50);
		requestRecordLayout.setAlignment(Pos.CENTER);
		submitButton = new Button("Submit");
		facebookAuthButton = new Button("FB");
		userNameField = new TextField();
		userNameField.setMaxWidth(200);
		submitButton.setOnAction(e->{
			addHighScore(userNameField.getText(), finalScore); 
			showScores();
		});
		//facebookAuthButton.setOnAction(e->{authenticateUser();});
		requestRecordLayout.getChildren().addAll(userNameField, submitButton, facebookAuthButton);
		highScorePane.setCenter(requestRecordLayout);
	}
	
	
	public void showScores() {
		getHighScores();
		displayHighScores();
		highScorePane.setCenter(highScoreTable);
		//requestRecordLayout.getChildren().clear();
	}
	
	
	/**
	 * Method to add High Score to the High Score Data File
	 */
	public void addHighScore(String name, Double score) {
		Person recordHolder = new Person(name, score);
		DataWrite.saveHighscore(recordHolder);
		getHighScores();
		displayHighScores();
		data.add(recordHolder);
	}
	
	
	/**
	 * Method to obtain High Scores from the Data File
	 */
	public void getHighScores(){
		Map<String, List<Person>> highScoreMap = DataRead.loadHighscore(); //gives me map
		data = FXCollections.observableArrayList(highScoreMap.get(finalGameName));
		System.out.println(data.size());
	}
	
//	/**
//	 * Facebook Authentification Method 
//	 */
//	private void authenticateUser() {
//		System.setProperty("webdriver.chrome.driver", "/Users/Ryan/cs308/chromedriver");
//		WebDriver driver = new ChromeDriver();
//		driver.get(authUrl);
//		String accessToken;
//		while(true) {
//			if(!driver.getCurrentUrl().contains("facebook.com")) {
//				String url = driver.getCurrentUrl();
//				accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
//				accessToken = accessToken.split("&")[0];
//				driver.quit();
//				FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_9);
//				User user = fbClient.fetchObject("me", User.class);
//				userNameField.setText(user.getName());
//				break;
//			}	
//		}
//	
//	}
	

	public List<IGamePlayerButton> getButtons() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
