package catadopt;

import catadopt.service.CatApiService;
import catadopt.service.DatabaseService;
import catadopt.service.NavigationService;
import catadopt.service.api.MockCatApiService;
import catadopt.service.database.SQLiteDatabaseService;
import catadopt.service.navigation.JavaFXNavigationService;
import javafx.application.Application;
import javafx.stage.Stage;

// Alberto
public class App extends Application {

	// Este archivo no se toca! :)
	
	@Override
	public void start(Stage stage) throws Exception {
		CatApiService apiService = new MockCatApiService();
		DatabaseService databaseService = new SQLiteDatabaseService();
		NavigationService navigationService = new JavaFXNavigationService(stage, apiService, databaseService);
		
		navigationService.goToWelcome();
		stage.setTitle("CatAdopt");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
