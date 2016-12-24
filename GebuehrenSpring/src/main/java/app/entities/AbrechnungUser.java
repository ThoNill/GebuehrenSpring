package app.entities;

import app.ApplicationContextProvider;
import app.services.AbrechnungService;
import buchung.Bewegungen;
import buchung.BuchungsAuftrag;

/*
 * 
 * RepositoryFactorySupport factory = … // Instantiate factory here
 UserRepository repository = factory.getRepository(UserRepository.class);
 */
/**
 * Overwrite
 */
public class AbrechnungUser extends AbrechnungBasis {

    public AbrechnungUser() {
        super();
    }

    protected AbrechnungService getService() {
        return ApplicationContextProvider.getApplicationContext().getBean(
                AbrechnungService.class);
    }

    public void insertBuchung(BuchungsAuftrag auftrag) {
        getService().insertBuchung((Abrechnung) this, auftrag);

    }

    public Bewegungen getAktuelleWerte(Enum<?> art) {
        return getService().getAktuelleWerte((Abrechnung) this, art);
    }

}
