package catadopt.controller;

import java.util.List;

import catadopt.model.Cat;
import catadopt.service.DatabaseService;
import catadopt.service.NavigationService;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

// Alberto
public class AdoptedCatsController {
	
	private NavigationService navigationService;

    private DatabaseService databaseService;

    @FXML
    private FlowPane adoptedCatsContainer;

    /**
     * Inyección navegación.
     */
    public void setNavigationService(
            NavigationService navigationService
    ) {
        this.navigationService = navigationService;
    }

    /**
     * Inyección base de datos.
     */
    public void setDatabaseService(
            DatabaseService databaseService
    ) {
        this.databaseService = databaseService;
    }

    /**
     * Carga gatos adoptados.
     */
    public void loadAdoptedCats() {

        List<Cat> adoptedCats =
                databaseService.getAdoptedCats();

        for (Cat cat : adoptedCats) {

            System.out.println(cat.getName());
        }
    }

    /**
     * Botón volver.
     */
    @FXML
    private void onBackClick() {

        navigationService.goToBrowser();
    } 

}
