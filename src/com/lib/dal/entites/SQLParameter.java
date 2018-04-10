/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lib.dal.entites;

/**
 *
 * @author programer10
 */
public class SQLParameter<T> {
    
    private boolean isOutputParameter;
    private T value;
    private Integer ordinalNumber;

    public SQLParameter(boolean isOutputParameter, T value) {
        this.isOutputParameter = isOutputParameter;
        this.value = value;
    }

    public boolean getIsOutputParameter() {
        return isOutputParameter;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
    
}
