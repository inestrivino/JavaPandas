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
    public void sum(String label);
    public void mean(String label);
    public void quantile(String label);
    public void cumsum(String label);
    public void cumprod(String label);
}
