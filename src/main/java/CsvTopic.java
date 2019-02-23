/**
 * A value object to hold the category and text value read from the CSV file
 * 
 * @author karthik
 *
 */
public class CsvTopic {

	private String category;

	private String text;

	public CsvTopic() {
		super();
	}

	public String getCategory() {
		return category;
	}

	public String getTextData() {
		return text;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "CsvTopic [category=" + category + ", text=" + text + "]";
	}

}
