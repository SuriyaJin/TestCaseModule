package com.ebox.webtest
class WebTestEngine {
	private static def testCase;
	private static def file;
	private static def fileName;
	private static def testCaseList;
	private static def totalCount;
	private def path;
        
    def static getFileName() {
        fileName
    }
    
    static def setTestCaseList(testCaseList) {
        this.testCaseList = testCaseList
    }
    
    def generateTest() {
        totalCount = 0;
        testCaseList.each{test ->
                if(!(test.getAction().equals("Click")) && !(test.getAction().equals("Send Keys")) && !(test.getAction().equals("Select Options")))
                        totalCount++;
        }
        testCase = new StringBuffer();
        testCase.append("import java.io.File;" + System.getProperty("line.separator") +
                "import java.util.concurrent.TimeUnit;" + System.getProperty("line.separator") +
                "import org.junit.*;" + System.getProperty("line.separator") +
                "import static org.junit.Assert.*;" + System.getProperty("line.separator") +
                "import org.openqa.selenium.*;" + System.getProperty("line.separator") +
                "import org.openqa.selenium.firefox.FirefoxBinary;" + System.getProperty("line.separator") +
                "import org.openqa.selenium.firefox.FirefoxDriver;" + System.getProperty("line.separator") +
                "import org.openqa.selenium.firefox.FirefoxProfile;" + System.getProperty("line.separator") +
                "import org.openqa.selenium.support.ui.Select;" + System.getProperty("line.separator") +
                "" + System.getProperty("line.separator") +
                "public class WebTest {" + System.getProperty("line.separator") +
                "  private WebDriver driver;" + System.getProperty("line.separator") +
                "  private String baseUrl;" + System.getProperty("line.separator") +
                "  private boolean acceptNextAlert = true;" + System.getProperty("line.separator") +
                "  private StringBuffer verificationErrors = new StringBuffer();" + System.getProperty("line.separator") +
                "  public int passCount=0;" + System.getProperty("line.separator") +
                "  public int totalCount="+totalCount+";" + System.getProperty("line.separator") +
                "  public String text;" + System.getProperty("line.separator") +
                "  public Select select;" + System.getProperty("line.separator") +
                "  @Before" +System.getProperty("line.separator") +
                "  public void setUp() throws Exception {" + System.getProperty("line.separator") +
                "  String Xport = System.getProperty(\"lmportal.xvfb.id\", \":20\");" + System.getProperty("line.separator") +
                "  final File firefoxPath = new File(System.getProperty(\"lmportal.deploy.firefox.path\", \"/usr/bin/firefox\"));"+ System.getProperty("line.separator")+
                "  FirefoxBinary firefoxBinary = new FirefoxBinary(firefoxPath);"+System.getProperty("line.separator") +
                "  firefoxBinary.setEnvironmentProperty(\"DISPLAY\", Xport);"+System.getProperty("line.separator")+
                "  driver = new FirefoxDriver();" + System.getProperty("line.separator") + 
                "  baseUrl = \"#BASE_URL#\";" + System.getProperty("line.separator") +
                "  driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);" + System.getProperty("line.separator") +
                "}" + System.getProperty("line.separator") +
                "" + System.getProperty("line.separator") +
                "  @Test" + System.getProperty("line.separator") +
                "  public void testWeb() throws Exception {" + System.getProperty("line.separator") +
                "    driver.get(baseUrl + \"/index.html\");" + System.getProperty("line.separator"));
        fileName = new Date().getTime()+"WebTest.java";
        path = "/opt/Testcasefiles/" + fileName;
        beginGeneration();
        onFinish();
        file = new File(path)
        try{
            file.write(testCase.toString())
            testCase.delete(0,testCase.length())
        }catch(Exception e){
            e.printStackTrace()
        }
    }
    
    static def beginGeneration() {
        testCaseList.each{test ->
            switch(test.getAction()) {
                    case "Click":generateClick(test);break;
                    case "CheckTrue":generateCheckTrue(test);break;
                    case "CheckFalse":generateCheckFalse(test);break;
                    case "Send Keys":generateSendKeys(test);break;
                    case "Select Options":generateSelectOptions(test);break;
                    case "Verify Text":generateVerifyText(test);break;
                    case "Contains Text":generateContainsText(test);break;
            }
	}
    }
    
