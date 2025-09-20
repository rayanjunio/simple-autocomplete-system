package autocomplete.menu;

import autocomplete.arvore.ArvoreAVL;

import java.util.Scanner;

/**
 * Responsável por exibir um meny e gerenciar as interações com o usuário
 */
public class Menu {
  private final ArvoreAVL arvoreAVL;
  private final Scanner scanner;

  /**
   * Cria um objeto que utiliza as instâncias passadas como argumento
   *
   * @param arvoreAVL - Instância da árvore AVL que será utilizada no sistema
   * @param scanner - Instância responsável por capturar dados de entrada
   */
  public Menu(ArvoreAVL arvoreAVL, Scanner scanner) {
    this.arvoreAVL = arvoreAVL;
    this.scanner = scanner;
  }

  /**
   * Cria e executa o menu principal
   */
  public void criarMenu() {
    int opcao;
    do {
      // Exibe as opções disponíveis no menu
      opcoesMenu();

      System.out.println("DIGITE O NÚMERO DA OPÇÃO DESEJADA: ");

      // Verifica se a entrada é um número, caso não seja o switch-case redireciona para default
      if(scanner.hasNextInt()) opcao = scanner.nextInt();
      else opcao = -1;

      // Consome o enter do buffer
      scanner.nextLine();

      switch (opcao) {
        case 1:
          // Opção: Inserir palavra
          System.out.println("DIGITE A PALAVRA: \n");
          String palavraAInserir = scanner.nextLine().trim();

          if(validarPalavra(palavraAInserir)) {
            System.out.println("ENTRADA INVÁLIDA: INSIRA APENAS LETRAS.\n");
            break;
          }

          if(palavraAInserir.isEmpty()) System.out.println("DIGITE UMA PALAVRA NÃO VAZIA.\n");
          else this.arvoreAVL.inserir(palavraAInserir);

          break;
        case 2:
          // Opção: Remover palavra
          System.out.println("DIGITE A PALAVRA: \n");
          String palavraARemover = scanner.nextLine().trim();

          if(validarPalavra(palavraARemover)) {
            System.out.println("ENTRADA INVÁLIDA: INSIRA APENAS LETRAS.\n");
            break;
          }

          if(palavraARemover.isEmpty()) System.out.println("DIGITE UMA PALAVRA NÃO VAZIA.\n");
          else this.arvoreAVL.remover(palavraARemover);

          break;
        case 3:
          // Opção: Buscar palavras por prefixo
          System.out.println("DIGITE O PREFIXO: \n");
          String prefixo = scanner.nextLine().trim();

          if(validarPalavra(prefixo)) {
            System.out.println("ENTRADA INVÁLIDA: INSIRA APENAS LETRAS.\n");
            break;
          }

          if(prefixo.isEmpty()) System.out.println("DIGITE UM PREFIXO NÃO VAZIO\n");
          else this.arvoreAVL.buscarPalavrasComPrefixo(prefixo);

          break;
        case 4:
          // Opção: Exibir palavras em ordem lexicográfica
          this.arvoreAVL.pesquisaEmOrdem();
          break;
        case 0:
          // Opção: Encerrar o sistema
          System.out.println("ENCERRANDO O SISTEMA...\n");
          break;
        default:
          // Opção inválida
          System.out.println("OPÇÃO INVÁLIDA. TENTE NOVAMENTE OBSERVANDO AS OPÇÕES DO MENU.\n");
      }

    } while(opcao != 0);
    scanner.close();
  }

  /**
   * Exibe as opções do menu para o usuário
   */
  private void opcoesMenu() {
    System.out.println("--------------------------------");
    System.out.println("             MENU");
    System.out.println("--------------------------------");
    System.out.println("1 - INSERIR PALAVRA");
    System.out.println("2 - REMOVER PALAVRA");
    System.out.println("3 - BUSCAR PALAVRAS POR PREFIXO");
    System.out.println("4 - PESQUISAR PALAVRAS EM ORDEM");
    System.out.println("0 - SAIR\n");
  }

  /**
   *
   * @param palavra - String a ser validada
   * @return false caso seja válida, true caso contrário
   */
  private boolean validarPalavra(String palavra) {
    return palavra.matches("\\d+");
  }
}

