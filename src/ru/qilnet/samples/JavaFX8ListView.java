package ru.qilnet.samples;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class JavaFX8ListView extends Application {


	@Override
	public void start(Stage primaryStage) {

		//Root Node
		BorderPane root = new BorderPane();

		//Text Node
		Text txtNode = new Text("Selection");
		txtNode.setFont(Font.font("Comic Sans MS", 30));
		txtNode.setFill(Color.BLUE);

		//ListView for Animals
		VBox vbox1 = new VBox(10);

		Text txtAnimal  = new Text("Animals");
		txtAnimal.setFont(Font.font("Comic Sans MS", 18));
		txtAnimal.setFill(Color.GREEN);

		ListView<String> listAnimals = new ListView<String>();
		ObservableList<String> animals = FXCollections.observableArrayList(
				"Dog",
				"Cat",
				"Horse",
				"Lion",
				"Tiger");
		listAnimals.setItems(animals);
		listAnimals.setMaxHeight(250);

		vbox1.getChildren().addAll(txtAnimal, listAnimals);
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setPadding(new Insets(10));

		//ListView for Selection Mode
		VBox vbox2 = new VBox(10);

		Text txtMode  = new Text("Selection Mode");
		txtMode.setFont(Font.font("Comic Sans MS", 18));
		txtMode.setFill(Color.RED);

		ListView<String> listMode = new ListView<String>();
		listMode.setOrientation(Orientation.HORIZONTAL);

		ObservableList<String> mode =FXCollections.observableArrayList(
				"Single",
				"Multiple"
		);
		listMode.setItems(mode);
		listMode.setMaxHeight(250);

		vbox2.getChildren().addAll(txtMode, listMode);
		vbox2.setAlignment(Pos.CENTER);
		vbox2.setPadding(new Insets(10));


		//List Mode Change Listener
		listMode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {

				if(t1.equals("Single"))
					listAnimals.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				else
					listAnimals.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

			}
		});



		//Button and Action
		Button btnShow = new Button("Show");
		btnShow.setMinWidth(100);
		BorderPane.setAlignment(btnShow, Pos.CENTER);
		BorderPane.setMargin(btnShow, new Insets(10));


		//setOnAction
		btnShow.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				//Check if ListMode ListView has a selected Item
				if(listMode.getSelectionModel().isEmpty()){

					txtNode.setText("Select a ListView Selection Mode");

				}else{
					//Check if ListAnimals ListView has a selected Item
					if(listAnimals.getSelectionModel().isEmpty()){

						txtNode.setText("Select an Animal");

					}else{
						//If ListView for Animals is in Single selection mode....
						if(listMode.getSelectionModel().getSelectedItem().equals("Single")){

							txtNode.setText((listAnimals.getSelectionModel().getSelectedIndex()) +
									" - " +
									listAnimals.getSelectionModel().getSelectedItem() + "");

							//If ListView for Animals is in Multiple selection mode....
						}else{

							String txtSelect = "";

							ObservableList<String> select = listAnimals.getSelectionModel().getSelectedItems();
							for (Object o : select) {
								txtSelect = txtSelect + animals.indexOf(o) + " - " + o + " ";
							}
							txtNode.setText(txtSelect);
						}
					}
					//Clear Selection
					listAnimals.getSelectionModel().clearSelection();


				}
			}
		});

		root.setRight(btnShow);
		root.setCenter(vbox1);
		root.setLeft(vbox2);
		root.setBottom(txtNode);

		Scene scene = new Scene(root, 600, 350);

		primaryStage.setTitle("JavaFX 8 - ListView");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}

