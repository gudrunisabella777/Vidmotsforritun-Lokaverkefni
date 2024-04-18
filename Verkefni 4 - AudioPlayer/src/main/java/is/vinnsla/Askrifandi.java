package is.vinnsla;
/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Viðmótsforritun 2024
 *
 * Vinnsluklasi fyrir áskrfianda. Hefur nafn
 *****************************************************************************/
import is.vidmot.AskrifandiDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Askrifandi {

    private final StringProperty nafnProperty = new SimpleStringProperty(); //Properties eru ætluð til að vera notuð á mjög svipaðan hátt og "standard" gagnatög, en eru með þá eiginlega að vera observable, mutable og bindable
    //Bindable = "You can directly link your stringProperty to another source such that when that source updates, so will the update of your stringProperty, without needing to explicitly add code to manage this synchronization.
    //Mutable = Ólíkt hefðbundnum strengjum, þá eru gagnatög sem venjulega væru immutable gerð mutable með propertyum.
    //Semsagt, þegar talað er um að strengir t.d séu immutable í Java, þá geturu ennþá endur-assignað gildið á streng breytu, en upprunalega gildið er ennþá í minninu. Ert í raun að búa til annan hlut, og endur-benda breytunni á nýja gildið.
    //Observable = ef hlutur er observable þá getur hann látið listenera vita þegar gildið á honum breytist

    /**
     * Smiður fyrir Áskrifanda. Tekur inn nafn
     * @param nafn
     */
    public Askrifandi(String nafn) {
       setNafn(nafn);
    }

    // get og set aðferðir fyrir nafn

    public String getNafn() {
        return nafnProperty.get();
    }

    public void setNafn(String nafn) {
        nafnProperty.set(nafn);
    }
    //Eina sem þessi klasi virðist gera er að nota StringProperty klasan og aðferðir hans
    //til að sækja, og breyta gildinu á nafnProperty breytunni sem er af StringProperty taginu.
}
