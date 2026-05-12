package ____TesteABB;

//Classe Node: relativa a um nó (Node) da estrutura de dados  
//Árvore de Busca Binária (ABB) capaz de armazenar dados genéricos
//De qualquer tipo de dado não primitivo.
//Autor1: Ivan Carlos Alcântara de Oliveira.
//Data da Criação: 14/04/2026. 15h.
public class Node <T extends Comparable<T>> {  // Nodo, nó ou elemento da árvore

    private T value;  // Dado a ser inserido no nó
    private Node<T> filhoEsquerdo; // Nó filho esquerdo
    private Node<T> filhoDireito; // Nó filho direito
    
    // Converte e retorna o dado do nó (valor) para String
    public String toString () {
        return value.toString();
    }
        
    // Construtor
    public Node(T valor) {
        this.value = valor;
    }

    // Pega o dado (valor) do nó
    public T getValue() {
        return value;
    }

    // Alera o conteúdo (valor) do nó
    public void setValue(T value) {
        this.value = value;
    }

    // Obtém o nó esquerdo
    public Node<T> getFilhoEsquerdo() {
        return filhoEsquerdo;
    }

    // Altera o nó esquerdo
    public void setFilhoEsquerdo(Node<T> filhoEsquerdo) {
        this.filhoEsquerdo = filhoEsquerdo;
    }

    // Obtém o nó direito
    public Node<T> getFilhoDireito() {
        return filhoDireito;
    }

    // Altera o nó direito
    public void setFilhoDireito(Node<T> filhoDireito) {
        this.filhoDireito = filhoDireito;
    }
    
}