package catadopt.service.navigation;

import catadopt.service.CatApiService;
import catadopt.service.DatabaseService;
import catadopt.service.NavigationService;
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
    public void goToWelcome() {
    	// TODO implementar
    }

    @Override
    public void goToBrowser() {
    	// TODO implementar
    }

    @Override
    public void goToAdoptedCats() {
    	// TODO implementar
    }

    @Override
    public String promptForName() {
    	// TODO implementar
        return null;
    }
}