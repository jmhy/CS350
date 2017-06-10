
public class Radio {
	private RadioStation currentStation;
	private boolean radioOn;
	private int volume = 0;
	
	public Radio(RadioStation st) {
		this.setCurrentStation(st);
		this.radioOn = true;
	}

	public RadioStation getCurrentStation() {
		return currentStation;
	}

	public void setCurrentStation(RadioStation currentStation) {
		this.currentStation = currentStation;
	}
	
	public boolean isRadioOn() {
		return this.radioOn;
	}
	
	public void turnOn() {
		this.radioOn = true;
		System.out.println("Radio turned on");
	}
	
	public void turnOff() {
		this.radioOn = false;
		System.out.println("Radio turned off");
	}
	
	public int getVolume() {
		return this.volume;
	}
	
	public void setVolume(int vol) {
		if (vol >= 0 && vol <= 10) {
			this.volume = vol;
		} else {
			System.out.println("Volume setting out of range of 0-10, remains " + this.getVolume());
		}
	}
	
	@Override
	public String toString() {
		if (isRadioOn()) {
			return "Radio is on, playing " + this.currentStation.toString() + " at volume: " + this.getVolume();
		} else {
			return "Radio is off, set to " + this.currentStation.toString() + " at volume: " + this.getVolume();
		}
	}
	
}
