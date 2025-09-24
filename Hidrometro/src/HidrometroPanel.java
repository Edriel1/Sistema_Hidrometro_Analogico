import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class HidrometroPanel extends JPanel {
    private double ponteiro = 0.0;       // Ângulo do ponteiro
    private double consumo = 0.0;        // Consumo total em litros
    private double vazaoAtual = 0.0;     // Vazão atual (L/s)

    public void atualizar(double vazao, double consumoTotal) {
        this.vazaoAtual = vazao;
        this.consumo = consumoTotal;
        System.out.println("Consumo: " + consumo +"   Vazao: " + vazao);
        this.ponteiro = Math.min(270, vazao * 10); // Escala simples
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int centerX = w / 2;
        int centerY = h / 2;
        int raio = Math.min(w, h) / 3;

        // Fundo
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillOval(centerX - raio - 20, centerY - raio - 20, (raio + 20) * 2, (raio + 20) * 2);

        // Borda
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(centerX - raio, centerY - raio, raio * 2, raio * 2);

        // Marcas
        for (int i = 0; i <= 10; i++) {
            double ang = Math.toRadians(225 - i * 27);
            int x1 = (int) (centerX + Math.cos(ang) * (raio - 10));
            int y1 = (int) (centerY - Math.sin(ang) * (raio - 10));
            int x2 = (int) (centerX + Math.cos(ang) * raio);
            int y2 = (int) (centerY - Math.sin(ang) * raio);
            g2d.drawLine(x1, y1, x2, y2);

            String label = String.valueOf(i * 10);
            int tx = (int) (centerX + Math.cos(ang) * (raio - 30));
            int ty = (int) (centerY - Math.sin(ang) * (raio - 30));
            g2d.drawString(label, tx - 10, ty + 5);
        }

        // Ponteiro
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(4));
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(225 - ponteiro), centerX, centerY);
        g2d.drawLine(centerX, centerY, centerX, centerY - raio + 20);
        g2d.setTransform(old);

        // Texto de informações
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString(String.format("Vazão: %.2f L/s", vazaoAtual), 20, h - 60);
        g2d.drawString(String.format("Consumo: %.2f L", consumo), 20, h - 30);
    }
}
