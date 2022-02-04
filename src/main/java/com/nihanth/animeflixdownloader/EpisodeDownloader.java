package com.nihanth.animeflixdownloader;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.nihanth.animeflixdownloader.models.EpisodeInfo;

public class EpisodeDownloader {

	private WebDriver driver;
	private FluentWait wait;

	public EpisodeDownloader(String webDriverPath) {
		System.out.println("Created Edge Driver");
		System.setProperty("webdriver.edge.driver", webDriverPath);
		driver = new EdgeDriver();
		wait = new FluentWait(driver);
		wait.pollingEvery(Duration.ofMillis(250));
		wait.withTimeout(Duration.ofSeconds(5));
		wait.ignoring(NoSuchElementException.class);
	}

	public String getDownloadUrl(EpisodeInfo e) {
		try {
			System.out.println("Downloaded " + e.getEpisodeName());
			driver.get(e.getEpisodeLink());
			wait.pollingEvery(Duration.ofSeconds(2));
			WebElement iframe = driver.findElement(By.id("iframe-to-load"));
			driver.get(iframe.getAttribute("src"));
			List<WebElement> servers = driver.findElements(By.className("item-server"));
			WebElement btnDownload = servers.stream().filter(p -> p.getAttribute("href").contains("gogo")).findFirst()
					.get();
			driver.get(btnDownload.getAttribute("href"));
			String downloadLink = null;
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("1080P")));
				downloadLink = driver.findElement(By.partialLinkText("1080P")).getAttribute("href");
			} catch (Exception e1) {
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("720P")));
					downloadLink = driver.findElement(By.partialLinkText("720P")).getAttribute("href");
				} catch (Exception e2) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("480P")));
					downloadLink = driver.findElement(By.partialLinkText("480P")).getAttribute("href");
				}
			}
			// driver.close();
			return downloadLink;
		} catch (Exception ex) {
			return null;
		}
	}

}
