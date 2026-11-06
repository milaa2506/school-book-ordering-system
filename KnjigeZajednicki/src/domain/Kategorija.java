/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Mila
 */
public class Kategorija extends AbstractDomainObject {

    private int kategorijaID;
    private String naziv;

    @Override
    public String toString() {
        return naziv + " (popust " + (int) getPopust() + "%)";
    }

    public Kategorija(int kategorijaID, String naziv) {
        this.kategorijaID = kategorijaID;
        this.naziv = naziv;
    }

    public Kategorija() {
    }

    
    public double getPopust() {
        switch (kategorijaID) {
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 15;
            default:
                return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return kategorijaID == ((Kategorija) obj).kategorijaID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(kategorijaID);
    }

    @Override
    public String nazivTabele() {
        return " Kategorija ";
    }

    @Override
    public String alijas() {
        return " kat ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Kategorija kat = new Kategorija(rs.getInt("KategorijaID"),
                    rs.getString("Naziv"));

            lista.add(kat);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (naziv) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return " '" + naziv + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " naziv = '" + naziv + "' ";
    }

    @Override
    public String uslov() {
        return " kategorijaID = " + kategorijaID;
    }

    @Override
    public String dodatniUslov() {
        if (kategorijaID != -1) {
            return " WHERE kategorijaID = " + kategorijaID;
        }
        return " WHERE LOWER(NAZIV) LIKE '%" + naziv + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY KATEGORIJAID ASC ";
    }

    public int getKategorijaID() {
        return kategorijaID;
    }

    public void setKategorijaID(int kategorijaID) {
        this.kategorijaID = kategorijaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

}
