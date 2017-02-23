package ser.main;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("unused")
public class Sound {
	URL url1 = Sound.class.getResource("/laserSound.wav");
	
	URL url2 = Sound.class.getResource("/boom.wav");
	
	URL url3 = Sound.class.getResource("/ad.wav");
	
	URL url4 = Sound.class.getResource("/ad.wav");
	
	
	AudioClip gun = Applet.newAudioClip(url1);
	
	AudioClip boom = Applet.newAudioClip(url2);
	
	AudioClip menu = Applet.newAudioClip(url3);
	
	AudioClip game = Applet.newAudioClip(url4);
	
	public void playGunSound()
	{
		gun.play();
	}
	public void stopGunSound()
	{
		gun.stop();
	}
	
	public void playExplosion(){
		boom.play();
	}
	public void stopExplosion(){
		boom.stop();
	}	
	public void playMenu(){
		menu.play();
	}
	public void stopMenu(){
		menu.stop();
	}
	public void playGame(){
		game.play();
	}
	public void stopGame(){
		game.stop();
	}
	

}
