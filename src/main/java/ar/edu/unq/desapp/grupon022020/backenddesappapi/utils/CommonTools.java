package ar.edu.unq.desapp.grupon022020.backenddesappapi.utils;

import java.util.List;

public abstract class CommonTools {

    public static <E> List<E> getFirstTenIfExists(List<E> elements) {
        int lastIndex = 10;
        if (elements.size() < lastIndex)
            lastIndex = elements.size();
        return elements.subList(0, lastIndex);
    }

}
