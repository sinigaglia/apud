/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Adriano
 */
public class Sentencas {

    //faz uma lista de string com frases do pdf
    private File arquivo;
    private List<String> vetor;

    private Pattern SEPARADOR = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]['\"]?(?=\\s|$)");
    private Pattern BIBLIOGRAFIA = Pattern.compile(".*,?\\s\\d+[,:]\\s?\\d+\\s?–\\s?\\d+");

    public Sentencas(File arquivo, String buffer) {
        vetor = new ArrayList();
        this.arquivo = arquivo;
        Pattern abrevia = Pattern.compile("([A-Z]|(?:spp)|(?:et al))\\.");      // trata abreviatura e divisão de pontos
        Matcher a = abrevia.matcher(buffer);
        buffer = a.replaceAll("$1");    //troca abreviações por texto sem ponto para não quebrar frase
        //buffer = buffer.toLowerCase();
        Matcher m = SEPARADOR.matcher(buffer);      //faz a separação das frases usando pattern
        while (m.find()) {
            vetor.add(m.group());   //frases separadas são adicionatas nesse List<String>
            //System.out.println(m.group());
        }
    }

    private void montaArray(String sentenca, int index, String[] palavras, List<String>[] fontes, int nivel, boolean regex, TableUpdateInterface tui, int percentualMinimo) {
        if (nivel == palavras.length) {
            int encontrado = 0;
            
            List<String> encontrados = new ArrayList();
            for (String palavraABuscar : palavras) {
                if (regex) {
                    Pattern p = Pattern.compile(palavraABuscar);
                    Matcher m = p.matcher(sentenca);
                    if (m.find()) {
                        encontrado++;
                        encontrados.add(m.group());
                    }
                } else {
                    if (sentenca.toLowerCase().contains(palavraABuscar.toLowerCase())) {
                        encontrado++;
                        encontrados.add(palavraABuscar);
                    }
                }
            }
            if (encontrado > 0) {
                int percentual = (int) (((double) encontrado / (double) palavras.length) * 100d);
                if (percentual > percentualMinimo) {
                    Presenca p = new Presenca(arquivo, sentenca, encontrados, percentual);
                    if (index > 0) {
                        p.setSentencaAnterior(vetor.get(index - 1));
                    }
                    if (index < vetor.size() - 1) {
                        p.setSentencaPosterior(vetor.get(index + 1));
                    }
                    //presenca.add(p);
                    Matcher m = BIBLIOGRAFIA.matcher(p.getSentencaPosterior());
                    if (!m.find()) {
                        tui.updateTable(p);
                    } else {
                        //System.out.println("Ignorado por ser bibliografia: " + p.toString());
                    }
                }
            }
        } else {
            for (int i = 0; i < fontes[nivel].size(); i++) {
                palavras[nivel] = fontes[nivel].get(i);
                montaArray(sentenca, index, palavras, fontes, nivel + 1, regex, tui, percentualMinimo);
            }
        }
    }

    public void analisaPresenca(Sinonimos[] sinos, TableUpdateInterface tui, int percentualMinimo, boolean regex) {
        //List<Presenca> presenca = new ArrayList();
        List<String>[] fontes = new List[sinos.length];
        for (int i = 0; i < sinos.length; i++) {
            fontes[i] = sinos[i].getSinonimos();
        }

        int index = 0;
        for (String sentenca : vetor) {
            String[] palavras = new String[fontes.length];
            montaArray(sentenca, index++, palavras, fontes, 0, regex, tui, percentualMinimo);
        }
        //Collections.sort(presenca);
        //System.out.println(presenca);
    }

    public File getArquivo() {
        return arquivo;
    }

}
