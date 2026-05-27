package catadopt.controller;

import catadopt.service.NavigationService;
import javafx.fxml.FXML;

// Alberto
public class WelcomeController {
	
	private NavigationService navigationService;

    /**
     * Inyección del servicio de navegación.
     */
    public void setNavigationService(
            NavigationService navigationService
    ) {
        this.navigationService = navigationService;
    }

    /**
     * Acción del botón "Explorar".
     */
    @FXML
    private void toBrowser() {
        navigationService.goToBrowser();
    }
	
}
