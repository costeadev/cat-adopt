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

public class JavaFXNavigationService implements NavigationService {

    private final Stage stage;
    private final CatApiService apiService;
    private final DatabaseService databaseService;

    private Parent browserRoot;
    private Parent adoptedRoot;
    private Parent welcomeRoot;

    private Scene browserScene;
    private Scene adoptedScene;
    private Scene welcomeScene;

    private BrowserController browserController;
    private AdoptedCatsController adoptedCatsController;

    public JavaFXNavigationService(Stage stage,
                                   CatApiService apiService,
                                   DatabaseService databaseService) {
        this.stage = stage;
        this.apiService = apiService;
        this.databaseService = databaseService;
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
    public void goToWelcome() {
        loadScene("/fxml/welcome_screen.fxml");
    }


    @Override
    public void loadScene(String fxmlFile) {

        try {

            switch (fxmlFile) {

                case "/fxml/browser_view.fxml" -> {

                    if (browserRoot == null) {

                        FXMLLoader loader =
                                new FXMLLoader(getClass().getResource(fxmlFile));

                        browserRoot = loader.load();

                        browserController = loader.getController();

                        browserController.setNavigationService(this);
                        browserController.setApiService(apiService);
                        browserController.setDatabaseService(databaseService);

                        browserController.start();

                        browserScene = new Scene(browserRoot);
                    }

                    stage.setScene(browserScene);
                }

                case "/fxml/adopted_cats_view.fxml" -> {

                    if (adoptedRoot == null) {

                        FXMLLoader loader =
                                new FXMLLoader(getClass().getResource(fxmlFile));

                        adoptedRoot = loader.load();

                        adoptedCatsController = loader.getController();

                        adoptedCatsController.setNavigationService(this);
                        adoptedCatsController.setDatabaseService(databaseService);

                        adoptedCatsController.start();

                        adoptedScene = new Scene(adoptedRoot);
                    }
                    
                    adoptedCatsController.refresh();

                    stage.setScene(adoptedScene);
                }

                case "/fxml/welcome_screen.fxml" -> {

                    if (welcomeRoot == null) {

                        FXMLLoader loader =
                                new FXMLLoader(getClass().getResource(fxmlFile));

                        welcomeRoot = loader.load();

                        WelcomeController controller =
                                loader.getController();

                        controller.setNavigationService(this);

                        welcomeScene = new Scene(welcomeRoot);
                    }

                    stage.setScene(welcomeScene);
                }

                default -> {

                    FXMLLoader loader =
                            new FXMLLoader(getClass().getResource(fxmlFile));

                    Parent root = loader.load();

                    stage.setScene(new Scene(root));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String promptForName() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass()
                            .getResource("/fxml/name_prompt_dialog.fxml"));

            Parent root = loader.load();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Nombre del Gatito");

            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(stage);

            dialogStage.setScene(new Scene(root));

            NamePromptController controller =
                    loader.getController();

            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.getCatName();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}