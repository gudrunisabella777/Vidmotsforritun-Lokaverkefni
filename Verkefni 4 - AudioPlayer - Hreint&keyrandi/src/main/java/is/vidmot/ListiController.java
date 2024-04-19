package is.vidmot;
import is.vinnsla.Lag;
import is.vinnsla.Lagalistar;
import is.vinnsla.Lagalisti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import java.io.File;


public class ListiController  {

    // fastar
    private final String PAUSE = "images/pause.png";
    private final String PlAY = "images/play.png";

    // viðmótshlutir
    @FXML
    public ProgressBar fxProgressBar;   // progress bar fyrir spilun á lagi
    public TextField fxLeitarTexti; // leitarTexti
    public Button fxLeitaTakki; // button til að taka við orði sem á að leita að
    public Button fxHreinsaLeitTakki; // takki til að birta aftur upprunalega lagalistann
    public Button fxBaetaLagi;
    public Button fxEydaVolduLagi;
    @FXML
    protected ImageView fxPlayPauseView; // mynd fyrir play/pause hnappinn
    @FXML
    protected ListView<Lag> fxListView; // lagalistinn
    @FXML
    protected ImageView fxMyndLagView;    // mynd fyrir lagið

    // vinnslan
    private Lagalisti lagalisti; // lagalistinn
    private Lagalisti syningarListi; // Listinn sem er notaður til að birta lög
    private MediaPlayer player; // ein player breyta per forritið
    private Lag validLag;       // núverandi valið lag

    /**
     * Frumstillir lagalistann og tengir hann við ListView viðmótshlut
     */

    public void initialize() {
        // setur lagalistann sem núverandi lagalista úr Lagalistar
        lagalisti = Lagalistar.getNuverandi();
        // Afrita lista yfir í sýningarlista
        //ObservableList<Lag> syningarListaLog = FXCollections.observableArrayList();
        //lagalisti.getListi().forEach((Lag lag) -> syningarListaLog.add(lag));
        //syningarListi = new Lagalisti();
        //syningarListi.setSongs(syningarListaLog);
        syningarListi = lagalisti;
        // tengdu lagalistann við ListView-ið
        fxListView.setItems(syningarListi.getListi());
        // man hvaða lag var síðast spilað á lagalistanum og setur það sem valið stak á ListView
        fxListView.getSelectionModel().select(syningarListi.getIndex());
        // setur lagið í focus
        fxListView.requestFocus();
        // // Lætur lagalista vita hvert valda lagið er í viðmótinu og uppfærir myndina fyrir lagið
        veljaLag();
        // setur upp player
        setjaPlayer();
    }

    /**
     * Bregðast við músaratburði og spila valið lag
     *
     * @param mouseEvent
     */

    @FXML
    protected void onValidLag(MouseEvent mouseEvent) {
        System.out.println(fxListView.getSelectionModel().getSelectedItem());
        // Lætur lagalista vita hvert valda lagið er í viðmótinu og uppfærir myndina fyrir lagið
        veljaLag();
        // spila lagið
        spilaLag();
    }

    /**
     * Lagið er pásað ef það er í spilun, lagið er spilað ef það er í pásu
     *
     * @param actionEvent ónotað
     */

    @FXML
    protected void onPlayPause(ActionEvent actionEvent) {
        // ef player-inn er spilandi
        if (player.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            setjaMynd(fxPlayPauseView, PlAY);   // uppfærðu myndina með play (ör)
            player.pause();                     // pásaðu spilarann
        } else if (player.getStatus().equals(MediaPlayer.Status.PAUSED)) {
            setjaMynd(fxPlayPauseView, PAUSE);  // uppfærðu myndina með pause
            player.play();                      // haltu áfram að spila
        }
    }
    /**
     * Fara aftur í heima view. Ef spilari er til stöðva spilarann
     *
     * @param actionEvent ónotað
     */

    @FXML
    protected void onHeim(ActionEvent actionEvent) {
        // stoppaðu player ef hann er ekki null
        if (player != null)
            player.stop();
        // farðu í HEIMA senuna með ViewSwitcher
        ViewSwitcher.switchTo(View.HEIMA, true);
    }

    /**
     * Lætur laga lista vita hvert valda lagið er. Uppfærir myndina fyrir lagið.
     */
    private void veljaLag() {
        // hvaða lag er valið
        validLag = fxListView.getSelectionModel().getSelectedItem();
        //  láttu lagalista vita um indexinn á völdu lagi
        syningarListi.setIndex(fxListView.getSelectionModel().getSelectedIndex());

    }

