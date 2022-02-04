package com.nihanth.animeflixdownloader;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nihanth.animeflixdownloader.models.EpisodeInfo;

@Service
public class EpisodeLinkGrabber {

	private WebDriver driver;
	private FluentWait wait;
	@Value("${msedgedriver}")
	private String webDriverPath;

	@PostConstruct
	public void OnInit() {
		System.out.println("Created Edge Driver");
		System.setProperty("webdriver.edge.driver", webDriverPath);
		driver = new EdgeDriver();
		wait = new FluentWait(driver);
		System.out.println("Minimizing Browser Window");
		driver.manage().window().setPosition(new Point(0, -1000));
	}

	public List<EpisodeInfo> getEpisodes(String animeflixUrl, int minEpisode) {
		List<EpisodeInfo> episodes = new ArrayList<EpisodeInfo>();
		driver.get(animeflixUrl);
		driver.manage().window().maximize();
		List<WebElement> elements = driver.findElements(By.className("epi-me"));
		for (WebElement element : elements) {
			try {
				WebElement episode = element.findElement(By.tagName("a"));
				String episodeUrl = episode.getAttribute("href");
				WebElement episodeDiv = episode.findElement(By.tagName("div"));
				String title = episodeDiv.getAttribute("title").trim().replace("-", "");
				int episodeNumber = Integer.parseInt(title.split(" ")[1]);
				if (episodeNumber >= minEpisode) {
					EpisodeInfo e = new EpisodeInfo(episodeNumber, title, episodeUrl);
					episodes.add(e);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		episodes.sort(new EpisodeNumberComparator());
		return episodes;
	}

	@PreDestroy
	public void Destroy() {
		driver.close();
	}
}

class EpisodeNumberComparator implements Comparator<EpisodeInfo> {
	@Override
	public int compare(EpisodeInfo e1, EpisodeInfo e2) {
		if (e1.getEpisodeNumber() == e2.getEpisodeNumber())
			return 0;
		else if (e1.getEpisodeNumber() > e2.getEpisodeNumber())
			return 1;
		else
			return -1;
	}
}
