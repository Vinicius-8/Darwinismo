package com.oytu.darwinismo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinicius
 */
class Individuo implements Comparable<Individuo> {
    private List espacos = new ArrayList<>();
    private List valores = new ArrayList<>();
    private Double limiteEspacos; // limite do espaço em metros cúbicos do caminhão
    private Double notaAvaliacao; // somatório em reais dos produtos que estão no cromossomo do indivíduo;
    private Double espacoUsado; // armazena os metros cúbicos que o cromossomo do indivíduo está armazenando
    private int geracao;
    private List cromossomo = new ArrayList<>();
    
    public Individuo(List espacos, List valores, Double limiteEspacos){
        this.espacos = espacos;
        this.valores = valores;
        this.limiteEspacos = limiteEspacos;
        this.notaAvaliacao = 0.0;
        this.espacoUsado = 0.0;
        this.geracao = 0;
        
        for(int i=0; i < this.espacos.size(); i++){
            if (java.lang.Math.random() < 0.5 ){ // na primeira geração é criado o cromossomo com valores aleatórios
                this.cromossomo.add("0");
            }else{
                this.cromossomo.add("1");
            }
        }
    }
    
    public void avaliacao(){ // função fitness para avaliação de cada indivíduo
        Double nota = 0.0;
        Double somaEspacos = 0.0;
        for (int i =0; i < this.cromossomo.size(); i++){
            if(this.cromossomo.get(i).equals("1")){
                nota += (Double) this.valores.get(i);
                somaEspacos += (Double) this.espacos.get(i);
            }
        }
        
        if(somaEspacos > this.limiteEspacos){
            nota = 1.0;
        }
        
        this.notaAvaliacao = nota;
        this.espacoUsado = somaEspacos;
    }
    
    /*
        Operador CROSSOVER responsável pelo cruzamento de indivíduos de uma população. Utiliza o método de um ponto
        para sortiar o ponto de corte, onde o primeiro filho é composto pela concatenação da parte do primeiro pai 
        à esquerda do ponto de corte com a parte do segundo pai à direita do ponto de corte e o segundo filho pelas 
        partes restantes.        
    */
    public List crossover(Individuo outroIndividuo){ // função para cruzamento dos indivíduos e geração de novas soluções
        int corte = (int) Math.round(Math.random() * this.cromossomo.size()); // ponto de corte no cromossomo, função Math.random retorna um valor entre 0 e 1
        List filho1 = new ArrayList<>();
        filho1.addAll(outroIndividuo.getCromossomo().subList(0, corte));
        filho1.addAll(this.cromossomo.subList(corte, this.cromossomo.size()));
        
        List filho2 = new ArrayList<>();
        filho2.addAll(this.cromossomo.subList(0, corte));
        filho2.addAll(outroIndividuo.getCromossomo().subList(corte, this.cromossomo.size()));
        
        List<Individuo> filhos = new ArrayList<>();
        filhos.add(new Individuo(this.espacos, this.valores, this.limiteEspacos));
        filhos.add(new Individuo(this.espacos, this.valores, this.limiteEspacos));
        
        filhos.get(0).setCromossomo(filho1);
        filhos.get(0).setGeracao(this.geracao + 1);        
        filhos.get(1).setCromossomo(filho2);
        filhos.get(1).setGeracao(this.geracao + 1);
        
        return filhos;
    }
    
    /*
        Operador de MUTAÇÃO que faz a mutação de um cromossomo com base na taxa de mutação passada para o método.
        A mutação é a operação realizada após a operação crossover.
        O método de mutação utiliza o operador Random Resetting: é escolhido um novo valor, aleatoriamente, 
        a partir do conjunto de valores inteiros para cada posição.
    */    
    public Individuo mutacao(Double taxaMutacao){
        //System.out.println("Antes da mutação: " + this.cromossomo);
        
        for(int i = 0; i < this.cromossomo.size(); i++){
            if(Math.random() < taxaMutacao){
                if(this.cromossomo.get(i).equals("1")){
                    this.cromossomo.set(i, "0");
                }else{
                    this.cromossomo.set(i, "1");
                }
            }
        }
        
        //System.out.println("Depois da mutação: " + this.cromossomo);
        
        return this;
    }

    public Double getEspacoUsado() {
        return espacoUsado;
    }

    public void setEspacoUsado(Double espacoUsado) {
        this.espacoUsado = espacoUsado;
    }    

    public List getEspacos() {
        return espacos;
    }

    public void setEspacos(List espacos) {
        this.espacos = espacos;
    }

    public List getValores() {
        return valores;
    }

    public void setValores(List valores) {
        this.valores = valores;
    }

    public Double getLimiteEspacos() {
        return limiteEspacos;
    }

    public void setLimiteEspacos(Double limiteEspacos) {
        this.limiteEspacos = limiteEspacos;
    }

    public Double getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(Double notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public int getGeracao() {
        return geracao;
    }

    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public List getCromossomo() {
        return cromossomo;
    }

    public void setCromossomo(List cromossomo) {
        this.cromossomo = cromossomo;
    }        

    @Override
    public int compareTo(Individuo o) {
        if(this.notaAvaliacao > o.getNotaAvaliacao()){
            return -1;
        }
        if(this.notaAvaliacao < o.getNotaAvaliacao()){
            return 1;
        }
        return 0;
    }
} // fim classe Individuo

