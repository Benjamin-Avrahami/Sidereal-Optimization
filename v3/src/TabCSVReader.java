

import java.util.*;
import java.io.*;

public class TabCSVReader {
  private Scanner fileReader;
  private final String delimiter;
  private ArrayList<String> headers;
  private String currentLine;


  public TabCSVReader(String fileName) {
    delimiter = "\t";
    try {
      File f = new File(fileName);
      fileReader = new Scanner(f);
      String headerLine = nextLine(); // Gets headers so getNextLine does not return headers to user
      headers = new ArrayList<String>();
      Scanner headerReader = new Scanner(headerLine);
      headerReader.useDelimiter(delimiter);
      while (headerReader.hasNext()) {
        headers.add(headerReader.next());
      }
      currentLine = "";
    }
    catch (FileNotFoundException fe) {
			System.out.println("File not found");
      fileReader = null;
      headers = null;
      currentLine = "";
		}
  }

  public boolean hasNextLine() {
    return fileReader.hasNext();
  }

  // Returns next non-empty line, and moves onto the line for state-based operations
  // Will not return headers (used in constructor)
  public String nextLine() {
    currentLine = fileReader.nextLine();
    if (currentLine.trim().length() == 0) return nextLine(); // move on if blank line
    return currentLine;
  }


  // Returns the value on the current line under the header of columnHeader
  // If called before nextLine, will return columnHeader if it is a header
  // Each value (both in the file's headers and the current line) are separated by the delimiter, usually tab
  // If the columnHeader is not in the file's headers, returns empty string
  public String getLineValue(String columnHeader) {
    int headerValue = -1;
    for (int i = 0; i < headers.size(); ++i) {
      if (columnHeader.equals(headers.get(i))) {
        headerValue = i;
        break;
      }
    }
    if (headerValue == -1) {
      return "";
    }
    int index = 0;
    Scanner lineReader = new Scanner(currentLine);
    lineReader.useDelimiter(delimiter);
    String value = "";
    while (index <= headerValue) {
      value = lineReader.next();
      ++index;
    }
    return value;
  }

}
