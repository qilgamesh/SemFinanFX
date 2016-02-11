package ru.qilnet.samples;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFX8TableView extends Application {

	@Override
	public void start(Stage primaryStage) {

		//Header
		Text txtHeader = new Text("Soccer Teams TableView");
		txtHeader.setFont(Font.font(20));
		txtHeader.setFill(Color.GREEN);

		//TableView + ObservableList
		TableView<SoccerTeam> teamTable = new TableView<>();
		ObservableList<SoccerTeam> teams = FXCollections.observableArrayList(
				new SoccerTeam("Barcelona FC", "Spain", "Camp Nou", "www.fcbarcelona.es"),
				new SoccerTeam("Juventus FC", "Italy", "Juventus Stadium", "www.juventus.com/"),
				new SoccerTeam("FC Bayern Munich", "Germany", "Allianz Arena", "www.fcbayern.de"),
				new SoccerTeam("Manchester United FC", "England", "Old Trafford", "www.manutd.com"),
				new SoccerTeam("Paris Saint-Germain FC", "France", "Parc des Princes", "www.psg.fr")
		);

		teamTable.setItems(teams);
		teamTable.setMaxSize(500, 600);
		teamTable.setMinSize(250, 300);

		//Set TableColumns
		TableColumn colClubInfo = new TableColumn("Club Information");

		TableColumn<SoccerTeam, String> colName = new TableColumn<>("Team Name");
		colName.setCellValueFactory(new PropertyValueFactory<SoccerTeam, String>("name"));
		colName.setMinWidth(teamTable.getMaxWidth()/4);


		TableColumn<SoccerTeam, String> colCountry = new TableColumn<>("Country");
		colCountry.setCellValueFactory(new PropertyValueFactory<SoccerTeam, String>("country"));
		colCountry.setMinWidth(teamTable.getMaxWidth()/4);

		TableColumn<SoccerTeam, String> colStadium = new TableColumn<>("Stadium");
		colStadium.setCellValueFactory(new PropertyValueFactory<SoccerTeam, String>("stadium"));
		colStadium.setMinWidth(teamTable.getMaxWidth()/4);

		TableColumn<SoccerTeam, String> colWeb = new TableColumn<>("Web Site");
		colWeb.setCellValueFactory(new PropertyValueFactory<SoccerTeam, String>("webSite"));
		colWeb.setMinWidth(teamTable.getMaxWidth()/4);


		//Asign Columns to TableView
		colClubInfo.getColumns().addAll(colName, colCountry, colStadium);
		teamTable.getColumns().addAll(colClubInfo, colWeb);

		//Nodes
		Text txtName = new Text("Name");
		Text txtCountry = new Text("Country");
		Text txtStadium = new Text("Stadium");
		Text txtWeb = new Text("WebSite");
		Text txtNotification = new Text("Notifications");
		txtNotification.setFont(Font.font(20));
		txtNotification.setFill(Color.BLUE);

		TextField fldName = new TextField();
		TextField fldCountry = new TextField();
		TextField fldStadium = new TextField();
		TextField fldWeb = new TextField();

		Button btnAdd = new Button("Add");
		btnAdd.setMinWidth(85);

		//Add Information to TableView
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				if(fldName.getText().isEmpty() || fldCountry.getText().isEmpty() || fldStadium.getText().isEmpty() ||
						fldWeb.getText().isEmpty()){
					txtNotification.setText("Please Add information to all the fields");
				}else{
					teams.add(new SoccerTeam(
							fldName.getText(),
							fldCountry.getText(),
							fldStadium.getText(),
							fldWeb.getText()));

				}


			}
		});

		Button btnReplace = new Button("Replace");
		btnReplace.setMinWidth(85);

		//Replace Information in TableView
		btnReplace.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {

				if(teamTable.getSelectionModel().isEmpty() ||
						(fldName.getText().isEmpty() && fldCountry.getText().isEmpty() && fldStadium.getText().isEmpty() &&
								fldWeb.getText().isEmpty())){

					txtNotification.setText("Please Add information to all the fields and Select an Item in the Table");

				}else{
					String strName = teams.get(teamTable.getSelectionModel().getSelectedIndex()).getName();
					String strCountry = teams.get(teamTable.getSelectionModel().getSelectedIndex()).getCountry();
					String strStadium = teams.get(teamTable.getSelectionModel().getSelectedIndex()).getStadium();
					String strWeb = teams.get(teamTable.getSelectionModel().getSelectedIndex()).getWebSite();
					if(!fldName.getText().isEmpty()){
						strName = fldName.getText();
					}
					if(!fldCountry.getText().isEmpty()){
						strCountry = fldCountry.getText();
					}
					if(!fldStadium.getText().isEmpty()){
						strStadium = fldStadium.getText();
					}
					if(!fldWeb.getText().isEmpty()){
						strWeb = fldWeb.getText();

					}

					teams.set(teamTable.getSelectionModel().getSelectedIndex(),
							new SoccerTeam(strName, strCountry, strStadium, strWeb));
				}
			}
		});

		Button btnRemove = new Button("Remove");
		btnRemove.setMinWidth(85);

		//Remove Row from TableView
		btnRemove.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				if(teamTable.getSelectionModel().isEmpty()){
					txtNotification.setText("Please Select an Item from the List");
				}else{
					teams.remove(teamTable.getSelectionModel().getSelectedItem());
				}

			}
		});

		//Adding Change Listener to TableView
		teams.addListener(new ListChangeListener() {

			@Override
			public void onChanged(ListChangeListener.Change change) {

				txtNotification.setText("TableView Successfully changed");
			}
		});

		//Layouts
		GridPane center = new GridPane();
		center.setHgap(10);
		center.setVgap(10);
		center.setPadding(new Insets(50));

		center.add(txtHeader, 0, 0, 5, 1);
		GridPane.setHalignment(txtHeader, HPos.CENTER);
		center.add(teamTable, 0, 1, 5, 5);

		center.add(txtName, 0, 7);
		center.add(txtCountry, 1, 7);
		center.add(txtStadium, 2, 7);
		center.add(txtWeb, 3, 7);

		center.add(fldName, 0, 8);
		center.add(fldCountry, 1, 8);
		center.add(fldStadium, 2, 8);
		center.add(fldWeb, 3, 8);

		VBox right = new VBox(15);
		right.setAlignment(Pos.CENTER);
		right.setPadding(new Insets(5, 100,5,5));

		right.getChildren().addAll(btnAdd, btnReplace, btnRemove);

		BorderPane root = new BorderPane();

		root.setCenter(center);
		root.setRight(right);
		root.setBottom(txtNotification);

		Scene scene = new Scene(root, 800, 500);

		primaryStage.setTitle("JavaFX 8 - TableView");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	//Class example
	public static class SoccerTeam{

		private final SimpleStringProperty name;
		private final SimpleStringProperty country;
		private final SimpleStringProperty stadium;
		private final SimpleStringProperty webSite;

		private SoccerTeam(String strName, String strCountry, String strStadium,String strWeb) {
			this.name = new SimpleStringProperty(strName);
			this.country = new SimpleStringProperty(strCountry);
			this.stadium = new SimpleStringProperty(strStadium);
			this.webSite = new SimpleStringProperty(strWeb);

		}

		public String getName() {
			return name.get();
		}

		public String getCountry() {
			return country.get();
		}

		public String getStadium() {
			return stadium.get();
		}

		public String getWebSite() {
			return webSite.get();
		}

		public void setName(String strName) {
			name.set(strName);
		}

		public void setCountry(String strCountry) {
			country.set(strCountry);
		}

		public void setStadium(String strStadium) {
			stadium.set(strStadium);
		}

		public void setWebSite(String strWeb) {
			webSite.set(strWeb);
		}
	}


	public static void main(String[] args) {
		launch(args);
	}

}
