package quntear.dec.seller.boundry;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.component.UIViewRoot;
import javax.inject.Inject;
import javax.inject.Named;

import quntear.dec.seller.model.DashboardNavigationOutcome;

@Named("navigation")
@RequestScoped
public class DashboardNavigation {

	private List<DashboardNavigationOutcome> links;
	
	@Inject
	private UIViewRoot view;
	
	@Inject
	@ManagedProperty("#{text}")
	private ResourceBundle bundle;
	
	@PostConstruct
	public void init() {
		this.links = new ArrayList<>();
		
		links.add(new DashboardNavigationOutcome("/dashboard/index", bundle.getString("dashboard"), view.getViewId().contains("dashboard/index")));
		//links.add(new DashboardNavigationOutcome("Orders", "Orders", view.getViewId().contains("index")));
		links.add(new DashboardNavigationOutcome("/dashboard/products/index", bundle.getString("products"), view.getViewId().contains("dashboard/products/index")));
		//links.add(new DashboardNavigationOutcome("Customers", "Customers", view.getViewId().contains("index")));
		//links.add(new DashboardNavigationOutcome("Reports", "Reports", view.getViewId().contains("index")));
	}

	public List<DashboardNavigationOutcome> getLinks() {
		return links;
	}
}
