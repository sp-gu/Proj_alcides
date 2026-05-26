package proj;

//Classe ABB<T>: encarregada de manipular a estrutura de dados
//árvore de busca binária (ABB) genérica.
//
//Autor1: Ivan Carlos Alcântara de Oliveira.
//Data da Criação: 04/14/2026. 15h.
public class ABB<T extends Comparable<T>> {   

    private Node<T> raiz; // Nó raiz da ABB

    // Construtor da ABB
    public ABB() {
        raiz = null;
    }

    // Verifica se a ABB está vazia
    public boolean isEmpty() {
        return (raiz == null);
    }

    // Configura a raiz da árvore
    public void setRaiz(Node<T> araiz) {
        raiz = araiz;
    }

    // Obtém o nó raiz da ABB
    public Node<T> getRaiz() {
        return raiz;
    }

    // Procura o elemento e na ABB
    public Node<T> search(T e){
    	return search( raiz, e );
    }
    // Método que procura o elemento e na ABB de raiz
    public Node<T> search( Node<T> node, T e ){
        if (node == null) // elemento não foi encontrado
            return null;
        else if (compara(e, node.getValue()) == 0)
            return node;
        else  if (compara(e, node.getValue() ) < 0) 
            return search( node.getFilhoEsquerdo(), e );
        else return search( node.getFilhoDireito(), e );
    }    
    
    // Método público que insere "valor" na ABB.
    // Ou seja, responsável por chamar o método que
    // insere um novo nó (contendo "valor") na ABB de "raiz"
    public T inserir(T valor) {
        try {
            Node<T> novo = new Node<>(valor);
            raiz = inserir(novo, raiz);
            return valor;
        } catch (Exception e) {
            return null;
        }
    }

    // Método que realiza a inserção de um novo nó (novo) 
    // na ABB 
    private Node<T> inserir(Node<T> novo, Node<T> atual) {
        if (atual == null) {
            return novo;
        }

        if (compara(novo.getValue(), atual.getValue()) < 0) {
            atual.setFilhoEsquerdo(inserir(novo, atual.getFilhoEsquerdo()));
        } else {
            atual.setFilhoDireito(inserir(novo, atual.getFilhoDireito()));
        }

        return atual;
    }

    // Encarregado de chamar o método que percorre a ABB em
    // emOrdem a partir do raiz
    public String emOrdem() {
        return emOrdem(raiz);
    }

    // Encarregado de chamar o método que percorre a ABB em
    // emOrdem2 a partir do raiz
    public void emOrdem2() {
        emOrdem2(raiz);
    }

    // Encarregado de chamar o método que percorre a ABB em
    // preOrdem a partir do raiz
    public void preOrdem() {
        preOrdem(raiz);
    }

    // Encarregado de chamar o método que percorre a ABB em
    // posOrdem a partir do raiz
    public void posOrdem() {
        posOrdem(raiz);
    }

    // Método que percorre a ABB em Ordem
    // retornando uma String com os valores
    // concatenados do nó
    public String emOrdem(Node<T> no) {
        if (no == null) return "";
        
        StringBuilder sb = new StringBuilder();
        sb.append(emOrdem(no.getFilhoEsquerdo()));
        sb.append(no.getValue() + " ");
        sb.append(emOrdem(no.getFilhoDireito()));
        
        return sb.toString();
    }

    // Método que percorre a ABB em Ordem
    // e imprime os valores dos nós
    public void emOrdem2(Node<T> no) {
        if (no != null) {
            emOrdem2(no.getFilhoEsquerdo());
            System.out.print(no.getValue() + "\n");
            emOrdem2(no.getFilhoDireito());
        }
    }

    // Método que percorre a ABB em preOrdem
    // e imprime os valores dos nós
    public void preOrdem(Node<T> no) {
        if (no != null) {
            System.out.print(no.getValue() + "   ");
            preOrdem(no.getFilhoEsquerdo());
            preOrdem(no.getFilhoDireito());
        }
    }

    // Método que percorre a ABB em posOrdem
    // e imprime os valores dos nós
    public void posOrdem(Node<T> no) {
        if (no != null) {
            posOrdem(no.getFilhoEsquerdo());
            posOrdem(no.getFilhoDireito());
            System.out.print(no.getValue() + "   ");
        }
    }

