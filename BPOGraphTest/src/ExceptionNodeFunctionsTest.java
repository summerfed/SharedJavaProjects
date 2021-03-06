import com.fed.dev.utilities.Tester;
import com.svi.bpo.graph.BPO;
import com.svi.bpo.graph.ElementFunctions;
import com.svi.bpo.graph.ExceptionNodeFunctions;
import com.svi.bpo.graph.NodeFunctions;
import com.svi.bpo.graph.notifications.BPONotifications;


public class ExceptionNodeFunctionsTest {
	
	private ExceptionNodeFunctions excFunctions;
	private NodeFunctions nodeFunctions;
	private ElementFunctions elementFunctions;
	private BPO instance;
	
	public ExceptionNodeFunctionsTest(BPO bpoInstance) {
		this.instance = bpoInstance;
		this.excFunctions = bpoInstance.getExceptionNodeFunctions();
		this.nodeFunctions = bpoInstance.getNodeFunctions();
		this.elementFunctions = bpoInstance.getElementFunctions();
	}
	
	
	public void insertExceptionNodeMissingNode() {
		String nodeId = "TestNodeId";
		if(nodeFunctions.isNodeExist(nodeId)) {
			nodeFunctions.deleteNode(nodeId);
		}
		String exceptionCode = "Test - ExceptionCode";
		String exceptionName = "Test - ExceptionName";
		long allowedWaitingTime = 100L;
		long allowedProcessTime = 100L;		
		BPONotifications dbResult = excFunctions.insertExceptionNode(nodeId, exceptionCode, exceptionName, allowedWaitingTime, allowedProcessTime);
		test(BPONotifications.NODE_DOES_NOT_EXIST, dbResult, Tester.getCurrentExecutionMethodName());
	}
	
	public void insertExceptionNodeSuccess() {
		String nodeId = "TestNodeId";
		if(!nodeFunctions.isNodeExist(nodeId)) {
			nodeFunctions.insertNode(nodeId, "TestNodeName", "TestCluster", "Box", 2.2, 100, 100, 100, 100);
		}
		String exceptionCode = "Test - ExceptionCode";
		String exceptionName = "Test - ExceptionName";
		long allowedWaitingTime = 100L;
		long allowedProcessTime = 100L;		
		BPONotifications dbResult = excFunctions.insertExceptionNode(nodeId, exceptionCode, exceptionName, allowedWaitingTime, allowedProcessTime);
		test(BPONotifications.EXCEPTION_NODE_INSERT_SUCCESS, dbResult, Tester.getCurrentExecutionMethodName());
	}
	
	
	public void test(BPONotifications correctResult, BPONotifications actualResult, String methodName) {
		if(actualResult.equals(correctResult)) {
			System.out.println("SUCCESS!: " + Tester.getCurrentExecutionClassName() +" : " + methodName+"\n***");
		} else {
			System.out.println("FAILED!: " + Tester.getCurrentExecutionClassName() +" : " + methodName+"\nRETURN VALUE: " + actualResult.name()+"\n***");
		}
	}
}
