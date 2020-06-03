package vip.malagu.config;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.bdf3.security.ui.builder.AbstractBuilder;
import com.bstek.dorado.view.widget.grid.Column;
import com.bstek.dorado.view.widget.grid.ColumnGroup;

@Component
public class ColumnBuilder extends AbstractBuilder<Column> {
	
	protected String getId(Column column){
		String id = column.getName();
		if (StringUtils.isEmpty(id)) {
			id = column.getCaption();
		}
		return id;
	}
	
	protected Collection<Column> getChildren(Column column){
		if(column instanceof ColumnGroup){
			return ((ColumnGroup)column).getColumns();
		}
		return new ArrayList<>();
	}

}
