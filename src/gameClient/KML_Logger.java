package gameClient;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Server.Fruit;
import Server.RobotG;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.IconStyle;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.TimeSpan;



public class KML_Logger 
{
	Kml k;
	Document doc;
	int i;
	int l;	

	//******CONSTRUCTOR*************************	
	public KML_Logger() 
	{
		k = new Kml ();
		doc = k.createAndSetDocument();
		i=0;
		l=0;		
	}


	/**
	 * Creat Placemark of the fruit and update the kml file
	 * @param f
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public synchronized void SetFruit(double x, double y) throws ParseException, InterruptedException
	{
		Placemark placmark = doc.createAndAddPlacemark();
		Icon ff = new Icon();
		ff.setHref("https://cdn3.iconfinder.com/data/icons/fruits-52/150/icon_fruit_maca-512.png");
		ff.setViewBoundScale(1);
		ff.setViewRefreshTime(1);
		ff.withRefreshInterval(1);
		IconStyle pp = new IconStyle();
		pp.setScale(1);
		pp.setHeading(1);
		pp.setColor("ff007db3");
		pp.setIcon(ff);
		placmark.createAndAddStyle().setIconStyle(pp);
		placmark.withDescription("MAC: " + "\nType: fruit").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(x,y);
		String time1 = MillisToString(StringToMillis(TimeNow()) + i * 1000);
		String time2 = MillisToString(StringToMillis(TimeNow()) + (i + 1) * 1000);
		String[] aa = time1.split(" ");
		time1 = aa[0] + "T" + aa[1] + "Z";
		String[] bb = time2.split(" ");
		time2 = bb[0] + "T" + bb[1] + "Z";
		TimeSpan b = placmark.createAndSetTimeSpan();
		b.setBegin(time1);
		b.setEnd(time2);
	}

	
	
	public synchronized void SetRobot(double x, double y) throws ParseException, InterruptedException
	{
		Placemark plmark = doc.createAndAddPlacemark();
		Icon ff = new Icon();

		ff.setHref("data:image/jpeg;base64,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");
		ff.setViewBoundScale(1);
		ff.setViewRefreshTime(1);
		ff.withRefreshInterval(1);
		IconStyle pp = new IconStyle();
		pp.setScale(1);
		pp.setHeading(1);
		pp.setColor("ff007db3");
		pp.setIcon(ff);
		plmark.createAndAddStyle().setIconStyle(pp);
		plmark.withDescription("Mac: " + "\nType: Robot").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(x,y);
		l++;
		String time1 = MillisToString(StringToMillis(TimeNow()) + i * 1000);
		String time2 = MillisToString(StringToMillis(TimeNow()) + (i + 1) * 1000);
		String[] aa = time1.split(" ");
		time1 = aa[0] + "T" + aa[1] + "Z";
		String[] bb = time2.split(" ");
		time2 = bb[0] + "T" + bb[1] + "Z";
		TimeSpan a = plmark.createAndSetTimeSpan();
		a.setBegin(time1);
		a.setEnd(time2);

	}
	
	
	
	
	
	
	
	
	

	/**
	 * Generate the collected data to kml file.
	 */
	public void CreatFile(String filename) 
	{
		try {
			String file=filename+".kml";
			k.marshal(new File(file));
			System.out.println("file "+file+" was created");
		}
		catch (Exception e)
		{
			System.err.println("Failed while creating kml file "+e.getMessage());
		}
	}


	//***********Helper Function*******************
	private String MillisToString(Long millis)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(millis));
	}


	private long StringToMillis(String TimeAsString) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		Date date = format.parse(TimeAsString.toString());
		long millis = date.getTime();
		return millis;
	}

	private String TimeNow()
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}
}