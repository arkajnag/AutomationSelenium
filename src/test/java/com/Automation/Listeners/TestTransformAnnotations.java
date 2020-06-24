package com.Automation.Listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class TestTransformAnnotations implements IAnnotationTransformer{

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class arg1, Constructor arg2, Method arg3) {
		annotation.setRetryAnalyzer(TestRetryAnalyzer.class);
		
	}

}
