package com.nie.rule;

import java.util.List;

import org.fit.cssbox.layout.Box;
import org.fit.cssbox.layout.ElementBox;
import org.fit.cssbox.layout.TextBox;
import org.w3c.dom.Node;

import com.nie.vo.BlockVo;

public class BlockRule {

	//TODO threshold value
	private static int threshold=40000;
	
	public static boolean dividable(BlockVo block) {
		Box box=block.getBox();
		if (box instanceof TextBox) {
			return false;
		}
		String name=box.getNode().getNodeName();
//		System.out.println("name::"+name);
		if (name.equals("img")) {
			return false;
		}
		if (!box.isBlock())
		{
			return inlineRules(block);
		}
		else if (name.equals("table"))
		{
			return tableRules(block);
		}
		else if (name.equals("tr"))
		{
			return trRules(block);
		}
		else if (name.equals("td"))
		{
			return tdRules(block);
		}
		else if (name.equals("p"))
		{
			return pRules(block);
		}
		else
		{
			return otherRules(block);
		}
	}
	
	private static boolean otherRules(BlockVo block) {
//		System.out.println("------------------------------otherRules");
		// 1,2,3,4,6,7,9,10,12
		if (rule1(block)) {
			return true;
		}
		if (rule2(block)) {
			return true;
		}
		if (rule3(block)) {
			return true;
		}
		if (rule4(block)) {
			return true;
		}
		if (rule6(block)) {
			return true;
		}
		if (rule7(block)) {
			return true;
		}
		if (rule9(block)) {
			return true;
		}
		if (rule10(block)) {
			return true;
		}
		if (rule12(block)) {
			return true;
		}
		return false;
	}

	private static boolean pRules(BlockVo block) {
//		System.out.println("------------------------------pRules");
		// 1,2,3,4,5,6,7,9,10,12
		if (rule1(block)) {
			return true;
		}
		if (rule2(block)) {
			return true;
		}
		if (rule3(block)) {
			return true;
		}
		if (rule4(block)) {
			return true;
		}
		if (rule5(block)) {
			return true;
		}
		if (rule6(block)) {
			return true;
		}
		if (rule7(block)) {
			return true;
		}
		if (rule9(block)) {
			return true;
		}
		if (rule10(block)) {
			return true;
		}
		if (rule12(block)) {
			return true;
		}
		return false;
	}

	private static boolean tdRules(BlockVo block) {
//		System.out.println("------------------------------tdRules");
		// 1,2,3,4,9,10,11,13
		if (rule1(block)) {
			return true;
		}
		if (rule2(block)) {
			return true;
		}
		if (rule3(block)) {
			return true;
		}
		if (rule4(block)) {
			return true;
		}
		if (rule9(block)) {
			return true;
		}
		if (rule10(block)) {
			return true;
		}
		if (rule11(block)) {
			return true;
		}
		if (rule13(block)) {
			return true;
		}
		return false;
	}

	private static boolean trRules(BlockVo block) {
//		System.out.println("------------------------------trRules");
		// 1,2,3,7,8,10,13
		if (rule1(block)) {
			return true;
		}
		if (rule2(block)) {
			return true;
		}
		if (rule3(block)) {
			return true;
		}
		if (rule7(block)) {
			return true;
		}
		if (rule8(block)) {
			return true;
		}
		if (rule10(block)) {
			return true;
		}
		if (rule13(block)) {
			return true;
		}
		return false;
	}

	private static boolean tableRules(BlockVo block) {
//		System.out.println("------------------------------tableRules");
		// 1,2,3,8,10,13
		if (rule1(block)) {
			return true;
		}
		if (rule2(block)) {
			return true;
		}
		if (rule3(block)) {
			return true;
		}
		if (rule8(block)) {
			return true;
		}
		if (rule10(block)) {
			return true;
		}
		if (rule13(block)) {
			return true;
		}
		return false;
	}

	private static boolean inlineRules(BlockVo block) {
		// 1,2,3,4,5,6,7,9,10,12
//		System.out.println("------------------------------inlineRules");
		if (rule1(block)) {
			return true;
		}
		if (rule2(block)) {
			return true;
		}
		if (rule3(block)) {
			return true;
		}
		if (rule4(block)) {
			return true;
		}
		if (rule5(block)) {
			return true;
		}
		if (rule6(block)) {
			return true;
		}
		if (rule7(block)) {
			return true;
		}
		if (rule9(block)) {
			return true;
		}
		if (rule10(block)) {
			return true;
		}
		if (rule12(block)) {
			return true;
		}
		return false;
	}

	/**
	 * If the DOM node is not a text node and it has no valid children, 
	 * then this node cannot be divided and will be cut.
	 * @param block
	 * @return
	 */
	private static boolean rule1(BlockVo block) {
		Box node=block.getBox();
		if (!isTextNode(node)&&!hasValidChildNode(node)) {
//			block.getParent().getChildren().remove(block);
		}
		return false;
	}
	
