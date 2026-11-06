/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Kategorija;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mila
 */
public class TableModelKategorije extends AbstractTableModel implements Runnable {

    private ArrayList<Kategorija> lista;
    private String[] kolone = {"ID", "Naziv"};
    private String parametarNaziv = "";
    private Kategorija kategorija = new Kategorija(-1, "");

    public TableModelKategorije() {
        try {
            lista = ClientController.getInstance()
                    .getAllKategorija(new Kategorija(-1, ""));
        } catch (Exception ex) {
            Logger.getLogger(TableModelKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int i) {
        return kolone[i];
    }

    @Override
    public Object getValueAt(int row, int column) {
        Kategorija kat = lista.get(row);

        switch (column) {
            case 0:
                return kat.getKategorijaID();
            case 1:
                return kat.getNaziv();

            default:
                return null;
        }
    }

    public Kategorija getSelectedKategorija(int row) {
        return lista.get(row);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(10000);
                refreshTable();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(TableModelKategorije.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametarNaziv(String naziv) {
        this.parametarNaziv = naziv;
        refreshTable();
    }

    public void refreshTable() {
        try {

            kategorija.setNaziv(parametarNaziv.toLowerCase());

            lista = ClientController.getInstance().getAllKategorija(kategorija);
            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Kategorija> getLista() {
        return lista;
    }

}
