package surveyTest;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//A concrete console input, console-audio output implementation for interaction with the survey system
public class ConsoleAudioSurveyIO extends AbstractSurveyIO {
	private BufferedReader br;
	private String voiceName;
	private VoiceManager voiceManager;
	private Voice voice;
	
	public ConsoleAudioSurveyIO() {
		this.br = new BufferedReader(new InputStreamReader(System.in));
		this.voiceName = "kevin16";
		this.voiceManager = VoiceManager.getInstance();
		this.voice = voiceManager.getVoice(voiceName);
		this.voice.allocate();
	}

	//Print string to console and read to audio
	@Override
	public void display(String s) {
		System.out.println(s);	
        this.voice.speak(s);
	}

	//This must be able to throw an IOException in order to use BufferedReader
	@Override
	public String getUserInput() throws IOException {
		String in = this.br.readLine();
		return in;
	}

}
