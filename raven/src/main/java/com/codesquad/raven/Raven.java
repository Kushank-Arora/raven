package com.codesquad.raven;

import android.content.Context;

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
public class Raven {

    /**
     * It deletes all the earlier communication data stored in DB.
     * <p>
     * This should be done only once when the app starts.
     */
    public static void initApplication() {

    }

    /**
     * This function is used to instance Raven for the current Activity/Fragment.
     * <p>
     * It deletes all previous communication it had made to other Activity/Fragment.
     * It also returns a map<String, Object> representing the data passed to this Activity/Fragment.
     *
     * @param context Context in which the data is to be retrieved.
     * @return Data passed to this Activity/Fragment.
     */
    public static Map<String, Object> instantiate(Context context) {
        return instantiate(context.getClass());
    }

    /**
     * This function is used to instance Raven for the current Activity/Fragment.
     * <p>
     * It deletes all previous communication it had made to other Activity/Fragment.
     * It also returns a map<String, Object> representing the data passed to this Activity/Fragment.
     *
     * @param thisClass Class to whose intended data is to be gathered.
     * @return Data passed to this Activity/Fragment.
     */
    public static Map<String, Object> instantiate(Class thisClass) {
        return null;
    }

    /**
     * It returns a map<String, Object> representing the data passed to this Activity/Fragment.
     *
     * @param context Context in which the data is to be retrieved.
     * @return Data passed to this Activity/Fragment.
     */
    public static Map<String, Object> getValues(Context context) {
        return getValues(context.getClass());
    }

    /**
     * It returns a map<String, Object> representing the data passed to this Activity/Fragment.
     *
     * @param thisClass Class to whose intended data is to be gathered.
     * @return Data passed to this Activity/Fragment.
     */
    public static Map<String, Object> getValues(Class thisClass) {
        return null;
    }

    /**
     * It returns the object mapped with `key` passed to this Activity/Fragment.
     *
     * @param context Context in which the data is to be retrieved.
     * @param key     Key with which the intended data was mapped.
     * @return value corresponding to that key, or null otherwise.
     */
    public static Object getValue(Context context, String key) {
        return getValue(context.getClass(), key);
    }

    /**
     * It returns the object mapped with `key` passed to this Activity / Fragment.
     *
     * @param thisClass Class to whose intended data is to be gathered.
     * @param key       Key with which the intended data was mapped.
     * @return value corresponding to that key, or null otherwise.
     */
    public static Object getValue(Class thisClass, String key) {
        return null;
    }

    /**
     * It deletes all previous communication it had made to other Activity/Fragment.
     *
     * @param thisClass Class to whose intended data is to be gathered.
     */
    public void startFreshInstance(Class thisClass) {

    }

    /**
     * This deletes all data intended to this Activity/Fragment.
     */
    public void cleanup(Class thisClass) {

    }

    /**
     * This deletes every communication data left in the app, related to every Activity/Fragment.
     */
    public void hardCleanup() {

    }

}
