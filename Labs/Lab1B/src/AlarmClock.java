import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmClock extends AbstractClock {
	protected Date alarm;
	protected Date snoozeTime;
	protected boolean isAlarmOn;
	
	public AlarmClock(String inTimeStr, String inAlarmTimeStr) {
		super(inTimeStr);
		this.setAlarm(inAlarmTimeStr);
		this.enableAlarm();
	}

	@Override
	void displayClockInfo() {
		System.out.println("Clock Time: " + this.getClockTimeStr());
		System.out.println("Alarm set for: " + this.getAlarmStr());
	}
	
	public Date getAlarm() {
		return this.alarm;
	}
	
	public String getAlarmStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		return sdf.format(this.alarm);
	}

	public void setAlarm(String inAlarmTimeStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		try {
			this.alarm = sdf.parse(inAlarmTimeStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void enableAlarm() {
		this.isAlarmOn = true;
	}
	
	public void disableAlarm() {
		this.isAlarmOn = false;
		this.snoozeTime = null;
		System.out.println("The alarm was shut off");
	}
	
	protected boolean compareTime(Date d1, Date d2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		int hour1, minute1, second1, hour2, minute2, second2;
		hour1 = cal1.get(Calendar.HOUR_OF_DAY);
		minute1 = cal1.get(Calendar.MINUTE);
		second1 = cal1.get(Calendar.SECOND);
		hour2 = cal2.get(Calendar.HOUR_OF_DAY);
		minute2 = cal2.get(Calendar.MINUTE);
		second2 = cal2.get(Calendar.SECOND);
		return ((hour1==hour2)&&(minute1==minute2)&&(second1==second2));
	}
	
	public void checkAlarm() {
		if (this.isAlarmOn && compareTime(getClockTime(), getAlarm())) {
			System.out.println("Buzz Buzz Buzz");
		}
		else if (this.isAlarmOn && (this.snoozeTime != null) && compareTime(getClockTime(), this.snoozeTime)) {
			System.out.println("Buzz Buzz Buzz");
		}
	}
	
	public void snooze() {
		System.out.println("Snooze was pressed");
		Calendar cal = Calendar.getInstance();
		if (this.snoozeTime == null) {
			cal.setTime(this.getAlarm());
		} else {
			cal.setTime(this.snoozeTime);
		}
		cal.add(Calendar.MINUTE, 9);
		this.snoozeTime = cal.getTime();
	}

}
