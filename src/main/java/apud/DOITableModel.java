/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Xando
 */
public class DOITableModel extends AbstractTableModel {

    private List<String> dois;
    private List<String> caminhos;
    private List<JProgressBar> progressos;
    private List<JButton> botoes;
    //private ActionListener action;

    private final String[] colunas = {"DOI", "Progresso", "Caminho", "Abrir"};

    public DOITableModel() {
        super();
        this.dois = new ArrayList<>();
        this.caminhos = new ArrayList<>();
        this.progressos = new ArrayList<>();
        this.botoes = new ArrayList<>();
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        if (col == 3) {
            return true;
        } else {
            return false;
        }
    }

    public List<String> getDois() {
        return dois;
    }

    public List<JProgressBar> getProgressos() {
        return progressos;
    }

    public List<JButton> getBotoes() {
        return botoes;
    }

    public List<String> getCaminhos() {
        return caminhos;
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public boolean adicionarRow(String l, String caminho) {
        if (!dois.contains(l)) {
            System.out.println(l + " -> " + caminho);
            boolean b = dois.add(l);
            caminhos.add(caminho);
            File f = new File(caminho);
            JProgressBar progress = new JProgressBar(0, 100);
            progressos.add(progress);
            JButton btn = new JButton("Visualizar");
            btn.setEnabled(false);
            botoes.add(btn);
            if (f.exists()) {
                progress.setValue(100);
                btn.setEnabled(true);
            }
            fireTableDataChanged();
            return b;
        }
        return false;
    }

    @Override
    public String getColumnName(int col) {
        return colunas[col];
    }
    
    public void fireContentChanged(int row) {
        fireTableCellUpdated(row, 1);
        fireTableCellUpdated(row, 3);
    }

    @Override
    public int getRowCount() {
        return dois.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    public void limpa() {
        this.dois = new ArrayList<>();
        this.caminhos = new ArrayList<>();
        this.botoes = new ArrayList<>();
        this.progressos = new ArrayList<>();
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            switch (columnIndex) {
                case 0:
                    return dois.get(rowIndex);
                case 1:
                    return progressos.get(rowIndex);
                case 2:
                    return caminhos.get(rowIndex);
                case 3:
                    return botoes.get(rowIndex);
            }
        } catch (java.lang.IndexOutOfBoundsException ex) {
        }
        return "";
    }
}
