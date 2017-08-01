package testcasemodule
import com.ebox.webtest.TestCase
import com.ebox.webtest.WebTestEngine
class AppController {
    
    def index() {
        if(session["testCaseList"] == null)
            session["testCaseList"] = new ArrayList<TestCase>()
    }
    
    def show() {
        
    }
    
    def add() {
        List<TestCase> testCaseList = session["testCaseList"]
        TestCase testCase = null
        if(testCaseList.size() == 0)
            testCase = new TestCase(testCaseList.size()+1, params.selector, params.selectorValue, params.operation, false);
        else
            testCase = new TestCase(testCaseList.get(testCaseList.size()-1).getId()+1,params.selector,params.selectorValue,params.operation,false);
        testCaseList.add(testCase)
        session["testCaseList"] = testCaseList
        redirect(action:"show")
    }
    
    def addWithText() {
        List<TestCase> testCaseList = session["testCaseList"]
        TestCase testCase = null;
        if(testCaseList.size() == 0)
            testCase = new TestCase(testCaseList.size()+1, params.selector, params.selectorValue, params.operation, true, params.text);
        else
            testCase = new TestCase(testCaseList.get(testCaseList.size()-1).getId()+1, params.selector, params.selectorValue, params.operation, true, params.text);
        testCaseList.add(testCase);
        session["testCaseList"] = testCaseList
        redirect(action:"show")
    }
    
    def edit() {
        List testCaseList = session["testCaseList"]
        TestCase testCase = testCaseList.find{ tc -> tc?.id?.toInteger() == params?.id?.toInteger() }
        [testCase:testCase]
    }
    
    def update() {
        List testCaseList = session["testCaseList"]
        TestCase testCase = testCaseList.find{tc -> tc?.id?.toInteger() == params?.id?.toInteger()}
        testCase.selector = params.selector
        testCase.selectorValue = params.selectorValue
        testCase.action = params.operation
        redirect(action:"show")
    }
    
    def updateWithText() {
        List testCaseList = session["testCaseList"]
        TestCase testCase = testCaseList.find{tc -> tc?.id?.toInteger() == params?.id?.toInteger()}
        testCase.selector = params.selector
        testCase.selectorValue = params.selectorValue
        testCase.action = params.operation
        testCase.text = params.text
        redirect(action:"show")
    }
    
    def delete() {
        List testCaseList = session["testCaseList"]
	TestCase testCase = testCaseList.find{tc -> tc?.id?.toInteger() == params?.id?.toInteger()}
        testCaseList.remove(testCase)
        redirect(action:"show")
    }
    
    def finish() {
        List<TestCase> testCaseList = (List<TestCase>)request.getSession().getAttribute("testCaseList");
        WebTestEngine.setTestCaseList(testCaseList);
        new WebTestEngine().generateTest();
        session["fileName"] = WebTestEngine.fileName	
    }
    
    def download() {
        String fileName = session["fileName"]
        String filePath = "/opt/Testcasefiles/"+fileName;
        File file = new File(filePath);
        if (file.exists()) {
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "filename=WebTest.java")
            response.outputStream << file.bytes
        }
        session.invalidate()
    }
}