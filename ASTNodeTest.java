package com.gradescope.bool_exp;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


class ASTNodeTest {

	@Test
	public void testNull(){
	    ASTNode testNand = ASTNode.createNandNode(null, null);
	    Assert.assertNull(testNand.child1);
	    Assert.assertNull(testNand.child2);
	}
	
	@Test
	public void testChildrenl(){
	    ASTNode childOne = ASTNode.createNandNode(null, null);
	    ASTNode childTwo = ASTNode.createNandNode(null, null);
	    ASTNode testParent = ASTNode.createNandNode(childOne, childTwo);
	    Assert.assertTrue(testParent.child1 == childOne);
	    Assert.assertTrue(testParent.child2 == childTwo);
	}
	
	@Test
	public void testType(){
	    ASTNode testNand = ASTNode.createNandNode(null, null);
	    ASTNode testId = ASTNode.createIdNode("a");
	    Assert.assertTrue(testNand.isNand());
	    Assert.assertFalse(testNand.isId());
	    Assert.assertTrue(testId.isId());
	    Assert.assertFalse(testId.isNand());
	}
	
	@Test
	public void testId(){
	    String str = "a";
	    ASTNode testId = ASTNode.createIdNode(str);
	    Assert.assertEquals(testId.getId(), str);
	}
	

}
