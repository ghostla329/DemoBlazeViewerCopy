package utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	// instance initializer block
	static {
		preset();
	}
	// variable which holds the workbook
	private static Workbook workbook;

	private static Workbook preset() {
// the file is loaded into memory via sileinputstream
		try (FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + File.separator + "productData.xlsx")) {
			workbook = WorkbookFactory.create(fis);
		} catch (Exception e) {
			// if the error like io exception, file not foundexception ocuurs, then handle
			// it
			e.printStackTrace();
		}
		return workbook;

	}

	/**
	 * give strng he string value of the a cell
	 * 
	 * @param rowindex    the index of the row to be acessed
	 * @param columnindex the index of the column to be acessed
	 * @param sheetName   the bname of the sheet
	 * @return the string value present in the cell
	 */
	public static String getValue(int rowindex, int columnindex, String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		Row row = sheet.getRow(rowindex);
		Cell cell = row.getCell(columnindex);
		return cell.getStringCellValue();
	}

	/**
	 * 
	 * @param sheetName   the name of the sheet
	 * @param columnIndex the index of the column.index is for the column which
	 *                    contains your desired value to return.(index of price
	 *                    column if you want to retrive price)
	 * @param valueToFind the string value which product details like price and
	 *                    description you want to find
	 * @return the value if it exists. else returns null if doesnot exists.
	 */
	public static String itemDetailsfnderByProdName(String sheetName, int columnIndex, String valueToFind) {
		// get acess to sheet
		Sheet sheet = workbook.getSheet(sheetName);
		// create an iterator
		Iterator<Row> rowIterator = sheet.rowIterator();
		// this stores the value to be returned
		String returnable = null;
		// while it has next
		while (rowIterator.hasNext()) {
			// get the next row
			Row row = rowIterator.next();
			//check if the product nameexistin this row
			String comparingColumn = row.getCell(1).getStringCellValue();// here the 1 is the column index of the
																			// product name

			if (comparingColumn.equals(valueToFind)) {
				returnable = row.getCell(columnIndex).getStringCellValue();
				break;// i think this line is unnecessary
			} else {
				returnable = null;
			}
		}
		return returnable;
	}

	/**
	 * 
	 * @param sheeetname- name of the sheet.
	 * @returnthe number of row inthat sheet.note that it gets the number of rows
	 *            accord to first non blank row found to last lnon blank row found.
	 *            may give in consstent rtesult when the a row has inconsistent
	 *            column nuber.
	 */
	public static int numberOfitems(String sheeetname) {// maybe not required.alreaddy got the data
		Sheet sheet = workbook.getSheet(sheeetname);
		Iterator<Row> row = sheet.iterator();
		// number ofd row counter
		int count = 0;
		while (row.hasNext()) {
			row.next();
			count++;
		}
		return count - 1;// cause the heading is also included in the count;
	}

}
