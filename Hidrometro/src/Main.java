import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            final int NUM_MAXIMO_HIDROMETROS = 5;
            Scanner sc = new Scanner(System.in);

            System.out.println("Digite quantos hidrômetros você quer utilizar? (MAX: 5)");
            int quantidadeHidrometros = sc.nextInt();


            for (int i = 1; i <= Math.min(quantidadeHidrometros, NUM_MAXIMO_HIDROMETROS); i++) {
                System.out.print("Digite a vazão inicial do Hidrômetro " + i + ": ");
                double vazao = sc.nextDouble();

                String entrada = "vazao" + i + ".txt";
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(entrada))) {
                    bw.write(String.valueOf(vazao));
                } catch (IOException e) {
                    throw new IOError(e);
                }
            }

            for (int i = 1; i <= (Math.min(quantidadeHidrometros, NUM_MAXIMO_HIDROMETROS)); i++) {
                String entrada = "vazao" + i + ".txt";
                String saida = "hidrometro" + i + ".png";
                HidrometroUI ui = new HidrometroUI("Hidrometro " + i, entrada, saida);
                ui.setVisible(true);
            }

            sc.close();
        });
    }
}
