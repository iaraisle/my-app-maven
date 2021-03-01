package ar.com.ada.second.online.maven.utils;

import java.util.ArrayList;
import java.util.List;

public class Paginator {

    private static final String OPTION_FIRST = "[ Inicio ]";
    private static final String OPTION_NEXT = "[ Siguiente ]";
    private static final String OPTION_PREVIOUS = "[ Anterior ]";
    private static final String OPTION_LAST = "[ Ultimo ]";
    private static final String OPTION_EXIT = "[ Q para salir ]";

    public static List<String> buildPaginator(int currentPage, int totalPages) {
        List<String> pages = new ArrayList<>();
        pages.add(OPTION_FIRST);
        pages.add(OPTION_NEXT);

        for (int i = 1; i <= totalPages; i++) {
            pages.add("[" + i + "]");
        }

        pages.add(OPTION_PREVIOUS);
        pages.add(OPTION_LAST);
        pages.add(OPTION_EXIT);
        return null;
    }
}
