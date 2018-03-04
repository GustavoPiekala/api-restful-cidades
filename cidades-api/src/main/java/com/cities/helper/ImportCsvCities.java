package com.cities.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cities.model.City;

public class ImportCsvCities {

	public List<City> readCsvFile() {

		String csvFile = "Trabalho Java - Cidades.csv";

		BufferedReader csvContent = null;

		String line = "";

		List<City> cityList = new ArrayList<>();

		try {

			csvContent = new BufferedReader(new FileReader(csvFile));

			int counterLine = 0;
			while ((line = csvContent.readLine()) != null) {

				if (counterLine >= 1) {

					String[] cityArray = line.split(",");

					City city = new City();
					city.setIbgeId(Long.parseLong(cityArray[0]));
					city.setUf(cityArray[1]);
					city.setName(cityArray[2]);
					city.setCapital(cityArray[3].equals("") ? false : true);
					city.setLongitude(Double.parseDouble(cityArray[4]));
					city.setLatitude(Double.parseDouble(cityArray[5]));
					city.setNoAccents(cityArray[6]);
					city.setAlternativeNames(cityArray[7].equals("") ? "" : cityArray[7]);
					city.setMicroregion(cityArray[8]);
					city.setMesoregion(cityArray[9]);

					cityList.add(city);

				}

				counterLine++;
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (csvContent != null) {
				try {
					csvContent.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		return cityList;
	}

}
