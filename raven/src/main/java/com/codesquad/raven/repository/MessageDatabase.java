package com.codesquad.raven.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * This is be the bridge between our library and SQLite.
 */
@Database(entities = {Message.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public static String DATABASE_NAME = "RAVEN_MESSAGE_DATABASE";

    public abstract MessageDao messageDao();

    private static MessageDatabase INSTANCE;

    public static MessageDatabase getMessageDatabase(Context context) {
        if( INSTANCE == null ) {
            INSTANCE = create(context);
        }

        return INSTANCE;
    }

    private static MessageDatabase create(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), MessageDatabase.class, DATABASE_NAME).build();
    }
}
