/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.porudzbina;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Porudzbina;
import domain.StavkaPorudzbine;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import so.AbstractSO;

/**
 *
 * @author Mila
 */
public class SOAddPorudzbina extends AbstractSO {

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Porudzbina)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Porudzbina!");
        }

        Porudzbina p = (Porudzbina) ado;

        if (!p.getDatumVremeIsporuke().after(new Date())) {
            throw new Exception("Datum isporuke mora biti posle danasnjeg datuma!");
        }

        if (p.getStavkePorudzbine().isEmpty()) {
            throw new Exception("Porudzbina mora imati barem jednu stavku!");
        }

        double zbirStavki = 0;
        for (StavkaPorudzbine stavka : p.getStavkePorudzbine()) {
            zbirStavki += stavka.getIznos();
        }

        if (Math.abs(zbirStavki - p.getIznosBezPopusta()) > 0.01) {
            throw new Exception("Iznos bez popusta nije jednak zbiru iznosa stavki!");
        }

        if (p.getPopust() != p.getKupac().getKategorija().getPopust()) {
            throw new Exception("Popust ne odgovara kategoriji kupca!");
        }
        double ocekivaniUkupanIznos = Porudzbina.izracunajUkupanIznos(
                p.getIznosBezPopusta(), p.getPopust());

        if (Math.abs(ocekivaniUkupanIznos - p.getUkupanIznos()) > 0.01) {
            throw new Exception("Ukupan iznos nije ispravno obracunat!");
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        
        PreparedStatement ps = DBBroker.getInstance().insert(ado);

        ResultSet tableKeys = ps.getGeneratedKeys(); 
        tableKeys.next(); 
        int novaPorudzbinaID = tableKeys.getInt(1);

       
        Porudzbina novaPorudzbina = (Porudzbina) ado;
        novaPorudzbina.setPorudzbinaID(novaPorudzbinaID);
        
        for (StavkaPorudzbine stavkaPorudzbine : novaPorudzbina.getStavkePorudzbine()) {
            stavkaPorudzbine.setPorudzbina(novaPorudzbina);
            DBBroker.getInstance().insert(stavkaPorudzbine);
        }
    }

}
