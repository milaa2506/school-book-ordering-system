/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mila
 */
public class Porudzbina extends AbstractDomainObject {

    private int porudzbinaID;
    private Date datumVreme;
    private Date datumVremeIsporuke;
    private double iznosBezPopusta;
    private double popust;
    private double ukupanIznos;
    private Kupac kupac;
    private Knjizar knjizar;
    private ArrayList<StavkaPorudzbine> stavkePorudzbine;

    public Porudzbina(int porudzbinaID, Date datumVreme, Date datumVremeIsporuke,
            double iznosBezPopusta, double popust, double ukupanIznos,
            Kupac kupac, Knjizar knjizar, ArrayList<StavkaPorudzbine> stavkePorudzbine) {
        this.porudzbinaID = porudzbinaID;
        this.datumVreme = datumVreme;
        this.datumVremeIsporuke = datumVremeIsporuke;
        this.iznosBezPopusta = iznosBezPopusta;
        this.popust = popust;
        this.ukupanIznos = ukupanIznos;
        this.kupac = kupac;
        this.knjizar = knjizar;
        this.stavkePorudzbine = stavkePorudzbine;
    }

    public Porudzbina() {
    }

  
    public static double izracunajUkupanIznos(double iznosBezPopusta, double popust) {
        double iznosPopusta = iznosBezPopusta * popust / 100;
        return Math.round((iznosBezPopusta - iznosPopusta) * 100) / 100.0;
    }

    @Override
    public String nazivTabele() {
        return " Porudzbina ";
    }

    @Override
    public String alijas() {
        return " p ";
    }

    @Override
    public String join() {
        return " JOIN KUPAC K ON (K.KUPACID = P.KUPACID)\n"
                + "JOIN KATEGORIJA KAT ON (KAT.KATEGORIJAID = K.KATEGORIJAID)\n"
                + "JOIN KNJIZAR KN ON (KN.KNJIZARID = P.KNJIZARID) ";
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
                    rs.getDouble("UkupanIznos"), k, kn, new ArrayList<>());

            lista.add(p);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (DatumVreme, DatumVremeIsporuke, IznosBezPopusta, Popust, "
                + "UkupanIznos, KupacID, KnjizarID) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return "'" + new Timestamp(datumVreme.getTime()) + "', "
                + "'" + new Timestamp(datumVremeIsporuke.getTime()) + "', "
                + " " + iznosBezPopusta + ", " + popust + ", " + ukupanIznos + ", "
                + kupac.getKupacID() + ", " + knjizar.getKnjizarID();
    }

    @Override
    public String vrednostiZaUpdate() {
        return " datumVremeIsporuke = '" + new Timestamp(datumVremeIsporuke.getTime()) + "', "
                + "iznosBezPopusta = " + iznosBezPopusta + ", "
                + "popust = " + popust + ", "
                + "ukupanIznos = " + ukupanIznos + " ";
    }

    @Override
    public String uslov() {
        return " porudzbinaID = " + porudzbinaID;
    }

    @Override
    public String dodatniUslov() {
        if (porudzbinaID != -1) {
            return " WHERE P.PORUDZBINAID = " + porudzbinaID;
        }
        return " WHERE LOWER(K.IME) LIKE '%" + kupac.getIme() + "%' "
                + "AND LOWER(K.PREZIME) LIKE '%" + kupac.getPrezime() + "%' ";
    }

    @Override
    public String orderBy() {
        return " ORDER BY P.PORUDZBINAID ASC ";
    }

    public int getPorudzbinaID() {
        return porudzbinaID;
    }

    public void setPorudzbinaID(int porudzbinaID) {
        this.porudzbinaID = porudzbinaID;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public Date getDatumVremeIsporuke() {
        return datumVremeIsporuke;
    }

    public void setDatumVremeIsporuke(Date datumVremeIsporuke) {
        this.datumVremeIsporuke = datumVremeIsporuke;
    }

    public double getIznosBezPopusta() {
        return iznosBezPopusta;
    }

    public void setIznosBezPopusta(double iznosBezPopusta) {
        this.iznosBezPopusta = iznosBezPopusta;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public Knjizar getKnjizar() {
        return knjizar;
    }

    public void setKnjizar(Knjizar knjizar) {
        this.knjizar = knjizar;
    }

    public ArrayList<StavkaPorudzbine> getStavkePorudzbine() {
        return stavkePorudzbine;
    }

    public void setStavkePorudzbine(ArrayList<StavkaPorudzbine> stavkePorudzbine) {
        this.stavkePorudzbine = stavkePorudzbine;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

}
