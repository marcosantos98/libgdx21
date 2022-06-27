package marco.ffloat.gwt;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import marco.ffloat.Float;

/** Launches the GWT application. */
public class GwtLauncher extends GwtApplication {
		@Override
		public GwtApplicationConfiguration getConfig () {
			// Resizable application, uses available space in browser with no padding:
			GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(true);
			cfg.padVertical = 100;
			cfg.padHorizontal = 100;
			//return cfg;
			// If you want a fixed size application, comment out the above resizable section,
			// and uncomment below:
			return new GwtApplicationConfiguration(1280, 720);
		}

		@Override
		public ApplicationListener createApplicationListener () {
			return new Float();
		}
}
