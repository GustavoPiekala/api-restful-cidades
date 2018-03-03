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
	
}