    /**
     * Spila lagið
     */

    private void spilaLag() {
        setjaMynd(fxPlayPauseView, PAUSE);
        // Búa til nýjan player
        setjaPlayer();
        // setja spilun í gang
        player.play();
    }

    /**
     * Setja mynd með nafni á ImageView
     *
     * @param fxImageView viðmótshluturinn sem á að uppfærast
     * @param nafnMynd    nafn á myndinni
     */

    private void setjaMynd(ImageView fxImageView, String nafnMynd) {
        System.out.println ("nafn á mynd "+nafnMynd);
        fxImageView.setImage(new Image(getClass().getResource(nafnMynd).toExternalForm()));
    }

    /**
     * Setja upp player fyrir lagið, þ.m.t. at setja handler á hvenær lagið stoppar og tengja
     * lagið við progress bar
     */

    private void setjaPlayer() {
        // Stoppa player-inn ef hann var ekki stopp
        if (player != null)
            player.stop();
        // Smíða nýjan player með nýju Media fyrir lagið
        System.out.println(validLag.getMedia());
        try {
            player = new MediaPlayer(new Media(getClass().getResource(validLag.getMedia()).toExternalForm()));
        } catch (Exception e) {
            File songFile = new File(validLag.getMedia());
            String songURI = songFile.toURI().toString();
            Media media = new Media(songURI);
            player = new MediaPlayer(media);
        }
        // Láta player vita hvenær lagið endar - stop time
        player.setStopTime(player.getTotalDuration());
        // setja fall sem er keyrð þegar lagið hættir
        player.setOnEndOfMedia(this::naestaLag);
        // setja listener tengingu á milli player og progress bar
        player.currentTimeProperty().addListener((observable, old, newValue) ->
                fxProgressBar.setProgress(newValue.divide(validLag.getLengd()).toMillis()));

    }

    /**
     * Næsta lag er spilað. Kallað á þessa aðferð þegar fyrra lag á listanum endar
     */
    private void naestaLag() {
        // setja valið lag sem næsta lag á núverandi lagalista
         syningarListi.naesti();
        // uppfæra ListView til samræmis, þ.e. að næsta lag sé valið
        fxListView.getSelectionModel().selectIndices(syningarListi.getIndex());
        // velja lag
        veljaLag();
        // spila lag
        spilaLag();
    }


    public void onLeitaTakki(ActionEvent actionEvent) {
        String leitarTexti = fxLeitarTexti.getText();
        ObservableList<Lag> allSongs = lagalisti.getListi();
        ObservableList<Lag> filterSongs = FXCollections.observableArrayList();

        for (int i = 0; i < allSongs.size(); i += 1) {
            String currentSongName = allSongs.get(i).getNafn();
            if(currentSongName.contains(leitarTexti)){
                filterSongs.add(allSongs.get(i));
            }
        }
        
        Lagalisti newSongList = new Lagalisti();
        newSongList.setSongs(filterSongs);
        syningarListi = newSongList;
        fxListView.setItems(syningarListi.getListi());
        fxListView.getSelectionModel().select(syningarListi.getIndex());
    }

    public void onHreinsaLeit(ActionEvent actionEvent) {
        syningarListi = lagalisti;
        fxListView.setItems(syningarListi.getListi());
        fxListView.getSelectionModel().select(syningarListi.getIndex());
    }

    public void onBaetaLagi(ActionEvent actionEvent) throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veldu lag");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.mp4"));
        File selectedFile = fileChooser.showOpenDialog(null);
        System.out.println(selectedFile);
        System.out.println(selectedFile.getName());

        //AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(selectedFile);
        //long durationInMicroseconds = (Long) fileFormat.properties().get("duration");
        //int durationInMilliSeconds = (int) (durationInMicroseconds / 1_000.0);
        int durationinMilli = 15000;

        Lag nyttLag = new Lag(selectedFile.getPath(), selectedFile.getName(), durationinMilli);
        lagalisti.setSong(nyttLag);
    }

    public void onEydaVolduLagi(ActionEvent actionEvent) {
        Lag validLag = fxListView.getSelectionModel().getSelectedItem();
        if (validLag != null){
            syningarListi.getListi().remove(validLag);
        }
    }
}


