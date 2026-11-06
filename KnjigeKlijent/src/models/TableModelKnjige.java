/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Knjiga;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mila
 */
public class TableModelKnjige extends AbstractTableModel implements Runnable {

    private ArrayList<Knjiga> lista;
    private String[] kolone = {"ID", "Naziv", "Proizvodjac", "Cena"};
    private String parametarNaziv = "";
    private String parametarProizvodjac = "";
    private Knjiga knjiga = new Knjiga(-1, "", "", "", 0);

    public TableModelKnjige() {
        try {
            lista = ClientController.getInstance()
                    .getAllKnjiga(new Knjiga(-1, "", "", "", 0));
        } catch (Exception ex) {
            Logger.getLogger(TableModelKnjige.class.getName()).log(Level.SEVERE, null, ex);
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
        Knjiga kg = lista.get(row);

        switch (column) {
            case 0:
                return kg.getKnjigaID();
            case 1:
                return kg.getNaziv();
            case 2:
                return kg.getProizvodjac();
            case 3:
                return kg.getCena() + " din";

            default:
                return null;
        }
    }

    public Knjiga getSelectedKnjiga(int row) {
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
            Logger.getLogger(TableModelKnjige.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametarNaziv(String naziv) {
        this.parametarNaziv = naziv;
        refreshTable();
    }

    public void setParametarProizvodjac(String proizvodjac) {
        this.parametarProizvodjac = proizvodjac;
        refreshTable();
    }

    public void refreshTable() {
        try {

            knjiga.setNaziv(parametarNaziv.toLowerCase());
            knjiga.setProizvodjac(parametarProizvodjac.toLowerCase());

            lista = ClientController.getInstance().getAllKnjiga(knjiga);
            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Knjiga> getLista() {
        return lista;
    }

}
