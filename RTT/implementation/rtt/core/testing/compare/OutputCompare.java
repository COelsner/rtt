package rtt.core.testing.compare;

import java.util.ArrayList;
import java.util.List;

import rtt.core.archive.output.Element;
import rtt.core.archive.output.Output;
import rtt.core.testing.compare.OutputCompare.CompareResult.Difference;
import rtt.core.testing.compare.results.TestFailure;

public class OutputCompare {
	
	public static class CompareResult {
		
		public enum Difference {
			ELEMENT_TYPE("Element type"),
			GENERATOR_TYPE("Generated by"),
			INFORMATIONAL("IsInformational"),
			NAME("Name"),			
			VALUE("Value"),
			CHILD_COUNT("Sizes of children");			
			
			private String description;
			
			private Difference(String description) {
				this.description = description;
			}
		}
		
		private Difference difference = null;
		private Object expected = null;
		private Object actual = null;
		
		protected CompareResult(Difference difference) {
			this.difference = difference;
		}
		
		public Difference getDifference() {
			return difference;
		}

		public boolean hasDifferences() {
			return difference != null;
		}
		
		public String getMessage() {
			if (difference != null) {
				StringBuilder builder = new StringBuilder(difference.description);
				if (expected != null && actual != null) {
					builder.append(" expected '");
					builder.append(expected.toString());
					builder.append("', but was '");
					builder.append(actual.toString());
					builder.append("'.");
				}
				
				return builder.toString();
			}
			
			return "No differences found.";			
		}
		
		public static CompareResult create(Difference difference, Object expected, Object actual) {
			CompareResult result = new CompareResult(difference);
			result.difference = difference;
			result.expected = expected;
			result.actual = actual;
			
			return result;
		}		
	}
	
	private static final String ELEMENT_NULL = 
			"One or both given elements were null.";
	
	private boolean testInformational;
	
	public OutputCompare(boolean testInformational) {
		this.testInformational = testInformational;
	}

	public static List<TestFailure> compareOutput(
			Output referenceOutput, Output actualOutput, boolean testInformational) {
		
		if (referenceOutput == null || actualOutput == null) {
			throw new IllegalArgumentException("Reference or actual output was null.");
		}
		
		List<TestFailure> failures = new ArrayList<>();
		Element refInitElement = referenceOutput.getInitialElement();
		Element actualInitElement = actualOutput.getInitialElement();		
		
		if (refInitElement != null && actualInitElement != null) {
			OutputCompare comparer = new OutputCompare(testInformational);
			CompareResult result = comparer.compareElements(
					refInitElement, actualInitElement);
			
			if (result != null && result.hasDifferences()) {			
				failures.add(new TestFailure(result.getMessage()));
			}
		} else if (refInitElement != actualInitElement) {
			failures.add(new TestFailure("Initial elements are different."));
		}
		
		return failures;		
	}
	
	public CompareResult compareElements(Element referenceElement, Element actualElement) {
		if (referenceElement == null || actualElement == null) {
			throw new IllegalArgumentException(ELEMENT_NULL);
		}
		
		if (!referenceElement.getElementType().equals(actualElement.getElementType())) {
			return CompareResult.create(Difference.ELEMENT_TYPE, 
					referenceElement.getElementType().name(), 
					actualElement.getElementType().name());
		}
		
		if (!referenceElement.getGeneratedBy().equals(actualElement.getGeneratedBy())) {
			return CompareResult.create(Difference.GENERATOR_TYPE, 
					referenceElement.getElementType().name(), 
					actualElement.getElementType().name());
		}
		
		if (!referenceElement.getName().equals(actualElement.getName())) {
			return CompareResult.create(Difference.NAME, 
					referenceElement.getName(), actualElement.getName());
		}		
		
		if (referenceElement.isInformational() != actualElement.isInformational()) {
			return CompareResult.create(Difference.INFORMATIONAL, 
					referenceElement.isInformational(), actualElement.isInformational());
		}
		
		if (testInformational(referenceElement)) {
			String valueOfRefer = String.valueOf(referenceElement.getValue());
			String valueOfActual = String.valueOf(actualElement.getValue());			
			
			if (!valueOfRefer.equals(valueOfActual)) {
				return CompareResult.create(Difference.VALUE, valueOfRefer, valueOfActual);
			}

			List<Element> referenceCompareElements = referenceElement.getElements();
			List<Element> actualCompareElements = actualElement.getElements();
			
			if (testInformational == false) {
				referenceCompareElements = getCompareElements(referenceCompareElements);
				actualCompareElements = getCompareElements(actualCompareElements);
			}
			
			return compareChildElements(referenceCompareElements, actualCompareElements);
		}
		
		return null;	
	}
	
	private boolean hasDifferences(CompareResult result) {
		return result != null && result.hasDifferences();
	}
	
	private boolean testInformational(Element element) {
		return !element.isInformational() || testInformational;
	}

	private List<Element> getCompareElements(List<Element> refValues) {
		List<Element> compareElements = new ArrayList<>();
		for (Element element : refValues) {
			if (!element.isInformational()) {
				compareElements.add(element);
			}
		}
		
		return compareElements;
	}
	
	private CompareResult compareChildElements(
			List<Element> refElements, List<Element> actualElements) {				
		
		if (refElements.size() != actualElements.size()) {
			return CompareResult.create(Difference.CHILD_COUNT,
					refElements.size(), actualElements.size());
		}
		
		int childCount = refElements.size();
		for (int index = 0; index < childCount; index++) {
			Element referenceElement = refElements.get(index);
			Element actualElement = actualElements.get(index);		
			
			CompareResult result = compareElements(
					referenceElement, actualElement);
			
			if (hasDifferences(result)) {
				return result;
			}
		}
		
		return null;
	}	
}
