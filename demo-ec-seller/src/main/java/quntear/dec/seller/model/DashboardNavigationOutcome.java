package quntear.dec.seller.model;

public class DashboardNavigationOutcome {

	private String outcome;
	private String label;
	private Boolean active;
	
	public DashboardNavigationOutcome() {
	}
	
	public DashboardNavigationOutcome(String outcome, String label, Boolean active) {
		this.outcome = outcome;
		this.label = label;
		this.active = active;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
