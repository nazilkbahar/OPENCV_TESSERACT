import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.Button;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import jdk.nashorn.api.tree.ForOfLoopTree;

public class Form extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField deger_arama;
	private JTextField foto_text;
	private JTable table;

	DefaultTableModel modelim = new DefaultTableModel();
	Object[] kolonlar = { "firma_ismi", "tarih", "fis_no", "toplam", "kdv", "urun" };
	Object[] satirlar = new Object[6];
	baglanti connect = new baglanti();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form frame = new Form();
					frame.setVisible(true);
					frame.setLocation(0, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Form() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1314, 856);
		contentPane = new JPanel();
		contentPane.setBackground(Color.blue);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1310, 33);
		contentPane.add(menuBar);

		JPanel listele = new JPanel();
		listele.setBounds(560, 32, 730, 790);
		contentPane.add(listele);
		listele.setLayout(null);

		JComboBox comboBox = new JComboBox();

		comboBox.setBounds(10, 9, 152, 32);
		listele.add(comboBox);
		comboBox.addItem("Ýþletme Adý");
		comboBox.addItem("Tarih");
		comboBox.setSelectedItem(null);

		deger_arama = new JTextField();
		deger_arama.setBounds(193, 10, 217, 32);
		listele.add(deger_arama);
		deger_arama.setColumns(10);

		JButton ara = new JButton("Search");
		ara.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				toblo_temizle(modelim);
				String sql;
				String a;
				String deger;
				if (comboBox.getSelectedIndex() == 0)
					a = "firma_ismi";
				else if (comboBox.getSelectedIndex() == 1)
					a = "tarih";
				else
					return;
				deger = deger_arama.getText();

				sql = "SELECT * FROM fis WHERE " + a + " = '" + deger + "';";
				System.out.println(sql);
				ResultSet cevab = connect.show(sql);
				try {
					while (cevab.next()) {

						satirlar[0] = cevab.getString("firma_ismi");
						satirlar[1] = cevab.getString("tarih");
						satirlar[2] = cevab.getString("fis_no");
						satirlar[3] = cevab.getString("toplam");
						satirlar[4] = cevab.getString("kdv");
						satirlar[5] = cevab.getString("urun");
						modelim.addRow(satirlar);
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}
		});
		ara.setBounds(442, 10, 85, 32);
		listele.add(ara);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 97, 710, 441);
		listele.add(scrollPane);

		table = new JTable();

		table.setBounds(26, 548, 437, 200);
		modelim.setColumnIdentifiers(kolonlar);
		modelim.addRow(satirlar);
		table.setModel(modelim);
		scrollPane.setViewportView(table);

		Button vt_listele = new Button("DB listele");
		vt_listele.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				toblo_temizle(modelim);
				String sql = "Select * FROM fis";
				ResultSet cevab = connect.show(sql);
				try {
					while (cevab.next()) {
						satirlar[0] = cevab.getString("firma_ismi");
						satirlar[1] = cevab.getString("tarih");
						satirlar[2] = cevab.getString("fis_no");
						satirlar[3] = cevab.getString("toplam");
						satirlar[4] = cevab.getString("kdv");
						satirlar[5] = cevab.getString("urun");
						modelim.addRow(satirlar);
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}

			}
		});

		vt_listele.setBounds(92, 57, 135, 34);
		listele.add(vt_listele);

		JPanel foto_ekle = new JPanel();
		foto_ekle.setBounds(0, 0, 554, 822);
		contentPane.add(foto_ekle);
		foto_ekle.setLayout(null);

		Button add = new Button("Add");

		add.setBounds(470, 41, 76, 36);
		foto_ekle.add(add);

		textField = new JTextField();
		textField.setBounds(10, 41, 443, 36);
		foto_ekle.add(textField);
		textField.setColumns(10);

		JLabel lblFotorafYolunuGiriniz = new JLabel("Foto\u011Fraf Yolunu giriniz");
		lblFotorafYolunuGiriniz.setBounds(10, 10, 443, 27);
		foto_ekle.add(lblFotorafYolunuGiriniz);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 98, 536, 528);
		foto_ekle.add(scrollPane_1);
		JPanel panel = new JPanel();
		panel.setBounds(256, 597, 245, 84);
		scrollPane_1.setViewportView(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 649, 536, 163);
		foto_ekle.add(panel_1);
		panel_1.setLayout(null);

		foto_text = new JTextField();
		foto_text.setToolTipText("");
		foto_text.setBounds(0, 0, 526, 87);
		panel_1.add(foto_text);
		foto_text.setColumns(10);

		add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				BufferedImage image = null;
				try {
					image = ImageIO.read(new File(textField.getText()));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				JLabel label = new JLabel(new ImageIcon(image));
				panel.add(label);
				panel.setVisible(false);
				panel.setVisible(true);
				Read dosya = new Read(textField.getText());
				foto_text.setText(dosya.result);
				/*
				String sonuc = "Ýsletme Adi"+satirlar[0]+
						       "tarih:"+satirlar[1];
				foto_text.setText(sonuc);
				*/

				String sql = "INSERT INTO fis VALUE ('" + dosya.isletmeAdiAl(dosya.result) + "','"
						+ dosya.tarihAl(dosya.result) + "','" + dosya.fisNoAl(dosya.result) + "','"
						+ dosya.toplamTutarAl(dosya.result) + "','" + dosya.kdvAl(dosya.result) + "','"
						+ dosya.urunAl(dosya.result) + "')";
				// C:\\Program Files\\Tesseract-OCR\\tessdata\\resim\\fis4.jpg
				connect.add(sql);

			}

		});

		JMenu show = new JMenu("Show");
		show.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				foto_ekle.setVisible(false);
				listele.setVisible(true);
			}
		});
		menuBar.add(show);

		JMenu ekle = new JMenu("Add");
		ekle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				foto_ekle.setVisible(true);
				listele.setVisible(false);
			}
		});
		menuBar.add(ekle);

	}

	public void toblo_temizle(TableModel model) {
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.getDataVector().removeAllElements();
		table.repaint();
	}
}
