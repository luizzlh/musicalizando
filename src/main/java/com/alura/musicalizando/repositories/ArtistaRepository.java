package com.alura.musicalizando.repositories;

import java.util.List;
import java.util.Optional;

import com.alura.musicalizando.models.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alura.musicalizando.models.Artista;
import org.springframework.data.jpa.repository.Query;

public interface ArtistaRepository extends JpaRepository<Artista, Long>{

   Optional<Artista> findByNomeContainingIgnoreCase(String nome);

   @Query("SELECT M FROM Artista A JOIN A.musicas M WHERE A.nome ILIKE %:nome%")
   List<Musica> buscaMusicasPeloArtista(String nome);

}
