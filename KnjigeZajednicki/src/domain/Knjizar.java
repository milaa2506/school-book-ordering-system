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
public class Knjizar extends AbstractDomainObject {

    private int knjizarID;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String lozinka;

    public Knjizar() {
    }

    public Knjizar(int knjizarID, String ime, String prezime, String korisnickoIme, String lozinka) {
        this.knjizarID = knjizarID;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.lozinka = lozinka;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Knjizar other = (Knjizar) obj;
        if (!Objects.equals(this.knjizarID, other.knjizarID)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(knjizarID);
    }

    public int getKnjizarID() {
        return knjizarID;
    }

    public void setKnjizarID(int knjizarID) {
        this.knjizarID = knjizarID;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String nazivTabele() {
        return " knjizar ";
    }

    @Override
    public String alijas() {
        return " kn ";
    }

    @Override
    public String join() {
        return "";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Knjizar kn = new Knjizar(rs.getInt("KnjizarID"),
                    rs.getString("Ime"), rs.getString("Prezime"),
                    rs.getString("KorisnickoIme"), rs.getString("Lozinka"));

            lista.add(kn);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Ime, Prezime, KorisnickoIme, Lozinka) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', "
                + "'" + korisnickoIme + "', '" + lozinka + "' ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " Ime = '" + ime + "', Prezime = '" + prezime + "', "
                + "korisnickoIme = '" + korisnickoIme + "', lozinka = '" + lozinka + "' ";
    }

    @Override
    public String uslov() {
        return " knjizarID = " + knjizarID;
    }

    @Override
    public String dodatniUslov() {
        if (knjizarID != -1) {
            return " WHERE knjizarID = " + knjizarID;
        }
        return " WHERE LOWER(IME) LIKE '%" + ime + "%' "
                + "AND LOWER(PREZIME) LIKE '%" + prezime + "%' "
                + "AND LOWER(KORISNICKOIME) LIKE '%" + korisnickoIme + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY KNJIZARID ASC ";
    }

}
