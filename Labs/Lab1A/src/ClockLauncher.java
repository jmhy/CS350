
public class ClockLauncher {

	public static void main(String[] args) {
		int minutesToRun = 5;
		AlarmClockRadio acr = new AlarmClockRadio("08:00 AM", "08:05 AM");
		acr.setVolume(5);
		acr.displayClockInfo();
		
		for (int minutes = 0; minutes <= minutesToRun; minutes++) {
			System.out.println(acr.getClockTimeStr());
			
			for (int seconds = 0; seconds < 60; seconds++){
				acr.checkAlarm();
				acr.tick();
			}
		}
		
		acr.snooze();
		
		minutesToRun = 9;
		for (int minutes = 0; minutes < minutesToRun; minutes++) {
			System.out.println(acr.getClockTimeStr());
			
			for (int seconds = 0; seconds < 60; seconds++){
				acr.checkAlarm();
				acr.tick();
			}
		}
		
		acr.disableAlarm();
		
		acr.setCurrentStation("WFIL", 560, RadioStation.Modulation.AM);
		acr.setVolume(11);
		acr.turnRadioOff();
		acr.displayClockInfo();
	}

}
