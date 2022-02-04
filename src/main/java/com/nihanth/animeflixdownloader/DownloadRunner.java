package com.nihanth.animeflixdownloader;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nihanth.animeflixdownloader.models.EpisodeInfo;

@Component
public class DownloadRunner implements CommandLineRunner {

	@Autowired
	private EpisodeLinkGrabber grabber;

	@Value("${msedgedriver}")
	private String webDriverPath;

	@Override
	public void run(String... args) throws Exception {
		String animeflixUrl = null;

//		System.out.print("Please input AnimeFles URL: ");
//		try (Scanner scanner = new Scanner(System.in)) {
//			animeflixUrl = scanner.nextLine();
//			System.out.println(animeflixUrl);
//		}

		animeflixUrl = "https://animeflix.sbs/anime/one-piece";

		List<EpisodeInfo> episodes = grabber.getEpisodes(animeflixUrl, 747);

		System.out.println("Completed getting episode info. Total Episodes Found: " + episodes.size());

		EpisodeDownloader ed = new EpisodeDownloader(webDriverPath);
		for (EpisodeInfo e : episodes) {
			String link = ed.getDownloadUrl(e);
			if (link != null)
				e.setDownloadLink(link);
		}
		ed = null;
		FileOutputStream fileOut = new FileOutputStream("data.bin");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(episodes);
        objectOut.close();
	}
}