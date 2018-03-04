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

import com.cities.helper.CalculateDistanceInKm;
import com.cities.helper.ImportCsvCities;
import com.cities.model.CitiesByState;
import com.cities.model.City;
import com.cities.model.MajorDistance;
import com.cities.model.TotalData;
import com.cities.model.TotalDifferentData;
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
	
	//Task 1
	@GetMapping("/citiesCsv")
	public String importCitiesCsv() {
		
		ImportCsvCities csvCities = new ImportCsvCities(); 
		
		List<City> citiesCsvList = csvCities.readCsvFile();
		
		for (City city : citiesCsvList) {
			cityRepository.save(city);
		}
		
		return "Cities imported with success!";
	}
	
	//Task 2
	@GetMapping("/capitals")
	public List<City> getOnlyCapitals() {

		return cityRepository.getCapitalList();
	}

	//Task 3
	@GetMapping("/minorMajorCitiesNumber")
	public List<CitiesByState> getMinorMajorCitiesNumber(){
		
		return getMinorMajorCitiesNumberList();
	}
	
	//Task 4
	@GetMapping("/numberOfCitiesPerState")
	public List<CitiesByState> getNumberOfCitiesPerState(){
		
		return getCityListPerState();
		
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

	//Task 5
	@GetMapping("/ibgeId/{ibgeId}")
	public ResponseEntity<City> searchCityByIbge(@PathVariable Long ibgeId){
		
		City city = cityRepository.searchCityByIbgeCode(ibgeId);
		
		if (city == null) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok(city);
		
	}
	
	//Task 6
	@GetMapping("/state/{uf}")
	public List<City> searchCitiesByState(@PathVariable String uf){
		
		return cityRepository.searchCitiesByState(uf);
	}
	
	//Task 7
	@PostMapping
	public City saveNewCity(@RequestBody @Valid City city) {
		
		return cityRepository.save(city);
	}

	//Task 8
	@DeleteMapping("/delete/{ibgeId}")
	public ResponseEntity<Void> deleteCity(@PathVariable Long ibgeId) {
		
		City city = cityRepository.searchCityByIbgeCode(ibgeId);
		
		if (city == null) {
			return ResponseEntity.notFound().build();
		}
		
		cityRepository.delete(city);
		
		return ResponseEntity.noContent().build();
	}
	
	//Task 9
	@GetMapping("/capital/{capital}")
	public List<City> searchCitiesByCapital(@PathVariable boolean capital){

		return cityRepository.searchCitiesByCapital(capital);
		
	}
	
	//Task 9
	@GetMapping("/longitude/{longitude}")
	public List<City> searchCitiesByLongitude(@PathVariable double longitude){

		return cityRepository.searchCitiesByLongitude(longitude);
		
	}
	
	//Task 9
	@GetMapping("/latitude/{latitude}")
	public List<City> searchCitiesByLatitude(@PathVariable double latitude){

		return cityRepository.searchCitiesByLatitude(latitude);
		
	}
	
	//Task 9
	@GetMapping("/noAccents/{noAccents}")
	public List<City> searchCitiesByNoAccents(@PathVariable String noAccents){

		return cityRepository.searchCitiesByNoAccents(noAccents);
		
	}
	
	//Task 9
	@GetMapping("/alternativeNames/{alternativeNames}")
	public List<City> searchCitiesByAlternativeNames(@PathVariable String alternativeNames){

		return cityRepository.searchCitiesByAlternativeNames(alternativeNames);
		
	}
	
	//Task 9
	@GetMapping("/microregion/{microregion}")
	public List<City> searchCitiesByMicroregion(@PathVariable String microregion){

		return cityRepository.searchCitiesByMicroregion(microregion);
		
	}
	
	//Task 9
	@GetMapping("/mesoregion/{mesoregion}")
	public List<City> searchCitiesByMesoregion(@PathVariable String mesoregion){

		return cityRepository.searchCitiesByMesoregion(mesoregion);
		
	}
	
	//Task 10
	@GetMapping("/totalDifferentData")
	public TotalDifferentData getTotalDifferentData() {
		
		List<String> ufOrderedList = cityRepository.getUfOrdered();
		
		String oldUf = ufOrderedList.get(0);
		int oldIndex = 0;
		int dataCounter = 0;
		
		for (int i = 0; i < ufOrderedList.size(); i++) {
			
			if (i > 0) {
				oldUf = ufOrderedList.get(oldIndex);
				oldIndex++;
			}
			
			if (!oldUf.equals(ufOrderedList.get(i)) || (i == ufOrderedList.size()-1)) {
				dataCounter++;
			}
			
		}
		
		TotalDifferentData totalDifferentData = new TotalDifferentData();
		totalDifferentData.setTotalDifferentData(dataCounter);
		
		return totalDifferentData;
		
	}
	
	//Task 11
	@GetMapping("/totalData")
	public TotalData getTotalData() {
		
		List<String> ufOrdered = cityRepository.getUfOrdered();
		
		int dataCounter = 0;
		
		for (int i = 0; i <= ufOrdered.size(); i++) {
			dataCounter++;
		}
		
		TotalData totalData = new TotalData();
		totalData.setTotalData(dataCounter);
		
		return totalData;
		
	}
	
	//Task 12
	@GetMapping("/majorDistance")
	public MajorDistance getGreaterDistance() {
		
		return getMajorDistance();
		
	}

	private MajorDistance getMajorDistance() {
		
		List<City> cityList = cityRepository.findAll();
		
		int k = 0;
		int j = 1;

		int majorDistance = 0;
		
		do {
			/*Quando j atingir o tamanho do array, ele é zerado para começar a
			percorrer novamente até que k atinja o tamanho máximo do array*/
			if (j == cityList.size()) {
				
				j = 0;
				k++;
				
			}else {
				//Condição para k não comparar com ele mesmo
				if (k != j) {
					
					double firLongitude = cityList.get(k).getLongitude();
					double firLatitude = cityList.get(k).getLatitude();
					double secLongitude = cityList.get(j).getLongitude();
					double secLatitude = cityList.get(j).getLatitude();
					
					int resultDistance = CalculateDistanceInKm.calculateDistanceInKm(firLongitude, firLatitude, secLongitude, secLatitude);
					
					if (resultDistance > majorDistance) {
						majorDistance = resultDistance;
					}
				}
				
				j++;
			}
			
		}while(k < cityList.size());
			
			MajorDistance greaterDistance = new MajorDistance();
			greaterDistance.setMajorDistance(majorDistance);
			
			return greaterDistance;

		}

}
