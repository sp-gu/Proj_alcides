package proj;

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
                if (arvore.isEmpty()) {
                    System.out.println("A arvore esta vazia. Carregue os dados primeiro (Opcao 1).");
                    break;
                }
                System.out.println("\n--- ANALISES DE DADOS ---");
                System.out.println("1. Ranking de Qualidade por Genero");
                System.out.println("2. Analise de Maturidade e Duracao");
                System.out.println("3. Tendencias de Producao por Decada");
                System.out.println("4. Eficiencia de Producao Internacional");
                System.out.println("5. Divergencia Critica (IMDB vs TMDB)");
                System.out.print("Escolha a analise: ");
                try {
                    int opAnalise = Integer.parseInt(scanner.nextLine());
                    switch (opAnalise) {
                        case 1:
                            // rankingPorGenero(); 
                            System.out.println("Funcionalidade em desenvolvimento.");
                            break;
                        case 2:
                            // maturidadeDuracao(); 
                            System.out.println("Funcionalidade em desenvolvimento.");
                            break;
                        case 3:
                            tendenciasPorDecada();
                            break;
                        case 4:
                            // eficienciaInternacional();
                            System.out.println("Funcionalidade em desenvolvimento.");
                            break;
                        case 5:
                            // divergenciaCritica();
                            System.out.println("Funcionalidade em desenvolvimento.");
                            break;
                        default:
                            System.out.println("Opcao invalida.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Opcao invalida.");
                }
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

    private static void carregarArquivo() {
        String nomeArquivo = "C:\\Users\\spgu\\OneDrive - amazon.com\\Apagar\\codes_mack\\EstrDados\\PrjN2\\titles.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            br.readLine(); // pula o cabeçalho
            int inseridos = 0, descartados = 0;

            while ((linha = br.readLine()) != null) {
                // Regex para dividir CSV considerando aspas
                String[] campos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (validarCampos(campos)) { //se validarCampos retornar true, os dados são inseridos
                    ProgramaNetFlix p = new ProgramaNetFlix(
                        campos[0], campos[1], campos[2], campos[3],
                        Integer.parseInt(campos[4]), campos[5], 
                        Integer.parseInt(campos[6]), campos[7],
                        campos[8], parseDoubleSafe(campos[9]),
                        campos[10], parseDoubleSafe(campos[11]),
                        parseDoubleSafe(campos[12]), parseDoubleSafe(campos[13]),
                        parseDoubleSafe(campos[14])
                    );
                    arvore.inserir(p); // Chave de inserção é o ID
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

    // método de validação da inserção
    private static boolean validarCampos(String[] campos) {
        if (campos.length < 15) return false;
        for (String campo : campos) {
            if (campo == null || campo.trim().isEmpty()) return false;
        }
        return true;
    }

    private static double parseDoubleSafe(String s) {
        try { return s.isEmpty() ? 0.0 : Double.parseDouble(s); }
        catch (Exception e) { return 0.0; }
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


    private static void tendenciasPorDecada() {

        System.out.println("\n--- Tendencias de Producao por Decada ---");

        // Faixa fechada: 1940-2030 (10 décadas).
        final int ANO_BASE = 1940;
        final int NUM_DECADAS = 10;
        double[] somaPop = new double[NUM_DECADAS];
        int[] contagem = new int[NUM_DECADAS];

        // percurso em pós-ordem
        posOrdemDecadas(arvore.getRaiz(), somaPop, contagem, ANO_BASE);

        // Imprime resultado
        boolean encontrou = false;
        System.out.printf("%-10s | %-22s | %s\n", "Decada", "Popularidade Media", "Qtd Titulos");
        System.out.println("-------------------------------------------------------");
        for (int i = 0; i < NUM_DECADAS; i++) {
            if (contagem[i] > 0) {
                int anoInicio = ANO_BASE + i * 10;
                double media = somaPop[i] / contagem[i];
                System.out.printf("%d-%d | %-22.2f | %d\n",
                        anoInicio, anoInicio + 9, media, contagem[i]);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma decada com dados validos foi encontrada.");
        }
        System.out.println("-------------------------------------------------------");
    }

    // Percurso em pós-ordem
    private static void posOrdemDecadas(Node<ProgramaNetFlix> no, double[] somaPop,
                                        int[] contagem, int anoBase) {
        if (no == null) return;

        posOrdemDecadas(no.getFilhoEsquerdo(), somaPop, contagem, anoBase);
        posOrdemDecadas(no.getFilhoDireito(), somaPop, contagem, anoBase);

        // Processamento do nó
        ProgramaNetFlix p = no.getValue();
        int ano = p.getRelease_year();
        double pop = p.getTmdb_popularity();

        // Descarta entradas com ano ou popularidade inválidos
        if (ano <= 0 || pop <= 0) return;

        int indice = (ano - anoBase) / 10;
        if (indice < 0 || indice >= somaPop.length) return;

        somaPop[indice] += pop;
        contagem[indice]++;
    }
}