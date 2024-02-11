package ru.chirkov.bookServiceApp.model;

/**
 * Projection for {@link Book}
 */
public interface BookInfo {
    String getName();

    AuthorInfo getAuthor();

    /**
     * Projection for {@link Author}
     */
    interface AuthorInfo {
        Long getId();

        String getName();

        String getSurname();

        String getPatronymic();
    }
}