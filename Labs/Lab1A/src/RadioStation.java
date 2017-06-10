
public class RadioStation {
	public String callSign;
	public double freq;
	public Modulation mod;
	
	public RadioStation(String inCallSign, double inFreq, Modulation inMod) {
		this.callSign = inCallSign;
		this.freq = inFreq;
		this.mod = inMod;
	}
	
	@Override
	public String toString() {
		return this.callSign + " " + this.freq + " " + this.mod.name();
	}
	
	public enum Modulation {
		AM,
		FM
	}
}
