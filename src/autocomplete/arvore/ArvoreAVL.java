package autocomplete.arvore;

import autocomplete.arquivo.ManipuladorDeArquivo;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsável por manipular as palavras em uma árvore binária balanceada
 * Permite operações de inserção, remoção, busca por prefixo e pesquisa em ordem
 */
public class ArvoreAVL {
  private Node raiz;
  private final ManipuladorDeArquivo manipuladorDeArquivo;
  private boolean carregandoDados = false;

  /**
   * Ao criar o objeto, o construtor automaticamente carrega
   * as palavras armazenadas no arquivo para a árvore
   *
   * @param manipuladorDeArquivo - Responsável pela manipulação no arquivo
   */
  public ArvoreAVL(ManipuladorDeArquivo manipuladorDeArquivo) {
    this.raiz = null;
    this.manipuladorDeArquivo = manipuladorDeArquivo;
    this.carregarPalavrasSalvas();
  }

  /**
   * Insere uma nova palavra na árvore
   *
   * @param valor - Palavra a ser inserida
   */
  public void inserir(String valor) {
    raiz = inserirRecursivo(raiz, valor);
  }

  /**
   * Insere recursivamente, mantendo o balanceamento da árvore
   */
  private Node inserirRecursivo(Node node, String valor) {
    // Cria um novo nó quando a posição correta na árvore é encontrada
    if(node == null) {
      // Salva a palavra no arquivo caso não esteja carregando dados
      if(!carregandoDados) manipuladorDeArquivo.escreverNoArquivo(valor);
      return new Node(valor);
    }

    // Impede inserção de palavras duplicadas
    if(node.valor.equals(valor)) return node;

    if(valor.compareTo(node.valor) < 0) node.esquerda = inserirRecursivo(node.esquerda, valor);
    else node.direita = inserirRecursivo(node.direita, valor);

    node.altura = 1 + Math.max(getAltura(node.esquerda), getAltura(node.direita));

    int balanceamento = getBalanceamento(node);

    // Nó desbalanceado à direita
    if(balanceamento > 1) {
      if(valor.compareTo(node.direita.valor) > 0) {
        return rotacaoEsquerda(node);
      } else {
        node.direita = rotacaoDireita(node.direita);
        return rotacaoEsquerda(node);
      }
    }

    // Nó desbalanceado à esquerda
    if(balanceamento < -1) {
      if(valor.compareTo(node.esquerda.valor) < 0) {
        return rotacaoDireita(node);
      } else {
        node.esquerda = rotacaoEsquerda(node.esquerda);
        return rotacaoDireita(node);
      }
    }

    return node;
  }

  /**
   * Remove uma palavra da árvore
   *
   * @param valor - Palavra a ser removida
   */
  public void remover(String valor) {
    raiz = removerRecursivo(raiz, valor);

    // Remove a palavra do arquivo
    manipuladorDeArquivo.removerPalavra(valor);
  }

  /**
   * Remove recursivamente, mantendo o balanceamento da árvore
   */
  private Node removerRecursivo(Node node, String valor) {
    if(node == null) return node;

    if(valor.compareTo(node.valor) < 0) node.esquerda = removerRecursivo(node.esquerda, valor);
    else if(valor.compareTo(node.valor) > 0) node.direita = removerRecursivo(node.direita, valor);

    // Nó encontrado
    else {
      // Caso 1: nó sem filhos
      if(node.esquerda == null && node.direita == null) return null;
      // Caso 2: nó com 1 filho a esquerda
      else if(node.esquerda == null) return node.direita;
      // Caso 3: nó com 1 filho a direita
      else if(node.direita == null) return node.esquerda;
      // Caso 4: nó com 2 filhos
      else {
        Node temp = minimoValor(node.direita); // Menor valor da subárvore a direita

        node.valor = temp.valor; // Substitui o valor atual pelo valor encontrado
        node.direita = removerRecursivo(node.direita, temp.valor); // Remove o nó duplicado
      }
    }

    node.altura = 1 + Math.max(getAltura(node.esquerda), getAltura(node.direita));

    int balanceamento = getBalanceamento(node);

    // Desbalanceamento à direita
    if(balanceamento > 1) {
      if(getBalanceamento(node) >= 0) {
        return rotacaoEsquerda(node);
      } else {
        node.direita = rotacaoDireita(node.direita);
        return rotacaoEsquerda(node);
      }
    }

    // Desbalanceamento à esquerda
    if(balanceamento < -1) {
        if(getBalanceamento(node.esquerda) <= 0) {
        return rotacaoDireita(node);
      } else {
        node.esquerda = rotacaoEsquerda(node.esquerda);
        return rotacaoDireita(node);
      }
    }

    return node;
  }

