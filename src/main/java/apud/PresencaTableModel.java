/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Adriano
 */
public class PresencaTableModel extends AbstractTableModel {

    private List<Presenca> rows;

    private String[] colunas = {"File", "Terms", "Sentence", "Relevance"};

    public PresencaTableModel() {
        super();
        this.rows = new ArrayList<>();
    }
    
    public Presenca getPresenca(int row) {
        return rows.get(row);
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public boolean adicionarRow(Presenca l) {
        if(!rows.contains(l)) {
            boolean b = rows.add(l);
            fireTableDataChanged();
            return b;
        }
        return false;
    }

    @Override
    public String getColumnName(int col) {
        return colunas[col];
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    public void limpa() {
        this.rows = new ArrayList<>();
        fireTableDataChanged();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Presenca p = rows.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return p.getArquivo().getName();
                case 1:
                    return p.getBusca().toString();
                case 2:
                    return p.getSentenca();
                case 3:
                    return new Integer(p.getPercentual());
            }
        } catch (java.lang.IndexOutOfBoundsException ex) {
        }
        return "";
    }
}
