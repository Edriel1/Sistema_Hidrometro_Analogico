public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            HidrometroUI ui = new HidrometroUI();
            ui.setVisible(true);
        });
    }
}
