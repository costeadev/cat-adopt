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

	private List<Cat> cachedCats;
	private List<Image> cachedImages;
	int catIndex;

	/*
	 * Contenedor visual donde se mostrarán los gatos.
	 */
	@FXML
	private ImageView catViewer;

	@FXML
	private void initialize() {
		catIndex = 0;
	}

	public void start() {

	    if (apiService == null) {
	        return;
	    }

	    loadCats();

	    if (cachedCats.isEmpty()) {
	        return;
	    }

	    catIndex = 0;
	    updateCat();
	}

	/**
	 * Inyección del servicio de navegación.
	 */
	public void setNavigationService(NavigationService navigationService) {
		this.navigationService = navigationService;
	}

	/**
	 * Inyección del servicio API.
	 */
	public void setApiService(CatApiService apiService) {
		this.apiService = apiService;
		loadCats();

		if (!cachedCats.isEmpty()) {
			catIndex = 0;
			updateCat();
		}
	}

	/**
	 * Inyección del servicio de base de datos.
	 */
	public void setDatabaseService(DatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	/**
	 * Carga gatos desde la API.
	 */
	public void loadCats() {

	    if (cachedCats == null || cachedCats.isEmpty()) {
	        cachedCats = apiService.fetchCats();
	    }

	    cachedImages = new ArrayList<>();

	    for (Cat cat : cachedCats) {
	        cachedImages.add(new Image(cat.getImageUrl(), true));
	    }
	}

	public void updateCat() {
	    if (cachedImages == null || cachedImages.isEmpty()) {
	    	return;
	    }

	    catViewer.setImage(cachedImages.get(catIndex));
	}

	public void nextCat() {
	    if (cachedCats == null || cachedCats.isEmpty()) {
	    	return;
	    }

	    // Al pasar el gato en la última posicion, cambiamos a la primera posición, haciendo la vuelta completa
	    catIndex = (catIndex + 1) % cachedCats.size();
	    updateCat();
	}

	public void prevCat() {
	    if (cachedCats == null || cachedCats.isEmpty()) {
	    	return;
	    }

	    // Al pasar el gato en la primera posición, cambiamos a la última posición, haciendo la vuelta completa
	    catIndex = (catIndex - 1 + cachedCats.size()) % cachedCats.size(); 
	    updateCat();
	}

	/**
	 * Acción del botón Adoptar.
	 */
	public void adoptCat() {

		Cat currentCat = cachedCats.get(catIndex);

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

		// Elimina de las listas visible
		cachedCats.remove(catIndex);
		cachedImages.remove(catIndex);

		// Refresca gato
		if (!cachedCats.isEmpty()) {
			if (catIndex != 0) {
				prevCat();
			} else {
				updateCat();
			}
		} 
		else {
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

}
