/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controller.ClientController;
import domain.Kategorija;
import domain.Knjizar;
import domain.Kupac;
import domain.Porudzbina;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mila
 */
public class TableModelPorudzbine extends AbstractTableModel implements Runnable {

    private ArrayList<Porudzbina> lista;
    private String[] kolone = {"ID", "Kupac", "Kategorija", "Datum isporuke",
        "Bez popusta", "Popust", "Ukupan iznos"};
    private String parametarIme = "";
    private String parametarPrezime = "";
    Porudzbina porudzbina = new Porudzbina(-1, null, null, 0, 0, 0,
            new Kupac(-1, "", "", "", "", new Kategorija(-1, "")),
            new Knjizar(-1, "", "", "", ""), null);

    public TableModelPorudzbine() {
        try {
            lista = ClientController.getInstance()
                    .getAllPorudzbina(new Porudzbina(-1, null, null, 0, 0, 0,
                            new Kupac(-1, "", "", "", "", new Kategorija(-1, "")),
                            new Knjizar(-1, "", "", "", ""), null));
        } catch (Exception ex) {
            Logger.getLogger(TableModelPorudzbine.class.getName()).log(Level.SEVERE, null, ex);
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
        Porudzbina p = lista.get(row);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        switch (column) {
            case 0:
                return p.getPorudzbinaID();
            case 1:
                return p.getKupac();
            case 2:
                return p.getKupac().getKategorija().getNaziv();
            case 3:
                return sdf.format(p.getDatumVremeIsporuke());
            case 4:
                return p.getIznosBezPopusta() + " din";
            case 5:
                return (int) p.getPopust() + "%";
            case 6:
                return p.getUkupanIznos() + " din";

            default:
                return null;
        }
    }

    public Porudzbina getSelectedPorudzbina(int row) {
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
            Logger.getLogger(TableModelPorudzbine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setParametarIme(String parametarIme) {
        this.parametarIme = parametarIme;
        refreshTable();
    }

    public void setParametarPrezime(String parametarPrezime) {
        this.parametarPrezime = parametarPrezime;
        refreshTable();
    }

    public void refreshTable() {
        try {

            porudzbina.getKupac().setIme(parametarIme.toLowerCase());
            porudzbina.getKupac().setPrezime(parametarPrezime.toLowerCase());

            lista = ClientController.getInstance().getAllPorudzbina(porudzbina);
            fireTableDataChanged();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Porudzbina> getLista() {
        return lista;
    }

}
