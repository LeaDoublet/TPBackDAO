package monprojet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.City;
import monprojet.entity.Country;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {


    /*@Query(value = "SELECT SUM(city.population) FROM Country country JOIN country.cities city WHERE country.id = ?1")
    Integer calculatePopulationByCountryId(Integer countryId);

    @Query(value = "SELECT country.name, SUM(city.population) FROM Country country JOIN country.cities city GROUP BY country")
    List<Object[]> getCountryPopulation(); */

    @Query(value = "SELECT SUM(city.population) FROM City city WHERE city.country.id = :countryId")
    Integer calculatePopulationByCountryId2(@Param("countryId") Integer countryId);
}
