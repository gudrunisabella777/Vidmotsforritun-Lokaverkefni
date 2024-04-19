package is.vidmot;

import is.vinnsla.breytaLagalista;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class nyttLagDialog extends Dialog<breytaLagalista> {
    public ListView fxBaetaLagiListView;

    public Button fxNyttLagButton;
    private final breytaLagalista breytaLagaLista; //breytaLagaLista vinnsluhlutur

    public nyttLagDialog(breytaLagalista breytaLagaLista){
        this.breytaLagaLista = breytaLagaLista;
        //Setja sviðið
        setDialogPane(lesaDialog());

        setResultConverter();
    }
    public void onNyttLag(ActionEvent actionEvent) {

    }
    private void setResultConverter() {
        setResultConverter(b -> {                    // b er af taginu ButtonType - hér er lambda fall sem tekur inn b
            if (b.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                // REPLACE W/ LAG askrifandi.setNafn(fxNafn.getText());   // ná í nafnið út dialog og setja í áskrifanda hlutinn
                return breytaLagaLista;
            } else {
                return null;
            }
        });         // endir á d.setResultConverter
    }
    private DialogPane lesaDialog() {

        FXMLLoader fxmlLoader = new FXMLLoader(AskrifandiDialog.class.getResource(View.BAETALAGI.getFileName()));
        try {
            fxmlLoader.setController(this); // setur þennan hlut sem controller
            return fxmlLoader.load();       // hlaða inn fxml skránni
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
