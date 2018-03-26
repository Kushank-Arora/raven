package com.codesquad.raven;

import android.content.Context;

import com.codesquad.raven.repository.Message;
import com.codesquad.raven.repository.MessageDatabase;
import com.codesquad.raven.repository.PairModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to communicate between different activities/fragments using DB (Room)
 * <p>
 * Each Activity/Fragment can get the data sent to them, which launching it.
 * <p>
 * Each Fragment of an activity can get the data sent to its parent Activity.
 * <p>
 * All the communication is based on the complete package name of the Activity/Fragment.
 */
public abstract class Raven {

    /**
     * It creates the database used for Raven
     * <p>
     * Database Instantiation is expensive, thus should be done once,
     * and thus should be done once, i.e., in Application Class
     */
    public static void init(Context context) {
        MessageDatabase.getMessageDatabase(context);
    }

    /**
     * It deletes all previous communication 'it' had made to other Activity/Fragment.
     *
     * @param context Context which wants messages send by 'it' to be deleted.
     */
    public static void deletePrevCommunication(Context context, OnMessagesDeletedListener listener) {
        deletePrevCommunication(context.getClass(), listener);
    }

    /**
     * It deletes all previous communication it had made to other Activity/Fragment.
     *
     * @param from Class to whose intended data is to be gathered.
     */
    public static void deletePrevCommunication(final Class from, final OnMessagesDeletedListener listener) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        MessageDatabase db = MessageDatabase.getMessageDatabase(null);
                        db.messageDao().deleteMessagesFrom(from.getName());

