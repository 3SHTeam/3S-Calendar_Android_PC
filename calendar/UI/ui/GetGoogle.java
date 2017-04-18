package ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.Organizer;
import com.google.api.services.calendar.model.Events;

import Data.EventData;

public class GetGoogle {
	private static String eventId;// eventId
	private static String scheduleName;// title
	private static String location;// location
	private static Organizer SMaster;//주최자
	private static String date;// 시작날짜
	private static String startTime;// 시작시간
	private static String endTime; // 끝나는 시간

	/** Application name. */
	private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.dir"),
			".credentials/calendar-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/calendar-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = GetGoogle.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Calendar client service.
	 * 
	 * @return an authorized Calendar client service
	 * @throws IOException
	 */
	public static com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
		Credential credential = authorize();
		return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}


	public static void main(String[] args) throws IOException {
		com.google.api.services.calendar.Calendar service = getCalendarService();

		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary")
				.setMaxResults(250)
				.setTimeMin(now)
				.setOrderBy("startTime")
				.setSingleEvents(true)
				.execute();

		List<Event> items = events.getItems();
		if (items.size() == 0) {
			System.out.println("No upcoming events found.");
		} else {
			System.out.println("Upcoming events");
			for (Event event : items) {
				DateTime startInfo = event.getStart().getDateTime();			// 시작날짜와시간
				DateTime endInfo = event.getEnd().getDateTime();				// 끝난 날짜와 시간

				if (startInfo == null) {
					startInfo = event.getStart().getDate();
				}
				if (endInfo == null) {
					endInfo = event.getStart().getDate();
				}
				eventId = event.getId();										// eventId
				scheduleName = event.getSummary();								// title
				location = event.getLocation();									// location

				// 날짜와 시간 형식 변경
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
				date = dateFormat.format(startInfo.getValue()); 				// 시작날짜
				startTime = timeFormat.format(startInfo.getValue()); 			// 시작시간
				endTime = timeFormat.format(endInfo.getValue()); 				// 끝나는시간

				
				//구글에서 받아온 정보들 ScheduleData에 저장
				EventData eventData = new EventData();

				eventData.setData(0,eventId);
				eventData.setData(1,scheduleName);
				//eventData.setData(2,SMaster);//주최자?
				eventData.setData(3,location);
				eventData.setData(4,startTime);
				eventData.setData(5,endTime);
			
				//아직 db에 연결이 안되어있으므로
				
				System.out.println("[이벤트id]:" + eventId + " [스케줄명]:" + scheduleName + " [장소]:" + location 
						 + " [날짜]:" + date + " [시작시간]:" + startTime + " [끝나는 시간]:" + endTime);
			}
		}

	}

}