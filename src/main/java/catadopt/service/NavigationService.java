package catadopt.service;

public interface NavigationService {
	void goToWelcome();
	void goToBrowser();
	void goToAdoptedCats();
	String promptForName(); // abre el diálogo y devuelve el nombre introducido
}
