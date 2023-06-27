package login;

public class MainVo {
	private String div, sname, schedule;
	int year, month, day;
	
	public MainVo() {
		
	}
	
	public MainVo(String sname) {
		this.sname = sname;
	}	
	
	public MainVo(String sname, String schedule, int year, int month, int day) {
		this.sname = sname;
		this.schedule = schedule;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public MainVo(String div, String sname, String schedule, int year, int month, int day) {
		this.div = div;
		this.sname = sname;
		this.schedule = schedule;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public String getName() {
		return sname;
	}
	
	public String getSch() {
		return schedule;
	}

	public String getDiv() {
		return div;
	}
	
	public String setName() {
		return sname;
	}
	
	public String setSch() {
		return schedule;
	}
}
