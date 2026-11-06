/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.kupac;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Kupac;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author Mila
 */
public class SOGetAllKupac extends AbstractSO {

    private ArrayList<Kupac> lista;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Kupac)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Kupac!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        ArrayList<AbstractDomainObject> kupci = DBBroker.getInstance().select(ado);
        lista = (ArrayList<Kupac>) (ArrayList<?>) kupci;
    }

    public ArrayList<Kupac> getLista() {
        return lista;
    }

}
