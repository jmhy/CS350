
public class AlarmClockRadio extends AlarmClock {
	protected Radio radio;
	
	public AlarmClockRadio(String inTimeStr, String inAlarmTimeStr) {
		super(inTimeStr, inAlarmTimeStr);
		RadioStation rs = new RadioStation("WMMR", 93.3, RadioStation.Modulation.FM);
		Radio clkRadio = new Radio(rs);
		this.radio = clkRadio;
	}
	
	@Override
	void displayClockInfo() {
		System.out.println("Clock Time: " + this.getClockTimeStr());
		System.out.println("Alarm set for: " + this.getAlarmStr());
		System.out.println(radio.toString());
	}
	
	public RadioStation getCurrentStation() {
		return this.radio.getCurrentStation();
	}
	
	public void setCurrentStation(String callSign, double freq, RadioStation.Modulation mod) {
		RadioStation rs = new RadioStation(callSign, freq, mod);
		this.radio.setCurrentStation(rs);
	}
	
	public int getVolume() {
		return this.radio.getVolume();
	}
	
	public void setVolume(int vol) {
		this.radio.setVolume(vol);
	}
	
	public void turnRadioOn() {
		this.radio.turnOn();
	}
	
	public void turnRadioOff() {
		this.radio.turnOff();
	}

}
