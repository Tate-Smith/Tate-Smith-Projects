package com.gradescope.bool_exp;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class BoolSatTest {

	@Test
	public void testExpression() {
		Assert.assertTrue(expression(true, false));
		Assert.assertTrue(expression(false, false));
		Assert.assertTrue(expression(false, true));
		Assert.assertFalse(expression(true, true));
	}

}
