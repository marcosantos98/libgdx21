package marco.ffloat.event;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;

public class EventManager {

    private final ObjectMap<Class<? extends Event>, ObjectSet<IEventListener>> bus = new ObjectMap<>();

    public static final String TAG = EventManager.class.getSimpleName();

    public void addListener(Class<? extends Event> eventClass, IEventListener listener) {
        ObjectSet<IEventListener> eventListeners = bus.get(eventClass);
        if (eventListeners == null) {
            eventListeners = new ObjectSet<>(8);
            bus.put(eventClass, eventListeners);
        }

        eventListeners.add(listener);
    }

    public void sendEvent(Event event) {
        ObjectSet<IEventListener> eventListeners = bus.get(event.getClass());
        if (eventListeners != null) {
            eventListeners.forEach(eventListener -> eventListener.onEvent(event));
        }
    }
}