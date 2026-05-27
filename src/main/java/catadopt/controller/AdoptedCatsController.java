package catadopt.controller;

import java.util.List;

import catadopt.model.Cat;
import catadopt.service.DatabaseService;
import catadopt.service.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

// Alberto
public class AdoptedCatsController {

	private Scene scene;

	private NavigationService navigationService;

	private DatabaseService databaseService;

	@FXML
	private GridPane adoptedCatsContainer;
	
	List<Cat> adoptedCats = null;
	
	private VBox selectedCell = null;
	
	private static final String SELECTED_STYLE =
		    "-fx-border-color: dodgerblue;" +
		    "-fx-border-width: 2;" +
		    "-fx-background-color: rgba(30,144,255,0.1);";

		private static final String NORMAL_STYLE =
		    "-fx-border-color: lightgray;" +
		    "-fx-padding: 10;";

	/**
	 * Inyección navegación.
	 */
	public void setNavigationService(NavigationService navigationService) {
		this.navigationService = navigationService;
	}

	/**
	 * Inyección base de datos.
	 */
	public void setDatabaseService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}
	
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void start() {
		setupGrid();
		loadAdoptedCats();
		updateAdoptedCats();
		System.out.println(adoptedCatsContainer);
		System.out.println("beep");
	}
	
	public void setupGrid() {
	    adoptedCatsContainer.getColumnConstraints().clear();

	    adoptedCatsContainer.setMaxWidth(Double.MAX_VALUE);
	    adoptedCatsContainer.setMinWidth(0);
	}
	
	
	public void updateAdoptedCats() {

	    adoptedCatsContainer.getChildren().clear();

	    if (adoptedCats == null || adoptedCats.isEmpty()) {
	        System.out.println("No adopted cats to display");
	        return;
	    }

	    int maxCols = 4;

	    for (int i = 0; i < adoptedCats.size(); i++) {

	        Cat cat = adoptedCats.get(i);

	        int col = i % maxCols;
	        int row = i / maxCols;

	        // 🖼️ image
	        ImageView imageView = new ImageView(new Image(cat.getImageUrl(), true));
	        imageView.setFitWidth(140);
	        imageView.setFitHeight(140);
	        imageView.setPreserveRatio(true);

	        // 🔘 button
	        Button button = new Button("Select");
	        button.setText(cat.getName());
	        
	        button.setOnMouseClicked(e -> {
	        	onRenameClick(e);
	        });

	        // 📦 cell
	        VBox cell = new VBox(10, imageView, button);
	        cell.setUserData(cat);
	        
	        cell.setStyle(NORMAL_STYLE);
	        
	        // select
	        cell.setOnMouseClicked(e -> {
	        	selectCell(cell);
	        });

	        // 📏 FIXED HEIGHT
	        cell.setPrefHeight(200);
	        cell.setMinHeight(200);
	        cell.setMaxHeight(200);

	        // 📏 HARD WIDTH = container / 4
	        cell.prefWidthProperty().bind(
	            adoptedCatsContainer.widthProperty().divide(4)
	        );

	        // ✂️ clip overflow
	        Rectangle clip = new Rectangle();
	        clip.widthProperty().bind(cell.widthProperty());
	        clip.heightProperty().bind(cell.heightProperty());
	        cell.setClip(clip);

	        // 🎨 simple style
	        cell.setStyle(
	            "-fx-border-color: lightgray;" +
	            "-fx-padding: 10;"
	        );

	        adoptedCatsContainer.add(cell, col, row);
	    }
	}

	private void selectCell(VBox cell) {

	    // 🔄 unselect previous
	    if (selectedCell != null) {
	        selectedCell.setStyle(NORMAL_STYLE);
	    }

	    // 🎯 select new
	    selectedCell = cell;
	    selectedCell.setStyle(SELECTED_STYLE);
	}
	
	/**
	 * Carga gatos adoptados.
	 */
	public void loadAdoptedCats() {

		adoptedCats = databaseService.getAdoptedCats();

		for (Cat cat : adoptedCats) {

			System.out.println(cat.getName());
		}
		
		// TODO setUserData del objeto gato la cell (VBox)
	}

	/**
	 * Botón volver.
	 */
	@FXML
	private void onBackClick() {
		navigationService.goToBrowser();
	}
	
	@FXML
	private void onDeleteClick() {
		if (selectedCell != null) {
			adoptedCats.remove(selectedCell.getUserData());
			updateAdoptedCats();
		}
	}
	
	@FXML
	private void onRenameClick(MouseEvent e) {
		
		Button renameBtn = (Button) e.getSource();
		VBox cell = (VBox) renameBtn.getParent();
		Cat cat = (Cat) cell.getUserData();
		
		String newName = navigationService.promptForName();
		cat.setName(newName);
		
		databaseService.renameCat(cat);
		updateAdoptedCats();
	}


}
