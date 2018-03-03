package com.cidades.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cidades.model.Cidade;

public class ImportaCsvCidades {

	public List<Cidade> leArquivoCsv() {

		String csvArquivo = "C:\\Users\\Gustavo\\Desktop\\Trabalho Java - Cidades.csv";

		BufferedReader conteudoCsv = null;

		String linha = "";

		List<Cidade> listaDeCidades = new ArrayList<>();

		try {

			conteudoCsv = new BufferedReader(new FileReader(csvArquivo));

			int contadorDeLinhas = 0;
			while ((linha = conteudoCsv.readLine()) != null) {

				if (contadorDeLinhas >= 1) {

					String[] arrCidade = linha.split(",");

					Cidade cidade = new Cidade();
					cidade.setIbgeId(Long.parseLong(arrCidade[0]));
					cidade.setUf(arrCidade[1]);
					cidade.setName(arrCidade[2]);
					cidade.setCapital(arrCidade[3].equals("") ? false : true);
					cidade.setLongitude(arrCidade[4]);
					cidade.setLatitude(arrCidade[5]);
					cidade.setNoAccents(arrCidade[6]);
					cidade.setAlternativeNames(arrCidade[7].equals("") ? "" : arrCidade[7]);
					cidade.setMicroregion(arrCidade[8]);
					cidade.setMesoregion(arrCidade[9]);

					listaDeCidades.add(cidade);

				}

				contadorDeLinhas++;
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (conteudoCsv != null) {
				try {
					conteudoCsv.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

		return listaDeCidades;
	}

	/*public void importaCidadesCsv() {

		List<Cidade> listaCidadesCsv = leArquivoCsv();

		try {
			
			FileWriter arq = new FileWriter("C:\\Users\\Gustavo\\Desktop\\Teste Cidades\\cidades-api\\src\\main\\resources\\import.sql");
			PrintWriter escreveArq = new PrintWriter(arq);

			String sql = "insert into cidade (IBGE_ID, uf, capital) values";

			for (Cidade cidade : listaCidadesCsv) {
				escreveArq.println(sql + "(" + cidade.getIbgeId() + ", '" + cidade.getUf() + "', " + cidade.isCapital() + ");");
			}

			arq.close();

		} catch (IOException e) { 
			e.printStackTrace();
		}

	}
*/
}
