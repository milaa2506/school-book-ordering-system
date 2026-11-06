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
public class StavkaPorudzbine extends AbstractDomainObject {

    private Porudzbina porudzbina;
    private int rb;
    private int kolicina;
    private double cena;
    private double iznos;
    private Knjiga knjiga;

    public StavkaPorudzbine(Porudzbina porudzbina, int rb, int kolicina, double cena, double iznos, Knjiga knjiga) {
        this.porudzbina = porudzbina;
        this.rb = rb;
        this.kolicina = kolicina;
        this.cena = cena;
        this.iznos = iznos;
        this.knjiga = knjiga;
    }

    public StavkaPorudzbine() {
    }

    @Override
    public String nazivTabele() {
        return " StavkaPorudzbine ";
    }

    @Override
    public String alijas() {
        return " sp ";
    }

    @Override
    public String join() {
        return " JOIN PORUDZBINA P ON (P.PORUDZBINAID = SP.PORUDZBINAID)\n"
                + "JOIN KUPAC K ON (K.KUPACID = P.KUPACID)\n"
                + "JOIN KATEGORIJA KAT ON (KAT.KATEGORIJAID = K.KATEGORIJAID)\n"
                + "JOIN KNJIZAR KN ON (KN.KNJIZARID = P.KNJIZARID)\n"
                + "JOIN KNJIGA KG ON (KG.KNJIGAID = SP.KNJIGAID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Knjizar kn = new Knjizar(rs.getInt("KnjizarID"),
                    rs.getString("kn.Ime"), rs.getString("kn.Prezime"),
                    rs.getString("KorisnickoIme"), rs.getString("Lozinka"));

            Kategorija kat = new Kategorija(rs.getInt("KategorijaID"),
                    rs.getString("kat.Naziv"));

            Kupac k = new Kupac(rs.getInt("KupacID"), rs.getString("k.Ime"),
                    rs.getString("k.Prezime"), rs.getString("Email"),
                    rs.getString("Telefon"), kat);

            Porudzbina p = new Porudzbina(rs.getInt("PorudzbinaID"),
                    rs.getTimestamp("DatumVreme"), rs.getTimestamp("DatumVremeIsporuke"),
                    rs.getDouble("IznosBezPopusta"), rs.getDouble("Popust"),
                    rs.getDouble("UkupanIznos"), k, kn, null);

            Knjiga kg = new Knjiga(rs.getInt("KnjigaID"),
                    rs.getString("kg.Naziv"),
                    rs.getString("kg.Proizvodjac"),
                    rs.getString("kg.Opis"),
                    rs.getDouble("kg.Cena"));

            StavkaPorudzbine sp = new StavkaPorudzbine(p, rs.getInt("Rb"),
                    rs.getInt("Kolicina"), rs.getDouble("sp.Cena"),
                    rs.getDouble("sp.Iznos"), kg);

            lista.add(sp);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (PorudzbinaID, Rb, Kolicina, Cena, Iznos, KnjigaID) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return " " + porudzbina.getPorudzbinaID() + ", " + rb + ", "
                + kolicina + ", " + cena + ", " + iznos + ", "
                + knjiga.getKnjigaID();
    }

    @Override
    public String vrednostiZaUpdate() {
        return " Kolicina = " + kolicina + ", "
                + "cena = " + cena + ", "
                + "iznos = " + iznos + ", "
                + "knjigaID = " + knjiga.getKnjigaID();
    }

    @Override
    public String uslov() {
        return " porudzbinaID = " + porudzbina.getPorudzbinaID() + " AND RB = " + rb;
    }

    @Override
    public String dodatniUslov() {
        return " WHERE P.PORUDZBINAID = " + porudzbina.getPorudzbinaID();
    }

    @Override
    public String orderBy() {
        return " ORDER BY RB ASC ";
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

}
