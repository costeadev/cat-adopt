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
    nameAdoptedCat */
	@FXML
	private void onConfirm() {
        String text = nameField.getText().trim();

        if (!text.isEmpty()) {
            catName = text;
        }

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
    
    @FXML
    private void initialize() { // El formulario en envia al pulsar ENTER
        nameField.setOnAction(e -> onConfirm());
    }
	
}
