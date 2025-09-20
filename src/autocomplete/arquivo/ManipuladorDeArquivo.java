package autocomplete.arquivo;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Responsável por manipular o arquivo texto.
 * Permite operações de escrita, leitura e remoção de palavras
 */
public class ManipuladorDeArquivo {
  private final String caminhoArquivo = "palavras.txt";

  /**
   * Chama método para criar arquivo
   */
  public ManipuladorDeArquivo() {
    criarArquivo();
  }

  /**
   * Escreve uma palavra no arquivo, separando-a com vírgula
   *
   * @param palavra - String que deve ser adicionada ao final do arquivo
   */
  public void escreverNoArquivo(String palavra) {
    if(palavra == null || palavra.trim().isEmpty()) return;

    try {
      // Abre o arquivo no modo de adição (append)
      FileWriter writer = new FileWriter(caminhoArquivo, true);

      // Escreve a palavra no arquivo, usando vírgula como delimitador
      writer.write(palavra + ",");
      System.out.println("PALAVRA INSERIDA COM SUCESSO.\n");

      writer.close();

    } catch (IOException e) {
      System.out.println("ERRO DE LEITURA/ESCRITA DE ARQUIVO: " + e.getMessage());
    }
  }

  /**
   * Lê todas as palavras armazenadas no arquivo
   *
   * @return lista de palavras recuperadas do arquivo
   */
  public List<String> lerArquivo() {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo));
      List<String> palavrasList = new ArrayList<>();

      String palavras = "";
      String linha;

      try {
        // Lê todas as linhas do arquivo e concatena em uma String
        while ((linha = reader.readLine()) != null) palavras += linha;

        // Separa as palavras usando vírgula como delimitador e adiciona na lista
        if (!palavras.isEmpty()) palavrasList.addAll(Arrays.asList(palavras.split(",")));

      } catch (IOException e) {
        System.out.println("ERRO DE LEITURA/ESCRITA DE ARQUIVO: " + e.getMessage());
      }

      return palavrasList;

    } catch (FileNotFoundException e) {
      System.out.println("O ARQUIVO 'palavras.txt' NÃO EXISTE.");
      throw new RuntimeException(e);
    }
  }

  /**
   * Remove uma palavra específica do arquivo
   *
   * @param palavra - String que deve ser removida do arquivo
   */
  public void removerPalavra(String palavra) {
    try {
      // Lê todas as palavras do arquivo
      List<String> palavrasList = lerArquivo();

      if (palavrasList.remove(palavra)) {
        System.out.println("PALAVRA REMOVIDA COM SUCESSO.\n");

        // Abre o arquivo no modo sobrescrita
        FileWriter writer = new FileWriter(caminhoArquivo, false);

        // Sobrescreve o arquivo com todas as palavras, exceto a removida
        for (String p : palavrasList) {
          writer.write(p.trim() + ",");
        }

        writer.close();
      }

    } catch (IOException e) {
      System.out.println("ERRO DE LEITURA/ESCRITA DE ARQUIVO: " + e.getMessage());
    }
  }

  /**
   * Cria o arquivo txt (dicionário), caso ainda não exista
   */
  private void criarArquivo() {
    try {
      File arquivo = new File(caminhoArquivo);
      if (!arquivo.exists()) arquivo.createNewFile();
    } catch (IOException e) {
      System.out.println("ERRO DE LEITURA/ESCRITA DE ARQUIVO: + e.getMessage()");
      throw new RuntimeException(e);
    }
  }
}
