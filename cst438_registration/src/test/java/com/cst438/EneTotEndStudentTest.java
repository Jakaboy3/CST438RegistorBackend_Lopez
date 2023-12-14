package com.cst438;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class EndToEndStudentTest {
	public static final String CHROME_DRIVER_FILE_LOCATION =
            "C:/chromedriver_win32/chromedriver.exe";
	public static final String URL = "http://localhost:3000";
	public static final String ALIAS_NAME = "test";
	public static final int SLEEP_DURATION = 1000;
	WebDriver driver;
	
	@BeforeEach
	public void testSetup() throws Exception {
             // if you are not using Chrome, 
             // the following lines will be different. 
		System.setProperty(
                 "webdriver.chrome.driver", 
                 CHROME_DRIVER_FILE_LOCATION);
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(ops);


		driver.get(URL);
        // must have a short wait to allow time for the page to download 
		Thread.sleep(SLEEP_DURATION);
	}

	@Test
	public void addStudent() throws Exception{
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver drive = new ChromeDriver();
		
		drive.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		try {
			drive.get(URL);
			Thread.sleep(SLEEP_DURATION);
			List<WebElement> we = drive.findElements(By.xpath("//input"));
			
			we.get(2).click();
			drive.findElement(By.id("viewStudent")).click();
			Thread.sleep(SLEEP_DURATION);
			
			drive.findElement(By.id("StudentId")).sendKeys("12345");
			drive.findElement(By.id("add")).click();
			Thread.sleep(SLEEP_DURATION);
			
			drive.findElement(By.id("addStudents")).click();
			Thread.sleep(SLEEP_DURATION);
			
//			WebElement web = drive.findElement(By.xpath("//tr{td=" + drive.getStudent_id));
//			assertNotNull(web, "student id not found");
//			WebElement drop = web.findElement(By.xpath("//button"));
//			assertNotNull(drop);
//			drop.click();
			
			WebDriverWait look = new WebDriverWait(drive, 1);
			look.until(ExpectedConditions.alertIsPresent());
			Alert alert = drive.switchTo().alert();
			alert.accept();
			Thread.sleep(SLEEP_DURATION);
//			assertThrows(NoSuchElementException.class,()->{
//				drive.findElement(By.xpath("//tr[td=") + TEST_STUDENT_ID + "]");
//			});
		}catch(Exception ex) {
			throw ex;
		}finally {
			drive.quit();
		}
	}
	
	@Test
	public void deleteStudet() throws Exception{
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver drive = new ChromeDriver();
		
		drive.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		try {
			drive.get(URL);
			Thread.sleep(SLEEP_DURATION);
			List<WebElement> we = drive.findElements(By.xpath("//input"));
			we.get(2).click();
			
			drive.findElement(By.id("viewStudent")).click();
			Thread.sleep(SLEEP_DURATION);
			WebElement web = drive.findElement(By.xpath("//button[text()='Delete']"));
//			asserNotNull(web);
			web.click();
			WebDriverWait look = new WebDriverWait(drive, 1);
			look.until(ExpectedConditions.alertIsPresent());
			Alert alert = drive.switchTo().alert();
			alert.accept();
			Thread.sleep(SLEEP_DURATION);
//			assertThrows(NoSuchElementException.class,()->{
//				drive.findElement(By.xpath("//tr[td=") + TEST_STUDENT_ID + "]");
//			});
		}catch(Exception ex) {
			throw ex;
		}finally {
			drive.quit();
		}
			
		
	}
	@Test
	public void updateStudent() throws Exception{
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver drive = new ChromeDriver();
		drive.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		try {
			drive.get(URL);
			Thread.sleep(SLEEP_DURATION);
			List<WebElement> we = drive.findElements(By.xpath("//input"));
			we.get(2).click();
			
			drive.findElement(By.id("viewStudent")).click();
			Thread.sleep(SLEEP_DURATION);
//			WebElement web = drive.findElement(By.xpath("//button[text()-'EDIT']")).clear();
//			assertNotNull(web);
//			web.click();
			drive.findElement(By.id("studentName")).clear();
			drive.findElement(By.id("studentName")).sendKeys("updated Name");
			drive.findElement(By.id("studentEmail")).clear();
			drive.findElement(By.id("studentEmail")).sendKeys("udate Email");
			
			drive.findElement(By.id("")).click();
			Thread.sleep(SLEEP_DURATION);
			
		}catch(Exception exception) {
			throw exception;
		}finally {
			drive.quit();
		}
	}
}
