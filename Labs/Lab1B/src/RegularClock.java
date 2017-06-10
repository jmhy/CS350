
public class RegularClock extends AbstractClock {

	public RegularClock(String inTimeStr) {
		super(inTimeStr);
	}

	@Override
	void displayClockInfo() {
		System.out.println("Clock Time: " + this.getClockTimeStr());
	}

}
