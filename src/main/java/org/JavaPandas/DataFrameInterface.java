package org.JavaPandas;
import java.util.List;

public interface DataFrameInterface {
    //Representation methods
    public void showFirstLines(int n);
    public void showLastLines(int n);
    public void showDataFrame();

    //Addition methods
    public void addRow(Object[] values);
    public void addCol(String label, String type, List<String> values);

    //Statistical calculation methods
    public double sum(String label);
    public double mean(String label);
    public DataFrame cumsum(String label);
    public DataFrame cumprod(String label);
    public double max(String label);
    public double min(String label);
}
