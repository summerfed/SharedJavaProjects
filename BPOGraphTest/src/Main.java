import com.svi.bpo.graph.BPO;


public class Main {

	public static void main(String[] args) {
		BPO bpo = new BPO("http://localhost:7474");
		bpo.clearBPO();
		System.out.println(bpo.getNodeFunctions().insertNode("Node1", "Node", "Cluster", "Box", 2.2, 100, 100, 100, 100));
		System.out.println(bpo.getElementFunctions().insertElement("Element1", 9, "Node1"));
		System.out.println(bpo.getElementFunctions().assignWorkerToElement("Node1", "Element1", "Fed"));
		System.out.println(bpo.getElementFunctions().getElement("Element1", "Node1", "Fed"));
		System.out.println(bpo.getElementFunctions().completeElement("Element1", "Node1", "Fed", 100, 0));
		System.out.println(bpo.getElementFunctions().insertElement("Element2", 9, "Node1"));
	}

}
