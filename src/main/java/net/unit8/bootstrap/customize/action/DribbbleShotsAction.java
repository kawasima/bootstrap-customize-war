package net.unit8.bootstrap.customize.action;

import com.opensymphony.xwork2.ActionSupport;
import net.arnx.jsonic.JSON;
import net.unit8.bootstrap.customize.dto.DribbbleColorDto;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
@Namespace("/dribbble")
public class DribbbleShotsAction extends ActionSupport {
	private String url;
	private InputStream inputStream;

	@Action(value = "colors", results = {
			@Result(name = "success", type="stream", params={"content-type", "application/json"})
	})
	public String execute() {
		try {
			List<DribbbleColorDto> colors = colors();
			setInputStream(new ByteArrayInputStream(JSON.encode(colors).getBytes("UTF-8")));
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}

	protected List<DribbbleColorDto> colors() throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements colorAnchors = doc.select("ul.color-chips li.color a");
		List<DribbbleColorDto> colors = new ArrayList<DribbbleColorDto>();
		for (Element colorAnchor : colorAnchors) {
			DribbbleColorDto color = new DribbbleColorDto();
			color.setCode(colorAnchor.attr("title"));
			colors.add(color);
		}
		return colors;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
