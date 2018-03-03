package com.cidades.resource;

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

import com.cidades.helper.ImportaCsvCidades;
import com.cidades.model.Cidade;
import com.cidades.model.CidadesPorEstado;
import com.cidades.repository.CidadeRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping
	public List<Cidade> listarCidades(){
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/capitais")
	public List<Cidade> listarApenasCapitais() {

		return cidadeRepository.retornarCapitais();
	}
	
	@GetMapping("/cidadesPorEstado")
	public List<CidadesPorEstado> listarQtdCidadesPorEstado(){
		
		return getListaDeCidadesPorEstado();
		
	}

	@GetMapping("/menorMaiorQtdDeCidades")
	public List<CidadesPorEstado> listarMenorMaiorQtdDeCidades(){
		
		return getMenorMaiorQtdDeCidades();
	}
	
	private List<CidadesPorEstado> getMenorMaiorQtdDeCidades() {
		
		List<CidadesPorEstado> listaDeCidadesPorEstado = getListaDeCidadesPorEstado();
		List<CidadesPorEstado> listaDaMenorMaiorQtdDeCidades = new ArrayList<>();
		
		int maior = 0;
		int menor = 0;
		CidadesPorEstado estadoComMaiorQtdDeCidades = new CidadesPorEstado();
		CidadesPorEstado estadoComMenorQtdDeCidades = new CidadesPorEstado();
		
		int index = 0;
		for (CidadesPorEstado cidadesPorEstado : listaDeCidadesPorEstado) {
			
			if (index == 0) {
				maior = cidadesPorEstado.getNumeroDeCidades();
				menor = cidadesPorEstado.getNumeroDeCidades();
			}

			if (cidadesPorEstado.getNumeroDeCidades() > maior) {
				maior = cidadesPorEstado.getNumeroDeCidades();
				estadoComMaiorQtdDeCidades = getCidadesPorEstado(cidadesPorEstado.getEstado(), maior);
			}
			
			if (cidadesPorEstado.getNumeroDeCidades() < menor) {
				menor = cidadesPorEstado.getNumeroDeCidades();
				estadoComMenorQtdDeCidades = getCidadesPorEstado(cidadesPorEstado.getEstado(), menor);
			}

			index++;
		}
		
		listaDaMenorMaiorQtdDeCidades.add(estadoComMaiorQtdDeCidades);
		listaDaMenorMaiorQtdDeCidades.add(estadoComMenorQtdDeCidades);
		
		return listaDaMenorMaiorQtdDeCidades;
	}

	private List<CidadesPorEstado> getListaDeCidadesPorEstado() {
		
		List<String> listaDeUfs = cidadeRepository.listarUfOrdenados();
		List<CidadesPorEstado> listaDeCidadesPorEstado = new ArrayList<>();
		
		String ufAnterior = listaDeUfs.get(0);
		int indexAnterior = 0;
		int numeroDeCidadesPorEstado = 0;
		int indexTotalListaDeUfs = listaDeUfs.size()-1;
		
		/*Compara Index Anterior com Index Atual
		Se diferente adiciona a uf anterior e a contagem do numero de cidades*/
		for (int i = 0; i < listaDeUfs.size(); i++) {

			if (i > 0) {
				ufAnterior = listaDeUfs.get(indexAnterior);
				indexAnterior++;
			}
			
			if (!ufAnterior.equals(listaDeUfs.get(i))) {
				listaDeCidadesPorEstado.add(getCidadesPorEstado(ufAnterior, numeroDeCidadesPorEstado));
				numeroDeCidadesPorEstado = 0;
			}
			
			numeroDeCidadesPorEstado++;

			if(i == indexTotalListaDeUfs) {
				listaDeCidadesPorEstado.add(getCidadesPorEstado(listaDeUfs.get(i), numeroDeCidadesPorEstado));
			}
			
		}
		
		return listaDeCidadesPorEstado;
	}

	private CidadesPorEstado getCidadesPorEstado(String ufAnterior, int numeroDeCidades) {
		
		CidadesPorEstado cidadesPorEstado = new CidadesPorEstado();
		cidadesPorEstado.setEstado(ufAnterior);
		cidadesPorEstado.setNumeroDeCidades(numeroDeCidades);
		
		return cidadesPorEstado;
		
	}

	@GetMapping("/ibgeId/{ibgeId}")
	public ResponseEntity<Cidade> buscarCidadePorCodIbge(@PathVariable Long ibgeId){
		
		Cidade cidade = cidadeRepository.buscarCidadePorCodigoIbge(ibgeId);
		
		if (cidade == null) {
			return ResponseEntity.notFound().build();
		}
	
		return ResponseEntity.ok(cidade);
		
	}
	
	@GetMapping("/estado/{uf}")
	public List<Cidade> buscarCidadesPorEstado(@PathVariable String uf){
		
		return cidadeRepository.buscarCidadesPorEstado(uf);
	}
	
	@PostMapping
	public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
		
		return cidadeRepository.save(cidade);
	}
	
	@GetMapping("/importaCidadesCsv")
	public String importaCidadesCsv() {
		
		ImportaCsvCidades csvCidades = new ImportaCsvCidades(); 
		
		List<Cidade> listCidadesCsv = csvCidades.leArquivoCsv();
		
		for (Cidade cidade : listCidadesCsv) {
			cidadeRepository.save(cidade);
		}
		
		return "Cidades Importadas Com Sucesso!";
	}
	
	@DeleteMapping()
	public Cidade deletar(@RequestBody Cidade cidade) {
		
		cidadeRepository.delete(cidade);
		return cidade;
	}
}
