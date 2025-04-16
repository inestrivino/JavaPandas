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
    public double sum(String label);
    public double mean(String label);
    public DataFrame cumsum(String label);
    public DataFrame cumprod(String label);
    public double max(String label);
    public double min(String label);
}
