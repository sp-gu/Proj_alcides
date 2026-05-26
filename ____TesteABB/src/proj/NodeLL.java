package proj;

//Classe NodeLL: relativa a um nó (NodeLL) da estrutura de dados  
//Lista Ligada capaz de armazenar dados genéricos
//De qualquer tipo de dado não primitivo.
//Autor1: Ivan Carlos Alcântara de Oliveira.
//Data da Criação: 10/10/2024. 15h.
public class NodeLL <T>{
	private T dado; // dado (tipo genérico) a ser armazenado no NodeLL
	private NodeLL<T> prox; // ponteiro para o próximo NodeLL (nó) da lista ligada
	// NodeLL(): construtor vazio
	public NodeLL() {
		this(null, null);
	}
	// NodeLL(T dado, NodeLL<T> prox): construtor com parâmetros
	public NodeLL(T dado, NodeLL<T> prox) {
		this.dado = dado;
		this.prox = prox;
	}
// getProx(): obtém o ponteiro para o próximo NodeLL (nó) da lista ligada
	public NodeLL<T> getProx() { return prox; };
	// getDado(): obtém o dado armazenado no NodeLL (nó)
	public T getDado(){ return dado; };
	// setProx(NodeLL<T> prox): atribui o endereço do próximo NodeLL (nó) da lista ligada
	public void setProx(NodeLL<T> prox) { this.prox = prox; };
	// setDado(T dado): atribui o dado a ser armazenado no NodeLL
	public void setDado(T dado) { this.dado = dado;	};	
}