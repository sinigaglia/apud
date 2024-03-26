/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Adriano
 */

//Oque achou e aonde achou

public class Presenca implements Comparable<Presenca> {
    private File arquivo;
    private String sentenca;
    private List<String> busca;
    private int percentual;
    
    private String sentencaAnterior;
    private String sentencaPosterior;

    public Presenca(File arquivo, String sentenca, List<String> busca, int percentual) {
        this.arquivo = arquivo;
        this.sentenca = sentenca;
        this.busca = busca;
        this.percentual = percentual;
        this.sentencaAnterior = this.sentencaPosterior = "";
        
        //System.out.println(this.toString());
    }

    public String getSentencaAnterior() {
        return sentencaAnterior;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.sentenca);
        hash = 73 * hash + Objects.hashCode(this.busca);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Presenca other = (Presenca) obj;
        if (!Objects.equals(this.sentenca, other.sentenca)) {
            return false;
        }
        if (!Objects.equals(this.busca, other.busca)) {
            return false;
        }
        return true;
    }

    
    //frase anterior e posterior para usuário ver contexto
    public void setSentencaAnterior(String sentencaAnterior) {
        this.sentencaAnterior = sentencaAnterior;
    }

    public String getSentencaPosterior() {
        return sentencaPosterior;
    }

    public void setSentencaPosterior(String sentencaPosterior) {
        this.sentencaPosterior = sentencaPosterior;
    }

    @Override
    public String toString() {
        return "Presenca{" + "arquivo=" + arquivo + ", sentenca=" + sentenca + ", busca=" + busca + ", percentual=" + percentual + ", sentencaAnterior=" + sentencaAnterior + ", sentencaPosterior=" + sentencaPosterior + '}';
    }

    
    
    public File getArquivo() {
        return arquivo;
    }

    public String getSentenca() {
        return sentenca;
    }

    public List<String> getBusca() {
        return busca;
    }

    public int getPercentual() {
        return percentual;
    }
    
    
    
    @Override
    public int compareTo(Presenca o) {
        return 100 - percentual;
    }
}
