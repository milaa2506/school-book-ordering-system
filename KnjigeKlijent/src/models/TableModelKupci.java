/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Kategorija;
import domain.Kupac;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mila
 */
public class TableModelKupci extends AbstractTableModel implements Runnable {

    private ArrayList<Kupac> lista;
    private String[] kolone = {"ID", "Ime", "Prezime", "Email", "Telefon", "Kategorija"};
    private String parametarIme = "";
    private String parametarPrezime = "";
    private String parametarEmail = "";
    private String parametarNazivKategorije = "";
    private Kupac kupac = new Kupac(-1, "", "", "", "", new Kategorija(-1, ""));

    public TableModelKupci() {
        try {
            lista = ClientController.getInstance()
                    .getAllKupac(new Kupac(-1, "", "", "", "", new Kategorija(-1, "")));
        } catch (Exception ex) {
            Logger.getLogger(TableModelKupci.class.getName()).log(Level.SEVERE, null, ex);
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
        Kupac k = lista.get(row);

        switch (column) {
            case 0:
                return k.getKupacID();
            case 1:
                return k.getIme();
            case 2:
                return k.getPrezime();
            case 3:
                return k.getEmail();
            case 4:
                return k.getTelefon();
            case 5:
                return k.getKategorija().getNaziv();

            default:
                return null;
        }
    }

    public Kupac getSelectedKupac(int row) {
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
            Logger.getLogger(TableModelKupci.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametarIme(String ime) {
        this.parametarIme = ime;
        refreshTable();
    }

    public void setParametarPrezime(String prezime) {
        this.parametarPrezime = prezime;
        refreshTable();
    }

    public void setParametarEmail(String email) {
        this.parametarEmail = email;
        refreshTable();
    }

    public void setParametarNazivKategorije(String nazivKategorije) {
        this.parametarNazivKategorije = nazivKategorije;
        refreshTable();
    }

    public void refreshTable() {
        try {

            kupac.setIme(parametarIme.toLowerCase());
            kupac.setPrezime(parametarPrezime.toLowerCase());
            kupac.setEmail(parametarEmail.toLowerCase());
            kupac.getKategorija().setNaziv(parametarNazivKategorije.toLowerCase());

            lista = ClientController.getInstance().getAllKupac(kupac);
            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Kupac> getLista() {
        return lista;
    }

}
