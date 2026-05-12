package ____TesteABB;

/* implementa Comparable para permitir a inserção na árvore BST usando o 'id' como chave. */
public class ProgramaNetFlix implements Comparable<ProgramaNetFlix> {

    private String id;
    private String title;
    private String type;
    private String description;
    private int release_year;
    private String age_certification;
    private int runtime;
    private String genres;
    private String production_countries;
    private double seasons;
    private String imdb_id;
    private double imdb_score;
    private double imdb_votes;
    private double tmdb_popularity;
    private double tmdb_score;

    public ProgramaNetFlix() {
    }

    public ProgramaNetFlix(String id, String title, String type, String description, int release_year,
                           String age_certification, int runtime, String genres, String production_countries,
                           double seasons, String imdb_id, double imdb_score, double imdb_votes,
                           double tmdb_popularity, double tmdb_score) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.release_year = release_year;
        this.age_certification = age_certification;
        this.runtime = runtime;
        this.genres = genres;
        this.production_countries = production_countries;
        this.seasons = seasons;
        this.imdb_id = imdb_id;
        this.imdb_score = imdb_score;
        this.imdb_votes = imdb_votes;
        this.tmdb_popularity = tmdb_popularity;
        this.tmdb_score = tmdb_score;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getRelease_year() { return release_year; }
    public void setRelease_year(int release_year) { this.release_year = release_year; }

    public String getAge_certification() { return age_certification; }
    public void setAge_certification(String age_certification) { this.age_certification = age_certification; }

    public int getRuntime() { return runtime; }
    public void setRuntime(int runtime) { this.runtime = runtime; }

    public String getGenres() { return genres; }
    public void setGenres(String genres) { this.genres = genres; }

    public String getProduction_countries() { return production_countries; }
    public void setProduction_countries(String production_countries) { this.production_countries = production_countries; }

    public double getSeasons() { return seasons; }
    public void setSeasons(double seasons) { this.seasons = seasons; }

    public String getImdb_id() { return imdb_id; }
    public void setImdb_id(String imdb_id) { this.imdb_id = imdb_id; }

    public double getImdb_score() { return imdb_score; }
    public void setImdb_score(double imdb_score) { this.imdb_score = imdb_score; }

    public double getImdb_votes() { return imdb_votes; }
    public void setImdb_votes(double imdb_votes) { this.imdb_votes = imdb_votes; }

    public double getTmdb_popularity() { return tmdb_popularity; }
    public void setTmdb_popularity(double tmdb_popularity) { this.tmdb_popularity = tmdb_popularity; }

    public double getTmdb_score() { return tmdb_score; }
    public void setTmdb_score(double tmdb_score) { this.tmdb_score = tmdb_score; }

    /**
     * compareTo deve ser obrigatoriamente implementado devido ao requisito da classe ABB
     * a comparação é feita utilizando o 'id' como chave de inserção
     */
    @Override
    public int compareTo(ProgramaNetFlix outro) {
        return this.id.compareTo(outro.id);
    }

    /* toString facilita a exibição dos dados */
    @Override
    public String toString() {
        return "ID: " + id + " | Título: " + title + " | Tipo: " + type + " | Ano: " + release_year + 
               " | IMDB Score: " + imdb_score;
    }
}