package nethesap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BankaHesap extends JFrame {

    private JComboBox<String> hesapComboBox;  // Hesap seçimi için ComboBox

    // Başlangıç ve bitiş tarihi spinnerları
    private JSpinner baslangicTarihSpinner;
    private JSpinner bitisTarihSpinner;

    private JButton araButton;                // "Ara" butonu
    private JTable sonucTable;                // Sonuçları gösterecek tablo
    private DefaultTableModel tableModel;     // Tablo modeli

    // Tarih formatı
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Yapıcı Metot
     */
    public BankaHesap() {

        // === PENCERE AYARLARI ===
        setTitle("Banka Ekstresi");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Tam ekran aç
        setLayout(null);

        // === ARKA PLAN ===
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maxBounds = ge.getMaximumWindowBounds();

        // Arka plan resmi
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/assets/login_background.png"));
        Image scaledImage = bgIcon.getImage().getScaledInstance(
                maxBounds.width,
                maxBounds.height,
                Image.SCALE_SMOOTH
        );

        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, maxBounds.width, maxBounds.height);
        backgroundLabel.setLayout(null);
        setContentPane(backgroundLabel);

        // === HESAP SEÇİMİ ===
        JLabel hesapLabel = new JLabel("Hesap Seçiniz:");
        hesapLabel.setForeground(Color.WHITE);
        hesapLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        hesapLabel.setBounds(50, 50, 150, 30);
        backgroundLabel.add(hesapLabel);

        // Boş JComboBox (sonradan doldurulacak)
        hesapComboBox = new JComboBox<>();
        hesapComboBox.setBounds(200, 50, 250, 30);
        backgroundLabel.add(hesapComboBox);

        // === TARİH ARALIĞI ===
        JLabel tarihLabel = new JLabel("Tarih Aralığı:");
        tarihLabel.setForeground(Color.WHITE);
        tarihLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tarihLabel.setBounds(50, 100, 300, 30);
        backgroundLabel.add(tarihLabel);

        // --- Başlangıç Tarihi ---
        JLabel basLabel = new JLabel("Başlangıç:");
        basLabel.setForeground(Color.WHITE);
        basLabel.setBounds(50, 140, 100, 30);
        backgroundLabel.add(basLabel);

        // SpinnerDateModel -> tarih için tek kutu
        baslangicTarihSpinner = new JSpinner(new SpinnerDateModel());
        baslangicTarihSpinner.setBounds(150, 140, 150, 30);

        // Görünümü gg/MM/yyyy yapıyoruz
        JSpinner.DateEditor basEditor = new JSpinner.DateEditor(baslangicTarihSpinner, "dd/MM/yyyy");
        baslangicTarihSpinner.setEditor(basEditor);

        // Varsayılan: Bugünden 7 gün öncesi
        baslangicTarihSpinner.setValue(new Date(System.currentTimeMillis() - (7L * 24 * 60 * 60 * 1000)));
        backgroundLabel.add(baslangicTarihSpinner);

        // --- Bitiş Tarihi ---
        JLabel bitLabel = new JLabel("Bitiş:");
        bitLabel.setForeground(Color.WHITE);
        bitLabel.setBounds(350, 140, 50, 30);
        backgroundLabel.add(bitLabel);

        bitisTarihSpinner = new JSpinner(new SpinnerDateModel());
        bitisTarihSpinner.setBounds(400, 140, 150, 30);

        JSpinner.DateEditor bitEditor = new JSpinner.DateEditor(bitisTarihSpinner, "dd/MM/yyyy");
        bitisTarihSpinner.setEditor(bitEditor);

        // Varsayılan: Bugün
        bitisTarihSpinner.setValue(new Date());

        backgroundLabel.add(bitisTarihSpinner);

        // === ARA BUTONU ===
        araButton = new JButton("Ara");
        araButton.setBounds(580, 140, 100, 30);
        backgroundLabel.add(araButton);

        // Buton tıklandığında çağrılacak metot
        araButton.addActionListener(e -> araButonuTiklandi());

        // === SONUÇ TABLOSU ===
        tableModel = new DefaultTableModel();
        sonucTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(sonucTable);
        scrollPane.setBounds(50, 200, maxBounds.width - 100, maxBounds.height - 260);
        backgroundLabel.add(scrollPane);

        // Tablo başlıkları (güncellendi)
        tableModel.setColumnIdentifiers(new Object[]{
                "Banka Adı", "Hesap No", "Bakiye", "Tarih", "İşlem Türü", "Tutar", "Açıklama"
        });

        setVisible(true); // ana menüden geçiş
    }

    /**
     * Ara Butonu Tıklanınca Çalışacak Metot
     */
    private void araButonuTiklandi() {
        // Tabloyu temizle
        tableModel.setRowCount(0);
        // Buraya veritabanı sorgusu veya manuel ekleme yapılacak
    }

    /**
     * Test Metodu
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankaHesap().setVisible(true));
    }
}