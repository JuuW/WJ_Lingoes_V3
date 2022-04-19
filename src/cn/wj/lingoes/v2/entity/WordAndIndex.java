/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.wj.lingoes.v2.entity;

import java.util.Objects;

/**
 *
 * @author huyvi
 */
public class WordAndIndex {
    private String word;
    private int index;

    public WordAndIndex() {
    }

    public WordAndIndex(String word, int index) {
        this.word = word;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getWord() {
        return word;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        
        if(obj == null || !(obj instanceof WordAndIndex)){
            return false;
        }
        
        WordAndIndex tmp = (WordAndIndex) obj;
        return tmp.getWord().equalsIgnoreCase(this.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.word);
    }

    @Override
    public String toString() {
        return word;
    }
}
