import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class HidrometroUpdater extends Thread {
    private final HidrometroPanel panel;
    private double consumo = 0.0;
    private final String arquivoEntrada;
    private final String arquivoSaida;

    public HidrometroUpdater(HidrometroPanel panel, String arquivoEntrada, String arquivoSaida) {
        this.panel = panel;
        String baseDir = System.getProperty("user.dir");
        this.arquivoEntrada = baseDir + File.separator + arquivoEntrada;
        this.arquivoSaida = baseDir + File.separator + arquivoSaida;
        inicializarArquivo();
    }

    private void inicializarArquivo() {
        File file = new File(arquivoEntrada);
        System.out.println("Usando arquivo em: " + file.getAbsolutePath());
        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("0.0");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private double lerVazao() {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada))) {
            return Double.parseDouble(br.readLine().trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private void salvarImagem() {
        BufferedImage img = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        panel.printAll(g2d); // garante que pega tudo do Swing
        g2d.dispose();

        try {
            ImageIO.write(img, "png", new File(arquivoSaida));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            double vazao = lerVazao();
            consumo += vazao; // Consumo acumulado (L) a cada segundo
            panel.atualizar(vazao, consumo);

            if (panel.getWidth() > 0 && panel.getHeight() > 0) {
                salvarImagem();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }
    }
}