	/**
	 * If the DOM node has only one valid child and the child is not a text node, 
	 * then divide this node.
	 * @param block
	 * @return
	 */
	private static boolean rule2(BlockVo block) {
		if (block.getChildren().size()==1) {
			Box node=block.getChildren().get(0).getBox();
			if (isValidNode(node)&&!isTextNode(node)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If the DOM node is the root node of the sub-DOM tree (corresponding to the block), 
	 * and there is only one sub DOM tree corresponding to this block, divide this node.
	 * @param block
	 * @return
	 */
	private static boolean rule3(BlockVo block) {
		ElementBox node=(ElementBox)block.getBox();
		if (!node.isRootElement())
			return false;

		boolean result = true;
		int cnt = 0;

		for (BlockVo vipsBlock : block.getChildren())
		{
			if (vipsBlock.getBox().getNode().getNodeName().equals(node.getNode().getNodeName()))
			{
				result = true;
				isOnlyOneDomSubTree(node.getNode(), vipsBlock.getBox().getNode(), result);

				if (result)
					cnt++;
			}
		}

		return (cnt == 1) ? true : false;
	}
	
	/**
	 * If all of the child nodes of the DOM node are text nodes or virtual text nodes, 
	 * do not divide the node.
	 * If the font size and font weight of all these child nodes are same, 
	 * set the DoC of the extracted block to 10.
	 * Otherwise, set the DoC of this extracted block to 9.
	 * @param block
	 * @return
	 */
	private static boolean rule4(BlockVo block) {
		ElementBox node=(ElementBox)block.getBox();
		int size=node.getSubBoxList().size();
		int count=0;
		for (Box box : node.getSubBoxList()) {
			if (isTextNode(box)||isVirtualTextNode(box)) {
				count++;
			}
		}
		if (count==size) {
			int fontSize=0;
			for (Box box : node.getSubBoxList()) {
				int childSize=box.getVisualContext().getFont().getSize();
				if (fontSize!=0) {
					if (fontSize!=childSize) {
						block.setDoC(9);
						return false;
					}
				}else {
					fontSize=childSize;
				}
			}
			String fontWeight = null;
			for (Box box : node.getSubBoxList()) {
				if (box instanceof ElementBox) {
					ElementBox child = (ElementBox) box;
					String childWeight = child.getStylePropertyValue("font-weight");
					if (fontWeight != null) {
						if (!fontWeight.equals(childWeight)) {
							block.setDoC(9);
							return false;
						}
					} else {
						fontWeight = childWeight;
					} 
				}
			}
			block.setDoC(10);
			return false;
		}
		return true;
	}
	
	/**
	 * If one of the child nodes of the DOM node is line-break node, 
	 * then divide this DOM node
	 * @param block
	 * @return
	 */
	private static boolean rule5(BlockVo block) {
		ElementBox node=(ElementBox)block.getBox();
		for (Box box : node.getSubBoxList()) {
			if (!box.isBlock()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If one of the child nodes of the DOM node has HTML tag <HR>, 
	 * then divide this DOM node
	 * @param block
	 * @return
	 */
	private static boolean rule6(BlockVo block) {
		ElementBox node=(ElementBox)block.getBox();
		for (Box box : node.getSubBoxList()) {
			if (box.getNode().getNodeName().equals("hr")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If the sum of all the child nodes’ size is greater than this DOM node’s size, 
	 * then divide this node.
	 * @param block
	 * @return
	 */
	private static boolean rule7(BlockVo block) {
		ElementBox node=(ElementBox)block.getBox();
		int x=node.getContentX();
		int y=node.getContentY();
		int width=node.getContentWidth();
		int height=node.getContentHeight();
		for (Box box : node.getSubBoxList()) {
			if (box.getContentX()<x) {
				return true;
			}
			if (box.getContentY()<y) {
				return true;
			}
			if ((x+width)<(box.getContentX()+box.getContentWidth())) {
				return true;
			}
			if ((y+height)<(box.getContentY()+box.getContentHeight())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * If the background color of this node is different from one of its children’s, 
	 * divide this node and at the same time, 
	 * the child node with different background color will not bedivided in this round.
	 * Set the DoC value (6-8) for the child node based on the html tag of the child node and the size of the child node.
	 * @param block
	 * @return
	 */
	private static boolean rule8(BlockVo block) {
		boolean ret=false;
		ElementBox node=(ElementBox)block.getBox();
		String bColor=node.getStylePropertyValue("background-color");
		for (BlockVo b : block.getChildren()) {
			if (b.getBox() instanceof ElementBox) {
				ElementBox child = (ElementBox) b.getBox();
				String childColor = child.getStylePropertyValue("background-color");
				if (!bColor.equals(childColor)) {
					b.setDividable(false);
					b.setDoC(getDocByTagSize("",0));
					ret = true;
				} 
			}
		}
		return ret;
	}
	
	/**
	 * If the node has at least one text node child or at least one virtual text node child, 
	 * and the node's relative size is smaller than a threshold, then the node cannot be divided
	 * Set the DoC value (from 5-8) based on the html tag of the node
	 * @param block
	 * @return
	 */
	private static boolean rule9(BlockVo block) {
		boolean ret=true;
		ElementBox node=(ElementBox)block.getBox();
		int count=0;
		for (Box box : node.getSubBoxList()) {
			if (isTextNode(box)||isVirtualTextNode(box)) {
				count++;
			}
		}
		if (count>0) {
			if (node.getContentX()*node.getContentY()<threshold) {
				ret = false;
				block.setDoC(getDocByTagSize("",0));
			}
		}
		return ret;
	}
	
	/**
	 * If the child of the node with maximum size are small than a threshold (relative size), 
	 * do not divide this node.
	 * Set the DoC based on the html tag and size of this node.
	 * @param block
	 * @return
	 */
	private static boolean rule10(BlockVo block) {
		ElementBox node=(ElementBox)block.getBox();
		int maxSize=0;
		for (Box box : node.getSubBoxList()) {
			int childSize=box.getContentX()*box.getContentY();
			maxSize=maxSize<childSize?childSize:maxSize;
		}
		if (maxSize<threshold) {
			block.setDoC(getDocByTagSize("",0));
			return false;
		}
		return true;
	}
	
	/**
	 * If previous sibling node has not been divided, do not divide this node
	 * @param block
	 * @return
	 */
	private static boolean rule11(BlockVo block) {
		List<BlockVo> children=block.getParent().getChildren();
		int index=children.indexOf(block);
		int count=0;
		for (int i=0;i<index;i++) {
			if (!children.get(i).isDividable()) {
				count++;
			}
		}
		if (count==index) {
			return false;
		}
		return true;
	}
	
	/**
	 * Divide this node.
	 * @param block
	 * @return
	 */
	private static boolean rule12(BlockVo block) {
		return true;
	}
	
	/**
	 * Do not divide this node 
	 * Set the DoC value based on the html tag and size of this node.
	 * @param block
	 * @return
	 */
	private static boolean rule13(BlockVo block) {
		block.setDoC(getDocByTagSize("",0));
		return false;
	}
	
	/**
	 * 是否有有效的子节点
	 * @param node
	 * @return
	 */
	private static boolean hasValidChildNode(Box node) {
		for (Box box : ((ElementBox) node).getSubBoxList()) {
			if (isValidNode(box)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * a node that can be seen through the browser. 
	 * The node’s width and height are not equal to zero.
	 * @param node
	 * @return
	 */
	private static boolean isValidNode(Box node) {
		if (node.isDisplayed()&&node.getContentWidth()>0&&node.getContentHeight()>0) {
			return true;
		}
		return false;
	}
	
	/**
	 *  the DOM node corresponding to free text, 
	 *  which does not have an html tag
	 * @param box
	 * @return
	 */
	private static boolean isTextNode(Box node)
	{
		return node instanceof TextBox?true:false;
	}
	
	/**
	 * Virtual text node (recursive definition):
	 * Inline node with only text node children is a virtual text node.
	 * Inline node with only text node and virtual text node children is a virtual text node.
	 * @param node
	 * @return
	 */
	private static boolean isVirtualTextNode(Box node) {
		if (node instanceof ElementBox) {
			ElementBox eBox=(ElementBox)node;
			int size=eBox.getSubBoxList().size();
			int count=0;
			for(Box box:eBox.getSubBoxList()){
				if (isTextNode(box)||isVirtualTextNode(box)) {
					count++;
				}
			}
			if (count==size) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if node's subtree is unique in DOM tree.
	 * @param pattern Node for comparing
	 * @param node Node from DOM tree
	 * @param result True if element is unique otherwise false
	 */
	private static void isOnlyOneDomSubTree(Node pattern, Node node, boolean result) {
		if (!pattern.getNodeName().equals(node.getNodeName()))
			result = false;

		if (pattern.getChildNodes().getLength() != node.getChildNodes().getLength())
			result = false;

		if (!result)
			return;

		for (int i = 0; i < pattern.getChildNodes().getLength(); i++)
		{
			isOnlyOneDomSubTree(pattern.getChildNodes().item(i), node.getChildNodes().item(i), result);
		}
		
	}
	
	//TODO
	private static int getDocByTagSize(String tag,int size) {
		return 7;
	}
	
}
