/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tika.exception.TikaException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
/**
 *
 * @author Xando
 */
public class PastaPDFParser {

    private File pasta;
    private StatusUpdateInterface statusUpdate;
    private TableUpdateInterface tableUpdate;
    private boolean regex;
    private boolean recursivo;
    private PDFFileFilter pdfFilter;
    private DirFileFilter dirFilter;

    private int percentualMinimo;

    private List<Sentencas> sentencas;

    public PastaPDFParser(File pasta, StatusUpdateInterface statusUpdate, TableUpdateInterface tableUpdate, boolean recursivo) {
        pdfFilter = new PDFFileFilter();
        dirFilter = new DirFileFilter();
        this.statusUpdate = statusUpdate;
        this.recursivo = recursivo;
        regex = false;
        this.tableUpdate = tableUpdate;
        this.pasta = pasta;
        this.sentencas = new ArrayList();
        varreduraPasta(pasta);
        this.statusUpdate.finishedProcessing();
    }
    
    private void varreduraPasta(File pasta) {
        if (pasta.isDirectory()) {
            if(recursivo) {
                File[] arquivos = pasta.listFiles(dirFilter);
                for(File dir: arquivos) {
                    varreduraPasta(dir);
                }
            }
            File[] arquivos = pasta.listFiles(pdfFilter);
            int index = 1;
            for (File f : arquivos) {
                //System.out.println("Processando " + f.getName());
                Sentencas s = geraSentencas(f);
                sentencas.add(s);

                index++;
            }
        }
    }

    /*
    private Sentencas geraSentencasCermine2(File f) {
        statusUpdate.updateStatus("Processando arquivo " + f.getName());
        try {
            ContentExtractor e = new ContentExtractor();
            InputStream in = new FileInputStream(f);
            e.setPDF(in);
            Element resEl = e.getContentAsNLM();
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            Document doc = new Document(resEl);
            String nlm = outputter.outputString(doc);
            String html = new NLMToHTMLWriter().write(resEl);
            //ArticleMeta am = ArticleMeta.extractNLM(doc);
            Sentencas s = new Sentencas(f, limpaTexto(html));
            return s;
        } catch (AnalysisException ex) {
            Logger.getLogger(PastaPDFParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PastaPDFParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PastaPDFParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformationException ex) {
            Logger.getLogger(PastaPDFParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private Sentencas geraSentencasCermine(File f) {
        statusUpdate.updateStatus("Processando arquivo " + f.getName());
        try {
            PdfRawTextWithLabelsExtractor extractor = new PdfRawTextWithLabelsExtractor();
            InputStream in = new FileInputStream(f);
            Element result = extractor.extractRawText(in);
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            
            String res = outputter.outputString(result);
            Sentencas s = new Sentencas(f, limpaTexto(res.toString()));
            return s;
        } catch (AnalysisException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PastaPDFParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/

    private Sentencas geraSentencas(File f) {
        try {
            statusUpdate.updateStatus("Processando arquivo " + f.getName());
            InputStream is = new FileInputStream(f);
            ParseContext parseContext = new ParseContext();
            ContentHandler contentHandler = new BodyContentHandler();
            Metadata metadata = new Metadata();

            Metadata data = new Metadata();
            ParseContext context = new ParseContext();
            PDFParser pdfparser = new PDFParser();
            //pdfparser.setIncludeHeadersAndFooters(false);
            
            pdfparser.parse(is, contentHandler, metadata, parseContext);
            Sentencas s = new Sentencas(f, limpaTexto(contentHandler.toString()));
            return s;
        } catch (IOException ex) {
            Logger.getLogger(PastaPDFParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(PastaPDFParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TikaException ex) {
            Logger.getLogger(PastaPDFParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void realizaBusca(Sinonimos[] sin) {
        for (Sentencas s : sentencas) {
            try {
                statusUpdate.updateStatus("Buscando em " + s.getArquivo().getName() + "...");
                s.analisaPresenca(sin, tableUpdate, percentualMinimo, regex);
            } catch(NullPointerException n) {
                System.out.println("NullPointer");
            }
        }
    }

    private static String limpaTexto(String texto) {
        //texto = texto.replaceAll("\\d+", "");
        texto = texto.replaceAll("-(?:[\n\r]|<\\/?br>|<\\/p>)", "");
        texto = texto.replaceAll("[\n\r]", " ");
        texto = texto.replaceAll("\\s{2,}", " ");
        return texto;
    }

    /**
     * @return the percentualMinimo
     */
    public int getPercentualMinimo() {
        return percentualMinimo;
    }

    /**
     * @param percentualMinimo the percentualMinimo to set
     */
    public void setPercentualMinimo(int percentualMinimo) {
        this.percentualMinimo = percentualMinimo;
    }

    /**
     * @return the regex
     */
    public boolean isRegex() {
        return regex;
    }

    /**
     * @param regex the regex to set
     */
    public void setRegex(boolean regex) {
        this.regex = regex;
    }

}
