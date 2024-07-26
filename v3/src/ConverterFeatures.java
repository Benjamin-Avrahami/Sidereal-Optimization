public class ConverterFeatures {
	public Converter conv;
	public String name;
	public boolean instantRun; // true if run in trade, false in economy
	
	ConverterFeatures(Converter con, String conName, boolean instRun) {
		conv = con.getCopy();
		name = conName;
		instantRun = instRun;
	}
}