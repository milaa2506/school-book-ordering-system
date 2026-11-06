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
public class Kupac extends AbstractDomainObject {

    private int kupacID;
    private String ime;
    private String prezime;
    private String email;
    private String telefon;
    private Kategorija kategorija;

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    public Kupac(int kupacID, String ime, String prezime, String email, String telefon, Kategorija kategorija) {
        this.kupacID = kupacID;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.kategorija = kategorija;
    }

    public Kupac() {
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return kupacID == ((Kupac) obj).kupacID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(kupacID);
    }

    @Override
    public String nazivTabele() {
        return " kupac ";
    }

    @Override
    public String alijas() {
        return " k ";
    }

    @Override
    public String join() {
        return " JOIN KATEGORIJA KAT ON (KAT.KATEGORIJAID = K.KATEGORIJAID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {

            Kategorija kat = new Kategorija(rs.getInt("KategorijaID"),
                    rs.getString("kat.Naziv"));

            Kupac k = new Kupac(rs.getInt("KupacID"), rs.getString("k.Ime"),
                    rs.getString("k.Prezime"), rs.getString("Email"),
                    rs.getString("Telefon"), kat);

            lista.add(k);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (Ime, Prezime, Email, Telefon, KategorijaID) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + ime + "', '" + prezime + "', "
                + "'" + email + "', '" + telefon + "', " + kategorija.getKategorijaID();
    }

    @Override
    public String vrednostiZaUpdate() {
        return " email = '" + email + "', telefon = '" + telefon + "', kategorijaID = "
                + kategorija.getKategorijaID();
    }

    @Override
    public String uslov() {
        return " kupacID = " + kupacID;
    }

    @Override
    public String dodatniUslov() {
        if (kupacID != -1) {
            return " WHERE K.KUPACID = " + kupacID;
        }
        return " WHERE LOWER(K.IME) LIKE '%" + ime + "%' "
                + "AND LOWER(K.PREZIME) LIKE '%" + prezime + "%' "
                + "AND LOWER(K.EMAIL) LIKE '%" + email + "%' "
                + "AND LOWER(KAT.NAZIV) LIKE '%" + kategorija.getNaziv() + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY K.KUPACID ASC ";
    }

    public int getKupacID() {
        return kupacID;
    }

    public void setKupacID(int kupacID) {
        this.kupacID = kupacID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

}
