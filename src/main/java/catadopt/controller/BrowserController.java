package catadopt.controller;

import java.util.ArrayList;
import java.util.List;

import catadopt.model.Cat;
import catadopt.service.CatApiService;
import catadopt.service.DatabaseService;
import catadopt.service.NavigationService;
import javafx.fxml.FXML;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Alberto
public class BrowserController {

	private NavigationService navigationService;

    private CatApiService apiService;

    private DatabaseService databaseService;

    List<Cat> cats = new ArrayList();
    int catIndex = 0;


    /*
     * Contenedor visual donde se mostrarán
     * los gatos.
     */
    @FXML
    private ImageView catViewer;

    /**
     * Inyección del servicio de navegación.
     */
    public void setNavigationService(
            NavigationService navigationService
    ) {
        this.navigationService = navigationService;
    }

    /**
     * Inyección del servicio API.
     */
    public void setApiService(
            CatApiService apiService
    ) {
        this.apiService = apiService;
    }

    /**
     * Inyección del servicio de base de datos.
     */
    public void setDatabaseService(
            DatabaseService databaseService
    ) {
        this.databaseService = databaseService;
    }

    /**
     * Carga gatos desde la API.
     */
    public void loadCats() {

        if (cats.isEmpty()) {
            cats = apiService.fetchCats();
        }

        /*
         * Aquí se crearían tarjetas visuales
         * para cada gato.
         *
    
         */
        for (Cat cat : cats) {

            System.out.println(cat.getName());
        }
    }

    public void updateCat() {
        Cat currentCat = cats.get(catIndex);
        Image image = new Image(currentCat.getImageUrl());
        catViewer.setImage(image);
    }

    public void prevCat() {
        if (catIndex > 0) { // Indice está dentro de rango
            catIndex--;
            updateCat();
        }
    }

    public void nextCat() {
        if (catIndex < cats.size() - 1) { // Indice está dentro de rango
            catIndex++;
            updateCat();
        }
    }

    /**
     * Acción del botón Adoptar.
     */
    public void adoptCat() {

        Cat currentCat = cats.get(catIndex);

        // Pide nombre al usuario
        String newName = navigationService.promptForName();

        // Si canceló, no continuar
        if (newName == null || newName.isBlank()) {
            return;
        }

        // Cambia nombre del gato
        currentCat.setName(newName);

        // Guarda en DB
        databaseService.adoptCat(currentCat);

        // Refresca gato
        cats.remove(catIndex);

        if (!cats.isEmpty()) {
            prevCat();
        } else {
        	loadCats();
        }
    }

    /**
     * Navega a pantalla adoptados.
     */
    @FXML
    private void toAdoptedCats() {
        navigationService.goToAdoptedCats();
    }

    //@Override
    public void initialize() {
        loadCats();
    }
    
}
