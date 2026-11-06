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
import so.AbstractSO;

/**
 *
 * @author Mila
 */
public class SOGetAllPorudzbina extends AbstractSO {

    private ArrayList<Porudzbina> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Porudzbina)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Porudzbina!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> porudzbine = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Porudzbina>) (ArrayList<?>) porudzbine;

        for (Porudzbina trenutnaPorudzbina : lista) {

            
            StavkaPorudzbine trenutnaStavka = new StavkaPorudzbine();
            trenutnaStavka.setPorudzbina(trenutnaPorudzbina);

            ArrayList<StavkaPorudzbine> stavkeTrenutnePorudzbine
                    = (ArrayList<StavkaPorudzbine>) (ArrayList<?>) DBBroker
                            .getInstance().select(trenutnaStavka);

            trenutnaPorudzbina.setStavkePorudzbine(stavkeTrenutnePorudzbine);
        }
    }

    public ArrayList<Porudzbina> getLista() {
        return lista;
    }

}
