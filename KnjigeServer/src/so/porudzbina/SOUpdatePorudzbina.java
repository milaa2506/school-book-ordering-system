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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import so.AbstractSO;

/**
 *
 * @author Mila
 */
public class SOUpdatePorudzbina extends AbstractSO {

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

        
        double ocekivaniUkupanIznos = Porudzbina.izracunajUkupanIznos(
                p.getIznosBezPopusta(), p.getPopust());

        if (Math.abs(ocekivaniUkupanIznos - p.getUkupanIznos()) > 0.01) {
            throw new Exception("Ukupan iznos nije ispravno obracunat!");
        }

    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Porudzbina p = (Porudzbina) ado;

        DBBroker.getInstance().update(p);

        
        ArrayList<StavkaPorudzbine> stareStavke
                = (ArrayList<StavkaPorudzbine>) (ArrayList<?>) DBBroker.getInstance()
                        .select(new StavkaPorudzbine(p, 0, 0, 0, 0, null));

        HashMap<Integer, StavkaPorudzbine> mapaStarih = new HashMap<>();
        for (StavkaPorudzbine sn : stareStavke) {
            mapaStarih.put(sn.getRb(), sn);
        }

        HashMap<Integer, StavkaPorudzbine> mapaNovih = new HashMap<>();
        for (StavkaPorudzbine nova : p.getStavkePorudzbine()) {
            mapaNovih.put(nova.getRb(), nova);
        }

        
        for (StavkaPorudzbine stara : stareStavke) {
            if (!mapaNovih.containsKey(stara.getRb())) {
                DBBroker.getInstance().delete(stara);
            }
        }

        
        for (StavkaPorudzbine nova : p.getStavkePorudzbine()) {
            if (mapaStarih.containsKey(nova.getRb())) {
                DBBroker.getInstance().update(nova);
            }
        }

        
        for (StavkaPorudzbine nova : p.getStavkePorudzbine()) {
            if (!mapaStarih.containsKey(nova.getRb())) {
                DBBroker.getInstance().insert(nova);
            }
        }
    }

}
