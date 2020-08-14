package br.coffea.safekeeper.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;

public class LimitedTextField extends TextField {

	private IntegerProperty limit = new SimpleIntegerProperty();
	
	@Override
	public void replaceText(int start, int end, String text) {
		if (validate()) {
			super.replaceText(start, end, text);
		}
	}
	
	@Override
	public void replaceSelection(String replacement) {
		if(validate()) {
			super.replaceSelection(replacement);
		}
	}
	
	private boolean validate() {
		return lengthProperty().lessThan(limit).get();
	}
	
	public IntegerProperty limitProperty() {
		return this.limit;
	}
	
	public void setLimit(final int limit) {
		this.limitProperty().set(limit);
	}
	
	public int getLimit() {
		return this.limitProperty().get();
	}
	
}
