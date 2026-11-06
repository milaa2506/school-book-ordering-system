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
public class Knjiga extends AbstractDomainObject {

    private int knjigaID;
    private String naziv;
    private String proizvodjac;
    private String opis;
    private double cena;

    @Override
    public String toString() {
        return naziv + " (" + proizvodjac + ")";
    }

    public Knjiga(int knjigaID, String naziv, String proizvodjac, String opis, double cena) {
        this.knjigaID = knjigaID;
        this.naziv = naziv;
        this.proizvodjac = proizvodjac;
        this.opis = opis;
        this.cena = cena;
    }

    public Knjiga() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return knjigaID == ((Knjiga) obj).knjigaID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(knjigaID);
    }

    @Override
    public String nazivTabele() {
        return " knjiga ";
    }

    @Override
    public String alijas() {
        return " kg ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Knjiga kg = new Knjiga(rs.getInt("KnjigaID"),
                    rs.getString("Naziv"),
                    rs.getString("Proizvodjac"),
                    rs.getString("Opis"),
                    rs.getDouble("Cena"));

            lista.add(kg);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Naziv, Proizvodjac, Opis, Cena) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + naziv + "', '" + proizvodjac + "', '" + opis + "', " + cena + " ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " proizvodjac = '" + proizvodjac + "', opis = '" + opis + "', cena = " + cena;
    }

    @Override
    public String uslov() {
        return " knjigaID = " + knjigaID;
    }

    @Override
    public String dodatniUslov() {
        if (knjigaID != -1) {
            return " WHERE knjigaID = " + knjigaID;
        }
        return " WHERE LOWER(NAZIV) LIKE '%" + naziv + "%' "
                + "AND LOWER(PROIZVODJAC) LIKE '%" + proizvodjac + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY knjigaID ASC ";
    }

    public int getKnjigaID() {
        return knjigaID;
    }

    public void setKnjigaID(int knjigaID) {
        this.knjigaID = knjigaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

}
