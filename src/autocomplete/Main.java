package autocomplete;

import autocomplete.arquivo.ManipuladorDeArquivo;
import autocomplete.arvore.ArvoreAVL;
import autocomplete.menu.Menu;

import java.util.Scanner;

/**
 * Inicializa o sistema de autocomplete
 *
 * Projeto: Sistema de autocomplete
 * Autor: Rayan Junio
 * Data: 26/05/2025
 */
public class Main {
  public static void main(String[] args) {

    // Instancia o manipulador de arquivo
    ManipuladorDeArquivo manipuladorDeArquivo = new ManipuladorDeArquivo();

    // Cria uma árvore AVL utilizando o manipulador de arquivo
    ArvoreAVL tree = new ArvoreAVL(manipuladorDeArquivo);

    // Cria um objeto do tipo Scanner para capturar dados de entrada
    Scanner scanner = new Scanner(System.in);

    // Cria o menu e inicia a execução do sistema
    Menu menu = new Menu(tree, scanner);
    menu.criarMenu();
  }
}
