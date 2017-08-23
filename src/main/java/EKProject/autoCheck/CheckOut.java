package EKProject.autoCheck;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

/**
 * auto Check Out
 * 
 * @author Eric.Kuan
 *
 */
public class CheckOut {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		System.out.println(System.getProperty("user.dir"));

		Properties p = new Properties();

		FileInputStream input = null;
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "/conf/config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			p.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		String authCode = p.getProperty("HYTECH_AUTH_CODE");
		WebClient webClient = new WebClient();

		Cookie ASPXAUTH = new Cookie("www.hy-tech.com.tw", ".ASPXAUTH", authCode);
		CookieManager cookieManager = webClient.getCookieManager();
		cookieManager.addCookie(ASPXAUTH);

		HtmlPage page = webClient.getPage("http://www.hy-tech.com.tw/attendance/Default.aspx");
		System.out.println(page.asXml());

		HtmlForm form = page.getForms().get(0);
		HtmlSubmitInput button = form.getInputByName("ctl00$MainContent$btnClockout");
		button.click();

		webClient.close();
	}
}
