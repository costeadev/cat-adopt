package catadopt.controller;

import java.util.List;

import catadopt.model.Cat;
import catadopt.service.DatabaseService;
import catadopt.service.NavigationService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class AdoptedCatsController {

	private Scene scene;

	private NavigationService navigationService;

	private DatabaseService databaseService;

	@FXML
	private GridPane adoptedCatsContainer;

	List<Cat> adoptedCats = null;

	private VBox selectedCell = null;

	private static final String NORMAL_STYLE = "-fx-background-color: white;" + "-fx-border-color: #DDDDDD;"
			+ "-fx-border-radius: 12;" + "-fx-background-radius: 12;" + "-fx-padding: 12;"
			+ "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 10,0,0,2);";

	private static final String SELECTED_STYLE = "-fx-background-color: #EAF4FF;" + "-fx-border-color: dodgerblue;"
			+ "-fx-border-width: 2;" + "-fx-border-radius: 12;" + "-fx-background-radius: 12;" + "-fx-padding: 12;"
			+ "-fx-effect: dropshadow(gaussian, rgba(30,144,255,0.30), 15,0,0,2);";

	public void setNavigationService(NavigationService navigationService) {
		this.navigationService = navigationService;
	}

	public void setDatabaseService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void start() {
		setupGrid();
	}
	
	public void refresh() {
		loadAdoptedCats();
		updateAdoptedCats();
	}

	public void setupGrid() {
		adoptedCatsContainer.getColumnConstraints().clear();
		adoptedCatsContainer.setHgap(20);
		adoptedCatsContainer.setVgap(20);
		adoptedCatsContainer.setAlignment(Pos.TOP_CENTER);
	}

	public VBox createCell(Cat cat) {

		ImageView imageView = new ImageView(new Image(cat.getImageUrl(), true));

		imageView.setFitWidth(140);
		imageView.setFitHeight(140);
		imageView.setPreserveRatio(false);

		Rectangle clip = new Rectangle(140, 140);

		clip.setArcWidth(20);
		clip.setArcHeight(20);
		imageView.setClip(clip);

		Text nameText = new Text(cat.getName());

		nameText.setStyle("-fx-font-size:16;" + "-fx-font-weight:bold;");

		Button renameButton = new Button("Renombrar");

		renameButton.setOnMouseClicked(this::onRenameClick);

		VBox cell = new VBox(10, imageView, nameText, renameButton);

		cell.setUserData(cat);

		cell.setAlignment(Pos.CENTER);

		cell.setStyle(NORMAL_STYLE);

		cell.setPrefSize(180, 260);
		cell.setMinSize(180, 260);
		cell.setMaxSize(180, 260);

		cell.setOnMouseClicked(e -> selectCell(cell));

		cell.setOnMouseEntered(e -> {
			if (cell != selectedCell) {
				cell.setStyle(NORMAL_STYLE + "-fx-background-color:#F7F7F7;");
			}
		});

		cell.setOnMouseExited(e -> {
			if (cell != selectedCell) {
				cell.setStyle(NORMAL_STYLE);
			}
		});

		return cell;
	}

	public void updateAdoptedCats() {

		adoptedCatsContainer.getChildren().clear();

		if (adoptedCats == null || adoptedCats.isEmpty()) {
			Text text = new Text("No hay gatos adoptados");
			adoptedCatsContainer.add(text, 0, 0);
			adoptedCatsContainer.setAlignment(Pos.CENTER);
			return;
		}

		int maxCols = 4;

		for (int i = 0; i < adoptedCats.size(); i++) {
			Cat cat = adoptedCats.get(i);
			int col = i % maxCols;
			int row = i / maxCols;
			
			VBox cell = createCell(cat);
			adoptedCatsContainer.add(cell, col, row);
		}
	}

	private void selectCell(VBox cell) {

		if (selectedCell != null) {

			selectedCell.setStyle(NORMAL_STYLE);

		}

		selectedCell = cell;

		selectedCell.setStyle(SELECTED_STYLE);
	}

	public void loadAdoptedCats() {
		adoptedCats = databaseService.getAdoptedCats();
	}

	@FXML
	private void onBackClick() {

		navigationService.goToBrowser();
	}

	@FXML
	private void onDeleteClick() {

		if (selectedCell != null) {

			Cat cat = (Cat) selectedCell.getUserData();

			adoptedCats.remove(cat);

			databaseService.removeCat(cat);

			selectedCell = null;

			updateAdoptedCats();
		}
	}

	@FXML
	private void onRenameClick(MouseEvent e) {

		Button btn = (Button) e.getSource();

		VBox cell = (VBox) btn.getParent();

		Cat cat = (Cat) cell.getUserData();

		String newName = navigationService.promptForName();

		if (newName != null && !newName.isBlank()) {

			cat.setName(newName);

			databaseService.renameCat(cat);

			updateAdoptedCats();
		}
	}
}