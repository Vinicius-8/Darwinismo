package com.oytu.darwinismo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Vinicius
 */
class AlgoritmoGenetico {
    private int tamanhoPopulacao; // quantos individuos faz parte da população
    private List<Individuo> populacao = new ArrayList<>();
    private int geracao; // indica qual é a geração do AG
    private Individuo melhorSolucao; // indica qual melhor individuo que vai maximar o valor e apresentar a melhor solução
    private List<Individuo> geracoes;
    public AlgoritmoGenetico(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
        geracoes = new ArrayList<>();
    }
    
    public void inicializaPopulacao(List espacos, List valores, Double limiteEspacos){
        for(int i=0; i<this.tamanhoPopulacao; i++){
            this.populacao.add(new Individuo((espacos), valores, limiteEspacos));
        }
        
        this.melhorSolucao = this.populacao.get(0); // para a inicialização melhor individuo é na posição 0            
    }
    
    public void ordenaPopulacao(){ // método que ordena de forma decrescente o indivíduo que tem a melhor nota de avaliação (fitness)
        Collections.sort(this.populacao);
    }
    /*
        Método responsável por guardar e comparar a melhor solução(indivíduo) entre as diversas populações existentes.
    */    
    public void melhorIndividuo(Individuo individuo){ // verifica se existe uma melhor solução que a solução atual
        if(individuo.getNotaAvaliacao() > this.melhorSolucao.getNotaAvaliacao()){
            this.melhorSolucao = individuo;
        }
    }
    /*
        Método utilizado na ETAPA DE SELEÇÃO para o método da ROLETA VICIADA, que irá calcular a porcentagem com base
        na avaliação(FITNESS) de cada indivíduo e em seguida aplicar os operadores de CRUZAMENTO e MUTAÇÃO em uma população.
    */
    public Double somaAvaliacoes(){ // método que retorna o somatório da avaliação de todos os individuos de uma população
        Double soma = 0.0;
        
        for(Individuo individuo: this.populacao){
            soma += individuo.getNotaAvaliacao();
        }
        
        return soma;
    }
    /*
        Método responsável por selecionar os pais com base na porcentagem dos resultados do médoto somaAvaliacoes, 
        para cruzamento e geração dos filhos.
    */
    public int selecionaPai(Double somaAvaliacao){
        int pai = -1; // posição -1 inválida, somente para fazer a inicialização
        Double valorSorteado = Math.random() * somaAvaliacao; // função Math.random() vai retornar um valor entre 0..1
        Double soma = 0.0;
        int i = 0;
        while(i < this.populacao.size() && soma < valorSorteado){
            soma += this.populacao.get(i).getNotaAvaliacao();
            pai += 1;
            i += 1;
        }
        
        return pai;
    }
    
    /*
        Método utilizado para acompanhar como estão os resultados das gerações
    */
    public void visualizaGeracao(){
        Individuo melhor = this.populacao.get(0);
        this.geracoes.add(melhor);
        System.out.println("G: " + melhor.getGeracao() +
                " Valor: "+ melhor.getNotaAvaliacao() +
                " Espaço: "+melhor.getEspacoUsado() +
                " Cromossomo: " + melhor.getCromossomo());
    }
    /*
        Retorna qual a melhor solução de todas as gerações
    */
    public List resolver(Double taxaMutacao, int numeroGeracoes, List espacos, List valores, Double limiteEspacos){
        
        this.inicializaPopulacao(espacos, valores, limiteEspacos); // inicializa a população de forma aleatória
        for(Individuo individuo: this.populacao){
            individuo.avaliacao(); // faz a avaliação de cada individuo em uma população
        }        
        this.ordenaPopulacao(); // ordena de forma que o individuo com a nota de avaliação mais alta fica na posição 0
        this.visualizaGeracao();
        
        for(int geracao=0; geracao < numeroGeracoes; geracao++){
            Double somaAvaliacao = this.somaAvaliacoes(); // soma a nota de avaliação de cada individuo para toda população
            List<Individuo> novaPopulacao = new ArrayList<>();
            
            for(int i=0; i<this.populacao.size()/2; i++){
                int pai1 = this.selecionaPai(somaAvaliacao);
                int pai2 = this.selecionaPai(somaAvaliacao);
                
                List<Individuo> filhos = this.getPopulacao().get(pai1).crossover(this.getPopulacao().get(pai2));
                novaPopulacao.add(filhos.get(0).mutacao(taxaMutacao));
                novaPopulacao.add(filhos.get(1).mutacao(taxaMutacao));
            }
            
            this.setPopulacao(novaPopulacao);
            for(Individuo individuo: this.getPopulacao()){
                individuo.avaliacao();
            }
            this.ordenaPopulacao();
            this.visualizaGeracao();
            Individuo melhor = this.populacao.get(0);
            this.melhorIndividuo(melhor);
        }
        
        System.out.println("Melhor solução G -> " + this.melhorSolucao.getGeracao() +
                " Valor: " + this.melhorSolucao.getNotaAvaliacao() + 
                " Espaço: " + this.melhorSolucao.getEspacoUsado() +
                " Cromossomo: " + this.melhorSolucao.getCromossomo());
        
        return this.melhorSolucao.getCromossomo(); // retorna a melhor resposta de todas as soluções
    }

    public int getTamanhoPopulacao() {
        return tamanhoPopulacao;
    }

    public void setTamanhoPopulacao(int tamanhoPopulacao) {
        this.tamanhoPopulacao = tamanhoPopulacao;
    }

    public List<Individuo> getPopulacao() {
        return populacao;
    }

    public void setPopulacao(List<Individuo> populacao) {
        this.populacao = populacao;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public Individuo getMelhorSolucao() {
        return melhorSolucao;
    }

    public void setMelhorSolucao(Individuo melhorSolucao) {
        this.melhorSolucao = melhorSolucao;
    }   
    public List<Individuo> getGeracoes(){
        return this.geracoes;
    }
    
}
