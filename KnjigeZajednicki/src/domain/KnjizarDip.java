/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Mila
 */
public class KnjizarDip extends AbstractDomainObject {

    private Knjizar knjizar;
    private Diploma diploma;
    private Date datumDiplome;
    private double prosecnaOcena;

    public KnjizarDip(Knjizar knjizar, Diploma diploma, Date datumDiplome, double prosecnaOcena) {
        this.knjizar = knjizar;
        this.diploma = diploma;
        this.datumDiplome = datumDiplome;
        this.prosecnaOcena = prosecnaOcena;
    }

    public KnjizarDip() {
    }

    @Override
    public String nazivTabele() {
        return " KnjizarDip ";
    }

    @Override
    public String alijas() {
        return " kd ";
    }

    @Override
    public String join() {
        return " JOIN KNJIZAR KN ON (KN.KNJIZARID = KD.KNJIZARID)\n"
                + "JOIN DIPLOMA D ON (D.DIPLOMAID = KD.DIPLOMAID) ";
    }

    @Override
    public ArrayList<AbstractDomainObject> vratiListu(ResultSet rs) throws SQLException {
        ArrayList<AbstractDomainObject> lista = new ArrayList<>();

        while (rs.next()) {
            Knjizar kn = new Knjizar(rs.getInt("KnjizarID"),
                    rs.getString("kn.Ime"), rs.getString("kn.Prezime"),
                    rs.getString("KorisnickoIme"), rs.getString("Lozinka"));

            Diploma d = new Diploma(rs.getInt("DiplomaID"),
                    rs.getString("d.Naziv"), rs.getString("d.Opis"));

            KnjizarDip kd = new KnjizarDip(kn, d, rs.getDate("DatumDiplome"),
                    rs.getDouble("ProsecnaOcena"));

            lista.add(kd);
        }

        rs.close();
        return lista;
    }

    @Override
    public String koloneZaInsert() {
        return " (KnjizarID, DiplomaID, DatumDiplome, ProsecnaOcena) ";
    }

    @Override
    public String vrednostiZaInsert() {
        return " " + knjizar.getKnjizarID() + ", " + diploma.getDiplomaID() + ", "
                + " '" + new java.sql.Date(datumDiplome.getTime()) + "', "
                + prosecnaOcena + " ";
    }

    @Override
    public String vrednostiZaUpdate() {
        return " DatumDiplome = '" + new java.sql.Date(datumDiplome.getTime()) + "', "
                + "ProsecnaOcena = " + prosecnaOcena + " ";
    }

    @Override
    public String uslov() {
        return " KnjizarID = " + knjizar.getKnjizarID()
                + " AND DiplomaID = " + diploma.getDiplomaID();
    }

    @Override
    public String dodatniUslov() {
        return " WHERE KD.KNJIZARID = " + knjizar.getKnjizarID();
    }

    @Override
    public String orderBy() {
        return " ORDER BY KD.DATUMDIPLOME ASC ";
    }

    public Knjizar getKnjizar() {
        return knjizar;
    }

    public void setKnjizar(Knjizar knjizar) {
        this.knjizar = knjizar;
    }

    public Diploma getDiploma() {
        return diploma;
    }

    public void setDiploma(Diploma diploma) {
        this.diploma = diploma;
    }

    public Date getDatumDiplome() {
        return datumDiplome;
    }

    public void setDatumDiplome(Date datumDiplome) {
        this.datumDiplome = datumDiplome;
    }

    public double getProsecnaOcena() {
        return prosecnaOcena;
    }

    public void setProsecnaOcena(double prosecnaOcena) {
        this.prosecnaOcena = prosecnaOcena;
    }

}
