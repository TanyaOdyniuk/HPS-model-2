package exceptions;

public class Exceptions {
	
	private Exceptions() {}
	
	public static class InvalidInput extends Exception {
		private static final long serialVersionUID = 1L;
		public InvalidInput(String message, String inputArea, int row, int column) {
			super(String.format("Error in input \"%s\" (row# %d, column# %d):\n%s",
								inputArea, message, row, column));
		}
		public InvalidInput(String message, String inputArea) {
			super(String.format("Error in input \"%s\":\n%s",
								inputArea, message));
		}
	}
	
	public static class NotGenotype extends InvalidInput {
		private static final long serialVersionUID = 1L;
		public NotGenotype(String wrongString, String inputArea, int row, int column) {
			super(String.format("The string \"%s\" is not a presentation of a genotype",
								wrongString),
				  inputArea, row, column);
		}
	}
	
	public static class Negative extends InvalidInput {
		private static final long serialVersionUID = 1L;
		public Negative(String paramName, int value, String inputArea, int row, int column) {
			super(String.format("The \"%s\" cann't be negative, but actuale value is %d",
								paramName, value),
				  inputArea, row, column);
		}
		public Negative(String paramName, double value, String inputArea, int row, int column) {
			super(String.format("The \"%s\" cann't be negative, but actuale value is %f",
								paramName, value),
				  inputArea, row, column);
		}
	}
	
	public static class NotInRange extends InvalidInput {
		private static final long serialVersionUID = 1L;
		public NotInRange(String paramName, int min, int max, int value, String inputArea, int row, int column) {
			super(String.format("The \"%s\" cann't be <%d and >%d, but actuale value is %d",
								paramName, min, max, value),
				  inputArea, row, column);
		}
		public NotInRange(String paramName, float min, float max, float value, String inputArea, int row, int column) {
			super(String.format("The \"%s\" cann't be <%f and >%f, but actuale value is %f",
								paramName, min, max, value),
				  inputArea, row, column);
		}
	}
	
	public static class NotInteger extends InvalidInput {
		private static final long serialVersionUID = 1L;
		public NotInteger(String actualString, String inputArea, int row, int column) {
			super(String.format("String representation of an integer was expected, "+
								"but actual value is \"%s\"", actualString),
				  inputArea, row, column);
		}
	}
	
	public static class NotFloat extends InvalidInput {
		private static final long serialVersionUID = 1L;
		public NotFloat(String actualString, String inputArea, int row, int column) {
			super(String.format("String representation of an float number was expected, "+
								"but actual value is \"%s\"", actualString),
				  inputArea, row, column);
		}
	}
	
	public static class WrongFileStructure extends InvalidInput {
		private static final long serialVersionUID = 1L;
		public WrongFileStructure(String message, String inputArea) {
			super(String.format("Wrong file structure (%s)", message), inputArea);
		}
	}
	
	public static class UnknownHabitat extends InvalidInput {
		private static final long serialVersionUID = 1L;
		public UnknownHabitat(String actualHabitat, String inputArea, int row, int column) {
			super(String.format("Habitat \"%s\" doesn't exist", actualHabitat),
				  inputArea, row, column);
		}
	}
	
	public static class WrongParentsPair extends InvalidInput {
		private static final long serialVersionUID = 1L;
		public WrongParentsPair(String first, String second, String inputArea, int row, int column) {
			super(String.format("Individuals with the same sex (%s and %s) cann't create posterity", first, second),
				  inputArea, row, column);
		}
	}
	
}