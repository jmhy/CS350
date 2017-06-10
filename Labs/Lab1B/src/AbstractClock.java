import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class AbstractClock {
	protected Date clockTime;
	
	public AbstractClock(String inTimeStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		try {
			this.setClockTime(sdf.parse(inTimeStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Date getClockTime() {
		return this.clockTime;
	}
	
	public String getClockTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		return sdf.format(this.clockTime);
	}

	public void setClockTime(Date clockTime) {
		this.clockTime = clockTime;
	}
	
	public void tick() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.clockTime);
		cal.add(Calendar.SECOND, 1);
		this.setClockTime(cal.getTime());
	}
	
	abstract void displayClockInfo();
}
