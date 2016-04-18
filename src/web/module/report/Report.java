package web.module.report;

public class Report {

	private int id;
	private String name;
	private String start_date;
	private String end_date;

	public Report() {

	}

	public Report(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public boolean isNil() {
		return false;
	}

	public boolean matchesId(int rid) {
		return (this.id == rid);
	}

}