    // Método que percorre a ABB em Nível
    // e imprime os valores dos nós
    public void emNivel() {
        //Método iterativo que utiliza uma fila auxiliar
        Node<T> noAux;
        // Observe que a LinkedList está funcionando como uma Fila
        LinkedList<Node<T>> fila = new LinkedList<Node<T>>();
        fila.addLast(raiz);  // Adiciona no Final
        while (!fila.isEmpty()) {
            noAux = (Node<T>) fila.pollFirst();  // Remove do Começo
            if (noAux.getFilhoEsquerdo() != null) {
                fila.addLast(noAux.getFilhoEsquerdo());
            }
            if (noAux.getFilhoDireito() != null) {
                fila.addLast(noAux.getFilhoDireito());
            }
            System.out.print(noAux.getValue() + "   ");
        }
    }

    // Método que compara dois objetos do tipo T genérico
    //incremento p/ proj N2: contador de comparações
    private int compara(T ob1, T ob2) {
        this.contadorComparacoes++; // incrementa a cada comparação realizada
        return ob1.compareTo(ob2);
    }
    
    //Determina o menor elemento a partir de um nó
    public Node<T> getMenor(Node<T> node) {
        if (isEmpty()) {
            return null;
        }
        if (node.getFilhoEsquerdo() == null) {
            return node;
        } else {
            return getMenor(node.getFilhoEsquerdo());
        }
    }

    //Determina o maior elemento a partir de um nó
    public Node<T> getMaior(Node<T> node) {
        if (isEmpty()) {
            return null;
        }
        if (node.getFilhoDireito() == null) {
            return node;
        } else {
            return getMaior(node.getFilhoDireito());
        }
    }

    // Obtém o maior elemento a partir de um nó
    public Node<T> getMax(Node<T> raiz, Node<T> paiRaiz) {
        if (isEmpty()) {
            return null;
        }
        Node<T> aux;
        //Se não tiver mais filho direito
        if (raiz.getFilhoDireito() == null) {
            aux = raiz;
            //Se tiver um pai, ele assume o filho esquerdo
            if (paiRaiz != null) {
                if (paiRaiz.getFilhoEsquerdo() == raiz) // se é filho esquerdo
                {
                    paiRaiz.setFilhoEsquerdo(raiz.getFilhoEsquerdo());
                } else {
                    paiRaiz.setFilhoDireito(raiz.getFilhoEsquerdo());
                }
            }
            return aux;
        } else {
            return getMax(raiz.getFilhoDireito(), raiz);
        }
    }
    
    // Método encarregado de chamar outro método
    // que elimina o objeto e da ABB a partir da raiz
    public boolean eliminar(T e) {
        return eliminar(raiz, null, e);
    }

