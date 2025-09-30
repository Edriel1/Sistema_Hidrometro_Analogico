import javax.swing.*;
import java.awt.*;

public class HidrometroUI extends JFrame {
    private final HidrometroPanel panel;
    private final HidrometroUpdater updater;

    public HidrometroUI(String nome, String arquivoEntrada, String arquivoSaida) {
        setTitle("Hidrômetro Digital");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new HidrometroPanel();
        add(panel, BorderLayout.CENTER);

        updater = new HidrometroUpdater(panel, arquivoEntrada, arquivoSaida);
        updater.start();
    }
}
