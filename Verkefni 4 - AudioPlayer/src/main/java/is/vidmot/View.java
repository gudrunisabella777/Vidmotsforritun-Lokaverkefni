package is.vidmot;
public enum View {
    HEIMA("heima-view.fxml"),
    LAGALISTI("listi-view.fxml"),
    ASKRIFANDI("askrifandi-view.fxml");

    private final String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
