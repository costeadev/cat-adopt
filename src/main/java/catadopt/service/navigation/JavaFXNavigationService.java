package catadopt.service.navigation;

import java.io.IOException;

import catadopt.controller.AdoptedCatsController;
import catadopt.controller.BrowserController;
import catadopt.controller.NamePromptController;
import catadopt.controller.WelcomeController;
import catadopt.service.CatApiService;
import catadopt.service.DatabaseService;
import catadopt.service.NavigationService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Sandra

public class JavaFXNavigationService implements NavigationService {
	
	private Stage stage;
	private CatApiService apiService;
	private DatabaseService databaseService;

	public JavaFXNavigationService(Stage stage, CatApiService apiService, DatabaseService databaseService) {
		this.stage = stage;
		this.apiService = apiService;
		this.databaseService = databaseService;
	}
	
	@Override
	public void loadScene(String fxmlFile) {
		try {
			// Asumiendo los FXML  están en la carpeta de recursos
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
			Parent root = loader.load();

			// Obtener el controlador asignado en el FXML
			Object controller = loader.getController();

			// Inyección dinámica de dependencias según el controlador cargado
			if (controller instanceof WelcomeController) {
				WelcomeController welcomeCtrl = (WelcomeController) controller;
				welcomeCtrl.setNavigationService(this);
			} else if (controller instanceof BrowserController) {
			    BrowserController browserCtrl = (BrowserController) controller;
			    browserCtrl.setNavigationService(this);
			    browserCtrl.setApiService(apiService);
			    browserCtrl.setDatabaseService(databaseService);
			    browserCtrl.start();
			} else if (controller instanceof AdoptedCatsController) {
				AdoptedCatsController adoptedCtrl = (AdoptedCatsController) controller;
				adoptedCtrl.setNavigationService(this);
				adoptedCtrl.setDatabaseService(databaseService);
			}

			// Cambiar la escena del Stage principal
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			System.err.println("Error al cargar la vista: " + fxmlFile);
			e.printStackTrace();
		}
	}
		
    @Override
    public void goToWelcome() {
    	loadScene("/fxml/welcome_screen.fxml");
    }

    @Override
    public void goToBrowser() {
    	loadScene("/fxml/browser_view.fxml");
    }

    @Override
    public void goToAdoptedCats() {
    	loadScene("/fxml/adopted_cats_view.fxml");
    }

    @Override
    public String promptForName() {
    	 try {
         	// Carga del diálogo 
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/name_prompt_dialog.fxml"));
             Parent root = loader.load();

          // Crear una nueva ventana (Stage) secundaria para el diálogo
             Stage dialogStage = new Stage();
             dialogStage.setTitle("Nombre del Gatito");
             dialogStage.initModality(Modality.WINDOW_MODAL);
             dialogStage.initOwner(stage);
             
             Scene scene = new Scene(root);
             dialogStage.setScene(scene);

             // Obtener el controlador del diálogo para extraer el nombre ingresado
             NamePromptController controller = loader.getController();
             controller.setDialogStage(dialogStage);

             dialogStage.showAndWait(); // Pausa la ejecución hasta que se cierre la ventana

          
             return controller.getCatName();// Devuelve el texto definitivo 
         } catch (IOException e) {
         	System.err.println("Error al abrir el diálogo name_prompt_dialog.fxml");
             e.printStackTrace();
             return null;
         }
     
    }

}