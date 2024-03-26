/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apud;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class CustomCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    private JButton editorValue;
    private JTable tabela;
    private DOITableModel dtm;
    
    public CustomCellEditor(JTable table, DOITableModel a) {
        this.tabela = table;
        this.dtm = a;
        editorValue = new JButton("Visualizar");
        editorValue.addActionListener(this);
    } 

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return editorValue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = tabela.convertRowIndexToModel(tabela.getEditingRow());
        fireEditingStopped();
        if(dtm.getBotoes().get(row).isEnabled()) {
            try {
                Desktop.getDesktop().open(new File(dtm.getCaminhos().get(row)));
            } catch (IOException ex) {
                Logger.getLogger(CustomCellEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
