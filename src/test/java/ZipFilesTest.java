import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipFilesTest {
    private static final String CSV_NAME = "csvExample.csv";
    private static final String XLSX_NAME = "xlxsExample.xlsx";
    private static final String PDF_NAME = "pdfExample.pdf";
    private static final String ZIP_PATH = "src/test/resources/files/zipExample.zip";


    @Test
    void testPdfFile() throws Exception {
        ZipFile zipFile = new ZipFile(ZIP_PATH);
        ZipEntry entry = zipFile.getEntry(PDF_NAME);
        try (InputStream is = zipFile.getInputStream(entry)) {
            PDF pdf = new PDF(is);
            assertThat(pdf.numberOfPages).isEqualTo(2);
            assertThat(pdf.text).contains("little more text");
        }
    }

    @Test
    void testXlxsFile() throws Exception {
        ZipFile zipFile = new ZipFile(ZIP_PATH);
        ZipEntry entry = zipFile.getEntry(XLSX_NAME);
        try (InputStream is = zipFile.getInputStream(entry)) {
            XLS xls = new XLS(is);
            assertThat(xls.excel
                    .getSheetAt(0)
                    .getRow(1)
                    .getCell(3)
                    .getStringCellValue()
                    .contains("Реквизиты"));
        }
    }

    @Test
    void testCSVFile() throws Exception {
        ZipFile zipFile = new ZipFile(ZIP_PATH);
        ZipEntry entry = zipFile.getEntry(CSV_NAME);
        try (InputStream is = zipFile.getInputStream(entry)) {
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            List<String[]> csv = reader.readAll();
            assertThat(csv.get(1)).contains("booker12;9012;Rachel;Booker");
        }
    }
}