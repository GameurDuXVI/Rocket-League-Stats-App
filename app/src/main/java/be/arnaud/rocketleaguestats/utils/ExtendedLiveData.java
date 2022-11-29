package be.arnaud.rocketleaguestats.utils;

import android.util.Pair;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * #source https://stackoverflow.com/questions/56609545/how-to-change-value-of-livedata-and-not-notify-the-observers
 *
 * @param <T>
 */
public class ExtendedLiveData<T> extends MutableLiveData<T> {

    private final List<Pair<LifecycleOwner, Observer<T>>> observers = new ArrayList<>();

    public ExtendedLiveData() {
    }

    public ExtendedLiveData(T value) {
        super(value);
    }

    @MainThread
    @Override
    @SuppressWarnings("unchecked")
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer observer) {
        observers.add(new Pair<LifecycleOwner, Observer<T>>(owner, observer));
    }

    @MainThread
    private void resetAllObservers() {
        for (Pair<LifecycleOwner, Observer<T>> observer : observers) {
            super.observe(observer.first, observer.second);
        }
    }

    @MainThread
    private void removeAllObservers() {
        for (Pair<LifecycleOwner, Observer<T>> observer : observers) {
            removeObservers(observer.first);
        }
    }

    @MainThread
    void setValueWithoutNotify(T value) {
        removeAllObservers();
        super.setValue(value);
    }

    @MainThread
    @Override
    public void removeObserver(@NonNull Observer<? super T> observer) {
        for (Pair<LifecycleOwner, Observer<T>> observerItem : observers) {
            if (observerItem.second.equals(observer) && observerItem.first.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                observers.remove(observerItem);
            }
        }
        super.removeObserver(observer);
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
        if (!hasObservers()) {
            resetAllObservers();
        }
    }
}
