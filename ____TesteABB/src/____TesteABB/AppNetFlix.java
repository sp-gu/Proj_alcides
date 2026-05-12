package ____TesteABB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AppNetFlix {
    private static ABB<ProgramaNetFlix> arvore = new ABB<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = 0;
        do {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um numero válido.");
            }
        } while (opcao != 8);
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU: NETFLIX BST ---");
        System.out.println("1. Ler dados de arquivo");
        System.out.println("2. Análises de dados (5 opcoes)");
        System.out.println("3. Inserir Programa");
        System.out.println("4. Buscar Programa (ID)");
        System.out.println("5. Remover Programa (ID)");
        System.out.println("6. Exibir Altura da Árvore");
        System.out.println("7. Salvar dados em arquivo");
        System.out.println("8. Encerrar Aplicacao");
        System.out.print("Escolha uma opcao: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                carregarArquivo();
                break;
            case 2:
                System.out.println("Funcionalidade em desenvolvimento (Analises).");
                break;
            case 3:
                inserirNovoPrograma();
                break;
            case 4:
                buscarPrograma();
                break;
            case 5:
                removerPrograma();
                break;
            case 6:
                System.out.println("Altura da arvore: " + calcularAltura(arvore.getRaiz()));
                break;
            case 7:
                System.out.println("Funcionalidade em desenvolvimento (Salvar).");
                break;
            case 8:
                System.out.println("Encerrando...");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static boolean validarCampos(String[] campos) {
    // Se a linha for vazia ou tiver menos colunas que o mínimo necessário (ID e Título), descarta.
    if (campos == null || campos.length < 2) {
        return false; 
    }

    // apenas ID (0) e Título (1) como obrigatórios
    int[] indicesObrigatorios = {0, 1}; 

    for (int i : indicesObrigatorios) {
        // Verifica se o índice existe no array e se não está vazio
        if (i >= campos.length || campos[i] == null || campos[i].trim().isEmpty()) {
            return false;
        }
        }
        return true; // Se tem ID e Título, o resto nós tratamos como opcional
    }

    private static void carregarArquivo() {
        String nomeArquivo = "C:\\Users\\spgu\\OneDrive - amazon.com\\Apagar\\codes_mack\\EstrDados\\PrjN2\\titles.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            br.readLine(); // pula o cabeçalho
            int inseridos = 0, descartados = 0;

            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue; // Pula linhas totalmente vazias

                String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (validarCampos(campos)) {
                    // usa função auxiliar getSafe para evitar o erro de Index Out of Bounds
                    // caso a linha termine antes da coluna 14
                    ProgramaNetFlix p = new ProgramaNetFlix(
                        getSafe(campos, 0), getSafe(campos, 1), getSafe(campos, 2), getSafe(campos, 3),
                        parseIntegerSafe(getSafe(campos, 4)), getSafe(campos, 5), 
                        parseIntegerSafe(getSafe(campos, 6)), getSafe(campos, 7),
                        getSafe(campos, 8), parseDoubleSafe(getSafe(campos, 9)),
                        getSafe(campos, 10), parseDoubleSafe(getSafe(campos, 11)),
                        parseDoubleSafe(getSafe(campos, 12)), parseDoubleSafe(getSafe(campos, 13)),
                        parseDoubleSafe(getSafe(campos, 14))
                    );
                    arvore.inserir(p);
                    inseridos++;
                } else {
                    descartados++;
                }
            }
            System.out.println("Insercao concluida! Inseridos: " + inseridos + " | Descartados: " + descartados);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // 3. Função Auxiliar para ler colunas com segurança
    private static String getSafe(String[] campos, int indice) {
        if (indice >= campos.length) return ""; // Retorna vazio se a coluna não existir na linha
        return campos[indice];
    }

    //Converte para Double com segurança. Se o campo estiver vazio ou for texto inválido, retorna 0.0.
    private static double parseDoubleSafe(String s) {
        try { return s.isEmpty() ? 0.0 : Double.parseDouble(s); }
        catch (Exception e) { return 0.0; }
    }

    private static int parseIntegerSafe(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return 0;
            // Usamos Double.parseDouble antes do cast para int caso o CSV venha como "2020.0"
            return (int) Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

     //op 4: busca por ID, contabilizando tempo e comparações
    private static void buscarPrograma() {
        System.out.print("Digite o ID para busca (ex: tm84618): ");
        String idBusca = scanner.nextLine();
        
        // objeto temporário só com o ID p/ busca (usando compareTo)
        // é necessário criar um objeto (ao invés de buscar por ID) devido ao fato da árvore ser genérica. o método 'search' da classe ABB exige um parâmetro de busca igual ao armazenado, e o armazenamento é feito do tipo <T> na ABB (junto ao comparable que tb usa tipo <T>)
        ProgramaNetFlix template = new ProgramaNetFlix();
        template.setId(idBusca);

        long tempoInicial = System.nanoTime();
        Node<ProgramaNetFlix> resultado = arvore.search(template); // usa busca da BST

        arvore.resetContador();
        long tempoFinal = System.nanoTime();

        if (resultado != null) {
            System.out.println("Encontrado: " + resultado.getValue());
            System.out.println("---");
            System.out.println("Comparações realizadas: " + arvore.getContadorComparacoes());
            System.out.println("Tempo de execução: " + (tempoFinal - tempoInicial) + " nanosegundos");
        } else {
            System.out.println("Título não encontrado.");
        }
    }

    // método auxiliar para altura (op 6)
    private static int calcularAltura(Node<ProgramaNetFlix> atual) {
        if (atual == null) return -1;
        return 1 + Math.max(calcularAltura(atual.getFilhoEsquerdo()), calcularAltura(atual.getFilhoDireito()));
    }

    // PENDENTE: métodos para Inserção manual e Remoção c/  lógica similar chamando arvore.inserir e arvore.eliminar
    private static void inserirNovoPrograma() { /* implementar conforme op 3 do PDF */ }
    private static void removerPrograma() { /*implementar conforme op 5 do PDF */ }

    // PENDENTE: métodos de analise de dados da op 2 do PDF
    // criar outro while dentro do existente com as 5 opções de método de análise  
}