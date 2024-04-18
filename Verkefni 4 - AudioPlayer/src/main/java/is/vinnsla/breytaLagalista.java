package is.vinnsla;

import java.io.IOException;
import java.io.File;

public class breytaLagalista {
    public static void buatilSkra(String nafnASkra) {  // Parameter added here
        try {
            File skra = new File(nafnASkra);
            if (skra.createNewFile()) {
                System.out.println("Tókst að búa til skrá: " + skra.getName());
            } else {
                System.out.println("Skrá er nú þegar til.");
            }
        } catch (IOException e) {
            System.out.println("Ekki tókst að búa til skrá.");
            e.printStackTrace();
        }
    }
}
