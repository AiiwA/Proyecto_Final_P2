package co.edu.uniquindio.demop2pf.model;

import lombok.Getter;

public class Tarifa {
    private double base;
    private double peso;
    private double volumen;
    private double prioridad;
    private double recargos;
    
    private Tarifa(Builder builder) {
        this.base = builder.base;
        this.peso = builder.peso;
        this.volumen = builder.volumen;
        this.prioridad = builder.prioridad;
        this.recargos = builder.recargos;
    }
    
    // Builder Pattern
    public static class Builder {
        private double base;
        private double peso;
        private double volumen;
        private double prioridad;
        private double recargos;
        
        public Builder(double base) {
            this.base = base;
            this.peso = 0.0;
            this.volumen = 0.0;
            this.prioridad = 0.0;
            this.recargos = 0.0;
        }
        
        public Builder conBase(double base) {
            this.base = base;
            return this;
        }
        
        public Builder conPeso(double peso) {
            this.peso = peso;
            return this;
        }
        
        public Builder conVolumen(double volumen) {
            this.volumen = volumen;
            return this;
        }
        
        public Builder conPrioridad(double prioridad) {
            this.prioridad = prioridad;
            return this;
        }
        
        public Builder conRecargos(double recargos) {
            this.recargos = recargos;
            return this;
        }
        
        public Tarifa build() {
            return new Tarifa(this);
        }
    }
    
    // Getters
    public double getBase() {
        return base;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public double getVolumen() {
        return volumen;
    }
    
    public double getPrioridad() {
        return prioridad;
    }
    
    public double getRecargos() {
        return recargos;
    }
    
    public double calcularTotal() {
        return base + peso + volumen + prioridad + recargos;
    }
}
