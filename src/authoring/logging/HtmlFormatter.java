package authoring.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * 
 * @author Collin Brown(cdb55)
 *
 */
public class HtmlFormatter extends Formatter{
	private static final String DEFAULT_OPEN_TAG = "\t<td>";
	private static final String DEFAULT_CLOSE_TAG = "</td>\\n";
	private static final int STRING_BUFFER_LENGTH = 100;

	@Override
	public String format(LogRecord record) {
		StringBuffer stringBuffer = new StringBuffer(STRING_BUFFER_LENGTH);
		stringBuffer.append("<tr>\n");

		if(record.getLevel().intValue() >= Level.WARNING.intValue()) {
			stringBuffer.append("\t<td style=\"color:red\">");
			stringBuffer.append("<b>" + record.getLevel() + "<b>");
		} else {
			stringBuffer.append(DEFAULT_OPEN_TAG + record.getLevel());
		}
		stringBuffer.append(DEFAULT_CLOSE_TAG);
		stringBuffer.append(DEFAULT_OPEN_TAG);
		stringBuffer.append(getDate(record.getMillis()));
		stringBuffer.append(DEFAULT_CLOSE_TAG);
		stringBuffer.append(DEFAULT_OPEN_TAG);
		stringBuffer.append(formatMessage(record));
		stringBuffer.append(DEFAULT_CLOSE_TAG);
		stringBuffer.append("</tr>\n");

		return stringBuffer.toString();
	}

	/**
	 * Convert millisecs to Date
	 * @param millisecs
	 * @return String date
	 */
	private String getDate(long millisecs) {
		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	public String getHead(Handler h) {
		return "<!DOCTYPE html>\n<head>\n<style>\n"
				+ "table { width: 100% }\n"
				+ "th { font:bold 10pt Tahoma; }\n"
				+ "td { font:normal 10pt Tahoma; }\n"
				+ "h1 {font:normal 11pt Tahoma;}\n"
				+ "</style>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<h1>" + (new Date()) + "</h1>\n"
				+ "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\">\n"
				+ "<tr align=\"left\">\n"
				+ "\t<th style=\"width:10%\">Loglevel</th>\n"
				+ "\t<th style=\"width:15%\">Time</th>\n"
				+ "\t<th style=\"width:75%\">Log Message</th>\n"
				+ "</tr>\n";
	}

	public String getTail(Handler h) {
		return "</table>\n</body>\n</html>";
	}

}
