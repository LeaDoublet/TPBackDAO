package monprojet.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }


    }
    @Test
    void testCalculatePopulationByCountryId() {
        // Le pays avec l'ID 1 qui est FRANCE a une population totale de 12 que l'on a affecté à Paris
        Integer expectedPopulation = 12;
        Integer actualPopulation = countryDAO.calculatePopulationByCountryId(1);
        assertEquals(expectedPopulation, actualPopulation, "La population calculée ne correspond pas.");
    }
    @Test
    void testGetCountryPopulation() {
        List<Object[]> countryPopulationList = countryDAO.getCountryPopulation();

        // On 3 pays dans notre jeu de données de test
        // et la population totale pour chacun d'eux est [12, 18, 27]
        assertEquals(3, countryPopulationList.size(), "Le nombre de pays retourné ne correspond pas.");

        Object[] firstCountryPopulation = countryPopulationList.get(0);
        assertEquals("France", firstCountryPopulation[0], "Le nom du premier pays ne correspond pas.");
        assertEquals((long)12, firstCountryPopulation[1], "La population du premier pays ne correspond pas.");

        Object[] secondCountryPopulation = countryPopulationList.get(1);
        assertEquals("United Kingdom", secondCountryPopulation[0], "Le nom du deuxième pays ne correspond pas.");
        assertEquals((long)18, secondCountryPopulation[1], "La population du deuxième pays ne correspond pas.");

        Object[] thirdCountryPopulation = countryPopulationList.get(2);
        assertEquals("United States of America", thirdCountryPopulation[0], "Le nom du troisième pays ne correspond pas.");
        assertEquals((long)27, thirdCountryPopulation[1], "La population du troisième pays ne correspond pas.");
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }

}
