package is.vidmot;
import javafx.event.ActionEvent;

public interface PlayerControllerInterface {
    void initialize();

    void onVeljaLista(ActionEvent mouseEvent);

    /**
     * Loggar áskrifanda inn
     *
     * @param actionEvent
     */

    void onLogin(ActionEvent actionEvent);
}
