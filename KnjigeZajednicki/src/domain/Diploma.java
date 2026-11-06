/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mila
 */
public class Diploma extends AbstractDomainObject {

    private int diplomaID;
    private String naziv;
    private String opis;

    public Diploma(int diplomaID, String naziv, String opis) {
        this.diplomaID = diplomaID;
        this.naziv = naziv;
        this.opis = opis;
    }

    public Diploma() {
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String nazivTabele() {
        return " Diploma ";
    }

    @Override
    public String alijas() {
        return " d ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Diploma d = new Diploma(rs.getInt("DiplomaID"),
                    rs.getString("Naziv"), rs.getString("Opis"));

            lista.add(d);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Naziv, Opis) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + naziv + "', '" + opis + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " naziv = '" + naziv + "', opis = '" + opis + "' ";
    }

    @Override
    public String uslov() {
        return " DiplomaID = " + diplomaID;
    }

    @Override
    public String dodatniUslov() {
        if (diplomaID != -1) {
            return " WHERE diplomaID = " + diplomaID;
        }
        return " WHERE LOWER(NAZIV) LIKE '%" + naziv + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY DIPLOMAID ASC ";
    }

    public int getDiplomaID() {
        return diplomaID;
    }

    public void setDiplomaID(int diplomaID) {
        this.diplomaID = diplomaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

}
