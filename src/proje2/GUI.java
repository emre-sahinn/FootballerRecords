package proje2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Emre Şahin, Berna Bircan, Hamza Aybak
 */
public class GUI extends JFrame {

    private final int GENISLIK = 600;//Ana ekranın genişliği
    private final int YUKSEKLIK = 350;//Ana ekranın yüksekliği
    private DoublyLinkedList liste;//çift bağlı listemiz
    private JTextField sporcuSilmeAdField;
    private JTextField sporcuEklemeAdField;
    private JTextField sporcuEklemeDogumField;
    private JTextField sporcuEklemeTakimField;
    private JTextField sporcuAraAdField;
    private JTextArea sporcuAraTextArea;
    private JTable table;//verileri yazdırmak için gerekli olan table
    private boolean ileri_mi = true;//sıralamanın yönünü belirler
    private DefaultTableModel tableModel;//table için gerekli olan model

    //Constructer içinde atama işlemleri ve pencere ayarı yapılır
    public GUI(String title, DoublyLinkedList liste) {
        super(title);
        this.liste = liste;
        setSize(GENISLIK, YUKSEKLIK);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        sporcuTable();
        menuOlustur();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Kullanıcı programdan çıkmadan önce verileri metin belgesine kaydeder.
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter("sporcu.txt");

                fileWriter.write(""); //dolu olan sporcu dosyasının içini boşaltır

                //çift bağlı listemizde olan bütün elemanları dosyaya yazar
                DoublyLinkedList.TwoWayNode veri = liste.getHead();
                for (int i = 0; i < liste.size(); i++) {
                    Object[] veriler = new Object[3];
                    veriler[0] = veri.getBilgiler().getAdSoyad();
                    veriler[1] = veri.getBilgiler().getBirthDate();
                    veriler[2] = veri.getBilgiler().getTakimlar().toString().replace("  ", " ");

                    veriler[2] = veriler[2].toString().replace("[", "");
                    veriler[2] = veriler[2].toString().replace("]", "");

                    String yazi = veriler[0].toString() + ", " + veriler[1] + ", " + veriler[2].toString().trim() + "\n";
                    fileWriter.append(yazi); //yazıyı dosyaya ekler
                    veri = veri.getNext(); //sonraki elemana geçer
                }
                fileWriter.close(); //dosyayı kapatır.
            } catch (IOException eee) { //Eğer dosya bulunamazsa
                JOptionPane.showMessageDialog(this,
                        "Dosya Bulunamadı: Kayıt yapılamıyor..",
                        "Hata!",
                        JOptionPane.ERROR_MESSAGE);
            }
            System.exit(0); //en son çıkış işlemi yapılır
        }
    }

    //Üst tarafta bir menü oluşturur
    private void menuOlustur() {
        JMenu menu = new JMenu("Dosya");

        JMenuItem sporcuBul = new JMenuItem("Sporcu Bul");
        sporcuBul.addActionListener(new Listener());
        menu.add(sporcuBul);

        JMenuItem sporcuEkle = new JMenuItem("Sporcu Ekle");
        sporcuEkle.addActionListener(new Listener());
        menu.add(sporcuEkle);

        JMenuItem sporcuSil = new JMenuItem("Sporcu Sil");
        sporcuSil.addActionListener(new Listener());
        menu.add(sporcuSil);

        JMenu menu2 = new JMenu("Görünüm");
        
        JMenuItem artanSirala = new JMenuItem("Artan Alfabetik Sıra (A-Z)");
        artanSirala.addActionListener(new Listener());
        menu2.add(artanSirala);

        JMenuItem azalanSirala = new JMenuItem("Azalan Alfabetik Sıra (Z-A)");
        azalanSirala.setForeground(Color.red);
        azalanSirala.addActionListener(new Listener());
        menu2.add(azalanSirala);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        menuBar.add(menu2);
        setJMenuBar(menuBar);
    }

    //table daki verileri günceller yani silip tekrardan yazar
    private void tableReset() {
        //table dan bütün verileri silme işlemi
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        if (ileri_mi) {/*eğer liste düz sıralı olarak görülmek isteniyorsa
            listenin elemanlarını sırayla head den çeker ve table a ekler*/
            DoublyLinkedList.TwoWayNode veri = liste.getHead();
            for (int i = 0; i < liste.size(); i++) {
                Object[] veriler = new Object[3];
                veriler[0] = veri.getBilgiler().getAdSoyad();
                veriler[1] = veri.getBilgiler().getBirthDate();
                veriler[2] = veri.getBilgiler().getTakimlar();
                veriler[2] = veriler[2].toString().replace("[", "");
                veriler[2] = veriler[2].toString().replace("]", "");
                tableModel.addRow(veriler);
                veri = veri.getNext();
            }
        } else {//eğer liste ters sıralı olarak görülmek isteniyorsa..
            //listenin elemanlarını sırayla tail dan geriye doğru çeker ve table a ekler
            DoublyLinkedList.TwoWayNode veri = liste.getTail();
            for (int i = 0; i < liste.size(); i++) {
                Object[] veriler = new Object[3];
                veriler[0] = veri.getBilgiler().getAdSoyad();
                veriler[1] = veri.getBilgiler().getBirthDate();
                veriler[2] = veri.getBilgiler().getTakimlar();
                veriler[2] = veriler[2].toString().replace("[", "");
                veriler[2] = veriler[2].toString().replace("]", "");
                tableModel.addRow(veriler);
                veri = veri.getPrevious();
            }
        }
    }

    //table oluşturmak için
    private void sporcuTable() {
        String[] columnNames = {"Ad Soyad", "Doğum Tarihi", "Oynadığı Takımlar"};//sütun başlıkları
        Object[][] data = new Object[liste.size()][3];//satır verilerini tutacak liste

        //table a ilk verileri eklemek için listeyi dolaşırız
        DoublyLinkedList.TwoWayNode veri = liste.getHead();
        for (int i = 0; i < liste.size(); i++) {
            data[i][0] = veri.getBilgiler().getAdSoyad();
            data[i][1] = veri.getBilgiler().getBirthDate().toString().replace(" ", "/");
            data[i][2] = veri.getBilgiler().getTakimlar();
            data[i][2] = data[i][2].toString().replace("[", "");
            data[i][2] = data[i][2].toString().replace("]", "");
            veri = veri.getNext();
        }
        //table oluşturulur
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        //font ayarlanır
        Font font = new Font("Courier", Font.BOLD, 14);
        table.setFont(font);
        //başlık için font ayarlanır
        JTableHeader header = table.getTableHeader();
        header.setFont(font);
        header.setBackground(Color.cyan);
        //table daki verileri değişime kapatır ve ekrana yazdırır
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        add(scrollPane);
    }

    //Sporcu ekleme menüsünü gösterir
    private void sporcuEkleme() {
        JFrame frame = new JFrame("Sporcu Ekleme");//Yeni pencere oluşturur
        Double GENISLIK2 = GENISLIK / 1.4;
        Double YUKSEKLIK2 = YUKSEKLIK / 1.5;
        //frame için ayarları yapılır
        frame.setSize(GENISLIK2.intValue(), YUKSEKLIK2.intValue());
        frame.setLayout(new GridLayout(4, 2));
        //gerekli label ve butonlar eklenir
        JLabel isimLabel = new JLabel("Sporcunun Adı ve Soyadı:");
        sporcuEklemeAdField = new JTextField(50);
        JLabel dogumLabel = new JLabel("Sporcunun Doğum Tarihi: ");
        sporcuEklemeDogumField = new JTextField("Tarihler arasında BOŞLUK bırakın..", 50);
        JLabel takimLabel = new JLabel("Sporcunun Girdiği Takımlar: ");
        sporcuEklemeTakimField = new JTextField("Takımlar arasında VIRGUL bırakın..");
        JButton ekleButton = new JButton("Ekle");
        ekleButton.addActionListener(new ButtonListener());
        frame.add(isimLabel);
        frame.add(sporcuEklemeAdField);
        frame.add(dogumLabel);
        frame.add(sporcuEklemeDogumField);
        frame.add(takimLabel);
        frame.add(sporcuEklemeTakimField);
        frame.add(ekleButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //sporcu silme menüsünü gösterir
    private void sporcuSilme() {
        JFrame frame = new JFrame("Sporcu Silme");//Yeni pencere oluşturur
        Double GENISLIK2 = GENISLIK / 1.5;
        Double YUKSEKLIK2 = YUKSEKLIK / 2.5;
        //frame için ayarları yapılır
        frame.setSize(GENISLIK2.intValue(), YUKSEKLIK2.intValue());
        frame.setLayout(new GridLayout(2, 2));
        //gerekli label ve butonlar eklenir
        JLabel isimLabel3 = new JLabel("Sporcunun Adı ve Soyadı:");
        sporcuSilmeAdField = new JTextField(50);
        JButton silButton = new JButton("Sil");
        silButton.addActionListener(new ButtonListener());
        frame.add(isimLabel3);
        frame.add(sporcuSilmeAdField);
        frame.add(silButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Adı soyadı verilen kullanıcıyı getirme menüsünü gösterir
    private void sporcuBulma() {
        JFrame frame = new JFrame("Sporcu Bulma");//Yeni pencere oluşturur
        Double GENISLIK2 = GENISLIK / 1.5;
        Double YUKSEKLIK2 = YUKSEKLIK / 2.5;
        //frame için ayarları yapılır
        frame.setSize(GENISLIK2.intValue(), YUKSEKLIK2.intValue());
        frame.setLayout(new GridLayout(2, 3));
        //gerekli label ve butonlar eklenir
        JLabel isimLabel = new JLabel("Sporcunun Adı ve Soyadı:");
        sporcuAraAdField = new JTextField(50);
        JButton araButton = new JButton("Ara");
        sporcuAraTextArea = new JTextArea("Bilgiler");
        Font font = new Font("Courier", Font.BOLD, 12);//font eklenir
        sporcuAraTextArea.setFont(font);//font uygulanır
        sporcuAraTextArea.setBackground(Color.cyan);
        sporcuAraTextArea.setForeground(Color.black);
        sporcuAraTextArea.setEditable(false);
        araButton.addActionListener(new ButtonListener());
        frame.add(isimLabel);
        frame.add(sporcuAraAdField);
        frame.add(araButton);
        frame.add(sporcuAraTextArea);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //Burdaki "data" bulunmuş kişinin verilerini tutar ve ekrana yazdırır
    private void bilgiGetir(Bilgi data) {
        if (data == null) {//eğer gelen veri null ise hata verir
            JOptionPane.showMessageDialog(null,
                    "Bulma İşlemi Başarısız: Aranan kişi bulunamadı..",
                    "Hata!",
                    JOptionPane.ERROR_MESSAGE);
        } else {//hata yok ise
            //gerekli verileri data dan çekilerek ekrana yazılır
            String metin = " İsim: " + data.getAdSoyad() + "\n";
            metin += " Doğum Tarihi: " + data.getBirthDate().toString().replace(" ", "/") + "\n";
            String takimlar = data.getTakimlar().toString();
            takimlar = takimlar.replace("[", "");
            takimlar = takimlar.replace("]", "");
            metin += " Oynadığı Takımlar: " + takimlar;
            sporcuAraTextArea.setText(metin);
        }
    }

    private class ButtonListener implements ActionListener {

        //Bu class frame butonları için
        @Override
        public void actionPerformed(ActionEvent e) {
            String mesaj = e.getActionCommand();

            if (mesaj.equals("Sil")) {//eğer gelen mesaj sil ise
                String adSoyad = sporcuSilmeAdField.getText();
                System.out.println("sil: " + adSoyad);
                boolean hata_var;
                hata_var = liste.delete(adSoyad);//verilen string i bulup siler ve başarılı ise true döndürür
                if (hata_var) {
                    JOptionPane.showMessageDialog(null,
                            "Silme İşlemi Başarısız: Silinecek kişi bulunamadı..",
                            "Hata!",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("------------");
                    liste.outputList();
                    JOptionPane.showMessageDialog(null,
                            "Silme işlemi başarılı!", "Silme İşlemi", JOptionPane.DEFAULT_OPTION);
                }
                tableReset();//table i günceller
            } else if (mesaj.equals("Ekle")) {///eğer gelen mesaj ekle ise
                Bilgi bilgi = null;
                //hata kontrolü yapılır
                if (!sporcuEklemeAdField.getText().equals("") && !sporcuEklemeDogumField.getText().equals("") && !sporcuEklemeTakimField.getText().equals("")) {
                    //hata yoksa Bilgi tipinde bilgi nesnesi oluşturulur.
                    String adSoyad = sporcuEklemeAdField.getText();
                    String[] dogumTarihi = sporcuEklemeDogumField.getText().trim().split(" ");
                    String[] takimlar = sporcuEklemeTakimField.getText().trim().split(",");
                    ArrayList takimListesi = new ArrayList();
                    for (String item : takimlar) {//takımmları sırayla ekliyoruz
                        takimListesi.add(item.trim());
                    }
                    takimListesi.set(0, " " + takimListesi.get(0));//Table da estetik dursun diye başına boşluk karakteri ekledik
                    //Bütün verileri bilgi nesnesinin içine atıyoruz.
                    bilgi = new Bilgi(adSoyad, new Date(Integer.parseInt(dogumTarihi[0]),
                            Integer.parseInt(dogumTarihi[1]),
                            Integer.parseInt(dogumTarihi[2])),
                            takimListesi);
                }
                liste.add(bilgi);//verilen nesneyi listede uygun yerini bulup ekler
                if (bilgi == null) {
                    JOptionPane.showMessageDialog(null,
                            "Ekleme İşlemi Başarısız: Girilen veriler hatalı..",
                            "Hata!",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("----*****----");
                    liste.outputList();
                    JOptionPane.showMessageDialog(null,
                            "Ekleme işlemi başarılı!", "Ekleme İşlemi", JOptionPane.DEFAULT_OPTION);
                }
                tableReset();//table i günceller
            } else if (mesaj.equals("Ara")) {//eğer gelen mesaj ara ise
                bilgiGetir(liste.find(sporcuAraAdField.getText()));//listeden adı soyadı verilen oyuncuyu bulur ve verilerini getirir.
            }
        }
    }

    private class Listener implements ActionListener {
        //Bu class menu butonları için
        @Override
        public void actionPerformed(ActionEvent e) {
            String mesaj = e.getActionCommand();
            switch (mesaj) {
                case "Sporcu Ekle":
                    sporcuEkleme();
                    break;
                case "Sporcu Sil":
                    sporcuSilme();
                    break;
                case "Sporcu Bul":
                    sporcuBulma();
                    break;
                case "Artan Alfabetik Sıra (A-Z)":
                    ileri_mi = true;
                    tableReset();
                    break;
                case "Azalan Alfabetik Sıra (Z-A)":
                    ileri_mi = false;
                    tableReset();
                    break;
            }
        }
    }
}

