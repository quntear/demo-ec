package quntear.dec.view;

import java.util.HashSet;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

public class MessagesKeywordResolver extends SearchKeywordResolver {

	@Override
	public boolean isResolverForKeyword(SearchExpressionContext context, String keyword) {
		return "messages".equals(keyword);
	}

	@Override
	public void resolve(SearchKeywordContext context, UIComponent base, String keyword) {
		KeywordResolverHelper.getNamingContainer(base).ifPresent(form -> {
			Set<String> messageClientIds = new HashSet<>();
			VisitContext visitContext = VisitContext.createVisitContext(context.getSearchExpressionContext().getFacesContext());

			form.visitTree(visitContext, (visit, target) -> {
				if (target instanceof UIMessage) {
					messageClientIds.add(target.getClientId());
				}
				return VisitResult.ACCEPT;
			});

			if (!messageClientIds.isEmpty()) {
				context.invokeContextCallback(new UIMessage() {
					@Override
					public String getClientId(FacesContext context) {
						return String.join(" ", messageClientIds);
					}
				});
			}
		});

		context.setKeywordResolved(true);
	}

}
