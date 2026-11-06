/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.kupac;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Kategorija;
import domain.Kupac;
import java.util.ArrayList;
import java.util.regex.Pattern;
import so.AbstractSO;

/**
 *
 * @author Mila
 */
public class SOAddKupac extends AbstractSO {

    private static final Pattern EMAIL_PATTERN
            = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern TELEFON_PATTERN
            = Pattern.compile("^06[0-9]{8}$");

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Kupac)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Kupac!");
        }

        Kupac noviKupac = (Kupac) ado;

        if (!EMAIL_PATTERN.matcher(noviKupac.getEmail()).matches()) {
            throw new Exception("Email nije u ispravnom formatu!");
        }

        if (!TELEFON_PATTERN.matcher(noviKupac.getTelefon()).matches()) {
            throw new Exception("Telefon mora biti u formatu 06XXXXXXXX!");
        }

        ArrayList<Kupac> kupci = (ArrayList<Kupac>) (ArrayList<?>) DBBroker.getInstance()
                .select(new Kupac(-1, "", "", "", "", new Kategorija(-1, "")));

        for (Kupac kupacIzBaze : kupci) {
            if (kupacIzBaze.getEmail().equals(noviKupac.getEmail())) {
                throw new Exception("Kupac sa tim emailom vec postoji!");
            }
            if (kupacIzBaze.getTelefon().equals(noviKupac.getTelefon())) {
                throw new Exception("Kupac sa tim telefonom vec postoji!");
            }
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        DBBroker.getInstance().insert(ado);
    }

}
