package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder (MethodSorters.JVM)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getHomePageUnauthorized() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}


	@Test
	public void testSignupNewUser(){
		//Write a test that signs up a new user, logs in, verifies that the home page is accessible,
		// logs out, and verifies that the home page is no longer accessible.
		doMockSignUp("Sarah", "Alkhalawi","Sarah123", "soso2001");
		doLogIn("Sarah123", "soso2001");
//		doMockSignUp(fname, lname,uname, pass);
//		doLogIn(uname, pass);
		Assertions.assertEquals("Home", driver.getTitle());

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Logoutbtn")));
		WebElement Logoutbtn = driver.findElement(By.id("Logoutbtn"));
		Logoutbtn.click();

		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void LoginHome(String fname,String lname,String uname, String pass){
		doMockSignUp(fname, lname,uname, pass);
		doLogIn(uname, pass);
		Assertions.assertEquals("Home", driver.getTitle());

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}


	@Test
	public void credentialCreation(){
		LoginHome("Saleh", "Fahad","Salh88Fo","FofoSall");
		createCredentials();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assertions.assertEquals("https://www.udacity.com"
				,driver.findElement(By.id("urlDisplay")).getText());
		Assertions.assertEquals("Sarah"
				,driver.findElement(By.id("usernameDisplay")).getText());
		Assertions.assertNotEquals("Soso_kh"
				,driver.findElement(By.id("passwordDisplay")).getText());
//		Assertions.assertTrue(driver.findElement(By.id("displayNoteDescription")).getText().
//				contains("Testing note for superDuperDrive Project"));


	}


	@Test
	public void deleteCredential() {
		LoginHome("Lana", "Jama", "Lanoo", "LJnnma66");
		createCredentials();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deletebtnCredential")));
		WebElement deletebtnCredential = driver.findElement(By.id("deletebtnCredential"));
		deletebtnCredential.click();

		Assertions.assertEquals("Result", driver.getTitle());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("backToHome")));
		WebElement bachHome = driver.findElement(By.id("backToHome"));
		bachHome.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialstab = driver.findElement(By.id("nav-credentials-tab"));
		credentialstab.click();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assertions.assertFalse(elementExist(driver,"urlDisplay"));
		Assertions.assertFalse(elementExist(driver,"usernameDisplay"));
		Assertions.assertFalse(elementExist(driver,"passwordDisplay"));
	}

		@Test
	public void editCredential(){
		LoginHome("Gyda", "Saleh","gydasoso","Gyd22ss");
		createCredentials();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editcredentialsbtn")));
		WebElement edit = driver.findElement(By.id("editcredentialsbtn"));
		edit.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url = driver.findElement(By.id("credential-url"));
		String urlValue = driver.findElement(By.id("credential-url")).getAttribute("value");
		Assertions.assertEquals("https://www.udacity.com", urlValue);
		url.click();
		url.clear();
		url.sendKeys("https://www.youtube.com/");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement username = driver.findElement(By.id("credential-username"));
		Assertions.assertEquals("Sarah", username.getAttribute("value"));
		username.click();
		username.clear();
		username.sendKeys("Budur");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password = driver.findElement(By.id("credential-password"));
		Assertions.assertEquals("Soso_kh", password.getAttribute("value"));
		password.click();
		password.clear();
		password.sendKeys("Bkee445");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialSavechanges")));
		WebElement credentialSavechanges = driver.findElement(By.id("credentialSavechanges"));
		credentialSavechanges.click();

		Assertions.assertEquals("Result", driver.getTitle());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("backToHome")));
		WebElement bachHome = driver.findElement(By.id("backToHome"));
		bachHome.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialstab = driver.findElement(By.id("nav-credentials-tab"));
		credentialstab.click();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		Assertions.assertEquals("https://www.youtube.com/"
				,driver.findElement(By.id("urlDisplay")).getText());
		Assertions.assertEquals("Budur"
				,driver.findElement(By.id("usernameDisplay")).getText());
		Assertions.assertNotEquals("Bkee445"
				,driver.findElement(By.id("passwordDisplay")).getText());


	}
	public void createCredentials(){

		//create note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialstab = driver.findElement(By.id("nav-credentials-tab"));
		credentialstab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCredential")));
		WebElement addCredential = driver.findElement(By.id("addCredential"));
		addCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url = driver.findElement(By.id("credential-url"));
		url.click();
		url.sendKeys("https://www.udacity.com");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement username = driver.findElement(By.id("credential-username"));
		username.click();
		username.sendKeys("Sarah");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password = driver.findElement(By.id("credential-password"));
		password.click();
		password.sendKeys("Soso_kh");



		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialSavechanges")));
		WebElement credentialSavechanges = driver.findElement(By.id("credentialSavechanges"));
		credentialSavechanges.click();

		Assertions.assertEquals("Result", driver.getTitle());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("backToHome")));
		WebElement bachHome = driver.findElement(By.id("backToHome"));
		bachHome.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialstab = driver.findElement(By.id("nav-credentials-tab"));
		credentialstab.click();

	}

	public void createNote(){

		//create note
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notetab = driver.findElement(By.id("nav-notes-tab"));
		notetab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNotebtn")));
		WebElement addNotebtn = driver.findElement(By.id("addNotebtn"));
		addNotebtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.click();
		noteTitle.sendKeys("Test Creating Note");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement description = driver.findElement(By.id("note-description"));
		description.click();
		description.sendKeys("Testing note for superDuperDrive Project");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveNote")));
		WebElement saveNote = driver.findElement(By.id("saveNote"));
		saveNote.click();

		Assertions.assertEquals("Result", driver.getTitle());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("backToHome")));
		WebElement bachHome = driver.findElement(By.id("backToHome"));
		bachHome.click();



		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		 notetab = driver.findElement(By.id("nav-notes-tab"));
		notetab.click();

	}

	@Test
	public void noteCreation(){
		LoginHome("Rawan", "Ali","Roni_3","Roro88ll");
		createNote();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
//		driver.get("http://localhost:" + this.port + "/home");
//		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		//is it visible


		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assertions.assertEquals("Test Creating Note"
				,driver.findElement(By.id("displayNoteTitle")).getText());
		Assertions.assertTrue(driver.findElement(By.id("displayNoteDescription")).getText().
				contains("Testing note for superDuperDrive Project"));

	}

	@Test
	public void editNote(){
		LoginHome("Fadwa", "Mesho","Faddo3","fofom34m");
		createNote();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
//		driver.get("http://localhost:" + this.port + "/home");
//		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editbtn")));
		WebElement editbtn = driver.findElement(By.id("editbtn"));
		editbtn.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.click();
		noteTitle.clear();
		noteTitle.sendKeys("Edit Note testing");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement description = driver.findElement(By.id("note-description"));
		description.click();
		description.clear();
		description.sendKeys("This is edit note testing Project");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveNote")));
		WebElement saveNote = driver.findElement(By.id("saveNote"));
		saveNote.click();

		Assertions.assertEquals("Result", driver.getTitle());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("backToHome")));
		WebElement bachHome = driver.findElement(By.id("backToHome"));
		bachHome.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notetab = driver.findElement(By.id("nav-notes-tab"));
		notetab.click();


		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assertions.assertEquals("Edit Note testing"
				,driver.findElement(By.id("displayNoteTitle")).getText());
		Assertions.assertTrue(driver.findElement(By.id("displayNoteDescription")).getText().
				contains("This is edit note testing Project"));

	}

	@Test
	public void deleteNote(){
		LoginHome("Fatimah", "Meshari","Fatoom3M","Foti88MM");
		createNote();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/home");
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notetab = driver.findElement(By.id("nav-notes-tab"));
		notetab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deletNotebtn")));
		WebElement editbtn = driver.findElement(By.id("deletNotebtn"));
		editbtn.click();

		Assertions.assertEquals("Result", driver.getTitle());

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("backToHome")));
		WebElement bachHome = driver.findElement(By.id("backToHome"));
		bachHome.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		 notetab = driver.findElement(By.id("nav-notes-tab"));
		notetab.click();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assertions.assertFalse(elementExist(driver,"displayNoteTitle"));
		Assertions.assertFalse(elementExist(driver,"displayNoteDescription"));

	}

	public boolean elementExist(WebDriver webDriver, String id){
		try {
			webDriver.findElement(By.id(id));
			return true;
		}catch (NoSuchElementException exception){
			return false;
		}
	}


	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/

		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().
				contains("You successfully signed up! Please login to continue."));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
