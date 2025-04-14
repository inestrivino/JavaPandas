import static org.junit.Assert.assertThrows;

import org.JavaPandas.DataFrame;
import org.junit.Test;

public class DataFrameCalculTest {
    @Test
    public void testSum() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.sum("label"));
    }

    @Test
    public void testMean() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.mean("label"));
    }

    @Test
    public void testQuantile() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.quantile("label"));
    }

    @Test
    public void testCumsum() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.cumsum("label"));
    }

    @Test
    public void testCumprod() {
        DataFrame df = new DataFrame(new String[]{"int"});
        assertThrows(UnsupportedOperationException.class, () -> df.cumprod("label"));
    }
}
