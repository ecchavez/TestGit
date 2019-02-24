package mx.com.grupogigante.gestionvivienda.utils;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PageHeader extends PdfPageEventHelper{

    String header;

    public void setHeader(String header) {
        this.header = header;
    }

    public void onEndPage(PdfWriter writer, Document document) {
        System.out.print("Yeahhhhh...now add header");
        PdfPTable table = new PdfPTable(1);
        try {
            table.setWidths(new int[]{527});
            table.setTotalWidth(600);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(40);
            //table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            Image img = Image.getInstance(header);
            img.setScaleToFitLineWhenOverflow(true);
            PdfPCell cell = new PdfPCell(img);
            //cell.setBorder(Rectangle.BOTTOM);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            //table.addCell(String.format("Pagina %d de", writer.getPageNumber()));
            
            //PdfPCell cell = new PdfPCell(Image.getInstance(total));
            //cell.setBorder(Rectangle.BOTTOM);
            //table.addCell(cell);
            //System.out.println(850 - document.topMargin() + table.getTotalHeight());
            table.writeSelectedRows(0, -1, 0, 845 , writer.getDirectContent());
        }
        catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}