package com.functions.models;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class Relatorios {

        /***
         * @apiNote 0 - tipo(table or cell)
         * @apiNote 1 - column Widths
         * @apiNote 2 - width porcent
         * @apiNote 3 - font
         * @apiNote 4 - border
         * @apiNote 5 - alingment
         * @apiNote 6 - vertical alignment (0)
         * @apiNote 7 - dados...
         * @param parametros
         * @return
         *///Object[] ptable = {"tablecreate",float[],100,font,Rectangle.NO_BORDER,Element.ALIGN_CENTER,0,valores};
        public static Object Creation(Object[] parametros) {
                String res = ((String) parametros[0]).toLowerCase();
                if (res.equals("tablecreate") || res.equals("tablecreate-backgroundcolor")) {
                        float[] columnWidths = (float[]) parametros[1];
                        PdfPTable table = new PdfPTable(columnWidths);
                        PdfPCell cell = new PdfPCell();
                        table.setTotalWidth(60f);
                        table.setWidthPercentage((int) parametros[2]);
                        
                        Font texto = (Font) parametros[3];
                        int i = 7;
                        if(res.equals("tablecreate-backgroundcolor")){
                                texto.setColor((BaseColor)parametros[8]);
                                i = 9;
                        }
                        while (i < parametros.length) {
                                cell = new PdfPCell();
                                cell.setBorder((int) parametros[4]);
                                Paragraph p = new Paragraph();
                                
                                if (parametros[i] != null) {
                                        p.add(((String) parametros[i]));
                                } else {
                                        p.add("");
                                }
                                p.setAlignment((int) parametros[5]);
                                p.setFont(texto);
                                if (((int) parametros[6]) > 0) {
                                        cell.setVerticalAlignment((int) parametros[6]);
                                        cell.setPadding(6f);
                                        cell.setUseAscender(true);
                                } else {
                                        cell.setUseAscender(false);
                                }
                                if(res.equals("tablecreate-backgroundcolor")){
                                        cell.setBackgroundColor((BaseColor)parametros[7]);
                                        
                                }

                                /*
                                 * Float fontSize = p.getFont().getSize();
                                 * Float capHeight =
                                 * p.getFont().getBaseFont().getFontDescriptor(BaseFont.CAPHEIGHT, fontSize);
                                 * cell.setPadding(5f);
                                 * cell.setPaddingTop(capHeight - fontSize + 5f);
                                 */

                                cell.addElement(p);
                                table.addCell(cell);
                                i++;
                        }

                        table.addCell(cell);

                        
                        return table;
                }else if(res.equals("linha")){
                        float[] columnWidths = {100,0};
                        PdfPTable table = new PdfPTable(columnWidths);
                        PdfPCell cell = new PdfPCell();
                        table.setTotalWidth(60f);
                        table.setWidthPercentage(100);
                        cell = new PdfPCell();
                        cell.setBorder((int) parametros[1]);
                        Paragraph p = new Paragraph();
                        p.add(" ");
                        cell.addElement(p);
                        table.addCell(cell);
                        

                        table.addCell(cell);

                        
                        return table;
                }else if(res.equals("linhawithtamanho")){
                        float[] columnWidths = {100,0};
                        PdfPTable table = new PdfPTable(columnWidths);
                        PdfPCell cell = new PdfPCell();
                        table.setTotalWidth(60f);
                        table.setWidthPercentage((int)parametros[2]);
                        cell = new PdfPCell();
                        cell.setBorder((int) parametros[1]);
                        Paragraph p = new Paragraph();
                        p.add(" ");
                        cell.addElement(p);
                        table.addCell(cell);
                        

                        table.addCell(cell);

                       
                        return table;
                } else if (res.equals("cellcreate")) {
                        PdfPCell cell = new PdfPCell();
                        cell.setBorder((int) parametros[2]);
                        Font texto = (Font) parametros[3];
                        cell.setVerticalAlignment((int) parametros[4]);
                        cell.setHorizontalAlignment((int) parametros[5]);
                        Paragraph p = new Paragraph();
                        p.add((String) parametros[6]);
                        p.setFont(texto);
                        cell.addElement(p);
                      
                        return cell;
                }

                

                System.out.println("Retorno nulo, não encontrado parametro: " + res);
                PdfPTable table = new PdfPTable(2);
               
                return table;
        }

        public static String Limited(String value, int limit){
                if(value != null && value.length() > limit){
                        return value.substring(0, limit);
                }else{
                        return value;
                }
        }

}
