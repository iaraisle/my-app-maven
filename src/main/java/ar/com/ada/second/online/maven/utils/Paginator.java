package ar.com.ada.second.online.maven.utils;

import java.util.ArrayList;
import java.util.List;

public class Paginator {

    private static final String OPTION_FIRST = "[ Inicio ]";
    private static final String OPTION_NEXT = "[ Siguiente ]";
    private static final String OPTION_PREVIOUS = "[ Anterior ]";
    private static final String OPTION_LAST = "[ Ultimo ]";
    private static final String OPTION_EXIT = "[ Q para salir ]";

    public static final String EDIT = "[ Editar ]";
    public static final String DELETE = "[ Eliminar ]";


    public static List<String> buildPaginator(int currentPage, int totalPages) {
        List<String> pages = new ArrayList<>();

        pages.add(OPTION_FIRST);
        pages.add(OPTION_NEXT);

        if (totalPages > 10) {
            int from = 1;
            int limit = from + 4;

            if (currentPage > 3) {
                pages.add("[" + 1 + "]");
                pages.add("[...]");

                from = currentPage - 1;
                limit = ((totalPages - currentPage) >= 2) ? from + 4 : totalPages;
            }

            for (int i = from; i <= limit; i++) {
                pages.add("[" + i + "]");
            }

            if ((totalPages - currentPage) > 3) {
                pages.add("[...]");
                pages.add("[" + totalPages + "]");
            }
        } else {

            for (int i = 1; i <= totalPages; i++) {
                pages.add("[" + i + "]");
            }

        }


        pages.add(OPTION_PREVIOUS);
        pages.add(OPTION_LAST);
        pages.add(OPTION_EXIT);

        return pages;
    }
}