    static def generateClick(TestCase test) {
        testCase.append("try{" + System.getProperty("line.separator")
                    +"driver.findElement(By."+test.getSelector()+"(\""+test.getSelectorValue()+"\")).click();" + System.getProperty("line.separator")
                    +"}catch(Exception | Error e){" + System.getProperty("line.separator")
                    +"verificationErrors.append(\"Element with "+test.getSelector()+" '"+test.getSelectorValue()+"' not found\\n\");" + System.getProperty("line.separator")
                    +"}"+System.getProperty("line.separator"));
    }
    
    static def generateCheckTrue(TestCase test) {
        testCase.append("try{" + System.getProperty("line.separator")
                        + "assertTrue(isElementPresent(By."+test.getSelector()+"(\""+test.getSelectorValue()+"\")));" + System.getProperty("line.separator")
                        + "passCount++;" + System.getProperty("line.separator")
                        + "}catch(Exception | Error e){" + System.getProperty("line.separator")
                        + "verificationErrors.append(\"Element with "+test.getSelector()+" '"+test.getSelectorValue()+"' not found\\n\");" + System.getProperty("line.separator")
                        + "}"+System.getProperty("line.separator"));
    }
    
    static def generateCheckFalse(TestCase test) {
        testCase.append("try{" + System.getProperty("line.separator")
                        + "assertFalse(isElementPresent(By."+test.getSelector()+"(\""+test.getSelectorValue()+"\")));" + System.getProperty("line.separator")
                        + "passCount++;" + System.getProperty("line.separator")
                        + "}catch(Exception | Error e){" + System.getProperty("line.separator")
                        + "verificationErrors.append(\"Element with "+test.getSelector()+" '"+test.getSelectorValue()+"' should not present\\n\");" + System.getProperty("line.separator")
                        + "}"+System.getProperty("line.separator"));
    }
    
    static def generateSendKeys(TestCase test) {
        testCase.append("try{" + System.getProperty("line.separator")
                        +"driver.findElement(By."+test.getSelector()+"(\""+test.getSelectorValue()+"\")).clear();" + System.getProperty("line.separator")
                        +"driver.findElement(By."+test.getSelector()+"(\""+test.getSelectorValue()+"\")).sendKeys(\""+test.getText()+"\");" + System.getProperty("line.separator")
                        +"}catch(Exception | Error e){"+System.getProperty("line.separator") 
                        +"verificationErrors.append(\"Element with "+test.getSelector()+" '"+test.getSelectorValue()+"' not found\");" + System.getProperty("line.separator")
                        +"}"+System.getProperty("line.separator"));
		
    }
    
    static def generateSelectOptions(TestCase test) {
        testCase.append("try{" + System.getProperty("line.separator")
                       +"select = new Select(driver.findElement(By."+test.getSelector()+"(\""+test.getSelectorValue()+"\")));" + System.getProperty("line.separator")
                       +"select.selectByVisibleText(\""+test.getText()+"\");"+System.getProperty("line.separator")
                       +"}catch(Exception | Error e){"+System.getProperty("line.separator")
                       +"verificationErrors.append(\"Element with "+test.getSelector()+"'"+test.getSelectorValue()+"' not found\");"+System.getProperty("line.separator")
                       +"}"+System.getProperty("line.separator"));
    }
    
    static def generateVerifyText(TestCase test) {
        testCase.append("try{"+System.getProperty("line.separator")
                        +"assertEquals(\""+test.getText()+"\",driver.findElement(By."+test.getSelector()+"(\""+test.getSelectorValue()+"\")));"+System.getProperty("line.separator")
                        +"passCount++;"+System.getProperty("line.separator")
                        +"}catch(Exception | Error e){"+System.getProperty("line.separator")
                        +"verificationErrors.append(\"Text '"+test.getText()+"' not found on element with "+test.getSelector()+"'"+test.getSelectorValue()+"'\");"+System.getProperty("line.separator")
                        +"}"+System.getProperty("line.separator"));
    }
    
