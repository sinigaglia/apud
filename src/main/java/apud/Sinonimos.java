package apud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sinonimos {

    private static boolean VERBOSE = false;

    private String palavra;
    private List<String> sinonimos;

    private static Pattern BLOCO = Pattern.compile("=+Synonyms=+\\s([^=]+)\\s=+");
    private static Pattern BLOCO2 = Pattern.compile("=+Related terms=+\\s([^=]+)\\s=+");
    private static Pattern PALAVRA = Pattern.compile("\\{\\{l\\|[a-z]+\\|([^}]+)\\}\\}");

    public Sinonimos(String palavra) {
        sinonimos = new ArrayList();
        this.palavra = palavra;
        sinonimos.add(palavra);
        try {
            buscaOnline();
        } catch (IOException ex) {
            Logger.getLogger(Sinonimos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Sinonimos(String palavra, boolean b) {
        sinonimos = new ArrayList();
        this.palavra = palavra;
        sinonimos.add(palavra);
    }

    public String getPalavra() {
        return palavra;
    }

    public List<String> getSinonimos() {
        return sinonimos;
    }

    private void parseSinonimos(String texto) {
        Matcher m = BLOCO.matcher(texto);
        if (m.find()) {
            String result = m.group(1).replaceAll("\n", "");
            //System.out.println(result);
            Matcher word = PALAVRA.matcher(result);
            while (word.find()) {
                sinonimos.add(word.group(1));
            }
            if (VERBOSE) {
                System.out.println(sinonimos);
            }
        } else {
            if (VERBOSE) {
                System.err.println(texto);
            }
        }
        m = BLOCO2.matcher(texto);
        if (m.find()) {
            String result = m.group(1).replaceAll("\n", "");
            //System.out.println(result);
            Matcher word = PALAVRA.matcher(result);
            while (word.find()) {
                sinonimos.add(word.group(1));
            }
            if (VERBOSE) {
                System.out.println(sinonimos);
            }
        } else {
            if (VERBOSE) {
                System.err.println(texto);
            }
        }
    }

    private boolean buscaOnline() throws MalformedURLException, IOException {
        //https://en.wiktionary.org/w/index.php?title=path&action=raw

        InputStream is = new URL(formaUrl()).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String texto = readAll(rd);
            parseSinonimos(texto);
        } finally {
            is.close();
        }

        return true;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private String formaUrl() {
        return "https://en.wiktionary.org/w/index.php?title=" + palavra + "&action=raw";
    }

}
