package org.JavaPandas;

import java.util.HashMap;
import java.util.List;

public class DataFrame implements DataFrameInterface{    
    private HashMap<String, List<Object>> Columns; //The string is the label, the list are the elements on the column
    //The indexes are numerical, therefore we don't need to implement anything

    //CONSTRUCTORS
    public DataFrame(String[] columnTypes){
        //TODO
    }

    public DataFrame(String csvName){
        //TODO
    }

    public DataFrame(DataFrame motherFrame, int[] index){
        //TODO: Create data frame from sub-section of indexes
    }

    public DataFrame(DataFrame mothFrame, String[] labels){
        //TODO: Create data frame from sub-secition of labels
    }

    //REPRESENTATION METHODS
    @Override
    public void showFirstLines(int n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showFirstLines'");
    }

    @Override
    public void showLastLines(int n) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showLastLines'");
    }

    @Override
    public void showDataFrame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showDataFrame'");
    }

    //STATISTICAL CALCULATION METHODS
    @Override
    public void sum(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sum'");
    }

    @Override
    public void mean(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mean'");
    }

    @Override
    public void quantile(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'quantile'");
    }

    @Override
    public void cumsum(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cumsum'");
    }

    @Override
    public void cumprod(String label) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cumprod'");
    }

    //TODO: Mechanism for advanced selection
}