    static def generateContainsText(TestCase test) {
        testCase.append("try{"+System.getProperty("line.separator")
                       +"text = driver.findElement(By."+test.getSelector()+"(\""+test.getSelectorValue()+"\")).getText();"+System.getProperty("line.separator")
                       +"if(text.contains(\""+test.getText()+"\")){"+System.getProperty("line.separator")
                       +"passCount++;"+System.getProperty("line.separator")
                       +"}else{"+System.getProperty("line.separator")
                       +"verificationErrors.append(\"Text '"+test.getText()+"' not found on element with "+test.getSelector()+"'"+test.getSelectorValue()+"'\");"+System.getProperty("line.separator")
                       +"}"+System.getProperty("line.separator")
                       +"}catch(Exception | Error e){"+System.getProperty("line.separator")
                                   +"verificationErrors.append(\"Text '"+test.getText()+"' not found on element with "+test.getSelector()+"'"+test.getSelectorValue()+"'\");"+System.getProperty("line.separator")
                                   +"}"+System.getProperty("line.separator"));
    }
    
    static def onFinish() {
        testCase.append("}" + System.getProperty("line.separator") +
        "\n" + System.getProperty("line.separator") +
        "  @After" + System.getProperty("line.separator") +
        "  public void tearDown() throws Exception {" + System.getProperty("line.separator") +
        "  System.out.println(\"#\"+Math.round((passCount/(float)totalCount)*100));" + System.getProperty("line.separator") +
        "    driver.quit();" + System.getProperty("line.separator") +
        "    String verificationErrorString = verificationErrors.toString();" + System.getProperty("line.separator") +
        "    if (!\"\".equals(verificationErrorString)) {" + System.getProperty("line.separator") +
        "      fail(verificationErrorString);" + System.getProperty("line.separator") +
        "    }\n" + System.getProperty("line.separator") +
        "  }\n" + System.getProperty("line.separator") +
        "\n" + System.getProperty("line.separator") +
        "  private boolean isElementPresent(By by) {" + System.getProperty("line.separator") +
        "    try {\n" + System.getProperty("line.separator") +
        "      driver.findElement(by);" + System.getProperty("line.separator") +
        "      return true;" + System.getProperty("line.separator") +
        "    } catch (NoSuchElementException e) {" + System.getProperty("line.separator") +
        "      return false;" + System.getProperty("line.separator") +
        "    }\n" + System.getProperty("line.separator") +
        "  }\n" + System.getProperty("line.separator") +
        "\n" + System.getProperty("line.separator") +
        "  @SuppressWarnings(\"unused\")" + System.getProperty("line.separator") +
        "private boolean isAlertPresent() {" + System.getProperty("line.separator") +
        "    try {\n" + System.getProperty("line.separator") +
        "      driver.switchTo().alert();" + System.getProperty("line.separator") +
        "      return true;" + System.getProperty("line.separator") +
        "    } catch (NoAlertPresentException e) {" + System.getProperty("line.separator") +
        "      return false;" + System.getProperty("line.separator") +
        "    }\n" + System.getProperty("line.separator") +
        "  }\n" + System.getProperty("line.separator") +
        "\n" + System.getProperty("line.separator") +
        "  @SuppressWarnings(\"unused\")" + System.getProperty("line.separator") +
        "private String closeAlertAndGetItsText() {" + System.getProperty("line.separator") +
        "    try {" + System.getProperty("line.separator") +
        "      Alert alert = driver.switchTo().alert();" + System.getProperty("line.separator") +
        "      String alertText = alert.getText();" + System.getProperty("line.separator") +
        "      if (acceptNextAlert) {" + System.getProperty("line.separator") +
        "        alert.accept();" + System.getProperty("line.separator") +
        "      } else {" + System.getProperty("line.separator") +
        "        alert.dismiss();" + System.getProperty("line.separator") +
        "      }\n" + System.getProperty("line.separator") +
        "      return alertText;" + System.getProperty("line.separator") +
        "    } finally {" + System.getProperty("line.separator") +
        "      acceptNextAlert = true;" + System.getProperty("line.separator") +
        "    }\n" + System.getProperty("line.separator") +
        "  }\n" + System.getProperty("line.separator") +
        "}");
    }
}

