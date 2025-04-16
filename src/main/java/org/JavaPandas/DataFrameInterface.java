package org.JavaPandas;

public interface DataFrameInterface {
    //Representation methods
    public void showFirstLines(int n);
    public void showLastLines(int n);
    public void showDataFrame();

    //Addition methods
    public void addRow();
    public void addCol(String label);

    //Statistical calculation methods
    public void sum(String label);
    public void mean(String label);
    public void quantile(String label);
    public void cumsum(String label);
    public void cumprod(String label);
}
