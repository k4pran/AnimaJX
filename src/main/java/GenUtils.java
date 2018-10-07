import java.util.*;

public class GenUtils<T> {

    public static <T extends  Comparable<? super  T>> T getMinValue(List<T> vals) {
        Collections.sort(vals);
        return vals.get(0);
    }

    public static <T extends  Comparable<? super  T>> T getMaxValue(List<T> vals) {
        Collections.sort(vals);
        return vals.get(vals.size() - 1);
    }
}
