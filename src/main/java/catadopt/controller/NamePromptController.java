package catadopt.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Alberto
public class NamePromptController {

	@FXML
	private TextField nameField;
	private Stage dialogStage;
	private String catName;

	/**
     * Acción del botón confirmar.
     */
	@FXML
	private void onConfirm() {
		catName = nameField.getText();
		dialogStage.close();
	}

	 /**
     * Acción del botón cancelar.
     */
    @FXML
    private void onCancel() {
        catName = null;
        dialogStage.close();
    }

    /**
     * Recibe referencia del diálogo.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Devuelve nombre introducido.
     */
    public String getCatName() {
        return catName;
    }
	
}
