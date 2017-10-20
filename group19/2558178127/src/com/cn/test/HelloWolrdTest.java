package com.cn.test;

import org.junit.Assert;
import org.junit.Test;

import com.cn.kevin.HelloWorld;

public class HelloWolrdTest {

	@Test
	public void test() {
		Assert.assertEquals("HelloWorld", new HelloWorld().sayHello());
	}

}
