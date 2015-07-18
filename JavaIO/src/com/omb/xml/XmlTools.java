package com.omb.xml;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XmlTools {

	public static Element getElementByTagName(Element startElm, String tagName){
		NodeList children =startElm.getChildNodes();
		Element result=null;

		int i=0;
		while(result==null && i<children.getLength()){
			Node child=children.item(i);
			if(child instanceof Element){
				if(((Element) child).getTagName().equals(tagName))
					return (Element) child;
				else
					result= getElementByTagName((Element)child, tagName);
			}
			i++;
		}
		return result;
	}
	
	public static Element [] getElementsByTagName(Element elm, String tagName){
		Element [] elements=null;
		ArrayList<Element> elmList = new ArrayList<Element>();
		Element elmFound = getElementByTagName(elm, tagName);
		if(elmFound!=null){
			elmList.add(elmFound);
			Node node=elmFound;
			while((node=node.getNextSibling())!=null){
				if(node instanceof Element){
					elmFound=(Element) node;			
					if(elmFound.getTagName().equals(tagName))
						elmList.add(elmFound);
				}
			}
		}
		if(elmList.size()>0){
			elements = new Element[elmList.size()];
			elmList.toArray(elements);
		}
		return elements;
	}
	
	public static String getElementText(Element elm){
		if (elm==null) 
			return "";
		else{
			Text txt =(Text) elm.getFirstChild();
			if (txt==null){
				return "";
			}else{
				return txt.getData().trim();
			}
		}
	}
	
	public static Element createDocElementWithTextContent(Document doc, String tagName, String text){
		Element elm = doc.createElement(tagName);
		elm.appendChild(doc.createTextNode(text));
		return elm;
	}
	
	public static void removeElementFromDoc(Document doc, String tagName){
		Element root = doc.getDocumentElement();
		Element remove = getElementByTagName(root, tagName);
		Node parent = remove.getParentNode();
		parent.removeChild(remove);
	}

}

