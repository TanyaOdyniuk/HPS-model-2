package point_initialization.inputs_areas;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.Exceptions.InvalidInput;
import exceptions.Exceptions.Negative;
import exceptions.Exceptions.NotAllGenotypes;
import exceptions.Exceptions.NotDouble;
import exceptions.Exceptions.NotGenotype;
import exceptions.Exceptions.NotInRange;
import exceptions.Exceptions.NotInteger;
import exceptions.Exceptions.WrongFileStructure;

public class ViabilityTest {
	
	private String[][] rows;
	private static final String input = 
	";(xL)xR;(xL)yR\n" +
	"����������������� �����;11;9;\n" +
	"������� 1-�� �������;5;4\n" +
	"������������;0.52;0.45\n" +
	"������� ���������� ������������ S;5;4;\n" +
	"����������� ����� ���������� S;0.06;0.03;\n" +
	"I-�� ����;0.04;0.04;\n" +
	"����������� �� ���������� S;0.5;0.5\n" +
	"���������������������;0.7;0.9;\n" +
	"������� ���������� C;5;4\n" +
	"����������� ����� ���������� �;0.2;0.2;\n" +
	"I-�� ����;0.1;0.1;\n" +
	"����������� �� ���������� �;0.25;0.328\n" +
	"����������� �����������;0.9;0.7;\n" +
	"�����������;0.1;0.1\n" +
	"������������;1200;0.9;\n" +
	"�����������;0.2;0.2\n" +
	"������.����. �����������;0;0.5;\n" +
	"�����������;0;0.4\n" +
	"����������� � ��������;1;1\n" +
	"����������� � ��������;4;4;\n" +
	"����������� � ��������;9;9\n" +
	"����������� � ��������;16;16;\n" +
	"����������� � ��������;22;19;\n" +
	"����������� � ��������;25;20\n" +
	"����������� � ��������;27;21\n" +
	"����������� � ��������;28;21\n" +
	"����������� � ��������;28;21;\n" +
	"����������� � ��������;28;21;\n";

	@Before
	public void setUpBefore() throws Exception {
		rows = CSVHelper.getTrimmedTable(input);
	}
	
	@After
	public void tearDownAfter() throws Exception {
		Viability.getKnownGenotypesSet().clear();
	}

	@Test(expected = WrongFileStructure.class)
	public void testWrongFileStructure() throws InvalidInput {
		rows[6] = new String[7];
		String input = getInput(rows);
		new Viability(input);
	}

	@Test(expected = NotGenotype.class)
	public void testNotGenotype() throws InvalidInput {
		rows[0][1] = "xR(xr(";
		String input = getInput(rows);
		new Viability(input);
	}

	@Test(expected = NotGenotype.class)
	public void testUnknownGenotype() throws InvalidInput {
		String input = getInput(rows);
		new Viability(input);
		rows[0][1] = "xRxR";
		input = getInput(rows);
		new Viability(input);
	}

	@Test(expected = NotAllGenotypes.class)
	public void testNotAllGenotypes() throws InvalidInput {
		String input = 
		";(xL)xR;(xL)yR;xLyL\n" +
		"����������������� �����;11;9;9;\n" +
		"������� 1-�� �������;5;4;4\n" +
		"������������;0.52;0.45;0.45\n" +
		"������� ���������� ������������ S;5;4;4;\n" +
		"����������� ����� ���������� S;0.06;0.03;0.03;\n" +
		"I-�� ����;0.04;0.04;0.04;\n" +
		"����������� �� ���������� S;0.5;0.5;0.5\n" +
		"���������������������;0.7;0.9;0.9;\n" +
		"������� ���������� C;5;4;4\n" +
		"����������� ����� ���������� �;0.2;0.2;0.2;\n" +
		"I-�� ����;0.1;0.1;0.1;\n" +
		"����������� �� ���������� �;0.25;0.328;0.328\n" +
		"����������� �����������;0.9;0.7;0.7;\n" +
		"�����������;0.1;0.1;0.1\n" +
		"������������;1200;0.9;0.9;\n" +
		"�����������;0.2;0.2;0.2\n" +
		"������.����. �����������;0;0.5;0.5;\n" +
		"�����������;0;0.4;0.4\n" +
		"����������� � ��������;1;1;1\n" +
		"����������� � ��������;4;4;4;\n" +
		"����������� � ��������;9;9;9\n" +
		"����������� � ��������;16;16;16;\n" +
		"����������� � ��������;22;19;19;\n" +
		"����������� � ��������;25;20;20\n" +
		"����������� � ��������;27;21;21\n" +
		"����������� � ��������;28;21;21\n" +
		"����������� � ��������;28;21;21;\n" +
		"����������� � ��������;28;21;21;\n";
		new Viability(input);
		input = getInput(rows);
		new Viability(input);
	}

	@Test(expected = NotDouble.class)
	public void testNotDouble() throws InvalidInput {
		rows[20][1] = "notDouble";
		String input = getInput(rows);
		new Viability(input);
	}

	@Test(expected = NotInteger.class)
	public void testInteger() throws InvalidInput {
		rows[1][1] = "notInteger";
		String input = getInput(rows);
		new Viability(input);
	}

	@Test(expected = Negative.class)
	public void testNegative() throws InvalidInput {
		rows[1][1] = "-17";
		String input = getInput(rows);
		new Viability(input);
	}

	@Test(expected = NotInRange.class)
	public void testNotInRange() throws InvalidInput {
		rows[3][1] = "1.5";
		String input = getInput(rows);
		new Viability(input);
	}
	
	private String getInput(String[][] rows) {
		StringBuffer buffer = new StringBuffer();
		for (int i=0; i<rows.length; i++) {
			for (int j=0; j<rows[i].length; j++)
				buffer.append(rows[i][j]).append(';');
			buffer.append('\n');
		}
		return buffer.toString();
	}

}
