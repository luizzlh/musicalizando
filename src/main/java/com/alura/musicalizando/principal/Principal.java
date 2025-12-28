package com.alura.musicalizando.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.alura.musicalizando.models.Artista;
import com.alura.musicalizando.models.Musica;
import com.alura.musicalizando.models.TipoArtista;
import com.alura.musicalizando.repositories.ArtistaRepository;
import com.alura.musicalizando.service.ConsultaChatGPT;

public class Principal {

   private Scanner leitura = new Scanner(System.in);
   private final ArtistaRepository repositorio;
   // private Optional<Artista> artistaBuscado;

   public Principal(ArtistaRepository repositorio) {
      this.repositorio = repositorio;
   }

   public void exibeMenu() {
      var opcao = -1;
      while (opcao != 0) {
         var menu = """
               1 - Cadastrar artistas
               2 - Cadastrar músicas
               3 - Listar músicas
               4 - Buscar músicas por artistas
               5 - Pesquisar dados sobre um artista
               9 - Sair
               """;

         System.out.println(menu);
         opcao = leitura.nextInt();
         leitura.nextLine();

         switch (opcao) {
            case 1:
               cadastrarArtista();
               break;
            case 2:
               cadastrarMusica();
               break;
            case 3:
               listarMusicas();
               break;
            case 4:
               buscarMusicasPorArtista();
               break;
            case 5:
               pesquisarDadosDoArtista();
               break;
            case 9:
               break;
            default:
               System.out.println("Opção inválida!");
               break;
         }
      }
   }

   private void cadastrarArtista() {
      var cadastrarNovo = "S";

      while (cadastrarNovo.equalsIgnoreCase("S")) {

         System.out.print("Digite o nome do artista a ser cadastrado: ");
         var nomeArtista = leitura.nextLine();
         System.out.print("Digite o tipo do artista:(Solo, Dupla ou Banda)");
         var tipo = leitura.nextLine();
         TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
         Artista artista = new Artista(nomeArtista, tipoArtista);
         repositorio.save(artista);
         System.out.println("Cadastrar novo artista? (S/N)");
      }
   }

   private void cadastrarMusica() {
      System.out.println("Cadastrar música de qual artista? ");
      var nome = leitura.nextLine();
      Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
      if (artista.isPresent()) {
         System.out.println("Informe o titulo da música: ");
         var nomeMusica = leitura.nextLine();
         Musica musica = new Musica(nomeMusica);
         musica.setArtista(artista.get());
         artista.get().getMusicas().add(musica);
         repositorio.save(artista.get());
      } else {
         System.out.println("Artista não existe!");
      }
   }

   private void listarMusicas() {
      List<Artista> artistas = repositorio.findAll();
      artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
   }

   private void buscarMusicasPorArtista(){
      System.out.println("Buscar músicas de que artista?");
      var nome = leitura.nextLine();
      List<Musica> musicas = repositorio.buscaMusicasPeloArtista(nome);
      musicas.forEach(System.out::println);
   }

   private void pesquisarDadosDoArtista(){
      System.out.println("Pesquisar dados sobre qual artista?");
      var nome = leitura.nextLine();
      var resposta = ConsultaChatGPT.obterInformacao(nome);
      System.out.println(resposta.trim());
   }
}