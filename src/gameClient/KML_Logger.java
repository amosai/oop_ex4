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
		ff.setHref("https://cdn1.imggmi.com/uploads/2020/2/10/cb2c8089d3c7260ac6807345ed82afdd-full.png");
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

		ff.setHref("https://cdn1.imggmi.com/uploads/2020/2/10/2e63455a4a53f1633ef645ca1b19658c-full.png");
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