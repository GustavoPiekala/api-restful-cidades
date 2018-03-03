package com.cidades.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cidades.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

	@Query("select c from Cidade c where c.capital = true order by c.name")
	public List<Cidade> retornarCapitais();
	
	@Query("select c.uf from Cidade c order by c.uf")
	public List<String> listarUfOrdenados();
	
	@Query("select c from Cidade c where c.ibgeId = ?1")
	public Cidade buscarCidadePorCodigoIbge(Long ibgeId);

	@Query("select c from Cidade c where UPPER(c.uf) = UPPER(?1)")
	public List<Cidade> buscarCidadesPorEstado(String uf);
	
}
