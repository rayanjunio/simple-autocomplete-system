package autocomplete.arvore;

/**
 * Representa um nó da árvore AVL
 */
public class Node {
  public Node esquerda;
  public Node direita;
  public String valor;
  public int altura;

  /**
   * Construtor que cria um nó com o valor passado como argumento
   * @param valor - String que deve ser o conteúdo do nó
   */
  public Node(String valor) {
    this.valor = valor;
    this.esquerda = null;
    this.direita = null;
    this.altura = 1;
  }
}
