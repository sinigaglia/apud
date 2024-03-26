/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Adriano
 */
public class HistoricoListModel extends AbstractListModel implements ComboBoxModel {

    private List<String> historico;
    private String selected;

    public HistoricoListModel() {
        this.historico = new ArrayList();
        selected = null;
    }
    
    public void addHistorico(String linha) {
        if(!historico.contains(linha)) {
            historico.add(linha);
            fireContentsChanged(this, 0, historico.size());
        }
    }
    
    @Override
    public int getSize() {
        return historico.size();
    }

    @Override
    public Object getElementAt(int index) {
        return historico.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.selected = (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return (selected != null ? selected : "");
    }
    
}
