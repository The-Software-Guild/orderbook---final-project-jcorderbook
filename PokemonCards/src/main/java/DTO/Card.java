/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Objects;

/**
 *
 * @author recyn
 */
public class Card {

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.name);
        hash = 43 * hash + this.hp;
        hash = 43 * hash + Objects.hashCode(this.stage);
        hash = 43 * hash + Objects.hashCode(this.attack);
        hash = 43 * hash + Objects.hashCode(this.element);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.hp != other.hp) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.stage, other.stage)) {
            return false;
        }
        if (!Objects.equals(this.attack, other.attack)) {
            return false;
        }
        if (this.element != other.element) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Card{" + "name=" + name + ", hp=" + hp + ", stage=" + stage + ", attack=" + attack + ", element=" + element + '}';
    }
    
    public enum Element{
            WATER, FIRE, ELECTRIC, GRASS, STEEL, DARK, PSYCHIC, FIGHTING, NORMAL
            }
    private String name;
    private int hp;
    private String stage;
    private String attack;
    private Element element;
    
    public Card(String name, int hp, String stage, String attack, Element element){
        this.name=name;
        this.hp= hp;
        this.attack=attack;
        this.stage=stage;
        this.element=element;
    }
    public Card(String name){
        this.name=name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    public void setHp(int hp){
        this.hp=hp;
    }
    public void setAttack(String attack){
        this.attack=attack;
    }
    
    public void setStage(String stage){
        this.stage=stage;
    }
    
    public void setElement(Element element){
        this.element=element;
    }
    
    public String getName(){
        return name;
    }
    public int getHp(){
        return hp;
    }
    
    public String getAttack(){
        return attack;
    }
    
    public String getStage(){
        return stage;
    }
    
    public Element getElement(){
        return element;
    }
    
}
