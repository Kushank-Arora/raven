# Raven
A library for easy and advanced communication between Activities and Fragments.

This library can be used to send data in the form of messages from one activity to another activity or fragment, or from one fragment to another activity or fragment. Thus it allows easy to use communication mechanism for communicating a large amount of data.

It internally uses the database for preserving the state, if the activity is killed while being in the background.

## Need
As compared to the conventional way of sending data using intents, which allows a practical limit of much less than 1 MB, Raven can send data to nearly 2 MB, which will be tried to be converted to infinity(i.e., the amount of data the storage can hold) in later versions.

## Setup Raven
To setup Raven,

Step 1: Add it to your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2: Add the dependency in the app level build.gradle:
```
dependencies {
    implementation 'com.github.Kushank-Arora:raven:0.0.1'
}
```

## Usage

### Initialise
Firstly you need to initialize Raven for the whole application.
This is an expensive operation, thus it should be done once, i.e., in Application Class.

```
Raven.init(this);
```

### Send Data
Now, you can easily send data to another Activity or Fragment using the following code:


For the Activity: 
```
Raven.startCommunication(context_of_sending_activity, DestinationActivityOrFragment.class)
  .add(KEY_TO_DATA_1, any_valid_pojo)
  .add(KEY_TO_DATA_2, any_Valid_pojo_2)
  .save(new Raven.OnMessagesSavedListener() {
    @Override
    public void onMessagesSaved() {
      /*
        do anything here like: 
        startActivity(new Intent(SendingActivity.this, DestinationActivityOrFragment.class));
      */
    }
  });
```

For the Fragment: 
```
Raven.startCommunication(SendingFragment.class, DestinationActivityOrFragment.class)
  .add(KEY_TO_DATA_1, any_valid_pojo)
  .add(KEY_TO_DATA_2, any_Valid_pojo_2)
  .save(new Raven.OnMessagesSavedListener() {
    @Override
    public void onMessagesSaved() {
      /*
        do anything here like: 
        startActivity(new Intent(getContext(), DestinationActivityOrFragment.class));
      */
    }
  });
```

### Receive Data

To receive all the messages for the current activity:
```
Raven.getValues(this, new Raven.OnMessagesLoadedListener() {
  @Override
  public void onMessagesLoaded(Map<String, Object> messages) {
    DestinationType1 data1 = (DestinationType1) messages.get(KEY_TO_DATA_1);
    DestinationType2 data2 = (DestinationType2) messages.get(KEY_TO_DATA_2);
    /*
      Do anything with this data here.
    */
  }
});
```

To receive all the messages for the current fragment/activity:
```
Raven.getValues(CurrentFragmentOrCurrentActivity.class, new Raven.OnMessagesLoadedListener() {
  @Override
  public void onMessagesLoaded(Map<String, Object> messages) {
    DestinationType1 data1 = (DestinationType1) messages.get(KEY_TO_DATA_1);
    DestinationType2 data2 = (DestinationType2) messages.get(KEY_TO_DATA_2);
    /*
      Do anything with this data here.
    */
  }
});
```


To receive single data element in activity:
```
Raven.getValue(this, KEY_TO_DATA_1, new Raven.OnSingleMessageLoadedListener() {
  @Override
  public void onSingleMessageLoaded(Object value) {
    DestinationType destinationObject = (DestinationType) value;
    /*
      Do anything with this data here.
    */
  }
});
```

To receive single data element in fragment/activity:
```
Raven.getValue(CurrentFragmentOrCurrentActivity.class, KEY_TO_DATA_1, new Raven.OnSingleMessageLoadedListener() {
  @Override
  public void onSingleMessageLoaded(Object value) {
    DestinationType destinationObject = (DestinationType) value;
    /*
      Do anything with this data here.
    */
  }
});
```

## Things to remember:

### How to clean up all communication previously done in the app

Cleanup should be done regularly to delete the inbox for all activities/fragments.
It should be done preferably at the time of app launch, every time.

```
Raven.hardCleanup(new Raven.OnMessagesDeletedListener() {
  @Override
  public void onMessagesDeleted() {
    /*
      do anything here.
    */
  }
}); 
```

### When to necessarily delete messages.
Since for persistence, Raven doesn't empty the inbox for an Activity/Fragment, unless someone sends a message, thus it is your responsibility to delete the messages for an activity, if you are not sending the message to the activity/fragment who is expecting some message.
It could be done by:
```
Raven.deletePrevCommunication(this, new Raven.OnMessagesDeletedListener() {
  @Override
  public void onMessagesDeleted() {
    /*
      do anything here.
    */
  }
}); 
```

### What not to do in listeners.
All listeners in Raven run on a different thread, i.e., not on the main thread.
Thus remember not to use functions which needs to be called in UI thread, like, handling views and their contents.
To do tasks related to views, you can instead do :
```
@Override
public void anyRavenListener() {
  runOnUiThread(new Runnable() {
    @Override
    public void run() {
      textView.setText("some_text");
      /*
      other UI related tasks.
      */
    }
  });
  /*
    do anything here, not related to UI.
  */
}
```

### How to explicitly delete messages which were intended to some Activity/Fragment
```
Raven.cleanup(IntendedActivityOrFragment.class, new Raven.OnMessagesDeletedListener() {
  @Override
  public void onMessagesDeleted() {
    /*
      do anything here.
    */
  }
}); 
```
