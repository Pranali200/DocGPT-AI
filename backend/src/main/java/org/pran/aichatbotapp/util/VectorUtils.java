package org.pran.aichatbotapp.util;

import java.util.List;
import java.util.stream.Collectors;

public class VectorUtils {

    private VectorUtils() {
    }

    public static String toPgVector(List<Double> vector) {

        return vector.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(
                        ",",
                        "[",
                        "]"
                ));
    }
}
