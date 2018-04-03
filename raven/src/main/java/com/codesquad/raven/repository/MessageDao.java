package com.codesquad.raven.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT key_message, value_message FROM raven_message WHERE whom = :whom")
    List<PairModel> findMessageFor(String whom);

    @Query("SELECT key_message, value_message FROM raven_message WHERE whom = :whom AND key_message = :key")
    PairModel findValueFor(String whom, String key);

    @Insert
    void insertAll(List<Message> message);

    @Query("DELETE FROM raven_message WHERE whom = :whom")
    void deleteMessagesFor(String whom);

    @Query("DELETE FROM raven_message")
    void deleteAllMessages();

    @Query("DELETE FROM raven_message WHERE who = :who")
    void deleteMessagesFrom(String who);
}
