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
                System.out.println("\n--- ANALISES DE DADOS ---");
                System.out.println("1. Qualidade por Genero");
                System.out.println("2. Analise de maturidade e Duracao");
                System.out.println("3. Tendencias de Producao por Decada");
                System.out.println("4. Eficiencia de Producao Internacional");
                System.out.println("5. Divergencia Critica (IMDB vs TMDB)");
                System.out.print("Escolha a analise: ");
                try {
                    int opAnalise = Integer.parseInt(scanner.nextLine());
                    switch (opAnalise) {
                        case 5:
                            divergenciaCritica();
                            break;
                        case 4:
                            break;
                        case 3:
                            break;
                        case 2:
                            break;
                        case 1:
                            break;
                        default:
                    System.out.println("Opcao invalida!.");
                    break;
            }
                } catch (NumberFormatException e) {
                    System.out.println("Opção inválida.");
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
                salvarArquivo();
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

    // função Auxiliar para ler colunas com segurança
    private static String getSafe(String[] campos, int indice) {
        if (indice >= campos.length) return ""; // Retorna vazio se a coluna não existir na linha
        return campos[indice];
    }

    // converte para Double com segurança. Se o campo estiver vazio ou for texto inválido, retorna 0.0.
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

    // op 2.5
    private static void divergenciaCritica() {
        if (arvore.isEmpty()) {
            System.out.println("A arvore está vazia. Carregue os dados primeiro (Opcao 1).");
            return;
        }

        System.out.print("Digite o valor de corte para a diferença de notas (ex: 2.0): ");
        double corte;
        try {
            corte = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número decimal válido.");
            return;
        }

        System.out.println("\n--- Títulos com Divergência Crítica (> " + corte + ") ---");
        
        // Utilizando a LinkedList customizada do projeto como Fila (Queue)
        LinkedList<Node<ProgramaNetFlix>> fila = new LinkedList<>();
        fila.addLast(arvore.getRaiz());

        boolean encontrou = false;

        // Loop do percurso em Nível (Breadth-First Search)
        while (!fila.isEmpty()) {
            Node<ProgramaNetFlix> atual = fila.pollFirst(); // Desenfileira
            ProgramaNetFlix programa = atual.getValue();

            double imdb = programa.getImdb_score();
            double tmdb = programa.getTmdb_score();

            // Filtramos notas 0.0 para evitar falsas divergências devido a dados faltantes no CSV
            if (imdb > 0 && tmdb > 0) {
                double diff = Math.abs(imdb - tmdb);
                
                if (diff > corte) {
                    System.out.printf("Título: %-40s | IMDB: %.1f | TMDB: %.1f | Diferença: %.1f\n", 
                            programa.getTitle(), imdb, tmdb, diff);
                    encontrou = true;
                }
            }

            // Enfileira os filhos para continuar o percurso em nível
            if (atual.getFilhoEsquerdo() != null) {
                fila.addLast(atual.getFilhoEsquerdo());
            }
            if (atual.getFilhoDireito() != null) {
                fila.addLast(atual.getFilhoDireito());
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum título encontrado com uma divergência superior a " + corte + ".");
        }
        System.out.println("---------------------------------------------------------");
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
            System.out.println("Tempo de execucao: " + (tempoFinal - tempoInicial) + " nanosegundos");
        } else {
            System.out.println("Titulo nao encontrado.");
        }
    }

    // método auxiliar para altura (op 6)
    private static int calcularAltura(Node<ProgramaNetFlix> atual) {
        if (atual == null) return -1;
        return 1 + Math.max(calcularAltura(atual.getFilhoEsquerdo()), calcularAltura(atual.getFilhoDireito()));
    }

    // PENDENTE: métodos para Inserção manual e Remoção c/  lógica similar chamando arvore.inserir e arvore.eliminar
    private static void inserirNovoPrograma() { 
        System.out.println("\n--- INSERIR NOVO PROGRAMA ---");
        
        System.out.print("Tipo (Digite 'ts' para SHOW ou 'tm' para MOVIE): ");
        String prefixo = scanner.nextLine().toLowerCase();
        if (!prefixo.equals("ts") && !prefixo.equals("tm")) {
            System.out.println("Erro: Categoria invalida.");
            return;
        }

        System.out.print("Digite o número único para o ID: ");
        String numeroUnico = scanner.nextLine();
        String id = prefixo + numeroUnico; // Conforme regra do ID 

        // Coleta de dados básicos para exemplificar os 15 atributos [cite: 65]
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Tipo (SHOW/MOVIE): ");
        String tipo = scanner.nextLine();
        System.out.print("Descricao: ");
        String desc = scanner.nextLine();
        System.out.print("Ano de Lançamento: ");
        int ano = parseIntegerSafe(scanner.nextLine());
        System.out.print("Certificacao de Idade: ");
        String age = scanner.nextLine();
        System.out.print("Duração (runtime): ");
        int runtime = parseIntegerSafe(scanner.nextLine());
        System.out.print("Gêneros: ");
        String generos = scanner.nextLine();
        System.out.print("Países de Produção: ");
        String paises = scanner.nextLine();
        System.out.print("Temporadas (se SHOW): ");
        double seasons = parseDoubleSafe(scanner.nextLine());
        System.out.print("IMDB ID: ");
        String imdbId = scanner.nextLine();
        System.out.print("IMDB Score: ");
        double imdbScore = parseDoubleSafe(scanner.nextLine());
        System.out.print("IMDB Votes: ");
        double imdbVotes = parseDoubleSafe(scanner.nextLine());
        System.out.print("TMDB Popularity: ");
        double tmdbPop = parseDoubleSafe(scanner.nextLine());
        System.out.print("TMDB Score: ");
        double tmdbScore = parseDoubleSafe(scanner.nextLine());

        // Criando o array para validação de preenchimento 
        String[] campos = {id, titulo, tipo, desc, String.valueOf(ano), age, String.valueOf(runtime), 
                           generos, paises, String.valueOf(seasons), imdbId, String.valueOf(imdbScore), 
                           String.valueOf(imdbVotes), String.valueOf(tmdbPop), String.valueOf(tmdbScore)};

        if (validarCampos(campos)) {
            ProgramaNetFlix novo = new ProgramaNetFlix(id, titulo, tipo, desc, ano, age, runtime, 
                                     generos, paises, seasons, imdbId, imdbScore, imdbVotes, tmdbPop, tmdbScore);
            arvore.inserir(novo);
            System.out.println("Programa inserido com sucesso!");
        } else {
            System.out.println("Erro: Todos os 15 atributos devem estar preenchidos.");
        }
     }

    private static void removerPrograma() { 
        System.out.print("Digite o ID do programa a ser removido: ");
        String idRemover = scanner.nextLine();

        // Template para busca e remoção via Comparable (ID)
        ProgramaNetFlix template = new ProgramaNetFlix();
        template.setId(idRemover);

        if (arvore.eliminar(template)) {
            System.out.println("Programa com ID " + idRemover + " removido com sucesso.");
        } else {
            System.out.println("Erro: Programa não encontrado.");
        }
     }

     private static void salvarArquivo() {
        System.out.print("Digite o nome do arquivo para salvar (ex: dataset_atualizado.csv): ");
        String nomeArquivo = scanner.nextLine();

        try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(nomeArquivo))) {
            // Cabeçalho do CSV [cite: 49]
            pw.println("id,title,type,description,release_year,age_certification,runtime,genres,production_countries,seasons,imdb_id,imdb_score,imdb_votes,tmdb_popularity,tmdb_score");
            
            // Percurso em ordem para salvar os dados de forma organizada 
            escreverNoArquivo(arvore.getRaiz(), pw);
            
            System.out.println("Dados salvos com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
     }

     // método auxiliar recursivo do "salvarArquivo", para percorrer a árvore e escrever no PrintWriter
    private static void escreverNoArquivo(Node<ProgramaNetFlix> no, java.io.PrintWriter pw) {
        if (no != null) {
            escreverNoArquivo(no.getFilhoEsquerdo(), pw);
            
            ProgramaNetFlix p = no.getValue();
            // Formatação CSV simples (considerando que dados com vírgula devem estar entre aspas)
            String linha = String.format("%s,\"%s\",%s,\"%s\",%d,%s,%d,\"%s\",\"%s\",%.1f,%s,%.1f,%.1f,%.1f,%.1f",
                p.getId(), p.getTitle(), p.getType(), p.getDescription(), p.getRelease_year(),
                p.getAge_certification(), p.getRuntime(), p.getGenres(), p.getProduction_countries(),
                p.getSeasons(), p.getImdb_id(), p.getImdb_score(), p.getImdb_votes(),
                p.getTmdb_popularity(), p.getTmdb_score());
            
            pw.println(linha);
            
            escreverNoArquivo(no.getFilhoDireito(), pw);
        }
    }

    // PENDENTE: métodos de analise de dados da op 2 do PDF
    // criar outro while dentro do existente com as 5 opções de método de análise  
}