    //Remove um elemento da árvore e retorna true ou false
    private boolean eliminar(Node<T> node, Node<T> paiRaiz, T e) {
        Node<T> aux;
        if (node == null) {  // não achou o elemento, não existe (chegou na folha)
            return false;
        } else { // a árvore ou sub-árvore não está vazia
            if (compara(e, node.getValue()) == 0) {  // o nó a eliminar está na raiz
                aux = node;
                //Se o nó não possui filhos, basta sumir com o nó
                if (node.getFilhoEsquerdo() == null && node.getFilhoDireito() == null) {
                    //Se não tiver pai, é a raiz da árvore
                    if (paiRaiz == null) {
                        setRaiz(null);
                    } //Senão, o pai deve "deserdar" o filho
                    else {
                        //Verifica se o nó que será eliminado é o filho esquerdo ou direito  do pai:
                        if (paiRaiz.getFilhoEsquerdo() != null && compara(paiRaiz.getFilhoEsquerdo().getValue(), e) == 0) {
                            paiRaiz.setFilhoEsquerdo(null);
                        } else if (paiRaiz.getFilhoDireito() != null && compara(paiRaiz.getFilhoDireito().getValue(), e) == 0) {
                            paiRaiz.setFilhoDireito(null);
                        }
                    }
                } else if (node.getFilhoDireito() == null) {   // se só tiver o filho esquerdo
                    //Se tiver um pai, ele assume o filho esquerdo
                    if (paiRaiz != null) {
                        //Verifica se a raiz é filho esquerdo ou direito para assumir o neto
                        if (paiRaiz.getFilhoEsquerdo() != null && compara(paiRaiz.getFilhoEsquerdo().getValue(), e) == 0) {
                            paiRaiz.setFilhoEsquerdo(node.getFilhoEsquerdo());
                        } else {
                            paiRaiz.setFilhoDireito(node.getFilhoEsquerdo());
                        }
                    } //Se não tiver pai (caso da raiz), adotar seu filho esquerdo
                    else {
                        node.setValue(node.getFilhoEsquerdo().getValue());
                        node.setFilhoEsquerdo(node.getFilhoEsquerdo().getFilhoEsquerdo());
                        node.setFilhoDireito(node.getFilhoEsquerdo().getFilhoDireito());
                    }
                } else if (node.getFilhoEsquerdo() == null) {   // se só tiver o filho direito
                    //Se tiver um pai, ele assume o filho esquerdo
                    if (paiRaiz != null) {
                        //Verifica se a raiz é filho esquerdo ou direito para assumir o neto
                        if (paiRaiz.getFilhoEsquerdo() != null && compara(paiRaiz.getFilhoEsquerdo().getValue(), e) == 0) {
                            paiRaiz.setFilhoEsquerdo(node.getFilhoDireito());
                        } else {
                            paiRaiz.setFilhoDireito(node.getFilhoDireito());
                        }
                    } //Se não tiver pai (caso da raiz), adotar seu filho esquerdo
                    else {
                        node.setValue(node.getFilhoDireito().getValue());
                        node.setFilhoEsquerdo(node.getFilhoDireito().getFilhoEsquerdo());
                        node.setFilhoDireito(node.getFilhoDireito().getFilhoDireito());
                    }
                } else {   //Raiz possui os 2 filhos
                    aux = getMax(node.getFilhoEsquerdo(), node);
                    node.setValue(aux.getValue());
                }
                aux = null;
                return true;
            } else { //Se não achou o nó a eliminar na raiz, continue procurando recursivamente:
                //Se for menor que a raiz, continuar procurando à esquerda
                if (compara(e, node.getValue()) < 0) {
                    return eliminar(node.getFilhoEsquerdo(), node, e);
                } else { // ou à direita
                    return eliminar(node.getFilhoDireito(), node, e);
                }
            }
        }
    }
    
    // Algumas implementações de operações com ABBs em forma iterativa:
    
    // Método que procura um objeto (obj) dentro da árvore
    // Retornando o objeto (obj) se encontra ou null, caso contrário
    public Node<T> find(T obj) {
        Node<T> atual = raiz;

        while (atual != null) {
            int cmp = compara(obj, atual.getValue());

            if (cmp == 0) {
                return atual;
            } else if (cmp < 0) {
                atual = atual.getFilhoEsquerdo();
            } else {
                atual = atual.getFilhoDireito();
            }
        }

        return null;
    }

    // Implementação iterativa da Inserção
    public T insert(T valor) {
        try {
            Node<T> novoNodo = new Node<>(valor);

            if (isEmpty()) {
                raiz = novoNodo;
                return valor;
            }

            Node<T> atual = raiz;
            Node<T> pai = null;

            while (atual != null) {
                pai = atual;
                if (compara(valor, atual.getValue()) < 0) {
                    atual = atual.getFilhoEsquerdo();
                } else {
                    atual = atual.getFilhoDireito();
                }
            }

            if (compara(valor, pai.getValue()) < 0) {
                pai.setFilhoEsquerdo(novoNodo);
            } else {
                pai.setFilhoDireito(novoNodo);
            }

            return valor;
        } catch (Exception e) {
            return null;
        }
    }
    
    //atributo p/ projeto N2
    private int contadorComparacoes = 0;

    // método p/ resetar o contador antes de cada busca
    public void resetContador() {
        this.contadorComparacoes = 0;
    }

    // método p/ obter o total de comparações
    public int getContadorComparacoes() {
        return this.contadorComparacoes;
    }
}