                        if (listener != null) {
                            listener.onMessagesDeleted();
                        }
                    }
                }
        ).start();
    }

    /**
     * It returns a map<String, Object> representing the data passed to this Activity/Fragment.
     *
     * @param context Context in which the data is to be retrieved.
     */
    public static void getValues(Context context, OnMessagesLoadedListener listener) {
        getValues(context.getClass(), listener);
    }

    /**
     * It returns a map<String, Object> representing the data passed to this Activity/Fragment.
     *
     * @param thisClass Class to whose intended data is to be gathered.
     */
    public static void getValues(final Class thisClass, final OnMessagesLoadedListener listener) {
        if (listener != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MessageDatabase db = MessageDatabase.getMessageDatabase(null);
                    List<PairModel> listData = db.messageDao().findMessageFor(thisClass.getName());
                    Map<String, Object> mapData = new HashMap<>();
                    Gson gson = new Gson();
                    for (PairModel pair : listData) {
                        try {
                            mapData.put(
                                    pair.getKeyMessage(),
                                    gson.fromJson(pair.getValueMessage(), Class.forName(pair.getValueType()))
                            );
                        } catch (ClassNotFoundException e) {
                            //TODO: remove this.
                            e.printStackTrace();
                        }
                    }
                    listener.onMessagesLoaded(mapData);
                }
            }).start();
        }
    }

    /**
     * It returns the object mapped with `key` passed to this Activity/Fragment.
     *
     * @param context Context in which the data is to be retrieved.
     * @param key     Key with which the intended data was mapped.
     */
    public static void getValue(Context context, String key, OnSingleMessageLoadedListener listener) {
        getValue(context.getClass(), key, listener);
    }

    /**
     * It returns the object mapped with `key` passed to this Activity / Fragment.
     *
     * @param thisClass Class to whose intended data is to be gathered.
     * @param key       Key with which the intended data was mapped.
     */
    public static void getValue(final Class thisClass, final String key, final OnSingleMessageLoadedListener listener) {
        if (listener != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MessageDatabase db = MessageDatabase.getMessageDatabase(null);
                    PairModel value = db.messageDao().findValueFor(thisClass.getName(), key);
                    try {
                        listener.onSingleMessageLoaded(
                                (new Gson()).fromJson(
                                        value.getValueMessage(),
                                        Class.forName(value.getValueType()
                                        ))
                        );
                    } catch (ClassNotFoundException e) {
                        //TODO : remove this
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * This saves key-value pairs for the one who wants to send, to whom it is to be sent, and what.
     *
     * @param from          the sending class
     * @param to            the class going to receive it.
     * @param keyValuePairs data
     */
    private static void setValue(final Class from, final Class to, final List<PairModel> keyValuePairs, final OnMessagesSavedListener listener) {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        List<Message> listValues = new ArrayList<>();
                        for (PairModel keyValuePair : keyValuePairs) {
                            listValues.add(new Message(
                                    from.getName(),
                                    to.getName(),
                                    keyValuePair.getKeyMessage(),
                                    keyValuePair.getValueMessage(),
                                    keyValuePair.getValueType()
                            ));
                        }

                        MessageDatabase db = MessageDatabase.getMessageDatabase(null);
                        db.messageDao().deleteMessagesFor(to.getName());
                        db.messageDao().insertAll(listValues);
                        if (listener != null) {
                            listener.onMessagesSaved();
                        }
                    }
                }
        ).start();
    }

    /**
     * It is used to send Data from One Activity/Fragment to another using kkey value pairs.
     *
     * @param from the class sending the message
     * @param to   the class, to which the message is intended.
     */
    public static RavenInstance startCommunication(Class from, Class to) {
        return new RavenInstance(from, to);
    }

    /**
     * It is used to send Data from One Activity/Fragment to another using kkey value pairs.
     *
     * @param from the context of sending the message
     * @param to   the class, to which the message is intended.
     */
    public static RavenInstance startCommunication(Context from, Class to) {
        return new RavenInstance(from.getClass(), to);
    }

    /**
     * This deletes all data intended to this Activity/Fragment.
     */
    public static void cleanup(final Context intendedTo, final OnMessagesDeletedListener listener) {
        cleanup(intendedTo.getClass(), listener);
    }

    /**
     * This deletes all data intended to this Activity/Fragment.
     */
    public static void cleanup(final Class intendedTo, final OnMessagesDeletedListener listener) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        MessageDatabase db = MessageDatabase.getMessageDatabase(null);
                        db.messageDao().deleteMessagesFor(intendedTo.getName());

                        if (listener != null) {
                            listener.onMessagesDeleted();
                        }
                    }
                }
        ).start();
    }

    /**
     * This deletes every communication data left in the app, related to every Activity/Fragment.
     */
    public static void hardCleanup(final OnMessagesDeletedListener listener) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        MessageDatabase db = MessageDatabase.getMessageDatabase(null);
                        db.messageDao().deleteAllMessages();

                        if (listener != null) {
                            listener.onMessagesDeleted();
                        }
                    }
                }
        ).start();
    }

    public static class RavenInstance {
        Class from;
        Class to;
        List<PairModel> keyValuePairs;

        /**
         * Instance used for sending data.
         *
         * @param from The class going to send.
         * @param to   The class going to receive.
         */
        private RavenInstance(Class from, Class to) {
            this.from = from;
            this.to = to;
            keyValuePairs = new ArrayList<>();
        }

        /**
         * Instance used for sending data.
         *
         * @param from The context in which it is going to send.
         * @param to   The class going to receive.
         */
        private RavenInstance(Context from, Class to) {
            this(from.getClass(), to);
        }

        public RavenInstance add(String key, Object value) {
            keyValuePairs.add(new PairModel(
                    key,
                    (new Gson()).toJson(value),
                    value.getClass().getName()
            ));
            return this;
        }

        public void save(OnMessagesSavedListener listener) {
            Raven.setValue(from, to, keyValuePairs, listener);
        }
    }

    public interface OnMessagesDeletedListener {
        void onMessagesDeleted();
    }

    public interface OnMessagesLoadedListener {
        void onMessagesLoaded(Map<String, Object> messages);
    }

    public interface OnSingleMessageLoadedListener {
        void onSingleMessageLoaded(Object value);
    }

    public interface OnMessagesSavedListener {
        void onMessagesSaved();
    }
}
