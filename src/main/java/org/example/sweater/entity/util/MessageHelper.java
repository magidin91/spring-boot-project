package org.example.sweater.entity.util;

import org.example.sweater.entity.User;

public abstract class MessageHelper {

    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}
