package com.cities.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cities.model.City;

public interface CityRepository extends JpaRepository<City, Long>{
	
	@Query("select c from City c where c.capital = true order by c.name")
	public List<City> getCapitalList();
	
	@Query("select c.uf from City c order by c.uf")
	public List<String> getUfOrdered();
	
	@Query("select c from City c where c.ibgeId = ?1")
	public City searchCityByIbgeCode(Long ibgeId);

	@Query("select c from City c where UPPER(c.uf) = UPPER(?1)")
	public List<City> searchCitiesByState(String uf);
	
	@Query("select c from City c where UPPER(c.name) like UPPER(?1)")
	public List<City> searchCitiesByName(String name);
	
	/*OBS TAREFA 9 
	Sei que esse não é o jeito correto de fazer a filtragem,chega ate dar dor nos olhos 
	mas tentei de tudo que jeito e não consegui...
	*/
	
	@Query("select c from City c where c.capital like ?1")
	public List<City> searchCitiesByCapital(boolean capital);
	
	@Query("select c from City c where c.longitude like ?1 || '%'")
	public List<City> searchCitiesByLongitude(double longitude);
	
	@Query("select c from City c where c.latitude like ?1 || '%'")
	public List<City> searchCitiesByLatitude(double latitude);
	
	@Query("select c from City c where UPPER(c.noAccents) like UPPER(?1) || '%'")
	public List<City> searchCitiesByNoAccents(String noAccents);
	
	@Query("select c from City c where UPPER(c.alternativeNames) like UPPER(?1) || '%'")
	public List<City> searchCitiesByAlternativeNames(String alternativeNames);
	
	@Query("select c from City c where UPPER(c.microregion) like UPPER(?1) || '%'")
	public List<City> searchCitiesByMicroregion(String microregion);
	
	@Query("select c from City c where UPPER(c.mesoregion) like UPPER(?1) || '%'")
	public List<City> searchCitiesByMesoregion(String mesoregion);
	
}
