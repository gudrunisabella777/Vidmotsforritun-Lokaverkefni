package is.vinnsla;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Viðmótsforritun 2024
 *
 *  Vinnsluklasi fyrir Lag. Hefur nafn, lengd, mynd og media (skráaslóð) fyrir lagið
 *****************************************************************************/

public class Lag {
    private final String media;
    private final String nafn;

    private final int lengd;



    /**
     * Smiður sem tekur inn eiginleika lags og smíðar lagið
     *
     * @param slod  skráaslóð fyrir lagið
     * @param nafn  nafn lags
     * @param lengd lengd lags
     */
    public Lag(String slod, String nafn, int lengd) {
        media = slod;
        this.nafn = nafn;
        this.lengd = lengd;
    }

    @Override
    public String toString() {
        return nafn;
    }

    // get aðferðir fyrir tilviksbreytur

    public String getMedia() {
        return media;
    }

    public String getNafn() {
        return nafn;
    }


    public int getLengd() {
        return lengd;
    }

}
