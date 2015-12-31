package com.fed.dev.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serializer.OutputPropertiesFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlDomUtility {
	
	/**
	 * 
	 * @return Empty Document Object
	 */
	public Document getFileDocumentObject() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;
		try {
			builder = dbf.newDocumentBuilder();
			doc = builder.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		/*Element node = doc.createElement("Header");
		doc.appendChild(node);*/
		return doc;
	}
	public Document getFileDocumentObject(File file) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
//			System.out.println("Error File Absolute Path: " + file.getAbsolutePath());
		}
		return doc;
	}
	
	public Document getFileDocumentObject(byte[] blob) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		ByteArrayInputStream input = new ByteArrayInputStream(blob);
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(input);
		} catch (ParserConfigurationException | SAXException | IOException e) {
//			System.out.println("Error File Absolute Path: " + file.getAbsolutePath());
			System.out.println(e.getLocalizedMessage());
		}
		return doc;
	}
	
	public static Document getFileDocumentFromString(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try 
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }
	
	public void xmlFileToFilesystem(File xmlFile, String location) 
	        throws SAXException, ParserConfigurationException, IOException {
	    // Parse the given input
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.parse(xmlFile);

	    // Write the parsed document to an xml file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
		    StreamResult result =  new StreamResult(new File(location));
		    transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public void xmlDocToFilesystem(Document xmlFile, String location) throws IOException, TransformerException {
	    // Write the parsed document to an xml file
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer;
		transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "4");
		DOMSource source = new DOMSource(xmlFile);
		File newFile = new File(location);
		if(!newFile.exists()) {
		 newFile.getParentFile().mkdirs();
		 newFile.createNewFile();
		}
	    StreamResult result =  new StreamResult(newFile.getPath());
	    transformer.transform(source, result);
	}
	
	public boolean hasElementByTagName(Document doc, String elementName) {
		NodeList nodeList = doc.getElementsByTagName(elementName);
		if(nodeList.getLength()>0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param doc
	 * @param elementName
	 * @return null if element does not exist
	 */
	public String getFirstElementTextContentByTagName(Document doc, String elementName)  {
		String dataToReturn = null;
		if(doc!=null) {
			NodeList nodeList = doc.getElementsByTagName(elementName);
			
			if(nodeList!=null && nodeList.getLength()>0) {
				dataToReturn = nodeList.item(0).getTextContent();
			}
		}
		return dataToReturn;
	}
	
	/**
	 * 
	 * @param doc
	 * @param tagNameToAdd
	 * @param parentTag
	 * @return addedTag
	 */
	public Node appendNode(Document doc, String tagNameToAdd, String parentTag) {
		Element nodeToAdd = null;
		try {
			Node parentNode = doc.getElementsByTagName(parentTag).item(0);
			nodeToAdd = doc.createElement(tagNameToAdd);
			
			if(parentNode!=null) {
				parentNode.appendChild(nodeToAdd);
			} else {
				appendNode(doc, parentTag);
				appendNode(doc, tagNameToAdd, parentTag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeToAdd;
	}
	
	public void appendNode(Document doc, String tagNameToAdd) {
		try {
			Node parentNode = doc.getDocumentElement();
			Element nodeToAdd = doc.createElement(tagNameToAdd);
			if(parentNode!=null) {
				parentNode.appendChild(nodeToAdd);
			} else {
				doc.appendChild(nodeToAdd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void appendNode(Document doc, Node parentNode, Node childNode) {
		try {
			parentNode.appendChild(childNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNodeBeforeAnotherNode(Document doc, String tagNameToAdd, String beforeTagName, String parentTag) {
		try {
			Node parentNode = doc.getElementsByTagName(parentTag).item(0);
			Element nodeToAdd = doc.createElement(tagNameToAdd);
			Node beforeThisNode = doc.getElementsByTagName(beforeTagName).item(0);
			parentNode.insertBefore(nodeToAdd, beforeThisNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setNodeTextContent(Document doc, String tagName, String textValue) {
		try {
			doc.getElementsByTagName(tagName).item(0).setTextContent(textValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void removeChildNode(Document doc, String parentNodeTag, String tagName) {
		try { 
			Node parentNode = doc.getElementsByTagName(parentNodeTag).item(0);
			NodeList nodeList = doc.getElementsByTagName(tagName);
			if(nodeList.getLength()>0) {
				Node childNodeToRemove = doc.getElementsByTagName(tagName).item(0);
				parentNode.removeChild(childNodeToRemove);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeNode(Document doc, String tagName) {
		try { 
			
			NodeList nodeList = doc.getElementsByTagName(tagName);
			if(nodeList.getLength()>0) {
				for(int i=0; i<nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					node.getParentNode().removeChild(node);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Node getFirstNodeByTagName(Document doc, String tagName) {
		NodeList list = doc.getElementsByTagName(tagName);
		Node dataToReturn = null;
		if(list.getLength()>0) {
			dataToReturn = list.item(0);
		}
		return dataToReturn;
	}
	
	public byte[] DocumentToByte(Document doc) {
		byte[] dataToReturn = null;
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			StreamResult result=new StreamResult(bos);
			transformer.transform(source, result);
			dataToReturn = bos.toByteArray();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return dataToReturn;
	}
	
	 public static String DocumentToString(Document doc) {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ElementToStream(doc.getDocumentElement(), baos);
	    return new String(baos.toByteArray());
	 }
	 
	 public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder builder; 
        try 
        { 
            builder = factory.newDocumentBuilder(); 
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return null;
    }
	 
	 public static void ElementToStream(Element element, OutputStream out) {
	    try {
	      DOMSource source = new DOMSource(element);
	      StreamResult result = new StreamResult(out);
	      TransformerFactory transFactory = TransformerFactory.newInstance();
	      Transformer transformer = transFactory.newTransformer();
	      transformer.transform(source, result);
	    } catch (Exception ex) {
	    }
	  }
}
