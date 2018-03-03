package com.cities.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cities.helper.ImportCsvCities;
import com.cities.model.CitiesByState;
import com.cities.model.City;
import com.cities.repository.CityRepository;

@RestController
@RequestMapping("/cities")
public class CidadeResource {

	@Autowired
	private CityRepository cityRepository;
	
	@GetMapping
	public List<City> getCityList(){
		return cityRepository.findAll();
	}
	
	@GetMapping("/capitals")
	public List<City> getOnlyCapitals() {

		return cityRepository.getCapitalList();
	}
	
	@GetMapping("/numberOfCitiesPerState")
	public List<CitiesByState> getNumberOfCitiesPerState(){
		
		return getCityListPerState();
		
	}

	@GetMapping("/minorMajorCitiesNumber")
	public List<CitiesByState> getMinorMajorCitiesNumber(){
		
		return getMinorMajorCitiesNumberList();
	}
	
	private List<CitiesByState> getMinorMajorCitiesNumberList() {
		
		List<CitiesByState> citiesByStateList = getCityListPerState();
		List<CitiesByState> minorMajorCitiesList = new ArrayList<>();
		
		int major = 0;
		int minor = 0;
		CitiesByState majorStateCities = new CitiesByState();
		CitiesByState minorStateCities = new CitiesByState();
		
		int index = 0;
		for (CitiesByState citiesPerState : citiesByStateList) {
			
			if (index == 0) {
				major = citiesPerState.getNumberOfCities();
				minor = citiesPerState.getNumberOfCities();
			}

			if (citiesPerState.getNumberOfCities() > major) {
				major = citiesPerState.getNumberOfCities();
				majorStateCities = getStateAndNumberOfCities(citiesPerState.getState(), major);
			}
			
			if (citiesPerState.getNumberOfCities() < minor) {
				minor = citiesPerState.getNumberOfCities();
				minorStateCities = getStateAndNumberOfCities(citiesPerState.getState(), minor);
			}

			index++;
		}
		
		minorMajorCitiesList.add(majorStateCities);
		minorMajorCitiesList.add(minorStateCities);
		
		return minorMajorCitiesList;
	}

	private List<CitiesByState> getCityListPerState() {
		
		List<String> ufList = cityRepository.getUfOrdered();
		List<CitiesByState> citiesPerStateList = new ArrayList<>();
		
		String oldUf = ufList.get(0);
		int oldIndex = 0;
		int numberOfCitiesByState = 0;
		int indexTotalListaDeUfs = ufList.size()-1;
		
		/*Compara Index Anterior com Index Atual
		Se diferente adiciona a uf anterior e a contagem do numero de cidades*/
		for (int i = 0; i < ufList.size(); i++) {

			if (i > 0) {
				oldUf = ufList.get(oldIndex);
				oldIndex++;
			}
			
			if (!oldUf.equals(ufList.get(i))) {
				citiesPerStateList.add(getStateAndNumberOfCities(oldUf, numberOfCitiesByState));
				numberOfCitiesByState = 0;
			}
			
			numberOfCitiesByState++;

			if(i == indexTotalListaDeUfs) {
				citiesPerStateList.add(getStateAndNumberOfCities(ufList.get(i), numberOfCitiesByState));
			}
			
		}
		
		return citiesPerStateList;
	}

	private CitiesByState getStateAndNumberOfCities(String oldUf, int numberOfCities) {
		
		CitiesByState cititesPerState = new CitiesByState();
		cititesPerState.setState(oldUf);
		cititesPerState.setNumberOfCities(numberOfCities);
		
		return cititesPerState;
		
	}

	@GetMapping("/ibgeId/{ibgeId}")
	public ResponseEntity<City> searchCityByIbge(@PathVariable Long ibgeId){
		
		City city = cityRepository.searchCityByIbgeCode(ibgeId);
		
		if (city == null) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok(city);
		
	}
	
	@GetMapping("/state/{uf}")
	public List<City> searchCitiesByState(@PathVariable String uf){
		
		return cityRepository.searchCitiesByState(uf);
	}
	
	@PostMapping
	public City saveNewCity(@RequestBody @Valid City city) {
		
		return cityRepository.save(city);
	}
	
	@GetMapping("/citiesCsv")
	public String importCitiesCsv() {
		
		ImportCsvCities csvCities = new ImportCsvCities(); 
		
		List<City> citiesCsvList = csvCities.readCsvFile();
		
		for (City city : citiesCsvList) {
			cityRepository.save(city);
		}
		
		return "Cities imported with success!";
	}
	
	@DeleteMapping()
	public City deleteCity(@RequestBody City city) {
		
		cityRepository.delete(city);
		return city;
	}
}
