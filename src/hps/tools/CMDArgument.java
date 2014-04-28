package hps.tools;

import hps.tools.CMDLineParser.IllegalOptionValueException;
import hps.tools.CMDLineParser.Option;
import hps.tools.CMDLineParser.UnknownOptionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public enum CMDArgument {

	HELP ("help", Boolean.FALSE, Boolean.class),
	YEARS ('y',"years", new Integer(1), Integer.class),
	EXPERIMENTS ('e', "experiments", new Range(1), Range.class),
	POINTS('p', "points", new Range(1), Range.class),
	STATISTIC ("statistic", "ages with_immatures after_each", String.class),
	EXPERIMENTS_SERIES_NAME ("name", "modeling", String.class),
	INPUTS_FOLDER("inputs_folder", "inputs", String.class),
	SETTINGS_FOLDER("settings_folder", "settings", String.class),
	STATISTIC_FOLDER("statistic_folder", "statistic", String.class),
	LOGS_FOLDER("logs_folder", "logs", String.class);
	
	public final static String
	HELP_TEXT = "Usage:\n" +
				" 	[--help] show list of expected arguments. \n\n" +
			
				"	[{--name} string]  name of experiments series | DEFAULT \"modeling\"\n"	+ 
				"			Examples:\n" +
				"			--name \"some modeling\"\n" +
				"			--name some_modeling\n\n" +

				"	[{-y, --years} int]  number of simulated years | DEFAULT 1\n" + 
				"			Examples:\n" +
				"			-y 200\n" +
				"			--years 200\n\n" +

				"	[{-e, --experiments} range]  range of simulated experiments | DEFAULT \"1\"\n" + 
				"			Examples:\n" +
				"			-e 1..100  (1st, 2nd, ... , 100th experiments will be modelled)\n" +
				"			-e 55      (55th experiment will be modelled)\n" +
				"			--experiments 1..100\n\n" +

				"	[{-p, --points} range]  range of point to test | DEFAULT \"1\"\n" + 
				"			Examples:\n" +
				"			-p 17     (17th point will be tested)\n" +
				"			-p 1..81  (1st, 2nd, ... , 81st points will be tested)\n" +
				"			--points 30..60\n\n" +

				"   [{--statistic} string]  statistic collecting properties which match to regexp:\n | DEFAULT \"ages with_immatures after_each\""+
				"                              ( ages\n"+
				"                               |genotypes\n"+
				"                               |with_immatures\n"+
				"                               |without_immatures\n"+
				"                               |after_each\n"+
				"                               |after_move_and_scenario\n"+
				"                               |after_evolution\n"+
				"                               |after_reproduction\n"+
				"                               |after_competition\n"+
				"                               |after_dieing\n"+
				"                              )*\n" + 
				"			Examples:\n" +
				"			--statistic \"ages with_immatures after_reproduction after_competition\"\n" +
				"			--statistic \"ages without_immatures after_each\"\n" +
				"			--statistic \"ageswithout_immatures after_dieing\"\n" +
				"			--statistic \"genotypes with_immatures after_dieing\"\n" +
				"			--statistic \"genotypes with_immatures after_dieing\"\n" +
				"			--statistic \"ages without_immatures after_dieing\"\n\n" +

				"	[{--inputs_folder} string]  path to a folder from which a parameters of experiments series will be taken | DEFAULT \"inputs\"" + 
				"			Examples:\n" +
				"			--inputs_folder \"d:\\Modeling\\inputs\"\n" +
				"			--inputs_folder \"subfolder of my project\"\n" +

				"	[{--statistic_folder} string]  path to a folder to which a statistic will be saved | DEFAULT \"statistic\"" + 
				"			Examples:\n" +
				"			--statistic_folder \"d:\\Modeling\\statistic\"\n" +
				"			--statistic_folder \"subfolder of my project\"\n" +

				"	[{--settings_folder} string]  path to a folder to which a settings of modelled experiments will be saved | DEFAULT \"settings\"" + 
				"			Examples:\n" +
				"			--settings_folder \"d:\\Modeling\\settings\"\n" +
				"			--settings_folder \"subfolder of my project\"\n" +

				"	[{--logs_folder} string]  path to folder to which a logs will be saved | DEFAULT \"statistic\"" + 
				"			Examples:\n" +
				"			--logs_folder \"d:\\Modeling\\logs\"\n" +
				"			--logs_folder \"subfolder of my project\"\n" +
				"";
	
	private static CMDLineParser parser;
	private Character shortName;
	private String fullName;
	private Object defaultValue;
	private Option option;
	private Class<?> valueClass;
	private Object value=null;
	
	static {
		parser = new CMDLineParser();
		for (CMDArgument argument : CMDArgument.values()) {
			try {
				if (argument.shortName == null) {
					Method addMethod = getShortAddMethod(argument.valueClass);
					argument.option = (Option) addMethod.invoke(parser, argument.fullName);
				}
				else {
					Method addMethod = getLongAddMethod(argument.valueClass);
					argument.option = (Option) addMethod.invoke(parser, argument.shortName, argument.fullName);
				}
			}
			catch (NoSuchMethodException | 
					SecurityException | 
					IllegalAccessException | 
					IllegalArgumentException | 
					InvocationTargetException e) {
				Logger.error(e);
			}
		}
	
	}
		
	private CMDArgument(char shortName, String fullName, Object defaultValue, Class<?> valueClass) {
		this.shortName = shortName;
		this.fullName = fullName;
		this.defaultValue = defaultValue;
		this.valueClass = valueClass;
	}
	
	private CMDArgument(String fullName, Object defaultValue, Class<?> valueClass) {
		this.shortName = null;
		this.fullName = fullName;
		this.defaultValue = defaultValue;
		this.valueClass = valueClass;
	}
	
	private static Method getShortAddMethod(Class<?> valueClass) throws NoSuchMethodException, SecurityException {
		if (valueClass.equals(Boolean.class))
			return CMDLineParser.class.getMethod("addBooleanOption", String.class);
		if (valueClass.equals(Integer.class))
			return CMDLineParser.class.getMethod("addIntegerOption", String.class);
		if (valueClass.equals(Double.class))
			return CMDLineParser.class.getMethod("addDoubleOption", String.class);
		if (valueClass.equals(String.class))
			return CMDLineParser.class.getMethod("addStringOption", String.class);
		if (valueClass.equals(Range.class))
			return CMDLineParser.class.getMethod("addRangeOption", String.class);
		return null;
	}
	
	private static Method getLongAddMethod(Class<?> valueClass) throws NoSuchMethodException, SecurityException {
		if (valueClass.equals(Boolean.class))
			return CMDLineParser.class.getMethod("addBooleanOption", char.class, String.class);
		if (valueClass.equals(Integer.class))
			return CMDLineParser.class.getMethod("addIntegerOption", char.class, String.class);
		if (valueClass.equals(Double.class))
			return CMDLineParser.class.getMethod("addDoubleOption", char.class, String.class);
		if (valueClass.equals(String.class))
			return CMDLineParser.class.getMethod("addStringOption", char.class, String.class);
		if (valueClass.equals(Range.class))
			return CMDLineParser.class.getMethod("addRangeOption", char.class, String.class);
		return null;
	}
	
	public static void parse(String[] arguments) throws IllegalOptionValueException, UnknownOptionException {
		parser.parse(arguments);
	}

	public Object getValue() {
		if (value == null)
			value = parser.getOptionValue(option, defaultValue);
		return value;
	}
	
}

