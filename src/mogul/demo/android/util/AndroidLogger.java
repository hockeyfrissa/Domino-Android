package mogul.demo.android.util;

import org.apache.commons.logging.Log;

public class AndroidLogger implements Log {

	private String name;
	
	public AndroidLogger(Class<?> clazz) {
		this.name = clazz.getName();
	}
	
	public void debug(Object arg0) {
		android.util.Log.d(name, arg0.toString());
		
	}

	public void debug(Object arg0, Throwable arg1) {

		
	}

	public void error(Object arg0) {

		
	}

	public void error(Object arg0, Throwable arg1) {
		android.util.Log.e(name, "", arg1);
	}

	public void fatal(Object arg0) {

		
	}

	public void fatal(Object arg0, Throwable arg1) {

		
	}

	public void info(Object arg0) {

		
	}

	public void info(Object arg0, Throwable arg1) {

		
	}

	public boolean isDebugEnabled() {

		return false;
	}

	public boolean isErrorEnabled() {
		return false;
	}

	public boolean isFatalEnabled() {
		return false;
	}

	public boolean isInfoEnabled() {
		return false;
	}

	public boolean isTraceEnabled() {
		return false;
	}

	public boolean isWarnEnabled() {
		return false;
	}

	public void trace(Object arg0) {
		
	}

	public void trace(Object arg0, Throwable arg1) {
	}

	public void warn(Object arg0) {
		
	}

	public void warn(Object arg0, Throwable arg1) {
	}

}
