import static org.opencv.highgui.Highgui.imread;
import static org.opencv.highgui.Highgui.imwrite;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;
import java.io.File;
import java.util.ArrayList;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Read {

	// Source path content images
	static String FOTO_PATH; // = "C:\\Program Files\\Tesseract-OCR\\tessdata\\resim\\fis6.jpg";
	static String TESS_DATA = "C:\\Program Files\\Tesseract-OCR\\tessdata\\dil_data\\";
	String result;

	public Read(String FOTO_PATH) {
		this.FOTO_PATH = FOTO_PATH;
		Tesseract tesseract = new Tesseract();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		tesseract.setDatapath(TESS_DATA);
		tesseract.setLanguage("eng");
		tesseract.setLanguage("tur");
		tesseract.setLanguage("deu");
		Mat origin = imread(FOTO_PATH);
		this.result = extractTextFromImage(origin, tesseract);
	}

	public String extractTextFromImage(Mat inputMat, Tesseract tesseract) {
		String result = "";
		Mat gray = new Mat(); // renkli veya gri goruntuleri depolama->MAT

		// Convert to gray scale
		cvtColor(inputMat, gray, COLOR_BGR2GRAY);
		imwrite(FOTO_PATH, gray);

		try {
			result = tesseract.doOCR(new File(FOTO_PATH));
		} catch (TesseractException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String isletmeAdiAl(String result) {

		String[] lines = result.split("\n");
		String firstLine = lines[0];
		System.out.println("\nFÝRMA ÝSMÝ: " + firstLine);
		return firstLine;
	}

	public String tarihAl(String result) {

		String[] lines = result.split("\n");

		int rowCount = lines.length;

		for (int i = 0; i < rowCount; i++) {
			String line = lines[i];
			String[] dizi = new String[line.length()];
			for (int j = 0; j < line.length(); j++) {
				dizi[j] = line.substring(j, j + 1);

			}
			for (int k = 0; k < line.length(); k++) {

				if (dizi[k].contains(".") || dizi[k].contains("/")) {

					int count = k;
					int count2 = 0;
					if (line.length() - k > 3) {
						count2 = count + 3;
					}

					if (dizi[count2].contains(".") || dizi[count2].contains("/")) {

						int ilk = count - 2;
						int son = count + 8;

						String tarih = line.substring(ilk, son);
						System.out.println("TARÝH:" + tarih);

						return tarih;
					}
				}

			}
		}

		return null;
	}

	public String fisNoAl(String result) {

		String[] lines = result.split("\n");
		int rowCount = lines.length;
		for (int i = 0; i < rowCount; i++) {
			String line = lines[i];
			String[] dizi = new String[line.length()];
			for (int j = 0; j < line.length(); j++) {

				dizi[j] = line.substring(j, j + 1);

			}
			for (int k = 0; k < line.length(); k++) {
				if (dizi[k].contains("F")) {
					if (dizi[k + 1].contains("Ý") || dizi[k + 1].contains("I") || dizi[k + 1].contains("i")) {
						int count = k;
						int count2 = count + 8;
						String fisNo = line.substring(count2, line.length());
						String fisNo1 = fisNo.trim();
						System.out.println("FÝS NO:" + fisNo);

						return fisNo1;

					}
				}
			}

		}

		return null;

	}

	public String toplamTutarAl(String result) {

		String[] lines = result.split("\n");
		int rowCount = lines.length;
		for (int i = 0; i < rowCount; i++) {
			String line = lines[i];
			String[] dizi = new String[line.length()];
			if (line.contains("TOPLAM") || line.contains("TOPL")) {
				for (int j = 0; j < line.length(); j++) {

					dizi[j] = line.substring(j, j + 1);

				}
				for (int t = 0; t < line.length(); t++) {
					if (dizi[t].contains("0") || dizi[t].contains("1") || dizi[t].contains("2") || dizi[t].contains("3")
							|| dizi[t].contains("4") || dizi[t].contains("5") || dizi[t].contains("6")
							|| dizi[t].contains("7") || dizi[t].contains("8") || dizi[t].contains("9")) {

						int sayac = t;
						String toplamTutar = line.substring(t, line.length());
						System.out.println("TOPLAM:" + toplamTutar);
						return toplamTutar;
					}

				}

			}
		}

		return null;
	}

	public String kdvAl(String result) {

		String[] lines = result.split("\n");
		int rowCount = lines.length;
		for (int i = 0; i < rowCount; i++) {

			String line = lines[i];
			String[] dizi = new String[line.length()];
			if (line.contains("KD")) {

				for (int j = 0; j < line.length(); j++) {

					dizi[j] = line.substring(j, j + 1);

				}
				for (int t = 0; t < line.length(); t++) {
					if (dizi[t].contains("0") || dizi[t].contains("1") || dizi[t].contains("2") || dizi[t].contains("3")
							|| dizi[t].contains("4") || dizi[t].contains("5") || dizi[t].contains("6")
							|| dizi[t].contains("7") || dizi[t].contains("8") || dizi[t].contains("9")) {

						int sayac = t;
						String kdvTutar = line.substring(t, line.length());
						System.out.println("KDV:" + kdvTutar);
						return kdvTutar;
					}

				}

			} else if (line.contains("TOPK")) {
				int son = line.length();
				String kdvTutar = line.substring(son - 5, son);
				return kdvTutar;
			}

		}

		return null;

	}

	public String urunAl(String result) {

		String[] lines = result.split("\n");
		String tmp = "";
		int rowCount = lines.length;
		String[] dizi = new String[rowCount];
		ArrayList<String> list = new ArrayList<>();

		for (int i = 0; i < rowCount; i++) {
			String line = lines[i];

			if (line.contains("%")) {

				String urun = line.substring(0, line.length());
				tmp = tmp + " " + urun;
				list.add(urun);

			} else if (line.contains("X")) {
				String urun = line.substring(0, line.length());
				tmp = tmp + " " + urun;
				list.add(urun);
			}

		}
		for (String element : list) {
			System.out.println(element);
		}

		return tmp;

	}

}