  /**
   * Realiza rotação simples à direita
   */
  private Node rotacaoDireita(Node node) {
    Node newRoot = node.esquerda;
    Node newChild = newRoot.direita;

    newRoot.direita = node;
    node.esquerda = newChild;

    node.altura = 1 + Math.max(getAltura(node.esquerda), getAltura(node.direita));
    newRoot.altura = 1 + Math.max(getAltura(newRoot.esquerda), getAltura(newRoot.direita));

    return newRoot;
  }

  /**
   * Realiza rotação simples à esquerda
   */
  private Node rotacaoEsquerda(Node node) {
    Node newRoot = node.direita;
    Node newChild = newRoot.esquerda;

    newRoot.esquerda = node;
    node.direita = newChild;

    node.altura = 1 + Math.max(getAltura(node.esquerda), getAltura(node.direita));
    newRoot.altura = 1 + Math.max(getAltura(newRoot.esquerda), getAltura(newRoot.direita));

    return newRoot;
  }

  /**
   * Busca e exibe as palavras que começam com o prefixo
   *
   * @param prefixo - início das palavras a serem buscadas
   */
  public void buscarPalavrasComPrefixo(String prefixo) {
    List<String> palavras = new ArrayList<>();

    // Busca os nós com o prefixo
    buscaPorPrefixo(raiz, palavras, prefixo);

    System.out.print("PALAVRAS COM O PREFIXO '" + prefixo.toLowerCase() + "': ");
    System.out.println(palavras);
  }

  /**
   * Método recursivo que busca as palavras que começam com o prefixo
   */
  private void buscaPorPrefixo(Node raizLocal, List<String> palavras, String prefixo)  {
    if (raizLocal != null) {
      buscaPorPrefixo(raizLocal.esquerda, palavras, prefixo);

      // Adiciona à lista caso a palavra comece com o prefixo
      if(raizLocal.valor.toLowerCase().startsWith(prefixo.toLowerCase())) palavras.add(raizLocal.valor);

      buscaPorPrefixo(raizLocal.direita, palavras, prefixo);
    }
  }

  /**
   * Exibe todas as palavras da árvore em ordem lexicográfica
   */
  public void pesquisaEmOrdem() {
    List<String> palavras = new ArrayList<>();

    buscarEmOrdem(raiz, palavras);

    System.out.print("PALAVRAS EM ORDEM: ");
    System.out.println(palavras);
  }

  /**
   * Método recursivo que exibe as palavras da árvore em ordem
   */
  private void buscarEmOrdem(Node rootLocal, List<String> palavras) {
    if(rootLocal != null) {
      buscarEmOrdem(rootLocal.esquerda, palavras);

      palavras.add(rootLocal.valor);

      buscarEmOrdem(rootLocal.direita, palavras);
    }
  }

  /**
   * Retorna o fator de balanceamento do nó
   */
  private int getBalanceamento(Node node) {
    if(node == null) return 0;
    return getAltura(node.direita) - getAltura(node.esquerda);
  }

  /**
   * Retorna a altura do nó
   */
  public int getAltura(Node node) {
    if(node == null) return 0;
    return node.altura;
  }

  /**
   * Retorna o nó com o menor valor na subárvore
   */
  private Node minimoValor(Node node) {
    Node atual = node;
    Node temp = null;

    while(atual != null) {
      temp = atual;
      atual = atual.esquerda;
    }
    return temp;
  }

  /**
   * Carrega todas as palavras do arquivo e insere na árvore
   */
  private void carregarPalavrasSalvas() {
    // Desativa escrita no arquivo
    this.carregandoDados = true;

    List<String> palavras = manipuladorDeArquivo.lerArquivo();
    for(String p : palavras)
      inserir(p);

    // Reativa escrita no arquivo
    this.carregandoDados = false;
  }
}
