package org.JavaPandas;
import java.util.List;
import java.util.Map;

public interface DataFrameInterface {
    //Representation methods
    public void showFirstLines(int n);
    public void showLastLines(int n);
    public void showDataFrame();

    //Advanced selection method
    public DataFrame query(String condition);

    //Addition methods
    public void addRow(String[] values);
    public void addCol(String label, String type, List<String> values);

    //Statistical calculation methods
    public double sum(String label);
    public double mean(String label);
    public DataFrame cumsum(String label);
    public DataFrame cumprod(String label);
    public double max(String label);
    public double min(String label);

    //Getters
    public List<String> getColumnNames();
    public List<String> getColumnTypes();
    public Map<String, List<Object>> getData();
}
